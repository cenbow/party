package com.party.mobile.web.dto.dynamic.output;

/**
 * 动态点赞输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 20:13 16/11/9
 * @Modified by:
 */
public class DyLoveOutput {
    //动态点赞作者
    private DyMemberOutput author;

    public DyMemberOutput getAuthor() {
        return author;
    }

    public void setAuthor(DyMemberOutput author) {
        this.author = author;
    }
}
