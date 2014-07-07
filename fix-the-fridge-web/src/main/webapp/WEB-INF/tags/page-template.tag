<%@ include file="/WEB-INF/pages/taglibs.jsp" %>

<%@tag description="Page template" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="leftPanel" fragment="true" %>
<%@attribute name="rightPanel" fragment="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
    <link href="${contextPath}/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="${contextPath}/lib/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${contextPath}/lib/jquery/jquery.form.js" type="text/javascript"></script>
    <script src="${contextPath}/lib/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <jsp:invoke fragment="header"/>
</head>
<body style="background-color: #F5F5DC;">
<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-brand">
            <img src='${contextPath}/png/logo.png' class='img-circle' width='45px' height='45px' style="margin-top: -10px"/>
            Fix The Fridge
        </div>
    </div>
</div>
<div class="container container-with-margin-top">
    <div class="row">
        <div class="sidebar col-xs-10 col-md-2" >
            <div class="well" style="position: fixed">
                <c:if test="${avatar == ''}">
                    <img src="${contextPath}/png/default_ava.png" alt="text" class="img-circle user-avatar-img" width="100px" height="100px">
                </c:if>
                <c:if test="${avatar != ''}">
                    <img src="${avatar}" alt="text" class="img-circle user-avatar-img" width="100px" height="100px">
                </c:if>
                <br>
                <h4>
                <p style="width: 100%; text-align: center; font-size: 14pt" class="save-image">
                    ${nickName}
                </p>
                </h4>

                <p style="width: 100%; text-align: center; font-size: 10pt">
                    <a href="<c:url value="/j_spring_security_logout" />" >Logout</a>
                </p>

                <input type="button" class="btn btn-default" value="My ideas" style="width: 100%; background-color: #0088cc; color: #fff" onclick="showMyIdeas()"/>
                <br>
                <input type="button" class="btn btn-default" value="Hottest" style="width: 100%; background-color: #0088cc; color: #fff" onclick="showHottest()"/>
                <br>
                <input type="button" class="btn btn-default" value="Latest" style="width: 100%; background-color: #0088cc; color: #fff" onclick="showLatest()"/>
                <br>
            </div>
            <jsp:invoke fragment="leftPanel"/>
        </div>
        <div class="col-xs-12 col-md-10 main">
            <jsp:invoke fragment="rightPanel"/>
        </div>
    </div>
</div>

<div id="footer" class="container">
    <nav class="navbar-inverse navbar-default navbar-fixed-bottom">
        <div class="navbar-inner navbar-content-center">
            <p class="text-muted credit text-center">
                Powered by &copy;FixTheFridge

            </p>
        </div>
    </nav>
</div>
</body>
</html>
