package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoUsaPixException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoUsaSaldoException;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Representa o metodo de pagamento associado a um usuario.
 */
public abstract class MetodoPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Retorna o tipo textual do metodo de pagamento.
     *
     * @return nome do tipo do metodo de pagamento
     */
    public abstract String getTipo();

    public String getNumeroCartao() throws UsuarioNaoUsaCartaoException {
        throw new UsuarioNaoUsaCartaoException();
    }

    public String getValidadeCartao() throws UsuarioNaoUsaCartaoException {
        throw new UsuarioNaoUsaCartaoException();
    }

    public String getChavePix() throws UsuarioNaoUsaPixException {
        throw new UsuarioNaoUsaPixException();
    }

    public BigDecimal getSaldo() throws UsuarioNaoUsaSaldoException {
        throw new UsuarioNaoUsaSaldoException();
    }
}
