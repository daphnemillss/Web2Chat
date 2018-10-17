<%-- 
    Document   : chatprincipal
    Created on : 22/05/2018, 21:00:01
    Author     : caiot
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
    <head>
        <meta charset ="UTF-8">
        <meta name= "viewport" content="width=device-width, initial-scale=1.0">
        <title>Chat - ${user} </title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-3 mt-5">
                    <h3>${msgs["hello1"]} <span class="negrito">${user}</span>!</h3> 
                    <a href="Logout">${msgs["logout"]}</a>
                        
                    
                    <p class="mt-3">${msgs["contact"]}</p>
                    <div class="list-group contatos">
                        
                        <c:forEach items="${contatos}" var="element">
                            <a href="ChatPrincipal?from=${user}&to=<c:out value="${element}"/>" class="list-group-item list-group-item-action"><c:out value="${element}"/></a>
                        </c:forEach>
                       
                    </div>
                    
                    <input type="button" class="btn btn-primary mt-4" value="Apagar Minha Conta" onclick="deleteUser('${user}')">
                </div>
                <div class="col-sm-8 mt-5">
                    
                    <c:choose>
                        <c:when test="${empty param.to}">
                            <h3>${msgs["selectcont"]}</h3>
                        </c:when>
                        <c:otherwise>
                            <h3>${msgs["chatw"]} <c:out value="${param.to}"/>
                        </c:otherwise>
                    </c:choose>
                    
                       
                    <iframe src = "Mensagem?from=${param.from}&to=${param.to}" height = "425" width = "725"></iframe>
                    
                 
                    <form action = "Mensagem" method = "POST">
                        <textarea class="mt-3 form-control" name="msg" rows="5" placeholder="${msgs["wriw"]}"></textarea>
                        
                </div>
                        
                <div class="col-sm-1">
                    <input type='hidden' name='from' value="${param.from}">
                    <input type='hidden' name='to' value="${param.to}">
                    <input type='submit' class="btn btn-primary botaoenviar" value="${msgs["send"]}">
                    </form>
                </div>
            </div>
        </div>
                    
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script src="js/usuarios.js" type="text/javascript"></script>
    </body>
</html>
