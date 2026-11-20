package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.modelos.Estacao;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioEstacoes;

/**
 * Servico responsavel pelos casos de uso de cadastro e consulta de disponibilidade de estacoes.
 */
public class ServicoEstacao {

    private final ServicoSistema servicoSistema;

    public ServicoEstacao(ServicoSistema servicoSistema) {
        this.servicoSistema = servicoSistema;
    }

    /**
     * Cadastra uma nova estacao e retorna seu identificador sequencial.
     */
    public String criarEstacao(String nome, String endereco, String capacidadeTexto) throws BikeShareException {
        servicoSistema.garantirAtivo();
        ValidadorEntrada.validarNome(nome);
        ValidadorEntrada.validarEndereco(endereco);
        int capacidade = ValidadorEntrada.parsearCapacidade(capacidadeTexto);

        String id = repositorio().proximoIdDisponivel();
        Estacao estacao = new Estacao(id, nome, endereco, capacidade);
        repositorio().salvar(estacao);
        return id;
    }

    /**
     * Consulta o numero de bicicletas disponiveis para retirada na estacao informada.
     */
    public String getBicicletasDisponiveis(String idEstacao) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Estacao estacao = buscarEstacaoObrigatoria(idEstacao);
        return String.valueOf(estacao.getBicicletasDisponiveis());
    }

    /**
     * Consulta o numero de vagas fisicas livres na estacao informada.
     */
    public String getVagasDisponiveis(String idEstacao) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Estacao estacao = buscarEstacaoObrigatoria(idEstacao);
        return String.valueOf(estacao.getVagasDisponiveis());
    }

    private Estacao buscarEstacaoObrigatoria(String idEstacao) throws BikeShareException {
        ValidadorEntrada.validarIdentificacaoEstacao(idEstacao);
        return repositorio().buscarPorId(idEstacao)
                .orElseThrow(EstacaoNaoExisteException::new);
    }

    private RepositorioEstacoes repositorio() {
        return servicoSistema.getEstado().getRepositorioEstacoes();
    }
}
