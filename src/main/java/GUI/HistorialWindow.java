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
    
    public HistorialWindow(EightOffGame juego, Runnable onConfirmarEstado){
        this.juego = juego;
        this.cursor = juego.getHistorial().getFin();
        
        estadoLabel = new Label();
        btnUndo = new Button("←");
        btnRedo = new Button("→");
        btnConfirmar = new Button("Usar este estado");
        
        actualizarVista();
        
        btnUndo.setOnAction(e -> {
            if(cursor != null && cursor.getAnt() != null){
                cursor.getInfo().revertir();
                cursor = cursor.getAnt();
                actualizarVista();
            }
        });
        
        btnRedo.setOnAction(e -> {
            if(cursor != null && cursor.getSig() != null){
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
        
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(15));
        layout.getChildren().addAll(estadoLabel, btnUndo, btnRedo, btnConfirmar);
        Scene scene = new Scene(layout, 300, 150);
        setTitle("Historial de movimientos");
        setScene(scene);
    }
    
    public void actualizarVista(){
        if(cursor != null){
            Movimiento m = cursor.getInfo();
            estadoLabel.setText("Movimiento: " + m.getTipo() + " con " + m.getCartaMovida());
        } else {
            estadoLabel.setText("Inicio del historial");
        }
    }
}
