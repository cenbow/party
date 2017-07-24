package com.party.core.model.records;

/**
 * 众筹跟进记录
 * Created by wei.li
 *
 * @date 2017/7/12 0012
 * @time 10:28
 */
public class UpRecordWithProject extends UpRecords {

    //众筹名称
    private String projectTitle;

    //众筹者
    private String authorName;

    //众筹者编号
    private String authorId;

    //众筹者图像
    private String authorLogo;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }


    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorLogo() {
        return authorLogo;
    }

    public void setAuthorLogo(String authorLogo) {
        this.authorLogo = authorLogo;
    }

    @Override
    public String toString() {
        return "UpRecordWithProject{" +
                "projectTitle='" + projectTitle + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorId='" + authorId + '\'' +
                ", authorLogo='" + authorLogo + '\'' +
                '}';
    }
}
