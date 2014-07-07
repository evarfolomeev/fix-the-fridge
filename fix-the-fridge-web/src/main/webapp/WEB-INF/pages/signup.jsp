
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

	<form class="form-signin" role="form" action="/fix-the-fridge-web/api/user/do-sign-up" method="POST">
		<h2 class="form-signin-heading">Please sign up</h2>
		<input type="text" id="name" class="form-control" name="nickname" placeholder="Nickname" required autofocus>
		<input type="email" class="form-control" name="email" placeholder="Email address" required autofocus>
		<input type="password" class="form-control" name="password" placeholder="Password" required>
		<input type="password" id="confirm" class="form-control" placeholder="Confirm Password" required>

		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
	</form>

</div>

</body>
</html>

