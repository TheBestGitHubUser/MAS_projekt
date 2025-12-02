<%@ page import="com.example.demo.model.Product" %><%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 08.06.2025
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Product product = (Product) request.getAttribute("product");
    boolean isEdit = (product != null);
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>climb BRUH</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<%@include file="header.jsp"%>
    <div class="container">
        <h2><%= isEdit ? "Edit" : "Add" %> Product</h2>
        <br>
        <form action="" method="post">
            <% if (isEdit) { %>
            <input type="hidden" name="ID" value="<%= product.getId() %>"/>
            <% } %>

            <label>Name:</label><br/>
            <input type="text" name="Name" value="<%= isEdit ? product.getName() : "" %>" required/><br/><br/>

            <label>Description:</label><br/>
            <textarea name="Description" rows="4" cols="50" required><%= isEdit ? product.getDescription() : "" %></textarea><br/><br/>

            <label>Price:</label><br/>
            <input type="number" step="0.01" name="Price" value="<%= isEdit ? product.getPrice() : "" %>" required/><br/><br/>

            <label>Stock:</label><br/>
            <input type="number" name="Stock" value="<%= isEdit ? product.getStock() : "" %>" required/><br/><br/>

            <label>Category:</label><br/>
            <input type="text" name="Category" value="<%= isEdit ? product.getCategory() : "" %>" required/><br/><br/>

            <label>Image URL:</label><br/>
            <input type="text" name="ImageURL" value="<%= isEdit ? product.getImageURL() : "" %>" required/><br/><br/>

            <button type="submit"><%= isEdit ? "Update" : "Add" %> Product</button>
        </form>

    </div>

</body>
</html>
