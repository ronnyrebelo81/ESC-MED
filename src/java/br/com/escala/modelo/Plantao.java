
package br.com.escala.modelo;

import java.sql.Date;
import java.sql.Time;

public class Plantao {

    private int id;
    private int escalaId;
    private int medicoId;
    private Date dataPlantao;
    private Time horaInicio;
    private Time horaFim;
    private String status;
    private String nomeEscala;
    private String unidade;
    private String nomeMedico;

    public Plantao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEscalaId() {
        return escalaId;
    }

    public void setEscalaId(int escalaId) {
        this.escalaId = escalaId;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }
}
