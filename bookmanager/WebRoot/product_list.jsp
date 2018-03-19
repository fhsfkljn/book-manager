<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

<title>bookStore列表</title>
<%--导入css --%>
<link rel="stylesheet" href="css/main.css" type="text/css" />
</head>

<body class="main">

	<jsp:include page="head.jsp" />
	<jsp:include page="menu_search.jsp" />

	<div id="divpagecontent">
		<table width="100%" border="0" cellspacing="0">
			<tr>

				<td>
					<div style="text-align:right; margin:5px 10px 5px 0px">
						<a href="index.jsp">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;计算机&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;图书列表
					</div>

					<table cellspacing="0" class="listcontent">
						<tr>
							<td>
								<h1>商品目录</h1>
								<hr />
								<h1>计算机</h1>&nbsp;&nbsp;&nbsp;&nbsp;共100种商品
								<hr />
								<div style="margin-top:20px; margin-bottom:5px">
									<img src="images/productlist.gif" width="100%" height="38" />
								</div>

								<table cellspacing="0" class="booklist">
									<tr>
									<c:forEach items="${page.books }" var="b">
										<td>

											<div class="divbookpic">
												<p>
													<a href="${pageContext.request.contextPath}/servlet/bookInfoServlet?id=${b.id}"><img src="${pageContext.request.contextPath}/upload/${b.img_url}" width="115" height="129"
														border="0" /> </a>
												</p>
											</div>

											<div class="divlisttitle">
												<a href="${pageContext.request.contextPath}/servlet/bookInfoServlet?id=${b.id}">书名:${b.name }<br />售价:${b.price } </a>
											</div>
										</td>
									</c:forEach>

									</tr>
								</table>
								
								
















								<div class="pagination">
									<ul>


										<li class="disablepage"><a href="${pageContext.request.contextPath }/servlet/pageServlet?currentPage=${page.currentPage==1?1:page.currentPage-1}">&lt;&lt;上一页</a></li>
										
										
										<li>第${page.currentPage }页/共${page.totalPage }页</li>

										<li class="nextPage"><a href="${pageContext.request.contextPath }/servlet/pageServlet?currentPage=${page.currentPage==page.totalPage?page.totalPage:page.currentPage+1}">&lt;&lt;下一页</a></li>


									</ul>
								</div></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>



	<jsp:include page="foot.jsp" />


</body>
</html>
