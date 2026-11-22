package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoDisponivelException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoBicicletaNulaException;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidade base que representa uma bicicleta cadastrada no sistema BikeShare.
 */
public abstract class Bicicleta implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEM_ESTACAO = "nenhuma";

    private final String id;
    private EstadoBicicleta estado;
    private String idEstacao;

    /**
     * Cria uma bicicleta no estado disponivel e sem estacao vinculada inicialmente.
     *
     * @param id identificador unico da bicicleta
     * @throws IdentificacaoBicicletaNulaException se o identificador for nulo ou em branco
     */
    protected Bicicleta(String id) throws IdentificacaoBicicletaNulaException {
        if (id == null || id.trim().isEmpty()) {
            throw new IdentificacaoBicicletaNulaException();
        }
        this.id = id;
        this.estado = EstadoBicicleta.DISPONIVEL;
        this.idEstacao = null;
    }

    /**
     * Retorna o identificador unico da bicicleta.
     *
     * @return identificador da bicicleta
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o estado atual da bicicleta.
     *
     * @return estado da bicicleta
     */
    public EstadoBicicleta getEstado() {
        return estado;
    }

    /**
     * Retorna o identificador da estacao onde a bicicleta esta estacionada ou {@code "nenhuma"}.
     *
     * @return identificador da estacao formatado
     */
    public String getIdEstacaoFormatado() {
        return idEstacao == null ? SEM_ESTACAO : idEstacao;
    }

    /**
     * Retorna o tipo concreto da bicicleta.
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
     * Calcula eventual adicional tarifario especifico do tipo da bicicleta para a duracao da corrida.
     *
     * @param duracaoMinutos duracao total da corrida em minutos
     * @return valor adicional monetario
     */
    public BigDecimal calcularAdicional(long duracaoMinutos) {
        return BigDecimal.ZERO;
    }

    /**
     * Indica se a bicicleta esta no estado disponivel.
     *
     * @return {@code true} se o estado atual for {@link EstadoBicicleta#DISPONIVEL}
     */
    public boolean estaDisponivel() {
        return estado == EstadoBicicleta.DISPONIVEL;
    }

    /**
     * Indica se a bicicleta esta apta a ser retirada por um usuario.
     *
     * @return {@code true} se puder ser retirada
     */
    public boolean estaDisponivelParaRetirada() {
        return estaDisponivel();
    }

    /**
     * Valida se a bicicleta atende a todas as condicoes especificas para retirada.
     *
     * @throws BikeShareException se a bicicleta estiver indisponivel ou sem bateria suficiente
     */
    public void validarAptidaoParaRetirada() throws BikeShareException {
        if (!estaDisponivel()) {
            throw new BicicletaNaoDisponivelException();
        }
    }

    /**
     * Altera o estado da bicicleta para em uso.
     */
    public void marcarEmUso() {
        this.estado = EstadoBicicleta.EM_USO;
    }

    /**
     * Altera o estado da bicicleta para disponivel.
     */
    public void marcarDisponivel() {
        this.estado = EstadoBicicleta.DISPONIVEL;
    }

    /**
     * Altera o estado da bicicleta para em manutencao.
     */
    public void marcarEmManutencao() {
        this.estado = EstadoBicicleta.EM_MANUTENCAO;
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
