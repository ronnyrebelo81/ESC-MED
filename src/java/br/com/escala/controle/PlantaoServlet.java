package br.com.escala.controle;

import br.com.escala.dao.PlantaoDAO;
import br.com.escala.dao.EscalaDAO;
import br.com.escala.dao.MedicoDAO;
import br.com.escala.modelo.Plantao;
import br.com.escala.modelo.Escala;
import br.com.escala.modelo.Medico;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PlantaoServlet")
public class PlantaoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || !"ADMIN".equals(sessao.getAttribute("perfil"))) {
            response.sendError(403);
            return;
        }

        try {
            EscalaDAO escalaDAO = new EscalaDAO();
            MedicoDAO medicoDAO = new MedicoDAO();
            PlantaoDAO plantaoDAO = new PlantaoDAO();

            List<Escala> escalas = escalaDAO.listarTodas();
            List<Medico> medicos = medicoDAO.listarTodos();
            List<Plantao> plantoes = plantaoDAO.listarTodos();

            request.setAttribute("escalas", escalas);
            request.setAttribute("medicos", medicos);
            request.setAttribute("plantoes", plantoes);

            request.getRequestDispatcher("/plantoes.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Erro ao carregar os plantões.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sessao = request.getSession(false);

        if (sessao == null || !"ADMIN".equals(sessao.getAttribute("perfil"))) {
            response.sendError(403);
            return;
        }

        try {
            Plantao plantao = new Plantao();

            plantao.setEscalaId(Integer.parseInt(request.getParameter("escalaId")));
            plantao.setMedicoId(Integer.parseInt(request.getParameter("medicoId")));
            plantao.setDataPlantao(Date.valueOf(request.getParameter("dataPlantao")));
            plantao.setHoraInicio(Time.valueOf(request.getParameter("horaInicio") + ":00"));
            plantao.setHoraFim(Time.valueOf(request.getParameter("horaFim") + ":00"));
            plantao.setStatus("PROGRAMADO");

            PlantaoDAO dao = new PlantaoDAO();

            if (dao.cadastrar(plantao)) {
                response.sendRedirect(request.getContextPath() + "/PlantaoServlet?sucesso=1");
            } else {
                response.sendError(500, "Não foi possível cadastrar o plantão.");
            }

        } catch (IllegalArgumentException e) {
            response.sendError(400, "Verifique a data e os horários informados.");
        } catch (SQLException e) {
            throw new ServletException("Erro ao cadastrar o plantão.", e);
        }
    }
}