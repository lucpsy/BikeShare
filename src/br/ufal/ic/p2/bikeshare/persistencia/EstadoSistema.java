package br.ufal.ic.p2.bikeshare.persistencia;

import java.io.Serializable;

/**
 * Agrupa o estado persistivel completo do sistema BikeShare.
 */
public class EstadoSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RepositorioUsuarios repositorioUsuarios;
    private final RepositorioEstacoes repositorioEstacoes;
    private final RepositorioBicicletas repositorioBicicletas;

    public EstadoSistema() {
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioEstacoes = new RepositorioEstacoes();
        this.repositorioBicicletas = new RepositorioBicicletas();
    }

    public RepositorioUsuarios getRepositorioUsuarios() {
        return repositorioUsuarios;
    }

    public RepositorioEstacoes getRepositorioEstacoes() {
        return repositorioEstacoes;
    }

    public RepositorioBicicletas getRepositorioBicicletas() {
        return repositorioBicicletas;
    }

    /**
     * Limpa todos os repositorios mantidos pelo estado do sistema.
     */
    public void limpar() {
        repositorioUsuarios.limpar();
        repositorioEstacoes.limpar();
        repositorioBicicletas.limpar();
    }
}
