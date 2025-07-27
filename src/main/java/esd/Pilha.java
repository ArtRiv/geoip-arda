package esd;

public class Pilha <T> {
    T[] mem;
    int len = 0;

    @SuppressWarnings("unchecked")
    public Pilha() {
      mem = (T[])new Object[1];
    }

    public int comprimento() {
        return len;
    }

    public int capacidade() {
        return mem.length;
    }

    public void empilha(T algo) {
        if (mem.length == len) {
            expande();
        }

        mem[len] = algo;
        len++;
    }

    public T desempilha() {
        if (len == 0) throw new IndexOutOfBoundsException("aa");

        T removed = mem[len - 1];
        mem[len - 1] = null;
        len--;
        return removed;
    }

    public T topo() {
        if (len == 0) throw new IndexOutOfBoundsException("Pilha vazia");
        return mem[len - 1];
    }

    public boolean estaVazia() {
        return (len == 0);
    }

    public void limpa() {
        for(int i = 0; i < len; i++) {
            mem[i] = null;
        }
        len = 0;
    }

    @SuppressWarnings("unchecked")
    void expande() {
        T[] mem_temp = (T[]) new Object[mem.length * 2];
        for (int i = 0; i < len; i++) {
            mem_temp[i] = mem[i];
        }
        mem = mem_temp;
    }
    
    public static String reduz_caminho(String caminho) {
        if (caminho == null || caminho.isEmpty()) {
            return "";
        }

        Pilha<String> pilha = new Pilha<>();
        String[] partes = caminho.split("/");

        for (String parte : partes) {
            if (parte.equals(".")) {
                continue;
            } else if (parte.equals("..")) {
                if (!pilha.estaVazia()) {
                    pilha.desempilha();
                }
            } else {
                pilha.empilha(parte);
            }
        }

        String resultado[] = new String[pilha.comprimento()];
        for (int i = pilha.comprimento() - 1; i >= 0; i--) {
            resultado[i] = pilha.desempilha();
        }
    
        return resultado.length == 0 ? "/" : "" + String.join("/", resultado);

    }

    public void descarta(int n, boolean do_topo) {
        if(!do_topo) { // se for falso, quer tirar do topo
            for (int i = 0; i <= n; i++) {
                this.desempilha();
            }
        } else {
            int until = len - n;

            for (int i = 0; i <= len; i++) {
                if (i >= until) {
                    this.desempilha();
                }
            }
            // len = 100
            // remover os 10 ultimos
            // until = 100 - 10 = 90
            // faço um for ate o len, quando bater o until, faço para começar a remover
        }
    }
}