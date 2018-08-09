package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 * Classe Fluxo1Test
 * Acessa listagem, criar novo lançamento e validar lançamento criado
 */
public class Fluxo1Test extends LancamentoTest {
    @Test
    public void criaLancamentoTest() {
        this.criaLancamento();
    }
}
