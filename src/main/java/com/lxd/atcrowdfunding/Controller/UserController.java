package com.lxd.atcrowdfunding.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;

import org.activiti.engine.impl.cmd.AddCommentCmd;
import org.activiti.explorer.ui.task.listener.DeleteSubTaskClickListener;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.ibatis.annotations.Insert;
import org.apache.poi.ss.formula.functions.Index;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lxd.atcrowdfunding.bean.AJAXResult;
import com.lxd.atcrowdfunding.bean.Page;
import com.lxd.atcrowdfunding.bean.Role;
import com.lxd.atcrowdfunding.bean.User;
import com.lxd.atcrowdfunding.service.RoleService;
import com.lxd.atcrowdfunding.service.UserService;
import com.mxgraph.swing.util.mxGraphActions.EditAction;

@Controller
@RequestMapping("/user")
public class UserController {
	/**
	 * 用户首页
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleservice;
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer userid,Integer[] unassginroLeids) {
		AJAXResult result = new AJAXResult();
		try {
			//增加关系表数据
			Map<String, Object> map = new HashMap<>();
			map.put("userid", userid);
			map.put("roleids",unassginroLeids);
			userService.insertUserRoles(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/dounAssign")
	public Object dounAssign(Integer userid,Integer[] assginroLeids) {
		AJAXResult result = new AJAXResult();
		try {
			//删除关系表数据
			Map<String, Object> map = new HashMap<>();
			map.put("userid", userid);
			map.put("roleids",assginroLeids);
			userService.deleteUserRoles(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Integer[] userid) {
		AJAXResult result = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("userids", userid);
			userService.deleteUsers(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	} 
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		try {
			userService.deleteById(id);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(User user) {
		AJAXResult result = new AJAXResult();
		try {
			userService.updateUser(user);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	@RequestMapping("/assign")
	public String assign(Integer id,Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		List<Role> roles = roleservice.queryAll();
		
		List<Role> assignedRoles = new ArrayList<>();
		List<Role> unassignedRoles = new ArrayList<>();
		
		//获取关系表数据
		List<Integer> rolesids = userService.queryRoleidsByUserid(id);
		
		for(Role role:roles) {
			if(rolesids.contains(role.getId())) {
				assignedRoles.add(role);
			}else {
				unassignedRoles.add(role);
			}
		}
		
		model.addAttribute("assignedRoles", assignedRoles);
		model.addAttribute("unassignedRoles", unassignedRoles);
		
		return "user/assign";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object Insert(User user) {
		AJAXResult result = new AJAXResult();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setCreatetime(sdf.format(new Date()));
			user.setUserpswd("123456");
			userService.insertUser(user);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@RequestMapping("add")
	public String add() {
		return "user/add";
	}

	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText,Integer pageno, Integer pagesize) {
		AJAXResult result = new AJAXResult();

		try {
			Map<String, Object> map = new HashMap<>();
			map.put("start", (pageno - 1) * pagesize);
			map.put("size", pagesize);
			map.put("queryText", queryText);

			List<User> users = userService.pageQueryData(map);

			// 总的数据条数
			int totalsize = userService.pageQueryCount(map);
			// 最大页码
			int totalno = 0;
			if (totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			} else {
				totalno = totalsize / pagesize + 1;
			}
			//分页对象
			Page<User> userPage = new Page<>();
			userPage.setDatas(users);
			userPage.setPageno(pageno);
			userPage.setTotalno(totalno);
			userPage.setTotalsize(totalsize);
			result.setData(userPage);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping("/index")
	public String Index() {
		return "user/index";
	}

	@RequestMapping("/index1")
	public String index1(@RequestParam(required = false, defaultValue = "1") Integer pageno,
			@RequestParam(required = false, defaultValue = "2") Integer pagesize, Model model) {
		// 分页查询
		// limit start，size
		Map<String, Object> map = new HashMap<>();
		map.put("start", (pageno - 1) * pagesize);
		map.put("size", pagesize);

		List<User> users = userService.pageQueryData(map);

		model.addAttribute("users", users);
		// 当前页码数
		model.addAttribute("pageno", pageno);
		// 总的数据条数
		int totalsize = userService.pageQueryCount(map);
		// 最大页码
		int totalno = 0;
		if (totalsize % pagesize == 0) {
			totalno = totalsize / pagesize;
		} else {
			totalno = totalsize / pagesize + 1;
		}
		model.addAttribute("totalno", totalno);
		return "user/index";
	}

}
