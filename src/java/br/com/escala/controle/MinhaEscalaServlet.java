
package br.com.escala.controle;

import br.com.escala.dao.PlantaoDAO;
import br.com.escala.modelo.Plantao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MinhaEscalaServlet")
public class MinhaEscalaServlet extends HttpServlet {

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

            PlantaoDAO dao = new PlantaoDAO();
            List<Plantao> plantoes =
                    dao.listarEscalaPorMedico(medicoId);

            request.setAttribute("plantoes", plantoes);

            request.getRequestDispatcher("/minha-escala.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Erro ao consultar sua escala.", e);
        }
    }
}
