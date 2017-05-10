package com.blankzhou.dao.master;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.blankzhou.mybatis.MyBatisRepository;

@MyBatisRepository
public interface MasterUserDao {
	
	@Insert("insert into deal_user (user_name, password) values (#{user_name}, #{password})")
	public int addUser(@Param("user_name")String user_name, @Param("password")String password);
}
