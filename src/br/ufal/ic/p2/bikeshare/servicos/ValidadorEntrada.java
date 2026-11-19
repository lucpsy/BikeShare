package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.CpfNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoUsuarioNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.NomeNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.PlanoNaoPossuiMensalidadeException;
import br.ufal.ic.p2.bikeshare.excecoes.RazaoSocialNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.TipoUsuarioInvalidoException;
import br.ufal.ic.p2.bikeshare.modelos.Plano;
import br.ufal.ic.p2.bikeshare.modelos.PlanoAssinante;
import br.ufal.ic.p2.bikeshare.modelos.PlanoAvulso;
import br.ufal.ic.p2.bikeshare.modelos.TipoPlano;
import br.ufal.ic.p2.bikeshare.modelos.TipoUsuario;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Centraliza as validacoes e conversoes de dados textuais de entrada para objetos de dominio.
 */
public final class ValidadorEntrada {

    private static final Pattern PADRAO_EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern PADRAO_NUMERO_DECIMAL = Pattern.compile("^[+-]?\\d+([.,]\\d+)?$");

    private ValidadorEntrada() {
    }

    public static boolean estaVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public static void validarNome(String nome) throws NomeNuloException {
        if (estaVazio(nome)) {
            throw new NomeNuloException();
        }
    }

    public static void validarEmailObrigatorio(String email) throws EmailNuloException {
        if (estaVazio(email)) {
            throw new EmailNuloException();
        }
    }

    public static void validarEmail(String email) throws EmailNuloException, EmailInvalidoException {
        validarEmailObrigatorio(email);
        if (!PADRAO_EMAIL.matcher(email.trim()).matches()) {
            throw new EmailInvalidoException();
        }
    }

    public static void validarSenha(String senha) throws SenhaNulaException {
        if (estaVazio(senha)) {
            throw new SenhaNulaException();
        }
    }

    public static void validarTipoEsperado(String tipoTexto, TipoUsuario tipoEsperado)
            throws TipoUsuarioInvalidoException {
        TipoUsuario tipo = TipoUsuario.deCodigo(tipoTexto);
        if (tipo != tipoEsperado) {
            throw new TipoUsuarioInvalidoException();
        }
    }

    public static void validarCpf(String cpf) throws CpfNuloException {
        if (estaVazio(cpf)) {
            throw new CpfNuloException();
        }
    }

    public static void validarCnpj(String cnpj) throws CnpjNuloException {
        if (estaVazio(cnpj)) {
            throw new CnpjNuloException();
        }
    }

    public static void validarRazaoSocial(String razaoSocial) throws RazaoSocialNulaException {
        if (estaVazio(razaoSocial)) {
            throw new RazaoSocialNulaException();
        }
    }

    public static void validarIdentificacaoUsuario(String idUsuario) throws IdentificacaoUsuarioNulaException {
        if (estaVazio(idUsuario)) {
            throw new IdentificacaoUsuarioNulaException();
        }
    }

    /**
     * Converte e valida uma string de mensalidade em {@link BigDecimal} nao-negativo.
     *
     * @param mensalidade representacao textual da mensalidade
     * @return valor numerico validado
     * @throws MensalidadeNulaException        se a mensalidade estiver nula ou em branco
     * @throws MensalidadeNaoNumericaException se o formato nao for numerico valido
     * @throws MensalidadeNegativaException    se o valor for negativo
     */
    public static BigDecimal parsearMensalidade(String mensalidade)
            throws MensalidadeNulaException, MensalidadeNaoNumericaException, MensalidadeNegativaException {
        if (estaVazio(mensalidade)) {
            throw new MensalidadeNulaException();
        }
        String texto = mensalidade.trim();
        if (!PADRAO_NUMERO_DECIMAL.matcher(texto).matches()) {
            throw new MensalidadeNaoNumericaException();
        }
        BigDecimal valor;
        try {
            valor = new BigDecimal(texto.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new MensalidadeNaoNumericaException();
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new MensalidadeNegativaException();
        }
        return valor;
    }

    /**
     * Cria o objeto de dominio {@link Plano} correspondente ao nome e mensalidade informados.
     *
     * @param nomePlano   nome exato do plano ({@code avulso} ou {@code assinante})
     * @param mensalidade valor da mensalidade (obrigatorio para {@code assinante}, deve ser vazio para {@code avulso})
     * @return instancia concreta de {@link Plano}
     * @throws BikeShareException se o plano ou a mensalidade forem invalidos
     */
    public static Plano criarPlano(String nomePlano, String mensalidade) throws BikeShareException {
        TipoPlano tipoPlano = TipoPlano.deCodigo(nomePlano);
        switch (tipoPlano) {
            case AVULSO:
                if (!estaVazio(mensalidade)) {
                    throw new PlanoNaoPossuiMensalidadeException();
                }
                return new PlanoAvulso();
            case ASSINANTE:
                BigDecimal valorMensalidade = parsearMensalidade(mensalidade);
                return new PlanoAssinante(valorMensalidade);
            default:
                throw new PlanoInvalidoException();
        }
    }
}
