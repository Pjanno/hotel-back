package br.felipe.hotel.hospede.vo;

import java.math.BigDecimal;

public class HospedeConsultaVo extends HospedeVo {

    private BigDecimal valorUltimaHospedagem;
    private BigDecimal valorTotalGastoHotel;

    public HospedeConsultaVo() {
    }

    public HospedeConsultaVo(BigDecimal valorUltimaHospedagem, BigDecimal valorTotalGastoHotel) {
        this.valorUltimaHospedagem = valorUltimaHospedagem;
        this.valorTotalGastoHotel = valorTotalGastoHotel;
    }

    public HospedeConsultaVo(String nome, String telefone, String documento, BigDecimal valorUltimaHospedagem, BigDecimal valorTotalGastoHotel) {
        super(nome, telefone, documento);
        this.valorUltimaHospedagem = valorUltimaHospedagem;
        this.valorTotalGastoHotel = valorTotalGastoHotel;
    }

    public BigDecimal getValorUltimaHospedagem() {
        return valorUltimaHospedagem;
    }

    public void setValorUltimaHospedagem(BigDecimal valorUltimaHospedagem) {
        this.valorUltimaHospedagem = valorUltimaHospedagem;
    }

    public BigDecimal getValorTotalGastoHotel() {
        return valorTotalGastoHotel;
    }

    public void setValorTotalGastoHotel(BigDecimal valorTotalGastoHotel) {
        this.valorTotalGastoHotel = valorTotalGastoHotel;
    }
}
