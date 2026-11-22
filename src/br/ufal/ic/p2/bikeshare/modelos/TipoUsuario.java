package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.TipoUsuarioInvalidoException;

/**
 * Tipos de usuario suportados pelo sistema BikeShare.
 */
public enum TipoUsuario {

    /** Usuario pessoa fisica identificado por CPF. */
    PESSOA_FISICA("pessoaFisica"),

    /** Usuario pessoa juridica identificado por CNPJ e razao social. */
    PESSOA_JURIDICA("pessoaJuridica");

    private final String codigo;

    TipoUsuario(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o codigo textual do tipo de usuario.
     *
     * @return codigo do tipo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Converte o codigo textual exato (sensivel a maiusculas/minusculas) em um {@link TipoUsuario}.
     *
     * @param codigo representacao textual do tipo
     * @return constante correspondente do enum
     * @throws TipoUsuarioInvalidoException se o codigo for nulo ou desconhecido
     */
    public static TipoUsuario deCodigo(String codigo) throws TipoUsuarioInvalidoException {
        if (codigo == null) {
            throw new TipoUsuarioInvalidoException();
        }
        for (TipoUsuario tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new TipoUsuarioInvalidoException();
    }
}
