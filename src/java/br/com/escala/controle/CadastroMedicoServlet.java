
package br.com.escala.controle;

import br.com.escala.dao.MedicoDAO;
import br.com.escala.dao.HashSenha;
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

@WebServlet("/CadastroMedicoServlet")
public class CadastroMedicoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String crm = request.getParameter("crm");
        String especialidade = request.getParameter("especialidade");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        if (nome == null || crm == null || usuario == null
                || senha == null || confirmarSenha == null
                || nome.trim().isEmpty() || crm.trim().isEmpty()
                || usuario.trim().isEmpty() || senha.isEmpty()) {

            response.sendError(400, "Preencha todos os campos obrigatorios.");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            response.sendError(400, "As senhas nao coincidem.");
            return;
        }

        try {
            Medico medico = new Medico();

            medico.setNome(nome.trim());
            medico.setCrm(crm.trim());
            medico.setEspecialidade(
                    especialidade == null ? "" : especialidade.trim());
            medico.setUsuario(usuario.trim());
            medico.setSenhaHash(HashSenha.gerar(senha));
            medico.setPerfil("MEDICO");
            medico.setAtivo(true);

            MedicoDAO dao = new MedicoDAO();

            if (dao.cadastrar(medico)) {
                response.sendRedirect("cadastro-medico.html?sucesso=1");
            } else {
                response.sendError(500,
                        "Nao foi possivel cadastrar o medico.");
            }

        } catch (SQLException | NoSuchAlgorithmException
                | InvalidKeySpecException e) {

            throw new ServletException("Erro ao cadastrar medico.", e);
        }
    }
}
