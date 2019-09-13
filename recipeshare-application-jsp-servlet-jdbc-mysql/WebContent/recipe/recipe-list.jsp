<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>User Management Application</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>

</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a class="navbar-brand" style="color: white"> Recipe-sharing App</a>
			</div>

			<ul class="navbar-nav navbar-collapse justify-content-end">
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">Logout</a></li>
			</ul>
		</nav>
	</header>

	<div class="row">
		<div class="container">
			<h3 class="text-center">Recipe Collections</h3>
			<hr>

			<div class="container text-left">
				<a href="new?currentUser=<c:out value='${currentUser}'/>"
					class="btn btn-success">Upload A Recipe</a>
			</div>
			<br>

			<div class="row">
				<c:forEach var="recipe" items="${listRecipe}">
					<div class="img-with-text">
						<img class="recipe-img"
							src="<%=request.getContextPath()%>/uploadFile/${recipe.filename}"
							alt="Image Not Found" /> <a class="recipe-name-text"
							href="edit?id=<c:out value='${recipe.id}'/>&currentUser=<c:out value='${currentUser}'/>">${recipe.title}
							>></a>
						<p class="prep-text">
							by <span class="username-text">${recipe.owner}</span>
						</p>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>

<style>
.img-with-text {
	background-color: #ffded5;
	text-align: justify;
	width: 25vw;
	height: 32vw;
	margin: 1vw;
	border: 2px solid #aaa;
}

img.recipe-img {
	width: calc(25vw - 4px);
	height: calc(25vw - 4px);
	padding-top: 2px;
	padding-left: 2px;
	padding-right: 2px;
	padding-left: 2px;
}

a.recipe-name-text {
	color: #ff6347;
	font-family: georgia, garamond, serif;
	font-size: 1.7vw;
	font-style: italic;
	margin-top: 2%;
	margin-left: 2%;
	margin-bottom: 2%;
}

p.prep-text {
	font-style: italic;
	font-size: 1.7vw;
	margin-left: 2%;
}

span.username-text {
	color: #ff6347;
	font-weight: bold;
	font-size: 1.7vw;
}
</style>