package interfaz;

import java.awt.*;
import javax.swing.*;

public class InterfazEstudiante {

    public InterfazEstudiante(String nombreEstudiante){
        JFrame frame = new JFrame("Sistema de rutas de Aprendizaje");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(null);

        //Panel superior
        JPanel headerJPanel = new JPanel();
        headerJPanel.setBackground(Color.lightGray);
        headerJPanel.setBounds(0, 0, 400, 100);
        headerJPanel.setLayout(null);
        frame.add(headerJPanel);

        //Etiqueta del titulo
        JLabel label = new JLabel("Hola, " + nombreEstudiante);
        label.setBounds(50, 20, 300, 30);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        headerJPanel.add(label);
        
        //subtitulo
        JLabel subtitulo = new JLabel("Bienvenido al sistema de rutas de Aprendizaje");
        subtitulo.setBounds(50,50,300,20);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        headerJPanel.add(subtitulo);

        //Crear un panel para los botones 
        JPanel botonesPanel = new JPanel();
        botonesPanel.setBounds(50, 120, 300, 300);
        botonesPanel.setLayout(new GridLayout(8, 1, 5, 10));
        frame.add(botonesPanel);

        //Botones
        String[] buttonLabels = {
            "Ver Actividades",
            "Ver Reseñas",
            "Ver Learning Paths",
            "Inscribir en Learning Paths",
            "Revisar Progreso",
            "Desarrollar Actividad",
            "Salvar Datos",
            "Crear Reseña",
        };
        for(String titulo: buttonLabels) {
            JButton button = new JButton(titulo);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            botonesPanel.add(button);
        }









         // Hacer visible la ventana
         frame.setVisible(true);
    }
}