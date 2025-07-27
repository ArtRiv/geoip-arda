package esd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class NodoAPB<T extends Comparable<T>> {
    T valor;
    NodoAPB<T> esq, dir, pai;

    public NodoAPB(T item) {
        valor = item;
        esq = dir = pai = null;
    }
}

public class APB<T extends Comparable<T>> {
    private NodoAPB<T> raiz;
    private int len;

    public APB() {
        raiz = null;
        len = 0;
    }

    public void adiciona(T val) {
        raiz = adicionaRec(raiz, val, null);
        len++;
    }

    private NodoAPB<T> adicionaRec(NodoAPB<T> atual, T val, NodoAPB<T> pai) {
        if (atual == null) {
            atual = new NodoAPB<>(val);
            atual.pai = pai;
            return atual;
        }

        if (val.compareTo(atual.valor) < 0) {
            atual.esq = adicionaRec(atual.esq, val, atual);
        } else if (val.compareTo(atual.valor) > 0) {
            atual.dir = adicionaRec(atual.dir, val, atual);
        }

        return atual;
    }

    public void remove(T val) {
        raiz = removeRec(raiz, val);
        len--;
    }

    private NodoAPB<T> removeRec(NodoAPB<T> raiz, T val) {
        if (raiz == null) {
            return raiz;
        }

        if (val.compareTo(raiz.valor) < 0) {
            raiz.esq = removeRec(raiz.esq, val);
        } else if (val.compareTo(raiz.valor) > 0) {
            raiz.dir = removeRec(raiz.dir, val);
        } else {
            if (raiz.esq == null) {
                return raiz.dir;
            } else if (raiz.dir == null) {
                return raiz.esq;
            }

            raiz.valor = menorValor(raiz.dir);
            raiz.dir = removeRec(raiz.dir, raiz.valor);
        }

        return raiz;
    }

    public NodoAPB<T> procura(T val) {
        return procuraRec(raiz, val);
    }

    private NodoAPB<T> procuraRec(NodoAPB<T> raiz, T val) {
        if (raiz == null || raiz.valor.equals(val)) {
            return raiz;
        }
        if (val.compareTo(raiz.valor) < 0) {
            return procuraRec(raiz.esq, val);
        }
        return procuraRec(raiz.dir, val);
    }

    public T obtem_raiz() {
        return raiz != null ? raiz.valor : null;
    }

    public boolean esta_vazia() {
        return raiz == null;
    }

    public ListaSequencial<T> listeInOrder() {
        ListaSequencial<T> lista = new ListaSequencial<>();

        listeInOrderRec(raiz, lista);

        return lista;
    }

    private void listeInOrderRec(NodoAPB<T> raiz, ListaSequencial<T> lista) {
        if (raiz != null) {
            listeInOrderRec(raiz.esq, lista);
            lista.adiciona(raiz.valor);
            listeInOrderRec(raiz.dir, lista);
        }
    }

    public ListaSequencial<T> listePreOrder() {
        ListaSequencial<T> lista = new ListaSequencial<>();

        listePreOrderRec(raiz, lista);

        return lista;
    }

    private void listePreOrderRec(NodoAPB<T> raiz, ListaSequencial<T> lista) {
        if (raiz != null) {
            lista.adiciona(raiz.valor);
            listePreOrderRec(raiz.esq, lista);
            listePreOrderRec(raiz.dir, lista);
        }
    }

    public ListaSequencial<T> listePostOrder() {
        ListaSequencial<T> lista = new ListaSequencial<>();
        
        listePostOrderRec(raiz, lista);

        return lista;
    }

    private void listePostOrderRec(NodoAPB<T> raiz, ListaSequencial<T> lista) {
        if (raiz != null) {
            listePostOrderRec(raiz.esq, lista);
            listePostOrderRec(raiz.dir, lista);
            lista.adiciona(raiz.valor);
        }
    }

    public ListaSequencial<T> listeEmLargura() {
        ListaSequencial<T> lista = new ListaSequencial<>();

        Deque<NodoAPB<T>> nos = new Deque<>();

        nos.adiciona(raiz);

        while (!nos.esta_vazia()) {
            NodoAPB<T> no = nos.acessa_inicio();
            lista.adiciona(no.valor);
            if (no.esq != null) {
                nos.adiciona(no.esq);
            }
            if (no.dir != null) {
                nos.adiciona(no.dir);
            }
        }
        return lista;
    }

    public T menor() {
        if (raiz == null)
            return null;
        return menorValor(raiz);
    }

    private T menorValor(NodoAPB<T> raiz) {
        T minv = raiz.valor;
        while (raiz.esq != null) {
            minv = raiz.esq.valor;
            raiz = raiz.esq;
        }
        return minv;
    }

    public T maior() {
        if (raiz == null)
            return null;
        return maiorValor(raiz);
    }

    private T maiorValor(NodoAPB<T> raiz) {
        T maxv = raiz.valor;
        while (raiz.dir != null) {
            maxv = raiz.dir.valor;
            raiz = raiz.dir;
        }
        return maxv;
    }

    public List<T> menoresQue(T val) {
        List<T> result = new ArrayList<>();
        menoresQueRec(raiz, val, result);
        return result;
    }

    private void menoresQueRec(NodoAPB<T> no, T val, List<T> result) {
        if (no == null)
            return;
        if (no.valor.compareTo(val) < 0) {
            result.add(no.valor);
            menoresQueRec(no.esq, val, result);
            menoresQueRec(no.dir, val, result);
        } else {
            menoresQueRec(no.esq, val, result);
        }
    }

    public List<T> maioresQue(T val) {
        List<T> result = new ArrayList<>();
        maioresQueRec(raiz, val, result);
        return result;
    }

    private void maioresQueRec(NodoAPB<T> no, T val, List<T> result) {
        if (no == null)
            return;
        if (no.valor.compareTo(val) > 0) {
            result.add(no.valor);
            maioresQueRec(no.esq, val, result);
            maioresQueRec(no.dir, val, result);
        } else {
            maioresQueRec(no.dir, val, result);
        }
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoAPB<T> no) {
        if (no == null)
            return 0;
        return 1 + Math.max(alturaRec(no.esq), alturaRec(no.dir));
    }

    public int tamanho() {
        return len;
    }

    public void limpa() {
        raiz = null;
    }

    public void balanceia() {
        List<T> nos = new ArrayList<>();
        storeInOrder(raiz, nos);
        this.raiz = buildBalanced(nos, 0, nos.size() - 1, null);
    }

    private void storeInOrder(NodoAPB<T> no, List<T> nos) {
        if (no == null)
            return;
        storeInOrder(no.esq, nos);
        nos.add(no.valor);
        storeInOrder(no.dir, nos);
    }

    private NodoAPB<T> buildBalanced(List<T> nos, int start, int end, NodoAPB<T> pai) {
        if (start > end)
            return null;
        int mid = (start + end) / 2;
        NodoAPB<T> no = new NodoAPB<>(nos.get(mid));
        no.pai = pai;
        no.esq = buildBalanced(nos, start, mid - 1, no);
        no.dir = buildBalanced(nos, mid + 1, end, no);
        return no;
    }
}