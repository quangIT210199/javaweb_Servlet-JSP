package com.laptrinhjavaweb.controller.admin.api;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.utils.HttpUtil;
import com.laptrinhjavaweb.utils.SessionUtil;

//Sử dụng annotation để mapping controller (k cần phải cho vào web.xml)
//urlPatterns: như 1 cái mảng để có thể chứa nhiều url( multi url servlet)
@WebServlet(urlPatterns= {"/api-admin-new"})
//Phải kế thừa HttpServlet
public class NewAPI extends HttpServlet {
	
	@Inject
	private INewService newService;
	
	private static final long serialVersionUID = -915988021506484384L;
	
	//vì tk servlet doGet có r nên k dùng GET, vì việc trả data về view của mô hình MVC là GET rồi
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// set font cho DL từ client gửi về
		//Server phải định nghĩa kiểu DL để trả về cho client
		response.setContentType("application/json");
		//convert từ json data sang class NewModel
		NewModel newModel = HttpUtil.of(request.getReader()).toModel(NewModel.class);
		//lấy name của User đăng nhập
		newModel.setCreatedBy(((UserModel)SessionUtil.getInstance().getValue(request, "USERMODEL")).getUserName());
		
		//lưu vào database
		newModel = newService.save(newModel);//lưu và lấy ra tk vừa thêm
		
		//Chuyển dữ liệu newModel thành json và trả về client
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), newModel);
	}
	
	//ByQuang
//	public void doGet(HttpServletRequest req, HttpServletResponse res)
//			throws ServletException, IOException  {
//		req.setCharacterEncoding("UTF-8");
//		res.setContentType("application/json");
//		
//		List<NewModel> newModel = newService.findAll();
//		
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(res.getOutputStream(), newModel);
//	}
	//End by quang
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// set font cho DL từ client gửi về
		//Server phải định nghĩa kiểu DL để trả về cho client
		response.setContentType("application/json");
		//convert từ json data sang class NewModel
		NewModel updateNew = HttpUtil.of(request.getReader()).toModel(NewModel.class);
		//lấy name của User đăng nhập
		updateNew.setModifiedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUserName());
		//lưu vào database
		updateNew = newService.update(updateNew);//lưu và lấy ra tk vừa thêm
		
		//Chuyển dữ liệu newModel thành json và trả về client
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), updateNew);	
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// set font cho DL từ client gửi về
		//Server phải định nghĩa kiểu DL để trả về cho client
		response.setContentType("application/json");
		//convert từ json data sang class NewModel
		NewModel newModel = HttpUtil.of(request.getReader()).toModel(NewModel.class);
		//lấy mảng id từ newModel gửi từ client về
		newService.delete(newModel.getIds());
		
		//Chuyển dữ liệu newModel thành json và trả về client
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), "{}");	
	}
}
