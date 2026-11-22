package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNulaException;

import java.math.BigDecimal;

/**
 * Plano de tarifacao por assinatura, com mensalidade nao-negativa, franquia gratuita e tarifa por minuto excedente.
 */
public class PlanoAssinante extends Plano {

    private static final long serialVersionUID = 1L;

    /** Mensalidade padrao aplicada quando o usuario migra para o plano assinante. */
    public static final BigDecimal MENSALIDADE_PADRAO = new BigDecimal("30.00");

    /** Franquia gratuita padrao de minutos por corrida. */
    public static final long FRANQUIA_MINUTOS_PADRAO = 60L;

    /** Valor padrao cobrado por minuto excedente apos a franquia. */
    public static final BigDecimal TARIFA_MINUTO_EXCEDENTE_PADRAO = new BigDecimal("0.30");

    private final BigDecimal mensalidade;
    private final long franquiaMinutos;
    private final BigDecimal tarifaMinutoExcedente;

    /**
     * Cria um plano assinante com a mensalidade informada e parametros padrao de franquia e excedente.
     *
     * @param mensalidade valor da mensalidade contratada
     * @throws MensalidadeNulaException     se a mensalidade for nula
     * @throws MensalidadeNegativaException se a mensalidade for negativa
     */
    public PlanoAssinante(BigDecimal mensalidade) throws MensalidadeNulaException, MensalidadeNegativaException {
        this(mensalidade, FRANQUIA_MINUTOS_PADRAO, TARIFA_MINUTO_EXCEDENTE_PADRAO);
    }

    /**
     * Cria um plano assinante com mensalidade, franquia e valor de minuto excedente configuraveis.
     *
     * @param mensalidade           valor da mensalidade contratada
     * @param franquiaMinutos       quantidade de minutos gratuitos por corrida
     * @param tarifaMinutoExcedente valor cobrado por cada minuto alem da franquia
     * @throws MensalidadeNulaException     se a mensalidade for nula
     * @throws MensalidadeNegativaException se a mensalidade for negativa
     */
    public PlanoAssinante(BigDecimal mensalidade, long franquiaMinutos, BigDecimal tarifaMinutoExcedente)
            throws MensalidadeNulaException, MensalidadeNegativaException {
        if (mensalidade == null) {
            throw new MensalidadeNulaException();
        }
        if (mensalidade.compareTo(BigDecimal.ZERO) < 0) {
            throw new MensalidadeNegativaException();
        }
        this.mensalidade = mensalidade;
        this.franquiaMinutos = franquiaMinutos;
        this.tarifaMinutoExcedente = tarifaMinutoExcedente;
    }

    /**
     * Cria um plano assinante com a mensalidade padrao do sistema.
     *
     * @return instancia de {@link PlanoAssinante} com parametros padrao
     * @throws MensalidadeNulaException     se a mensalidade padrao for nula
     * @throws MensalidadeNegativaException se a mensalidade padrao for negativa
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

    @Override
    public BigDecimal calcularTarifaBase(long duracaoMinutos) {
        long excedente = Math.max(0L, duracaoMinutos - franquiaMinutos);
        return tarifaMinutoExcedente.multiply(BigDecimal.valueOf(excedente));
    }
}
