<%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 07.06.2025
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.demo.model.BusinessPerson" %>
<%
    List<BusinessPerson> businessPersons = (List<BusinessPerson>) request.getAttribute("businessPersons");
%>
<html>
<%@include file="Components/head.jsp"%>
<body>
<%@ include file="Components/header.jsp"%>

<div class="container">
    <h2>Lista sprzedawców</h2>
    
    <%@include file="Components/searchBar.jsp"%>

    <table border='1'>
        <br>
        <tr><th>Name</th><th>Email</th><th>Nip</th><th>rating</th></tr>
        <% for (BusinessPerson  bp : businessPersons) { %>
        <tr><th><a href="<%= request.getContextPath()%>/products?ID=<%=bp.getBusinessID()%>"><%= bp.getName() %></a></th><th><%= bp.getEmail()%></th>
            <th><%=bp.getNIP()%></th><th> <%=bp.getRating()%></th></tr>


        <% } %>


    </table>


</div>

</body>
</html>
