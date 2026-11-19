package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.modelos.PessoaFisica;
import br.ufal.ic.p2.bikeshare.modelos.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.modelos.Plano;
import br.ufal.ic.p2.bikeshare.modelos.PlanoAvulso;
import br.ufal.ic.p2.bikeshare.modelos.TipoUsuario;
import br.ufal.ic.p2.bikeshare.modelos.Usuario;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioUsuarios;

/**
 * Servico responsavel pelos casos de uso de cadastro e consulta de usuarios (US1 e US2).
 */
public class ServicoUsuario {

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
        ValidadorEntrada.validarIdentificacaoUsuario(idUsuario);
        Usuario usuario = repositorio().buscarPorId(idUsuario)
                .orElseThrow(UsuarioNaoExisteException::new);
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
