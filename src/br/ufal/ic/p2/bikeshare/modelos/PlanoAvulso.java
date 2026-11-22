package br.ufal.ic.p2.bikeshare.modelos;

import java.math.BigDecimal;

/**
 * Plano de tarifacao avulso, sem cobranca de mensalidade fixa e tarifado por minuto de uso.
 */
public class PlanoAvulso extends Plano {

    private static final long serialVersionUID = 1L;

    /** Valor padrao cobrado por minuto no plano avulso. */
    public static final BigDecimal TARIFA_MINUTO_PADRAO = new BigDecimal("0.50");

    private final BigDecimal tarifaMinuto;

    /**
     * Cria um plano avulso com a tarifa por minuto padrao.
     */
    public PlanoAvulso() {
        this(TARIFA_MINUTO_PADRAO);
    }

    /**
     * Cria um plano avulso com tarifa por minuto configuravel.
     *
     * @param tarifaMinuto valor cobrado por minuto de corrida
     */
    public PlanoAvulso(BigDecimal tarifaMinuto) {
        this.tarifaMinuto = tarifaMinuto;
    }

    @Override
    public String getNome() {
        return TipoPlano.AVULSO.getCodigo();
    }

    @Override
    public BigDecimal calcularTarifaBase(long duracaoMinutos) {
        if (duracaoMinutos <= 0L) {
            return BigDecimal.ZERO;
        }
        return tarifaMinuto.multiply(BigDecimal.valueOf(duracaoMinutos));
    }
}
