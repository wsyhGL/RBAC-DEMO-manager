package com.lxd.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxd.atcrowdfunding.bean.Permission;
import com.lxd.atcrowdfunding.bean.User;
import com.lxd.atcrowdfunding.dao.PermissionDao;
import com.lxd.atcrowdfunding.service.PermissionService;
@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Permission queryRootPermission() {
		// TODO Auto-generated method stub
		return permissionDao.queryRootPermission();
	}

	@Override
	public List<Permission> queryChildPermission(Integer id) {
		// TODO Auto-generated method stub
		return permissionDao.queryChildPermission(id);
	}

	@Override
	public List<Permission> queryAll() {
		// TODO Auto-generated method stub
		return permissionDao.queryAll();
	}

	@Override
	public void insertPsermission(Permission permission) {
		// TODO Auto-generated method stub
		permissionDao.insertPsermission(permission);
	}

	@Override
	public Permission queryById(Integer id) {
		// TODO Auto-generated method stub
		return permissionDao.queryById(id);
	}

	@Override
	public void updatePsermission(Permission permission) {
		// TODO Auto-generated method stub
		permissionDao.updatePsermission(permission);
	}

	@Override
	public void deletePsermission(Permission permission) {
		// TODO Auto-generated method stub
		permissionDao.deletePsermission(permission);
	}

	@Override
	public List<Integer> queryPermissionsByRoleid(Integer roleid) {
		// TODO Auto-generated method stub
		return permissionDao.queryPermissionsByRoleid(roleid);
	}

	@Override
	public List<Permission> queryPermissionsByUsers(User dbUser) {
		// TODO Auto-generated method stub
		return permissionDao.queryPermissionsByUsers(dbUser);
	}

}
