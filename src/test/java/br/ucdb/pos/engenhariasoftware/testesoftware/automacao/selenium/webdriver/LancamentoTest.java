package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.*;
import org.mockito.cglib.core.Local;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LancamentoTest {

    protected WebDriver driver;
    protected ListaLancamentosPage listaLancamentosPage;
    protected LancamentoPage lancamentoPage;
    protected Lancamento lancamento;

    @BeforeClass
    protected void inicializa() {
        boolean windows = System.getProperty("os.name").toUpperCase().contains("WIN");
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("user.dir") + "/src/test/resources/drivers/" +
                        "/geckodriver" + (windows ? ".exe" : ""));
        driver = new FirefoxDriver();
        listaLancamentosPage = new ListaLancamentosPage(driver);
        lancamentoPage = new LancamentoPage(driver);
        lancamento = new LancamentoBuilder().random().build();
    }

    /**
     * Método que cria e valida a criação de um lançamento
     */
    public void criaLancamento() {
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();
        lancamentoPage.cria(lancamento);
        listaLancamentosPage.busca(lancamento.getDescricao());
        assertTrue(listaLancamentosPage.existeLancamento(lancamento), "Falha no teste de criação de lançamento.");
    }

    /**
     * Método que edita e valida a edição de um lançamento
     */
    public void editarLancamento() {
        listaLancamentosPage.acessa();
        listaLancamentosPage.busca(lancamento.getDescricao());
        listaLancamentosPage.editaLancamento();
        lancamento.setDescricao(lancamentoPage.edita());
        listaLancamentosPage.busca(lancamento.getDescricao());
        assertTrue(listaLancamentosPage.existeLancamento(lancamento), "Falha no teste de edição de lançamento.");
    }

    /**
     * Método que exclui e valida a exclusão de um lançamento
     */
    public void excluirLancamento() {
        listaLancamentosPage.acessa();
        listaLancamentosPage.busca(lancamento.getDescricao());
        listaLancamentosPage.excluiLancamento();
        listaLancamentosPage.busca(lancamento.getDescricao());
        assertFalse(listaLancamentosPage.existeLancamento(lancamento), "Falha no teste de exclusão de lançamento.");
    }

    /**
     * Método que valida a obrigatoriedade dos campos do formulário estão sendo mostradas na tela de cadastro
     */
    public void validarCamposObrigatorios() {
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();
        lancamentoPage.salvar();
        assertEquals(lancamentoPage.getQtdeMensagensCamposObrigatorios(), lancamentoPage.getQtdeInputs(), "Erro de validação de campos obrigatórios.");

    }

    /**
     * Método que valida o total de entrada da página atual, verificando o total de entrada nas linhas da tabela com o total informado no rodapé
     */
    public void validarTotalEntrada() {
        listaLancamentosPage.acessa();
        assertEquals(listaLancamentosPage.getTotalPorTipoTabela(TipoLancamento.ENTRADA), listaLancamentosPage.getTotalEntradaRodape(), "Falha no teste para validar total de entrada.");

    }


    /**
     * Método que valida o total de saída da página atual, verificando o total de saída nas linhas da tabela com o total informado no rodapé
     */
    public void validarTotalSaida() {
        listaLancamentosPage.acessa();
        assertEquals(listaLancamentosPage.getTotalPorTipoTabela(TipoLancamento.SAIDA), listaLancamentosPage.getTotalSaidaRodape(), "Falha no teste para validar total de saída");
    }

    /**
     * Método que valida o acesso a página de relatórios
     */
    public void acessarRelatorios() {
        listaLancamentosPage.acessa();
        listaLancamentosPage.acessarRelatorios();
        assertTrue(this.existeTituloPagina("Dashboard"), "Falha no teste para acessar página de relatórios.");
    }

    /**
     * Método que verificar se o título informado existe na página atual
     *
     * @param titulo titulo a ser verificado
     * @return
     */
    public boolean existeTituloPagina(String titulo) {
        return driver.findElement(By.xpath("//div[contains(@class, 'panel-heading')]")).getText().contains(titulo);
    }

    @AfterClass
    protected void finaliza() {
        driver.quit();
    }

    /**
     * Classe LancamentoBuilder
     * Responsável por construir um objeto da classe Lancamento
     */
    static class LancamentoBuilder {
        private Lancamento lancamento;

        LancamentoBuilder() {
            lancamento = new Lancamento();
        }

        LancamentoBuilder comId(Long id) {
            lancamento.setId(id);
            return this;
        }

        LancamentoBuilder comData(LocalDateTime data) {
            lancamento.setDataLancamento(data);
            return this;
        }

        LancamentoBuilder comDescricao(String descricao) {
            lancamento.setDescricao(descricao);
            return this;
        }

        LancamentoBuilder comValor(double valor) {
            lancamento.setValor(BigDecimal.valueOf(valor));
            return this;
        }

        LancamentoBuilder comTipo(TipoLancamento tipo) {
            lancamento.setTipoLancamento(tipo);
            return this;
        }

        LancamentoBuilder comCategoria(Categoria categoria) {
            lancamento.setCategoria(categoria);
            return this;
        }

        /**
         * Método que devolve um valor aleatório de uma classe Enum Java
         *
         * @param classe
         * @param <T>
         * @return
         */
        private <T extends Enum<?>> T randomEnum(Class<T> classe) {
            final SecureRandom random = new SecureRandom();
            int x = random.nextInt(classe.getEnumConstants().length);
            return classe.getEnumConstants()[x];
        }

        /**
         * Método que gera o objeto Lancamento de forma aleatória
         *
         * @return
         */
        LancamentoBuilder random() {
            Random rand = new Random();
            return comData(LocalDateTime.now())
                    .comTipo(randomEnum(TipoLancamento.class))
                    .comDescricao("Lançamento automatizado " + (new Timestamp(System.currentTimeMillis())))
                    .comValor(rand.nextInt(100_000) / 100.0)
                    .comCategoria(randomEnum(Categoria.class));
        }

        Lancamento build() {
            System.out.println("=============================================================================");
            System.out.println("Lançamento aleatório criado:\n" + lancamento.toString());
            System.out.println("=============================================================================");
            return lancamento;
        }
    }


}


