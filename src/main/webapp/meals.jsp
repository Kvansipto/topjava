<%@ page import="ru.javawebinar.topjava.MealTestData" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Users</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="<%=MealTestData.getMealWithExcessList()%>">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:if test="${meal.excess == true}">
            <tr class="excess">
        </c:if>
        <c:if test="${meal.excess == false}">
            <tr class="within">
        </c:if>
        <td><%=TimeUtil.getDateTimeFormatted(meal.getDateTime())%>
        </td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>