package com.blankzhou.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blankzhou.dao.master.MasterOrderDao;
import com.blankzhou.dao.slave.SlaveItemDao;
import com.blankzhou.dao.slave.SlaveOrderDao;
import com.blankzhou.entity.Item;
import com.blankzhou.entity.Order;

@Service
public class OrderService {
	
	@Autowired
	private MasterOrderDao masterOrderDao;
	
	@Autowired
	private SlaveOrderDao slaveOrderDao;
	
	@Autowired
	private SlaveItemDao slaveItemDao;
	
	//获取登录者订单信息
	public List<Map<String, Object>> my_order(HttpSession httpSession){
		int create_id = (Integer) httpSession.getAttribute("user_id");
		
		return slaveOrderDao.my_order(create_id);
	}
	//获取卖家出售订单信息
	public List<Map<String, Object>> sell_orders(HttpSession httpSession){
		int user_id = (Integer) httpSession.getAttribute("user_id");
		
		return slaveOrderDao.sell_orders(user_id);
	}
	//添加订单
	public int add_order(Item item, HttpSession httpSession){
		Order order = new Order();
		Map<String, Object> map = slaveItemDao.select_item(item.getItem_id());
		int user_id = (Integer) httpSession.getAttribute("user_id");
		Date now = new Date();
		int item_id = (int) map.get("item_id");
		String item_name = (String) map.get("item_name");
		String item_price = (String) map.get("item_price");
		int store_id = (Integer) map.get("store_id");
		String store_name = (String) map.get("store_name");
		int payment_state = 0;
		int send_state = 0;
		int save_state = 0;
		int order_state = 0;
		int del_flg = 0;
		order.setItem_id(item_id);
		order.setItem_name(item_name);
		order.setItem_price(item_price);
		order.setStore_id(store_id);
		order.setStore_name(store_name);
		order.setPayment_state(payment_state);
		order.setSend_state(send_state);
		order.setSave_state(save_state);
		order.setOrder_state(order_state);
		order.setDel_flg(del_flg);
		order.setCreate_id(user_id);
		order.setCreate_time(now.toString());
		
		return masterOrderDao.add_order(order);
	}
	//支付订单
	public int pay_order(Order order, HttpSession httpSession){
		return masterOrderDao.pay_order(order.getOrder_id());
	}
	//取消订单
	public int cancel_order(Order order){
		return masterOrderDao.cancel_order(order.getOrder_id());
	}
	//删除订单
	public int del_order(Order order){
		return masterOrderDao.del_order(order.getOrder_id());
	}
	//确认收货
	public int affirm_goods(Order order){
		return masterOrderDao.affirm_goods(order.getOrder_id());
	}
	//发货
	public int send_goods(Order order,HttpSession httpSession){
		int user_id = (Integer) httpSession.getAttribute("user_id");
		
		return masterOrderDao.send_goods(order.getOrder_id(), new Date().toString(), user_id);
	}
}
