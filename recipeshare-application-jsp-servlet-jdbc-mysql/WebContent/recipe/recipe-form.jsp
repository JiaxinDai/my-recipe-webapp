<%@ include file="common/header.jspf"%>
<div class="container">
	<form:form method="post" commandName="todo">
		<form:hidden path="id" />
		<fieldset class="form-group">
			<h1 path="desc">Editer</h1>
			<form:input path="desc" type="text" required="required" />
			<form:errors path="desc" cssClass="text-warning" />
		</fieldset>
		<div class="container">
			<div class="row">
				<h3 class="col-3 mx-1">Quantite</h3>
				<h3 class="col-3 mx-1">Unite</h3>
				<h3 class="col-3 mx-1">Ingredient</h3>
			</div>
			<c:forEach items="${ingredients}" var="ingredient">
				<div class="row">
					<input type="text" class="col-3 mx-1"
						required="required" value="${ingredient.name}" />
					<select id="measure" name="measure">
					   <option value="grams">g</option>
					   <option value="litters">L</option>
					   <option value="millilitters">ml</option>
					</select>
					<form:select path="selectedIngredient">
					   <form:options items="${ingredientList}" itemLabel="name" itemValue="name" />
					</form:select> 
					<button>+</button>
				</div>
			</c:forEach>
			
			<hr/>
			
			<c:forEach items="${steps}" var="step">
				<div class="d-flex flex-row">
					<div class="jumbotron flex-row d-flex" style="flex: 1;">
						<div class="col-8">
							<h3>Etape ${step}</h3>
							<form:textarea path="desc" type="text" class="mx-1 col-12"
								required="required" >${step}</form:textarea>
						</div>
						<div class="col-4 d-flex align-items-start justify-content-end flex-column">
							<button class="btn btn-primary mb-1">Up</button>
							<button class="btn btn-primary">Down</button>
						</div>
					</div>
					<button class="btn btn-success" style="height: 2.5em;">+</button>
				</div>
			</c:forEach>
		</div>

		<button type="submit" class="btn btn-success">Add</button>
	</form:form>
</div>
<%@ include file="common/footer.jspf"%>
