package br.ufal.ic.p2.bikeshare.modelos;

/**
 * Estados possiveis de uma bicicleta no sistema BikeShare.
 */
public enum EstadoBicicleta {

    /** Bicicleta estacionada e livre de defeitos. */
    DISPONIVEL("disponivel"),

    /** Bicicleta retirada em um aluguel em andamento. */
    EM_USO("emUso"),

    /** Bicicleta estacionada e bloqueada para manutencao. */
    EM_MANUTENCAO("emManutencao");

    private final String codigo;

    EstadoBicicleta(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o codigo textual do estado da bicicleta.
     *
     * @return codigo do estado
     */
    public String getCodigo() {
        return codigo;
    }
}
