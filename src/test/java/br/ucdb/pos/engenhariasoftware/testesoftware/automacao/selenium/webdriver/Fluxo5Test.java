package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 *  Classe Fluxo5Test
 *  Acessar cadastro de lançamento, validar a exibição de mensagens de campos obrigatório
 * para todos os campos, voltar para tela de listagem com botão Cancelar e clicar no
 * botão recarregar.
 */
public class Fluxo5Test extends LancamentoTest {
    @Test
    public void validarCamposObrigatoriosTest() {
        this.validarCamposObrigatorios();
    }
}
