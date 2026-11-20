package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.AtributoNaoPodeSerAlteradoException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioComAluguelEmAndamentoException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorMetodoPagamentoInvalidoException;
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

    public ServicoUsuario(ServicoSistema servicoSistema) {
        this.servicoSistema = servicoSistema;
    }

    /**
     * Cadastra um usuario pessoa fisica no plano avulso padrao.
     */
    public String criarPessoaFisica(String nome, String email, String senha, String tipo, String cpf)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_FISICA);
        ValidadorEntrada.validarCpf(cpf);
        return registrarPessoaFisica(nome, email, senha, cpf, new PlanoAvulso());
    }

    /**
     * Cadastra um usuario pessoa fisica com plano e mensalidade informados explicitamente.
     */
    public String criarPessoaFisica(String nome, String email, String senha, String tipo, String cpf,
            String plano, String mensalidade) throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_FISICA);
        ValidadorEntrada.validarCpf(cpf);
        Plano planoCriado = ValidadorEntrada.criarPlano(plano, mensalidade);
        return registrarPessoaFisica(nome, email, senha, cpf, planoCriado);
    }

    /**
     * Cadastra um usuario pessoa juridica no plano avulso padrao.
     */
    public String criarPessoaJuridica(String nome, String email, String senha, String tipo,
            String cnpj, String razaoSocial) throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_JURIDICA);
        ValidadorEntrada.validarCnpj(cnpj);
        ValidadorEntrada.validarRazaoSocial(razaoSocial);
        return registrarPessoaJuridica(nome, email, senha, cnpj, razaoSocial, new PlanoAvulso());
    }

    /**
     * Cadastra um usuario pessoa juridica com plano e mensalidade informados explicitamente.
     */
    public String criarPessoaJuridica(String nome, String email, String senha, String tipo,
            String cnpj, String razaoSocial, String plano, String mensalidade)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        validarDadosComuns(nome, email, senha, tipo, TipoUsuario.PESSOA_JURIDICA);
        ValidadorEntrada.validarCnpj(cnpj);
        ValidadorEntrada.validarRazaoSocial(razaoSocial);
        Plano planoCriado = ValidadorEntrada.criarPlano(plano, mensalidade);
        return registrarPessoaJuridica(nome, email, senha, cnpj, razaoSocial, planoCriado);
    }

    /**
     * Consulta o valor de um atributo especifico de um usuario cadastrado.
     */
    public String getAtributoUsuario(String idUsuario, String atributo) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Usuario usuario = buscarUsuarioObrigatorio(idUsuario);
        return usuario.getAtributo(atributo);
    }

    /**
     * Busca o identificador de um usuario cadastrado a partir do seu email.
     */
    public String getUsuarioPorEmail(String email) throws BikeShareException {
        servicoSistema.garantirAtivo();
        ValidadorEntrada.validarEmailObrigatorio(email);
        Usuario usuario = repositorio().buscarPorEmail(email)
                .orElseThrow(UsuarioNaoExisteException::new);
        return usuario.getId();
    }

    /**
     * Altera um atributo simples do usuario (nome, email, senha, plano, saldo ou metodo de pagamento saldoPreCarregado).
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
                usuario.alterarPlano(new PlanoAvulso());
                break;
            case ASSINANTE:
                usuario.alterarPlano(PlanoAssinante.padrao());
                break;
            default:
                throw new AtributoNaoPodeSerAlteradoException();
        }
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

    private RepositorioUsuarios repositorio() {
        return servicoSistema.getEstado().getRepositorioUsuarios();
    }
}
