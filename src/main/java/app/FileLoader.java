package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import esd.APB;
import esd.Lista;
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

            System.out.println("Total de linhas de localidades lidas: " + contador);
        }

        System.out.println("Carregadas " + localizacoes.tamanho() + " localidades.");
    }

    public static void carregarIPRanges(
            APB<IPRange> ipRanges,
            TabHash<Integer, Localidade> localizacoes,
            String IPV4_BLOCKS) throws Exception {

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(IPV4_BLOCKS);
        if (is == null) throw new FileNotFoundException("Arquivo de blocos IPv4 não encontrado: " + IPV4_BLOCKS);

        Lista<IPRange> rangesListLigada = new Lista<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            reader.readLine();
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length >= 2) {
                    try {
                        String rede = campos[0];
                        int geonameId = Integer.parseInt(campos[1]);
                        if (localizacoes.contem(geonameId)) {
                            rangesListLigada.adiciona(IPUtil.cidrToRange(rede, geonameId));
                        }
                    } catch (Exception e) { }
                }
            }
        }
        System.out.println("Lidos " + rangesListLigada.comprimento() + " blocos de IP para a sua Lista (ligada).");

        System.out.println("Iniciando ordenação da Lista (ligada)...");
        rangesListLigada.ordena();
        System.out.println("Sua Lista (ligada) foi ordenada.");

        System.out.println("Convertendo para ListaSequencial para construção otimizada da árvore...");
        ListaSequencial<IPRange> rangesListSequencial = new ListaSequencial<>();
        for (int i = 0; i < rangesListLigada.comprimento(); i++) {
            rangesListSequencial.adiciona(rangesListLigada.obtem(i));
        }
        rangesListLigada = null; 

        System.out.println("Construindo a árvore balanceada a partir da ListaSequencial...");
        ipRanges.constroiDeListaOrdenada(rangesListSequencial);
        System.out.println("Árvore de intervalos IP construída com sucesso de forma balanceada.");
    }
}
