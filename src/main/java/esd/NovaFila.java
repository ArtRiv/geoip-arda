package esd;

public class NovaFila<T> {

    Pilha<T> p1 = new Pilha<>();
    Pilha<T> p2 = new Pilha<>();

    public NovaFila() {}

    public int comprimento() {
        return p1.comprimento() + p2.comprimento();
    }

    public int capacidade() {
        return Math.max(p1.capacidade(), p2.capacidade());
    }

    public T remove() {
        if (estaVazia()) throw new IndexOutOfBoundsException("Fila vazia");

        if (p2.estaVazia()) {
            while (!p1.estaVazia()) {
                p2.empilha(p1.desempilha());
            }
        }

        return p2.desempilha();
    }

    public T frente() {
        if (estaVazia()) throw new IndexOutOfBoundsException("Fila vazia");

        if (p2.estaVazia()) {
            while (!p1.estaVazia()) {
                p2.empilha(p1.desempilha());
            }
        }

        return p2.topo();
    }

    public void adiciona(T val) {
        p1.empilha(val);
    }

    public boolean estaVazia() {
        return p1.estaVazia() && p2.estaVazia();
    }

    public void limpa() {
        p1.limpa();
        p2.limpa();
    }

}
