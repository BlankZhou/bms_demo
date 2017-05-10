package com.blankzhou.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blankzhou.dao.master.MasterUserDao;
import com.blankzhou.dao.slave.SlaveUserDao;
import com.blankzhou.entity.User;

@Service
public class UserService {
	
	@Autowired
	private MasterUserDao masterUserDao;
	
	@Autowired
	private SlaveUserDao slaveUserDao;
	
	//获取头部信息
	public List<Map<String, Object>> header(HttpSession httpSession){
		int user_id = (Integer) httpSession.getAttribute("user_id");
		
		return slaveUserDao.header(user_id);
	}
	//校样用户名是否存在
	public boolean proof(User user){
		Map<String, Object> map = slaveUserDao.proof(user.getUser_name());
		if (map != null) {
			return true;
		} else {
			return false;
		}
	}
	//用户登录
	public boolean login(User user,HttpSession httpSession){
		
		if ("login".equals(user.getAct())) {
			Map<String, Object> map = slaveUserDao.login(user.getUser_name(), user.getPassword());
			if (map != null) {
				httpSession.setAttribute("LOGIN_INFO", map);
				httpSession.setAttribute("user_id", map.get("user_id"));
				return true;
			}
		} else if ("signup".equals(user.getAct())) {
			int rlt = masterUserDao.addUser(user.getUser_name(), user.getPassword());
			if (rlt == 1) {
				return true;
			}
		}
		
		return false;
	}
	
}
