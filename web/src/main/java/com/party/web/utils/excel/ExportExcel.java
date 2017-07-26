/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.web.utils.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.party.common.annotation.ExcelField;
import com.party.common.utils.DateUtils;
import com.party.common.utils.Encodes;
import com.party.common.utils.SpringContextHolder;
import com.party.core.model.city.City;
import com.party.core.service.DictUtils;
import com.party.core.service.city.ICityService;
import com.party.web.web.dto.output.activity.MemberActOutput;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出 @see org.apache.poi.ss.SpreadsheetVersion）
 * 
 * @author jeeplus
 * @version 2013-04-21
 */
public class ExportExcel {

	private static Logger log = LoggerFactory.getLogger(ExportExcel.class);

	private ICityService cityService = SpringContextHolder.getBean(ICityService.class);

	/**
	 * 工作薄对象
	 */
	private SXSSFWorkbook wb;

	/**
	 * 工作表对象
	 */
	private Sheet sheet;

	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;

	/**
	 * 当前行号
	 */
	private int rownum;

	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	static List<Object[]> annotationList = Lists.newArrayList();
	
	static List<String> headerList = Lists.newArrayList();

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param cls
	 *            实体对象，通过annotation.ExportField获取标题
	 */
	public ExportExcel(String title, Class<?> cls) {
		this(title, cls, 1);
	}
	
