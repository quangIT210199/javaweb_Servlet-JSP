package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.dao.INewDAO;
import com.laptrinhjavaweb.dao.impl.CategoryDAO;
import com.laptrinhjavaweb.dao.impl.NewDAO;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.INewService;

public class NewService implements INewService {

	@Inject
	private INewDAO newDao;
	
	@Inject
	private ICategoryDAO categoryDAO;
	
	@Override
	public List<NewModel> findByCategoryId(Long categoryId) {
		return newDao.findByCategoryId(categoryId);
	}

	@Override
	public NewModel save(NewModel newModel) {
//		Long newId = newDao.save(newModel); // lấy id
//		System.out.println(newId);
		
		newModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		//tìm tk cate theo code để set cateID cho tk newModel
		CategoryModel category = categoryDAO.findOneByCode(newModel.getCategoryCode());
		newModel.setCategoryId(category.getId());
		
		Long newId = newDao.save(newModel);
		return newDao.findOne(newId);// trả về cái tk vừa thêm
	}
	
	@Override
	public NewModel update(NewModel updateNew) {
		NewModel oldNew = newDao.findOne(updateNew.getId());
		updateNew.setCreatedDate(oldNew.getCreatedDate());
		updateNew.setCreatedBy(oldNew.getCreatedBy());
		updateNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		//tìm tk cate theo code để set cateID cho tk newModel
		CategoryModel category = categoryDAO.findOneByCode(updateNew.getCategoryCode());
		updateNew.setCategoryId(category.getId());
		
		newDao.update(updateNew);
		
		return newDao.findOne(updateNew.getId());
	}
	
	@Override
	public void delete(long[] ids) {
		for (long id: ids) {
			//1.delete comment (khoa ngoai new_id)
			//2.delete news
			newDao.delete(id);
		}
	}
	//ByQuang
	@Override
	public List<NewModel> findAll(Pageble pageble) {
		
		return newDao.findAll(pageble);
	} 
	//End by quang

	@Override
	public int getTotalItem() {
		return newDao.getTotalItem();
	}

	@Override
	public NewModel findOne(long id) {
		NewModel newModel = newDao.findOne(id);
		//Lấy categoryCode do chúng ta k dùng query Inner Join trong newDao
		CategoryModel categoryModel = categoryDAO.findOne(newModel.getCategoryId());
		
		newModel.setCategoryCode(categoryModel.getCode());
		
		return newModel;
	}
}
