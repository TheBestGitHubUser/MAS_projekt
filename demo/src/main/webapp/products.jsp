<%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 07.06.2025
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.demo.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>
<html>
<%@include file="Components/head.jsp"%>
<body>
    <%@ include file="Components/header.jsp"%>

    <div class="container">
        <h2>Lista produktów</h2>

        <br>

        <table border='1'>
            <tr><th>Name</th><th>Description</th><th>Price</th><th>Stock</th><th>Category</th><th>Picture</th></tr>
            <% for (Product  product : products) { %>
                <tr><th><a href="<%= request.getContextPath()%>/details?ID=<%=product.getId()%>"><%=product.getName()%></a> </th><th><%=product.getDescription()%></th><th><%= product.getPrice()%></th>
                    <th><%=product.getStock()%></th><th><%=product.getCategory()%></th><th><img src="<%=product.getImageURL()%>" class="img-table"> </th></tr>

            <% } %>


        </table>

    </div>

</body>
</html>
