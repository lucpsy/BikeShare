package br.ufal.ic.p2.bikeshare.persistencia;

import br.ufal.ic.p2.bikeshare.modelos.ConfiguracaoSistema;

import java.io.Serializable;

/**
 * Agrupa o estado persistivel completo do sistema BikeShare.
 */
public class EstadoSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ConfiguracaoSistema configuracao;
    private final RepositorioUsuarios repositorioUsuarios;
    private final RepositorioEstacoes repositorioEstacoes;
    private final RepositorioBicicletas repositorioBicicletas;
    private final RepositorioAlugueis repositorioAlugueis;

    /**
     * Inicializa o estado do sistema com configuracao padrao e repositorios vazios.
     */
    public EstadoSistema() {
        this.configuracao = new ConfiguracaoSistema();
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioEstacoes = new RepositorioEstacoes();
        this.repositorioBicicletas = new RepositorioBicicletas();
        this.repositorioAlugueis = new RepositorioAlugueis();
    }

    /**
     * Retorna a configuracao de parametros de negocio do sistema.
     *
     * @return configuracao vigente do sistema
     */
    public ConfiguracaoSistema getConfiguracao() {
        return configuracao;
    }

    /**
     * Retorna o repositorio de usuarios.
     *
     * @return repositorio de usuarios
     */
    public RepositorioUsuarios getRepositorioUsuarios() {
        return repositorioUsuarios;
    }

    /**
     * Retorna o repositorio de estacoes.
     *
     * @return repositorio de estacoes
     */
    public RepositorioEstacoes getRepositorioEstacoes() {
        return repositorioEstacoes;
    }

    /**
     * Retorna o repositorio de bicicletas.
     *
     * @return repositorio de bicicletas
     */
    public RepositorioBicicletas getRepositorioBicicletas() {
        return repositorioBicicletas;
    }

    /**
     * Retorna o repositorio de alugueis.
     *
     * @return repositorio de alugueis
     */
    public RepositorioAlugueis getRepositorioAlugueis() {
        return repositorioAlugueis;
    }

    /**
     * Limpa todos os repositorios mantidos pelo estado do sistema.
     */
    public void limpar() {
        repositorioUsuarios.limpar();
        repositorioEstacoes.limpar();
        repositorioBicicletas.limpar();
        repositorioAlugueis.limpar();
    }
}
