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
     * Inicializa um metodo de pagamento.
     */
    protected MetodoPagamento() {
    }

    /**
     * Retorna o tipo textual do metodo de pagamento.
     *
     * @return nome do tipo do metodo de pagamento
     */
    public abstract String getTipo();

    /**
     * Retorna o numero do cartao, quando o metodo de pagamento for cartao.
     *
     * @return numero do cartao cadastrado
     * @throws UsuarioNaoUsaCartaoException se o metodo nao for cartao
     */
    public String getNumeroCartao() throws UsuarioNaoUsaCartaoException {
        throw new UsuarioNaoUsaCartaoException();
    }

    /**
     * Retorna a validade do cartao, quando o metodo de pagamento for cartao.
     *
     * @return validade do cartao no formato {@code MM/AAAA}
     * @throws UsuarioNaoUsaCartaoException se o metodo nao for cartao
     */
    public String getValidadeCartao() throws UsuarioNaoUsaCartaoException {
        throw new UsuarioNaoUsaCartaoException();
    }

    /**
     * Retorna a chave Pix cadastrada, quando o metodo de pagamento for Pix.
     *
     * @return chave Pix cadastrada
     * @throws UsuarioNaoUsaPixException se o metodo nao for Pix
     */
    public String getChavePix() throws UsuarioNaoUsaPixException {
        throw new UsuarioNaoUsaPixException();
    }

    /**
     * Retorna o saldo disponivel, quando o metodo de pagamento for saldo pre-carregado.
     *
     * @return saldo atual em {@link BigDecimal}
     * @throws UsuarioNaoUsaSaldoException se o metodo nao for saldo pre-carregado
     */
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

    /**
     * Verifica se o metodo de pagamento autoriza a retirada de uma bicicleta.
     *
     * @throws BikeShareException se o pagamento nao estiver apto para autorizar a retirada
     */
    public void validarAutorizacaoRetirada() throws BikeShareException {
    }

    /**
     * Efetua a cobranca do valor de uma corrida neste metodo de pagamento.
     *
     * @param valor valor monetario a cobrar
     * @throws BikeShareException se nao for possivel concluir a cobranca
     */
    public void cobrar(BigDecimal valor) throws BikeShareException {
    }
}
