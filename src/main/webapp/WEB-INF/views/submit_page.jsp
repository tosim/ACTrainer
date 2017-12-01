<%--
  Created by IntelliJ IDEA.
  User: yyc
  Date: 2017/11/20
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<FORM action="/submissions" method="post">
    <input name="remoteProblemId"/><br>
    <input name="remoteOj"/><br>
    <input name="language" value="0"/><br>
    <textarea name="source"></textarea>><br>
    <input type="submit" value="提交"/><br>
</FORM>
</body>
</html>
