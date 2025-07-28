package app;

import java.io.*;

import esd.TabHash;
import esd.APB;
import esd.ListaSequencial;
import esd.APB.NodoAPB;

public class App {

    final String IPV4_BLOCKS = "GeoLite2-City-Blocks-IPv4.csv";
    final String CITY_LOCATIONS = "GeoLite2-City-Locations-pt-BR.csv";

    private TabHash<Integer, Localidade> localizacoes;
    private APB<IPRange> ipRanges;
    private ListaSequencial<IPRange> rangesList;

    public App() {
        localizacoes = new TabHash<>();
        ipRanges = new APB<>();
        rangesList = new ListaSequencial<>();

        try {
            System.out.println(">> [DEBUG] Tentando carregar localidades: " + CITY_LOCATIONS);
            System.out.println("   resource URL: " +
                Thread.currentThread()
                      .getContextClassLoader()
                      .getResource(CITY_LOCATIONS));
            FileLoader.carregarLocalidades(localizacoes, CITY_LOCATIONS);
            System.out.println(">> [DEBUG] Localidades carregadas: " + localizacoes.comprimento());

            System.out.println(">> [DEBUG] Tentando carregar IP ranges: " + IPV4_BLOCKS);
            System.out.println("   resource URL: " +
                Thread.currentThread()
                      .getContextClassLoader()
                      .getResource(IPV4_BLOCKS));
            FileLoader.carregarIPRanges(ipRanges, localizacoes, IPV4_BLOCKS);
            System.out.println(">> [DEBUG] IP ranges carregados: " + ipRanges.tamanho());
        }
        catch (Exception e) {
            // mostra stacktrace real
            e.printStackTrace();
            // rethrow com causa
            throw new IllegalStateException(
                "Erro ao processar algum dos arquivos: " + e.getMessage(), e);
        }
    }
    

    private IPRange findIPRangeContainingIP(long ip) {
        System.out.println("[DEBUG-SEARCH] procurando IP numérico = " + ip);
        NodoAPB<IPRange> raizNodo = ipRanges.raiz; 
        IPRange resultado = findIPRangeRec(raizNodo, ip);
        System.out.println("[DEBUG-SEARCH] resultado = " + resultado);
        return resultado;
    }

    private IPRange findIPRangeRec(NodoAPB<IPRange> nodo, long ip) {
        if (nodo == null) {
            System.out.println("[TRACE] nodo NULO — retorna null");
            return null;
        }
        IPRange r = nodo.valor;
        System.out.printf("[TRACE] visitando nó: start=%d, end=%d%n",
                          r.getStartIP(), r.getEndIP());
        if (r.contains(ip)) {
            System.out.println("[TRACE] encontrou aqui!");
            return r;
        } 
        else if (ip < r.getStartIP()) {
            return findIPRangeRec(nodo.esq, ip);
        } else {
            return findIPRangeRec(nodo.dir, ip);
        }
    }

    public Localidade busca_localidade(String IP) {
        try {
            System.out.println("IP: " + IP);
            long numericIP = IPUtil.ipToLong(IP);
            System.out.println("numericIP: " + numericIP);
            IPRange range = findIPRangeContainingIP(numericIP);
            System.out.println("range: " + range);
            if (range != null) {
                return localizacoes.obtem(range.getGeonameId());
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar localidade para IP " + IP + ": " + e.getMessage());
        }
        return null;
    }
}
