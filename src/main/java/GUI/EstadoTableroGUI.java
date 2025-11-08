package GUI;

import Logica.*;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EstadoTableroGUI extends VBox {

    private EstadoTablero estado;

    public EstadoTableroGUI(EstadoTablero estado, Carta cartaResaltada) {
        this.estado = estado;
        setSpacing(10);
        setStyle("-fx-border-color: gray; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        mostrarEstado(cartaResaltada);
    }

    public EstadoTableroGUI(String mensaje) {
        setSpacing(10);
        setStyle("-fx-border-color: gray; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        getChildren().add(new Label(mensaje));
    }

    private void mostrarEstado(Carta cartaResaltada) {
        getChildren().clear();

        //Fundaciones
        HBox fundacionesBox = new HBox(10);
        NodoDoble<ListaDoble<Carta>> fundNode = estado.getFundaciones().getInicio();
        while (fundNode != null) {
            VBox fundacion = new VBox(5);
            ListaDoble<Carta> cartasFundacion = fundNode.getInfo();
            NodoDoble<Carta> cima = cartasFundacion.getInicio();
            while (cima != null && cima.getSig() != null) {
                cima = cima.getSig(); // avanzar hasta el final
            }
            if (cima != null) {
                StackPane cartaVista = crearVistaCarta(cima.getInfo(), cartaResaltada);
                fundacion.getChildren().add(cartaVista);
            }
            fundacionesBox.getChildren().add(fundacion);
            fundNode = fundNode.getSig();
        }

        //Celdas
        HBox celdasBox = new HBox(10);
        NodoDoble<Carta> celdaNode = estado.getCeldas().getInicio();
        while (celdaNode != null) {
            StackPane cartaVista = crearVistaCarta(celdaNode.getInfo(), cartaResaltada);
            celdasBox.getChildren().add(cartaVista);
            celdaNode = celdaNode.getSig();
        }

        //Columnas
        HBox columnasBox = new HBox(10);
        NodoDoble<ListaDoble<Carta>> colNode = estado.getColumnas().getInicio();
        while (colNode != null) {
            Pane columna = new Pane(); // permite posicionamiento libre
            columna.setPrefWidth(80); // espacio horizontal para cada columna
            double offsetY = 25;
            int index = 0;

            NodoDoble<Carta> cartaNode = colNode.getInfo().getInicio();
            while (cartaNode != null) {
                StackPane cartaVista = crearVistaCarta(cartaNode.getInfo(), cartaResaltada);
                cartaVista.setLayoutY(index * offsetY); // escalonado vertical
                columna.getChildren().add(cartaVista);
                cartaNode = cartaNode.getSig();
                index++;
            }

            columnasBox.getChildren().add(columna);
            colNode = colNode.getSig();
        }

        getChildren().add(new Label("Celdas libres"));
        getChildren().add(celdasBox);
        getChildren().add(new Label("Columnas"));
        getChildren().add(columnasBox);
        getChildren().add(new Label("Fundaciones"));
        getChildren().add(fundacionesBox);
    }

    private StackPane crearVistaCarta(Carta carta, Carta cartaResaltada) {
        CartaGUI vista = new CartaGUI(carta, -1, null); // sin controlador ni origenIndex
        vista.setPrefSize(10, 20);

        if (carta.equals(cartaResaltada)) {
            vista.setEffect(new DropShadow(20, Color.RED));
        }

        return vista;
    }
}
