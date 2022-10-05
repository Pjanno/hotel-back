package br.felipe.hotel.hospede.vo;

public class HospedeVo {

    private String nome;
    private String telefone;
    private String documento;

    public HospedeVo() {
    }

    public HospedeVo(String nome, String telefone, String documento) {
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
}
