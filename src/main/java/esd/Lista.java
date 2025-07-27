package esd;

import java.util.Random;

public class Lista<T extends Comparable<T>> {
    class Node {
        T valor = null;
        Node proximo;
        Node antecessor;

        // operações de Node
        Node() {
            // este nodo deve inicialmente ser seu próprio sucessor e antecessor
            this.antecessor = this;
            this.proximo = this;
        }

        Node(T valor) {
            // inicializa o nodo, que deve inicialmente ser seu próprio sucessor e
            // antecessor
            this.valor = valor;
            this.antecessor = this;
            this.proximo = this;
        }

        void conecta(Node sucessor) {
            // insere este nodo antes de sucessor
            antecessor = sucessor.antecessor;
            proximo = sucessor;
            sucessor.antecessor = this;
            antecessor.proximo = this;
        }

        void desconecta() {
            // desconecta este nodo, desfazendo as referências de seu antecessor e sucessor
            antecessor.proximo = proximo;
            proximo.antecessor = antecessor;
            proximo = null;
            antecessor = null;
        }
    }

    Node guarda;
    int len;

    // operações de Lista
    public Lista() {
        guarda = new Node();
        len = 0;
    }

    public Node obterNodo(int indice) {
        if (indice < 0 || indice >= len)
            throw new IndexOutOfBoundsException("Indíce inválido");

        Node atual = guarda;
        for (int a = 0; a <= indice; a++) {
            atual = atual.proximo;
        }
        return atual;
    }

    // insere valor na posição dada por "indice"
    // se "indice" > comprimento da lista, dispara exceção
    // IndexOutOfBoundException
    public void insere(int indice, T valor) {
        if (indice < 0 || indice > len)
            throw new IndexOutOfBoundsException("Índice fora dos limites");
    
        Node criado = new Node(valor);
    
        if (indice == len) {
            criado.conecta(guarda);
        } else {
            criado.conecta(obterNodo(indice));
        }
        len++;
    }

    // adiciona no fim
    public void adiciona(T valor) {
        Node novo = new Node(valor);
        novo.conecta(guarda);
        len++;
    }

    // obtém o valor que está na posição dada por "indice" se "indice" >= comprimento da lista, dispara exceção IndexOutOfBoundException
    public T obtem(int indice) {
        return obterNodo(indice).valor;
    }

    public int procura(T valor) {
        Node atual = guarda.proximo;
        int indice = 0;
        while (atual != guarda) {
            if (atual.valor.equals(valor)) {
                return indice;
            }
            atual = atual.proximo;
            indice++;
        }
        return -1;
    }

    public T obtem_primeiro() {
        return obtem(0);

    }

    public T obtem_ultimo() {
        return obtem(len - 1);
    }

    public boolean esta_vazia() {
        return len == 0;
    }

    public int comprimento() {
        return len;
    }

    public void remove(int indice) {
        obterNodo(indice).desconecta();
        len--;
    }

    public void remove_ultimo() {
        remove(len - 1);

    }

    public void substitui(int indice, T valor) {
        obterNodo(indice).valor = valor;
    }

    // public void ordena() {
    //     APB<T> arvore = new APB<>(); // cria arvore

    //     Node atual = guarda.proximo;
    //     while (atual != guarda) {
    //         arvore.adiciona(atual.valor); // adiciona cada valor da lista na árvore
    //         atual = atual.proximo;
    //     }

    //     // pegas os valores ordenados da árvore
    //     ListaSequencial<T> ordenados = arvore.emOrdem();

    //     // copia novamente
    //     atual = guarda.proximo;
    //     int i = 0;
    //     while (atual != guarda && i < ordenados.comprimento()) { // verifica se ainda tem valores na lista e se ainda tem valores ordenados
    //         atual.valor = ordenados.obtem(i);
    //         atual = atual.proximo;
    //         i++;
    //     }
    //     // se repetir diz null
    //     while (atual != guarda) {
    //         atual.valor = null;
    //         atual = atual.proximo;
    //     }
    // }

    // public void ordena() {
    //     APB<T> arvore = new APB<>();
    //     for (int a = 0; a < len; a++) {
    //         arvore.adiciona(obtem(a));
    //     }
    //     arvore.balanceia();

    //     ListaSequencial<T> lista = arvore.emOrdem();

    //     Node atual = guarda;

    //     for (int a = 0; a < len; a++) {
    //         atual = atual.proximo;
    //         atual.valor = lista.obtem(a);
    //     }

    //     guarda.antecessor = atual;
    // }

    public void ordena() {

    }
    
