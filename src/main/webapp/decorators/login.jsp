<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Mặc định nếu các trang k có title sẽ tự thêm, còn nếu có thì ưu tiên ở trang đó -->
<title><dec:title default="Đăng nhập" /></title>
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- file css -->
	<link href="<c:url value='/template/login/style.css' />" rel="stylesheet" type="text/css" media="all"/>
</head>
<body id="LoginForm">
	<!--include: Thư mục chứa tất cả các tệp được đưa vào các tệp khác (ví dụ: header.jsp, footer.jsp, copyright.jsp). -->
	
	    <!--biến dec chính là cái prefix="dec" gọi cho tk taglib sitemesh-->
    	<!--Các trang khác đều thay đổi body-->
    	<!-- Để những các trang thay đổi body theo y.c thì tất cả các page con chỉ cần push các cái body n muốn thông qua biến dec-->
    	<dec:body/>
</body>
</html>