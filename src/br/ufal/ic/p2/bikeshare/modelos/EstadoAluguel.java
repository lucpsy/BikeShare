package br.ufal.ic.p2.bikeshare.modelos;

/**
 * Estados possiveis do ciclo de vida de um aluguel de bicicleta.
 */
public enum EstadoAluguel {

    /** Aluguel iniciado e ainda sem devolucao concluida. */
    EM_ANDAMENTO("emAndamento"),

    /** Aluguel devolvido normalmente com calculo de tarifa. */
    FINALIZADO("finalizado"),

    /** Aluguel devolvido na mesma estacao dentro do prazo de carencia. */
    CANCELADO("cancelado"),

    /** Aluguel devolvido apos ultrapassar o tempo maximo permitido. */
    EXPIRADO("expirado");

    private final String codigo;

    EstadoAluguel(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o codigo textual do estado do aluguel.
     *
     * @return codigo do estado
     */
    public String getCodigo() {
        return codigo;
    }
}
