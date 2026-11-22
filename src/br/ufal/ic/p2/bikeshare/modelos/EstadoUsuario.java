package br.ufal.ic.p2.bikeshare.modelos;

/**
 * Estados possiveis de um usuario no sistema BikeShare.
 */
public enum EstadoUsuario {

    /** Usuario regular autorizado a retirar bicicletas. */
    ATIVO("ativo"),

    /** Usuario com pendencias financeiras e bloqueado para novas retiradas. */
    INADIMPLENTE("inadimplente");

    private final String codigo;

    EstadoUsuario(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o codigo textual do estado do usuario.
     *
     * @return codigo do estado
     */
    public String getCodigo() {
        return codigo;
    }
}
