<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        form {
            display: block;
            margin-top: 0em;
            margin-block-end: 1em;
        }

        input {
            margin-left: 15px;
            margin-right: 15px;
            margin-top: 15px;
            max-width: 16.666667%;
        }

        button {
            margin-right: 15px;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <form id="filter">
        <div>
            <label for="startDate">От даты (включая)</label>
            <input name="startDate" type="date" id="startDate" autocomplete="off" value="${param.get("startDate")}">
        </div>
        <div>
            <label for="endDate">До даты (включая)</label>
            <input name="endDate" type="date" id="endDate" autocomplete="off" value="${param.get("endDate")}">
        </div>
        <div>
            <label for="startTime">От времени (включая)</label>
            <input name="startTime" type="time" id="startTime" autocomplete="off" value="${param.get("startTime")}">
        </div>
        <div>
            <label for="endTime">До времени (исключая)</label>
            <input name="endTime" type="time" id="endTime" autocomplete="off" value="${param.get("endTime")}">
        </div>
        <button type="submit">Отфильтровать</button>
    </form>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>