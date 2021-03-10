package com.laptrinhjavaweb.filter;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//triển khai từ interface ra
public class AuthorizationFilter implements Filter {

    private ServletContext context;//đây là logFile
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //phương thức chỉ được gọi một lần.  Nó được sử dụng để khởi tạo bộ lọc.
    	this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //ép kiểu về
    	HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();//lấu URL để ktra
        if (url.startsWith("/admin")) {
        	//Lấy User trong session ra ktra
            UserModel model = (UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL");
            if (model != null) {
                if (model.getRole().getCode().equals(SystemConstant.ADMIN)) {
                	// Cho phép request đi tiếp lên phía trước.
                	// Nó có thể tới một Filter tiếp theo hoặc tới mục tiêu. 
                    filterChain.doFilter(servletRequest, servletResponse);
                } else if (model.getRole().getCode().equals(SystemConstant.USER)) {	
                	//sendRedirect chuyển từ controller này sang controller khác
                    response.sendRedirect(request.getContextPath()+"/dang-nhap?action=login&message=not_permission&alert=danger");
                }
            } else {//Chưa đăng nhập
                response.sendRedirect(request.getContextPath()+"/dang-nhap?action=login&message=not_login&alert=danger");
            }
        } else {
        	// Cho phép request đi tiếp lên phía trước.
        	// Nó có thể tới một Filter tiếp theo hoặc tới mục tiêu.
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
