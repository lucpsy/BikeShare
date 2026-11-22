package br.ufal.ic.p2.bikeshare.persistencia;

import br.ufal.ic.p2.bikeshare.modelos.Aluguel;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio responsavel pelo armazenamento e consulta de alugueis em memoria,
 * mantendo a geracao sequencial de identificadores.
 */
public class RepositorioAlugueis implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PREFIXO_ID = "A";

    private final Map<String, Aluguel> alugueisPorId;
    private int proximoNumeroId;

    /**
     * Inicializa o repositorio vazio com contador sequencial a partir de 1.
     */
    public RepositorioAlugueis() {
        this.alugueisPorId = new LinkedHashMap<>();
        this.proximoNumeroId = 1;
    }

    /**
     * Retorna o proximo identificador de aluguel disponivel sem consumir a sequencia.
     *
     * @return identificador no formato {@code A1}, {@code A2}, etc.
     */
    public String proximoIdDisponivel() {
        return PREFIXO_ID + proximoNumeroId;
    }

    /**
     * Persiste um novo aluguel em memoria e avanca o contador sequencial de identificadores.
     *
     * @param aluguel aluguel valido a registrar
     */
    public void salvar(Aluguel aluguel) {
        alugueisPorId.put(aluguel.getId(), aluguel);
        proximoNumeroId++;
    }

    /**
     * Busca um aluguel pelo seu identificador exato.
     *
     * @param id identificador do aluguel
     * @return {@link Optional} contendo o aluguel encontrado ou vazio
     */
    public Optional<Aluguel> buscarPorId(String id) {
        return Optional.ofNullable(alugueisPorId.get(id));
    }

    /**
     * Remove todos os alugueis cadastrados e reinicia o contador de identificadores.
     */
    public void limpar() {
        alugueisPorId.clear();
        proximoNumeroId = 1;
    }
}