	public ExportExcel(Class<?> cls, int type, int... groups) {
		List<String> headerList = getHeaderList(cls, type, groups);
		ExportExcel.headerList = headerList;
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param cls
	 *            实体对象，通过annotation.ExportField获取标题
	 * @param type
	 *            导出类型（1:导出数据；2：导出模板）
	 * @param groups
	 *            导入分组
	 */
	public ExportExcel(String title, Class<?> cls, int type, int... groups) {
		// Get annotation field
		List<String> headerList = getHeaderList(cls, type, groups);
		ExportExcel.headerList = headerList;
		initialize(title, headerList);
	}

	public List<String> getHeaderList(Class<?> cls, int type, int... groups) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		this.styles = createStyles(wb);
		annotationList = new ArrayList<Object[]>();
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs) {
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, f });
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, f });
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms) {
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, m });
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, m });
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
			};
		});
		// Initialize
		List<String> headerList = Lists.newArrayList();
		for (Object[] os : annotationList) {
			String t = ((ExcelField) os[0]).title();
			// 如果是导出，则去掉注释
			if (type == 1) {
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length == 2) {
					t = ss[0];
				}
			}
			headerList.add(t);
		}
		return headerList;
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headers
	 *            表头数组
	 */
	public ExportExcel(String title, String[] headers) {
		initialize(title, Lists.newArrayList(headers));
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	public ExportExcel(String title, List<String> headerList) {
	}

	/**
	 * 初始化函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	public void initialize(String title, List<String> headerList) {
		// Create title
		if (StringUtils.isNotBlank(title)) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(), headerList.size() - 1));
		}
		// Create header
		if (headerList == null) {
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length == 2) {
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			} else {
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {
			int colWidth = sheet.getColumnWidth(i) * 2;
			sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
		}
		log.debug("Initialize success.");
	}

	/**
	 * 创建表格样式
	 * 
	 * @param wb
	 *            工作薄对象
	 * @return 样式列表
	 */
	public Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		// style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);

		return styles;
	}

	/**
	 * 添加一行
	 * 
	 * @return 行对象
	 */
	public Row addRow() {
		return sheet.createRow(rownum++);
	}

	/**
	 * 添加一个单元格
	 * 
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val) {
		return this.addCell(row, column, val, 0, Class.class, "");
	}


	/**
	 * 添加一个单元格
	 * @param row  行
	 * @param column 列号
	 * @param val 值
	 * @param cellStyle 样式
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, CellStyle cellStyle){
		Cell cell = row.createCell(column);
		cellStyle = null == cellStyle ? wb.createCellStyle() : cellStyle;
		if (val == null) {
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);
		} else if (val instanceof String) {
			cell.setCellValue((String) val);
			cell.setCellStyle(cellStyle);
		} else if (val instanceof Integer) {

			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("0"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue((Integer) val);
		} else if (val instanceof Long) {
			cell.setCellValue((Long) val);
			cell.setCellStyle(cellStyle);
		} else if (val instanceof Double) {
			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("0.00"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue((Double) val);
			cell.setCellStyle(cellStyle);
		} else if (val instanceof Float) {
			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("0.00"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue((Float) val);
		} else if (val instanceof Date) {
			String date = DateUtils.formatDate((Date) val, "yyyy-MM-dd HH:mm");
			cell.setCellValue(date);
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}

	/**
	 * 添加一个单元格
	 * 
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @param align
	 *            对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType, String methodName) {
		Cell cell = row.createCell(column);
//		CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
		try {
			if (val == null) {
				cell.setCellValue("");
//				cell.setCellStyle(style);
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
//				cell.setCellStyle(style);
			} else if (val instanceof Integer) {
				CellStyle style1 = wb.createCellStyle();
				DataFormat format = wb.createDataFormat();
				style1.setDataFormat(format.getFormat("0"));
				cell.setCellStyle(style1);
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
//				cell.setCellStyle(style);
			} else if (val instanceof Double) {
				CellStyle style1 = wb.createCellStyle();
				DataFormat format = wb.createDataFormat();
				style1.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(style1);
				cell.setCellValue((Double) val);
//				cell.setCellStyle(style);
			} else if (val instanceof Float) {
				CellStyle style1 = wb.createCellStyle();
				DataFormat format = wb.createDataFormat();
				style1.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(style1);
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				String date = DateUtils.formatDate((Date) val, "yyyy-MM-dd HH:mm");
				cell.setCellValue(date);
			} else {
				if (fieldType != Class.class) {
					if (StringUtils.isNotEmpty(methodName)) {
						String fieldValue = (String) fieldType.getMethod(methodName).invoke(val);
						cell.setCellValue(fieldValue);
					} else {
						cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
					}
				} else {
					cell.setCellValue((String) Class
							.forName(
									this.getClass().getName()
											.replaceAll(this.getClass().getSimpleName(), "fieldtype." + val.getClass().getSimpleName() + "Type"))
							.getMethod("setValue", Object.class).invoke(null, val));
				}
//				cell.setCellStyle(style);
			}
		} catch (Exception ex) {
			log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		return cell;
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * 
	 * @return list 数据列表
	 */
	public <E> ExportExcel setDataList(List<E> list) {
		for (E e : list) {
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList) {
				ExcelField ef = (ExcelField) os[0];
				Object val = null;
				// Get entity value
				try {
					if (StringUtils.isNotBlank(ef.value())) {
						val = Reflections.invokeGetter(e, ef.value());
					} else {
						if (os[1] instanceof Field) {
							val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
						} else if (os[1] instanceof Method) {
							val = Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[] {}, new Object[] {});
							if (e instanceof MemberActOutput) {
								String method = ((Method) os[1]).getName();
								if (method.equals("getActivity")) {
									Reflections.invokeSetter(e, "Activity", val);
								}
								if (method.equals("getCity")) {
									val = Reflections.invokeMethod(e, method, new Class[] {}, new Object[] {});
									City city = cityService.get(val.toString());
									val = city.getName();
								}
							}
						}
					}
					if (StringUtils.isNotBlank(ef.dictType())) {
						val = DictUtils.getDictLabel(val == null ? "" : val.toString(), ef.dictType(), "");
					}
				} catch (Exception ex) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType(), ef.methodName());
				sb.append(val + ", ");
			}
			log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
		}
		return this;
	}

	/**
	 * 输出数据流
	 * 
	 * @param os
	 *            输出数据流
	 */
	public ExportExcel write(OutputStream os) throws IOException {
		wb.write(os);
		return this;
	}

	/**
	 * 输出到客户端
	 * 
	 * @param fileName
	 *            输出文件名
	 */
	public ExportExcel write(HttpServletResponse response, String fileName) throws IOException {
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
		write(response.getOutputStream());
		return this;
	}

	public ExportExcel(Workbook workbook) {
		this.wb = (SXSSFWorkbook) workbook;
	}

	/**
	 * 输出到文件
	 *
	 */
	public ExportExcel writeFile(String name) throws FileNotFoundException, IOException {
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}

	/**
	 * 清理临时文件
	 */
	public ExportExcel dispose() {
		wb.dispose();
		return this;
	}

	public static void setAnnotationList(List<Object[]> annotationList) {
		ExportExcel.annotationList = annotationList;
	}

	public SXSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(SXSSFWorkbook wb) {
		this.wb = wb;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public static List<String> getHeaderList() {
		return headerList;
	}

	public static void setHeaderList(List<String> headerList) {
		ExportExcel.headerList = headerList;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Map<String, CellStyle> getStyles() {
		return styles;
	}

	public void setStyles(Map<String, CellStyle> styles) {
		this.styles = styles;
	}

	// /**
	// * 导出测试
	// */
	// public static void main(String[] args) throws Throwable {
	//
	// List<String> headerList = Lists.newArrayList();
	// for (int i = 1; i <= 10; i++) {
	// headerList.add("表头"+i);
	// }
	//
	// List<String> dataRowList = Lists.newArrayList();
	// for (int i = 1; i <= headerList.size(); i++) {
	// dataRowList.add("数据"+i);
	// }
	//
	// List<List<String>> dataList = Lists.newArrayList();
	// for (int i = 1; i <=1000000; i++) {
	// dataList.add(dataRowList);
	// }
	//
	// ExportExcel ee = new ExportExcel("表格标题", headerList);
	//
	// for (int i = 0; i < dataList.size(); i++) {
	// Row row = ee.addRow();
	// for (int j = 0; j < dataList.get(i).size(); j++) {
	// ee.addCell(row, j, dataList.get(i).get(j));
	// }
	// }
	//
	// ee.writeFile("target/export.xlsx");
	//
	// ee.dispose();
	//
	// log.debug("Export success.");
	//
	// }

}
