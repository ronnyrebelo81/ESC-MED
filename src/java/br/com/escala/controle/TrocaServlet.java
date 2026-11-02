
package br.com.escala.controle;

import br.com.escala.dao.TrocaDAO;
import br.com.escala.modelo.Troca;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TrocaServlet")
public class TrocaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null
                || !"ADMIN".equals(sessao.getAttribute("perfil"))) {
            response.sendError(403);
            return;
        }

        try {
            TrocaDAO dao = new TrocaDAO();
            List<Troca> trocas = dao.listarTodas();

            request.setAttribute("trocas", trocas);

            request.getRequestDispatcher("/trocas.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Erro ao consultar as trocas.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sessao = request.getSession(false);

        if (sessao == null
                || !"ADMIN".equals(sessao.getAttribute("perfil"))) {
            response.sendError(403);
            return;
        }

        try {
            String idParametro = request.getParameter("id");
            String status = request.getParameter("status");

            if (idParametro == null || idParametro.trim().isEmpty()
                    || status == null) {
                response.sendError(400, "Dados da solicitação incompletos.");
                return;
            }

            int id = Integer.parseInt(idParametro);

            TrocaDAO dao = new TrocaDAO();

            if (dao.atualizarStatus(id, status)) {
                response.sendRedirect(
                        request.getContextPath() + "/TrocaServlet?sucesso=1");
            } else {
                response.sendError(400,
                        "Solicitação não encontrada, plantão indisponível "
                        + "ou solicitação já analisada.");
            }

        } catch (NumberFormatException e) {
            response.sendError(400, "Identificador da troca inválido.");

        } catch (IllegalArgumentException e) {
            response.sendError(400, "Status inválido.");

        } catch (SQLException e) {
            throw new ServletException("Erro ao atualizar a troca.", e);
        }
    }
}
