package com.party.admin.biz.advertise;


import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.core.model.advertise.Advertise;
import com.party.core.service.advertise.IAdvertiseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 广告业务
 */
@Service
public class AdvertiseBizService {

    @Autowired
    IAdvertiseService advertiseService;

    public void saveBiz(Advertise entity) throws Exception {
        if(StringUtils.isNotBlank(entity.getId())){//编辑表单保存
            Advertise ad = advertiseService.get(entity.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(entity, ad);//将编辑表单中的非\NULL值覆盖数据库记录中的值
            advertiseService.update(ad);//保存
        }else{//新增表单保存
            advertiseService.insert(entity);//保存
        }
    }
}
