package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.ValorNaoPositivoException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorNuloException;

import java.math.BigDecimal;

/**
 * Metodo de pagamento por saldo pre-carregado, mantendo o saldo acumulado em {@link BigDecimal}.
 */
public class PagamentoSaldoPreCarregado extends MetodoPagamento {

    private static final long serialVersionUID = 1L;

    private BigDecimal saldo;

    public PagamentoSaldoPreCarregado() {
        this.saldo = BigDecimal.ZERO;
    }

    @Override
    public String getTipo() {
        return TipoMetodoPagamento.SALDO_PRE_CARREGADO.getCodigo();
    }

    @Override
    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public void validarUsoDeSaldo() {
        // Metodo de pagamento e saldo pre-carregado: valido.
    }

    @Override
    public void adicionarSaldo(BigDecimal valor) throws ValorNuloException, ValorNaoPositivoException {
        if (valor == null) {
            throw new ValorNuloException();
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorNaoPositivoException();
        }
        this.saldo = this.saldo.add(valor);
    }
}
