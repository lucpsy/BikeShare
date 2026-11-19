package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.PlanoInvalidoException;

/**
 * Tipos padrao de plano de tarifacao suportados no cadastro de usuarios.
 */
public enum TipoPlano {

    AVULSO("avulso"),
    ASSINANTE("assinante");

    private final String codigo;

    TipoPlano(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    /**
     * Converte o codigo textual exato (sensivel a maiusculas/minusculas) em um {@link TipoPlano}.
     *
     * @param codigo representacao textual do plano
     * @return constante correspondente do enum
     * @throws PlanoInvalidoException se o codigo for nulo ou desconhecido
     */
    public static TipoPlano deCodigo(String codigo) throws PlanoInvalidoException {
        if (codigo == null) {
            throw new PlanoInvalidoException();
        }
        for (TipoPlano tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new PlanoInvalidoException();
    }
}
