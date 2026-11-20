package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNulaException;

import java.math.BigDecimal;

/**
 * Plano de tarifacao por assinatura, com valor de mensalidade nao-negativo.
 */
public class PlanoAssinante extends Plano {

    private static final long serialVersionUID = 1L;
    public static final BigDecimal MENSALIDADE_PADRAO = new BigDecimal("30.00");

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

    /**
     * Cria um plano assinante com a mensalidade padrao do sistema.
     *
     * @return instancia de {@link PlanoAssinante} com mensalidade padrao
     * @throws MensalidadeNulaException     nunca esperado para a constante padrao
     * @throws MensalidadeNegativaException nunca esperado para a constante padrao
     */
    public static PlanoAssinante padrao() throws MensalidadeNulaException, MensalidadeNegativaException {
        return new PlanoAssinante(MENSALIDADE_PADRAO);
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
