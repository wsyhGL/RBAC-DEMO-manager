package com.lxd.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.lxd.atcrowdfunding.bean.Role;

public interface RoleService {
	List<Role> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	List<Role> queryAll();

	void insertRolePermission(Map<String, Object> paramMap);


}
