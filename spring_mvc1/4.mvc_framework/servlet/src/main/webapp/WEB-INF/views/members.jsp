<%--
  Created by IntelliJ IDEA.
  User: atlas
  Date: 2021/07/08
  Time: 11:39 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
<%--    <%--%>
<%--        for (Member member : members) {--%>
<%--            out.write(" <tr>");--%>
<%--            out.write(" <td>" + member.getId() + "</td>");--%>
<%--            out.write(" <td>" + member.getUsername() + "</td>");--%>
<%--            out.write(" <td>" + member.getAge() + "</td>");--%>
<%--            out.write(" </tr>");--%>
<%--        }--%>
<%--    %>--%>
    <c:forEach var="item" items="${members}">
        <tr>
            <td>${item.id}</td>
            <td>${item.username}</td>
            <td>${item.age}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
