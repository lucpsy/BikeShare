package br.ufal.ic.p2.bikeshare.modelos;

/**
 * Representa a ausencia de metodo de pagamento cadastrado para o usuario (Null Object).
 */
public class SemMetodoPagamento extends MetodoPagamento {

    private static final long serialVersionUID = 1L;
    private static final String TIPO = "nenhum";

    @Override
    public String getTipo() {
        return TIPO;
    }
}
