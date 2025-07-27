package esd;

public class ListaSequencialOrdenada<T extends Comparable<T>> {

    final int MinSubseqLen = 31;
    Comparable<T>[] area;
    int len = 0;
    int defcap = 8;

    @SuppressWarnings("unchecked")
    public ListaSequencialOrdenada() {
        area = new Comparable[defcap];
    }

    @SuppressWarnings("unchecked")
    public void expande(int number) {
        T[] temp_Area = (T[]) new Comparable[number]; // ou new Object[number], veja abaixo
        for (int i = 0; i < len; i++) {
            temp_Area[i] = (T) area[i]; // cast necessário porque area é Comparable[]
        }
        area = temp_Area;
    }

    void expande() {
        expande(2 * area.length);
    }

    public boolean estaVazia() {
        return len == 0;
    }

    public int capacidade() {
        return area.length;
    }

    public void remove(T valor) {
        int posicao = procura(valor);
        if (posicao >= 0) {
            for (int i = posicao; i < len - 1; i++) {
                area[i] = area[i + 1];
            }
            len--;
            area[len] = null; // garbage collector *preferível pra evitar erros, professor falou que nao da mas por precaucao
        }
    }

    @SuppressWarnings("unchecked")
    public T obtem(int indice) {
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        return (T) area[indice];
    }

    public int comprimento() {
        return len;
    }

    public void limpa() {
        for (int i = 0; i < len; i++) {
            area[i] = null; // apaga tudo
        }
        len = 0;
    }

    @SuppressWarnings("unchecked")
    public void insere(T elemento) {
        if (len == area.length) {
            expande();
        }

        int i = len - 1;
        while (i >= 0 && elemento.compareTo((T) area[i]) < 0) {
            area[i + 1] = area[i];
            i--;
        }
        
        area[i + 1] = elemento;
        len++;
    }

    @SuppressWarnings("unchecked")
    public int procura(T valor) {
        int inicio = 0;
        int fim = len - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int cmp = valor.compareTo((T) area[meio]);
            if (cmp == 0) {
                return meio;
            } else if (cmp < 0) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }
        }

        return -1; // caso nao tenha
    }
}