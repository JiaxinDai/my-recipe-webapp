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
				<a class="navbar-brand" style="color: white"> Recipe sharing App</a>
			</div>

			<ul class="navbar-nav">
				<li><a href="list?currentUser=<c:out value='${currentUser}'/>"
					class="nav-link">Recipes</a></li>
			</ul>

			<ul class="navbar-nav navbar-collapse justify-content-end">
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">Logout</a></li>
			</ul>
		</nav>
	</header>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${recipe != null}">
					<form action="update?currentUser=<c:out value='${currentUser}'/>"
						enctype="multipart/form-data" method="post">
				</c:if>
				<c:if test="${recipe == null}">
					<form action="insert?currentUser=<c:out value='${currentUser}'/>"
						enctype="multipart/form-data" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${recipe != null}">
            			Edit recipe
            		</c:if>
						<c:if test="${recipe == null}">
            			Add New recipe
            		</c:if>
					</h2>
				</caption>

				<c:if test="${recipe != null}">
					<input type="hidden" name="id"
						value="<c:out value='${recipe.id}' />" />
				</c:if>

				<c:if test="${recipe != null}">
					<input type="hidden" name="filename"
						value="<c:out value='${recipe.filename}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Recipe Name</label> <input type="text"
						value="<c:out value='${recipe.title}' />" class="form-control"
						name="title" required="true">
				</fieldset>

				<fieldset class="form-group">
					<label>Recipe Description</label>
					<textarea class="form-control" name="description" type="text"
						value="<c:out value='${recipe.description}'/>" minlength="10">${recipe.description}</textarea>
				</fieldset>

				<fieldset class="form-group">
					<label>Upload Your Food Photo</label> <input type="file"
						name="file2" /><br>
				</fieldset>

				<button class="btn btn-success" type="submit" value="upload">Save</button>
				</form>
				<c:if test="${recipe != null}">
					<a
						href="delete?id=<c:out value='${recipe.id}'/>&currentUser=<c:out value='${currentUser}'/>"
						class="btn btn-danger">Delete Recipe</a>
				</c:if>
			</div>
		</div>
	</div>

	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>