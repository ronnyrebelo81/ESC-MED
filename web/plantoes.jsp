
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.com.escala.modelo.Plantao"%>
<%@page import="br.com.escala.modelo.Escala"%>
<%@page import="br.com.escala.modelo.Medico"%>

<%
    List<Escala> escalas = (List<Escala>) request.getAttribute("escalas");
    List<Medico> medicos = (List<Medico>) request.getAttribute("medicos");
    List<Plantao> plantoes = (List<Plantao>) request.getAttribute("plantoes");

    SimpleDateFormat formatoData =
            new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Plantões - ESC-MED</title>

    <style>
        * { box-sizing: border-box; }

        body {
            margin: 0;
            padding: 24px 30px;
            font-family: Arial, sans-serif;
            background: #F4F8F5;
            color: #202820;
            font-size: 14px;
        }

        .container {
            width: 100%;
            max-width: 1450px;
            margin: auto;
        }

        h1 { color: #006B42; }

        .painel {
            background: white;
            padding: 24px;
            border-radius: 12px;
            margin-bottom: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
            overflow-x: auto;
        }

        label {
            display: block;
            margin: 14px 0 6px;
            font-weight: bold;
        }

        input, select {
            width: 100%;
            padding: 11px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .botao {
            margin-top: 20px;
            padding: 11px 20px;
            background: #00995D;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
        }

        .botao:hover { background: #006B42; }

        table {
            width: 100%;
            min-width: 900px;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 11px 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
            white-space: nowrap;
        }

        th {
            background: #DDF3E7;
            color: #006B42;
        }

        .voltar {
            display: inline-block;
            margin-bottom: 20px;
            color: #006B42;
            text-decoration: none;
            font-weight: bold;
        }

        .vazio { color: #526157; }

        @media (max-width: 700px) {
            body { padding: 15px; }
        }
    </style>
</head>

<body>

<div class="container">

    <a class="voltar" href="admin.html">← Voltar ao painel</a>

    <h1>Cadastro de Plantões</h1>

    <div class="painel">
        <h2>Novo Plantão</h2>

        <form action="PlantaoServlet" method="POST">

            <label>Escala</label>
            <select name="escalaId" required>
                <option value="">Selecione a escala</option>
                <% if (escalas != null) {
                    for (Escala escala : escalas) {
                        if (escala.isAtiva()) { %>
                    <option value="<%= escala.getId() %>">
                        <%= escala.getNome() %> - <%= escala.getUnidade() %>
                    </option>
                <% }}} %>
            </select>

            <label>Médico</label>
            <select name="medicoId" required>
                <option value="">Selecione o médico</option>
                <% if (medicos != null) {
                    for (Medico medico : medicos) { %>
                    <option value="<%= medico.getId() %>">
                        <%= medico.getNome() %> - CRM: <%= medico.getCrm() %>
                    </option>
                <% }} %>
            </select>

            <label>Data do plantão</label>
            <input type="date" name="dataPlantao" required>

            <label>Horário de início</label>
            <input type="time" name="horaInicio" required>

            <label>Horário de término</label>
            <input type="time" name="horaFim" required>

            <button class="botao" type="submit">
                Cadastrar Plantão
            </button>
        </form>
    </div>

    <div class="painel">
        <h2>Plantões Cadastrados</h2>

        <table>
            <thead>
                <tr>
                    <th>Plantão</th>
                    <th>Escala</th>
                    <th>Unidade</th>
                    <th>Médico responsável</th>
                    <th>Data</th>
                    <th>Horário</th>
                    <th>Status</th>
                </tr>
            </thead>

            <tbody>
                <% if (plantoes != null && !plantoes.isEmpty()) {
                    for (Plantao p : plantoes) { %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNomeEscala() %></td>
                        <td><%= p.getUnidade() %></td>
                        <td><%= p.getNomeMedico() %></td>
                        <td><%= p.getDataPlantao() != null
                                ? formatoData.format(p.getDataPlantao())
                                : "-" %></td>
                        <td>
                            <%= p.getHoraInicio() %> -
                            <%= p.getHoraFim() %>
                        </td>
                        <td><%= p.getStatus() %></td>
                    </tr>
                <% }} else { %>
                    <tr>
                        <td colspan="7" class="vazio">
                            Nenhum plantão cadastrado.
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
