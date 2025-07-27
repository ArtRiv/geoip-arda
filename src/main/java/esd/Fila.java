package esd;

@SuppressWarnings("unchecked")
public class Fila<T> {

    private T[] mem;
    private int tamanho;
    private int defcap = 8;
    private int capacidade = defcap;

    public Fila(int cap) {
        defcap = cap;
        capacidade = cap;
        mem = (T[]) new Object[capacidade];
        tamanho = 0;
    }

    public Fila() {
        mem = (T[]) new Object[defcap];
        tamanho = 0;
    }

    public int comprimento() {
        return tamanho;
    }

    public int capacidade() {
        return capacidade;
    }

    public T frente() {
        if (estaVazia()) {
            throw new IndexOutOfBoundsException("Fila vazia");
        }
        return mem[0];
    }

    public T fim() {
        if (estaVazia()) {
            throw new IndexOutOfBoundsException("Fila vazia");
        }
        return mem[tamanho - 1];
    }

    public T get(int n) {
        if (estaVazia()) {
            throw new IndexOutOfBoundsException("Fila vazia");
        }
        return mem[n];
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public void limpa() {
        for (int i = 0; i < tamanho; i++) {
            mem[i] = null;
        }
        tamanho = 0;
    }

    public void adiciona(T algo) {
        if (tamanho == capacidade) {
            T[] novaFila = (T[]) new Object[capacidade + defcap];
            // for
            System.arraycopy(mem, 0, novaFila, 0, tamanho);
            mem = novaFila;
            capacidade += defcap;
        }
        mem[tamanho] = algo;
        tamanho++;
    }

    public T remove() {
        if (estaVazia()) {
            throw new IndexOutOfBoundsException("Fila vazia");
        }

        T retorno = mem[0];

        for (int a = 0; a < tamanho - 1; a++) {
            mem[a] = mem[a + 1];
        }

        mem[tamanho - 1] = null;
        tamanho--;

        return retorno;
    }

    public void adiciona_muitos(Fila<T> outra) {
        while (!outra.estaVazia()) {
            if (tamanho == capacidade) {
                T[] novaFila = (T[]) new Object[capacidade + defcap];
                System.arraycopy(mem, 0, novaFila, 0, tamanho);
                mem = novaFila;
                capacidade += defcap;
            }
            mem[tamanho] = outra.remove();
            tamanho++;
        }
    }
}
