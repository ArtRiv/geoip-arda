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
            System.out.println(">> [DEBUG] Localidades carregadas: " + localizacoes.tamanho());

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
    

    private IPRange findIPRangeContainingIP(long IP) {
        return findIPRangeRec(ipRanges.procura(new IPRange(IP, IP, 0)), IP);
    }

    private IPRange findIPRangeRec(NodoAPB<IPRange> nodo, long ip) {
        if (nodo == null) {
            return null;
        }
        
        IPRange range = nodo.valor;
        
        if (range.contains(ip)) {
            return range;
        }
        
        if (ip < range.getStartIP()) {
            if (nodo.esq != null) {
                IPRange resultado = findIPRangeRec(nodo.esq, ip);
                if (resultado != null) return resultado;
            }
        } else {
            if (nodo.dir != null) {
                IPRange resultado = findIPRangeRec(nodo.dir, ip);
                if (resultado != null) return resultado;
            }
        }
        
        if (nodo.pai != null) {
            IPRange paiRange = nodo.pai.valor;
            if (paiRange.contains(ip)) {
                return paiRange;
            }
        }
        
        return null;
    }

    public Localidade busca_localidade(String IP) {
        try {
            long numericIP = IPUtil.ipToLong(IP);
            IPRange range = findIPRangeContainingIP(numericIP);
            if (range != null) {
                return localizacoes.obtem(range.getGeonameId());
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar localidade para IP " + IP + ": " + e.getMessage());
        }
        return null;
    }
}
