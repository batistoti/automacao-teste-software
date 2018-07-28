package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListaLancamentosPage {

    private WebDriver driver;

    public ListaLancamentosPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void acessa() {
        driver.get("http://localhost:8080/lancamentos");
    }

    /**
     * Método de busca criado para verificar se registro foi inserido
     *
     * @param descricaoLancamento
     */
    public void busca(final String descricaoLancamento) {
        WebElement descricao = driver.findElement(By.id("itemBusca"));
        descricao.click();
        descricao.sendKeys(descricaoLancamento);
        driver.findElement(By.id("bth-search")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr/td")));
    }

    public void novoLancamento() {
        driver.findElement(By.id("novoLancamento")).click();
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    LocalDateTime dataHora, TipoLancamento tipo) {

        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String lancamentos = driver.getPageSource();
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return (lancamentos.contains(descricaoLancamento) &&
                lancamentos.contains(df.format(valorLancamento)) &&
                lancamentos.contains(dataHora.format(formatoDataLancamento)) &&
                lancamentos.contains(tipo.getDescricao()));
    }
}

