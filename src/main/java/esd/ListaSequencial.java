package esd;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ListaSequencial<T> {

    T[] area;
    int len = 0;
    static final int defcap = 8;

    public ListaSequencial() {
        area = (T[]) new Object[defcap];
    }

    void expande(int novaCapacidade) {
        T[] novaLista = Arrays.copyOf(area, novaCapacidade);
        area = novaLista;
    }

    public void expande() {
        expande(2 * area.length);
    }

    public boolean esta_vazia() {
        return len == 0;
    }

    public int capacidade() {
        return area.length;
    }

    public void adiciona(T elemento) {
        if (len == capacidade()) {
            expande();
        }
        area[len++] = elemento;
    }

    public void insere(int indice, T elemento) {
        if (indice < 0 || indice > len) {
            throw new IndexOutOfBoundsException("Índice inválido.");
        }
        if (len == capacidade()) {
            expande();
        }
        for (int i = len; i > indice; i--) {
            area[i] = area[i - 1];
        }
        area[indice] = elemento;
        len++;
    }

    public void remove(int indice) {
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Índice inválido.");
        }
        for (int i = indice; i < len - 1; i++) {
            area[i] = area[i + 1];
        }
        area[len - 1] = null;
        len--;
    }

    public void remove_ultimo() {
        if (esta_vazia()) {
            throw new IndexOutOfBoundsException("Lista vazia.");
        }
        len--;
        area[len] = null;
    }

    public int procura(T valor) {
        for (int i = 0; i < len; i++) {
            if (area[i].equals(valor)) {
                return i;
            }
        }
        return -1;
    }

    public T obtem(int indice) {
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Índice inválido.");
        }
        return area[indice];
    }

    public void substitui(int indice, T valor) {
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Índice inválido.");
        }
        area[indice] = valor;
    }

    public int comprimento() {
        return len;
    }

    public void limpa() {
        for (int i = 0; i < len; i++) {
            area[i] = null;
        }
        len = 0;
    }

    public void bubbleSort() {
        boolean trocou;
        for (int i = 0; i < len - 1; i++) {
            trocou = false;
            for (int j = 0; j < len - i - 1; j++) {
                Comparable<T> val = (Comparable<T>) area[j];
                if (val.compareTo(area[j + 1]) > 0) {
                    T temp = area[j];
                    area[j] = area[j + 1];
                    area[j + 1] = temp;
                    trocou = true;
                }
            }
            if (!trocou)
                break;
        }
    }

    public void quickSort() {
        quickSort(0, len - 1);
    }

    private void quickSort(int inicio, int fim) {
        if (inicio < fim) {
            int p = particionar(inicio, fim);
            quickSort(inicio, p - 1);
            quickSort(p + 1, fim);
        }
    }

    private int particionar(int inicio, int fim) {
        T pivo = area[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            Comparable<T> val = (Comparable<T>) area[j];

            if (val.compareTo(pivo) <= 0) {
                i++;
                T temp = area[i];
                area[i] = area[j];
                area[j] = temp;
            }
        }
        T temp = area[i + 1];
        area[i + 1] = area[fim];
        area[fim] = temp;
        return i + 1;
    }

    public void mergeSort() {
        mergeSort(0, len - 1);
    }

    private void mergeSort(int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(inicio, meio);
            mergeSort(meio + 1, fim);
            merge(inicio, meio, fim);
        }
    }

    private void merge(int inicio, int meio, int fim) {
        int tamanhoEsq = meio - inicio + 1;
        int tamanhoDir = fim - meio;

        T[] esq = (T[]) new Object[tamanhoEsq];
        T[] dir = (T[]) new Object[tamanhoDir];

        for (int i = 0; i < tamanhoEsq; i++) {
            esq[i] = area[inicio + i];
        }
        for (int j = 0; j < tamanhoDir; j++) {
            dir[j] = area[meio + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = inicio;

        while (i < tamanhoEsq && j < tamanhoDir) {
            Comparable<T> val = (Comparable<T>) esq[i];

            if (val.compareTo(dir[j]) <= 0) {
                area[k++] = esq[i++];
            } else {
                area[k++] = dir[j++];
            }
        }

        while (i < tamanhoEsq) {
            area[k++] = esq[i++];
        }

        while (j < tamanhoDir) {
            area[k++] = dir[j++];
        }
    }


    public void ordena() {
        mergeSort(); 
    }

    public void inverte() {
        if (len <= 1) { 
            return;
        }

        int inicio = 0;
        int fim = len - 1;

        while (inicio < fim) {
            T temp = area[inicio];
            area[inicio] = area[fim];
            area[fim] = temp;

            inicio++;
            fim--;
        }
    }

    public void adicionaLista(ListaSequencial<T> lista) {
        for (int a = 0; a < lista.comprimento(); a++) {
            adiciona(lista.obtem(a));
        }
    }
}