
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

@WebServlet("/MinhasTrocasServlet")
public class MinhasTrocasServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null
                || !"MEDICO".equals(sessao.getAttribute("perfil"))) {
            response.sendError(403);
            return;
        }

        try {
            int medicoId = (Integer) sessao.getAttribute("medicoId");

            TrocaDAO dao = new TrocaDAO();
            List<Troca> trocas = dao.listarPorMedico(medicoId);

            request.setAttribute("trocas", trocas);
            request.setAttribute("medicoId", medicoId);

            request.getRequestDispatcher("/minhas-trocas.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Erro ao consultar suas solicitações.", e);
        }
    }
}
