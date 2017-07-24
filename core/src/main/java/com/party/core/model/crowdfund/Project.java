package com.party.core.model.crowdfund;

import com.party.core.model.BaseModel;

import java.util.Date;


/**
 * 众筹项目实体
 * Created by wei.li
 * @date 2017/2/16 0016
 * @time 16:49
 */
public class Project extends BaseModel{

    private static final long serialVersionUID = -5923585277400660719L;

    //项目标题
    private String title;

    //项目内容
    private String contentId;

    //支持者数目
    private Integer favorerNum;

    //目标筹集资金
    private Float targetAmount;

    //实际筹集资金
    private Float actualAmount;

    //实时筹集资金(有可能大于目标金额)
    private Float realTimeAmount;

    //发布者编号
    private String authorId;

    //众筹是否成功(0:众筹中 1:成功 2:失败 3:退款中 4：退款成功)
    private Integer isSuccess;

    //照片地址
    private String pic;

    //众筹结束时间
    private Date endDate;

    //宣言
    private String declaration;

    //风格
    private String style;
    
    private String qrCodeUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public Float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Float getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

    public Float getRealTimeAmount() {
        return realTimeAmount;
    }

    public void setRealTimeAmount(Float realTimeAmount) {
        this.realTimeAmount = realTimeAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Project project = (Project) o;

        if (title != null ? !title.equals(project.title) : project.title != null) return false;
        if (contentId != null ? !contentId.equals(project.contentId) : project.contentId != null) return false;
        if (favorerNum != null ? !favorerNum.equals(project.favorerNum) : project.favorerNum != null) return false;
        if (targetAmount != null ? !targetAmount.equals(project.targetAmount) : project.targetAmount != null)
            return false;
        if (actualAmount != null ? !actualAmount.equals(project.actualAmount) : project.actualAmount != null)
            return false;
        if (realTimeAmount != null ? !realTimeAmount.equals(project.realTimeAmount) : project.realTimeAmount != null)
            return false;
        if (authorId != null ? !authorId.equals(project.authorId) : project.authorId != null) return false;
        if (isSuccess != null ? !isSuccess.equals(project.isSuccess) : project.isSuccess != null) return false;
        if (pic != null ? !pic.equals(project.pic) : project.pic != null) return false;
        if (endDate != null ? !endDate.equals(project.endDate) : project.endDate != null) return false;
        if (declaration != null ? !declaration.equals(project.declaration) : project.declaration != null) return false;
        if (style != null ? !style.equals(project.style) : project.style != null) return false;
        return qrCodeUrl != null ? qrCodeUrl.equals(project.qrCodeUrl) : project.qrCodeUrl == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (favorerNum != null ? favorerNum.hashCode() : 0);
        result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
        result = 31 * result + (actualAmount != null ? actualAmount.hashCode() : 0);
        result = 31 * result + (realTimeAmount != null ? realTimeAmount.hashCode() : 0);
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        result = 31 * result + (isSuccess != null ? isSuccess.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (declaration != null ? declaration.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (qrCodeUrl != null ? qrCodeUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", contentId='" + contentId + '\'' +
                ", favorerNum=" + favorerNum +
                ", targetAmount=" + targetAmount +
                ", actualAmount=" + actualAmount +
                ", realTimeAmount=" + realTimeAmount +
                ", authorId='" + authorId + '\'' +
                ", isSuccess=" + isSuccess +
                ", pic='" + pic + '\'' +
                ", endDate=" + endDate +
                ", declaration='" + declaration + '\'' +
                ", style='" + style + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                '}';
    }
}
