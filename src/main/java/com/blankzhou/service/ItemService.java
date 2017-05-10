package com.blankzhou.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blankzhou.dao.master.MasterItemDao;
import com.blankzhou.dao.slave.SlaveItemDao;
import com.blankzhou.dao.slave.SlaveUserDao;
import com.blankzhou.entity.Item;

@Service
public class ItemService {
	
	@Autowired
	private MasterItemDao masterItemDao;
	
	@Autowired
	private SlaveItemDao slaveItemDao;
	
	@Autowired
	private SlaveUserDao slaveUserDao;
	
	//获取首页商品信息
	public List<Map<String, Object>> home_item(){
		return slaveItemDao.home_item();
	}
	//获取登录者商品信息
	public List<Map<String, Object>> my_item(int user_id){
		return slaveItemDao.my_item(user_id);
	}
	// 获取需要修改的商品信息
	public List<Map<String, Object>> current_goods(int item_id){
		return slaveItemDao.current_goods(item_id);
	}
	//编辑商品信息
	public int edit_goods(Item item, HttpSession httpSession){
		int user_id = (Integer) httpSession.getAttribute("user_id");
		int del_flg = 0; // 设置默认商品状态值为0（0为开启、1为关闭）
		if (item.getItem_id() == 0 && "edit".equals(item.getAct())) {
			int store_id = slaveUserDao.getStoreId(user_id);
			// 添加商品
			item.setDel_flg(del_flg);
			item.setCreate_time(new Date().toString());
			item.setCreate_id(user_id);
			item.setStore_id(store_id);
			return masterItemDao.addItem(item);
		} else if(item.getItem_id() != 0 && "edit".equals(item.getAct())){
			// 修改商品
			item.setUpdate_id(user_id);
			return masterItemDao.updateItem(item.getItem_name(), item.getItem_price(), item.getUpdate_time(), item.getUpdate_id(), item.getItem_id());
		}else {
			// 删除商品
			del_flg = 1;
			item.setUpdate_id(user_id);
			return masterItemDao.delItem(del_flg, new Date().toString(), item.getUpdate_id(), item.getItem_id());
		}		
	}
}
