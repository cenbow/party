package com.party.admin.biz.crowdfund;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.output.crowdfund.AnalyzeOutput;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.crowdfund.ProjectAnalyze;
import com.party.core.model.crowdfund.ProjectLabel;
import com.party.core.model.crowdfund.SupportCount;
import com.party.core.model.label.Label;
import com.party.core.service.crowdfund.IProjectLabelService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 众筹分析业务接口
 * Created by wei.li
 *
 * @date 2017/7/10 0010
 * @time 15:04
 */

@Service
public class AnalyzeBizService {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IProjectLabelService projectLabelService;


    public List<AnalyzeOutput> list(ProjectAnalyze projectAnalyze, Page page){
        List<ProjectAnalyze> list = projectService.analyzeList(projectAnalyze, page);
        List<AnalyzeOutput> analyzeOutputList = LangUtils.transform(list, new Function<ProjectAnalyze, AnalyzeOutput>() {
            @Override
            public AnalyzeOutput apply(ProjectAnalyze projectAnalyze) {

                //统计总数
                AnalyzeOutput output = AnalyzeOutput.transform(projectAnalyze);

                convert(projectAnalyze, output);
                Date now = new Date();
                Date startDate = DateUtils.addDay(now, -15);
                Float sum = supportService.countAll(DateUtils.formatDate(startDate), projectAnalyze.getId());
                List<SupportCount> countList = supportService.count(DateUtils.formatDate(DateUtils.addDay(now, -15)), DateUtils.getTodayStr(), projectAnalyze.getId());

                Map<String, Float> moneyMap = Maps.newHashMap();
                List<Date> dateList = dateList();
                for (Date date : dateList){
                    Float mapSum = 0f;
                    for (SupportCount supportCount : countList){
                        if (DateUtils.formatDate(date, "yyyy-MM-dd").equals(DateUtils.formatDate(supportCount.getDate(), "yyyy-MM-dd"))){
                            mapSum = supportCount.getPayment();
                        }
                    }

                    mapSum = BigDecimalUtils.add(mapSum, sum);
                    mapSum = BigDecimalUtils.round(mapSum, 2);
                    moneyMap.put(DateUtils.formatDate(date, "yyyy-MM-dd"), mapSum);
                    sum = mapSum;
                }
                output.setMoneyMap(moneyMap);
                return output;
            }
        });
        return analyzeOutputList;
    }


    /**
     * 获取时间列表
     * @return 时间列表
     */
    public List<Date> dateList(){
        Date now = new Date();
        Date startDate = DateUtils.addDay(now, -14);
        List<Date> dateList =  DateUtils.getDateDifference(startDate, now);
        return dateList;
    }


    public List<String> dateStringList(){
        List<Date> list = dateList();
        List<String> dateList = Lists.newArrayList();
        for (Date date : list){
            dateList.add(DateUtils.formatDate(date, "yyyy-MM-dd"));
        }
        return dateList;
    }

    /**
     * 转换输出视图
     * @param projectAnalyze 项目分析
     * @param output 输出视图
     */
    public void convert(ProjectAnalyze projectAnalyze, AnalyzeOutput output){
        if (null != projectAnalyze.getIsFriend()){
            output.setIsFriend(YesNoStatus.getValue(projectAnalyze.getIsFriend()));
        }
        if (null != projectAnalyze.getIsGroup()){
            output.setIsGroup(YesNoStatus.getValue(projectAnalyze.getIsGroup()));
        }
        if (null != projectAnalyze.getIsSuccess()){
            if (projectAnalyze.getIsSuccess().equals(0)){
                output.setIsSuccess("众筹中");
            }
            if (projectAnalyze.getIsSuccess().equals(1)){
                output.setIsSuccess("众筹成功");
            }
            if (projectAnalyze.getIsSuccess().equals(2)){
                output.setIsSuccess("众筹失败");
            }
            if (projectAnalyze.getIsSuccess().equals(3)){
                output.setIsSuccess("退款中");
            }
            if (projectAnalyze.getIsSuccess().equals(4)){
                output.setIsSuccess("退款成功");
            }
        }

        if (!CollectionUtils.isEmpty(projectAnalyze.getLabelList())){
            List<String> labelNames = LangUtils.transform(projectAnalyze.getLabelList(), new Function<Label, String>() {
                @Override
                public String apply(Label label) {
                    return label.getName();
                }
            });
            String lables = Joiner.on(",").join(labelNames);
            output.setLabels(lables);
        }
    }


    /**
     * 保存标签
     * @param ids 标签编号
     * @param projectId 项目编号
     */
    @Transactional
    public void labelSave(Set<String> ids, String projectId){
        //删除项目关系
        projectLabelService.deleteByProjectId(projectId);

        for (String id : ids){
            ProjectLabel projectLabel = new ProjectLabel();
            projectLabel.setProjectId(projectId);
            projectLabel.setLabelId(id);
            projectLabelService.insert(projectLabel);
        }
    }

    /**
     *  导出excel
     * @param fileName 文件名称
     * @param list 分析列表
     * @param response 响应参数
     * @throws IOException
     */
    public void export(String fileName, List<AnalyzeOutput>  list, HttpServletResponse response)throws IOException {
        List<String> headerList
                = Lists.newArrayList("众筹者", "渠道", "区域", "公司", "职务", "联系电话",
                "加好友", "入群状态", "状态", "支持人数", "支持金额", "时间", "标签");

        List<String> dateList = dateStringList();
        for (String title : dateList){
            headerList.add(title);
        }

        ExportExcel exportExcel = new ExportExcel(fileName, headerList);
        exportExcel.setWb(new SXSSFWorkbook(500));
        exportExcel.setSheet(exportExcel.getWb().createSheet("Export"));
        exportExcel.setStyles(exportExcel.createStyles(exportExcel.getWb()));
        exportExcel.initialize(fileName, headerList);
        exportExcel = setDataList(list, exportExcel);
        exportExcel.write(response, fileName).dispose();
    }

    /**
     * 设置导出内容
     * @param list 输出列表
     * @param exportExcel 导出excel
     * @return 导出excel
     */
    public  ExportExcel setDataList(List<AnalyzeOutput> list, ExportExcel exportExcel){
        List<String> dateList = dateStringList();
        for (AnalyzeOutput analyzeOutput : list){
            Row row = exportExcel.addRow();
            exportExcel.addCell(row, 0, analyzeOutput.getAuthorName());
            exportExcel.addCell(row, 1, analyzeOutput.getParentName());
            exportExcel.addCell(row, 2, analyzeOutput.getCityName());
            exportExcel.addCell(row, 3, analyzeOutput.getCompany());
            exportExcel.addCell(row, 4, analyzeOutput.getJobTitle());
            exportExcel.addCell(row, 5, analyzeOutput.getMobile());
            exportExcel.addCell(row, 6, analyzeOutput.getIsFriend());
            exportExcel.addCell(row, 7, analyzeOutput.getIsGroup());
            exportExcel.addCell(row, 8, analyzeOutput.getIsSuccess());
            exportExcel.addCell(row, 9, analyzeOutput.getFavorerNum());
            exportExcel.addCell(row, 10, analyzeOutput.getActualAmount());
            exportExcel.addCell(row, 11, analyzeOutput.getCreateDate());
            exportExcel.addCell(row, 12, analyzeOutput.getLabels());
            for (int i = 0; i< dateList.size(); i++){
                String title = dateList.get(i);
                exportExcel.addCell(row, 13 + i, analyzeOutput.getMoneyMap().get(title));
            }
        }
        return exportExcel;
    }


}
