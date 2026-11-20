package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.ValorMetodoPagamentoInvalidoException;

/**
 * Tipos de metodo de pagamento suportados pelo sistema BikeShare.
 */
public enum TipoMetodoPagamento {

    CARTAO("cartao"),
    PIX("pix"),
    SALDO_PRE_CARREGADO("saldoPreCarregado");

    private final String codigo;

    TipoMetodoPagamento(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    /**
     * Converte o codigo textual exato (sensivel a maiusculas/minusculas) em um {@link TipoMetodoPagamento}.
     *
     * @param codigo representacao textual do metodo de pagamento
     * @return constante correspondente do enum
     * @throws ValorMetodoPagamentoInvalidoException se o codigo for nulo ou desconhecido
     */
    public static TipoMetodoPagamento deCodigo(String codigo) throws ValorMetodoPagamentoInvalidoException {
        if (codigo == null) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        for (TipoMetodoPagamento tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new ValorMetodoPagamentoInvalidoException();
    }
}
