package Logica;


public class EstadoTablero {
    private ListaDoble<ListaDoble<Carta>> columnas;
    private ListaDoble<Carta> celdas;
    private ListaDoble<ListaDoble<Carta>> fundaciones;
    
    public EstadoTablero(EightOffGame juego){
        columnas = copiarColumnas(juego.getColumnas());
        celdas = copiarCeldas(juego.getCeldas());
        fundaciones = copiarFundaciones(juego.getFundaciones());
    }
    
    private ListaDoble<ListaDoble<Carta>> copiarColumnas(Columna[] columnasOriginales){
        ListaDoble<ListaDoble<Carta>> copia = new ListaDoble<>();
        for (Columna col : columnasOriginales){
            ListaDoble<Carta> cartasColumna = new ListaDoble<>();
            Nodo<Carta> actual = col.getCartas().getInicio();
            while ( actual != null){
                cartasColumna.insertaFinal(actual.getInfo());
                actual = actual.getSig();
            }
            copia.insertaFinal(cartasColumna);
        }
        return copia;
    }
    
    private ListaDoble<Carta> copiarCeldas(CeldaLibre celdasOriginales){
        ListaDoble<Carta> copia = new ListaDoble<>();
        Nodo<Carta> actual = celdasOriginales.getCeldas().getInicio();
        while (actual != null){
            copia.insertaFinal(actual.getInfo());
            actual = actual.getSig();
        }
        return copia;
    }
    
    private ListaDoble<ListaDoble<Carta>> copiarFundaciones(Fundacion[] fundacionesOriginales){
        ListaDoble<ListaDoble<Carta>> copia = new ListaDoble<>();
        for (Fundacion fun : fundacionesOriginales){
            ListaDoble<Carta> cartasFundacion = new ListaDoble<>();
            Nodo<Carta> actual = fun.getCartas().getInicio();
            while (actual != null){
                cartasFundacion.insertaFinal(actual.getInfo());
                actual = actual.getSig();
            }
            copia.insertaFinal(cartasFundacion);
        }
        return copia;
    }
    
    public ListaDoble<ListaDoble<Carta>> getColumnas(){
        return columnas;
    }
    
    public ListaDoble<Carta> getCeldas(){
        return celdas;
    }
    
    public ListaDoble<ListaDoble<Carta>> getFundaciones(){
        return fundaciones;
    }
}
