package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

//Tầng service chỉ return những hàm của DAO
public interface INewService {
	List<NewModel> findByCategoryId(Long categoryId );
	
	NewModel save(NewModel newModel);
	
	NewModel update(NewModel updateNew);
	
	void delete(long[] ids);
	
	//ByQ
	List<NewModel> findAll(Pageble pageble);
	
	int getTotalItem();
	//Làm cho phần thêm, sửa, xóa
	NewModel findOne(long id);
}
