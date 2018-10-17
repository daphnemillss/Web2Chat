<%-- 
    Document   : mensagens
    Created on : 01/06/2018, 15:32:14
    Author     : caiot
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">

        <title>${msgs["hello2"]}</title>
    </head>
    <body>

        <div id="messages">
            <c:forEach var="msg" items="${listajson}">
                <p>[<c:out value="${msg.date}"/>] <c:out value="${msg.from}"/> diz: <c:out value="${msg.message}"/></p>
            </c:forEach>
        </div>

        <%-- USANDO JSTL --%>

        <%--c:forEach items="${lista}" var="msgobject">
            <p>[${msgobject.date}] ${msgobject.from} ${msgs["said"]} ${msgobject.message}</p>
        </c:forEach--%>
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script src="js/mensagens.js" type="text/javascript"></script>
    </body>

</html>
