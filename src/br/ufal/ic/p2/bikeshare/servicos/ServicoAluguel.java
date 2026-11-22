package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.excecoes.AluguelNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoEstaNaEstacaoException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.DataDevolucaoAnteriorException;
import br.ufal.ic.p2.bikeshare.excecoes.EstacaoCheiaException;
import br.ufal.ic.p2.bikeshare.excecoes.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioInadimplenteException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioJaPossuiAluguelException;
import br.ufal.ic.p2.bikeshare.excecoes.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.modelos.Aluguel;
import br.ufal.ic.p2.bikeshare.modelos.Bicicleta;
import br.ufal.ic.p2.bikeshare.modelos.ConfiguracaoSistema;
import br.ufal.ic.p2.bikeshare.modelos.Estacao;
import br.ufal.ic.p2.bikeshare.modelos.PenalidadeAtraso;
import br.ufal.ic.p2.bikeshare.modelos.Usuario;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioUsuarios;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Servico responsavel pelos casos de uso de retirada, devolucao e consulta de alugueis de bicicletas.
 */
public class ServicoAluguel {

    private final ServicoSistema servicoSistema;

    /**
     * Cria o servico de alugueis vinculado ao gerenciador do ciclo de vida do sistema.
     *
     * @param servicoSistema servico que controla o estado e persistencia do sistema
     */
    public ServicoAluguel(ServicoSistema servicoSistema) {
        this.servicoSistema = servicoSistema;
    }

    /**
     * Realiza a retirada de uma bicicleta de uma estacao por um usuario autorizado, iniciando um aluguel.
     *
     * @param idUsuario   identificador do usuario
     * @param idEstacao   identificador da estacao de origem
     * @param idBicicleta identificador da bicicleta a retirar
     * @param dataTexto   data e hora da retirada no formato {@code d/M/uuuu H:mm}
     * @return identificador sequencial do aluguel iniciado
     * @throws BikeShareException se alguma validacao de entrada ou regra de negocio impedir a retirada
     */
    public String retirarBicicleta(String idUsuario, String idEstacao, String idBicicleta, String dataTexto)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        Usuario usuario = buscarUsuarioObrigatorio(idUsuario);
        Estacao estacao = buscarEstacaoObrigatoria(idEstacao);
        Bicicleta bicicleta = buscarBicicletaObrigatoria(idBicicleta);
        LocalDateTime dataInicio = ValidadorEntrada.parsearDataHora(dataTexto);

        validarPreCondicoesRetirada(usuario, estacao, bicicleta);

        String idAluguel = repositorioAlugueis().proximoIdDisponivel();
        Aluguel aluguel = new Aluguel(idAluguel, usuario.getId(), bicicleta.getId(), estacao.getId(), dataInicio);

