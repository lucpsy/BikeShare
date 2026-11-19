package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoUsuarioNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.NomeNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoEhPessoaFisicaException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoEhPessoaJuridicaException;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidade base que representa um usuario cadastrado no sistema BikeShare.
 */
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEM_ALUGUEL_ATIVO = "nenhum";

    private final String id;
    private final String nome;
    private final String email;
    private final String senha;
    private final EstadoUsuario estado;
    private final Plano plano;
    private final MetodoPagamento metodoPagamento;
    private final String aluguelAtivo;
    private final BigDecimal totalPendencias;

    protected Usuario(String id, String nome, String email, String senha, Plano plano) throws BikeShareException {
        if (estaEmBranco(id)) {
            throw new IdentificacaoUsuarioNulaException();
        }
        if (estaEmBranco(nome)) {
            throw new NomeNuloException();
        }
        if (estaEmBranco(email)) {
            throw new EmailNuloException();
        }
        if (estaEmBranco(senha)) {
            throw new SenhaNulaException();
        }
        if (plano == null) {
            throw new PlanoInvalidoException();
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.estado = EstadoUsuario.ATIVO;
        this.plano = plano;
        this.metodoPagamento = new SemMetodoPagamento();
        this.aluguelAtivo = null;
        this.totalPendencias = BigDecimal.ZERO;
    }

    protected static boolean estaEmBranco(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public Plano getPlano() {
        return plano;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public BigDecimal getTotalPendencias() {
        return totalPendencias;
    }

    public String getAluguelAtivoFormatado() {
        return aluguelAtivo == null ? SEM_ALUGUEL_ATIVO : aluguelAtivo;
    }

    public abstract TipoUsuario getTipo();

    public String getCpf() throws UsuarioNaoEhPessoaFisicaException {
        throw new UsuarioNaoEhPessoaFisicaException();
    }

    public String getCnpj() throws UsuarioNaoEhPessoaJuridicaException {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    public String getRazaoSocial() throws UsuarioNaoEhPessoaJuridicaException {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    /**
     * Consulta o valor textual de um atributo do usuario pelo seu nome exato.
     *
     * @param atributo nome do atributo solicitado
     * @return valor formatado do atributo
     * @throws BikeShareException se o atributo nao existir ou nao se aplicar ao subtipo/plano/pagamento do usuario
     */
    public String getAtributo(String atributo) throws BikeShareException {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "nome":
                return getNome();
            case "email":
                return getEmail();
            case "senha":
                return getSenha();
            case "tipo":
                return getTipo().getCodigo();
            case "estado":
                return getEstado().getCodigo();
            case "plano":
                return getPlano().getNome();
            case "mensalidade":
                return FormatadorMonetario.formatar(getPlano().getMensalidade());
            case "cpf":
                return getCpf();
            case "cnpj":
                return getCnpj();
            case "razaoSocial":
                return getRazaoSocial();
            case "metodoPagamento":
                return getMetodoPagamento().getTipo();
            case "numeroCartao":
                return getMetodoPagamento().getNumeroCartao();
            case "validadeCartao":
                return getMetodoPagamento().getValidadeCartao();
            case "chavePix":
                return getMetodoPagamento().getChavePix();
            case "saldo":
                return FormatadorMonetario.formatar(getMetodoPagamento().getSaldo());
            case "aluguelAtivo":
                return getAluguelAtivoFormatado();
            case "totalPendencias":
                return FormatadorMonetario.formatar(getTotalPendencias());
            default:
                throw new AtributoNaoExisteException();
        }
    }
}
