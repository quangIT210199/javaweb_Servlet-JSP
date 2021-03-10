package com.laptrinhjavaweb.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.service.ICategoryService;

public class CategoryService implements ICategoryService {
	//Đây là kiểu gọi thủ công
//	private ICategoryDAO categoryDao;
//	
//	public CategoryService() {
//		categoryDao = new CategoryDAO();
//	}
	
	//Đây là gọi theo anonitation: trong spring thì là @autowire
	//Thì tk Inject này tự hiểu là sẽ tạo categoryDao = new CategoryDAO();
	//kể cả có nhiều hàm implement thì tìm sẽ dựa vào đối số của hàm đó
	//Hoặc khi em để 2 hàm chung 1 tên chắc chắn 2 hàm phải giống nhau, do đó nếu 2 hàm khác nhau logic thì nên để tên khác nhau
	@Inject
	private ICategoryDAO categoryDao;
	
	@Override
	public List<CategoryModel> findAll() {
		return categoryDao.findAll();
	}
	
}
