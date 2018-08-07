package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 * Classe Fluxo3Test
 * Acessar listagem, criar novo lançamento, validar lançamento criado, editar
 * lançamento, validar edição, remover lançamento e validar remoção
 */
public class Fluxo3Test extends Fluxo2Test {
    @Test
    public void excluiLancamentoTest() {
        this.excluirLancamento(this.lancamento);
    }
}
