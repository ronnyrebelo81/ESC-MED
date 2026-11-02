
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.com.escala.modelo.Plantao"%>

<%
    List<Plantao> plantoes =
            (List<Plantao>) request.getAttribute("plantoes");

    SimpleDateFormat formatoData =
            new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meus Plantões - ESC-MED</title>

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
            max-width: 1400px;
            margin: auto;
        }

        .painel {
            background: white;
            padding: 24px;
            border-radius: 12px;
            margin-top: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
            overflow-x: auto;
        }

        h1, h2 { color: #006B42; }

        table {
            width: 100%;
            min-width: 850px;
            border-collapse: collapse;
            margin-top: 18px;
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
            color: #006B42;
            text-decoration: none;
            font-weight: bold;
        }

        .vazio { color: #526157; }

        .status {
            color: #006B42;
            font-weight: bold;
        }

        @media (max-width: 700px) {
            body { padding: 15px; }
        }
    </style>
</head>

<body>

<div class="container">

    <a class="voltar" href="medico.html">← Voltar ao painel</a>

    <h1>Meus Plantões</h1>

    <div class="painel">

        <h2>Minha programação</h2>

        <table>
            <thead>
                <tr>
                    <th>Plantão</th>
                    <th>Escala</th>
                    <th>Unidade</th>
                    <th>Data</th>
                    <th>Horário de início</th>
                    <th>Horário de término</th>
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
                        <td><%= p.getDataPlantao() != null
                                ? formatoData.format(p.getDataPlantao())
                                : "-" %></td>
                        <td><%= p.getHoraInicio() %></td>
                        <td><%= p.getHoraFim() %></td>
                        <td class="status"><%= p.getStatus() %></td>
                    </tr>

                <% }} else { %>

                    <tr>
                        <td colspan="7" class="vazio">
                            Você não possui plantões programados.
                        </td>
                    </tr>

                <% } %>

            </tbody>
        </table>

    </div>

</div>

</body>
</html>
