package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.MetodoPagamentoNaoCadastradoException;

import java.math.BigDecimal;

/**
 * Representa a ausencia de metodo de pagamento cadastrado para o usuario (Null Object).
 */
public class SemMetodoPagamento extends MetodoPagamento {

    private static final long serialVersionUID = 1L;
    private static final String TIPO = "nenhum";

    /**
     * Cria a representacao de ausencia de metodo de pagamento.
     */
    public SemMetodoPagamento() {
    }

    @Override
    public String getTipo() {
        return TIPO;
    }

    @Override
    public void validarAutorizacaoRetirada() throws MetodoPagamentoNaoCadastradoException {
        throw new MetodoPagamentoNaoCadastradoException();
    }

    @Override
    public void cobrar(BigDecimal valor) throws MetodoPagamentoNaoCadastradoException {
        throw new MetodoPagamentoNaoCadastradoException();
    }
}
