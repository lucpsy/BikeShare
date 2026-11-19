package br.ufal.ic.p2.bikeshare.modelos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Utilitario para formatacao de valores monetarios no padrao brasileiro (duas casas decimais com virgula).
 */
public final class FormatadorMonetario {

    private static final Locale LOCALE_PT_BR = Locale.forLanguageTag("pt-BR");

    private FormatadorMonetario() {
    }

    /**
     * Formata um valor monetario com duas casas decimais e virgula como separador decimal,
     * sem separador de milhar.
     *
     * @param valor valor numerico a ser formatado
     * @return representacao textual formatada (ex.: {@code "50,00"})
     */
    public static String formatar(BigDecimal valor) {
        BigDecimal escalado = valor.setScale(2, RoundingMode.HALF_UP);
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(LOCALE_PT_BR);
        DecimalFormat formato = new DecimalFormat("0.00", simbolos);
        return formato.format(escalado);
    }
}
