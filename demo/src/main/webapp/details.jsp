<%@ page import="com.example.demo.model.Product" %>
<%@ page import="com.example.demo.model.BusinessPerson" %><%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 07.06.2025
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Product product = (Product) request.getAttribute("product");
%>
<html>
<%@include file="Components/head.jsp"%>
<body>
    <%@ include file="Components/header.jsp"%>

    <div class="container">
        <div class="center container two-columns">
            <img src="<%=product.getImageURL()%>" alt="box art" class="large-product-img"/>
            <div id="product-details">
                <h1><%=product.getName()%></h1><br/>
                <strong>Price: </strong><%=product.getPrice()%><br/>
                <strong>Category: </strong><%=product.getCategory()%><br/>
                <strong>Amount available: </strong><%=product.getStock()%><br/><br/>
                <strong>Description: </strong><%=product.getDescription()%><br/><br/>


                <br></br>


                    <form action="details" method="post">
                        <input type="hidden" name="productId" value="<%= product.getId() %>"/>
                        <button type="submit" <% if (product.getStock()<=0){%> disabled <%}%>>
                            <% if (product.getStock()<=0){%> sold out <%}else{%> buy<%}%>
                        </button>
                    </form>

            </div>
        </div>
    </div>

</body>
</html>
