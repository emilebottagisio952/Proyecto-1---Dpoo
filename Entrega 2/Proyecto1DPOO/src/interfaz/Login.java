package interfaz;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import procesamiento.ProcesamientoEstudiante;
public class Login {
    private ProcesamientoEstudiante procesamiento;
    private String loginActual;
	private String rolActual;
    public Login(){
        this.procesamiento = new ProcesamientoEstudiante();
        try{
            procesamiento.cargarEstudiantesDesdeArchivo("estudiantes.txt");
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error al cargar los datos: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            
        }
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null); 



        // Crear un panel para el fondo amarillo
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.YELLOW);
        headerPanel.setBounds(0, 0, 400, 50); 
        headerPanel.setLayout(null);
        frame.add(headerPanel);

        // Etiqueta del título "Login"
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setBounds(170, 10, 100, 30); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);

        // Etiqueta "Usuario"
        JLabel userLabel = new JLabel("Usuario");
        userLabel.setBounds(50, 80, 100, 20);
        frame.add(userLabel);

        // Campo de texto para el usuario
        JTextField userField = new JTextField();
        userField.setBounds(150, 80, 200, 25);
        frame.add(userField);

        // Etiqueta "Clave"
        JLabel passwordLabel = new JLabel("Clave");
        passwordLabel.setBounds(50, 120, 100, 20);
        frame.add(passwordLabel);

        // Campo de texto para la clave
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 200, 25);
        frame.add(passwordField);

        // Botón "INGRESAR"
        JButton loginButton = new JButton("INGRESAR");
        loginButton.setBounds(150, 170, 100, 30);
        frame.add(loginButton);
        loginButton.addActionListener((actionEvent) -> {
            String login = userField.getText();
            String passwordString= new String(passwordField.getPassword());
            IngresarEstudiante(login, passwordString, frame );
        });

        // Hacer visible la ventana
        frame.setVisible(true);
    }

    private void IngresarEstudiante(String login, String contraseña, JFrame loginFrame){
        if (!procesamiento.ExisteEstudiante(login)){
            JOptionPane.showMessageDialog(null, "Login ingresado no esta registrado", "Esrror ",JOptionPane.ERROR_MESSAGE);
            
        }else{
            if (procesamiento.IngresoEstudiante(login, contraseña)){
                this.loginActual = login; 
                this.rolActual = "Estudiante";
                JOptionPane.showMessageDialog(null, "Contraseña correcta", "Exito",JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Bienvenido " + login , "Exito", JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();
                new InterfazEstudiante(login);

            }else{
                JOptionPane.showMessageDialog(null, "contraseña incorrecta", "Esrror ",JOptionPane.ERROR_MESSAGE);
                
            }
        }
    }
    
    public static void main(String[] args) {
        new Login();

        
    }


}