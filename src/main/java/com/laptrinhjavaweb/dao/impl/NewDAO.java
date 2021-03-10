package com.laptrinhjavaweb.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.dao.INewDAO;
import com.laptrinhjavaweb.mapper.NewMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

public class NewDAO extends AbstractDAO<NewModel> implements INewDAO {

	@Override
	public List<NewModel> findByCategoryId(Long categoryId) {
		String sql = "SELECT * FROM news WHERE categoryid = ?;";

		return query(sql, new NewMapper(), categoryId);
	}

	@Override
	public Long save(NewModel newModel) {
		StringBuilder sql = new StringBuilder("INSERT INTO news (title, content,");
		sql.append(" thumbnail, shortdescription, categoryid, createddate, createdby)");
		sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");
		
		return insert(sql.toString(), newModel.getTitle(), newModel.getContent(), 
				newModel.getThumbnail(), newModel.getShortDescription(), newModel.getCategoryId(),
				newModel.getCreatedDate(), newModel.getCreatedBy());
		//Cái dưới là tạo ra test trước, còn dùng thi hàm common trong AbstracDAO là dc
//		ResultSet rs = null;
//		Long id = null;
//		Connection cnn = null;
//		PreparedStatement statement = null;
//		try {
//			String sql = "Insert into news (title, content, categoryid) values (?, ?, ?);";
//			cnn = getConnection();// của tk abstract
//			cnn.setAutoCommit(false);
//			
//			statement = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			statement.setString(1, newModel.getTitle());
//			statement.setString(2, newModel.getContent());
//			statement.setLong(3, newModel.getCategoryId());
//			
//			statement.executeUpdate();
//			
//			rs = statement.getGeneratedKeys();//lấy primary key do để auto tự tăng, phải thêm tk này Statement.RETURN_GENERATED_KEYS
//			
//			if(rs.next()) {
//				id = rs.getLong(1);
//			}
//			
//			cnn.commit();//Tất cả phải success thì commit mới lưu xuống database
//			
//			return id;
//		} catch (SQLException e) {
//			//TH 1 thao tác bị lỗi thì rollback
//			if(cnn != null) {
//				try {
//					cnn.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//			return null;
//		} finally {
//			try {
//				if (cnn != null) {
//					cnn.close();
//				}
//				if (statement != null) {
//					statement.close();
//				}
//				if (rs != null) {
//					rs.close();
//				}
//			} catch (SQLException e2) {
//				return null;
//			}
//		}
	}
	
	@Override
	public void update(NewModel updateNew) {
		StringBuilder sql = new StringBuilder("UPDATE news SET title = ?, thumbnail = ?,");
		sql.append(" shortdescription = ?, content = ?, categoryid = ?,");
		sql.append(" createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? WHERE id = ?");
		update(sql.toString(), updateNew.getTitle(), updateNew.getThumbnail(), updateNew.getShortDescription(),
				updateNew.getContent(), updateNew.getCategoryId(), updateNew.getCreatedDate(), 
				updateNew.getCreatedBy(), updateNew.getModifiedDate(), 
				updateNew.getModifiedBy(), updateNew.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM news WHERE id = ?";
		//gọi hàm update bên abstracDAO
		update(sql, id);
	}

	@Override
	public NewModel findOne(Long id) {
		String sql = "SELECT * FROM news WHERE id = ?";
		List<NewModel> news = query(sql, new NewMapper(), id);
		return news.isEmpty() ? null : news.get(0);
	}
	
	//By quang
	@Override
	public List<NewModel> findAll(Pageble pageble) {	
		StringBuilder sql = new StringBuilder("SELECT * FROM news");
		//tk StringUtils của thư viện apache common lang dùng để ktra null và ""
		//Check TH k muốn sắp xếp
		if (pageble.getSorter() != null && StringUtils.isNotBlank(pageble.getSorter().getSortName()) && StringUtils.isNotBlank(pageble.getSorter().getSortBy())) {
			sql.append(" ORDER BY "+pageble.getSorter().getSortName()+" "+pageble.getSorter().getSortBy()+"");
		}
		if(pageble.getOffset() != null && pageble.getLimit() != null) {
			sql.append(" LIMIT " + pageble.getOffset()+", " + pageble.getLimit()+"");
			
		}
		return query(sql.toString(), new NewMapper());
//		String sql = "SELECT * FROM news LIMIT ?, ?";
//		
//		return query(sql.toString(), new NewMapper(), offset, limit);
	}

	@Override
	public int getTotalItem() {
		String sql ="SELECT count(*) FROM news";
		return count(sql);
	}

}
