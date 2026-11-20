package br.ufal.ic.p2.bikeshare.persistencia;

import br.ufal.ic.p2.bikeshare.modelos.Usuario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio responsavel pelo armazenamento e consulta de usuarios em memoria,
 * mantendo o encapsulamento das colecoes internas e a geracao sequencial de identificadores.
 */
public class RepositorioUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PREFIXO_ID = "U";

    private final Map<String, Usuario> usuariosPorId;
    private final Map<String, String> idsPorEmailNormalizado;
    private int proximoNumeroId;

    public RepositorioUsuarios() {
        this.usuariosPorId = new LinkedHashMap<>();
        this.idsPorEmailNormalizado = new HashMap<>();
        this.proximoNumeroId = 1;
    }

    /**
     * Retorna o proximo identificador disponivel sem consumir a sequencia.
     *
     * @return identificador sequencial no formato {@code U1}, {@code U2}, etc.
     */
    public String proximoIdDisponivel() {
        return PREFIXO_ID + proximoNumeroId;
    }

    /**
     * Persiste um novo usuario em memoria e avanca o contador sequencial de identificadores.
     *
     * @param usuario usuario valido a ser registrado
     */
    public void salvar(Usuario usuario) {
        usuariosPorId.put(usuario.getId(), usuario);
        idsPorEmailNormalizado.put(normalizarEmail(usuario.getEmail()), usuario.getId());
        proximoNumeroId++;
    }

    /**
     * Busca um usuario pelo seu identificador exato.
     *
     * @param id identificador do usuario
     * @return {@link Optional} contendo o usuario encontrado ou vazio
     */
    public Optional<Usuario> buscarPorId(String id) {
        return Optional.ofNullable(usuariosPorId.get(id));
    }

    /**
     * Busca um usuario pelo email, ignorando diferencas entre maiusculas e minusculas.
     *
     * @param email email do usuario
     * @return {@link Optional} contendo o usuario encontrado ou vazio
     */
    public Optional<Usuario> buscarPorEmail(String email) {
        String id = idsPorEmailNormalizado.get(normalizarEmail(email));
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(usuariosPorId.get(id));
    }

    /**
     * Verifica se ja existe usuario cadastrado com o email informado (insensivel a maiusculas/minusculas).
     *
     * @param email email a verificar
     * @return {@code true} se o email ja estiver em uso
     */
    public boolean existeEmail(String email) {
        return idsPorEmailNormalizado.containsKey(normalizarEmail(email));
    }

    /**
     * Atualiza o indice de emails quando um usuario altera o seu endereco de email.
     *
     * @param emailAnterior email previamente associado ao usuario
     * @param usuario       usuario ja atualizado com o novo email
     */
    public void atualizarEmail(String emailAnterior, Usuario usuario) {
        idsPorEmailNormalizado.remove(normalizarEmail(emailAnterior));
        idsPorEmailNormalizado.put(normalizarEmail(usuario.getEmail()), usuario.getId());
    }

    /**
     * Remove um usuario do repositorio e libera seu email, mantendo o contador de IDs intacto.
     *
     * @param usuario usuario a remover
     */
    public void remover(Usuario usuario) {
        usuariosPorId.remove(usuario.getId());
        idsPorEmailNormalizado.remove(normalizarEmail(usuario.getEmail()));
    }

    /**
     * Remove todos os usuarios cadastrados e reinicia o contador de identificadores.
     */
    public void limpar() {
        usuariosPorId.clear();
        idsPorEmailNormalizado.clear();
        proximoNumeroId = 1;
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
