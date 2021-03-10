package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.laptrinhjavaweb.dao.GenericDAO;
import com.laptrinhjavaweb.mapper.RowMapper;
import com.mysql.cj.xdevapi.Type;
//Xây dựng lại các hàm query chung
//Nếu search thì khác nhau sql, đối tượng, parameters
//Nế thao tác với dữ liệu thì sql, parameters
public class AbstractDAO<T> implements GenericDAO<T> {
	//db.propertie trong folder Resource
	ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
	
	public Connection getConnection() {
		try {
			Class.forName(resourceBundle.getString("driverName"));
			String url = resourceBundle.getString("url");
			String user = resourceBundle.getString("user");
			String password = resourceBundle.getString("password");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		Connection cnn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			cnn = getConnection();
			statement = cnn.prepareStatement(sql);
			// set parameters vào biến statement
			setParameter(statement, parameters);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				//Sử dụng hàm mapRow để lấy ptu
				results.add(rowMapper.mapRow(resultSet));
			}

			return results;
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (cnn != null) {
					cnn.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				return null;
			}
		}
	}

	private void setParameter(PreparedStatement statement, Object... parameters) {
		try {//vì tk parameter là object k xác định, nên phải tìm và ép kiểu 
			for (int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i];
				int index = i + 1;
				if (parameter instanceof Long) {
					statement.setLong(index, (Long) parameter);
				} else if (parameter instanceof String) {
					statement.setString(index, (String) parameter);
				} else if (parameter instanceof Integer) {
					statement.setInt(index, (Integer) parameter);
				} else if (parameter instanceof Timestamp) {
					statement.setTimestamp(index, (Timestamp) parameter);
				}
//				else if(parameter == null) {
//					//các Field đc set NULL thì phải set parameter Null cho chúng khi truyền vô database
//					statement.setNull(index, Types.NULL);
//				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(String sql, Object... parameters) {
		Connection cnn = null;
		PreparedStatement statement = null;
		
		try {
			cnn = getConnection();// của tk abstract
			cnn.setAutoCommit(false);
			
			statement = cnn.prepareStatement(sql);
			//set tham số
			setParameter(statement, parameters);
			statement.executeUpdate();
			
			cnn.commit();//Tất cả phải success thì commit mới lưu xuống database
		} catch (SQLException e) {
			//TH 1 thao tác bị lỗi thì rollback lại k cho thực thi sql
			if(cnn != null) {
				try {
					cnn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}finally {
			try {
				if (cnn != null) {
					cnn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public Long insert(String sql, Object... parameters) {
		ResultSet rs = null;
		Long id = null;
		Connection cnn = null;
		PreparedStatement statement = null;
		
		try {
			cnn = getConnection();// của tk abstract
			cnn.setAutoCommit(false);
			
			statement = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//set tham số
			setParameter(statement, parameters);
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();//lấy primary key do để auto tự tăng, phải thêm tk này Statement.RETURN_GENERATED_KEYS
			
			if(rs.next()) {
				id = rs.getLong(1);
			}
			
			cnn.commit();//Tất cả phải success thì commit mới lưu xuống database
			return id;
		} catch (SQLException e) {
			//TH 1 thao tác bị lỗi thì rollback lại k cho thực thi sql
			if(cnn != null) {
				try {
					cnn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}finally {
			try {
				if (cnn != null) {
					cnn.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int count(String sql, Object... parameters) {
		Connection cnn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			int count = 0;
			cnn = getConnection();
			statement = cnn.prepareStatement(sql);
			// set parameters
			setParameter(statement, parameters);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				//Sử dụng hàm mapRow để lấy ptu
				count = resultSet.getInt(1);
			}

			return count;
		} catch (SQLException e) {
			return 0;
		} finally {
			try {
				if (cnn != null) {
					cnn.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				return 0;
			}
		}
	}
}
