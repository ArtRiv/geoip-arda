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
            FileLoader.carregarLocalidades(localizacoes, CITY_LOCATIONS);
            FileLoader.carregarIPRanges(ipRanges, localizacoes, IPV4_BLOCKS);
        }
        catch (Exception e) {
            throw new IllegalStateException("Erro ao processar algum dos arquivos: " + e.getMessage(), e);
        }
    }
    

    private IPRange findIPRangeContainingIP(long ip) {
        NodoAPB<IPRange> raizNodo = ipRanges.raiz; 
        IPRange resultado = findIPRangeRec(raizNodo, ip);
        return resultado;
    }

    private IPRange findIPRangeRec(NodoAPB<IPRange> nodo, long ip) {
        if (nodo == null) {
            return null;
        }
        IPRange r = nodo.valor;
        if (r.contains(ip)) {
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
