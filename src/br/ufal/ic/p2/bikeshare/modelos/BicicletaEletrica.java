package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoBicicletaNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.NivelBateriaInsuficienteException;
import br.ufal.ic.p2.bikeshare.excecoes.NivelBateriaInvalidoException;

import java.math.BigDecimal;

/**
 * Bicicleta eletrica, com nivel de bateria entre 0 e 100, limite minimo de retirada e adicional por minuto configuraveis.
 */
public class BicicletaEletrica extends Bicicleta {

    private static final long serialVersionUID = 1L;

    /** Limite inferior valido para o percentual de bateria. */
    public static final int BATERIA_MINIMA = 0;

    /** Limite superior valido para o percentual de bateria. */
    public static final int BATERIA_MAXIMA = 100;

    /** Percentual minimo padrao de bateria para que a bicicleta eletrica possa ser retirada. */
    public static final int NIVEL_MINIMO_DISPONIBILIDADE_PADRAO = 15;

    /** Valor adicional padrao cobrado por minuto de uso em bicicletas eletricas. */
    public static final BigDecimal ADICIONAL_MINUTO_PADRAO = new BigDecimal("0.20");

    private final int nivelBateria;
    private final int nivelMinimoDisponibilidade;
    private final BigDecimal adicionalPorMinuto;

    /**
     * Cria uma bicicleta eletrica utilizando os parametros padrao de bateria minima e adicional por minuto.
     *
     * @param id           identificador unico da bicicleta
     * @param nivelBateria percentual inicial de bateria entre 0 e 100
     * @throws IdentificacaoBicicletaNulaException se o identificador for nulo ou em branco
     * @throws NivelBateriaInvalidoException       se o nivel de bateria estiver fora do intervalo [0, 100]
     */
    public BicicletaEletrica(String id, int nivelBateria)
            throws IdentificacaoBicicletaNulaException, NivelBateriaInvalidoException {
        this(id, nivelBateria, NIVEL_MINIMO_DISPONIBILIDADE_PADRAO, ADICIONAL_MINUTO_PADRAO);
    }

    /**
     * Cria uma bicicleta eletrica com parametros configuraveis de bateria minima e adicional tarifario.
     *
     * @param id                         identificador unico da bicicleta
     * @param nivelBateria               percentual inicial de bateria entre 0 e 100
     * @param nivelMinimoDisponibilidade percentual minimo exigido para retirada
     * @param adicionalPorMinuto         valor adicional cobrado por minuto de corrida
     * @throws IdentificacaoBicicletaNulaException se o identificador for nulo ou em branco
     * @throws NivelBateriaInvalidoException       se o nivel de bateria estiver fora do intervalo [0, 100]
     */
    public BicicletaEletrica(String id, int nivelBateria, int nivelMinimoDisponibilidade,
            BigDecimal adicionalPorMinuto)
            throws IdentificacaoBicicletaNulaException, NivelBateriaInvalidoException {
        super(id);
        if (nivelBateria < BATERIA_MINIMA || nivelBateria > BATERIA_MAXIMA) {
            throw new NivelBateriaInvalidoException();
        }
        this.nivelBateria = nivelBateria;
        this.nivelMinimoDisponibilidade = nivelMinimoDisponibilidade;
        this.adicionalPorMinuto = adicionalPorMinuto;
    }

    @Override
    public TipoBicicleta getTipo() {
        return TipoBicicleta.ELETRICA;
    }

    @Override
    public int getNivelBateria() {
        return nivelBateria;
    }

    @Override
    public BigDecimal calcularAdicional(long duracaoMinutos) {
        if (duracaoMinutos <= 0L) {
            return BigDecimal.ZERO;
        }
        return adicionalPorMinuto.multiply(BigDecimal.valueOf(duracaoMinutos));
    }

    @Override
    public boolean estaDisponivelParaRetirada() {
        return super.estaDisponivelParaRetirada() && nivelBateria >= nivelMinimoDisponibilidade;
    }

    @Override
    public void validarAptidaoParaRetirada() throws BikeShareException {
        super.validarAptidaoParaRetirada();
        if (nivelBateria < nivelMinimoDisponibilidade) {
            throw new NivelBateriaInsuficienteException();
        }
    }
}
