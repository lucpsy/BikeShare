package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Atributo nao pode ser alterado.}.
 */
public class AtributoNaoPodeSerAlteradoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Atributo nao pode ser alterado."}.
     */
    public AtributoNaoPodeSerAlteradoException() {
        super("Atributo nao pode ser alterado.");
    }
}
