
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.com.escala.modelo.Troca"%>

<%
    List<Troca> trocas =
            (List<Troca>) request.getAttribute("trocas");

    Integer medicoId =
            (Integer) request.getAttribute("medicoId");

    SimpleDateFormat formatoData =
            new SimpleDateFormat("dd/MM/yyyy");

    SimpleDateFormat formatoDataHora =
            new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minhas Solicitações - ESC-MED</title>

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

        .voltar {
            display: inline-block;
            margin-bottom: 20px;
            color: #006B42;
            text-decoration: none;
            font-weight: bold;
        }

        h1 { color: #006B42; }

        .card {
            background: white;
            padding: 24px;
            border-radius: 14px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.06);
            overflow-x: auto;
        }

        table {
            width: 100%;
            min-width: 950px;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 11px 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
            white-space: nowrap;
        }

        th {
            background: #DDF3E7;
            color: #006B42;
        }

        .status { font-weight: bold; }
        .PENDENTE { color: #B7791F; }
        .APROVADA { color: #16834A; }
        .RECUSADA { color: #B83232; }

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
            body { padding: 15px; }
        }
    </style>
</head>

<body>

<div class="container">

    <a href="medico.html" class="voltar">
        ← Voltar ao painel
    </a>

    <h1>Minhas Solicitações de Troca</h1>

    <div class="card">

        <h2>Histórico de solicitações</h2>

        <% if (trocas == null || trocas.isEmpty()) { %>

            <p class="vazio">
                Você ainda não possui solicitações de troca.
            </p>

        <% } else { %>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Plantão</th>
                        <th>Escala / Unidade</th>
                        <th>Data</th>
                        <th>Horário</th>
                        <th>Participação</th>
                        <th>Médico solicitante</th>
                        <th>Substituto</th>
                        <th>Status</th>
                        <th>Solicitação em</th>
                    </tr>
                </thead>

                <tbody>

                <% for (Troca troca : trocas) {
                    boolean solicitante =
                            troca.getMedicoSolicitanteId() == medicoId;

                    String participacao = solicitante
                            ? "Solicitante" : "Substituto";

                    String substituto =
                            troca.getMedicoSubstitutoId() != null
                            ? troca.getNomeMedicoSubstituto()
                            : (troca.getNomeExterno() != null
                            ? troca.getNomeExterno() : "Não informado");
                %>

                    <tr>
                        <td><%= troca.getId() %></td>
                        <td><%= troca.getPlantaoId() %></td>

                        <td>
                            <%= troca.getNomeEscala() %> /
                            <%= troca.getUnidade() %>
                        </td>

                        <td>
                            <%= troca.getDataPlantao() != null
                                    ? formatoData.format(troca.getDataPlantao())
                                    : "-" %>
                        </td>

                        <td>
                            <%= troca.getHoraInicio() %> -
                            <%= troca.getHoraFim() %>
                        </td>

                        <td><%= participacao %></td>

                        <td><%= troca.getNomeMedicoSolicitante() %></td>

                        <td><%= substituto %></td>

                        <td>
                            <span class="status <%= troca.getStatus() %>">
                                <%= troca.getStatus() %>
                            </span>
                        </td>

                        <td>
                            <%= troca.getDataSolicitacao() != null
                                    ? formatoDataHora.format(
                                            troca.getDataSolicitacao())
                                    : "-" %>
                        </td>
                    </tr>

                <% } %>

                </tbody>
            </table>

        <% } %>

        <br>

        <a href="MeusPlantoesServlet" class="botao">
            Ver meus plantões
        </a>

    </div>

</div>

</body>
</html>
