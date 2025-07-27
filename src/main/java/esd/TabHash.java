package esd;

class Par<K, V> {
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

public class TabHash<K, V> {
    private int capacidade = 8;
    private int tamanho = 0;

    private Par<K, V>[] tab;

    @SuppressWarnings("unchecked")
    public TabHash() {
        this.capacidade = 8;
        this.tab = (Par<K, V>[]) new Par[capacidade];
        this.tamanho = 0;
    }

    private int h1(K chave) {
        return Math.abs(chave.hashCode()) % capacidade;
    }

    private int h2(K chave) {
        return 1 + (Math.abs(chave.hashCode()) % (capacidade - 1));
    }

    public void adiciona(K chave, V valor) {
        if ((float)(tamanho + 1) / capacidade > 0.75) {
            expande();
        }

        int hash1 = h1(chave);
        int hash2 = h2(chave);

        for (int i = 0; i < capacidade; i++) {
            int indice = (hash1 + i * hash2) % capacidade;
            Par<K, V> par = tab[indice];

            if (par == null || par.obtemChave() == null) {
                tab[indice] = new Par<>(chave, valor);
                tamanho++;
                return;
            }

            if (par.obtemChave().equals(chave)) {
                par.defineValor(valor);
                return;
            }
        }

        throw new RuntimeException("Tabela cheia, não foi possível inserir");
    }

    public V obtem(K chave) {
        int hash1 = h1(chave);
        int hash2 = h2(chave);

        for (int i = 0; i < capacidade; i++) {
            int indice = (hash1 + i * hash2) % capacidade;
            Par<K, V> par = tab[indice];

            if (par == null) {
                return null;
            }

            if (par.obtemChave() != null && par.obtemChave().equals(chave)) {
                return par.obtemValor();
            }
        }

        return null;
    }

    public void remove(K chave) {
        int hash1 = h1(chave);
        int hash2 = h2(chave);

        for (int i = 0; i < capacidade; i++) {
            int indice = (hash1 + i * hash2) % capacidade;
            Par<K, V> par = tab[indice];

            if (par == null) {
                return;
            }

            if (par.obtemChave() != null && par.obtemChave().equals(chave)) {
                tab[indice] = new Par<>(null, null);
                tamanho--;
                return;
            }
        }
    }

    public boolean contem(K chave) {
        return obtem(chave) != null;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    private void expande() {
        int novaCapacidade = capacidade * 2;
        @SuppressWarnings("unchecked")
        Par<K, V>[] novaTab = (Par<K, V>[]) new Par[novaCapacidade];

        for (int i = 0; i < capacidade; i++) {
            Par<K, V> par = tab[i];

            if (par != null && par.obtemChave() != null) {
                K chave = par.obtemChave();
                V valor = par.obtemValor();

                int hash1 = Math.abs(chave.hashCode() % novaCapacidade);
                int hash2 = 1 + Math.abs(chave.hashCode() % (novaCapacidade - 1));

                for (int j = 0; j < novaCapacidade; j++) {
                    int indice = (hash1 + j * hash2) % novaCapacidade;
                    if (novaTab[indice] == null) {
                        novaTab[indice] = new Par<>(chave, valor);
                        break;
                    }
                }
            }
        }
        tab = novaTab;
        capacidade = novaCapacidade;
    }
}