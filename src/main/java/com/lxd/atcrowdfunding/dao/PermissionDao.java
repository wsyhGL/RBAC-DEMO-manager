package com.lxd.atcrowdfunding.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.lxd.atcrowdfunding.bean.Permission;
import com.lxd.atcrowdfunding.bean.User;

public interface PermissionDao {

	@Select("select * from t_permission where pid is null")
	Permission queryRootPermission();

	@Select("select * from t_permission where pid = #{pid}")
	List<Permission> queryChildPermission(Integer id);

	@Select("select * from t_permission")
	List<Permission> queryAll();

	void insertPsermission(Permission permission);

	@Select("select * from t_permission where id = #{id}")
	Permission queryById(Integer id);

	void updatePsermission(Permission permission);

	void deletePsermission(Permission permission);

	@Select("select permissionid from t_role_permission where roleid=#{roleid}")
	List<Integer> queryPermissionsByRoleid(Integer roleid);

	List<Permission> queryPermissionsByUsers(User dbUser);

}
