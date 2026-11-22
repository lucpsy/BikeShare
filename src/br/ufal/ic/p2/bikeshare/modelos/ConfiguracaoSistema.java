package br.ufal.ic.p2.bikeshare.modelos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Agrupa os parametros configuraveis de negocio e tarifacao do sistema BikeShare.
 */
public class ConfiguracaoSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private final BigDecimal tarifaMinutoAvulso;
    private final long franquiaMinutosAssinante;
    private final BigDecimal tarifaMinutoExcedenteAssinante;
    private final BigDecimal mensalidadePadraoAssinante;
    private final BigDecimal adicionalMinutoEletrica;
    private final int nivelMinimoBateriaEletrica;
    private final long prazoCarenciaMinutos;
    private final long tempoMaximoAluguelMinutos;
    private final BigDecimal valorPenalidadeAtraso;

    /**
     * Cria uma configuracao com os valores padrao do sistema.
     */
    public ConfiguracaoSistema() {
        this(
                PlanoAvulso.TARIFA_MINUTO_PADRAO,
                PlanoAssinante.FRANQUIA_MINUTOS_PADRAO,
                PlanoAssinante.TARIFA_MINUTO_EXCEDENTE_PADRAO,
                PlanoAssinante.MENSALIDADE_PADRAO,
                BicicletaEletrica.ADICIONAL_MINUTO_PADRAO,
                BicicletaEletrica.NIVEL_MINIMO_DISPONIBILIDADE_PADRAO,
                2L,
                1440L,
                new BigDecimal("10.00")
        );
    }

    /**
     * Cria uma configuracao personalizada com todos os parametros de negocio.
     *
     * @param tarifaMinutoAvulso            valor por minuto no plano avulso
     * @param franquiaMinutosAssinante      franquia gratuita em minutos no plano assinante
     * @param tarifaMinutoExcedenteAssinante valor por minuto excedente no plano assinante
     * @param mensalidadePadraoAssinante    mensalidade padrao ao aderir ao plano assinante
     * @param adicionalMinutoEletrica       adicional por minuto para bicicleta eletrica
     * @param nivelMinimoBateriaEletrica    percentual minimo de bateria para retirada
     * @param prazoCarenciaMinutos          prazo maximo em minutos para cancelamento na mesma estacao
     * @param tempoMaximoAluguelMinutos     duracao maxima em minutos antes de expirar o aluguel
     * @param valorPenalidadeAtraso         valor da penalidade automatica por atraso
     */
    public ConfiguracaoSistema(BigDecimal tarifaMinutoAvulso,
            long franquiaMinutosAssinante,
            BigDecimal tarifaMinutoExcedenteAssinante,
            BigDecimal mensalidadePadraoAssinante,
            BigDecimal adicionalMinutoEletrica,
            int nivelMinimoBateriaEletrica,
            long prazoCarenciaMinutos,
            long tempoMaximoAluguelMinutos,
            BigDecimal valorPenalidadeAtraso) {
        this.tarifaMinutoAvulso = tarifaMinutoAvulso;
        this.franquiaMinutosAssinante = franquiaMinutosAssinante;
        this.tarifaMinutoExcedenteAssinante = tarifaMinutoExcedenteAssinante;
        this.mensalidadePadraoAssinante = mensalidadePadraoAssinante;
        this.adicionalMinutoEletrica = adicionalMinutoEletrica;
        this.nivelMinimoBateriaEletrica = nivelMinimoBateriaEletrica;
        this.prazoCarenciaMinutos = prazoCarenciaMinutos;
        this.tempoMaximoAluguelMinutos = tempoMaximoAluguelMinutos;
        this.valorPenalidadeAtraso = valorPenalidadeAtraso;
    }

    /**
     * Retorna o valor por minuto do plano avulso.
     *
     * @return tarifa por minuto avulsa
     */
    public BigDecimal getTarifaMinutoAvulso() {
        return tarifaMinutoAvulso;
    }

    /**
     * Retorna a franquia gratuita de minutos do plano assinante.
     *
     * @return franquia em minutos
     */
    public long getFranquiaMinutosAssinante() {
        return franquiaMinutosAssinante;
    }

    /**
     * Retorna o valor cobrado por minuto excedente no plano assinante.
     *
     * @return tarifa de minuto excedente
     */
    public BigDecimal getTarifaMinutoExcedenteAssinante() {
        return tarifaMinutoExcedenteAssinante;
    }

    /**
     * Retorna a mensalidade padrao do plano assinante.
     *
     * @return valor da mensalidade padrao
     */
    public BigDecimal getMensalidadePadraoAssinante() {
        return mensalidadePadraoAssinante;
    }

    /**
     * Retorna o adicional cobrado por minuto para bicicletas eletricas.
     *
     * @return adicional por minuto da bicicleta eletrica
     */
    public BigDecimal getAdicionalMinutoEletrica() {
        return adicionalMinutoEletrica;
    }

    /**
     * Retorna o nivel minimo de bateria necessario para retirada de bicicleta eletrica.
     *
     * @return percentual minimo de bateria
     */
    public int getNivelMinimoBateriaEletrica() {
        return nivelMinimoBateriaEletrica;
    }

    /**
     * Retorna o prazo de carencia em minutos para cancelamento sem cobranca na mesma estacao.
     *
     * @return prazo de carencia em minutos
     */
    public long getPrazoCarenciaMinutos() {
        return prazoCarenciaMinutos;
    }

    /**
     * Retorna a duracao maxima permitida de um aluguel em minutos antes da expiracao.
     *
     * @return duracao maxima em minutos
     */
    public long getTempoMaximoAluguelMinutos() {
        return tempoMaximoAluguelMinutos;
    }

    /**
     * Retorna o valor fixo da penalidade aplicada automaticamente em alugueis expirados.
     *
     * @return valor da penalidade por atraso
     */
    public BigDecimal getValorPenalidadeAtraso() {
        return valorPenalidadeAtraso;
    }
}
