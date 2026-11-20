package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoBicicletaNulaException;

import java.io.Serializable;

/**
 * Entidade base que representa uma bicicleta cadastrada no sistema BikeShare.
 */
public abstract class Bicicleta implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEM_ESTACAO = "nenhuma";

    private final String id;
    private EstadoBicicleta estado;
    private String idEstacao;

    protected Bicicleta(String id) throws IdentificacaoBicicletaNulaException {
        if (id == null || id.trim().isEmpty()) {
            throw new IdentificacaoBicicletaNulaException();
        }
        this.id = id;
        this.estado = EstadoBicicleta.DISPONIVEL;
        this.idEstacao = null;
    }

    public String getId() {
        return id;
    }

    public EstadoBicicleta getEstado() {
        return estado;
    }

    public String getIdEstacaoFormatado() {
        return idEstacao == null ? SEM_ESTACAO : idEstacao;
    }

    /**
     * Retorna o tipo da bicicleta.
     *
     * @return tipo de bicicleta
     */
    public abstract TipoBicicleta getTipo();

    /**
     * Retorna o nivel atual de bateria (0 a 100), quando aplicavel ao tipo da bicicleta.
     *
     * @return nivel de bateria inteiro
     * @throws BicicletaNaoPossuiBateriaException se o tipo da bicicleta nao possuir bateria
     */
    public int getNivelBateria() throws BicicletaNaoPossuiBateriaException {
        throw new BicicletaNaoPossuiBateriaException();
    }

    /**
     * Indica se a bicicleta esta no estado disponivel (sem uso nem manutencao).
     *
     * @return {@code true} se o estado atual for {@link EstadoBicicleta#DISPONIVEL}
     */
    public boolean estaDisponivel() {
        return estado == EstadoBicicleta.DISPONIVEL;
    }

    /**
     * Indica se a bicicleta esta apta a ser retirada por um usuario.
     * Subtipos com restricoes adicionais (como bateria minima) devem sobrescrever este metodo.
     *
     * @return {@code true} se puder ser retirada
     */
    public boolean estaDisponivelParaRetirada() {
        return estaDisponivel();
    }

    void vincularEstacao(String idEstacao) {
        this.idEstacao = idEstacao;
    }

    void desvincularEstacao() {
        this.idEstacao = null;
    }

    /**
     * Consulta o valor textual de um atributo da bicicleta pelo seu nome exato.
     *
     * @param atributo nome exato do atributo solicitado
     * @return valor textual do atributo
     * @throws BikeShareException se o atributo nao existir ou nao se aplicar ao subtipo da bicicleta
     */
    public String getAtributo(String atributo) throws BikeShareException {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "tipo":
                return getTipo().getCodigo();
            case "estado":
                return getEstado().getCodigo();
            case "estacao":
                return getIdEstacaoFormatado();
            case "nivelBateria":
                return String.valueOf(getNivelBateria());
            default:
                throw new AtributoNaoExisteException();
        }
    }
}
