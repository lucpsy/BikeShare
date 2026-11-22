package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.ValorNaoPositivoException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorNuloException;

import java.math.BigDecimal;

/**
 * Penalidade aplicada automaticamente quando um aluguel ultrapassa o tempo maximo permitido.
 */
public class PenalidadeAtraso extends Penalidade {

    private static final long serialVersionUID = 1L;
    private static final String TIPO = "atraso";
    private static final String MOTIVO_PADRAO = "Tempo maximo de aluguel excedido.";

    /**
     * Cria uma penalidade de atraso com o valor configurado.
     *
     * @param valor valor monetario da penalidade de atraso
     * @throws ValorNuloException        se o valor for nulo
     * @throws ValorNaoPositivoException se o valor for menor ou igual a zero
     */
    public PenalidadeAtraso(BigDecimal valor) throws ValorNuloException, ValorNaoPositivoException {
        super(valor, MOTIVO_PADRAO);
    }

    @Override
    public String getTipo() {
        return TIPO;
    }
}
