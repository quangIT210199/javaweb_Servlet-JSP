package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.mapper.RowMapper;

public interface GenericDAO <T> {//Viết những hàm sử dụng Chung
	//Object... parameters multi param
	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);

	//Cập nhật
	void update(String sql, Object... parameters);
	//Thêm
	Long insert(String sql, Object... parameters);
	//đếm số page để hiển thị
	int count(String sql, Object... parameters);
}
