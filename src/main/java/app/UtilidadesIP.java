package app;

public class UtilidadesIP {
    public static long ipParaLongo(String enderecoIp) {
        String[] octetos = enderecoIp.split("\\.");
        if (octetos.length != 4) {
            throw new IllegalArgumentException("Formato de IP inválido: " + enderecoIp);
        }
        long resultado = 0;
        for (int i = 0; i < 4; i++) {
            int valor = Integer.parseInt(octetos[i]);
            resultado = (resultado << 8) | valor;
        }
        return resultado;
    }

    public static long obtemFimIpDeCidr(long inicioIp, int mascara) {
        int bitsHosts = 32 - mascara;
        return inicioIp | ((1L << bitsHosts) - 1);
    }

    public static FaixaIP cidrParaFaixa(String cidr, int idGeoname) {
        String[] partes = cidr.split("/");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato CIDR inválido: " + cidr);
        }
        String enderecoIp = partes[0];
        int prefixo = Integer.parseInt(partes[1]);

        long inicioIp = ipParaLongo(enderecoIp);
        long fimIp = obtemFimIpDeCidr(inicioIp, prefixo);

        return new FaixaIP(inicioIp, fimIp, idGeoname);
    }

    public static String longoParaIp(long ipNumerico) {
        return ((ipNumerico >> 24) & 0xFF) + "." +
               ((ipNumerico >> 16) & 0xFF) + "." +
               ((ipNumerico >> 8)  & 0xFF) + "." +
               ( ipNumerico        & 0xFF);
    }
}
