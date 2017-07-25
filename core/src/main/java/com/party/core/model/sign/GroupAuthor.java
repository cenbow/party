package com.party.core.model.sign;

/**
 * 签到小组
 * Created by wei.li
 *
 * @date 2017/6/6 0006
 * @time 12:04
 */
public class GroupAuthor extends SignGroup {
    private static final long serialVersionUID = -935148703965001245L;

    //创建者
    private String authorName;

    //创建者图像
    private String authorLogo;

    //开始时间
    private String startDate;

    //结束时间
    private String endDate;

    //排名
    private Integer rank;


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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "GroupAuthor{" +
                "authorName='" + authorName + '\'' +
                ", authorLogo='" + authorLogo + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", rank=" + rank +
                '}';
    }
}
