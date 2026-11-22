package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.CpfNuloException;

/**
 * Usuario do tipo pessoa fisica, identificado tambem pelo CPF.
 */
public class PessoaFisica extends Usuario {

    private static final long serialVersionUID = 1L;

    private final String cpf;

    /**
     * Cria um usuario pessoa fisica com os dados cadastrais informados.
     *
     * @param id    identificador unico do usuario
     * @param nome  nome do usuario
     * @param email endereco de email unico
     * @param senha senha de acesso
     * @param cpf   CPF da pessoa fisica
     * @param plano plano de tarifacao contratado
     * @throws BikeShareException se algum campo obrigatorio for invalido
     */
    public PessoaFisica(String id, String nome, String email, String senha, String cpf, Plano plano)
            throws BikeShareException {
        super(id, nome, email, senha, plano);
        if (estaEmBranco(cpf)) {
            throw new CpfNuloException();
        }
        this.cpf = cpf;
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.PESSOA_FISICA;
    }

    @Override
    public String getCpf() {
        return cpf;
    }
}
