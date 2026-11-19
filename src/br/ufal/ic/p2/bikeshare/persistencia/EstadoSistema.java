package br.ufal.ic.p2.bikeshare.persistencia;

import java.io.Serializable;

/**
 * Agrupa o estado persistivel completo do sistema BikeShare.
 */
public class EstadoSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RepositorioUsuarios repositorioUsuarios;

    public EstadoSistema() {
        this.repositorioUsuarios = new RepositorioUsuarios();
    }

    public RepositorioUsuarios getRepositorioUsuarios() {
        return repositorioUsuarios;
    }

    /**
     * Limpa todos os repositorios mantidos pelo estado do sistema.
     */
    public void limpar() {
        repositorioUsuarios.limpar();
    }
}
