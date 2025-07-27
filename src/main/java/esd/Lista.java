package esd;

public class Lista<T> {
    class Node {
        T valor;
        Node proximo;
        Node antecessor;

        Node() {
            this.antecessor = this;
            this.proximo = this;
        }

        Node(T valor) {
            this.valor = valor;
            this.antecessor = this;
            this.proximo = this;
        }

        void conecta(Node sucessor) {
            antecessor = sucessor.antecessor;
            proximo = sucessor;
            sucessor.antecessor = this;
            antecessor.proximo = this;
        }

        void desconecta() {
            antecessor.proximo = proximo;
            proximo.antecessor = antecessor;
            proximo = null;
            antecessor = null;
        }
    }

    Node guarda;
    int len;

    public Lista() {
        guarda = new Node();
        len = 0;
    }

    private Node obterNodo(int indice) {
        if (indice < 0 || indice >= len)
            throw new IndexOutOfBoundsException("Indice invalido");
        Node atual = guarda.proximo;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual;
    }

    public void insere(int indice, T valor) {
        if (indice < 0 || indice > len)
            throw new IndexOutOfBoundsException("Indice fora dos limites");
        Node criado = new Node(valor);
        if (len == 0) {
            criado.proximo = guarda;
            criado.antecessor = guarda;
            guarda.proximo = criado;
            guarda.antecessor = criado;
        } else if (indice == len) {
            criado.conecta(guarda);
        } else {
            criado.conecta(obterNodo(indice));
        }
        len++;
    }

    public void adiciona(T valor) {
        Node novo = new Node(valor);
        novo.conecta(guarda);
        len++;
    }

    public T obtem(int indice) {
        return obterNodo(indice).valor;
    }

    public int procura(T valor) {
        Node atual = guarda.proximo;
        int indice = 0;
        while (atual != guarda) {
            if (valor == null ? atual.valor == null : valor.equals(atual.valor)) {
                return indice;
            }
            atual = atual.proximo;
            indice++;
        }
        return -1;
    }

    public T obtem_primeiro() {
        if (len == 0)
            throw new IndexOutOfBoundsException("Lista vazia");
        return obtem(0);
    }

    public T obtem_ultimo() {
        if (len == 0)
            throw new IndexOutOfBoundsException("Lista vazia");
        return obtem(len - 1);
    }

    public boolean esta_vazia() {
        return len == 0;
    }

    public int comprimento() {
        return len;
    }

    public void remove(int indice) {
        if (indice < 0 || indice >= len)
            throw new IndexOutOfBoundsException("Indice invalido");
        Node alvo = obterNodo(indice);
        alvo.desconecta();
        len--;
    }

    public void remove_ultimo() {
        if (len == 0)
            throw new IndexOutOfBoundsException("Lista vazia");
        remove(len - 1);
    }

    public void substitui(int indice, T valor) {
        if (indice < 0 || indice >= len)
            throw new IndexOutOfBoundsException("Indice invalido");
        obterNodo(indice).valor = valor;
    }

    public void inverte() {
        int metade = len / 2;
        for (int i = 0; i < metade; i++) {
            Node a = obterNodo(i);
            Node b = obterNodo(len - 1 - i);
            T temp = a.valor;
            a.valor = b.valor;
            b.valor = temp;
        }
    }

    public boolean esta_ordenada() {
        if (len <= 1) {
            return true;
        }

        Node atual = guarda.proximo;
        while (atual.proximo != guarda) {
            @SuppressWarnings("unchecked")
            Comparable<T> c1 = (Comparable<T>) atual.valor;
            T v2 = atual.proximo.valor;
            if (c1.compareTo(v2) > 0) {
                return false;
            }
            atual = atual.proximo;
        }
        return true;
    }

    public T maximo() {
        if (len == 0) {
            throw new RuntimeException("Lista vazia");
        }
        Node atual = guarda.proximo;
        @SuppressWarnings("unchecked")
        Comparable<T> maior = (Comparable<T>) atual.valor;
        T maiorValor = atual.valor;
        atual = atual.proximo;
        while (atual != guarda) {
            @SuppressWarnings("unchecked")
            Comparable<T> c = (Comparable<T>) atual.valor;
            if (c.compareTo(maiorValor) > 0) {
                maior = c;
                maiorValor = atual.valor;
            }
            atual = atual.proximo;
        }
        return maiorValor;
    }

    public T minimo() {
        if (len == 0) {
            throw new RuntimeException("Lista vazia");
        }
        Node atual = guarda.proximo;
        @SuppressWarnings("unchecked")
        Comparable<T> menor = (Comparable<T>) atual.valor;
        T menorValor = atual.valor;
        atual = atual.proximo;
        while (atual != guarda) {
            @SuppressWarnings("unchecked")
            Comparable<T> c = (Comparable<T>) atual.valor;
            if (c.compareTo(menorValor) < 0) {
                menor = c;
                menorValor = atual.valor;
            }
            atual = atual.proximo;
        }
        return menorValor;
    }

    public int count(T valor) {
        int count = 0;
        Node atual = guarda.proximo;

        while (atual != guarda) {
            if (valor == null ? atual.valor == null : valor.equals(atual.valor)) {
                count++;
            }
            atual = atual.proximo;
        }
        
        return count;
    }

    public void ordena() {
        if (len <= 1) return;
        ordenaMescla(0, len);
    }

    public void ordenaMescla(int pos1, int pos2) {
        if (pos2 - pos1 > 1) {
            int meio = pos1 + (pos2 - pos1) / 2;
            ordenaMescla(pos1, meio);
            ordenaMescla(meio, pos2);
            mescla(pos1, meio, pos2);
        }
    }

    @SuppressWarnings("unchecked")
    private void mescla(int pos1, int meio, int pos2) {
        int n1 = meio - pos1;
        int n2 = pos2 - meio;
        Object[] esquerda = new Object[n1];
        Object[] direita = new Object[n2];
        for (int i = 0; i < n1; i++) {
            esquerda[i] = obtem(pos1 + i);
        }
        for (int j = 0; j < n2; j++) {
            direita[j] = obtem(meio + j);
        }
        int i = 0, j = 0, k = pos1;
        while (i < n1 && j < n2) {
            Comparable<T> valorEsq = (Comparable<T>) esquerda[i];
            T valorDir = (T) direita[j];
            if (valorEsq.compareTo(valorDir) <= 0) {
                substitui(k++, (T) esquerda[i++]);
            } else {
                substitui(k++, (T) direita[j++]);
            }
        }
        while (i < n1) {
            substitui(k++, (T) esquerda[i++]);
        }
        while (j < n2) {
            substitui(k++, (T) direita[j++]);
        }
    }

    public void mescla(Lista<T> outra) {
        Node p1 = this.guarda.proximo;
        Node p2 = outra.guarda.proximo;
        Lista<T> resultado = new Lista<>();

        while (p1 != this.guarda && p2 != outra.guarda) {
            @SuppressWarnings("unchecked")
            Comparable<T> c1 = (Comparable<T>) p1.valor;
            T v2 = p2.valor;
            if (c1.compareTo(v2) <= 0) {
                resultado.adiciona(p1.valor);
                p1 = p1.proximo;
            } else {
                resultado.adiciona(v2);
                p2 = p2.proximo;
            }
        }
        while (p1 != this.guarda) {
            resultado.adiciona(p1.valor);
            p1 = p1.proximo;
        }
        while (p2 != outra.guarda) {
            resultado.adiciona(p2.valor);
            p2 = p2.proximo;
        }

        this.guarda = resultado.guarda;
        this.len = resultado.len;
    }
}