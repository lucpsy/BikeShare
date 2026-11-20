package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoBicicletaNulaException;

/**
 * Bicicleta convencional (sem bateria).
 */
public class BicicletaConvencional extends Bicicleta {

    private static final long serialVersionUID = 1L;

    public BicicletaConvencional(String id) throws IdentificacaoBicicletaNulaException {
        super(id);
    }

    @Override
    public TipoBicicleta getTipo() {
        return TipoBicicleta.CONVENCIONAL;
    }
}
