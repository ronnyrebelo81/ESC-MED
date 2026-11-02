
package br.com.escala.modelo;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Troca {

    private int id;
    private int plantaoId;
    private int medicoSolicitanteId;
    private Integer medicoSubstitutoId;
    private String nomeExterno;
    private String contatoExterno;
    private String status;
    private Timestamp dataSolicitacao;

    private String nomeMedicoSolicitante;
    private String nomeMedicoSubstituto;
    private String nomeEscala;
    private String unidade;
    private Date dataPlantao;
    private Time horaInicio;
    private Time horaFim;

    public Troca() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlantaoId() {
        return plantaoId;
    }

    public void setPlantaoId(int plantaoId) {
        this.plantaoId = plantaoId;
    }

    public int getMedicoSolicitanteId() {
        return medicoSolicitanteId;
    }

    public void setMedicoSolicitanteId(int medicoSolicitanteId) {
        this.medicoSolicitanteId = medicoSolicitanteId;
    }

    public Integer getMedicoSubstitutoId() {
        return medicoSubstitutoId;
    }

    public void setMedicoSubstitutoId(Integer medicoSubstitutoId) {
        this.medicoSubstitutoId = medicoSubstitutoId;
    }

    public String getNomeExterno() {
        return nomeExterno;
    }

    public void setNomeExterno(String nomeExterno) {
        this.nomeExterno = nomeExterno;
    }

    public String getContatoExterno() {
        return contatoExterno;
    }

    public void setContatoExterno(String contatoExterno) {
        this.contatoExterno = contatoExterno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Timestamp dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getNomeMedicoSolicitante() {
        return nomeMedicoSolicitante;
    }

    public void setNomeMedicoSolicitante(String nomeMedicoSolicitante) {
        this.nomeMedicoSolicitante = nomeMedicoSolicitante;
    }

    public String getNomeMedicoSubstituto() {
        return nomeMedicoSubstituto;
    }

    public void setNomeMedicoSubstituto(String nomeMedicoSubstituto) {
        this.nomeMedicoSubstituto = nomeMedicoSubstituto;
    }

    public String getNomeEscala() {
        return nomeEscala;
    }

    public void setNomeEscala(String nomeEscala) {
        this.nomeEscala = nomeEscala;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Date getDataPlantao() {
        return dataPlantao;
    }

    public void setDataPlantao(Date dataPlantao) {
        this.dataPlantao = dataPlantao;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Time horaFim) {
        this.horaFim = horaFim;
    }
}
