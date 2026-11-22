package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.AluguelEmAndamentoException;
import br.ufal.ic.p2.bikeshare.excecoes.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.DataDevolucaoAnteriorException;
import br.ufal.ic.p2.bikeshare.excecoes.DataNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoAluguelNulaException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa um aluguel de bicicleta, registrando sua retirada, estado e eventual devolucao.
 */
public class Aluguel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String idUsuario;
    private final String idBicicleta;
    private final String idEstacaoOrigem;
    private final LocalDateTime inicio;
    private EstadoAluguel estado;
    private String idEstacaoDestino;
    private LocalDateTime fim;
    private BigDecimal valorCobrado;

    /**
     * Cria um novo aluguel no estado em andamento.
     *
     * @param id              identificador unico do aluguel
     * @param idUsuario       identificador do usuario que retirou a bicicleta
     * @param idBicicleta     identificador da bicicleta retirada
     * @param idEstacaoOrigem identificador da estacao de origem
     * @param inicio          data e hora da retirada
     * @throws BikeShareException se a identificacao ou a data de inicio forem nulas
     */
    public Aluguel(String id, String idUsuario, String idBicicleta, String idEstacaoOrigem, LocalDateTime inicio)
            throws BikeShareException {
        if (id == null || id.trim().isEmpty()) {
            throw new IdentificacaoAluguelNulaException();
        }
        if (inicio == null) {
            throw new DataNulaException();
        }
        this.id = id;
        this.idUsuario = idUsuario;
        this.idBicicleta = idBicicleta;
        this.idEstacaoOrigem = idEstacaoOrigem;
        this.inicio = inicio;
        this.estado = EstadoAluguel.EM_ANDAMENTO;
        this.idEstacaoDestino = null;
        this.fim = null;
        this.valorCobrado = null;
    }

    /**
     * Retorna o identificador unico do aluguel.
     *
     * @return identificador do aluguel
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o identificador do usuario associado ao aluguel.
     *
     * @return identificador do usuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Retorna o identificador da bicicleta associada ao aluguel.
     *
     * @return identificador da bicicleta
     */
    public String getIdBicicleta() {
        return idBicicleta;
    }

    /**
     * Retorna o identificador da estacao de origem.
     *
     * @return identificador da estacao de origem
     */
    public String getIdEstacaoOrigem() {
        return idEstacaoOrigem;
    }

    /**
     * Retorna a data e hora de inicio da retirada.
     *
     * @return data e hora de inicio
     */
    public LocalDateTime getInicio() {
        return inicio;
    }

    /**
     * Retorna o estado atual do aluguel.
     *
     * @return estado do aluguel
     */
    public EstadoAluguel getEstado() {
        return estado;
    }

    /**
     * Indica se o aluguel ainda esta em andamento.
     *
     * @return {@code true} se o estado for {@link EstadoAluguel#EM_ANDAMENTO}
     */
    public boolean estaEmAndamento() {
        return estado == EstadoAluguel.EM_ANDAMENTO;
    }

    /**
     * Finaliza o aluguel normalmente com o valor tarifado.
     *
     * @param idEstacaoDestino identificador da estacao de devolucao
     * @param dataFim          data e hora da devolucao
     * @param valorCobrado     valor total cobrado pela corrida
     * @throws BikeShareException se o aluguel nao estiver em andamento ou a data for invalida
     */
    public void finalizar(String idEstacaoDestino, LocalDateTime dataFim, BigDecimal valorCobrado)
            throws BikeShareException {
        encerrar(EstadoAluguel.FINALIZADO, idEstacaoDestino, dataFim, valorCobrado);
    }

    /**
     * Cancela o aluguel dentro do prazo de carencia, sem cobranca.
     *
     * @param idEstacaoDestino identificador da estacao de devolucao
     * @param dataFim          data e hora da devolucao
     * @throws BikeShareException se o aluguel nao estiver em andamento ou a data for invalida
     */
    public void cancelar(String idEstacaoDestino, LocalDateTime dataFim) throws BikeShareException {
        encerrar(EstadoAluguel.CANCELADO, idEstacaoDestino, dataFim, BigDecimal.ZERO);
    }

    /**
     * Marca o aluguel como expirado por exceder o tempo maximo permitido, sem cobranca de tarifa.
     *
     * @param idEstacaoDestino identificador da estacao de devolucao
     * @param dataFim          data e hora da devolucao
     * @throws BikeShareException se o aluguel nao estiver em andamento ou a data for invalida
     */
    public void expirar(String idEstacaoDestino, LocalDateTime dataFim) throws BikeShareException {
        encerrar(EstadoAluguel.EXPIRADO, idEstacaoDestino, dataFim, BigDecimal.ZERO);
    }

    /**
     * Consulta o valor textual de um atributo do aluguel pelo seu nome exato.
     *
     * @param atributo nome exato do atributo solicitado
     * @return representacao textual do atributo
     * @throws BikeShareException se o atributo nao existir ou exigir que o aluguel esteja encerrado
     */
    public String getAtributo(String atributo) throws BikeShareException {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "estado":
                return estado.getCodigo();
            case "usuario":
                return idUsuario;
            case "bicicleta":
                return idBicicleta;
            case "estacaoOrigem":
                return idEstacaoOrigem;
            case "inicio":
                return FormatadorDataHora.formatar(inicio);
            case "estacaoDestino":
                garantirEncerrado();
                return idEstacaoDestino;
            case "fim":
                garantirEncerrado();
                return FormatadorDataHora.formatar(fim);
            case "valorCobrado":
                garantirEncerrado();
                return FormatadorMonetario.formatar(valorCobrado);
            default:
                throw new AtributoNaoExisteException();
        }
    }

    private void encerrar(EstadoAluguel novoEstado, String estacaoDestino, LocalDateTime dataFim, BigDecimal valor)
            throws BikeShareException {
        if (!estaEmAndamento()) {
            throw new AluguelNaoEstaEmAndamentoException();
        }
        if (dataFim == null) {
            throw new DataNulaException();
        }
        if (dataFim.isBefore(inicio)) {
            throw new DataDevolucaoAnteriorException();
        }
        this.estado = novoEstado;
        this.idEstacaoDestino = estacaoDestino;
        this.fim = dataFim;
        this.valorCobrado = valor;
    }

    private void garantirEncerrado() throws AluguelEmAndamentoException {
        if (estaEmAndamento()) {
            throw new AluguelEmAndamentoException();
        }
    }
}
