package br.felipe.hotel.enums;

public enum Erros {

    ERRO_500("Um problema ocorreu no servidor. Entrar em contato com o Suporte \n"),
    DADOS_INFORMADOS_EXISTENTES("Um ou mais dados informados no cadastro já existem no sistema. \n Utilize a busca com os critérios informados para verificar o cadastro. \n"),
    CHECKIN_VIGENTE_ENCONTRADO("Dados de um Check-in vigente foram encontrados para um ou mais clientes selecionados. \n Utilize a busca com os critérios informados para verificar o cadastro. \n"),
    HOSPEDE_VAZIO("É necessário informar um hóspede para realizar o cadastro do Check-in. \n");

    private String descricao;

    Erros(String s) {
    }

    public String getDescricao(){
        return descricao;
    }
}
