package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.ValorNaoPositivoException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorNuloException;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Representa uma penalidade financeira aplicada a um usuario do sistema BikeShare.
 */
public abstract class Penalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    private final BigDecimal valor;
    private final String motivo;

    /**
     * Inicializa uma penalidade com valor estritamente positivo e motivo descritivo.
     *
     * @param valor  valor monetario da penalidade
     * @param motivo descricao do motivo da aplicacao
     * @throws ValorNuloException        se o valor for nulo
     * @throws ValorNaoPositivoException se o valor for menor ou igual a zero
     */
    protected Penalidade(BigDecimal valor, String motivo)
            throws ValorNuloException, ValorNaoPositivoException {
        if (valor == null) {
            throw new ValorNuloException();
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorNaoPositivoException();
        }
        this.valor = valor;
        this.motivo = motivo;
    }

    /**
     * Retorna o tipo textual da penalidade.
     *
     * @return codigo do tipo da penalidade
     */
    public abstract String getTipo();

    /**
     * Retorna o valor monetario da penalidade.
     *
     * @return valor da penalidade
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Retorna a descricao do motivo da penalidade.
     *
     * @return motivo da penalidade
     */
    public String getMotivo() {
        return motivo;
    }
}
