package esd;

public class APB<T extends Comparable<T>> {
    public NodoAPB<T> raiz;
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

    public ListaSequencial<T> emOrdem() {
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

    public ListaSequencial<T> preOrdem() {
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

    public ListaSequencial<T> posOrdem() {
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

    public ListaSequencial<T> emLargura() {
        ListaSequencial<T> lista = new ListaSequencial<>();

        Deque<NodoAPB<T>> nos = new Deque<>();

        nos.adiciona(raiz);

        while (!nos.estaVazia()) {
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

    public T menor_que(T valor) {
        return menorQueRec(valor, raiz);
    }

    public T menorQueRec(T valor, NodoAPB<T> nodo) {
        if (nodo == null)
            return null;

        if (nodo.valor.compareTo(valor) > 0) {
            return menorQueRec(valor, nodo.esq);
        } else {
            T candidato = menorQueRec(valor, nodo.dir);
            return (candidato != null) ? candidato : nodo.valor;
        }
    }

    public T maior_que(T valor) {
        return maiorQueRec(valor, raiz);
    }

    public T maiorQueRec(T valor, NodoAPB<T> nodo) {
        if (nodo.valor.compareTo(valor) == 0)
            return valor;
        if (nodo.valor.compareTo(valor) > 0) {
            if (nodo.esq != null)
                return maiorQueRec(valor, nodo.esq);
            else
                return nodo.valor;
        }

        if (nodo.valor.compareTo(valor) < 0 && nodo.dir != null)
            return maiorQueRec(valor, nodo.dir);

        return null;
    }

    public ListaSequencial<T> menores_que(T val) {
        ListaSequencial<T> result = new ListaSequencial<>();
        menoresQueRec(raiz, val, result);
        return result;
    }

    private void menoresQueRec(NodoAPB<T> no, T val, ListaSequencial<T> result) {
        if (no == null)
            return;
        if (no.valor.compareTo(val) < 0) {
            result.adiciona(no.valor);
            menoresQueRec(no.esq, val, result);
            menoresQueRec(no.dir, val, result);
        } else {
            menoresQueRec(no.esq, val, result);
        }
    }

    public ListaSequencial<T> maiores_que(T val) {
        ListaSequencial<T> result = new ListaSequencial<>();
        maioresQueRec(raiz, val, result);
        return result;
    }

    private void maioresQueRec(NodoAPB<T> no, T val, ListaSequencial<T> result) {
        if (no == null)
            return;
        if (no.valor.compareTo(val) > 0) {
            result.adiciona(no.valor);
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
        ListaSequencial<T> nos = new ListaSequencial<>();
        storeInOrder(raiz, nos);
        this.raiz = buildBalanced(nos, 0, nos.comprimento() - 1, null);
    }

    private void storeInOrder(NodoAPB<T> no, ListaSequencial<T> nos) {
        if (no == null)
            return;
        storeInOrder(no.esq, nos);
        nos.adiciona(no.valor);
        storeInOrder(no.dir, nos);
    }

    private NodoAPB<T> buildBalanced(ListaSequencial<T> nos, int start, int end, NodoAPB<T> pai) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        NodoAPB<T> no = new NodoAPB<>(nos.obtem(mid));
        no.pai = pai;
        no.esq = buildBalanced(nos, start, mid - 1, no);
        no.dir = buildBalanced(nos, mid + 1, end, no);
        return no;
    }

    public static class NodoAPB<E extends Comparable<E>> {
        public E valor;
        public NodoAPB<E> esq, dir, pai;

        public NodoAPB(E item) {
            valor = item;
            esq = dir = pai = null;
        }
    }

    public void constroiDeListaOrdenada(ListaSequencial<T> elementos) {
        if (elementos == null || elementos.esta_vazia()) {
            this.raiz = null;
            this.len = 0;
            return;
        }
        this.raiz = buildBalanced(elementos, 0, elementos.comprimento() - 1, null);
        this.len = elementos.comprimento();
    }

    public ListaSequencial<T> faixa(T min, T max) {
        ListaSequencial<T> todos = emOrdem();
        ListaSequencial<T> saida = new ListaSequencial<>();
        for (int i = 0; i < todos.comprimento(); i++) {
            T valor = todos.obtem(i);
            if (valor.compareTo(min) >= 0 && valor.compareTo(max) <= 0) {
                saida.adiciona(valor);
            }
        }
        return saida;
    }

}