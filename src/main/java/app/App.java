package app;

import java.io.*;

public class App {

    final String IPV4_BLOCKS = "GeoLite2-City-Blocks-IPv4.csv";
    final String CITY_LOCATIONS = "GeoLite2-City-Locations-pt-BR.csv";

    public App() {
        InputStream ipv4_blocks = ClassLoader.getSystemResourceAsStream(IPV4_BLOCKS);
        InputStream city_locations = ClassLoader.getSystemResourceAsStream(CITY_LOCATIONS);
    }

    public Localidade busca_localidade(String ip) {
        return new Localidade("BR", "Pindorama");
    }

}
