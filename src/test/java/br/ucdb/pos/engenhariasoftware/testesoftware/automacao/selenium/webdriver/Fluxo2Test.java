package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 * Classe Fluxo2Test
 * Acessa listagem, cria novo lançamento, valida lançamento criado, edita lançamento e valida edição
 */
public class Fluxo2Test extends Fluxo1Test {
    @Test()
    public void editaLancamentoTest() {
        this.editarLancamento(this.lancamento);
    }
}
