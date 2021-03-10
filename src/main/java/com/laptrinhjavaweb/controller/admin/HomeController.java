package com.laptrinhjavaweb.controller.admin;

import java.io.IOException;

import javax.imageio.IIOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Sử dụng annotation để mapping controller (k cần phải cho vào web.xml)
//urlPatterns: như 1 cái mảng để có thể chứa nhiều url( multi url servlet)
@WebServlet(urlPatterns= {"/admin-home"})
//Phải kế thừa HttpServlet
public class HomeController extends HttpServlet {

	/**
	 *Cảnh báo từ eclip warning cần chú ý
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Request tới trang chủ
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/home.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IIOException {
		
		
	}
}
