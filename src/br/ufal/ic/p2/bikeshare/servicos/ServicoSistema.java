package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.SistemaEncerradoException;
import br.ufal.ic.p2.bikeshare.persistencia.EstadoSistema;
import br.ufal.ic.p2.bikeshare.persistencia.PersistenciaSistema;

/**
 * Gerencia o ciclo de vida do sistema BikeShare, incluindo inicializacao, limpeza e persistencia.
 */
public class ServicoSistema {

    private final PersistenciaSistema persistencia;
    private final EstadoSistema estado;
    private boolean encerrado;

    /**
     * Inicializa o servico carregando o estado previamente persistido em disco, se existir.
     *
     * @throws BikeShareException se ocorrer falha ao carregar os dados persistidos
     */
    public ServicoSistema() throws BikeShareException {
        this.persistencia = new PersistenciaSistema();
        this.estado = persistencia.carregar();
        this.encerrado = false;
    }

    /**
     * Retorna o estado atual do sistema em memoria.
     *
     * @return instancia de {@link EstadoSistema}
     */
    public EstadoSistema getEstado() {
        return estado;
    }

    /**
     * Garante que o sistema ainda nao foi encerrado antes de executar operacoes.
     *
     * @throws SistemaEncerradoException se {@link #encerrarSistema()} ja tiver sido chamado
     */
    public void garantirAtivo() throws SistemaEncerradoException {
        if (encerrado) {
            throw new SistemaEncerradoException();
        }
    }

    /**
     * Limpa todos os dados em memoria e remove o arquivo de persistencia em disco.
     *
     * @throws BikeShareException se o sistema estiver encerrado ou ocorrer falha ao apagar os dados
     */
    public void zerarSistema() throws BikeShareException {
        garantirAtivo();
        estado.limpar();
        persistencia.apagar();
    }

    /**
     * Persiste o estado atual do sistema em disco e encerra esta sessao.
     *
     * @throws BikeShareException se o sistema ja estiver encerrado ou ocorrer falha ao gravar os dados
     */
    public void encerrarSistema() throws BikeShareException {
        garantirAtivo();
        persistencia.salvar(estado);
        encerrado = true;
    }
}
