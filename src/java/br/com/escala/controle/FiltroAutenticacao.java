
package br.com.escala.controle;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class FiltroAutenticacao implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String caminho = req.getServletPath();

        boolean paginaPublica =
                caminho.equals("/login.html")
                || caminho.equals("/login")
                || caminho.equals("/index.html")
                || caminho.equals("/")
                || caminho.startsWith("/css/")
                || caminho.startsWith("/js/")
                || caminho.startsWith("/imagens/");

        if (paginaPublica) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession sessao = req.getSession(false);

        if (sessao == null || sessao.getAttribute("perfil") == null) {
            res.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        String perfil = (String) sessao.getAttribute("perfil");

        boolean areaAdmin =
                caminho.equals("/admin.html")
                || caminho.equals("/cadastro-medico.html")
                || caminho.equals("/CadastroMedicoServlet")
                || caminho.equals("/escalas.html")
                || caminho.equals("/EscalaServlet")
                || caminho.equals("/plantoes.html")
                || caminho.equals("/PlantaoServlet")
                || caminho.equals("/trocas.html")
                || caminho.equals("/TrocaServlet");

        boolean areaMedico =
                caminho.equals("/medico.html")
                || caminho.equals("/meus-plantoes.html")
                || caminho.equals("/MeusPlantoesServlet")
                || caminho.equals("/minha-escala.html")
                || caminho.equals("/MinhaEscalaServlet")
                || caminho.equals("/solicitar-troca.html")
                || caminho.equals("/solicitar-troca.jsp")
                || caminho.equals("/SolicitarTrocaServlet")
                || caminho.equals("/minhas-trocas.html")
                || caminho.equals("/MinhasTrocasServlet");

        if (areaAdmin && !"ADMIN".equals(perfil)) {
            res.sendError(403, "Acesso permitido somente ao administrador.");
            return;
        }

        if (areaMedico && !"MEDICO".equals(perfil)) {
            res.sendError(403, "Acesso permitido somente ao medico.");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
