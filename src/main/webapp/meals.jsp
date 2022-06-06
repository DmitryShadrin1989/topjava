<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .normal {color: green;}
        .exceeded {color: red;}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <tbody>
    <p><a href="meals?action=insert">Add Meal</a></p>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="mealTo" items="${mealsTo}">
        <tr class="${mealTo.excess ? 'exceeded' : 'normal'}">
            <td align="center">${mealTo.dateTime}</td>
            <td align="left">${mealTo.description}</td>
            <td align="left">${mealTo.calories}</td>
            <td><a href="?action=edit&mealTo=<c:out value=""/>">Update</a></td>
            <td><a href="delete">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>