package br.com.alura.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGemini {

    private static final String MODELO = "gemini-3-flash-preview";
    private static final ConsultaGemini INSTANCE = new ConsultaGemini();

    private final Client client;

    private ConsultaGemini() {
        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "GEMINI_API_KEY nao encontrada. "
                            + "Configure em Run > Edit Configurations > Environment variables.");
        }

        this.client = Client.builder().apiKey(apiKey).build();
    }

    public static String traduzir(String texto) {
        return INSTANCE.traduzirInterno(texto);
    }

    private String traduzirInterno(String texto) {
        if (texto == null || texto.isBlank() || texto.equalsIgnoreCase("N/A")) {
            return texto;
        }

        try {
            GenerateContentResponse resposta = client.models.generateContent(
                    MODELO,
                    "Traduza para portugues do Brasil o trecho a seguir, da forma mais direta. "
                            + "Responda apenas com a traducao, sem comentarios:\n\n" + texto,
                    null);

            return resposta.text().trim();

        } catch (RuntimeException e) {
            System.out.println("[traducao indisponivel] " + e.getMessage());
            return texto;
        }
    }
}