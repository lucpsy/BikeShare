package br.ufal.ic.p2.bikeshare.modelos;

/**
 * Estados possiveis de uma bicicleta no sistema BikeShare.
 */
public enum EstadoBicicleta {

    DISPONIVEL("disponivel"),
    EM_USO("emUso"),
    EM_MANUTENCAO("emManutencao");

    private final String codigo;

    EstadoBicicleta(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
