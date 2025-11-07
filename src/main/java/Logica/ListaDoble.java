package Logica;

public class ListaDoble<T> {

    private NodoDoble<T> inicio;
    private NodoDoble<T> fin;

    public ListaDoble() {
        inicio = null;
        fin = null;
    }

    public void insertaInicio(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (inicio == null) {
            inicio = fin = nuevo;
        } else {
            nuevo.setSig(inicio);
            inicio.setAnt(nuevo);
            inicio = nuevo;
        }
    }

    public void insertaFinal(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (inicio == null) {
            inicio = fin = nuevo;
        } else {
            fin.setSig(nuevo);
            nuevo.setAnt(fin);
            fin = nuevo;
        }
    }

    public void eliminaInicio() {
        if (inicio == null) {
            System.out.println("Lista vacía");
        } else if (inicio == fin) {
            inicio = fin = null;
        } else {
            inicio = inicio.getSig();
            inicio.setAnt(null);
        }
    }

    public void eliminaFinal() {
        if (inicio == null) {
            System.out.println("Lista vacía");
        } else if (inicio == fin) {
            inicio = fin = null;
        } else {
            fin = fin.getAnt();
            fin.setSig(null);
        }
    }

    public void recorrer() {
        NodoDoble<T> actual = inicio;
        while (actual != null) {
            System.out.print(actual.getInfo() + " ");
            actual = actual.getSig();
        }
        System.out.println();
    }

    public void recorrerInverso() {
        NodoDoble<T> actual = fin;
        while (actual != null) {
            System.out.print(actual.getInfo() + " ");
            actual = actual.getAnt();
        }
        System.out.println();
    }

    public void setFin(NodoDoble<T> nuevoFin) {
        fin = nuevoFin;
        if (fin != null) {
            fin.setSig(null); // aseguramos que no haya elementos después
        }
    }

    public NodoDoble<T> getInicio() {
        return inicio;
    }

    public NodoDoble<T> getFin() {
        return fin;
    }
}
