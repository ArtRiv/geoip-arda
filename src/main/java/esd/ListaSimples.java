package esd;

public class ListaSimples<T> {
    class Node {
        T valor;
        Node proximo;

        Node(T valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    private Node primeiro = null;
    private Node ultimo = null;
    private int len = 0;

    public ListaSimples() {
    }

    // adiciona no fim
    
    public void adiciona(T valor) {
        Node novo = new Node(valor);
        if (primeiro == null) {
            primeiro = ultimo = novo;
        } else {
            ultimo.proximo = novo;
            ultimo = novo;
        }
        len++;
    }

    // obtém valor pelo índice

    public T obtem(int indice) {
        checaIndice(indice, false);
        Node atual = primeiro;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.valor;
    }

    // insere na posição indicada

    public void insere(int indice, T valor) {
        checaIndice(indice, true);

        Node novo = new Node(valor);
        if (indice == 0) {
            novo.proximo = primeiro;
            primeiro = novo;
            if (len == 0) ultimo = novo;
        } else {
            Node anterior = primeiro;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.proximo;
            }
            novo.proximo = anterior.proximo;
            anterior.proximo = novo;
            if (novo.proximo == null) ultimo = novo;
        }
        len++;
    }

    public boolean esta_vazia() {
        return len == 0;
    }

    public int comprimento() {
        return len;
    }

    public void remove(int indice) {
        checaIndice(indice, false);

        if (indice == 0) {
            primeiro = primeiro.proximo;
            if (primeiro == null) {
                ultimo = null;
            }
        } else {
            Node anterior = primeiro;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.proximo;
            }
            anterior.proximo = anterior.proximo.proximo;
            if (anterior.proximo == null) {
                ultimo = anterior;
            }
        }
        len--;
    }

    public void substitui(int indice, T valor) {
        checaIndice(indice, false);
        Node atual = primeiro;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        atual.valor = valor;
    }

    public void inverte() {
        Node anterior = null;
        Node atual = primeiro;
        Node proximo;

        ultimo = primeiro;

        while (atual != null) {
            proximo = atual.proximo;
            atual.proximo = anterior;
            anterior = atual;
            atual = proximo;
        }

        primeiro = anterior;
    }

    private void checaIndice(int indice, boolean permiteIgualComprimento) {
        if (indice < 0 || indice > len || (!permiteIgualComprimento && indice == len)) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
    }
}
