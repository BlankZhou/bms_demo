package com.blankzhou.dao.slave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.blankzhou.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SlaveUserDao {
	
	// 获取头部信息
	@Select("select * from deal_user where user_id = #{user_id}")
	public List<Map<String, Object>> header(@Param("user_id")int user_id);

	// 校样用户名是否存在
	@Select("select * from deal_user where user_name = #{user_name}")
	public Map<String, Object> proof(@Param("user_name")String user_name);
	
	@Select("SELECT store_id FROM deal_user WHERE user_id = #{user_id}")
	public int getStoreId(@Param("user_id")int user_id);
	
	@Select("select * from deal_user where user_name = #{user_name} and password = #{password}")
	public Map<String, Object> login(@Param("user_name")String user_name, @Param("password")String password);
}
