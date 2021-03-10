package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

public interface INewDAO extends GenericDAO<NewModel>{
	NewModel findOne(Long id);
	
	List<NewModel> findByCategoryId(Long categoryId);
	
	Long save(NewModel newModel);
	
	void update(NewModel updateNew);
	
	void delete(long id);
	
	//byQ
	//Lấy ra tất cả DL by quang
	List<NewModel> findAll(Pageble pageble);
	//Tính toán số lượng để hiển thị
	int getTotalItem();
}
