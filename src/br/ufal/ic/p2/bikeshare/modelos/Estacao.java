package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoEstaNaEstacaoException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.CapacidadeNaoPositivaException;
import br.ufal.ic.p2.bikeshare.excecoes.EnderecoNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.EstacaoSemVagasException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoEstacaoNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.NomeNuloException;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa uma estacao de bicicletas compartilhadas com capacidade fixa de vagas.
 */
public class Estacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String nome;
    private final String endereco;
    private final int capacidade;
    private final Map<String, Bicicleta> bicicletasEstacionadas;

    /**
     * Cria uma nova estacao inicialmente vazia com a capacidade de vagas especificada.
     *
     * @param id         identificador unico da estacao
     * @param nome       nome da estacao
     * @param endereco   endereco da estacao
     * @param capacidade capacidade total de vagas (inteiro positivo)
     * @throws BikeShareException se algum campo obrigatorio for invalido
     */
    public Estacao(String id, String nome, String endereco, int capacidade) throws BikeShareException {
        if (estaEmBranco(id)) {
            throw new IdentificacaoEstacaoNulaException();
        }
        if (estaEmBranco(nome)) {
            throw new NomeNuloException();
        }
        if (estaEmBranco(endereco)) {
            throw new EnderecoNuloException();
        }
        if (capacidade <= 0) {
            throw new CapacidadeNaoPositivaException();
        }
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.capacidade = capacidade;
        this.bicicletasEstacionadas = new LinkedHashMap<>();
    }

    private static boolean estaEmBranco(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    /**
     * Retorna o identificador unico da estacao.
     *
     * @return identificador da estacao
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o nome da estacao.
     *
     * @return nome da estacao
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o endereco da estacao.
     *
     * @return endereco da estacao
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Retorna a capacidade maxima de bicicletas da estacao.
     *
     * @return capacidade total de vagas
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Indica se ha pelo menos uma vaga fisica livre na estacao.
     *
     * @return {@code true} se houver vaga disponivel
     */
    public boolean possuiVaga() {
        return bicicletasEstacionadas.size() < capacidade;
    }

    /**
     * Retorna a quantidade de vagas fisicas livres na estacao.
     *
     * @return numero de vagas livres
     */
    public int getVagasDisponiveis() {
        return capacidade - bicicletasEstacionadas.size();
    }

    /**
     * Conta quantas bicicletas estacionadas nesta estacao estao disponiveis para retirada.
     *
     * @return quantidade de bicicletas aptas para retirada
     */
    public int getBicicletasDisponiveis() {
        int disponiveis = 0;
        for (Bicicleta bicicleta : bicicletasEstacionadas.values()) {
            if (bicicleta.estaDisponivelParaRetirada()) {
                disponiveis++;
            }
        }
        return disponiveis;
    }

    /**
     * Verifica se uma bicicleta especifica esta estacionada nesta estacao.
     *
     * @param idBicicleta identificador da bicicleta
     * @return {@code true} se a bicicleta estiver estacionada aqui
     */
    public boolean contemBicicleta(String idBicicleta) {
        return bicicletasEstacionadas.containsKey(idBicicleta);
    }

    /**
     * Estaciona uma bicicleta em uma vaga livre desta estacao.
     *
     * @param bicicleta bicicleta a ser vinculada a estacao
     * @throws EstacaoSemVagasException se a estacao ja atingiu sua capacidade maxima
     */
    public void estacionar(Bicicleta bicicleta) throws EstacaoSemVagasException {
        if (!possuiVaga()) {
            throw new EstacaoSemVagasException();
        }
        bicicletasEstacionadas.put(bicicleta.getId(), bicicleta);
        bicicleta.vincularEstacao(this.id);
    }

    /**
     * Remove uma bicicleta estacionada desta estacao, liberando sua vaga.
     *
     * @param idBicicleta identificador da bicicleta a remover
     * @throws BicicletaNaoEstaNaEstacaoException se a bicicleta nao estiver estacionada aqui
     */
    public void desestacionar(String idBicicleta) throws BicicletaNaoEstaNaEstacaoException {
        Bicicleta removida = bicicletasEstacionadas.remove(idBicicleta);
        if (removida == null) {
            throw new BicicletaNaoEstaNaEstacaoException();
        }
        removida.desvincularEstacao();
    }
}
