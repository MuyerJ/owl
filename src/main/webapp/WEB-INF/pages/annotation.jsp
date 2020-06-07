<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: 木叶小寒江
  Date: 2020/6/6
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>annotation</title>
</head>
<body>


    <h4>springmvc注解测试</h4>

    <h6>requestParam</h6>
    <form action="/owlmvc/annotation/requestParam" method="post">
        param1:<input name="param1" type="text" /><br>
        param2:<input name="param2" type="text" /><br>
        param3:<input name="param3" type="text" /><br>
        <input type="submit" /><br>
    </form>

    <h6>requestBody</h6>
    <button id="requestBody">测试requestBody</button>
    <script src='<c:url value="/static/js/jquery-1.4.4.min.js"/>'></script>
    <script>
        $(function () {
            $("#requestBody").click(function () {
                console.log("test requestBody");
                $.ajax({
                    url:"/owlmvc/annotation/requestbody",
                    type:"post",
                    contentType:"application/json;charset=UTF-8",
                    dataType:"json",
                    data:JSON.stringify({
                       id:"111",
                       name:"yejiang"
                    }),
                    success:function (data) {
                        console("success:"+data);
                    }
                })
            })

        })
    </script>
</body>
</html>
