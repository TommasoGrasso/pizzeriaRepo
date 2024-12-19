<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h2>Login</h2>

<form action="Login" method="post">
  <label for="username">Username:</label>
  <input type="text" id="username" name="username" required>
  <br>
  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required>
  <br>
  <input type="submit" value="Login">
</form>

<%-- Visualizzazione del messaggio di errore --%>
<% 
    String errorMessage = (String) request.getAttribute("error");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
        <p style="color: red;"><%= errorMessage %></p>
<% 
    }
%>

</body>
</html>
