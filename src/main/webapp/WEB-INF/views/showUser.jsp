<%--
  Created by IntelliJ IDEA.
  User: yyc
  Date: 2017/10/26
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示用户，测试用</title>
</head>
<body>
${user.userName}
<FORM action="/files/pic" method="post" enctype="multipart/form-data">
    <%--<input name="fileName"/><br>--%>
    <input name="userIcon" type="file"/>
    <%--<input name="userIcon" type="file"/>--%>
    <input type="submit" value="提交"/><br>
</FORM>
<img src="${sessionScope.iconPath}">
</body>
</html>
