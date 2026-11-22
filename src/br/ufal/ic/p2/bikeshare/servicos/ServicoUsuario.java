package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.AtributoNaoPodeSerAlteradoException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioComAluguelEmAndamentoException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorMetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.modelos.ConfiguracaoSistema;
import br.ufal.ic.p2.bikeshare.modelos.PagamentoCartao;
import br.ufal.ic.p2.bikeshare.modelos.PagamentoPix;
import br.ufal.ic.p2.bikeshare.modelos.PessoaFisica;
import br.ufal.ic.p2.bikeshare.modelos.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.modelos.Plano;
import br.ufal.ic.p2.bikeshare.modelos.PlanoAssinante;
import br.ufal.ic.p2.bikeshare.modelos.PlanoAvulso;
import br.ufal.ic.p2.bikeshare.modelos.TipoMetodoPagamento;
import br.ufal.ic.p2.bikeshare.modelos.TipoPlano;
import br.ufal.ic.p2.bikeshare.modelos.TipoUsuario;
import br.ufal.ic.p2.bikeshare.modelos.Usuario;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioUsuarios;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Servico responsavel pelos casos de uso de cadastro, consulta, alteracao e remocao de usuarios.
 */
public class ServicoUsuario {

    private static final String ATRIBUTO_METODO_PAGAMENTO = "metodoPagamento";

    private final ServicoSistema servicoSistema;

    /**
     * Cria o servico de usuarios vinculado ao gerenciador do ciclo de vida do sistema.
     *
     * @param servicoSistema servico que controla o estado e persistencia do sistema
     */
    public ServicoUsuario(ServicoSistema servicoSistema) {
        this.servicoSistema = servicoSistema;
    }

