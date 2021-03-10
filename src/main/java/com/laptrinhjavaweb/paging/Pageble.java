package com.laptrinhjavaweb.paging;

import com.laptrinhjavaweb.sort.Sorter;

public interface Pageble {
	Integer getPage();//lấy cái page hiện tại trong trang
	Integer getOffset();
	Integer getLimit();
	Sorter getSorter();
}
