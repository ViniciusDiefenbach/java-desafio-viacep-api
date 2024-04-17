package principal;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ViaCepDto;
import models.Cep;
import services.ViaCep;
import validations.CepValidation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Menu {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Cep> cepList = new ArrayList<>();
        String jsonPath = "ceps.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        while (true) {
            System.out.println("Seja bem vindo ao Desafio de consumo da API ViaCep!");
            System.out.println("1 - Buscar endereço(s)");
            System.out.println("2 - Salvar buscas em um arquivo");
            System.out.println("3 - Ordenar buscas");
            System.out.println("4 - Limpar histórico de busca");
            System.out.println("5 - Limpar arquivo");
            System.out.println("0 - Sair");
            System.out.println("Escolha uma das opções acima: ");
            var escolha = scanner.nextLine();
            var deveEncerrar = false;

            switch (escolha) {
                case "1":
                    buscarEnderecos(scanner, gson, cepList);
                    break;
                case "2":
                    salvarBuscas(jsonPath, gson, cepList);
                    break;
                case "3":
                    ordenarBuscas(scanner, cepList);
                    break;
                case "4":
                    cepList = new ArrayList<>();
                    break;
                case "5":
                    FileWriter fileWriter = new FileWriter(jsonPath);
                    fileWriter.write("");
                    fileWriter.close();
                    break;
                case "0":
                    deveEncerrar = true;
                    break;
            }
            if (deveEncerrar) break;
        }
    }

    private static void buscarEnderecos(Scanner scanner, Gson gson, List<Cep> cepList) {
        System.out.println("Digite o CEP que deseja buscar: ");
        var entrada = scanner.nextLine().split(", ");
        List<String> erros = new ArrayList<>();
        for (int i = 0; i < entrada.length; i++) {
            try {
                var cepBusca = CepValidation.validate(entrada[i]);
                var resultado = ViaCep.busca(cepBusca);
                ViaCepDto viaCepDto = gson.fromJson(resultado, ViaCepDto.class);
                Cep cep = new Cep(viaCepDto);
                if (!cep.equals(new Cep())) cepList.add(cep);
            } catch (Exception e) {
                erros.add(STR."CEP \{i + 1} -> \{e.getMessage()}");
            }
        }
        if (erros.isEmpty()) System.out.println("Sucesso!");
        else System.out.println(erros);
    }

    private static void salvarBuscas(String jsonPath, Gson gson, List<Cep> cepList) throws IOException {
        FileWriter fileWriter = new FileWriter(jsonPath);
        fileWriter.write(gson.toJson(cepList));
        fileWriter.close();
    }

    private static void ordenarBuscas(Scanner scanner, List<Cep> cepList) {
        System.out.println("Tipos de ordenação...");
        System.out.println("1 - Ordenar por CEP");
        System.out.println("2 - Ordenar por logradouro");
        System.out.println("3 - Ordenar por bairro");
        System.out.println("4 - Ordenar por localidade");
        System.out.println("5 - Ordenar por uf");
        System.out.println("0 - Sair");
        System.out.println("Digite o tipo de ordenação: ");
        var escolha = scanner.nextLine();
        switch (escolha) {
            case "1":
                Collections.sort(cepList);
                break;
            case "2":
                cepList.sort(Comparator.comparing(Cep::getLogradouro));
                break;
            case "3":
                cepList.sort(Comparator.comparing(Cep::getBairro));
                break;
            case "4":
                cepList.sort(Comparator.comparing(Cep::getLocalidade));
                break;
            case "5":
                cepList.sort(Comparator.comparing(Cep::getUf));
                break;
            case "0":
                break;
            default:
                System.out.println("Opção inválida");
        }
    }
}
