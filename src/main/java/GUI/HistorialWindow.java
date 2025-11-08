package GUI;

import Logica.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistorialWindow extends Stage {

    private EightOffGame juego;
    private NodoDoble<Movimiento> cursor;
    private Label estadoLabel;
    private Button btnUndo, btnRedo, btnConfirmar;
    private EstadoTableroGUI preview;
    private VBox layout;

    public HistorialWindow(EightOffGame juego, Runnable onConfirmarEstado) {
        this.juego = juego;
        this.cursor = juego.getHistorial().getFin();
        if (this.cursor == null) {
            // No hay movimientos en el historial
            VBox layout = new VBox(10);
            layout.setPadding(new javafx.geometry.Insets(15));
            layout.getChildren().add(new Label("No hay movimientos en el historial."));
            Scene scene = new Scene(layout, 300, 100);
            setTitle("Historial de movimientos");
            setScene(scene);
            return; // evita seguir construyendo la ventana
        }

        estadoLabel = new Label();
        EstadoTablero estado = cursor.getInfo().getEstadoDespues();
        if (estado != null) {
            preview = new EstadoTableroGUI(estado, cursor.getInfo().getCartaMovida());
        } else {
            preview = new EstadoTableroGUI("Sin vista previa disponible.");
        }
        btnUndo = new Button("←");
        btnRedo = new Button("→");
        btnConfirmar = new Button("Usar este estado");

        btnUndo.setOnAction(e -> {
            if (cursor != null && cursor.getAnt() != null) {
                cursor.getInfo().revertir();
                cursor = cursor.getAnt();
                actualizarVista();
            }
        });

        btnRedo.setOnAction(e -> {
            if (cursor != null && cursor.getSig() != null) {
                cursor = cursor.getSig();
                cursor.getInfo().reaplicar();
                actualizarVista();
            }
        });

        btnConfirmar.setOnAction(e -> {
            juego.setCursorHistorial(cursor);
            juego.confirmarEstadoDesdeHistorial();
            onConfirmarEstado.run();
            close();
        });

        layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(15));
        layout.getChildren().addAll(estadoLabel, preview, btnUndo, btnRedo, btnConfirmar);
        actualizarVista();
        Scene scene = new Scene(layout, 780, 810);
        setTitle("Historial de movimientos");
        setScene(scene);
    }

    public void actualizarVista() {
        if (cursor != null) {
            Movimiento m = cursor.getInfo();
            estadoLabel.setText("Movimiento: " + m.getTipo() + " con " + m.getCartaMovida());
            EstadoTablero estado = cursor.getInfo().getEstadoDespues();
            if (estado != null) {
                preview = new EstadoTableroGUI(estado, cursor.getInfo().getCartaMovida());
            } else {
                preview = new EstadoTableroGUI("Sin vista previa disponible.");
            }
            layout.getChildren().set(1, preview);
        } else {
            estadoLabel.setText("Inicio del historial");
        }
    }
}
