package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.CategoryModel;

public interface ICategoryDAO extends GenericDAO<CategoryModel>{
	//Lấy ra tất cả DL
	List<CategoryModel> findAll();
	
	CategoryModel findOne(long id);

	CategoryModel findOneByCode(String code);
}
