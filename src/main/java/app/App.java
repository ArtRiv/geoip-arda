package app;

import esd.TabHash;
import esd.APB;
import esd.APB.NodoAPB;

public class App {

    final String IPV4_BLOCKS = "GeoLite2-City-Blocks-IPv4.csv";
    final String CITY_LOCATIONS = "GeoLite2-City-Locations-pt-BR.csv";

    private TabHash<Integer, Localidade> localizacoes;
    private APB<FaixaIP> faixaIP;

    public App() {
        localizacoes = new TabHash<>();
        faixaIP = new APB<>();

        try {
            FileLoader.carregarLocalidades(localizacoes, CITY_LOCATIONS);
            FileLoader.carregarFaixasIP(faixaIP, localizacoes, IPV4_BLOCKS);
        }
        catch (Exception e) {
            throw new IllegalStateException("Erro ao processar algum dos arquivos: " + e.getMessage(), e);
        }
    }
    
    private FaixaIP encontrarFaixaIP(long ip) {
        NodoAPB<FaixaIP> raizNodo = faixaIP.raiz; 
        FaixaIP resultado = encontrarFaixaIPRec(raizNodo, ip);
        return resultado;
    }

    private FaixaIP encontrarFaixaIPRec(NodoAPB<FaixaIP> nodo, long ip) {
        if (nodo == null) {
            return null;
        }
        FaixaIP r = nodo.valor;
        if (r.contem(ip)) {
            return r;
        } 
        else if (ip < r.obtemInicioIP()) {
            return encontrarFaixaIPRec(nodo.esq, ip);
        } else {
            return encontrarFaixaIPRec(nodo.dir, ip);
        }
    }

    public Localidade busca_localidade(String ip) {
        try {
            long IPFormatadoParaNumerico = UtilidadesIP.ipParaLongo(ip);
            FaixaIP faixa = encontrarFaixaIP(IPFormatadoParaNumerico);
            if (faixa != null) {
                return localizacoes.obtem(faixa.obtemIdGeoname());
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar localidade para IP " + ip + ": " + e.getMessage());
        }
        return null;
    }
}
