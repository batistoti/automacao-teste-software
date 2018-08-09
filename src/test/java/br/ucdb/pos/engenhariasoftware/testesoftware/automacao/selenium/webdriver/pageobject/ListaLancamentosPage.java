package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListaLancamentosPage {

    private WebDriver driver;

    public ListaLancamentosPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void acessa() {
        driver.get("http://localhost:8080/lancamentos");
    }

    /**
     * Método para realizar uma busca usando o input correspodente
     *
     * @param descricaoLancamento descrição de lançamento a ser buscada
     */
    public void busca(final String descricaoLancamento) {
        WebElement descricao = driver.findElement(By.id("itemBusca"));
        descricao.click();
        descricao.sendKeys(descricaoLancamento);
        driver.findElement(By.id("bth-search")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"divTabelaLancamentos\"]")));
    }

    /**
     * Método para clicar no botão "Recarregar"
     */
    public void recarregar() {
        driver.findElement(By.id("recarregar")).click();
    }

    /**
     * Método para clicar no botão "Novo"
     */
    public void novoLancamento() {
        driver.findElement(By.id("novoLancamento")).click();
    }

    /**
     * Método que clica no botão "Editar" da primeira linha da tabela
     */
    public void editaLancamento() {
        driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[1]/td[6]/div/a[contains(@href,'editar')]")).click();
    }

    /**
     * Método que clica no botão "Excluir" da primeira linha da tabela
     */
    public void excluiLancamento() {
        driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[1]/td[6]/div/a[contains(@href,'remover')]")).click();
    }

    /**
     * Método para clicar no botão de Relatórios
     */
    public void acessarRelatorios() {
        driver.findElement(By.xpath("//*[@id=\"form-busca\"]/div[1]/div[2]/div[4]/a")).click();
    }

    /**
     * Método que busca o total de entrada que está no rodapé da tabela na página atual
     *
     * @return valor encontrado
     */
    public BigDecimal getTotalEntradaRodape() {
        return this.convertStringToBigDecimal(driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tfoot/tr[2]/th/span")).getText());
    }

    /**
     * Método que busca o total de saída que está no rodapé da tabela na página atual
     *
     * @return valor encontrado
     */
    public BigDecimal getTotalSaidaRodape() {
        return this.convertStringToBigDecimal(driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tfoot/tr[1]/th/span")).getText());
    }

    /**
     * Método que calcula o total por tipo de valor na página atual
     *
     * @param tipo tipo de lançamento (entrada ou saída)
     * @return total por tipo
     */
    public BigDecimal getTotalPorTipoTabela(TipoLancamento tipo) {
        List<WebElement> valores = driver.findElements(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr/td[4]"));
        BigDecimal total = BigDecimal.ZERO;
        int i = 1;
        for (WebElement valor : valores) {
            String valorLinha = valor.getText();
            String tipoLinha = driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[" + i + "]/td[5]")).getText();
            if (tipoLinha.equals(tipo.getDescricao())) {
                total = total.add(this.convertStringToBigDecimal(valorLinha));
            }
            i++;
        }
        return total;
    }


    /**
     * Método que verifica se existe o lançamento na página
     *
     * @param lancamento objeto da classe Lancamento
     * @return
     */
    public boolean existeLancamento(Lancamento lancamento) {
        String lancamentos = driver.findElement(By.id("tabelaLancamentos")).getText();
        return (lancamentos.contains(lancamento.getDescricao()) &&
                lancamentos.contains(lancamento.getValorFormatado()) &&
                lancamentos.contains(lancamento.getDataLancamentoFormatado()) &&
                lancamentos.contains(lancamento.getTipoLancamento().getDescricao()) &&
                lancamentos.contains(lancamento.getCategoria().getNome())
        );
    }

    /**
     * Método usado para converter um número de string para BigDecimal
     *
     * @param numberString número em string
     * @return
     */
    private BigDecimal convertStringToBigDecimal(String numberString) {
        if (numberString == null || numberString.equals("") || numberString.trim().replace("R$", "").equals("")) {
            return null;
        }
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            String pattern = "#,##0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            return new BigDecimal(decimalFormat.parse(numberString.replace("R$ ", "")).toString());
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

}

