package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.LancamentoPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.ListaLancamentosPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.TipoLancamento;
import org.mockito.cglib.core.Local;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertTrue;

public class LancamentoTest {

    private WebDriver driver;
    private ListaLancamentosPage listaLancamentosPage;
    private LancamentoPage lancamentoPage;

    @BeforeClass
    private void inicialliza() {
        boolean windows = System.getProperty("os.name").toUpperCase().contains("WIN");
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("user.dir") + "/src/test/resources/drivers/" +
                        "/geckodriver" + (windows ? ".exe" : ""));
        driver = new FirefoxDriver();
        listaLancamentosPage = new ListaLancamentosPage(driver);
        lancamentoPage = new LancamentoPage(driver);
    }

    @Test
    public void criaLancamento() {
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();

        LocalDateTime dataHoraGerada = this.getDataLancamento();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
        final String descricaoLancamento = "Lançando automaizado" + dataHoraGerada.format(formatoLancamento);
        final BigDecimal valor = getValorLancamento();
        TipoLancamento tipoLancamento = this.getTipoLancamento();
        lancamentoPage.cria(descricaoLancamento, valor, dataHoraGerada, tipoLancamento);
        listaLancamentosPage.busca(descricaoLancamento);
        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHoraGerada, tipoLancamento));
    }

    @AfterClass
    private void finaliza() {
        driver.quit();
    }

    /**
     * Método que devolve o tipo de Lançamento de forma aleatória
     *
     * @return
     */
    private TipoLancamento getTipoLancamento() {
        return new Random().nextBoolean() ? TipoLancamento.ENTRADA : TipoLancamento.SAIDA;
    }

    /**
     * Método que devolve a data de lançamento com o dia de forma aleatória
     * baseado no mês e ano atual
     *
     * @return
     */
    private LocalDateTime getDataLancamento() {
        LocalDateTime dataAtual = LocalDateTime.now();
        int diaGerado = ThreadLocalRandom.current().nextInt(1, 28);
        return LocalDateTime.of(dataAtual.getYear(), dataAtual.getMonth().getValue(), diaGerado, 0, 0);
    }

    /**
     * Método que
     * @return
     */
    private BigDecimal getValorLancamento() {

        boolean aplicaVariante = (System.currentTimeMillis() % 3) == 0;
        int fator = 10;
        long mim = 30;
        long max = 900;
        if (aplicaVariante) {
            mim /= fator;
            max /= fator;
        }
        return new BigDecimal((1 + (Math.random() * (max - mim)))).setScale(2, RoundingMode.HALF_DOWN);
    }

}


