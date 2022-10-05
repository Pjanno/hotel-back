package br.felipe.hotel.hospede;

import br.felipe.hotel.checkin.Checkin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hospede")
public class Hospede {

    @Id
    @SequenceGenerator(name = "hospede_sequence", sequenceName = "hospede_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospede_sequence")
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "telefone",nullable = false)
    private String telefone;
    @Column(name = "documento", nullable = false)
    private String documento;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospede")
    @JsonIgnore
    private List<Checkin> checkin;

    public Hospede() {
    }

    public Hospede(Long id, String nome, String telefone, String documento, List<Checkin> checkin) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
        this.checkin = checkin;
    }

    public Hospede(String nome, String telefone, String documento, Boolean ativo) {
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
        this.ativo = ativo;
    }

    public Hospede(String nome, String telefone, String documento, List<Checkin> checkin) {
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
        this.checkin = checkin;
    }

    public Hospede(String nome, String telefone, String documento) {
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Checkin> getCheckin() {
        return checkin;
    }
    public void setCheckin(List<Checkin> checkin) {
        this.checkin = checkin;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
