package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando ocorre falha ao carregar, gravar ou apagar o arquivo de persistencia do sistema.
 */
public class PersistenciaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao de persistencia com mensagem descritiva e causa original.
     *
     * @param mensagem descricao do erro de persistencia
     * @param causa    excecao subjacente que originou a falha
     */
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
