package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.TipoBicicletaInvalidoException;

/**
 * Tipos de bicicleta suportados pelo sistema BikeShare.
 */
public enum TipoBicicleta {

    CONVENCIONAL("convencional"),
    ELETRICA("eletrica");

    private final String codigo;

    TipoBicicleta(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    /**
     * Converte o codigo textual exato (sensivel a maiusculas/minusculas) em um {@link TipoBicicleta}.
     *
     * @param codigo representacao textual do tipo de bicicleta
     * @return constante correspondente do enum
     * @throws TipoBicicletaInvalidoException se o codigo for nulo ou desconhecido
     */
    public static TipoBicicleta deCodigo(String codigo) throws TipoBicicletaInvalidoException {
        if (codigo == null) {
            throw new TipoBicicletaInvalidoException();
        }
        for (TipoBicicleta tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new TipoBicicletaInvalidoException();
    }
}
