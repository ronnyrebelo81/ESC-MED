
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.com.escala.modelo.Troca"%>

<%
    List<Troca> trocas = (List<Troca>) request.getAttribute("trocas");

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
    <title>Gerenciamento de Trocas - ESC-MED</title>

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
            max-width: 1500px;
            margin: auto;
        }

        h1 {
            color: #006B42;
            font-size: 28px;
        }

        .painel {
            background: white;
            padding: 24px;
            border-radius: 12px;
            margin-top: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
            overflow-x: auto;
        }

        table {
            width: 100%;
            min-width: 1100px;
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

        button {
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
            margin: 2px;
            font-weight: bold;
        }

        .botao {
            display: inline-block;
            padding: 9px 13px;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
            font-weight: bold;
        }

        .aprovar { background: #00995D; }
        .recusar { background: #B83232; }

        .voltar {
            color: #006B42;
            text-decoration: none;
            font-weight: bold;
        }

        .vazio { color: #526157; }

        .status {
            font-weight: bold;
        }

        .PENDENTE { color: #B7791F; }
        .APROVADA { color: #16834A; }
        .RECUSADA { color: #B83232; }

        @media (max-width: 700px) {
            body { padding: 15px; }
        }
    </style>
</head>

<body>
<div class="container">

    <a class="voltar" href="admin.html">← Voltar ao painel</a>

    <h1>Gerenciamento de Trocas de Plantões</h1>

    <div class="painel">
        <h2>Solicitações recebidas</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Plantão</th>
                    <th>Escala / Unidade</th>
                    <th>Data do plantão</th>
                    <th>Horário</th>
                    <th>Médico solicitante</th>
                    <th>Substituto</th>
                    <th>Contato externo</th>
                    <th>Status</th>
                    <th>Solicitação em</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>
                <% if (trocas != null && !trocas.isEmpty()) {
                    for (Troca troca : trocas) { %>

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

                        <td><%= troca.getNomeMedicoSolicitante() %></td>

                        <td>
                            <% if (troca.getMedicoSubstitutoId() != null) { %>
                                <%= troca.getNomeMedicoSubstituto() %>
                            <% } else { %>
                                <%= troca.getNomeExterno() != null
                                    ? troca.getNomeExterno() : "Não informado" %>
                            <% } %>
                        </td>

                        <td>
                            <%= troca.getContatoExterno() != null
                                ? troca.getContatoExterno() : "-" %>
                        </td>

                        <td class="status <%= troca.getStatus() %>">
                            <%= troca.getStatus() %>
                        </td>

                        <td>
                            <%= troca.getDataSolicitacao() != null
                                    ? formatoDataHora.format(
                                            troca.getDataSolicitacao())
                                    : "-" %>
                        </td>

                        <td>
                            <% if ("PENDENTE".equals(troca.getStatus())) { %>

                                <form action="TrocaServlet" method="POST"
                                      style="display:inline;">
                                    <input type="hidden" name="id"
                                           value="<%= troca.getId() %>">
                                    <input type="hidden" name="status"
                                           value="APROVADA">
                                    <button class="botao aprovar" type="submit">
                                        Aprovar
                                    </button>
                                </form>

                                <form action="TrocaServlet" method="POST"
                                      style="display:inline;">
                                    <input type="hidden" name="id"
                                           value="<%= troca.getId() %>">
                                    <input type="hidden" name="status"
                                           value="RECUSADA">
                                    <button class="botao recusar" type="submit">
                                        Recusar
                                    </button>
                                </form>

                            <% } else { %>
                                Solicitação analisada
                            <% } %>
                        </td>
                    </tr>

                <% }} else { %>

                    <tr>
                        <td colspan="11" class="vazio">
                            Nenhuma solicitação de troca cadastrada.
                        </td>
                    </tr>

                <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
