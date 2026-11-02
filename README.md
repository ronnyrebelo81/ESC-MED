# ESC-MED — Sistema de Escala de Plantão Médico

Sistema web desenvolvido como Projeto Integrado do curso de Análise e Desenvolvimento de Sistemas (ADS), com o objetivo de facilitar o gerenciamento de médicos, escalas de atendimento, plantões e solicitações de troca.

## Sobre o projeto

O ESC-MED permite organizar a distribuição dos plantões médicos, acompanhar os horários de atendimento e administrar solicitações de substituição de profissionais.

A aplicação possui áreas específicas para administradores e médicos, com funcionalidades de acordo com o perfil de acesso.

## Funcionalidades

### Administrador
- Cadastro e gerenciamento de médicos.
- Criação e gerenciamento de escalas.
- Cadastro de plantões, com datas e horários.
- Consulta e gerenciamento das solicitações de troca.
- Aprovação ou recusa de solicitações de substituição.

### Médico
- Acesso individual ao sistema.
- Consulta dos próprios plantões.
- Visualização da própria escala.
- Solicitação de troca de plantão.
- Indicação de médico substituto cadastrado ou profissional externo.
- Acompanhamento das solicitações realizadas.

## Tecnologias utilizadas

- Java
- HTML5
- CSS3
- JavaServer Pages (JSP)
- Java Servlets
- MySQL
- Apache Tomcat 9
- Apache NetBeans IDE
- MySQL Connector/J

## Banco de dados

O projeto utiliza MySQL para armazenar os dados dos médicos, escalas, plantões e solicitações de troca.

Banco de dados: `escala_plantao_medico`

As credenciais de conexão são configuradas por meio de variáveis de ambiente, evitando a exposição de senhas no código-fonte.

Variáveis necessárias:

- `ESC_MED_DB_USER`
- `ESC_MED_DB_PASSWORD`

## Como executar o projeto

1. Clone ou baixe este repositório.
2. Abra o projeto no Apache NetBeans.
3. Configure o MySQL e crie o banco de dados `escala_plantao_medico`.
4. Configure as variáveis de ambiente de conexão com o banco.
5. Verifique se o MySQL Connector/J está disponível nas bibliotecas do projeto.
6. Configure o Apache Tomcat 9 no NetBeans.
7. Execute o projeto pelo NetBeans.

## Objetivo acadêmico

Este sistema foi desenvolvido como parte das atividades do Projeto Integrado do curso de Análise e Desenvolvimento de Sistemas, com foco na aplicação prática de conceitos de programação, banco de dados, desenvolvimento web e organização de sistemas.

---

**ESC-MED — Sistema de Escala de Plantão Médico**

Projeto acadêmico — ADS | 2026
