package br.ufal.ic.p2.bikeshare.persistencia;

import br.ufal.ic.p2.bikeshare.modelos.Bicicleta;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio responsavel pelo armazenamento e consulta de bicicletas em memoria.
 */
public class RepositorioBicicletas implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Bicicleta> bicicletasPorId;

    /**
     * Inicializa o repositorio de bicicletas vazio.
     */
    public RepositorioBicicletas() {
        this.bicicletasPorId = new LinkedHashMap<>();
    }

    /**
     * Verifica se ja existe bicicleta cadastrada com o identificador informado.
     *
     * @param id identificador da bicicleta
     * @return {@code true} se a bicicleta existir no repositorio
     */
    public boolean existe(String id) {
        return bicicletasPorId.containsKey(id);
    }

    /**
     * Persiste uma bicicleta em memoria.
     *
     * @param bicicleta bicicleta valida a registrar
     */
    public void salvar(Bicicleta bicicleta) {
        bicicletasPorId.put(bicicleta.getId(), bicicleta);
    }

    /**
     * Busca uma bicicleta pelo seu identificador exato.
     *
     * @param id identificador da bicicleta
     * @return {@link Optional} contendo a bicicleta encontrada ou vazio
     */
    public Optional<Bicicleta> buscarPorId(String id) {
        return Optional.ofNullable(bicicletasPorId.get(id));
    }

    /**
     * Remove uma bicicleta do repositorio pelo seu identificador.
     *
     * @param id identificador da bicicleta a remover
     */
    public void remover(String id) {
        bicicletasPorId.remove(id);
    }

    /**
     * Remove todas as bicicletas armazenadas em memoria.
     */
    public void limpar() {
        bicicletasPorId.clear();
    }
}
