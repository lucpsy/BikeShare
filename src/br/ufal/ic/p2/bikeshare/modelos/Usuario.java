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
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoUsaSaldoException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorMetodoPagamentoInvalidoException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade base que representa um usuario cadastrado no sistema BikeShare.
 */
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEM_ALUGUEL_ATIVO = "nenhum";

    private final String id;
    private String nome;
    private String email;
    private String senha;
    private EstadoUsuario estado;
    private Plano plano;
    private MetodoPagamento metodoPagamento;
    private final PagamentoSaldoPreCarregado carteiraSaldo;
    private String aluguelAtivo;
    private final List<Penalidade> penalidades;

    /**
     * Inicializa os atributos comuns de um usuario no estado ativo e sem metodo de pagamento.
     *
     * @param id    identificador unico do usuario
     * @param nome  nome do usuario
     * @param email endereco de email do usuario
     * @param senha senha de acesso
     * @param plano plano de tarifacao contratado
     * @throws BikeShareException se algum campo obrigatorio for nulo ou vazio
     */
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
        this.carteiraSaldo = new PagamentoSaldoPreCarregado();
        this.aluguelAtivo = null;
        this.penalidades = new ArrayList<>();
    }

    /**
     * Verifica se uma string e nula ou composta apenas por espacos em branco.
     *
     * @param valor texto a verificar
     * @return {@code true} se o texto for nulo ou em branco
     */
    protected static boolean estaEmBranco(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    /**
     * Retorna o identificador unico do usuario.
     *
     * @return identificador do usuario
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o nome do usuario.
     *
     * @return nome do usuario
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o email do usuario.
     *
     * @return endereco de email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retorna a senha do usuario.
     *
     * @return senha cadastrada
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Retorna o estado atual do usuario.
     *
     * @return estado do usuario
     */
    public EstadoUsuario getEstado() {
        return estado;
    }

    /**
     * Indica se o usuario esta inadimplente.
     *
     * @return {@code true} se o estado atual for {@link EstadoUsuario#INADIMPLENTE}
     */
    public boolean estaInadimplente() {
        return estado == EstadoUsuario.INADIMPLENTE;
    }

    /**
     * Retorna o plano de tarifacao vigente do usuario.
     *
     * @return plano vigente
     */
    public Plano getPlano() {
        return plano;
    }

    /**
     * Retorna o metodo de pagamento atualmente ativo para o usuario.
     *
     * @return metodo de pagamento ativo
     */
    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    /**
     * Calcula o total acumulado de pendencias provenientes de penalidades aplicadas ao usuario.
     *
     * @return soma monetaria das penalidades
     */
    public BigDecimal getTotalPendencias() {
        BigDecimal total = BigDecimal.ZERO;
        for (Penalidade penalidade : penalidades) {
            total = total.add(penalidade.getValor());
        }
        return total;
    }

    /**
     * Retorna o identificador do aluguel ativo ou {@code "nenhum"} caso nao exista.
     *
     * @return identificador do aluguel ativo formatado
     */
    public String getAluguelAtivoFormatado() {
        return aluguelAtivo == null ? SEM_ALUGUEL_ATIVO : aluguelAtivo;
    }

    /**
     * Indica se o usuario possui um aluguel em andamento.
     *
     * @return {@code true} se houver aluguel ativo
     */
    public boolean possuiAluguelAtivo() {
        return aluguelAtivo != null;
    }

    /**
     * Registra o identificador do aluguel atualmente em andamento para este usuario.
     *
     * @param idAluguel identificador do aluguel iniciado
     */
    public void iniciarAluguel(String idAluguel) {
        this.aluguelAtivo = idAluguel;
    }

    /**
     * Limpa a referencia de aluguel em andamento apos sua conclusao, cancelamento ou expiracao.
     */
    public void encerrarAluguelAtivo() {
        this.aluguelAtivo = null;
    }

    /**
     * Aplica uma penalidade ao usuario e altera seu estado para inadimplente.
     *
     * @param penalidade penalidade financeira a registrar
     */
    public void aplicarPenalidade(Penalidade penalidade) {
        this.penalidades.add(penalidade);
        this.estado = EstadoUsuario.INADIMPLENTE;
    }

    /**
     * Altera o nome do usuario.
     *
     * @param novoNome novo nome nao vazio
     * @throws NomeNuloException se o novo nome for nulo ou em branco
     */
    public void alterarNome(String novoNome) throws NomeNuloException {
        if (estaEmBranco(novoNome)) {
            throw new NomeNuloException();
        }
        this.nome = novoNome;
    }

    /**
     * Altera o email do usuario.
     *
     * @param novoEmail novo email nao vazio
     * @throws EmailNuloException se o novo email for nulo ou em branco
     */
    public void alterarEmail(String novoEmail) throws EmailNuloException {
        if (estaEmBranco(novoEmail)) {
            throw new EmailNuloException();
        }
        this.email = novoEmail;
    }

    /**
     * Altera a senha do usuario.
     *
     * @param novaSenha nova senha nao vazia
     * @throws SenhaNulaException se a nova senha for nula ou em branco
     */
    public void alterarSenha(String novaSenha) throws SenhaNulaException {
        if (estaEmBranco(novaSenha)) {
            throw new SenhaNulaException();
        }
        this.senha = novaSenha;
    }

    /**
     * Altera o plano de tarifacao do usuario.
     *
     * @param novoPlano novo plano valido
     * @throws PlanoInvalidoException se o plano for nulo
     */
    public void alterarPlano(Plano novoPlano) throws PlanoInvalidoException {
        if (novoPlano == null) {
            throw new PlanoInvalidoException();
        }
        this.plano = novoPlano;
    }

    /**
     * Define um novo metodo de pagamento ativo para o usuario.
     *
     * @param novoMetodo instancia concreta do metodo de pagamento
     * @throws ValorMetodoPagamentoInvalidoException se o metodo informado for nulo
     */
    public void definirMetodoPagamento(MetodoPagamento novoMetodo) throws ValorMetodoPagamentoInvalidoException {
        if (novoMetodo == null) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        this.metodoPagamento = novoMetodo;
    }

    /**
     * Ativa o saldo pre-carregado como metodo de pagamento atual, preservando eventual saldo acumulado.
     */
    public void ativarSaldoPreCarregado() {
        this.metodoPagamento = this.carteiraSaldo;
    }

    /**
     * Verifica se o usuario utiliza atualmente saldo pre-carregado.
     *
     * @throws UsuarioNaoUsaSaldoException se o metodo ativo nao for saldo pre-carregado
     */
    public void validarUsoDeSaldo() throws UsuarioNaoUsaSaldoException {
        this.metodoPagamento.validarUsoDeSaldo();
    }

    /**
     * Adiciona credito ao saldo pre-carregado do usuario.
     *
     * @param valor valor positivo a creditar
     * @throws BikeShareException se o usuario nao usar saldo ou o valor for invalido
     */
    public void recarregarSaldo(BigDecimal valor) throws BikeShareException {
        this.metodoPagamento.adicionarSaldo(valor);
    }

    /**
     * Valida se o metodo de pagamento do usuario autoriza a retirada de bicicleta.
     *
     * @throws BikeShareException se nao houver metodo cadastrado ou saldo suficiente
     */
    public void validarAutorizacaoRetirada() throws BikeShareException {
        this.metodoPagamento.validarAutorizacaoRetirada();
    }

    /**
     * Efetua a cobranca de uma corrida utilizando o metodo de pagamento ativo do usuario.
     *
     * @param valor valor monetario a cobrar
     * @throws BikeShareException se a cobranca nao puder ser concluida
     */
    public void cobrar(BigDecimal valor) throws BikeShareException {
        this.metodoPagamento.cobrar(valor);
    }

    /**
     * Retorna o tipo concreto do usuario.
     *
     * @return tipo de usuario
     */
    public abstract TipoUsuario getTipo();

    /**
     * Retorna o CPF do usuario, quando for pessoa fisica.
     *
     * @return numero do CPF
     * @throws UsuarioNaoEhPessoaFisicaException se o usuario nao for pessoa fisica
     */
    public String getCpf() throws UsuarioNaoEhPessoaFisicaException {
        throw new UsuarioNaoEhPessoaFisicaException();
    }

    /**
     * Retorna o CNPJ do usuario, quando for pessoa juridica.
     *
     * @return numero do CNPJ
     * @throws UsuarioNaoEhPessoaJuridicaException se o usuario nao for pessoa juridica
     */
    public String getCnpj() throws UsuarioNaoEhPessoaJuridicaException {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    /**
     * Retorna a razao social do usuario, quando for pessoa juridica.
     *
     * @return razao social
     * @throws UsuarioNaoEhPessoaJuridicaException se o usuario nao for pessoa juridica
     */
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
