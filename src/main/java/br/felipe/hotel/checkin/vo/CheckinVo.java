package br.felipe.hotel.checkin.vo;

import br.felipe.hotel.checkin.Checkin;
import br.felipe.hotel.hospede.Hospede;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CheckinVo {

    private Long id;

    private Hospede hospede;

    private LocalDateTime dataEntrada;

    private LocalDateTime dataSaida;

    private Boolean adicionalVeiculo;

    private BigDecimal valorCheckin;

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospedes(Hospede hospede) {
        this.hospede = hospede;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Boolean getAdicionalVeiculo() {
        return adicionalVeiculo;
    }

    public void setAdicionalVeiculo(Boolean adicionalVeiculo) {
        this.adicionalVeiculo = adicionalVeiculo;
    }

    public BigDecimal getValorCheckin() {
        return valorCheckin;
    }

    public void setValorCheckin(BigDecimal valorCheckin) {
        this.valorCheckin = valorCheckin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckinVo() {
    }

    public CheckinVo (Checkin checkin) {
        this.id = checkin.getId();
        this.hospede = checkin.getHospede();
        this.dataEntrada = checkin.getDataEntrada();
        this.dataSaida = checkin.getDataSaida();
        this.adicionalVeiculo = checkin.getAdicionalVeiculo();
    }
}
