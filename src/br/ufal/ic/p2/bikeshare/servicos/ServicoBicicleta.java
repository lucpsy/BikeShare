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
import br.ufal.ic.p2.bikeshare.modelos.Estacao;
import br.ufal.ic.p2.bikeshare.modelos.TipoBicicleta;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.persistencia.RepositorioEstacoes;

/**
 * Servico responsavel pelos casos de uso de cadastro, remocao e consulta de bicicletas.
 */
public class ServicoBicicleta {

    private final ServicoSistema servicoSistema;

    public ServicoBicicleta(ServicoSistema servicoSistema) {
        this.servicoSistema = servicoSistema;
    }

    /**
     * Adiciona uma bicicleta convencional a uma estacao.
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
            bicicleta = new BicicletaEletrica(idBicicleta, nivelBateria);
        }
        registrarNaEstacao(estacao, bicicleta);
    }

    /**
     * Remove uma bicicleta disponivel de uma estacao e do sistema.
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
     */
    public String getAtributoBicicleta(String idBicicleta, String atributo) throws BikeShareException {
        servicoSistema.garantirAtivo();
        Bicicleta bicicleta = buscarBicicletaObrigatoria(idBicicleta);
        return bicicleta.getAtributo(atributo);
    }

    /**
     * Consulta o estado atual de uma bicicleta cadastrada.
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

    private RepositorioEstacoes repositorioEstacoes() {
        return servicoSistema.getEstado().getRepositorioEstacoes();
    }

    private RepositorioBicicletas repositorioBicicletas() {
        return servicoSistema.getEstado().getRepositorioBicicletas();
    }
}
