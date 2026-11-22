package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.PlanoNaoPossuiMensalidadeException;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Representa um plano de tarifacao vinculado a um usuario.
 */
public abstract class Plano implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Inicializa um plano de tarifacao.
     */
    protected Plano() {
    }

    /**
     * Retorna o nome identificador do plano.
     *
     * @return nome do plano
     */
    public abstract String getNome();

    /**
     * Calcula a tarifa base de uma corrida com base em sua duracao em minutos.
     *
     * @param duracaoMinutos duracao total da corrida em minutos
     * @return valor monetario calculado pelo plano
     */
    public abstract BigDecimal calcularTarifaBase(long duracaoMinutos);

    /**
     * Retorna o valor da mensalidade do plano, quando aplicavel.
     *
     * @return valor da mensalidade
     * @throws PlanoNaoPossuiMensalidadeException se o plano nao possuir mensalidade
     */
    public BigDecimal getMensalidade() throws PlanoNaoPossuiMensalidadeException {
        throw new PlanoNaoPossuiMensalidadeException();
    }
}
