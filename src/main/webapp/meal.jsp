<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <h3>Add new Meal</h3>
<body>
<title>Meal</title>
<form method="POST" name="frmAddMeal">
    DateTime : <input
        type="date" name="dateTime"
        value="<fmt:formatDate pattern="MM/dd/yyyy" value="${meal.dateTime}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="number" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />
    <input
            type="submit" value="Save"/>
    <input
            type="submit" value="Cancel"/>
</form>
</body>
</head>
</html>