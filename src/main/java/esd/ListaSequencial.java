package esd;

import java.util.Random;

public class ListaSequencial<T> {
    T[] area;
    int len = 0;
    final int defcap = 8;

    @SuppressWarnings("unchecked")
    public ListaSequencial() {
        area = (T[]) new Object[defcap];
    }

    @SuppressWarnings("unchecked")
    void expande(int newCap) {
        T[] temp = (T[]) new Object[newCap];
        for (int i = 0; i < len; i++) {
            temp[i] = area[i];
        }
        area = temp;
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
        if (len == area.length) {
            expande();
        }
        area[len++] = elemento;
    }

    public void insere(int indice, T elemento) {
        if (indice < 0 || indice > len) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        if (len == area.length) {
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
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        for (int i = indice; i < len - 1; i++) {
            area[i] = area[i + 1];
        }
        area[len - 1] = null;
        len--;
    }

    public void embaralha() {
        if (comprimento() <= 1) return;

        Random gerador = new Random();

        
        for (int i = comprimento() - 1; i > 0; i--) {
            int random_number = gerador.nextInt(i);

            T temporary = obtem(i);

            substitui(i, obtem(random_number));
            substitui(random_number, temporary);
        }
    }

    public void remove_ultimo() {
        if (len == 0) {
            throw new IndexOutOfBoundsException("Lista vazia");
        }
        area[len - 1] = null;
        len--;
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
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        return area[indice];
    }

    public void substitui(int indice, T valor) {
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
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
}