package br.felipe.hotel.checkin;

import br.felipe.hotel.hospede.Hospede;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "checkin")
public class Checkin {

    @Id
    @SequenceGenerator(name = "checkin_sequence", sequenceName = "checkin_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checkin_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Column(name = "adicional_veiculo")
    private Boolean adicionalVeiculo;

    public Checkin() {
    }

    public Checkin(Long id, Hospede hospede, LocalDateTime dataEntrada, LocalDateTime dataSaida, Boolean adicionalVeiculo) {
        this.id = id;
        this.hospede = hospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.adicionalVeiculo = adicionalVeiculo;
    }

    public Checkin(Hospede hospede, LocalDateTime dataEntrada, LocalDateTime dataSaida, Boolean adicionalVeiculo) {
        this.hospede = hospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.adicionalVeiculo = adicionalVeiculo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
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

}
