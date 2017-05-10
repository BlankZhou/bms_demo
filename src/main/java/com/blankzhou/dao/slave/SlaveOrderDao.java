package com.blankzhou.dao.slave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.blankzhou.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SlaveOrderDao {
	// 获取登录者订单信息
	@Select("SELECT * FROM deal_order WHERE del_flg = 0 and create_id=#{create_id} ORDER BY order_id DESC")
	public List<Map<String, Object>> my_order(@Param("create_id") int create_id);
	
	// 获取卖家出售订单信息
	@Select("SELECT * FROM deal_order WHERE store_id = (select store_id FROM deal_user where user_id=#{user_id}) AND del_flg = 0 ORDER BY order_id DESC")
	public List<Map<String, Object>> sell_orders(@Param("user_id") int user_id);
}
