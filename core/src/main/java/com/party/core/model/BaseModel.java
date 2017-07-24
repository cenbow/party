package com.party.core.model;

import com.google.common.base.Strings;
import com.party.common.utils.UUIDUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基类
 * party
 * Created by wei.li
 * on 2016/8/10 0010.
 */
public class BaseModel implements Serializable{

    private static final long serialVersionUID = -2271648240532704003L;
    //主键
    private String id;

    //创建人
    private String createBy;

    //创建时间
    private Date createDate;

    //更新人
    private String updateBy;

    //更新时间
    private Date updateDate;

    //备注
    private String remarks;

    //删除标记
    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id='" + id + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", remarks='" + remarks + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModel baseModel = (BaseModel) o;

        if (id != null ? !id.equals(baseModel.id) : baseModel.id != null) return false;
        if (createBy != null ? !createBy.equals(baseModel.createBy) : baseModel.createBy != null) return false;
        if (createDate != null ? !createDate.equals(baseModel.createDate) : baseModel.createDate != null) return false;
        if (updateBy != null ? !updateBy.equals(baseModel.updateBy) : baseModel.updateBy != null) return false;
        if (updateDate != null ? !updateDate.equals(baseModel.updateDate) : baseModel.updateDate != null) return false;
        if (remarks != null ? !remarks.equals(baseModel.remarks) : baseModel.remarks != null) return false;
        return delFlag != null ? delFlag.equals(baseModel.delFlag) : baseModel.delFlag == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateBy != null ? updateBy.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (delFlag != null ? delFlag.hashCode() : 0);
        return result;
    }

    /**
     * 实体预操作
     * @param t 实体
     * @param <T>
     */
    public static<T extends BaseModel> void preInsert(T t){
        String uuid = UUIDUtils.generateRandomUUID();
        if (Strings.isNullOrEmpty(t.getId())) {//如果业务层没有指定主键id
            t.setId(uuid);
        }
        if (null == t.getCreateDate()) {//如果业务层没有指定创建时间
            t.setCreateDate(new Date());
        }
        if (null == t.getUpdateDate()) {//如果业务层没有指定更新时间
            t.setUpdateDate(new Date());
        }
        if (null == t.getDelFlag())
        {
            t.setDelFlag(DEL_FLAG_NORMAL);
        }
    }

    /**
     * 填充创建者信息
     * @param userId
     */
    public static<T extends BaseModel>  void preByInfo(String userId, T t){
        t.setCreateBy(userId);
        t.setUpdateBy(userId);
    }

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";
}
