package br.ufal.ic.p2.bikeshare.persistencia;

import br.ufal.ic.p2.bikeshare.modelos.Estacao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio responsavel pelo armazenamento e consulta de estacoes em memoria,
 * mantendo o encapsulamento das colecoes internas e a geracao sequencial de identificadores.
 */
public class RepositorioEstacoes implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PREFIXO_ID = "E";

    private final Map<String, Estacao> estacoesPorId;
    private int proximoNumeroId;

    /**
     * Inicializa o repositorio de estacoes vazio com contador sequencial a partir de 1.
     */
    public RepositorioEstacoes() {
        this.estacoesPorId = new LinkedHashMap<>();
        this.proximoNumeroId = 1;
    }

    /**
     * Retorna o proximo identificador disponivel sem consumir a sequencia.
     *
     * @return identificador sequencial no formato {@code E1}, {@code E2}, etc.
     */
    public String proximoIdDisponivel() {
        return PREFIXO_ID + proximoNumeroId;
    }

    /**
     * Persiste uma nova estacao em memoria e avanca o contador sequencial de identificadores.
     *
     * @param estacao estacao valida a ser registrada
     */
    public void salvar(Estacao estacao) {
        estacoesPorId.put(estacao.getId(), estacao);
        proximoNumeroId++;
    }

    /**
     * Busca uma estacao pelo seu identificador exato.
     *
     * @param id identificador da estacao
     * @return {@link Optional} contendo a estacao encontrada ou vazio
     */
    public Optional<Estacao> buscarPorId(String id) {
        return Optional.ofNullable(estacoesPorId.get(id));
    }

    /**
     * Remove todas as estacoes cadastradas e reinicia o contador de identificadores.
     */
    public void limpar() {
        estacoesPorId.clear();
        proximoNumeroId = 1;
    }
}
