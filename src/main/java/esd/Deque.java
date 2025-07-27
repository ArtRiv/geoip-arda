    package esd;

    public class Deque <T> {
        T[] mem;
        int size = 0;
        int end = 0;
        int start = 0;

        @SuppressWarnings("unchecked")
        public Deque() {
            mem = (T[])new Object[1];
        }
        
        public void adiciona(T algo) {
            if (mem.length == size) {
                expande();
            }

            mem[end] = algo;
            end = (end + 1) % mem.length;
            size++;
        }

        public void insere(T algo) {
            if (mem.length == size) {
                expande();
            }

            start = (start - 1 + mem.length) % mem.length;
            mem[start] = algo;
            size++;
        }

        public T extrai_final() {
            if (size == 0) throw new IndexOutOfBoundsException();
        
            end = (end - 1 + mem.length) % mem.length;
            T removed = mem[end];
            mem[end] = null;
            size--;
            return removed;
        }
        
        public T extrai_inicio() {
            if (size == 0) throw new IndexOutOfBoundsException();
        
            T removed = mem[start];
            mem[start] = null;
            start = (start + 1) % mem.length;
            size--;
            return removed;
        }

        public T acessa_final() {
            if (size == 0) throw new IndexOutOfBoundsException();
            return mem[(end - 1 + mem.length) % mem.length];
        }

        public T acessa_inicio() {
            if (size == 0) throw new IndexOutOfBoundsException();
            return mem[start];
        }

        public T acessa(int index) {
            if (size == 0) throw new IndexOutOfBoundsException();
            return mem[(start + index) % mem.length];
        }

        public int comprimento() {
            return size;
        }

        public int capacidade() {
            return mem.length;
        }

        public boolean estaVazia() {
            return size == 0;
        }

        public void limpa() {
            for(int i = 0; i < size; i++) {
                mem[i] = null;
            }
            size = 0;
            start = 0;
            end = 0;
        }

        
        @SuppressWarnings("unchecked")
        public void expande() {
            T[] mem_temp = (T[]) new Object[mem.length * 2];
            for (int i = 0; i < size; i++) {
                mem_temp[i] = mem[(start + i) % mem.length];
            }
            mem = mem_temp;
            start = 0;
            end = size;
        }
    }
