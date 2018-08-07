package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LancamentoPage {

    private WebDriver driver;

    public LancamentoPage(final WebDriver driver) {
        this.driver = driver;

    }

    /**
     * Método que calcula a quantidade de inputs que estão visíveis no formulário
     * @return
     */
    public int getQtdeInputs() {
        List<WebElement> campos = driver.findElements(By.xpath("//input[contains(@type,'text')]"));
        return campos.size();
    }

    /**
     * Método que calcula a quantidade de mensagens de campos obrigatórios encontrados
     * @return
     */
    public int getQtdeMensagensCamposObrigatorios() {
        List<WebElement> msgCampos = driver.findElements(By.xpath("//div[contains(@class,'alert-danger')]/div/span"));
        return msgCampos.size();
    }

    public void salvar() {
        driver.findElement(By.id("btnSalvar")).click();
    }

    public void cancela() {
        driver.findElement(By.id("cancelar")).click();
    }

    public String edita() {
        WebElement descricao = driver.findElement(By.id("descricao"));
        String id = driver.findElement(By.id("id")).getAttribute("value");
        String descricaoAntiga = descricao.getAttribute("value");
        String novaDescricao = "editado <" + id + "> " + descricaoAntiga;
        descricao.click();
        descricao.clear();
        descricao.sendKeys(novaDescricao);
        driver.findElement(By.id("btnSalvar")).click();
        return novaDescricao;
    }

    public void cria(Lancamento lancamento) {

        if (lancamento.getTipoLancamento() == TipoLancamento.SAIDA) {
            driver.findElement(By.id("tipoLancamento2")).click(); // informa lançamento: SAÍDA
        } else {
            driver.findElement(By.id("tipoLancamento1")).click(); // informa lançamento: ENTRADA
        }

        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.sendKeys(lancamento.getDescricao());
        WebElement dataLancamento = driver.findElement(By.name("dataLancamento"));
        dataLancamento.sendKeys(lancamento.getDataLancamentoFormatado());
        WebElement valor = driver.findElement(By.id("valor"));
        descricao.click();
        valor.sendKeys(lancamento.getValorFormatado());
        this.salvar();
    }
}


