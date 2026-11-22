package br.ufal.ic.p2.bikeshare.servicos;

import br.ufal.ic.p2.bikeshare.excecoes.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecoes.CapacidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.excecoes.CapacidadeNaoPositivaException;
import br.ufal.ic.p2.bikeshare.excecoes.CapacidadeNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.ChavePixNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.CpfNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.DataInvalidaException;
import br.ufal.ic.p2.bikeshare.excecoes.DataNulaException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.excecoes.EmailNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.EnderecoNuloException;
import br.ufal.ic.p2.bikeshare.excecoes.IdentificacaoAluguelNulaException;
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
import br.ufal.ic.p2.bikeshare.modelos.ConfiguracaoSistema;
import br.ufal.ic.p2.bikeshare.modelos.FormatadorDataHora;
import br.ufal.ic.p2.bikeshare.modelos.Plano;
import br.ufal.ic.p2.bikeshare.modelos.PlanoAssinante;
import br.ufal.ic.p2.bikeshare.modelos.PlanoAvulso;
import br.ufal.ic.p2.bikeshare.modelos.TipoPlano;
import br.ufal.ic.p2.bikeshare.modelos.TipoUsuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Centraliza as validacoes e conversoes de dados textuais de entrada para objetos de dominio.
 */
public final class ValidadorEntrada {

    private static final Pattern PADRAO_EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern PADRAO_NUMERO_DECIMAL = Pattern.compile("^[+-]?\\d+([.,]\\d+)?$");
    private static final Pattern PADRAO_INTEIRO = Pattern.compile("^[+-]?\\d+$");
    private static final Pattern PADRAO_VALIDADE_CARTAO = Pattern.compile("^(0[1-9]|1[0-2])/\\d{4}$");
    private static final Pattern PADRAO_DATA_HORA = Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$");

    private ValidadorEntrada() {
    }

    /**
     * Verifica se um texto e nulo ou composto apenas por espacos em branco.
     *
     * @param valor texto a verificar
     * @return {@code true} se o texto estiver vazio
     */
    public static boolean estaVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    /**
     * Valida que o nome informado nao e nulo nem vazio.
     *
     * @param nome nome a validar
     * @throws NomeNuloException se o nome for nulo ou em branco
     */
    public static void validarNome(String nome) throws NomeNuloException {
        if (estaVazio(nome)) {
            throw new NomeNuloException();
        }
    }

    /**
     * Valida que o email informado nao e nulo nem vazio.
     *
     * @param email email a validar
     * @throws EmailNuloException se o email for nulo ou em branco
     */
    public static void validarEmailObrigatorio(String email) throws EmailNuloException {
        if (estaVazio(email)) {
            throw new EmailNuloException();
        }
    }

    /**
     * Valida obrigatoriedade e formato sintatico de um endereco de email.
     *
     * @param email email a validar
     * @throws EmailNuloException     se o email for nulo ou em branco
     * @throws EmailInvalidoException se o formato do email for invalido
     */
    public static void validarEmail(String email) throws EmailNuloException, EmailInvalidoException {
        validarEmailObrigatorio(email);
        if (!PADRAO_EMAIL.matcher(email.trim()).matches()) {
            throw new EmailInvalidoException();
        }
    }

    /**
     * Valida que a senha informada nao e nula nem vazia.
     *
     * @param senha senha a validar
     * @throws SenhaNulaException se a senha for nula ou em branco
     */
    public static void validarSenha(String senha) throws SenhaNulaException {
        if (estaVazio(senha)) {
            throw new SenhaNulaException();
        }
    }

    /**
     * Valida que o texto de tipo de usuario corresponde exatamente ao tipo esperado.
     *
     * @param tipoTexto    codigo textual do tipo de usuario
     * @param tipoEsperado tipo de usuario exigido pela operacao
     * @throws TipoUsuarioInvalidoException se o tipo for nulo, desconhecido ou diferente do esperado
     */
    public static void validarTipoEsperado(String tipoTexto, TipoUsuario tipoEsperado)
            throws TipoUsuarioInvalidoException {
        TipoUsuario tipo = TipoUsuario.deCodigo(tipoTexto);
        if (tipo != tipoEsperado) {
            throw new TipoUsuarioInvalidoException();
        }
    }

