package com.laptrinhjavaweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.laptrinhjavaweb.model.NewModel;

public class NewMapper implements RowMapper<NewModel>{

	@Override
	public NewModel mapRow(ResultSet rs) {
		try {
			NewModel news = new NewModel();
			news.setId(rs.getLong("id"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setCategoryId(rs.getLong("categoryid"));
			news.setThumbnail(rs.getString("thumbnail"));
			news.setShortDescription(rs.getString("shortdescription"));
			news.setCreatedDate(rs.getTimestamp("createddate"));
			news.setCreatedBy(rs.getString("createdby"));
			//Sử dụng khi có sửa chữa và ng sửa
			if(rs.getTimestamp("modifieddate") != null) {
				news.setModifiedDate(rs.getTimestamp("modifieddate"));
			}
			if(rs.getTimestamp("modifiedby") != null) {
				news.setModifiedBy(rs.getString("modifiedby"));
			}
			
			return news;
		} catch (SQLException e) {
			return null;
		}
	}
	
}
