<%--
  Created by IntelliJ IDEA.
  User: 木叶小寒江
  Date: 2020/6/3
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>参数绑定</title>
</head>
<body>
    <h4>字符串和基本类型的参数绑定</h4>
    <a href="param/string?param1=yejiang&param2=1">string and integer</a>
    <br>
    <a href="/param/string?param1=yejiang&param2=1">/string and integer</a>
    <br>
    <a href="/owlmvc/param/stringAndInteger?param1=yejiang&param2=1">/string and integer</a>
    <br>
    <h4>对象的参数绑定</h4>
    <h7>GET请求：对象的参数绑定</h7>
    <a href="/owlmvc/param/getObject?id=124&name=MuyerJ">GET:object</a>
    <br>
    <h7>POST请求：对象的参数绑定</h7>
    <form action="/owlmvc/param/postObject" method="post">
        id:<input name="id" type="text"/><br>
        name:<input name="name" type="text"/><br>
        <input type="submit"/>
    </form>
    <h4>集合参数绑定</h4>
    <form action="/owlmvc/param/postCollection" method="post">
        id:<input name="id" type="text"/><br>
        name:<input name="name" type="text"/><br>
        subject:<input name="subject[0]" type="text" /><br>
        subject:<input name="subject[1]" type="text" /><br>
        Math score:<input name="score[math]" type="text" /><br>
        chinese score:<input name="score[chinese]" type="text" /><br>
        <input type="submit"/>
    </form>
    <br>
</body>
</html>
