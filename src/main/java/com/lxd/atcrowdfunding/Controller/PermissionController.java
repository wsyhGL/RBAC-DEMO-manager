package com.lxd.atcrowdfunding.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lxd.atcrowdfunding.bean.AJAXResult;
import com.lxd.atcrowdfunding.bean.Permission;
import com.lxd.atcrowdfunding.service.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@ResponseBody
	@RequestMapping("/loadAssignData")
	public Object loadAssignData(Integer roleid) {
		List<Permission> permissions = new ArrayList<Permission>();
		List<Permission> ps = permissionService.queryAll();
		
		List<Integer> permissionids = permissionService.queryPermissionsByRoleid(roleid);
		Map<Integer, Permission> permissionMap = new HashMap<>();
		for(Permission p : ps) {
			if(permissionids.contains(p.getId())) {
				p.setChecked(true);
			}else {
				p.setChecked(false);
			}
			permissionMap.put(p.getId(), p);
		}
		for(Permission p : ps) {
			Permission child = p;
			if(p.getPid() == 0) {
				permissions.add(p);
			}else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		
		return permissions;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Permission permission) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.deletePsermission(permission);
			result.setSuccess(true);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(Permission permission) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.updatePsermission(permission);
			result.setSuccess(true);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		return "permission/edit";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object Insert(Permission permission) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.insertPsermission(permission);
			result.setSuccess(true);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/add")
	public String add() {
		return "permission/add";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "permission/index";
	}
	
	@RequestMapping("/loadData")
	@ResponseBody
	public Object loadData() {
		List<Permission> permissions = new ArrayList<>();
		
		/*Permission root = permissionService.queryRootPermission();
		
		List<Permission> childPermissions = permissionService.queryChildPermission(root.getId());
		
		root.setChildren(childPermissions);*/
		
		/*
		 * 递归查询数据
		 * 缺点查询效率慢
		Permission parent = new Permission();
		parent.setId(0);
		queryChildPermissions(parent);
		
		return parent.getChildren();*/
		List<Permission> ps = permissionService.queryAll();
		/*
		 * 嵌套for循环
		System.out.println(ps);
		
		for(Permission p:ps) {
			Permission child = p;
			if(p.getPid() == 0) {
				permissions.add(p);
			}else {
				for(Permission innerPermission:ps) {
					if(child.getPid().equals(innerPermission.getId())) {
						Permission parent = innerPermission;
						parent.getChildren().add(child);
						break;
					}
				}
			}
		}*/
		
		//map集合方式
		Map<Integer, Permission> permissionMap = new HashMap<>();
		for(Permission p:ps) {
			permissionMap.put(p.getId(), p);
		}
		for(Permission p:ps) {
			Permission child = p;
			if(p.getPid() == 0) {
				permissions.add(p);
			}else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		return permissions;
		
	}
	/**
	 * 递归查询许可信息
	 */
	private void queryChildPermissions(Permission parent) {
		List<Permission> childPermissions = permissionService.queryChildPermission(parent.getId());
		
		for(Permission permission : childPermissions) {
			queryChildPermissions(permission);
		}
		
		parent.setChildren(childPermissions);
	}

}
