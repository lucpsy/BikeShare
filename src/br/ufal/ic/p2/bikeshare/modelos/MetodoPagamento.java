package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
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

    /**
     * Valida se este metodo de pagamento permite operacoes com saldo pre-carregado.
     *
     * @throws UsuarioNaoUsaSaldoException se o metodo nao for saldo pre-carregado
     */
    public void validarUsoDeSaldo() throws UsuarioNaoUsaSaldoException {
        throw new UsuarioNaoUsaSaldoException();
    }

    /**
     * Adiciona credito ao saldo pre-carregado, quando aplicavel.
     *
     * @param valor valor positivo a creditar
     * @throws BikeShareException se o metodo nao usar saldo ou o valor for invalido
     */
    public void adicionarSaldo(BigDecimal valor) throws BikeShareException {
        throw new UsuarioNaoUsaSaldoException();
    }
}
