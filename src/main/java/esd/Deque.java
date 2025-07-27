package esd;

import java.util.Arrays;

public class Deque<T> {
    T[] items;
    int count;
    int head;
    int tail;

    @SuppressWarnings("unchecked")
    public Deque() {
        items = (T[]) new Object[1];
        count = 0; 
        head = 0;
        tail = 0;
    }

    public int comprimento() {
        return count;
    }

    public int capacidade() {
        return items.length;
    }

    public void insere(T item) {
        if(count == items.length) expand();

        head = (head - 1 + this.capacidade()) % this.capacidade();
        items[head] = item;
        count++;
    }

    public void adiciona(T item) {
        if(count == items.length) expand();

        items[tail] = item;
        tail = (tail + 1) % capacidade();
        count++;
    }

    public T extrai_final() {
        if (esta_vazia()) throw new IndexOutOfBoundsException();

        tail = (tail - 1 + this.capacidade()) % this.capacidade();
        T removed = items[tail];
        items[tail] = null;
        tail = (tail + 1) % this.capacidade();
        count--;
        return removed;
    }

    public T extrai_inicio() {
        if (esta_vazia()) throw new IndexOutOfBoundsException();

        T removed = items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        count--;
        return removed;
    }

    public T acessa_inicio() {
        if (esta_vazia()) throw new IndexOutOfBoundsException();
        return items[head];
    }

    public T acessa_final() {
        if (esta_vazia()) throw new IndexOutOfBoundsException();
        int lastIndex = (tail - 1 + this.capacidade()) % this.capacidade();
        return items[lastIndex];
    }

    public T acessa(int position) {
        if (esta_vazia()) throw new IndexOutOfBoundsException();
        return items[(head + position) % this.capacidade()];
    }
    
    public boolean esta_vazia() {
        return count == 0;
    }

    public void limpa() {
        Arrays.fill(items, null);
    }

    @SuppressWarnings("unchecked")
    void expand() {
        T[] temp_items = (T[])new Object[items.length * 2];
        for (int i = 0; i < count; i++) {
            temp_items[i] = items[(head + i) % items.length];
        }

        items = temp_items;
        head = 0;
        tail = count;
    }

}