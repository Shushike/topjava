<%--
  Created by IntelliJ IDEA.
  User: helen
  Date: 04.10.2020
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<form method="post">
    <table style="padding: 4px;">
        <tr>
            <td><label>Date time: </label></td>
            <td><input type="datetime-local" name="datetime" value="${meal.dateTime}" placeholder="${meal.dateTime}"/>
            </td>
        </tr>
        <tr>
            <td><label>Description: </label></td>
            <td><input type="text" name="description" value="${meal.description}" placeholder="${meal.description}"/>
            </td>
        </tr>
        <tr>
            <td><label>Calories: </label></td>
            <td><input type="number" name="calories" value="${meal.calories}" placeholder="${meal.calories}"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="margin: 4px;" type="submit" formaction="meals?action=submit&id=${meal.id}">Save</button>
                <button type="submit" formaction="meals">Cancel</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
