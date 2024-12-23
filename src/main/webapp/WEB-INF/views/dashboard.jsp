<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.*" %>
<%@ page import="com.pizza.model.*" %>
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

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }

        button {
            padding: 5px 10px;
        }
    </style>
    <meta charset="UTF-8">
    <title>Dashboard</title>
</head>

<body>
    <h2 class="ttl">PIZZERIA</h2>

    <!-- Form per il salvataggio della pizza -->
    <form action="dashboard" method="post">
        <div class="form">
            <div>
                <h3 class="ttl">Seleziona Impasto:</h3>
                <table>
                    <tr>
                        <th>Selezione</th>
                        <th>Nome Impasto</th>
                    </tr>
                    <c:forEach var="impasto" items="${impasti}">
                        <tr>
                            <td><input type="radio" name="impasto" value="${impasto.id}" /></td>
                            <td>${impasto.nome}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty impasti}">
                        <tr>
                            <td colspan="2">Nessun impasto trovato.</td>
                        </tr>
                    </c:if>
                </table>
            </div>

            <div>
                <h3>Seleziona Ingredienti:</h3>
                <table>
                    <tr>
                        <th>Selezione</th>
                        <th>Nome Ingrediente</th>
                    </tr>
                    <c:forEach var="ingrediente" items="${ingredienti}">
                        <tr>
                            <td><input type="checkbox" name="ingredienti" value="${ingrediente.id}" /></td>
                            <td>${ingrediente.nome}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty ingredienti}">
                        <tr>
                            <td colspan="2">Nessun ingrediente trovato.</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>

        <h3>Nome Pizza:</h3>
        <input type="text" name="pizzaName" /><br> 
        <input type="submit" value="Salva Pizza" />
    </form>

    <!-- Messaggio di avviso per ingredienti o impasto mancanti -->
    <c:if test="${not empty avviso}">
        <p style="color: red;">${avviso}</p>
    </c:if>

    <h3>Pizze Selezionate:</h3>
    <table>
        <tr>
            <th>Nome Pizza</th>
            <th>Impasto</th>
            <th>Ingredienti</th>
            <th></th>
            <th>Modifica</th>
        </tr>
        <c:forEach var="pizza" items="${pizze}">
            <tr>
                <td>${pizza.nome}</td>
                <td>${pizza.impasto.nome}</td>
                <td>
                    <c:forEach var="ingrediente" items="${pizza.ingredienti}">
                        ${ingrediente.nome} 
                    </c:forEach>
                </td>
                <td>
                    <form action="dashboard" method="post">
                        <input type="hidden" name="action" value="delete"> 
                        <input type="hidden" name="pizzaId" value="${pizza.id}">
                        <button type="submit">Cancella</button>
                    </form>
                </td>
                <td>
                    <details>
                        <summary style="cursor: pointer; font-weight: bold;">
                            Aggiorna Pizza: ${pizza.nome}</summary>
                        <form action="dashboard" method="post" style="padding: 15px;">
                            <input type="hidden" name="action" value="update"> 
                            <input type="hidden" name="pizzaId" value="${pizza.id}">
                            <div style="margin-bottom: 10px;">
                                <label for="pizzaName">Nome Pizza:</label> 
                                <input type="text" name="pizzaName" value="${pizza.nome}" style="width: 100%; padding: 8px;">
                            </div>

                            <div style="margin-bottom: 10px;">
                                <label for="impasto">Impasto:</label> 
                                <select name="impasto" style="width: 100%; padding: 8px;">
                                    <c:forEach var="impasto" items="${impasti}">
                                        <option value="${impasto.id}" 
                                            ${pizza.impasto.id == impasto.id ? 'selected' : ''}>
                                            ${impasto.nome}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div style="margin-bottom: 10px;">
                                <label for="ingredienti">Ingredienti:</label><br>
                                <div style="display: flex; flex-wrap: wrap;">
                                    <c:forEach var="ingrediente" items="${ingredienti}">
                                        <label style="margin-right: 15px;"> 
                                            <input type="checkbox" name="ingredienti" 
                                                   value="${ingrediente.id}" 
                                                   ${pizza.ingredienti.contains(ingrediente) ? 'checked' : ''}>
                                            ${ingrediente.nome}
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>

                            <button type="submit" style="padding: 8px 12px;">Aggiorna</button>
                        </form>
                    </details>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty pizze}">
            <tr>
                <td colspan="5">Nessuna pizza trovata per questo utente.</td>
            </tr>
        </c:if>
    </table>
</body>
</html>