    public Lista<T> mergeSort(Lista<T> lista, int pos1, int pos2) {
        if (pos2 < pos1) {
            return new Lista<>();  // retorna lista vazia em vez de null
        }
    
        if (pos2 == pos1) {
            Lista<T> saida = new Lista<>();
            saida.adiciona(lista.obterNodo(pos2).valor);
            return saida;
        }
    
        if (pos2 == pos1 + 1) {
            T prim = lista.obterNodo(pos1).valor;
            T seg = lista.obterNodo(pos2).valor;
            Lista<T> saida = new Lista<>();
            if (prim.compareTo(seg) > 0) {
                saida.adiciona(seg);
                saida.adiciona(prim);
            } else {
                saida.adiciona(prim);
                saida.adiciona(seg);
            }
            return saida;
        }
    
        int meio = (pos1 + pos2) / 2;
        Lista<T> esquerda = mergeSort(lista, pos1, meio);
        Lista<T> direita = mergeSort(lista, meio + 1, pos2);
        return mergeMS(esquerda, direita);
    }
    
    public Lista<T> mergeMS(Lista<T> prim, Lista<T> seg) {
        Lista<T> saida = new Lista<>();
        Node pontPrim = prim.guarda.proximo;
        Node pontSeg = seg.guarda.proximo;
    
        while (pontPrim != prim.guarda && pontSeg != seg.guarda) {
            if (pontPrim.valor.compareTo(pontSeg.valor) <= 0) {
                saida.adiciona(pontPrim.valor);
                pontPrim = pontPrim.proximo;
            } else {
                saida.adiciona(pontSeg.valor);
                pontSeg = pontSeg.proximo;
            }
        }
    
        while (pontPrim != prim.guarda) {
            saida.adiciona(pontPrim.valor);
            pontPrim = pontPrim.proximo;
        }
    
        while (pontSeg != seg.guarda) {
            saida.adiciona(pontSeg.valor);
            pontSeg = pontSeg.proximo;
        }
    
        return saida;
    }

    public void inverte() {
        if (esta_vazia()) return;
    
        Node atual = guarda.proximo;
        Node temp = null;
    
        while (atual != guarda) { // os ultimos serao os primeiros
            temp = atual.proximo;
            atual.proximo = atual.antecessor;
            atual.antecessor = temp;
            atual = temp;
        }
    
        temp = guarda.proximo; // guarda trocada.
        guarda.proximo = guarda.antecessor;
        guarda.antecessor = temp;
    }

    public T maximo() {
        if (esta_vazia()) {
            throw new RuntimeException("Lista vazia");
        }
    
        T maior = obtem(0);
        for (int i = 1; i < comprimento(); i++) {
            T atual = obtem(i);
            if (maior.compareTo(atual) < 0) {
                maior = atual;
            }
        }
        return maior;
    }
    
    public T minimo() {
        if (esta_vazia()) {
            throw new RuntimeException("Lista vazia");
        }
    
        T menor = obtem(0);
        for (int i = 1; i < comprimento(); i++) {
            T atual = obtem(i);
            if (menor.compareTo(atual) > 0) {
                menor = atual;
            }
        }
        return menor;
    }

    public void embaralha() {
        if (comprimento() <= 1) return;

        Random gerador = new Random();

        for (int i = comprimento() - 1; i > 0; i--) {
            int j = gerador.nextInt(i); // 0 <= j < i
            // troca os valores em i e j
            T temp = obtem(i);
            substitui(i, obtem(j));
            substitui(j, temp);
        }
    }

    public boolean esta_ordenada() {
        if (esta_vazia() || comprimento() == 1) {
            return true;  // Uma lista vazia ou com um único elemento está ordenada por definição
        }
        
        Node atual = guarda.proximo;
        while (atual.proximo != guarda) {
            if (atual.valor.compareTo(atual.proximo.valor) > 0) {
                return false;  // Encontramos uma inversão, então a lista não está ordenada
            }
            atual = atual.proximo;
        }
        return true;  // Não encontramos inversões, então a lista está ordenada
    }

    public void ordenaReversa() {
        ordena();
        inverte();
    }

    public int count(T valor) {
        int count = 0;
        Node atual = guarda.proximo;
        
        while (atual != guarda) { // ve toda a lista atrás de outros valores.
            if (atual.valor.equals(valor)) {
                count++;
            }
            atual = atual.proximo;
        }
        
        return count;
    }

    public void exibir() {
        if (esta_vazia()) {
            System.out.println("A lista está vazia.");
            return;
        }

        Node atual = guarda.proximo;
        while (atual != guarda) {
            System.out.print(atual.valor + " "); // pega o valor e printa, pega o valor e printa...
            atual = atual.proximo;
        }
        System.out.println();
    }
}