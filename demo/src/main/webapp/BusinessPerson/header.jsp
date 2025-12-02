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
        <a href="<%= request.getContextPath()%>/BusinessPerson/index_seller.jsp" class="tab">Home</a>
        <a href="<%= request.getContextPath()%>/sales" class="tab">Order history</a>
        <a href="<%= request.getContextPath()%>/MyProducts" class="tab">My products</a>
    </div>
    <div class ="global-actions inline align-right">

        <button onclick="window.location.href='<%= request.getContextPath()%>'">Seller</button>

        <LanguageSelect/>
    </div>
</nav>
