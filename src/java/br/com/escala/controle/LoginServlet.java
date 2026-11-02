
package br.com.escala.controle;

import br.com.escala.dao.HashSenha;
import br.com.escala.dao.MedicoDAO;
import br.com.escala.modelo.Medico;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        if (usuario == null || senha == null
                || usuario.trim().isEmpty() || senha.isEmpty()) {
            response.sendRedirect("login.html?erro=campos");
            return;
        }

        try {
            MedicoDAO dao = new MedicoDAO();
            Medico medico = dao.buscarPorUsuario(usuario.trim());

            if (medico == null
                    || !HashSenha.verificar(senha, medico.getSenhaHash())) {

                response.sendRedirect("login.html?erro=credenciais");
                return;
            }

            HttpSession sessao = request.getSession(true);
            sessao.setAttribute("medicoId", medico.getId());
            sessao.setAttribute("nomeUsuario", medico.getNome());
            sessao.setAttribute("perfil", medico.getPerfil());

            sessao.setMaxInactiveInterval(30 * 60);

            if ("ADMIN".equals(medico.getPerfil())) {
                response.sendRedirect("admin.html");
            } else if ("MEDICO".equals(medico.getPerfil())) {
                response.sendRedirect("medico.html");
            } else {
                sessao.invalidate();
                response.sendRedirect("login.html?erro=perfil");
            }

        } catch (SQLException | NoSuchAlgorithmException
                | InvalidKeySpecException e) {

            throw new ServletException("Erro durante a autenticacao.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect("login.html");
    }
}
