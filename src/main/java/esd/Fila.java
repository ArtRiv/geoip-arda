package esd;

public class Fila <T> {
    T[] mem;
    int size = 0;
    int end = 0;
    int start = 0;

    @SuppressWarnings("unchecked")
    public Fila() {
        mem = (T[])new Object[1];
    }

    public int comprimento() {
        return size;
    }

    public int capacidade() {
        return mem.length;
    }

    public void adiciona(T algo) {
        if (mem.length == size) {
            expande();
        }

        mem[end] = algo;
        end = (end + 1) % mem.length;
        size++;
    }

    public T remove() {
        if (size == 0) throw new IndexOutOfBoundsException("Fila vazia");

        T removed = mem[start];
        mem[start] = null;
        start = (start + 1) % mem.length;
        size--;
        return removed;
    }

    public T frente() {
        if (size == 0) throw new IndexOutOfBoundsException();
        return mem[start];
    }

    public boolean estaVazia() {
        return (size == 0);
    }

    public void limpa() {
        for(int i = 0; i < size; i++) {
            mem[(start + i) % mem.length] = null;
        }
        size = 0;
        end = 0;
        start = 0;
    }

    @SuppressWarnings("unchecked")
    void expande() {
        T[] mem_temp = (T[]) new Object[mem.length * 2];
        for (int i = 0; i < size; i++) {
            mem_temp[i] = mem[(start + i) % mem.length];
        }
        mem = mem_temp;
        start = 0;
        end = size;
    }


    // Enfileira nesta fila os valores contidos na outra fila
    // Ao final, outra_fila deve estar vazia, e seus valores devem estar armazenados no final desta fila

    public void adiciona_muitos(Fila<T> outra_fila) {
        while (!outra_fila.estaVazia()) {
            this.adiciona(outra_fila.remove());
        }
    }

    public boolean adicionaSeInexistente(T valor) {
        Fila<T> temp = new Fila<>();
        boolean validador = false;
        while (!this.estaVazia()) {
            T guardaValor = this.remove();
            temp.adiciona(guardaValor);

            if (guardaValor == valor) {
                validador = true;
            }
        }

        while (!temp.estaVazia()) {
            this.adiciona(temp.remove());
        }

        if (validador) {
            this.adiciona(valor);
            return true;
        } else {
            return false;
        }
    }
}
 