
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gen" %>
<%@ include file="/WEB-INF/pages/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/signin.css">
    <link href="${contextPath}/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div class="container">

    <form class="form-signin" role="form" action="<c:url value='/j_spring_security_check' />" method='POST'>
        <h2 class="form-signin-heading">Please sign in</h2>

        <input type="text" id="name" name="j_username" class="form-control" placeholder="Nickname" required autofocus>
        <input type="password" name="j_password" class="form-control" placeholder="Password" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		<div>
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<font color="red">
					Wrong nickname or password.
				</font>
			</c:if>
		</div>
		<a href="<c:url value="/api/user/signup"/>">Sign up</a>
    </form>

</div> <!-- /container -->


</body>
</html>

