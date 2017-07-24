package com.party.core.model.crowdfund;

import java.io.Serializable;

/**
 * 众筹项目详情
 * Created by wei.li
 *
 * @date 2017/2/23 0023
 * @time 10:57
 */
public class ProjectContent implements Serializable {

    //项目详情编号
    private String id;

    //项目内容
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProjectContent() {
    }

    public ProjectContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectContent that = (ProjectContent) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
