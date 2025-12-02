<%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 07.06.2025
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
    <div class="global-actions inline">
        <a href="<%= request.getContextPath()%>" class="tab">Home</a>
        <a href="<%= request.getContextPath()%>/BusinessPersons" class="tab">Shop</a>
        <a href="<%= request.getContextPath()%>/MyPurchases" class="tab">My purchases</a>
    </div>
    <div class ="global-actions inline align-right">

        <button onclick="window.location.href='BusinessPerson/index_seller.jsp'">Client</button>

        <LanguageSelect/>
    </div>
</nav>
