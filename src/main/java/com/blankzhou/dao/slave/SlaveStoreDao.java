package com.blankzhou.dao.slave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.blankzhou.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SlaveStoreDao {
	// 获取登录者店铺信息
	@Select("SELECT * FROM deal_store WHERE store_id = #{store_id}")
	public List<Map<String, Object>> my_store(@Param("store_id")int store_id);
}
