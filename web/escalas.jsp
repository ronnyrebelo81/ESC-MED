
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="br.com.escala.modelo.Escala"%>
<%
    List<Escala> escalas =
            (List<Escala>) request.getAttribute("escalas");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Escalas - ESC-MED</title>
    <style>
        * { box-sizing: border-box; }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #F4F8F5;
            color: #202820;
        }

        header {
            background: #00995D;
            color: white;
            padding: 22px 5%;
        }

        main {
            max-width: 900px;
            margin: 40px auto;
            padding: 25px;
        }

        .card {
            background: white;
            padding: 25px;
            margin-bottom: 25px;
            border-radius: 10px;
            box-shadow: 0 3px 12px #00000012;
        }

        h2, h3 { color: #006B42; }

        label {
            display: block;
            margin: 15px 0 7px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccd8cf;
            border-radius: 5px;
        }

        button {
            margin-top: 20px;
            padding: 12px 22px;
            border: none;
            border-radius: 5px;
            background: #00995D;
            color: white;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 13px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th { color: #006B42; }

        a { color: #006B42; }
    </style>
</head>
<body>

<header>
    <h1>ESC-MED - Gerenciamento de Escalas</h1>
</header>

<main>

    <section class="card">
        <h2>Cadastrar Nova Escala</h2>

        <form action="EscalaServlet" method="POST">

            <label for="nome">Nome da escala</label>
            <input type="text" id="nome" name="nome" required>

            <label for="unidade">Unidade</label>
            <input type="text" id="unidade" name="unidade" required>

            <button type="submit">Cadastrar Escala</button>
        </form>
    </section>

    <section class="card">
        <h2>Escalas Cadastradas</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Unidade</th>
                    <th>Status</th>
                </tr>
            </thead>

            <tbody>
                <% if (escalas != null && !escalas.isEmpty()) {
                    for (Escala escala : escalas) { %>
                    <tr>
                        <td><%= escala.getId() %></td>
                        <td><%= escala.getNome() %></td>
                        <td><%= escala.getUnidade() %></td>
                        <td><%= escala.isAtiva() ? "Ativa" : "Inativa" %></td>
                    </tr>
                <%  }
                   } else { %>
                    <tr>
                        <td colspan="4">Nenhuma escala cadastrada.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>

    </section>

    <p><a href="admin.html">← Voltar ao painel administrativo</a></p>

</main>

</body>
</html>
