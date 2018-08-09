package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lancamento {
    private long id;

    private String descricao;

    private BigDecimal valor;

    private LocalDateTime dataLancamento;

    private TipoLancamento tipoLancamento = TipoLancamento.SAIDA;

    private Categoria categoria;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return df.format(this.valor);
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public String getDataLancamentoFormatado() {
        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataLancamento.format(formatoDataLancamento);
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Descrição: " + this.descricao
                + "\nValor: R$ " + this.getValorFormatado()
                + "\nData: " + this.getDataLancamentoFormatado()
                + "\nCategoria: " + this.categoria.getNome()
                + "\nTipo: " + this.tipoLancamento.getDescricao();
    }
}