        estacao.desestacionar(bicicleta.getId());
        bicicleta.marcarEmUso();
        usuario.iniciarAluguel(idAluguel);
        repositorioAlugueis().salvar(aluguel);
        return idAluguel;
    }

    /**
     * Realiza a devolucao de uma bicicleta em uma estacao com vaga livre, concluindo o aluguel.
     *
     * @param idAluguel identificador do aluguel em andamento
     * @param idEstacao identificador da estacao de destino
     * @param dataTexto data e hora da devolucao no formato {@code d/M/uuuu H:mm}
     * @throws BikeShareException se alguma validacao ou regra de negocio impedir a devolucao
     */
    public void devolverBicicleta(String idAluguel, String idEstacao, String dataTexto) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Aluguel aluguel = buscarAluguelObrigatorio(idAluguel);
        Estacao estacaoDestino = buscarEstacaoObrigatoria(idEstacao);
        LocalDateTime dataFim = ValidadorEntrada.parsearDataHora(dataTexto);

        if (!aluguel.estaEmAndamento()) {
            throw new AluguelNaoEstaEmAndamentoException();
        }
        if (dataFim.isBefore(aluguel.getInicio())) {
            throw new DataDevolucaoAnteriorException();
        }
        if (!estacaoDestino.possuiVaga()) {
            throw new EstacaoCheiaException();
        }

        Usuario usuario = buscarUsuarioObrigatorio(aluguel.getIdUsuario());
        Bicicleta bicicleta = buscarBicicletaObrigatoria(aluguel.getIdBicicleta());
        long duracaoMinutos = Duration.between(aluguel.getInicio(), dataFim).toMinutes();

        if (excedeuTempoMaximo(duracaoMinutos)) {
            processarExpiracao(aluguel, usuario, bicicleta, estacaoDestino, dataFim);
        } else if (estaDentroDaCarencia(aluguel, estacaoDestino, duracaoMinutos)) {
            processarCancelamento(aluguel, usuario, bicicleta, estacaoDestino, dataFim);
        } else {
            processarFinalizacaoNormal(aluguel, usuario, bicicleta, estacaoDestino, dataFim, duracaoMinutos);
        }
    }

    /**
     * Consulta o valor textual de um atributo de um aluguel cadastrado.
     *
     * @param idAluguel identificador do aluguel
     * @param atributo  nome exato do atributo solicitado
     * @return representacao textual do atributo
     * @throws BikeShareException se o aluguel ou atributo nao existir, ou se o atributo exigir aluguel encerrado
     */
    public String getAtributoAluguel(String idAluguel, String atributo) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Aluguel aluguel = buscarAluguelObrigatorio(idAluguel);
        return aluguel.getAtributo(atributo);
    }

    private void validarPreCondicoesRetirada(Usuario usuario, Estacao estacao, Bicicleta bicicleta)
            throws BikeShareException {
        if (usuario.estaInadimplente()) {
            throw new UsuarioInadimplenteException();
        }
        if (usuario.possuiAluguelAtivo()) {
            throw new UsuarioJaPossuiAluguelException();
        }
        if (!estacao.contemBicicleta(bicicleta.getId())) {
            throw new BicicletaNaoEstaNaEstacaoException();
        }
        bicicleta.validarAptidaoParaRetirada();
        usuario.validarAutorizacaoRetirada();
    }

    private boolean excedeuTempoMaximo(long duracaoMinutos) {
        return duracaoMinutos > configuracao().getTempoMaximoAluguelMinutos();
    }

    private boolean estaDentroDaCarencia(Aluguel aluguel, Estacao estacaoDestino, long duracaoMinutos) {
        return aluguel.getIdEstacaoOrigem().equals(estacaoDestino.getId())
                && duracaoMinutos <= configuracao().getPrazoCarenciaMinutos();
    }

    private void processarExpiracao(Aluguel aluguel, Usuario usuario, Bicicleta bicicleta,
            Estacao estacaoDestino, LocalDateTime dataFim) throws BikeShareException {
        PenalidadeAtraso penalidade = new PenalidadeAtraso(configuracao().getValorPenalidadeAtraso());
        estacaoDestino.estacionar(bicicleta);
        bicicleta.marcarEmManutencao();
        usuario.aplicarPenalidade(penalidade);
        usuario.encerrarAluguelAtivo();
        aluguel.expirar(estacaoDestino.getId(), dataFim);
    }

    private void processarCancelamento(Aluguel aluguel, Usuario usuario, Bicicleta bicicleta,
            Estacao estacaoDestino, LocalDateTime dataFim) throws BikeShareException {
        estacaoDestino.estacionar(bicicleta);
        bicicleta.marcarDisponivel();
        usuario.encerrarAluguelAtivo();
        aluguel.cancelar(estacaoDestino.getId(), dataFim);
    }

    private void processarFinalizacaoNormal(Aluguel aluguel, Usuario usuario, Bicicleta bicicleta,
            Estacao estacaoDestino, LocalDateTime dataFim, long duracaoMinutos) throws BikeShareException {
        BigDecimal tarifaBase = usuario.getPlano().calcularTarifaBase(duracaoMinutos);
        BigDecimal adicionalBicicleta = bicicleta.calcularAdicional(duracaoMinutos);
        BigDecimal valorTotal = tarifaBase.add(adicionalBicicleta);

        usuario.cobrar(valorTotal);
        estacaoDestino.estacionar(bicicleta);
        bicicleta.marcarDisponivel();
        usuario.encerrarAluguelAtivo();
        aluguel.finalizar(estacaoDestino.getId(), dataFim, valorTotal);
    }

    private Usuario buscarUsuarioObrigatorio(String idUsuario) throws BikeShareException {
        ValidadorEntrada.validarIdentificacaoUsuario(idUsuario);
        return repositorioUsuarios().buscarPorId(idUsuario)
                .orElseThrow(UsuarioNaoExisteException::new);
    }

    private Estacao buscarEstacaoObrigatoria(String idEstacao) throws BikeShareException {
        ValidadorEntrada.validarIdentificacaoEstacao(idEstacao);
        return repositorioEstacoes().buscarPorId(idEstacao)
                .orElseThrow(EstacaoNaoExisteException::new);
    }

    private Bicicleta buscarBicicletaObrigatoria(String idBicicleta) throws BikeShareException {
        ValidadorEntrada.validarIdentificacaoBicicleta(idBicicleta);
        return repositorioBicicletas().buscarPorId(idBicicleta)
                .orElseThrow(BicicletaNaoExisteException::new);
    }

    private Aluguel buscarAluguelObrigatorio(String idAluguel) throws BikeShareException {
        ValidadorEntrada.validarIdentificacaoAluguel(idAluguel);
        return repositorioAlugueis().buscarPorId(idAluguel)
                .orElseThrow(AluguelNaoExisteException::new);
    }

    private ConfiguracaoSistema configuracao() {
        return servicoSistema.getEstado().getConfiguracao();
    }

    private RepositorioUsuarios repositorioUsuarios() {
        return servicoSistema.getEstado().getRepositorioUsuarios();
    }

    private RepositorioEstacoes repositorioEstacoes() {
        return servicoSistema.getEstado().getRepositorioEstacoes();
    }

    private RepositorioBicicletas repositorioBicicletas() {
        return servicoSistema.getEstado().getRepositorioBicicletas();
    }

    private RepositorioAlugueis repositorioAlugueis() {
        return servicoSistema.getEstado().getRepositorioAlugueis();
    }
}
