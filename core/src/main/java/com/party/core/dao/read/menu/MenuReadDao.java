package com.party.core.dao.read.menu;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.menu.Menu;

/**
 * 菜单数据读取
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */
@Repository
public interface MenuReadDao extends BaseReadDao<Menu> {

	List<Menu> findByPermission(@Param(value = "permissions") List<String> permissions);
}
