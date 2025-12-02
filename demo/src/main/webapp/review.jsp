<%@ page import="com.example.demo.model.Review" %><%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 07.06.2025
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Review review = (Review) request.getAttribute("review");
%>
<html>
<%@include file="Components/head.jsp"%>
<body>
<%@include file="Components/header.jsp"%>
<div class="container">
    <form action="" method="post">
        <input type="hidden" name="reviewID" value="<%=review.getId()%>">
        <input type="hidden" name="OrderID" value="<%=review.getOrderId()%>">
        <input type="text" name="Comment" value="<%=review.getComment()%>">
        <button type="submit">save</button>
    </form>
</div>
</body>
</html>
