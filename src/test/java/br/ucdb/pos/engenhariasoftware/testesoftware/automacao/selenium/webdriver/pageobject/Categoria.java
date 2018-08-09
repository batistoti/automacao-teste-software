package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

public enum Categoria {

    ALIMENTACAO("Alimentação"),
    SALARIO("Salário"),
    LAZER("Lazer"),
    TELEFONE_INTERNET("Telefone & Internet"),
    CARRO("Carro"),
    EMPRESTIMO("Empréstimo"),
    INVESTIMENTOS("Investimentos"),
    OUTROS("Outros");

    private String nome;

    private Categoria(final String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
