package com.lxd.atcrowdfunding.service;

import java.util.List;

import com.lxd.atcrowdfunding.bean.Permission;
import com.lxd.atcrowdfunding.bean.User;

public interface PermissionService {

	Permission queryRootPermission();

	List<Permission> queryChildPermission(Integer id);

	List<Permission> queryAll();

	void insertPsermission(Permission permission);

	Permission queryById(Integer id);

	void updatePsermission(Permission permission);

	void deletePsermission(Permission permission);

	List<Integer> queryPermissionsByRoleid(Integer roleid);

	List<Permission> queryPermissionsByUsers(User dbUser);

}
