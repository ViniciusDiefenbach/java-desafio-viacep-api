package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCep {
    public static String busca(String cep) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(STR."http://viacep.com.br/ws/\{cep}/json/"))
                .setHeader("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }
}
