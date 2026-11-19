package br.ufal.ic.p2.bikeshare.modelos;

/**
 * Plano de tarifacao avulso, sem cobranca de mensalidade fixa.
 */
public class PlanoAvulso extends Plano {

    private static final long serialVersionUID = 1L;

    @Override
    public String getNome() {
        return TipoPlano.AVULSO.getCodigo();
    }
}
