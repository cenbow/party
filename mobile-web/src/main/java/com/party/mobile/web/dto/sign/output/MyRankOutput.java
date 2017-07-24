package com.party.mobile.web.dto.sign.output;

import com.party.core.model.sign.GroupMember;
import org.springframework.beans.BeanUtils;

/**
 * 我的排行输出视图
 * Created by wei.li
 *
 * @date 2017/6/9 0009
 * @time 15:19
 */
public class MyRankOutput extends GroupMember {

    //排行
    private Integer rank;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public static MyRankOutput transform(GroupMember groupMember){
        MyRankOutput myRankOutput = new MyRankOutput();
        BeanUtils.copyProperties(groupMember, myRankOutput);
        return myRankOutput;
    }
}
