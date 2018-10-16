<%-- 
    Document   : usuario.jsp
    Created on : 22/05/2018, 20:32:43
    Author     : caiot
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html lang = "en-us">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel = "stylesheet" href = "css/style.css">
        <script type = "text/javascript" src = "js/jquery-3.2.1.min"></script>
    </head>
    <body>
        <div class= "container">
            <div class = "divLogin row">
                <div class="col-md-4">
                </div>
                <div class= "col-md-4 text-center">
                    <div class= "search-box">
                        <div class= "caption mt-3">
                            <h1>${msgs["welcome"]}</h1>
                            <p>${msgs["pls"]} ${msgs["identify"]}</p>
                        </div>
                        
                            <div class= "input-group">
                                <input type= "email" id="email" name = "email" class= "form-control mb-4" placeholder= "Email" maxlength = "50">
                            </div>
                            
                            <c:choose>
                                <c:when test="${not empty emailvazio}">
                                    <p>${msgs["emailempty"]}</p>
                                </c:when>
                            </c:choose>
                                
                    </div>
                    <div class= "search-box">
                        <div class= "input-group">
                            <input type= "text" id= "user" name = "user" class= "form-control mb-4" placeholder= ${msgs["username"]} maxlength = "30">
                        </div>
                        
                        <c:choose>
                            <c:when test="${not empty uservazio}">
                                <p>${msgs["userempty"]}</p>
                            </c:when>
                        </c:choose>
                      
                    </div>
                    <div class="search-box">
                        <div class="input-group">
                            <input type="password" id="senha" name = "senha" class="form-control mb-4" placeholder=${msgs["password"]} maxlength = "16">
                        </div>
                        
                        <c:choose>
                            <c:when test="${not empty senhavazia}">
                                <p>${msgs["passempty"]}</p>
                            </c:when>
                        </c:choose>
                                
                        <c:choose>
                            <c:when test="${not empty senhasdiferentes}">
                                <p>${msgs["passntmatch"]}</p>
                            </c:when>
                        </c:choose>
                                
                        <c:choose>
                            <c:when test="${not empty cadastroduplicado}">
                                <p>${msgs["caddupl"]}</p>
                            </c:when>
                        </c:choose>
                                
                        <c:choose>
                            <c:when test="${not empty errocadastro}">
                                <p>${msgs["uerror"]}</p>
                            </c:when>
                        </c:choose>    
                        
                    </div>
                    <div class="search-box">
                        <div class="input-group">
                            <input type="password" id="senha_conf" name = "senha_conf" class="form-control mb-4" placeholder = "${msgs["confirmpass"]}" maxlength = "16">
                        </div>
                    </div>
                    <div class="search-box">
                        <div class="input-group">
                            <input type="button" id="register" class="btn btn-primary" value="${msgs["reg"]}" onclick="addUser()">
                        </div>
                        
                        <a href="Login">${msgs["goback"]}</a>
                    </div>
                </div>
            </div>
        </div>
                    
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script src="js/usuarios.js" type="text/javascript"></script>
    </body>
</html>