    /**
     * Valida que o CPF informado nao e nulo nem vazio.
     *
     * @param cpf CPF a validar
     * @throws CpfNuloException se o CPF for nulo ou em branco
     */
    public static void validarCpf(String cpf) throws CpfNuloException {
        if (estaVazio(cpf)) {
            throw new CpfNuloException();
        }
    }

    /**
     * Valida que o CNPJ informado nao e nulo nem vazio.
     *
     * @param cnpj CNPJ a validar
     * @throws CnpjNuloException se o CNPJ for nulo ou em branco
     */
    public static void validarCnpj(String cnpj) throws CnpjNuloException {
        if (estaVazio(cnpj)) {
            throw new CnpjNuloException();
        }
    }

    /**
     * Valida que a razao social informada nao e nula nem vazia.
     *
     * @param razaoSocial razao social a validar
     * @throws RazaoSocialNulaException se a razao social for nula ou em branco
     */
    public static void validarRazaoSocial(String razaoSocial) throws RazaoSocialNulaException {
        if (estaVazio(razaoSocial)) {
            throw new RazaoSocialNulaException();
        }
    }

    /**
     * Valida que a identificacao do usuario nao e nula nem vazia.
     *
     * @param idUsuario identificador do usuario
     * @throws IdentificacaoUsuarioNulaException se o identificador for nulo ou em branco
     */
    public static void validarIdentificacaoUsuario(String idUsuario) throws IdentificacaoUsuarioNulaException {
        if (estaVazio(idUsuario)) {
            throw new IdentificacaoUsuarioNulaException();
        }
    }

    /**
     * Valida que a chave Pix informada nao e nula nem vazia.
     *
     * @param chavePix chave Pix a validar
     * @throws ChavePixNulaException se a chave for nula ou em branco
     */
    public static void validarChavePix(String chavePix) throws ChavePixNulaException {
        if (estaVazio(chavePix)) {
            throw new ChavePixNulaException();
        }
    }

    /**
     * Valida que o numero do cartao informado nao e nulo nem vazio.
     *
     * @param numeroCartao numero do cartao a validar
     * @throws NumeroCartaoNuloException se o numero for nulo ou em branco
     */
    public static void validarNumeroCartao(String numeroCartao) throws NumeroCartaoNuloException {
        if (estaVazio(numeroCartao)) {
            throw new NumeroCartaoNuloException();
        }
    }

    /**
     * Valida obrigatoriedade e formato ({@code MM/AAAA}) da validade do cartao.
     *
     * @param validade validade textual do cartao
     * @throws ValidadeCartaoNulaException     se a validade for nula ou em branco
     * @throws ValidadeCartaoInvalidaException se o formato ou mes for invalido
     */
    public static void validarValidadeCartao(String validade)
            throws ValidadeCartaoNulaException, ValidadeCartaoInvalidaException {
        if (estaVazio(validade)) {
            throw new ValidadeCartaoNulaException();
        }
        if (!PADRAO_VALIDADE_CARTAO.matcher(validade.trim()).matches()) {
            throw new ValidadeCartaoInvalidaException();
        }
    }

    /**
     * Valida que o endereco da estacao nao e nulo nem vazio.
     *
     * @param endereco endereco a validar
     * @throws EnderecoNuloException se o endereco for nulo ou em branco
     */
    public static void validarEndereco(String endereco) throws EnderecoNuloException {
        if (estaVazio(endereco)) {
            throw new EnderecoNuloException();
        }
    }

    /**
     * Valida que a identificacao da estacao nao e nula nem vazia.
     *
     * @param idEstacao identificador da estacao
     * @throws IdentificacaoEstacaoNulaException se o identificador for nulo ou em branco
     */
    public static void validarIdentificacaoEstacao(String idEstacao) throws IdentificacaoEstacaoNulaException {
        if (estaVazio(idEstacao)) {
            throw new IdentificacaoEstacaoNulaException();
        }
    }

    /**
     * Valida que a identificacao da bicicleta nao e nula nem vazia.
     *
     * @param idBicicleta identificador da bicicleta
     * @throws IdentificacaoBicicletaNulaException se o identificador for nulo ou em branco
     */
    public static void validarIdentificacaoBicicleta(String idBicicleta) throws IdentificacaoBicicletaNulaException {
        if (estaVazio(idBicicleta)) {
            throw new IdentificacaoBicicletaNulaException();
        }
    }

