package com.party.core.service.dynamic.biz;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.dynamic.Comment;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.dynamic.Dypics;
import com.party.core.model.love.Love;
import com.party.core.model.member.Member;
import com.party.core.service.dynamic.ICommentService;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.dynamic.IDypicsService;
import com.party.core.service.love.ILoveService;
import com.party.core.service.member.IMemberService;
import com.party.core.utils.MyBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DynamicBaseBizService {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ILoveService loveService;

    @Autowired
    private IDypicsService dypicsService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IDynamicService dynamicService;

    /**
     * 编辑或保存动态
     *
     * @param entity
     * @throws Exception
     */
    @Transactional
    public String saveBizDynamic(Dynamic entity,String curId) throws Exception {
        String[] picArray = null;
        if (StringUtils.isNotBlank(entity.getPics())) {
            picArray = entity.getPics().split("\\|");
        }
        if(null == entity.getCreateBy()|| Strings.isNullOrEmpty(entity.getCreateBy())){
            entity.setCreateBy(curId);
        }
        Member member = memberService.get(entity.getCreateBy());
        Integer picNum = member.getPicNum();
        if (null == picNum) {
            picNum = 0;
        }
        if (StringUtils.isNotEmpty(entity.getId())) {//编辑表单保存
            Dynamic dy = dynamicService.get(entity.getId());//从数据库取出记录的值
            entity.setUpdateBy(curId);
            Integer dbPicNum = 0;
            if (null != dy.getPics()) {
                dbPicNum = dy.getPics().split("\\|").length;
            }
            picNum = picNum - dbPicNum;
            if (picNum < 0) {
                picNum = 0;
            }
            MyBeanUtils.copyBeanNotNull2Bean(entity, dy);//将编辑表单中的非\NULL值覆盖数据库记录中的值
            dynamicService.update(dy);//保存
            dypicsService.deleteByRefId(entity.getId());
            if (StringUtils.isNotEmpty(entity.getPics())) {
                int sort = 0;
                for (String pic : entity.getPics().split("\\|")) {
                    this.insertDynamicPic(dy.getCreateBy(), entity.getId(), pic, sort, "1");
                    sort++;
                }
                picNum += sort;
            }
            member.setPicNum(picNum);
            memberService.update(member);
            return entity.getId();
        } else {//新增表单保存
            entity.setDynamicWay(1);
            entity.setUpdateBy(curId);
            String id = dynamicService.insert(entity);//保存
            if (StringUtils.isNoneBlank(entity.getPics())) {
                String[] picList = entity.getPics().split("\\|");
                for (int i = 0; i < picList.length; i++) {

                    Dypics dypics = new Dypics();
                    dypics.setCreateBy(entity.getCreateBy());
                    dypics.setCreateDate(entity.getCreateDate());
                    dypics.setUpdateBy(entity.getUpdateBy());
                    dypics.setUpdateDate(entity.getUpdateDate());
                    dypics.setDelFlag(entity.getDelFlag());
                    dypics.setRemarks(entity.getRemarks());

                    dypics.setRefId(entity.getId());
                    dypics.setSort(i);
                    dypics.setType(entity.getDynamicType());
                    dypics.setPicUrl(picList[i]);

                    dypicsService.insert(dypics);

                }
                picNum += picList.length;
            }
            member.setPicNum(picNum);
            memberService.update(member);
            return id;
        }
    }

    /**
     * 删除动态点赞
     * @param id
     */
    @Transactional
    public void delLoveBiz(String id){
        Love love = loveService.get(id);
        if(null != love){
            Dynamic dynamic = dynamicService.get(love.getRefId());
            Integer loveNum = dynamic.getLoveNum();
            loveNum--;
            if(loveNum < 0){
                loveNum = 0;
            }
            dynamic.setLoveNum(loveNum);
            dynamicService.update(dynamic);
            loveService.delete(id);
        }
    }
    /**
     * 删除动态点赞通过动态id
     * @param id
     */
    @Transactional
    public void delLoveByDynamicBiz(String id){
        Love search = new Love();
        search.setRefId(id);
        Integer loveCount = loveService.countLove(search);
        Dynamic dynamic = dynamicService.get(id);
        Integer loveNum = dynamic.getLoveNum();
        loveNum-=loveCount;
        dynamic.setLoveNum(loveNum);
        dynamicService.update(dynamic);
        loveService.delByDynamicId(id);
    }
    /**
     * 删除动态评论通过动态id
     * @param id
     */
    @Transactional
    public void delCommentByDynamicBiz(String id){
        Comment search = new Comment();
        search.setRefId(id);
        Integer commentCount = commentService.countComment(search);
        Dynamic dynamic = dynamicService.get(id);
        Integer commentNum = dynamic.getCommentNum();
        commentNum-=commentCount;
        dynamic.setCommentNum(commentNum);
        dynamicService.update(dynamic);
        commentService.delByDynamicId(id);
    }
    /**
     * 删除动态评论
     * @param id
     */
    @Transactional
    public void delCmtBiz(String id){
        Comment comment = commentService.get(id);
        if(null != comment){
            Dynamic dynamic = dynamicService.get(comment.getRefId());
            Integer commentNum = dynamic.getCommentNum();
            commentNum--;
            if(commentNum < 0){
                commentNum = 0;
            }
            dynamic.setCommentNum(commentNum);
            dynamicService.update(dynamic);
            commentService.delete(id);
        }
    }

    /**
     * @param authorId 发布者
     * @param refId    动态id
     * @param pic      图片
     * @param sort     排序
     * @param type     类型
     */
    public void insertDynamicPic(String authorId, String refId, String pic, Integer sort, String type) {
        Dypics dypics = new Dypics();
        dypics.setCreateBy(authorId);
        dypics.setUpdateBy(authorId);
        dypics.setRefId(refId);
        dypics.setSort(sort);
        dypics.setType(type);
        dypics.setPicUrl(pic);
        dypicsService.insert(dypics);
    }

    /**
     * 删除动态图片
     *
     * @param picUrl
     * @param dynaimcId
     */
    @Transactional
    public void delDynamicPic(String picUrl, String dynaimcId) {
        Dynamic dynamic = dynamicService.get(dynaimcId);
        Member member = memberService.get(dynamic.getCreateBy());
        Page page = new Page();
        page.setLimit(20);
        List<Dypics> pics = dypicsService.findByDynamicId(dynaimcId, page);
        Boolean hasPic = false;
        for (Dypics pic : pics) {
            if (pic.getPicUrl().equals(picUrl)) {
                dypicsService.delete(pic.getId());
                hasPic = true;
            }
        }
        if (hasPic) {
            List<String> picListTemp = Arrays.asList(dynamic.getPics().split("\\|"));
            List<String> picList = new ArrayList<String>(picListTemp);
            for (int i = 0; i < picListTemp.size(); i++) {
                String pic = picListTemp.get(i);
                if (pic.equals(picUrl)) {
                    picList.remove(i);
                    break;
                }
            }
            dynamic.setPics(StringUtils.join(picList, "|"));
            int picNum = member.getPicNum() <= 0 ? 0 : member.getPicNum();
            member.setPicNum(picNum - 1);
            memberService.update(member);
            dynamicService.update(dynamic);
        }
    }


    @Transactional
    public void delBiz(String id) {
        Dynamic dynamic = dynamicService.get(id);
        Member author = memberService.get(dynamic.getCreateBy());
        // 会员动态图片
        List<Dypics> memberDypics = dypicsService.findByMemberId(author.getId(), null);
        // 动态图片
        List<Dypics> dypics = dypicsService.findByDynamicId(id, null);

        // 更新会员图片数量
        int picNum = memberDypics.size() - dypics.size();
        author.setPicNum(picNum > 0 ? picNum : 0);
        memberService.update(author);
        //通过动态ID删除点赞
        delLoveByDynamicBiz(id);

        //通过动态ID删除评论
        delCommentByDynamicBiz(id);

        // 删除动态图片
        dypicsService.deleteByRefId(id);
        // 删除动态
        dynamicService.delete(id);

    }
}
