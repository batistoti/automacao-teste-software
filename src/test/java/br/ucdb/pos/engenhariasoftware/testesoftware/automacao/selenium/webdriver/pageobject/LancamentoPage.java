package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
     *
     * @return
     */
    public int getQtdeInputs() {
        List<WebElement> inputs = driver.findElements(By.xpath("//input[contains(@type,'text')]"));
        List<WebElement> selects = driver.findElements(By.xpath("//select"));
        return inputs.size() + selects.size();
    }

    /**
     * Método que calcula a quantidade de mensagens de campos obrigatórios encontrados
     *
     * @return
     */
    public int getQtdeMensagensCamposObrigatorios() {
        List<WebElement> msgCampos = driver.findElements(By.xpath("//div[contains(@class,'alert-danger')]/div/span"));
        return msgCampos.size();
    }

    /**
     * Método que clica no botão salvar da tela de lançamento
     */
    public void salvar() {
        driver.findElement(By.id("btnSalvar")).click();
    }

    /**
     * Método que clica no botão cancelar da tela de lançamento
     */
    public void cancela() {
        driver.findElement(By.id("cancelar")).click();
    }

    /**
     * Método que realiza a alteração da descrição do lançamento
     *
     * @return
     */
    public String edita() {
        WebElement descricao = driver.findElement(By.id("descricao"));
        String id = driver.findElement(By.id("id")).getAttribute("value");
        String descricaoAntiga = descricao.getAttribute("value");
        String novaDescricao = "editado <" + id + "> " + descricaoAntiga;
        descricao.click();
        descricao.clear();
        descricao.sendKeys(novaDescricao);
        this.salvar();
        return novaDescricao;
    }


    /**
     * Método que realiza a criação de um novo lançamento
     *
     * @param lancamento
     */
    public void cria(Lancamento lancamento) {
        if (lancamento.getTipoLancamento() == TipoLancamento.SAIDA) {
            driver.findElement(By.id("tipoLancamento2")).click(); // informa lançamento: SAÍDA
        } else {
            driver.findElement(By.id("tipoLancamento1")).click(); // informa lançamento: ENTRADA
        }
        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.sendKeys(lancamento.getDescricao());
        driver.findElement(By.name("dataLancamento")).sendKeys(lancamento.getDataLancamentoFormatado());
        descricao.click();
        driver.findElement(By.id("valor")).sendKeys(lancamento.getValorFormatado());
        new Select(driver.findElement(By.id("categoria"))).selectByVisibleText(lancamento.getCategoria().getNome());
        this.salvar();
    }
}


