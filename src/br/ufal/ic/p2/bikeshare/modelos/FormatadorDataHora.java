package br.ufal.ic.p2.bikeshare.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Utilitario para formatacao de datas e horarios no padrao textual do sistema BikeShare.
 */
public final class FormatadorDataHora {

    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter
            .ofPattern("d/M/uuuu H:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private FormatadorDataHora() {
    }

    /**
     * Formata uma data e hora no padrao {@code d/M/uuuu H:mm}, sem zero a esquerda em dia, mes ou hora.
     *
     * @param dataHora data e hora a formatar
     * @return representacao textual formatada (ex.: {@code "10/3/2025 8:00"})
     */
    public static String formatar(LocalDateTime dataHora) {
        return dataHora.format(FORMATADOR);
    }

    /**
     * Retorna o formatador estrito utilizado para leitura e escrita de datas e horarios.
     *
     * @return instancia de {@link DateTimeFormatter} com resolucao estrita
     */
    public static DateTimeFormatter getFormatador() {
        return FORMATADOR;
    }
}
