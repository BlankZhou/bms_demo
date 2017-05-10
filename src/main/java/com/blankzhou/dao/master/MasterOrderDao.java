package com.blankzhou.dao.master;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.blankzhou.entity.Order;
import com.blankzhou.mybatis.MyBatisRepository;

@MyBatisRepository
public interface MasterOrderDao {
	// 添加订单
	@Insert("insert into deal_order (item_id, item_name, item_price, store_id, store_name, payment_state, send_state, save_state, order_state, del_flg, create_time, create_id) "
				+ "values (#{item_id}, #{item_name}, #{item_price}, #{store_id}, #{store_name}, #{payment_state}, #{send_state}, #{save_state}, #{order_state}, #{del_flg}, #{create_time}, #{create_id})")
	public int add_order(Order order);
	
	// 支付订单
	@Update("update deal_order set payment_state = 1 where order_id = #{order_id}")
	public int pay_order(@Param("order_id")int order_id);
	
	// 取消订单
	@Update("update deal_order set order_state = 2 where order_id = #{order_id}")
	public int cancel_order(@Param("order_id")int order_id);
	
	// 删除订单
	@Update("update deal_order set del_flg = 1 where order_id = #{order_id}")
	public int del_order(@Param("order_id")int order_id);
	
	// 确认收货
	@Update("update deal_order set save_state = 1, order_state = 1 where order_id = #{order_id}")
	public int affirm_goods(@Param("order_id")int order_id);
	
	// 发货
	@Update("update deal_order set send_state = 1, update_time = #{update_time}, update_id = #{update_id} where order_id = #{order_id}")
	public int send_goods(@Param("order_id")int order_id, @Param("update_time")String update_time, @Param("update_id")int update_id);

}
