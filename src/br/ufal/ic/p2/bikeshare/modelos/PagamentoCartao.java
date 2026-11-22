package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.NumeroCartaoNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.ValidadeCartaoNulaException;

/**
 * Metodo de pagamento por cartao de credito ou debito.
 */
public class PagamentoCartao extends MetodoPagamento {

    private static final long serialVersionUID = 1L;

    private final String numero;
    private final String validade;

    /**
     * Cria um metodo de pagamento por cartao com numero e validade informados.
     *
     * @param numero   numero do cartao
     * @param validade data de validade no formato {@code MM/AAAA}
     * @throws NumeroCartaoNuloException   se o numero do cartao for nulo ou em branco
     * @throws ValidadeCartaoNulaException se a validade do cartao for nula ou em branco
     */
    public PagamentoCartao(String numero, String validade)
            throws NumeroCartaoNuloException, ValidadeCartaoNulaException {
        if (numero == null || numero.trim().isEmpty()) {
            throw new NumeroCartaoNuloException();
        }
        if (validade == null || validade.trim().isEmpty()) {
            throw new ValidadeCartaoNulaException();
        }
        this.numero = numero;
        this.validade = validade;
    }

    @Override
    public String getTipo() {
        return TipoMetodoPagamento.CARTAO.getCodigo();
    }

    @Override
    public String getNumeroCartao() {
        return numero;
    }

    @Override
    public String getValidadeCartao() {
        return validade;
    }
}
