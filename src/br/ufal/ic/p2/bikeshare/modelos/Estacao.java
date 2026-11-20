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

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean possuiVaga() {
        return bicicletasEstacionadas.size() < capacidade;
    }

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