    /**
     * Valida que a identificacao do aluguel nao e nula nem vazia.
     *
     * @param idAluguel identificador do aluguel
     * @throws IdentificacaoAluguelNulaException se o identificador for nulo ou em branco
     */
    public static void validarIdentificacaoAluguel(String idAluguel) throws IdentificacaoAluguelNulaException {
        if (estaVazio(idAluguel)) {
            throw new IdentificacaoAluguelNulaException();
        }
    }

    /**
     * Converte e valida uma string de data e hora no formato {@code d/M/uuuu H:mm} com calendario estrito.
     *
     * @param dataTexto representacao textual da data e hora
     * @return instancia valida de {@link LocalDateTime}
     * @throws DataNulaException     se a data for nula ou em branco
     * @throws DataInvalidaException se o formato, horario ou data no calendario for invalido
     */
    public static LocalDateTime parsearDataHora(String dataTexto)
            throws DataNulaException, DataInvalidaException {
        if (estaVazio(dataTexto)) {
            throw new DataNulaException();
        }
        String texto = dataTexto.trim();
        if (!PADRAO_DATA_HORA.matcher(texto).matches()) {
            throw new DataInvalidaException();
        }
        try {
            return LocalDateTime.parse(texto, FormatadorDataHora.getFormatador());
        } catch (DateTimeParseException e) {
            throw new DataInvalidaException();
        }
    }

    /**
     * Converte e valida uma string de mensalidade em {@link BigDecimal} nao-negativo.
     *
     * @param mensalidade representacao textual da mensalidade
     * @return valor monetario nao-negativo
     * @throws MensalidadeNulaException        se a mensalidade for nula ou em branco
     * @throws MensalidadeNaoNumericaException se o texto nao representar um numero valido
     * @throws MensalidadeNegativaException    se o valor for negativo
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
     *
     * @param valorTexto representacao textual do valor
     * @return valor monetario positivo
     * @throws ValorNuloException        se o texto for nulo ou em branco
     * @throws ValorNaoNumericoException se o texto nao representar um numero valido
     * @throws ValorNaoPositivoException se o valor for menor ou igual a zero
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
     *
     * @param capacidadeTexto representacao textual da capacidade
     * @return capacidade inteira positiva
     * @throws CapacidadeNulaException         se a capacidade for nula ou em branco
     * @throws CapacidadeNaoNumericaException  se o texto nao representar um inteiro valido
     * @throws CapacidadeNaoPositivaException  se o valor for menor ou igual a zero
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
     *
     * @param nivelBateriaTexto representacao textual do nivel de bateria
     * @return nivel de bateria inteiro entre 0 e 100
     * @throws NivelBateriaNuloException        se o nivel for nulo ou em branco
     * @throws NivelBateriaNaoNumericoException se o texto nao representar um inteiro valido
     * @throws NivelBateriaInvalidoException    se o nivel estiver fora do intervalo [0, 100]
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
     * Cria o objeto de dominio {@link Plano} correspondente ao nome, mensalidade e configuracao vigentes.
     *
     * @param nomePlano    nome exato do plano ({@code avulso} ou {@code assinante})
     * @param mensalidade  mensalidade informada (obrigatoria para assinante, vazia para avulso)
     * @param configuracao parametros configuraveis de tarifacao do sistema
     * @return instancia concreta de {@link Plano}
     * @throws BikeShareException se o plano ou a mensalidade forem invalidos
     */
    public static Plano criarPlano(String nomePlano, String mensalidade, ConfiguracaoSistema configuracao)
            throws BikeShareException {
        TipoPlano tipoPlano = TipoPlano.deCodigo(nomePlano);
        switch (tipoPlano) {
            case AVULSO:
                if (!estaVazio(mensalidade)) {
                    throw new PlanoNaoPossuiMensalidadeException();
                }
                return new PlanoAvulso(configuracao.getTarifaMinutoAvulso());
            case ASSINANTE:
                BigDecimal valorMensalidade = parsearMensalidade(mensalidade);
                return new PlanoAssinante(
                        valorMensalidade,
                        configuracao.getFranquiaMinutosAssinante(),
                        configuracao.getTarifaMinutoExcedenteAssinante()
                );
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
