package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import esd.APB;
import esd.ListaSequencial;
import esd.TabHash;

public class FileLoader {

    public static void carregarLocalidades(
            TabHash<Integer, Localidade> localizacoes,
            String CITY_LOCATIONS) throws Exception {

        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(CITY_LOCATIONS);
        if (is == null) {
            throw new FileNotFoundException("Arquivo de localidades não encontrado: " + CITY_LOCATIONS);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String header = reader.readLine();

            String linha;
            int contador = 0;
            while ((linha = reader.readLine()) != null) {
                contador++;

                String[] campos = linha.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                if (campos.length >= 6) {
                    try {
                        int geonameId = Integer.parseInt(campos[0]);
                        String nomePais = campos[5].replace("\"", "");

                        String localidade;
                        if (campos.length >= 10 && !campos[10].isEmpty() && !campos[10].equals("\"\"")) {
                            localidade = campos[10].replace("\"", "");
                        } else if (campos.length >= 8 && !campos[7].isEmpty() && !campos[7].equals("\"\"")) {
                            localidade = campos[7].replace("\"", "");
                        } else {
                            localidade = nomePais;
                        }

                        localizacoes.adiciona(geonameId, new Localidade(nomePais, localidade));
                    } catch (NumberFormatException e) {
                        System.err.println("Linha " + contador + " inválida ao parsear geonameId: " + linha);
                    }
                }
            }
        }
    }

    public static void carregarFaixasIP(
            APB<FaixaIP> faixaIP,
            TabHash<Integer, Localidade> localizacoes,
            String IPV4_BLOCKS) throws Exception {

        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(IPV4_BLOCKS);
        if (is == null) {
            throw new FileNotFoundException("Arquivo de blocos IPv4 não encontrado: " + IPV4_BLOCKS);
        }

        ListaSequencial<FaixaIP> rangesListSequencial = new ListaSequencial<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {

            reader.readLine();

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length < 2) {
                    continue;
                }

                String rede = campos[0];
                int geonameId;
                try {
                    geonameId = Integer.parseInt(campos[1]);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (!localizacoes.contem(geonameId)) {
                    continue;
                }

                FaixaIP range = UtilidadesIP.cidrParaFaixa(rede, geonameId);
                rangesListSequencial.adiciona(range);
            }
        }

        ordenarListaSequencial(rangesListSequencial);

        faixaIP.constroiDeListaOrdenada(rangesListSequencial);
    }

    private static void ordenarListaSequencial(ListaSequencial<FaixaIP> lista) {
        if (lista.comprimento() <= 1)
            return;
        mergeSort(lista, 0, lista.comprimento() - 1, new FaixaIP[lista.comprimento()]);
    }

    private static void mergeSort(ListaSequencial<FaixaIP> lista, int inicio, int fim, FaixaIP[] aux) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(lista, inicio, meio, aux);
            mergeSort(lista, meio + 1, fim, aux);
            merge(lista, inicio, meio, fim, aux);
        }
    }

    private static void merge(ListaSequencial<FaixaIP> lista, int inicio, int meio, int fim, FaixaIP[] aux) {
        int i = inicio, j = meio + 1, k = inicio;
        while (i <= meio && j <= fim) {
            if (lista.obtem(i).compareTo(lista.obtem(j)) <= 0) {
                aux[k++] = lista.obtem(i++);
            } else {
                aux[k++] = lista.obtem(j++);
            }
        }
        while (i <= meio) {
            aux[k++] = lista.obtem(i++);
        }
        while (j <= fim) {
            aux[k++] = lista.obtem(j++);
        }
        for (int l = inicio; l <= fim; l++) {
            lista.substitui(l, aux[l]);
        }
    }
}