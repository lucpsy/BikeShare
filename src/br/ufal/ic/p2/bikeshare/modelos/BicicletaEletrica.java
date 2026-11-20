package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoBicicletaNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.NivelBateriaInvalidoException;

/**
 * Bicicleta eletrica, com nivel de bateria entre 0 e 100 e limite minimo configuravel para retirada.
 */
public class BicicletaEletrica extends Bicicleta {

    private static final long serialVersionUID = 1L;
    public static final int BATERIA_MINIMA = 0;
    public static final int BATERIA_MAXIMA = 100;
    public static final int NIVEL_MINIMO_DISPONIBILIDADE_PADRAO = 15;

    private final int nivelBateria;
    private final int nivelMinimoDisponibilidade;

    public BicicletaEletrica(String id, int nivelBateria)
            throws IdentificacaoBicicletaNulaException, NivelBateriaInvalidoException {
        this(id, nivelBateria, NIVEL_MINIMO_DISPONIBILIDADE_PADRAO);
    }

    public BicicletaEletrica(String id, int nivelBateria, int nivelMinimoDisponibilidade)
            throws IdentificacaoBicicletaNulaException, NivelBateriaInvalidoException {
        super(id);
        if (nivelBateria < BATERIA_MINIMA || nivelBateria > BATERIA_MAXIMA) {
            throw new NivelBateriaInvalidoException();
        }
        this.nivelBateria = nivelBateria;
        this.nivelMinimoDisponibilidade = nivelMinimoDisponibilidade;
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
    public boolean estaDisponivelParaRetirada() {
        return super.estaDisponivelParaRetirada() && nivelBateria >= nivelMinimoDisponibilidade;
    }
}
