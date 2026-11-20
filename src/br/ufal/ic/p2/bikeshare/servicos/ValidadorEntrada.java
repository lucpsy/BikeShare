package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.CapacidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.excecoes.CapacidadeNaoPositivaException;
import br.ufal.ic.p2.bikeshare.excecoes.CapacidadeNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.ChavePixNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.CpfNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.EnderecoNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoBicicletaNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoEstacaoNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoUsuarioNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.excecoes.MensalidadeNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.NivelBateriaInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.NivelBateriaNaoNumericoException;
import br.ufal.ic.p2.bikeshare.excecoes.NivelBateriaNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.NomeNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.NumeroCartaoNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.PlanoNaoPossuiMensalidadeException;
import br.ufal.ic.p2.bikeshare.excecoes.RazaoSocialNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.TipoUsuarioInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.ValidadeCartaoInvalidaException;
import br.ufal.ic.p2.bikeshare.excecoes.ValidadeCartaoNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorNaoNumericoException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorNaoPositivoException;
import br.ufal.ic.p2.bikeshare.excecoes.ValorNuloException;
import br.ufal.ic.p2.bikeshare.modelos.BicicletaEletrica;
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
    private static final Pattern PADRAO_INTEIRO = Pattern.compile("^[+-]?\\d+$");
    private static final Pattern PADRAO_VALIDADE_CARTAO = Pattern.compile("^(0[1-9]|1[0-2])/\\d{4}$");

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

    public static void validarChavePix(String chavePix) throws ChavePixNulaException {
        if (estaVazio(chavePix)) {
            throw new ChavePixNulaException();
        }
    }

    public static void validarNumeroCartao(String numeroCartao) throws NumeroCartaoNuloException {
        if (estaVazio(numeroCartao)) {
            throw new NumeroCartaoNuloException();
        }
    }

    public static void validarValidadeCartao(String validade)
            throws ValidadeCartaoNulaException, ValidadeCartaoInvalidaException {
        if (estaVazio(validade)) {
            throw new ValidadeCartaoNulaException();
        }
        if (!PADRAO_VALIDADE_CARTAO.matcher(validade.trim()).matches()) {
            throw new ValidadeCartaoInvalidaException();
        }
    }

    public static void validarEndereco(String endereco) throws EnderecoNuloException {
        if (estaVazio(endereco)) {
            throw new EnderecoNuloException();
        }
    }

    public static void validarIdentificacaoEstacao(String idEstacao) throws IdentificacaoEstacaoNulaException {
        if (estaVazio(idEstacao)) {
            throw new IdentificacaoEstacaoNulaException();
        }
    }

    public static void validarIdentificacaoBicicleta(String idBicicleta) throws IdentificacaoBicicletaNulaException {
        if (estaVazio(idBicicleta)) {
            throw new IdentificacaoBicicletaNulaException();
        }
    }

    /**
     * Converte e valida uma string de mensalidade em {@link BigDecimal} nao-negativo.
     */
    public static BigDecimal parsearMensalidade(String mensalidade)
            throws MensalidadeNulaException, MensalidadeNaoNumericaException, MensalidadeNegativaException {
        if (estaVazio(mensalidade)) {
            throw new MensalidadeNulaException();
        }
        BigDecimal valor = tentarParsearDecimal(mensalidade);
        if (valor == null) {
            throw new MensalidadeNaoNumericaException();
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new MensalidadeNegativaException();
        }
        return valor;
    }

    /**
     * Converte e valida uma string numerica em {@link BigDecimal} estritamente positivo.
     */
    public static BigDecimal parsearValorPositivo(String valorTexto)
            throws ValorNuloException, ValorNaoNumericoException, ValorNaoPositivoException {
        if (estaVazio(valorTexto)) {
            throw new ValorNuloException();
        }
        BigDecimal valor = tentarParsearDecimal(valorTexto);
        if (valor == null) {
            throw new ValorNaoNumericoException();
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorNaoPositivoException();
        }
        return valor;
    }

    /**
     * Converte e valida a capacidade de uma estacao como inteiro estritamente positivo.
     */
    public static int parsearCapacidade(String capacidadeTexto)
            throws CapacidadeNulaException, CapacidadeNaoNumericaException, CapacidadeNaoPositivaException {
        if (estaVazio(capacidadeTexto)) {
            throw new CapacidadeNulaException();
        }
        Integer valor = tentarParsearInteiro(capacidadeTexto);
        if (valor == null) {
            throw new CapacidadeNaoNumericaException();
        }
        if (valor <= 0) {
            throw new CapacidadeNaoPositivaException();
        }
        return valor;
    }

    /**
     * Converte e valida o nivel de bateria de uma bicicleta eletrica como inteiro entre 0 e 100.
     */
    public static int parsearNivelBateria(String nivelBateriaTexto)
            throws NivelBateriaNuloException, NivelBateriaNaoNumericoException, NivelBateriaInvalidoException {
        if (estaVazio(nivelBateriaTexto)) {
            throw new NivelBateriaNuloException();
        }
        Integer valor = tentarParsearInteiro(nivelBateriaTexto);
        if (valor == null) {
            throw new NivelBateriaNaoNumericoException();
        }
        if (valor < BicicletaEletrica.BATERIA_MINIMA || valor > BicicletaEletrica.BATERIA_MAXIMA) {
            throw new NivelBateriaInvalidoException();
        }
        return valor;
    }

    /**
     * Cria o objeto de dominio {@link Plano} correspondente ao nome e mensalidade informados.
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

    private static BigDecimal tentarParsearDecimal(String texto) {
        String normalizado = texto.trim();
        if (!PADRAO_NUMERO_DECIMAL.matcher(normalizado).matches()) {
            return null;
        }
        try {
            return new BigDecimal(normalizado.replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Integer tentarParsearInteiro(String texto) {
        String normalizado = texto.trim();
        if (!PADRAO_INTEIRO.matcher(normalizado).matches()) {
            return null;
        }
        try {
            return Integer.valueOf(normalizado);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
