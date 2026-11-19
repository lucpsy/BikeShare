package br.ufal.ic.p2.bikeshare.modelos;

/**
 * Estados possiveis de um usuario no sistema BikeShare.
 */
public enum EstadoUsuario {

    ATIVO("ativo");

    private final String codigo;

    EstadoUsuario(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
