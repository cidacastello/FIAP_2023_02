package br.com.fiap.apicep.service;

import br.com.fiap.apicep.dto.EnderecoDTO;
import br.com.fiap.apicep.entity.Endereco;
import br.com.fiap.apicep.service.exception.ClientHttpException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
@Service
public class EnderecoService {

    public EnderecoDTO getCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        try {
            URI uri = URI.create(url);
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String body = response.body();

            ObjectMapper mapper = new ObjectMapper();
            Endereco endereco = mapper.readValue(body, Endereco.class);
            EnderecoDTO dto = new EnderecoDTO(endereco);
            return dto;

        } catch (IOException | InterruptedException e) {
            throw new ClientHttpException("Erro");
        }
    }
}

