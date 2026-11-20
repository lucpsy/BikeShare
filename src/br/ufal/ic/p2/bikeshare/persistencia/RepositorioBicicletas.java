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

    public RepositorioBicicletas() {
        this.bicicletasPorId = new LinkedHashMap<>();
    }

    public boolean existe(String id) {
        return bicicletasPorId.containsKey(id);
    }

    public void salvar(Bicicleta bicicleta) {
        bicicletasPorId.put(bicicleta.getId(), bicicleta);
    }

    public Optional<Bicicleta> buscarPorId(String id) {
        return Optional.ofNullable(bicicletasPorId.get(id));
    }

    public void remover(String id) {
        bicicletasPorId.remove(id);
    }

    public void limpar() {
        bicicletasPorId.clear();
    }
}
