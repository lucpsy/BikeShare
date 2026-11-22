package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.servicos.ServicoAluguel;
import br.ufal.ic.p2.bikeshare.servicos.ServicoBicicleta;
import br.ufal.ic.p2.bikeshare.servicos.ServicoEstacao;
import br.ufal.ic.p2.bikeshare.servicos.ServicoSistema;
import br.ufal.ic.p2.bikeshare.servicos.ServicoUsuario;

/**
 * Facade do sistema BikeShare, responsavel por encaminhar cada operacao para o
 * servico correspondente.
 */
public class Facade {

    private static final String AINDA_NAO_IMPLEMENTADO = "Ainda não implementado.";

    private final ServicoSistema servicoSistema;
    private final ServicoUsuario servicoUsuario;
    private final ServicoEstacao servicoEstacao;
    private final ServicoBicicleta servicoBicicleta;
    private final ServicoAluguel servicoAluguel;

    /**
     * Inicializa a fachada e todos os servicos do sistema, carregando eventual
     * estado persistido.
     *
     * @throws BikeShareException se ocorrer erro ao carregar o estado persistido
     */
    public Facade() throws BikeShareException {
        this.servicoSistema = new ServicoSistema();
        this.servicoUsuario = new ServicoUsuario(this.servicoSistema);
        this.servicoEstacao = new ServicoEstacao(this.servicoSistema);
        this.servicoBicicleta = new ServicoBicicleta(this.servicoSistema);
        this.servicoAluguel = new ServicoAluguel(this.servicoSistema);
    }

    // =====================================================================
    // US1 - Cadastro de usuario
    // =====================================================================

    public String criarUsuario(String nome, String email, String senha, String tipo, String cpfOuCnpj)
            throws BikeShareException {
        return servicoUsuario.criarPessoaFisica(nome, email, senha, tipo, cpfOuCnpj);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial)
            throws BikeShareException {
        return servicoUsuario.criarPessoaJuridica(nome, email, senha, tipo, cnpj, razaoSocial);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf,
            String plano, String mensalidade) throws BikeShareException {
        return servicoUsuario.criarPessoaFisica(nome, email, senha, tipo, cpf, plano, mensalidade);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj,
            String razaoSocial, String plano, String mensalidade) throws BikeShareException {
        return servicoUsuario.criarPessoaJuridica(nome, email, senha, tipo, cnpj, razaoSocial, plano, mensalidade);
    }

    // =====================================================================
    // US2 - Consulta de usuario
    // =====================================================================

    public String getAtributoUsuario(String usuario, String atributo) throws BikeShareException {
        return servicoUsuario.getAtributoUsuario(usuario, atributo);
    }

    public String getUsuarioPorEmail(String email) throws BikeShareException {
        return servicoUsuario.getUsuarioPorEmail(email);
    }

    // =====================================================================
    // US3 - Alteracao de usuario (inclui metodo de pagamento)
    // =====================================================================

    public void alterarUsuario(String usuario, String atributo, String valor) throws BikeShareException {
        servicoUsuario.alterarUsuario(usuario, atributo, valor);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String chave)
            throws BikeShareException {
        servicoUsuario.alterarUsuario(usuario, atributo, valor1, chave);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String numero, String validade)
            throws BikeShareException {
        servicoUsuario.alterarUsuario(usuario, atributo, valor1, numero, validade);
    }

    // =====================================================================
    // US4 - Remocao de usuario
    // =====================================================================

    public void removerUsuario(String usuario) throws BikeShareException {
        servicoUsuario.removerUsuario(usuario);
    }

    // =====================================================================
    // US5 - Cadastro de estacao
    // =====================================================================

    public String criarEstacao(String nome, String endereco, String capacidade) throws BikeShareException {
        return servicoEstacao.criarEstacao(nome, endereco, capacidade);
    }

    // =====================================================================
    // US6 - Gestao de bicicletas na estacao
    // =====================================================================

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) throws BikeShareException {
        servicoBicicleta.adicionarBicicleta(estacao, bicicleta, tipo);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria)
            throws BikeShareException {
        servicoBicicleta.adicionarBicicleta(estacao, bicicleta, tipo, nivelBateria);
    }

    public void removerBicicleta(String estacao, String bicicleta) throws BikeShareException {
        servicoBicicleta.removerBicicleta(estacao, bicicleta);
    }

    public String getAtributoBicicleta(String bicicleta, String atributo) throws BikeShareException {
        return servicoBicicleta.getAtributoBicicleta(bicicleta, atributo);
    }

    // =====================================================================
    // US7 - Consulta de disponibilidade
    // =====================================================================

    public String getBicicletasDisponiveis(String estacao) throws BikeShareException {
        return servicoEstacao.getBicicletasDisponiveis(estacao);
    }

    public String getVagasDisponiveis(String estacao) throws BikeShareException {
        return servicoEstacao.getVagasDisponiveis(estacao);
    }

    public String getEstadoBicicleta(String bicicleta) throws BikeShareException {
        return servicoBicicleta.getEstadoBicicleta(bicicleta);
    }

    // =====================================================================
    // US8 - Retirada de bicicleta
    // =====================================================================

    public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data)
            throws BikeShareException {
        return servicoAluguel.retirarBicicleta(usuario, estacao, bicicleta, data);
    }

    // =====================================================================
    // US9 - Devolucao de bicicleta
    // =====================================================================

    public void devolverBicicleta(String aluguel, String estacao, String data) throws BikeShareException {
        servicoAluguel.devolverBicicleta(aluguel, estacao, data);
    }

    // =====================================================================
    // US10 - Consulta de historico de alugueis
    // =====================================================================

    public String getHistoricoAlugueis(String usuario) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getHistoricoAlugueis(String usuario, String atributo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getAtributoAluguel(String aluguel, String atributo) throws BikeShareException {
        return servicoAluguel.getAtributoAluguel(aluguel, atributo);
    }

    // =====================================================================
    // US11/US12 - Planos e tarifacao / criacao de novos planos
    // =====================================================================

    public String criarPlano(String nome, String franquiaMinutos, String valorMinutoExcedente, String mensalidade) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US13 - Regras de bloqueio / lancamento manual de penalidade
    // =====================================================================

    public void lancaPenalidade(String usuario, String tipo, String valor, String motivo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US14 - Lancamento e conclusao de manutencao
    // =====================================================================

    public void lancaDefeito(String bicicleta, String descricao) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void concluiManutencao(String bicicleta) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US15 - Relatorios
    // =====================================================================

    public String getTotalFaturado(String dataInicial, String dataFinal) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US16 - Undo/redo
    // =====================================================================

    public void undo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void redo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // Ciclo de vida do sistema
    // =====================================================================

    public void zerarSistema() throws BikeShareException {
        servicoSistema.zerarSistema();
    }

    public void encerrarSistema() throws BikeShareException {
        servicoSistema.encerrarSistema();
    }
}
