package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.ChavePixNulaException;

/**
 * Metodo de pagamento via Pix.
 */
public class PagamentoPix extends MetodoPagamento {

    private static final long serialVersionUID = 1L;

    private final String chavePix;

    /**
     * Cria um metodo de pagamento via Pix com a chave informada.
     *
     * @param chavePix chave Pix do usuario
     * @throws ChavePixNulaException se a chave Pix for nula ou em branco
     */
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