    /**
     * Cadastra um usuario pessoa fisica no plano avulso padrao.
     *
     * @param nome  nome do usuario
     * @param email endereco de email unico
     * @param senha senha de acesso
     * @param tipo  codigo textual do tipo de usuario ({@code pessoaFisica})
     * @param cpf   CPF da pessoa fisica
     * @return identificador sequencial gerado para o usuario
     * @throws BikeShareException se algum dado for invalido ou o email ja estiver cadastrado
     */
    public String criarPessoaFisica(String nome, String email, String senha, String tipo, String cpf)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_FISICA);
        ValidadorEntrada.validarCpf(cpf);
        return registrarPessoaFisica(nome, email, senha, cpf, criarPlanoAvulso());
    }

    /**
     * Cadastra um usuario pessoa fisica com plano e mensalidade informados explicitamente.
     *
     * @param nome        nome do usuario
     * @param email       endereco de email unico
     * @param senha       senha de acesso
     * @param tipo        codigo textual do tipo de usuario ({@code pessoaFisica})
     * @param cpf         CPF da pessoa fisica
     * @param plano       nome do plano escolhido
     * @param mensalidade mensalidade informada para o plano
     * @return identificador sequencial gerado para o usuario
     * @throws BikeShareException se algum dado for invalido ou o email ja estiver cadastrado
     */
    public String criarPessoaFisica(String nome, String email, String senha, String tipo, String cpf,
            String plano, String mensalidade) throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_FISICA);
        ValidadorEntrada.validarCpf(cpf);
        Plano planoCriado = ValidadorEntrada.criarPlano(plano, mensalidade, configuracao());
        return registrarPessoaFisica(nome, email, senha, cpf, planoCriado);
    }

    /**
     * Cadastra um usuario pessoa juridica no plano avulso padrao.
     *
     * @param nome        nome do usuario
     * @param email       endereco de email unico
     * @param senha       senha de acesso
     * @param tipo        codigo textual do tipo de usuario ({@code pessoaJuridica})
     * @param cnpj        CNPJ da pessoa juridica
     * @param razaoSocial razao social da empresa
     * @return identificador sequencial gerado para o usuario
     * @throws BikeShareException se algum dado for invalido ou o email ja estiver cadastrado
     */
    public String criarPessoaJuridica(String nome, String email, String senha, String tipo,
            String cnpj, String razaoSocial) throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_JURIDICA);
        ValidadorEntrada.validarCnpj(cnpj);
        ValidadorEntrada.validarRazaoSocial(razaoSocial);
        return registrarPessoaJuridica(nome, email, senha, cnpj, razaoSocial, criarPlanoAvulso());
    }

    /**
     * Cadastra um usuario pessoa juridica com plano e mensalidade informados explicitamente.
     *
     * @param nome        nome do usuario
     * @param email       endereco de email unico
     * @param senha       senha de acesso
     * @param tipo        codigo textual do tipo de usuario ({@code pessoaJuridica})
     * @param cnpj        CNPJ da pessoa juridica
     * @param razaoSocial razao social da empresa
     * @param plano       nome do plano escolhido
     * @param mensalidade mensalidade informada para o plano
     * @return identificador sequencial gerado para o usuario
     * @throws BikeShareException se algum dado for invalido ou o email ja estiver cadastrado
     */
    public String criarPessoaJuridica(String nome, String email, String senha, String tipo,
            String cnpj, String razaoSocial, String plano, String mensalidade)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_JURIDICA);
        ValidadorEntrada.validarCnpj(cnpj);
        ValidadorEntrada.validarRazaoSocial(razaoSocial);
        Plano planoCriado = ValidadorEntrada.criarPlano(plano, mensalidade, configuracao());
        return registrarPessoaJuridica(nome, email, senha, cnpj, razaoSocial, planoCriado);
    }

    /**
     * Consulta o valor de um atributo especifico de um usuario cadastrado.
     *
     * @param idUsuario identificador do usuario
     * @param atributo  nome exato do atributo solicitado
     * @return representacao textual do atributo
     * @throws BikeShareException se o usuario ou atributo for invalido
     */
    public String getAtributoUsuario(String idUsuario, String atributo) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Usuario usuario = buscarUsuarioObrigatorio(idUsuario);
        return usuario.getAtributo(atributo);
    }

    /**
     * Busca o identificador de um usuario cadastrado a partir do seu email.
     *
     * @param email endereco de email do usuario
     * @return identificador do usuario correspondente
     * @throws BikeShareException se o email for nulo ou nao corresponder a nenhum usuario
     */
    public String getUsuarioPorEmail(String email) throws BikeShareException {
        servicoSistema.garantirAtivo();
        ValidadorEntrada.validarEmailObrigatorio(email);
        Usuario usuario = repositorio().buscarPorEmail(email)
                .orElseThrow(UsuarioNaoExisteException::new);
        return usuario.getId();
    }

    /**
     * Altera um atributo simples do usuario (nome, email, senha, plano, saldo ou metodo saldoPreCarregado).
     *
     * @param idUsuario identificador do usuario
     * @param atributo  nome do atributo a alterar
     * @param valor     novo valor do atributo
     * @throws BikeShareException se o usuario, atributo ou valor for invalido
     */
    public void alterarUsuario(String idUsuario, String atributo, String valor) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Usuario usuario = buscarUsuarioObrigatorio(idUsuario);
        if (atributo == null) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        switch (atributo) {
            case "nome":
                ValidadorEntrada.validarNome(valor);
                usuario.alterarNome(valor);
                break;
            case "email":
                alterarEmail(usuario, valor);
                break;
            case "senha":
                ValidadorEntrada.validarSenha(valor);
                usuario.alterarSenha(valor);
                break;
            case "plano":
                alterarPlano(usuario, valor);
                break;
            case ATRIBUTO_METODO_PAGAMENTO:
                ativarSaldoPreCarregado(usuario, valor);
                break;
            case "saldo":
                recarregarSaldo(usuario, valor);
                break;
            default:
                throw new AtributoNaoPodeSerAlteradoException();
        }
    }

    /**
     * Altera o metodo de pagamento do usuario para Pix com a chave informada.
     *
     * @param idUsuario identificador do usuario
     * @param atributo  deve ser {@code metodoPagamento}
     * @param valor1    deve ser {@code pix}
     * @param chave     chave Pix do usuario
     * @throws BikeShareException se algum parametro for invalido
     */
    public void alterarUsuario(String idUsuario, String atributo, String valor1, String chave)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        Usuario usuario = buscarUsuarioObrigatorio(idUsuario);
        if (!ATRIBUTO_METODO_PAGAMENTO.equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        TipoMetodoPagamento tipo = TipoMetodoPagamento.deCodigo(valor1);
        if (tipo != TipoMetodoPagamento.PIX) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        ValidadorEntrada.validarChavePix(chave);
        usuario.definirMetodoPagamento(new PagamentoPix(chave));
    }

    /**
     * Altera o metodo de pagamento do usuario para cartao com numero e validade informados.
     *
     * @param idUsuario identificador do usuario
     * @param atributo  deve ser {@code metodoPagamento}
     * @param valor1    deve ser {@code cartao}
     * @param numero    numero do cartao
     * @param validade  validade no formato {@code MM/AAAA}
     * @throws BikeShareException se algum parametro for invalido
     */
    public void alterarUsuario(String idUsuario, String atributo, String valor1, String numero, String validade)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        Usuario usuario = buscarUsuarioObrigatorio(idUsuario);
        if (!ATRIBUTO_METODO_PAGAMENTO.equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        TipoMetodoPagamento tipo = TipoMetodoPagamento.deCodigo(valor1);
        if (tipo != TipoMetodoPagamento.CARTAO) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        ValidadorEntrada.validarNumeroCartao(numero);
        ValidadorEntrada.validarValidadeCartao(validade);
        usuario.definirMetodoPagamento(new PagamentoCartao(numero, validade));
    }

    /**
     * Remove um usuario cadastrado, desde que nao possua aluguel em andamento.
     *
     * @param idUsuario identificador do usuario a remover
     * @throws BikeShareException se o usuario nao existir ou possuir aluguel ativo
     */
    public void removerUsuario(String idUsuario) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Usuario usuario = buscarUsuarioObrigatorio(idUsuario);
        if (usuario.possuiAluguelAtivo()) {
            throw new UsuarioComAluguelEmAndamentoException();
        }
        repositorio().remover(usuario);
    }

    private void alterarEmail(Usuario usuario, String novoEmail) throws BikeShareException {
        ValidadorEntrada.validarEmail(novoEmail);
        Optional<Usuario> existente = repositorio().buscarPorEmail(novoEmail);
        if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
            throw new EmailJaCadastradoException();
        }
        String emailAnterior = usuario.getEmail();
        usuario.alterarEmail(novoEmail);
        repositorio().atualizarEmail(emailAnterior, usuario);
    }

    private void alterarPlano(Usuario usuario, String novoPlanoTexto) throws BikeShareException {
        TipoPlano tipoPlano = TipoPlano.deCodigo(novoPlanoTexto);
        if (usuario.getPlano().getNome().equals(tipoPlano.getCodigo())) {
            return;
        }
        switch (tipoPlano) {
            case AVULSO:
                usuario.alterarPlano(criarPlanoAvulso());
                break;
            case ASSINANTE:
                usuario.alterarPlano(criarPlanoAssinantePadrao());
                break;
            default:
                throw new AtributoNaoPodeSerAlteradoException();
        }
    }

    private PlanoAvulso criarPlanoAvulso() {
        return new PlanoAvulso(configuracao().getTarifaMinutoAvulso());
    }

    private PlanoAssinante criarPlanoAssinantePadrao() throws BikeShareException {
        return new PlanoAssinante(
                configuracao().getMensalidadePadraoAssinante(),
                configuracao().getFranquiaMinutosAssinante(),
                configuracao().getTarifaMinutoExcedenteAssinante()
        );
    }

    private void ativarSaldoPreCarregado(Usuario usuario, String tipoTexto) throws BikeShareException {
        TipoMetodoPagamento tipo = TipoMetodoPagamento.deCodigo(tipoTexto);
        if (tipo != TipoMetodoPagamento.SALDO_PRE_CARREGADO) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        usuario.ativarSaldoPreCarregado();
    }

    private void recarregarSaldo(Usuario usuario, String valorTexto) throws BikeShareException {
        usuario.validarUsoDeSaldo();
        BigDecimal valor = ValidadorEntrada.parsearValorPositivo(valorTexto);
        usuario.recarregarSaldo(valor);
    }

    private Usuario buscarUsuarioObrigatorio(String idUsuario) throws BikeShareException {
        ValidadorEntrada.validarIdentificacaoUsuario(idUsuario);
        return repositorio().buscarPorId(idUsuario)
                .orElseThrow(UsuarioNaoExisteException::new);
    }

    private void validarDadosComuns(String nome, String email, String senha, String tipo, TipoUsuario tipoEsperado)
            throws BikeShareException {
        ValidadorEntrada.validarNome(nome);
        ValidadorEntrada.validarEmail(email);
        ValidadorEntrada.validarSenha(senha);
        ValidadorEntrada.validarTipoEsperado(tipo, tipoEsperado);
    }

    private String registrarPessoaFisica(String nome, String email, String senha, String cpf, Plano plano)
            throws BikeShareException {
        validarEmailDisponivel(email);
        String id = repositorio().proximoIdDisponivel();
        Usuario usuario = new PessoaFisica(id, nome, email, senha, cpf, plano);
        repositorio().salvar(usuario);
        return id;
    }

    private String registrarPessoaJuridica(String nome, String email, String senha,
            String cnpj, String razaoSocial, Plano plano) throws BikeShareException {
        validarEmailDisponivel(email);
        String id = repositorio().proximoIdDisponivel();
        Usuario usuario = new PessoaJuridica(id, nome, email, senha, cnpj, razaoSocial, plano);
        repositorio().salvar(usuario);
        return id;
    }

    private void validarEmailDisponivel(String email) throws EmailJaCadastradoException {
        if (repositorio().existeEmail(email)) {
            throw new EmailJaCadastradoException();
        }
    }

    private ConfiguracaoSistema configuracao() {
        return servicoSistema.getEstado().getConfiguracao();
    }

    private RepositorioUsuarios repositorio() {
        return servicoSistema.getEstado().getRepositorioUsuarios();
    }
}
