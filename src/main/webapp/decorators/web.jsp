<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Mặc định nếu các trang k có title sẽ tự thêm, còn nếu có thì ưu tiên ở trang đó -->
<title><dec:title default="Trang chủ" /></title>

	<!-- css -->
	<!-- nếu k dùng c:url
	<link href="${pageContext.request.contextPath}/template/web/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all"/> -->
	<!-- c:url là biến prefix "c" của JSTL: trước /template thì n sẽ tự apply các địa chỉ default -->
	<link href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all"/>
	<link href="<c:url value='/template/web/css/style.css' />" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
	<!--include: Thư mục chứa tất cả các tệp được đưa vào các tệp khác (ví dụ: header.jsp, footer.jsp, copyright.jsp). -->

	<!-- header: include các component common dùng chung -->
	<!-- Sử dụng các taglib của sitemesh -->
	<%@ include file="/common/web/header.jsp" %>
	<!-- header -->
	
	<div class="container" style="padding-top: 50px;">
	    <!--biến dec chính là cái prefix="dec" gọi cho tk taglib sitemesh-->
    	<!--Các trang khác đều thay đổi body-->
    	<!-- Để những các trang thay đổi body theo y.c thì tất cả các page con chỉ cần push các cái body n muốn thông qua biến dec-->
    	<dec:body/>
	</div>
	
	<!-- footer -->
	<%@ include file="/common/web/footer.jsp" %>
	<!-- footer -->
	
	<script type="text/javascript" src="<c:url value='/template/web/jquery/jquery.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/template/web/bootstrap/js/bootstrap.bundle.min.js' />"></script>
</body>
</html>