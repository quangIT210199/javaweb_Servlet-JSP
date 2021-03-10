package com.laptrinhjavaweb.controller.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.IIOException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.SessionUtil;

//Sử dụng annotation để mapping controller (k cần phải cho vào web.xml)
//urlPatterns: như 1 cái mảng để có thể chứa nhiều url( multi url servlet)
@WebServlet(urlPatterns= {"/trang-chu","/dang-nhap","/thoat"})
//Phải kế thừa HttpServlet
public class HomeController extends HttpServlet {
	
	@Inject
	private ICategoryService categoryService;
	
	@Inject
	private INewService newService;
	
	@Inject 
	private IUserService userService;
	
	/**
	 *Cảnh báo từ eclip warning cần chú ý
	 */
	private static final long serialVersionUID = 1L;
	
	//db.propertie trong folder Resource
	ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Long categoryId = 1L;
//		request.setAttribute("news", newService.findByCategoryId(categoryId));
		
//		String title = "bài viết 4";
//		String content = "bai viet 4";
//		Long categoryId = 1L;
//		NewModel newModel = new NewModel();
//		newModel.setTitle(title);
//		newModel.setContent(content);
//		newModel.setCategoryId(categoryId);
//		newService.save(newModel);
		
//		long[] id = {7L}; // test xóa
//		newService.delete(id);
		
		//Đăng nhập và phân quyền trong jsp servlet jdbc mysql phần 1
		String action = request.getParameter("action");//lấy tham sốc action trên url để chạy if
		if(action != null && action.equals("login")) {
			String alert = request.getParameter("alert");
			String message = request.getParameter("message");
			if (message != null && alert != null) {
				//Để truyền giá trị ra view sử dụng setAtrribute
				request.setAttribute("message", resourceBundle.getString(message));
				request.setAttribute("alert", alert);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
			rd.forward(request, response);
			
		}else if(action != null && action.equals("logout")) {
			//thoát thì sẽ vào doGet
			SessionUtil.getInstance().removeValue(request, "USERMODEL");
			//Sử dụng tk response để chuyển từ controller đến controller /trang-chu
			response.sendRedirect(request.getContextPath()+"/trang-chu");
		}else {
			//Để truyền giá trị ra view sử dụng setAtrribute ra cái list bên view chạy forEach
			request.setAttribute("categories", categoryService.findAll());
			//RequestDispatcher này trả về View
			RequestDispatcher rd = request.getRequestDispatcher("/views/web/home.jsp");
			rd.forward(request, response);
		}
		//End Đăng nhập và phân quyền trong jsp servlet jdbc mysql phần 1
	}
	//Đăng nhập form
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action != null && action.equals("login")) {
			//nhận DL từ post gửi về qua request
			UserModel model = FormUtil.toModel(UserModel.class, request);//Nhận DL từ client
			//Lấy được thông tin, sau đó cho model đó vào sessionUtil
			model = userService.findByUserNameAndPasswordAndStatus(model.getUserName(), model.getPassword(), 1);
			if(model != null) {
				//key dùng để nhận diện các session và dùng như object bên view để gọi giá trị của biến
				//ngoài ra có id n tự sinh nữa là SessionID
				SessionUtil.getInstance().putValue(request, "USERMODEL", model); 
				if(model.getRole().getCode().equals("USER")) {
					response.sendRedirect(request.getContextPath() +"/trang-chu");
				}else if(model.getRole().getCode().equals("ADMIN")) {
					response.sendRedirect(request.getContextPath() +"/admin-home");
				}
			}else {
				//nếu k có user đó
				response.sendRedirect(request.getContextPath()+"/dang-nhap?action=login&message=username_password_invalid&alert=danger");
			}
		}
		
	}
}
