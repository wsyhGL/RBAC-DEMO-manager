package com.lxd.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.lxd.atcrowdfunding.bean.Role;
import com.lxd.atcrowdfunding.bean.User;

public interface UserService {

	List<User> queryAll();

	User query4Login(User user);

	List<User> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void insertUser(User user);

	User queryById(Integer id);

	void updateUser(User user);

	void deleteById(Integer id);

	void deleteUsers(Map<String, Object> map);

	void insertUserRoles(Map<String, Object> map);

	void deleteUserRoles(Map<String, Object> map);

	List<Integer> queryRoleidsByUserid(Integer id);

}
