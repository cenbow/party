package com.party.admin.biz.gatherForm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.party.common.utils.DateUtils;
import com.party.core.model.gatherForm.GatherForm;

@Service
public class GatherFormInfoBizService {
	/**
	 * 导出Excel
	 * 
	 * @param gatherForms
	 * @param fields
	 * @return
	 */
	public Workbook exportExcel(List<Map<String, Object>> gatherForms, List<GatherForm> fields) {
		Workbook wb = new SXSSFWorkbook();
		Sheet sheet0 = wb.createSheet();

		// 标题
		Row titleRow = sheet0.createRow(0);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("填写时间");
		for (int i = 0; i < fields.size(); i++) {
			Cell cell = titleRow.createCell(i + 1);
			cell.setCellValue(fields.get(i).getTitle());
		}

		// 内容
		for (int i = 0; i < gatherForms.size(); i++) {
			Row dataRow = sheet0.createRow(i + 1);

			Map<String, Object> map = gatherForms.get(i);
			String[] fieldValues = null != map.get("fieldValue") ? map.get("fieldValue").toString().split("maxIndex") : new String[0];

			Cell dateCell = dataRow.createCell(0);
			dateCell.setCellValue(DateUtils.formatDate((Date) map.get("createDate"), "yyyy-MM-dd HH:mm:ss"));
			for (int j = 0; j < fieldValues.length; j++) {
				Cell dataCell = dataRow.createCell(j + 1);
				dataCell.setCellValue(fieldValues[j]);
			}
		}
		return wb;
	}
}
