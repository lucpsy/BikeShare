package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.BicicletaJaExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoDisponivelException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoEstaNaEstacaoException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.excecoes.EstacaoSemVagasException;
import br.ufal.ic.p2.bikeshare.excecoes.NivelBateriaNuloException;
import br.ufal.ic.p2.bikeshare.modelos.Bicicleta;
import br.ufal.ic.p2.bikeshare.modelos.BicicletaConvencional;
import br.ufal.ic.p2.bikeshare.modelos.BicicletaEletrica;
import br.ufal.ic.p2.bikeshare.modelos.ConfiguracaoSistema;
import br.ufal.ic.p2.bikeshare.modelos.Estacao;
import br.ufal.ic.p2.bikeshare.modelos.TipoBicicleta;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioEstacoes;

/**
 * Servico responsavel pelos casos de uso de cadastro, remocao e consulta de bicicletas.
 */
public class ServicoBicicleta {

    private final ServicoSistema servicoSistema;

    /**
     * Cria o servico de bicicletas vinculado ao gerenciador do ciclo de vida do sistema.
     *
     * @param servicoSistema servico que controla o estado e persistencia do sistema
     */
    public ServicoBicicleta(ServicoSistema servicoSistema) {
        this.servicoSistema = servicoSistema;
    }

    /**
     * Adiciona uma bicicleta convencional a uma estacao.
     *
     * @param idEstacao   identificador da estacao
     * @param idBicicleta identificador unico da bicicleta
     * @param tipoTexto   codigo textual do tipo ({@code convencional})
     * @throws BikeShareException se algum dado for invalido ou a estacao estiver sem vagas
     */
    public void adicionarBicicleta(String idEstacao, String idBicicleta, String tipoTexto)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        Estacao estacao = buscarEstacaoObrigatoria(idEstacao);
        validarNovaBicicleta(idBicicleta);
        TipoBicicleta tipo = TipoBicicleta.deCodigo(tipoTexto);

        if (tipo == TipoBicicleta.ELETRICA) {
            throw new NivelBateriaNuloException();
        }
        Bicicleta bicicleta = new BicicletaConvencional(idBicicleta);
        registrarNaEstacao(estacao, bicicleta);
    }

    /**
     * Adiciona uma bicicleta eletrica (ou convencional sem bateria) a uma estacao.
     *
     * @param idEstacao         identificador da estacao
     * @param idBicicleta       identificador unico da bicicleta
     * @param tipoTexto         codigo textual do tipo ({@code convencional} ou {@code eletrica})
     * @param nivelBateriaTexto nivel inicial de bateria (0 a 100 para eletrica, vazio para convencional)
     * @throws BikeShareException se algum dado for invalido ou a estacao estiver sem vagas
     */
    public void adicionarBicicleta(String idEstacao, String idBicicleta, String tipoTexto, String nivelBateriaTexto)
            throws BikeShareException {
        servicoSistema.garantirAtivo();
        Estacao estacao = buscarEstacaoObrigatoria(idEstacao);
        validarNovaBicicleta(idBicicleta);
        TipoBicicleta tipo = TipoBicicleta.deCodigo(tipoTexto);

        Bicicleta bicicleta;
        if (tipo == TipoBicicleta.CONVENCIONAL) {
            if (!ValidadorEntrada.estaVazio(nivelBateriaTexto)) {
                throw new BicicletaNaoPossuiBateriaException();
            }
            bicicleta = new BicicletaConvencional(idBicicleta);
        } else {
            int nivelBateria = ValidadorEntrada.parsearNivelBateria(nivelBateriaTexto);
            bicicleta = new BicicletaEletrica(
                    idBicicleta,
                    nivelBateria,
                    configuracao().getNivelMinimoBateriaEletrica(),
                    configuracao().getAdicionalMinutoEletrica()
            );
        }
        registrarNaEstacao(estacao, bicicleta);
    }

    /**
     * Remove uma bicicleta disponivel de uma estacao e do sistema.
     *
     * @param idEstacao   identificador da estacao onde a bicicleta esta estacionada
     * @param idBicicleta identificador da bicicleta a remover
     * @throws BikeShareException se a estacao ou bicicleta nao existir, nao estiver na estacao ou nao estiver disponivel
     */
    public void removerBicicleta(String idEstacao, String idBicicleta) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Estacao estacao = buscarEstacaoObrigatoria(idEstacao);
        Bicicleta bicicleta = buscarBicicletaObrigatoria(idBicicleta);

        if (!estacao.contemBicicleta(idBicicleta)) {
            throw new BicicletaNaoEstaNaEstacaoException();
        }
        if (!bicicleta.estaDisponivel()) {
            throw new BicicletaNaoDisponivelException();
        }
        estacao.desestacionar(idBicicleta);
        repositorioBicicletas().remover(idBicicleta);
    }

    /**
     * Consulta um atributo especifico de uma bicicleta cadastrada.
     *
     * @param idBicicleta identificador da bicicleta
     * @param atributo    nome exato do atributo solicitado
     * @return valor textual do atributo
     * @throws BikeShareException se a bicicleta ou atributo for invalido
     */
    public String getAtributoBicicleta(String idBicicleta, String atributo) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Bicicleta bicicleta = buscarBicicletaObrigatoria(idBicicleta);
        return bicicleta.getAtributo(atributo);
    }

    /**
     * Consulta o estado atual de uma bicicleta cadastrada.
     *
     * @param idBicicleta identificador da bicicleta
     * @return codigo textual do estado atual
     * @throws BikeShareException se a bicicleta nao existir
     */
    public String getEstadoBicicleta(String idBicicleta) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Bicicleta bicicleta = buscarBicicletaObrigatoria(idBicicleta);
        return bicicleta.getEstado().getCodigo();
    }

    private void validarNovaBicicleta(String idBicicleta) throws BikeShareException {
        ValidadorEntrada.validarIdentificacaoBicicleta(idBicicleta);
        if (repositorioBicicletas().existe(idBicicleta)) {
            throw new BicicletaJaExisteException();
        }
    }

    private void registrarNaEstacao(Estacao estacao, Bicicleta bicicleta) throws EstacaoSemVagasException {
        estacao.estacionar(bicicleta);
        repositorioBicicletas().salvar(bicicleta);
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

    private ConfiguracaoSistema configuracao() {
        return servicoSistema.getEstado().getConfiguracao();
    }

    private RepositorioEstacoes repositorioEstacoes() {
        return servicoSistema.getEstado().getRepositorioEstacoes();
    }

    private RepositorioBicicletas repositorioBicicletas() {
        return servicoSistema.getEstado().getRepositorioBicicletas();
    }
}
