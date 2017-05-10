package com.blankzhou.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blankzhou.entity.Item;
import com.blankzhou.entity.Order;
import com.blankzhou.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	HashMap<String, Object> hash = new HashMap<String, Object>();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	// 获取登录者订单信息
	@RequestMapping("my_order")
	public List<Map<String, Object>> my_order(HttpSession httpSession) {
		return list = orderService.my_order(httpSession);
	}

	// 获取卖家出售订单信息
	@RequestMapping("sell_orders")
	public List<Map<String, Object>> sell_orders(HttpSession httpSession) {
		return list = orderService.sell_orders(httpSession);
	}

	// 添加订单
	@RequestMapping("add_order")
	public HashMap<String, Object> add_order(@ModelAttribute() Item item, HttpSession httpSession) {
		if (httpSession.getAttribute("LOGIN_INFO") == null) {
			hash.put("status", "not_login");
			hash.put("msg", "未登录！");
		} else if (orderService.add_order(item, httpSession) == 1) {
			hash.put("status", "success");
			hash.put("msg", "添加订单成功！");
		} else {
			hash.put("status", "failure");
			hash.put("msg", "添加订单失败！");
		}
		return hash;
	}

	// 支付订单
	@RequestMapping("pay_order")
	public HashMap<String, Object> pay_order(@ModelAttribute() Order order, HttpSession httpSession) {
		if (orderService.pay_order(order, httpSession) == 1) {
			hash.put("status", "success");
			hash.put("msg", "支付成功！");
		} else {
			hash.put("status", "no_money");
			hash.put("msg", "你的余额不足，订单支付失败！");
		}
		return hash;
	}

	// 取消订单
	@RequestMapping("cancel_order")
	public HashMap<String, Object> cancel_order(@ModelAttribute() Order order) {
		if (orderService.cancel_order(order) == 1) {
			hash.put("status", "success");
			hash.put("msg", "取消订单成功！");
		} else {
			hash.put("status", "failure");
			hash.put("msg", "取消订单失败");
		}
		return hash;
	}

	// 取消订单
	@RequestMapping("del_order")
	public HashMap<String, Object> del_order(@ModelAttribute() Order order) {
		if (orderService.del_order(order) == 1) {
			hash.put("status", "success");
			hash.put("msg", "删除订单成功！");
		} else {
			hash.put("status", "failure");
			hash.put("msg", "删除订单失败");
		}
		return hash;
	}

	// 确认收货
	@RequestMapping("affirm_goods")
	public HashMap<String, Object> affirm_goods(@ModelAttribute() Order order) {
		if (orderService.affirm_goods(order) == 1) {
			hash.put("status", "success");
			hash.put("msg", "确认收货成功！");
		} else {
			hash.put("status", "failure");
			hash.put("msg", "确认收货失败");
		}
		return hash;
	}

	// 发货
	@RequestMapping("send_goods")
	public HashMap<String, Object> send_goods(@ModelAttribute() Order order, HttpSession httpSession) {
		if (orderService.send_goods(order, httpSession) == 1) {
			hash.put("status", "success");
			hash.put("msg", "发货成功！");
		} else {
			hash.put("status", "failure");
			hash.put("msg", "发货失败");
		}
		return hash;
	}

}
