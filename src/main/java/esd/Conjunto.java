package esd;

public class Conjunto<V extends Comparable<V>> {
    TabHash<V, Object> tab = new TabHash<>();


    void adiciona(V valor) {
        tab.adiciona(valor, null);
    }

    boolean contem(V valor) {
        return tab.contem(valor);
    }

    void remove(V valor) {
        tab.remove(valor);
    }

    ListaSequencial<V> valores() {
        return tab.chaves();
    }
}

