package com.party.core.model.distributor;

/**
 * 分销关系和统计
 * Created by wei.li
 *
 * @date 2017/3/8 0008
 * @time 16:10
 */
public class WithCount extends  DistributorRelation {

    private static final long serialVersionUID = 6583236762743674157L;

    //浏览量
    private Integer viewNum;

    //分享量
    private Integer shareNum;

    //报名量
    private Integer applyNum;

    //销售量
    private Integer salesNum;

    //众筹数
    private Integer crowdfundNum;

    //支持人数
    private Integer favorerNum;

    //支持金额
    private Float favorerAmount;

    //创建者名称
    private String authorName;

    //创建者图像
    private String authorLogo;

    //创建者公司
    private String authorCompany;

    //创建者区域
    private String cityName;

    //创建者职务
    private String authorJobTitle;

    //创建者电话
    private String authorMobile;

    //时间编号
    private String eventId;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public Integer getCrowdfundNum() {
        return crowdfundNum;
    }

    public void setCrowdfundNum(Integer crowdfundNum) {
        this.crowdfundNum = crowdfundNum;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public Float getFavorerAmount() {
        return favorerAmount;
    }

    public void setFavorerAmount(Float favorerAmount) {
        this.favorerAmount = favorerAmount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLogo() {
        return authorLogo;
    }

    public void setAuthorLogo(String authorLogo) {
        this.authorLogo = authorLogo;
    }

    public String getAuthorCompany() {
        return authorCompany;
    }

    public void setAuthorCompany(String authorCompany) {
        this.authorCompany = authorCompany;
    }

    public String getAuthorJobTitle() {
        return authorJobTitle;
    }

    public void setAuthorJobTitle(String authorJobTitle) {
        this.authorJobTitle = authorJobTitle;
    }

    public String getAuthorMobile() {
        return authorMobile;
    }

    public void setAuthorMobile(String authorMobile) {
        this.authorMobile = authorMobile;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
