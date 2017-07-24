package com.party.core.model.sign;

/**
 * 签到项目
 * Created by wei.li
 *
 * @date 2017/6/6 0006
 * @time 17:29
 */
public class SignProjectAuthor extends SignProject {

    //创建者
    private String authorName;

    //创建者图像
    private String authorLogo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SignProjectAuthor that = (SignProjectAuthor) o;

        if (authorName != null ? !authorName.equals(that.authorName) : that.authorName != null) return false;
        return authorLogo != null ? authorLogo.equals(that.authorLogo) : that.authorLogo == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorLogo != null ? authorLogo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SignProjectAuthor{" +
                "authorName='" + authorName + '\'' +
                ", authorLogo='" + authorLogo + '\'' +
                '}';
    }
}
