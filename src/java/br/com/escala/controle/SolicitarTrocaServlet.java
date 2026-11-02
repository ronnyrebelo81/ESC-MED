
package br.com.escala.controle;

import br.com.escala.dao.TrocaDAO;
import br.com.escala.dao.PlantaoDAO;
import br.com.escala.dao.MedicoDAO;
import br.com.escala.modelo.Troca;
import br.com.escala.modelo.Plantao;
import br.com.escala.modelo.Medico;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SolicitarTrocaServlet")
public class SolicitarTrocaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || !"MEDICO".equals(sessao.getAttribute("perfil"))) {
            response.sendError(403);
            return;
        }

        try {
            int medicoId = (Integer) sessao.getAttribute("medicoId");

            PlantaoDAO plantaoDAO = new PlantaoDAO();
            MedicoDAO medicoDAO = new MedicoDAO();

            List<Plantao> plantoes =
                    plantaoDAO.listarPorMedico(medicoId);

            List<Medico> medicos = medicoDAO.listarTodos();

            medicos.removeIf(medico -> medico.getId() == medicoId);

            request.setAttribute("plantoes", plantoes);
            request.setAttribute("medicos", medicos);

            request.getRequestDispatcher("/solicitar-troca.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Erro ao carregar os dados da solicitação.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sessao = request.getSession(false);

        if (sessao == null || !"MEDICO".equals(sessao.getAttribute("perfil"))) {
            response.sendError(403);
            return;
        }

        try {
            int medicoId = (Integer) sessao.getAttribute("medicoId");
            int plantaoId = Integer.parseInt(request.getParameter("plantaoId"));

            String tipoSubstituto = request.getParameter("tipoSubstituto");

            Troca troca = new Troca();
            troca.setPlantaoId(plantaoId);
            troca.setMedicoSolicitanteId(medicoId);
            troca.setStatus("PENDENTE");

            if ("CADASTRADO".equals(tipoSubstituto)) {

                int substitutoId = Integer.parseInt(
                        request.getParameter("medicoSubstitutoId"));

                if (substitutoId == medicoId) {
                    response.sendError(400,
                            "Você não pode solicitar a própria substituição.");
                    return;
                }

                troca.setMedicoSubstitutoId(substitutoId);

            } else if ("EXTERNO".equals(tipoSubstituto)) {

                String nome = request.getParameter("nomeExterno");
                String contato = request.getParameter("contatoExterno");

                if (nome == null || nome.trim().isEmpty()
                        || contato == null || contato.trim().isEmpty()) {
                    response.sendError(400,
                            "Informe o nome e o contato do médico externo.");
                    return;
                }

                troca.setNomeExterno(nome.trim());
                troca.setContatoExterno(contato.trim());

            } else {
                response.sendError(400, "Tipo de substituto inválido.");
                return;
            }

            TrocaDAO dao = new TrocaDAO();

            if (dao.cadastrar(troca)) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/SolicitarTrocaServlet?sucesso=1");
            } else {
                response.sendError(400,
                        "Não foi possível registrar. Verifique o plantão e o substituto.");
            }

        } catch (NumberFormatException e) {
            response.sendError(400, "Dados inválidos.");
        } catch (SQLException e) {
            throw new ServletException(
                    "Erro ao registrar solicitação de troca.", e);
        }
    }
}
