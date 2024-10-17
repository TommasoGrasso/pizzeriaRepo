<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<html>
<head>
<style type="text/css">
.ttl {
	text-align: center;
}

.form {
	display: flex;
	gap: 70px;
}
</style>
<meta charset="UTF-8">
<title>Dashboard</title>
</head>

<body>
	<h2 class="ttl">PIZZERIA</h2>

	<form action="dashboard" method="post">
		<div class="form">
			<div>
				<h3 class="ttl">Seleziona Impasto:</h3>
				<table border="1">
					<tr>
						<th>Selezione</th>
						<th>Nome Impasto</th>
					</tr>
					<%
					List<Impasto> impasti = (List<Impasto>) request.getAttribute("impasti");
					if (impasti != null && !impasti.isEmpty()) {
						for (Impasto impasto : impasti) {
					%>
					<tr>
						<td><input type="radio" name="impasto"
							value="<%=impasto.getId()%>" /></td>
						<td><%=impasto.getNome()%></td>
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="2">Nessun impasto trovato.</td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
			<div>
				<h3>Seleziona Ingredienti:</h3>
				<table border="1">
					<tr>
						<th>Selezione</th>
						<th>Nome Ingrediente</th>
					</tr>
					<%
					List<Ingrediente> ingredienti = (List<Ingrediente>) request.getAttribute("ingredienti");
					if (ingredienti != null && !ingredienti.isEmpty()) {
						for (Ingrediente ingrediente : ingredienti) {
					%>
					<tr>
						<td><input type="checkbox" name="ingredienti"
							value="<%=ingrediente.getId()%>" /></td>
						<td><%=ingrediente.getNome()%></td>
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="2">Nessun ingrediente trovato.</td>
					</tr>
					<%
					}
					%>
				</table>
			</div>


		</div>

		<h3>Nome Pizza:</h3>
		<input type="text" name="pizzaName" /><br> <input type="submit"
			value="savePizza" />

	</form>
	<h3>Pizze Selezionate:</h3>
	<table border="1">
		<tr>
			<th>Nome Pizza</th>
			<th>Impasto</th>
			<th>Ingredienti</th>
			<th></th>
			<th>Modifica</th>
		</tr>
		<%
		List<Pizza> pizzeUtente = (List<Pizza>) request.getAttribute("pizzeUtente");
		if (pizzeUtente != null && !pizzeUtente.isEmpty()) {
			for (Pizza pizza : pizzeUtente) {
		%>
		<tr>
			<td><%=pizza.getNome()%></td>
			<td><%=pizza.getImpasto().getNome()%></td>
			<td>
				<%
				List<Ingrediente> ingredientiPizza = pizza.getIngredienti();
				for (Ingrediente ingrediente : ingredientiPizza) {
					out.print(ingrediente.getNome() + " ");
				}
				%>
			</td>
			<td>
				<form action="dashboard" method="post">
					<input type="hidden" name="action" value="delete"> <input
						type="hidden" name="pizzaId" value="<%=pizza.getId()%>">
					<button type="submit">Cancella</button>
				</form>
			</td>
			<td>
				<details>
					<summary style="cursor: pointer; font-weight: bold;">
						Aggiorna Pizza:
						<%=pizza.getNome()%></summary>
					<form action="dashboard" method="post" style="padding: 15px;">
						<input type="hidden" name="action" value="update"> <input
							type="hidden" name="pizzaId" value="<%=pizza.getId()%>">

						<div style="margin-bottom: 10px;">
							<label for="pizzaName">Nome Pizza:</label> <input type="text"
								name="pizzaName" value="<%=pizza.getNome()%>"
								style="width: 100%; padding: 8px;">
						</div>

						<div style="margin-bottom: 10px;">
							<label for="impasto">Impasto:</label> <select name="impasto"
								style="width: 100%; padding: 8px;">
								<%
                    for (Impasto impasto : impasti) {
                    %>
								<option value="<%=impasto.getId()%>"
									<%=pizza.getImpasto().getId() == impasto.getId() ? "selected" : ""%>>
									<%=impasto.getNome()%>
								</option>
								<%
                    }
                    %>
							</select>
						</div>

						<div style="margin-bottom: 10px;">
							<label for="ingredienti">Ingredienti:</label><br>
							<div style="display: flex; flex-wrap: wrap;">
								<%
                    for (Ingrediente ingrediente : ingredienti) {
                    %>
								<label style="margin-right: 15px;"> <input
									type="checkbox" name="ingredienti"
									value="<%=ingrediente.getId()%>"
									<%=pizza.getIngredienti().contains(ingrediente) ? "checked" : ""%>>
									<%=ingrediente.getNome()%>
								</label>
								<%
                    }
                    %>
							</div>
						</div>

						<button type="submit" style="padding: 8px 12px;">Aggiorna</button>
					</form>
				</details>
			</td>

		</tr>
		<%
		}
		} else {
		%>
		<tr>
			<td colspan="5">Nessuna pizza trovata per questo utente.</td>
		</tr>
		<%
		}
		%>
	</table>



</body>
</html>
