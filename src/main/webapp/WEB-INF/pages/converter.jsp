<%--
  Created by IntelliJ IDEA.
  User: 木叶小寒江
  Date: 2020/6/6
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>转换器测试</title>
</head>
<body>
    <%--日期转换器测试1--%>
    <form action="/owlmvc/converter/date" method="post">
        date:<input name="date" type="text"/>
        <input type="submit" />
    </form>

    <%--日期转换器测试2--%>
    <form action="/owlmvc/converter/dateString" method="post">
        date:<input name="date" type="text"/>
        <input type="submit" />
    </form>

    <br>
    <%--自定义类转换--%>
    <a href="/owlmvc/converter/user?yejiang#110">自定义类转换</a>
</body>
</html>
