
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.com.escala.modelo.Plantao"%>
<%@page import="br.com.escala.modelo.Medico"%>

<%
    List<Plantao> plantoes =
            (List<Plantao>) request.getAttribute("plantoes");

    List<Medico> medicos =
            (List<Medico>) request.getAttribute("medicos");

    SimpleDateFormat formatoData =
            new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Solicitar Troca - ESC-MED</title>

    <style>
        * { box-sizing: border-box; }

        body {
            margin: 0;
            padding: 30px;
            font-family: Arial, sans-serif;
            background: #F4F8F5;
            color: #202820;
        }

        .container {
            max-width: 850px;
            margin: auto;
        }

        .painel {
            background: white;
            padding: 28px;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
        }

        h1, h2 { color: #006B42; }

        label {
            display: block;
            margin: 17px 0 7px;
            font-weight: bold;
        }

        input, select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccd8cf;
            border-radius: 6px;
        }

        .botao {
            margin-top: 22px;
            padding: 13px 22px;
            border: none;
            border-radius: 6px;
            background: #00995D;
            color: white;
            font-weight: bold;
            cursor: pointer;
        }

        .botao:hover { background: #006B42; }

        .voltar {
            display: inline-block;
            margin-bottom: 20px;
            color: #006B42;
            text-decoration: none;
            font-weight: bold;
        }

        .aviso {
            color: #526157;
            font-size: 14px;
            margin-top: 18px;
        }

        .mensagem {
            background: #DDF3E7;
            padding: 12px;
            border-radius: 6px;
            color: #006B42;
            margin-bottom: 18px;
        }
    </style>

    <script>
        function mostrarSubstituto() {
            var tipo = document.getElementById("tipoSubstituto").value;

            document.getElementById("medicoCadastrado").style.display =
                    tipo === "CADASTRADO" ? "block" : "none";

            document.getElementById("medicoExterno").style.display =
                    tipo === "EXTERNO" ? "block" : "none";
        }
    </script>
</head>

<body>
<div class="container">

    <a class="voltar" href="medico.html">← Voltar ao painel</a>

    <h1>Solicitar Troca de Plantão</h1>

    <div class="painel">

        <% if ("1".equals(request.getParameter("sucesso"))) { %>
            <div class="mensagem">
                Solicitação registrada com sucesso! Aguarde a análise do administrador.
            </div>
        <% } %>

        <form action="SolicitarTrocaServlet" method="POST">

            <label>Selecione o plantão que deseja trocar</label>

            <select name="plantaoId" required>
                <option value="">Selecione seu plantão</option>

                <% if (plantoes != null) {
                    for (Plantao p : plantoes) { %>

                    <option value="<%= p.getId() %>">
                        Plantão <%= p.getId() %> -
                        Escala <%= p.getNomeEscala() %> -
                        <%= p.getDataPlantao() != null
                                ? formatoData.format(p.getDataPlantao())
                                : "-" %> -
                        <%= p.getHoraInicio() %> às <%= p.getHoraFim() %>
                    </option>

                <% }} %>
            </select>

            <label>Tipo de substituto</label>

            <select id="tipoSubstituto" name="tipoSubstituto"
                    onchange="mostrarSubstituto()" required>
                <option value="">Selecione</option>
                <option value="CADASTRADO">Médico cadastrado</option>
                <option value="EXTERNO">Médico externo</option>
            </select>

            <div id="medicoCadastrado" style="display:none;">

                <label>Selecione o médico substituto</label>

                <select name="medicoSubstitutoId">
                    <option value="">Selecione o médico</option>

                    <% if (medicos != null) {
                        for (Medico m : medicos) { %>

                        <option value="<%= m.getId() %>">
                            <%= m.getNome() %> - CRM: <%= m.getCrm() %>
                        </option>

                    <% }} %>
                </select>
            </div>

            <div id="medicoExterno" style="display:none;">

                <label>Nome do médico externo</label>
                <input type="text" name="nomeExterno"
                       maxlength="100">

                <label>Contato do médico externo</label>
                <input type="text" name="contatoExterno"
                       maxlength="100">
            </div>

            <button class="botao" type="submit">
                Enviar Solicitação
            </button>

        </form>

        <p class="aviso">
            Sua solicitação ficará pendente até a análise e confirmação do administrador.
        </p>

    </div>
</div>
</body>
</html>
