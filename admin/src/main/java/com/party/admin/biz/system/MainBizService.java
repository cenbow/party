package com.party.admin.biz.system;

import com.google.common.collect.Lists;
import com.party.common.utils.DateUtils;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 主页业务接口
 * Created by wei.li
 *
 * @date 2017/5/8 0008
 * @time 10:53
 */

@Service
public class MainBizService {

    //开始时间索引
    private final static Integer START_DATE_INDEX = 0;

    //结束时间索引
    private final static Integer END_DATE_INDEX = 14;


    //时间参数键
    private final static String DAY = "day";

    //总数参数键
    private final static String COUNT = "count";

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private IOrderFormService orderFormService;

    /**
     * 时间轴列表
     * @return 时间轴列表
     */
    public List<String> listDay(){
        List<String> list = Lists.newArrayList();
        for (int i = END_DATE_INDEX; i >= 0; i --){
            String day = DateUtils.formatDate(DateUtils.getNextNDay(new Date(), -i));
            list.add(day);
        }
        return list;
    }


    /**
     * 统计一段时间的数据
     * @param dayList 日期列表
     * @param countList 统计数据
     * @return 统计数据列表
     */
    public List<Integer> getCount(List<String> dayList, List<HashMap<String, Integer>> countList){
        List<Integer> resultList = Lists.newArrayList();
        for (String day : dayList){
            Integer num = 0;
            for (HashMap count : countList){
                if (day.equals(count.get(DAY))){
                    num = ((Long) count.get(COUNT)).intValue();
                    break;
                }
            }
            resultList.add(num);
        }
        return resultList;
    }

    /**
     * 会员列表
     * @return
     */
    public List<Integer> memberList(){
        List<String> dayList = this.listDay();
        List<HashMap<String, Integer>> list = memberService.countByDate(dayList.get(START_DATE_INDEX), dayList.get(END_DATE_INDEX));
        return this.getCount(dayList, list);
    }

    /**
     * 订单列表
     * @return
     */
    public List<Integer> orderList(){
        List<String> dayList = this.listDay();
        List<HashMap<String, Integer>> list = orderFormService.countByDate(dayList.get(START_DATE_INDEX), dayList.get(END_DATE_INDEX));
        return this.getCount(dayList, list);
    }

    /**
     * 报名列表
     * @return
     */
    public List<Integer> applyList(){
        List<String> dayList =this.listDay();
        List<HashMap<String, Integer>> list = memberActService.countByDate(dayList.get(START_DATE_INDEX), dayList.get(END_DATE_INDEX));
        return this.getCount(dayList, list);
    }
}
