package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.dao.IUserDAO;
import com.laptrinhjavaweb.dao.impl.UserDAO;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.service.IUserService;

public class UserService implements IUserService {

	private IUserDAO userDAO;
	//không sử dụng tk Inject để quản lý biến, đây là cách cơ bản
	public UserService() {
		userDAO = new UserDAO();
	}
	
	@Override
	public UserModel findByUserNameAndPasswordAndStatus(String userName, String password, Integer status) {
		return userDAO.findByUserNameAndPassword(userName, password, status);
	}
	
}
