package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 * Classe Fluxo4Test
 * Acessar listagem, criar novo lançamento, validar lançamento criado, validar
 * totais de saída e entrada e acessar relatórios
 */
public class Fluxo4Test extends Fluxo1Test {
    @Test(priority = 1)
    public void getTotalEntradaTest() {
        this.validarTotalEntrada();
    }

    @Test(priority = 2)
    public void getTotalSaidaTest() {
        this.validarTotalSaida();
    }

    @Test(priority = 3)
    void acessarRelatoriosTest() {
        this.acessarRelatorios();
    }
}
