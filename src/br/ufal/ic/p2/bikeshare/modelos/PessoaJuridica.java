package br.ufal.ic.p2.bikeshare.modelos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.RazaoSocialNulaException;

/**
 * Usuario do tipo pessoa juridica, identificado por CNPJ e razao social.
 */
public class PessoaJuridica extends Usuario {

    private static final long serialVersionUID = 1L;

    private final String cnpj;
    private final String razaoSocial;

    /**
     * Cria um usuario pessoa juridica com os dados cadastrais informados.
     *
     * @param id          identificador unico do usuario
     * @param nome        nome do usuario
     * @param email       endereco de email unico
     * @param senha       senha de acesso
     * @param cnpj        CNPJ da empresa
     * @param razaoSocial razao social da empresa
     * @param plano       plano de tarifacao contratado
     * @throws BikeShareException se algum campo obrigatorio for invalido
     */
    public PessoaJuridica(String id, String nome, String email, String senha,
            String cnpj, String razaoSocial, Plano plano) throws BikeShareException {
        super(id, nome, email, senha, plano);
        if (estaEmBranco(cnpj)) {
            throw new CnpjNuloException();
        }
        if (estaEmBranco(razaoSocial)) {
            throw new RazaoSocialNulaException();
        }
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.PESSOA_JURIDICA;
    }

    @Override
    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String getRazaoSocial() {
        return razaoSocial;
    }
}
