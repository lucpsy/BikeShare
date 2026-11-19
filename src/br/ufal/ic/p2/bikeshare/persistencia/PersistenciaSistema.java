package br.ufal.ic.p2.bikeshare.persistencia;

import br.ufal.ic.p2.bikeshare.excecoes.PersistenciaException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gerencia a gravacao e leitura do {@link EstadoSistema} em disco usando serializacao nativa Java (.ser).
 */
public class PersistenciaSistema {

    private static final String ARQUIVO_PADRAO = "bikeshare_dados.ser";

    private final Path caminhoArquivo;

    public PersistenciaSistema() {
        this.caminhoArquivo = Paths.get(ARQUIVO_PADRAO);
    }

    /**
     * Carrega o estado persistido do disco, ou retorna um estado vazio caso o arquivo ainda nao exista.
     *
     * @return estado carregado ou novo estado limpo
     * @throws PersistenciaException se ocorrer erro de leitura ou desserializacao
     */
    public EstadoSistema carregar() throws PersistenciaException {
        if (!Files.exists(caminhoArquivo)) {
            return new EstadoSistema();
        }
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(caminhoArquivo))) {
            Object lido = entrada.readObject();
            if (!(lido instanceof EstadoSistema)) {
                throw new PersistenciaException("Formato de dados persistidos invalido.", null);
            }
            return (EstadoSistema) lido;
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Erro ao carregar os dados do sistema.", e);
        }
    }

    /**
     * Grava o estado atual do sistema em disco.
     *
     * @param estado estado completo a ser persistido
     * @throws PersistenciaException se ocorrer erro de gravacao
     */
    public void salvar(EstadoSistema estado) throws PersistenciaException {
        try (ObjectOutputStream saida = new ObjectOutputStream(Files.newOutputStream(caminhoArquivo))) {
            saida.writeObject(estado);
        } catch (IOException e) {
            throw new PersistenciaException("Erro ao salvar os dados do sistema.", e);
        }
    }

    /**
     * Remove o arquivo de persistencia do disco, caso exista.
     *
     * @throws PersistenciaException se ocorrer erro ao remover o arquivo
     */
    public void apagar() throws PersistenciaException {
        try {
            Files.deleteIfExists(caminhoArquivo);
        } catch (IOException e) {
            throw new PersistenciaException("Erro ao apagar os dados persistidos do sistema.", e);
        }
    }
}
