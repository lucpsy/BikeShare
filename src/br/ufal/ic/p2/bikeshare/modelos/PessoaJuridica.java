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
