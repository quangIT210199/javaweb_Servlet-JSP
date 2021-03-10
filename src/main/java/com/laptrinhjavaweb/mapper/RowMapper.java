package com.laptrinhjavaweb.mapper;

import java.sql.ResultSet;

//dùng để mapping qua đối tượng ResultSet
public interface RowMapper<T> {
	T mapRow(ResultSet rs);
}
