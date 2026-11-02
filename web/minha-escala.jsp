
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

    <title>Minha Escala - ESC-MED</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            padding: 30px;
            font-family: Arial, sans-serif;
            background: #F4F8F5;
            color: #202820;
        }

        .container {
            max-width: 1100px;
            margin: auto;
        }

        .voltar {
            display: inline-block;
            margin-bottom: 20px;
            color: #006B42;
            text-decoration: none;
            font-weight: bold;
        }

        h1 {
            color: #006B42;
            margin-bottom: 25px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 14px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.06);
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 13px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background: #DDF3E7;
            color: #006B42;
        }

        .status {
            font-weight: bold;
        }

        .programado {
            color: #16834A;
        }

        .realizado {
            color: #276749;
        }

        .cancelado {
            color: #B83232;
        }

        .vazio {
            padding: 20px;
            background: #DDF3E7;
            border-radius: 8px;
            color: #006B42;
        }

        .botao {
            display: inline-block;
            padding: 10px 16px;
            background: #00995D;
            color: white;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
        }

        @media (max-width: 650px) {
            body {
                padding: 15px;
            }
        }
    </style>
</head>

<body>

<div class="container">

    <a href="medico.html" class="voltar">
        ← Voltar ao painel
    </a>

    <h1>Minha Escala de Plantões</h1>

    <div class="card">

        <h2>Histórico da minha escala</h2>

        <% if (plantoes == null || plantoes.isEmpty()) { %>

            <p class="vazio">
                Nenhum plantão encontrado na sua escala.
            </p>

        <% } else { %>

            <table>
                <thead>
                    <tr>
                        <th>Escala</th>
                        <th>Unidade</th>
                        <th>Data</th>
                        <th>Início</th>
                        <th>Término</th>
                        <th>Status</th>
                    </tr>
                </thead>

                <tbody>

                <% for (Plantao plantao : plantoes) {
                    String classeStatus =
                            plantao.getStatus().toLowerCase();
                %>

                    <tr>
                        <td><%= plantao.getNomeEscala() %></td>
                        <td><%= plantao.getUnidade() %></td>
                        <td><%= plantao.getDataPlantao() != null
                                ? formatoData.format(plantao.getDataPlantao())
                                : "-" %></td>
                        <td><%= plantao.getHoraInicio() %></td>
                        <td><%= plantao.getHoraFim() %></td>

                        <td>
                            <span class="status <%= classeStatus %>">
                                <%= plantao.getStatus() %>
                            </span>
                        </td>
                    </tr>

                <% } %>

                </tbody>
            </table>

        <% } %>

        <br>

        <a href="MeusPlantoesServlet" class="botao">
            Ver próximos plantões
        </a>

    </div>

</div>

</body>
</html>
