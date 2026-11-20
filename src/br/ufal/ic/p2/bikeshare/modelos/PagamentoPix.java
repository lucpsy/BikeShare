package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.ChavePixNulaException;

/**
 * Metodo de pagamento via Pix.
 */
public class PagamentoPix extends MetodoPagamento {

    private static final long serialVersionUID = 1L;

    private final String chavePix;

    public PagamentoPix(String chavePix) throws ChavePixNulaException {
        if (chavePix == null || chavePix.trim().isEmpty()) {
            throw new ChavePixNulaException();
        }
        this.chavePix = chavePix;
    }

    @Override
    public String getTipo() {
        return TipoMetodoPagamento.PIX.getCodigo();
    }

    @Override
    public String getChavePix() {
        return chavePix;
    }
}
