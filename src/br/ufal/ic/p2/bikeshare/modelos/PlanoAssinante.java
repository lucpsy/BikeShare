package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNulaException;

import java.math.BigDecimal;

/**
 * Plano de tarifacao por assinatura, com valor de mensalidade nao-negativo.
 */
public class PlanoAssinante extends Plano {

    private static final long serialVersionUID = 1L;

    private final BigDecimal mensalidade;

    public PlanoAssinante(BigDecimal mensalidade) throws MensalidadeNulaException, MensalidadeNegativaException {
        if (mensalidade == null) {
            throw new MensalidadeNulaException();
        }
        if (mensalidade.compareTo(BigDecimal.ZERO) < 0) {
            throw new MensalidadeNegativaException();
        }
        this.mensalidade = mensalidade;
    }

    @Override
    public String getNome() {
        return TipoPlano.ASSINANTE.getCodigo();
    }

    @Override
    public BigDecimal getMensalidade() {
        return mensalidade;
    }
}
