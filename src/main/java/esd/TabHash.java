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
    private int capacidade;
    private int tamanho = 0;
    private Par<K, V>[] tab;
    private final Par<K, V> TOMBSTONE = new Par<>(null, null);

    @SuppressWarnings("unchecked")
    public TabHash() {
        this(256); 
    }

    @SuppressWarnings("unchecked")
    public TabHash(int capacidadeInicial) {
        this.capacidade = proximaPotenciaDeDois(capacidadeInicial);
        this.tab = (Par<K, V>[]) new Par[this.capacidade];
        this.tamanho = 0;
    }

    private int proximaPotenciaDeDois(int n) {
        int cap = 1;
        while (cap < n) {
            cap <<= 1;
        }
        return cap;
    }

    private int h1(K chave) {
        int h = chave.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return (h ^ (h >>> 7) ^ (h >>> 4)) & (capacidade - 1);
    }

    private int h2(K chave) {
        int hash = Math.abs(chave.hashCode());
        return (hash % (capacidade / 2)) * 2 + 1;
    }

    public void adiciona(K chave, V valor) {
        if ((float)(tamanho + 1) / capacidade > 0.75f) {
            expande();
        }

        int hash1 = h1(chave);
        int passo = h2(chave);
        int indice = hash1;
        int primeiroTombstone = -1;

        for (int i = 0; i < capacidade; i++) {
            Par<K, V> par = tab[indice];

            if (par == null) {
                int indiceFinal = (primeiroTombstone != -1) ? primeiroTombstone : indice;
                tab[indiceFinal] = new Par<>(chave, valor);
                tamanho++;
                return;
            }

            if (par == TOMBSTONE) {
                if (primeiroTombstone == -1) {
                    primeiroTombstone = indice;
                }
            } else if (par.obtemChave().equals(chave)) {
                par.defineValor(valor);
                return;
            }

            indice = (indice + passo) & (capacidade - 1);
        }

        throw new RuntimeException("Tabela cheia, não foi possível inserir. Capacidade: " + capacidade + ", Tamanho: " + tamanho);
    }

    public V obtem(K chave) {
        int hash1 = h1(chave);
        int passo = h2(chave);
        int indice = hash1;

        for (int i = 0; i < capacidade; i++) {
            Par<K, V> par = tab[indice];

            if (par == null) {
                return null;
            }

            if (par != TOMBSTONE && par.obtemChave().equals(chave)) {
                return par.obtemValor();
            }

            indice = (indice + passo) & (capacidade - 1);
        }

        return null;
    }

    public void remove(K chave) {
        int hash1 = h1(chave);
        int passo = h2(chave);
        int indice = hash1;

        for (int i = 0; i < capacidade; i++) {
            Par<K, V> par = tab[indice];

            if (par == null) {
                return;
            }

            if (par != TOMBSTONE && par.obtemChave().equals(chave)) {
                tab[indice] = TOMBSTONE;
                tamanho--;
                return;
            }

            indice = (indice + passo) & (capacidade - 1);
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

    @SuppressWarnings("unchecked")
    private void expande() {
        int antigaCapacidade = capacidade;
        Par<K, V>[] antigaTab = tab;

        capacidade = antigaCapacidade * 2;
        tab = (Par<K, V>[]) new Par[capacidade];
        tamanho = 0;

        for (int i = 0; i < antigaCapacidade; i++) {
            Par<K, V> par = antigaTab[i];
            if (par != null && par != TOMBSTONE) {
                adiciona(par.obtemChave(), par.obtemValor());
            }
        }
    }
}