package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoBicicletaNulaException;

/**
 * Bicicleta convencional, sem bateria e sem adicional tarifario por minuto.
 */
public class BicicletaConvencional extends Bicicleta {

    private static final long serialVersionUID = 1L;

    /**
     * Cria uma bicicleta convencional com o identificador informado.
     *
     * @param id identificador unico da bicicleta
     * @throws IdentificacaoBicicletaNulaException se o identificador for nulo ou em branco
     */
    public BicicletaConvencional(String id) throws IdentificacaoBicicletaNulaException {
        super(id);
    }

    @Override
    public TipoBicicleta getTipo() {
        return TipoBicicleta.CONVENCIONAL;
    }
}
