package com.laptrinhjavaweb.controller.admin;

import java.io.IOException;

import javax.imageio.IIOException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.MessageUtil;

//Sử dụng annotation để mapping controller (k cần phải cho vào web.xml)
//urlPatterns: như 1 cái mảng để có thể chứa nhiều url( multi url servlet)
@WebServlet(urlPatterns= {"/admin-new"})
//Phải kế thừa HttpServlet
public class NewController extends HttpServlet {

	/**
	 *Cảnh báo từ eclip warning cần chú ý
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private INewService newService;
	
	@Inject
	private ICategoryService categoryService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//Xấy dựng bằng tay
		//		String pageStr = request.getParameter("page");
//		String maxPageItemStr = request.getParameter("maxPageItem");
//		NewModel model = new NewModel();
//		if(pageStr != null) {
//			model.setPage(Integer.parseInt(pageStr));
//		}
//		else {
//			model.setPage(1);
//		}
//		if(maxPageItemStr != null) {
//			model.setMaxPageItem(Integer.parseInt(maxPageItemStr));
//		}
		
		//Hàm toModel dựa vào model truyền vào và mapping với cái thẻ name
		NewModel model = FormUtil.toModel(NewModel.class, request);//Nhận DL từ client
		String view = "";
//		Integer offset = (model.getPage() - 1) * model.getMaxPageItem();// tính toán logic nên để ở tầng service, ở đây thì cho class PageRequest trong package paging
		if (model.getType().equals(SystemConstant.LIST)) {//TH là type list: lấy danh sách
			Pageble pageble = new PageRequest(model.getPage(), model.getMaxPageItem(),
					new Sorter(model.getSortName(), model.getSortBy()));// page(trang hiện tại) , limit (số lượng max trên 1 page), sort nhận từ client
			//Đẩy DL lên client
			model.setListResult(newService.findAll(pageble)); //
			model.setTotalItem(newService.getTotalItem());
			//Tính totalPage = số page trong db / số page trên 1 trang
			model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getMaxPageItem()));
			view = "/views/admin/new/list.jsp";
		} else if (model.getType().equals(SystemConstant.EDIT)) {
			if (model.getId() != null) {
				model = newService.findOne(model.getId());
			}
			//load toàn bộ danh mục lên view để Edit hoặc thêm mới( luôn luôn)
			request.setAttribute("categories", categoryService.findAll());
			view = "/views/admin/new/edit.jsp";
		}
		//trả String status ra view để bootStrap tự xử
		MessageUtil.showMessage(request);
		//Truyền model tới view để edit. nếu k edit thì model trống tới
		request.setAttribute(SystemConstant.MODEL, model);
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IIOException {
		
	}
}
