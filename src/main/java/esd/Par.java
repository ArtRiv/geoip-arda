package esd;

public class Par<K, V> {
    private K chave;
    private V valor;

    public Par(K chave, V valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public K obtemChave() {
        return chave;
    }

    public V obtemValor() {
        return valor;
    }

    public void defineValor(V novoValor) {
        this.valor = novoValor;
    }
}