package com.blankzhou.dao.slave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.blankzhou.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SlaveItemDao {
	//获取首页商品信息
	@Select("SELECT * FROM deal_item left join deal_store on deal_item.store_id = deal_store.store_id where deal_item.del_flg = 0")
	public List<Map<String, Object>> home_item();
	
	//获取首页商品信息
	@Select("select * from deal_item where store_id = (select store_id FROM deal_user where user_id = #{user_id} AND del_flg = 0)")
	public List<Map<String, Object>> my_item(@Param("user_id")int user_id);
	// 获取需要修改的商品信息
	
	@Select("SELECT * FROM deal_item WHERE item_id = #{item_id}")
	public List<Map<String, Object>> current_goods(@Param("item_id")int item_id);
	
	@Select("SELECT * FROM deal_item left join deal_store on deal_item.store_id = deal_store.store_id WHERE item_id = #{item_id}")
	public Map<String, Object> select_item(@Param("item_id") int item_id);
}
