
package br.com.escala.controle;

import br.com.escala.dao.EscalaDAO;
import br.com.escala.modelo.Escala;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EscalaServlet")
public class EscalaServlet extends HttpServlet {

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
            EscalaDAO dao = new EscalaDAO();
            List<Escala> escalas = dao.listarTodas();

            request.setAttribute("escalas", escalas);

            request.getRequestDispatcher("/escalas.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Erro ao consultar escalas.", e);
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

        String nome = request.getParameter("nome");
        String unidade = request.getParameter("unidade");

        if (nome == null || unidade == null
                || nome.trim().isEmpty()
                || unidade.trim().isEmpty()) {
            response.sendError(400, "Preencha o nome e a unidade.");
            return;
        }

        Escala escala = new Escala();
        escala.setNome(nome.trim());
        escala.setUnidade(unidade.trim());
        escala.setAtiva(true);

        try {
            EscalaDAO dao = new EscalaDAO();

            if (dao.cadastrar(escala)) {
                response.sendRedirect(
                        request.getContextPath() + "/EscalaServlet?sucesso=1");
            } else {
                response.sendError(500, "Não foi possível cadastrar a escala.");
            }

        } catch (SQLException e) {
            throw new ServletException("Erro ao cadastrar escala.", e);
        }
    }
}
