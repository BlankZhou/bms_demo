package com.blankzhou.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blankzhou.dao.slave.SlaveStoreDao;
import com.blankzhou.dao.slave.SlaveUserDao;

@Service
public class StoreService {
	@Autowired
	private SlaveStoreDao slaveStoreDao;
	
	@Autowired
	private SlaveUserDao slaveUserDao;
	
	//获取登录者店铺信息
	public List<Map<String, Object>> my_store(HttpSession httpSession) {
		int user_id = (Integer) httpSession.getAttribute("user_id");
		int store_id = slaveUserDao.getStoreId(user_id);
		
		return slaveStoreDao.my_store(store_id);
	}
}
