package com.blankzhou.dao.master;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.blankzhou.entity.Item;
import com.blankzhou.mybatis.MyBatisRepository;

@MyBatisRepository
public interface MasterItemDao {
	@Insert("insert into deal_item (item_name, item_price, store_id, del_flg, create_time, create_id)"
			+ " values (#{item_name}, #{item_price}, #{store_id}, #{del_flg}, #{create_time}, #{create_id})")
	public int addItem(Item item);
	
	@Update("update deal_item set item_name=#{item_name}, item_price=#{item_price}, update_time=#{update_time}, update_id=#{update_id} where item_id=#{item_id}")
	public int updateItem(@Param("item_name")String item_name, @Param("item_price")String item_price, @Param("update_time")String update_time, @Param("update_id")int update_id, @Param("item_id")int item_id);

	@Update("update deal_item set del_flg=#{del_flg}, update_time=#{update_time}, update_id=#{update_id} where item_id=#{item_id}")
	public int delItem(@Param("del_flg")int del_flg, @Param("update_time")String update_time, @Param("update_id")int update_id, @Param("item_id")int item_id);
}
