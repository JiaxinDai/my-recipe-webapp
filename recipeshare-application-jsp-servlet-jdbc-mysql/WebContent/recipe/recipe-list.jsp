<%@ include file="common/header.jspf" %>
	
	<div class="container">
		<table class="table table-striped">
			<caption>Recipes</caption>
			<thead>
				<tr>
					<th>Title</th>
					<th>User</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${recipes}" var="recipe">
					<tr>
						<td><a href="/view-recipe?id=${recipe.id}">${recipe.title}</a></td>
						<td>${recipe.user}</td>
						<td>
							<a href="/update-recipe?id=${recipe.id}">
								<button class="btn btn-success">
									Update
								</button>
							</a>
						</td>
						<td>
							<a href="/delete-recipe?id=${recipe.id}">
								<button class="btn btn-warning">
									Delete
								</button>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<a class="button" href="/recipe-edit">Add a recipe</a>
		</div>
	</div>
<%@ include file="common/footer.jspf" %>
