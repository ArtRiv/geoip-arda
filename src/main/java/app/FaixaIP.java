package app;

public class FaixaIP implements Comparable<FaixaIP> {
    private final long inicioIP;
    private final long fimIP;
    private final int idGeoname;

    public FaixaIP(long inicioIP, long fimIP, int idGeoname) {
        this.inicioIP = inicioIP;
        this.fimIP = fimIP;
        this.idGeoname = idGeoname;
    }

    public boolean contem(long ip) {
        return ip >= inicioIP && ip <= fimIP;
    }

    public int obtemIdGeoname() {
        return idGeoname;
    }

    public long obtemInicioIP() {
        return inicioIP;
    }

    public long obtemFimIP() {
        return fimIP;
    }

    @Override
    public int compareTo(FaixaIP outraFaixa) {
        return Long.compare(this.inicioIP, outraFaixa.inicioIP);
    }

    @Override
    public String toString() {
        return "FaixaIP [inicioIP=" + inicioIP
             + ", fimIP=" + fimIP
             + ", idGeoname=" + idGeoname
             + "]";
    }
}
