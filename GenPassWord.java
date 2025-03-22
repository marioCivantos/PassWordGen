package passwords;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GenPassWord {

    public static void main(String[] args) {
        // Crear el marco
        JFrame frame = new JFrame("Password Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(390, 100, 600, 300);
        
        // Crear un panel
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // Hacer el marco visible
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

     // Crear un JLabel con texto HTML para permitir múltiples líneas
        String instructions = "<html><body style='width: 5000px; padding: 0px; font-size: 8px'>"
                + "Este programa genera contraseñas seguras.<br>"
                + "Por favor, introduce el nombre de la plataforma para la que necesitas la contraseña y haz clic en el botón 'Generar'.<br>"
                + "La contraseña se mostrará en el campo de abajo y se guardará automáticamente en el archivo listado.txt <br>"
                + "</body></html>";
        
        JLabel instruccion = new JLabel(instructions);
        instruccion.setBounds(10, 5, 1900, 60);
        instruccion.setForeground(Color.GRAY);
        panel.add(instruccion);
        
        // Crear una etiqueta para la plataforma
        JLabel platformLabel = new JLabel("Plataforma:");
        platformLabel.setBounds(100, 90, 150, 25);
        panel.add(platformLabel);

        // Crear un campo de texto para la plataforma con placeholder
        PlaceholderTextField platformField = new PlaceholderTextField("Escribe la plataforma...");
        platformField.setBounds(250, 90, 200, 25);
        panel.add(platformField);

        // Crear una etiqueta para la contraseña generada
        JLabel passwordLabel = new JLabel("Contraseña generada:");
        passwordLabel.setBounds(100, 130, 150, 25);
        panel.add(passwordLabel);

        // Crear un campo de texto para la contraseña generada
        JTextField passwordField = new JTextField(20);
        passwordField.setBounds(250, 130, 200, 25);
        passwordField.setEditable(false);
        panel.add(passwordField);

        // Crear un botón para generar la contraseña
        JButton generateButton = new JButton("Generar");
        generateButton.setBounds(215, 190, 150, 25);
        panel.add(generateButton);

        // Acción del botón
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plataforma = platformField.getText().trim();
                if (plataforma.isEmpty() || plataforma.equals("Escribe la plataforma...")) {
                    JOptionPane.showMessageDialog(panel, "Por favor, ingrese una plataforma.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String generatedPassword = generaPassword();
                passwordField.setText(generatedPassword);
                writeToFile(plataforma, generatedPassword);
                JOptionPane.showMessageDialog(panel, "Contraseña generada y guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private static void writeToFile(String plataforma, String contrasenia) {
        try {
            File fsalida = new File("C:/Users/mario/OneDrive/Documentos/listado.txt");
            FileWriter escritor = new FileWriter(fsalida, true);
            BufferedWriter bw = new BufferedWriter(escritor);

            bw.write("\n" + plataforma + "--> " + contrasenia);
            bw.close();
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static int generaNumAleatorio(int n1, int n2) {
        return (int) (n1 + Math.random() * (n2 - n1 + 1));
    }

    public static String generaPassword() {
        String cadMayusc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String cadMinusc = cadMayusc.toLowerCase();
        String cadSignos = "!#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        String cadNumeros = "0123456789";

        ArrayList<Character> mayusc = new ArrayList<Character>();
        ArrayList<Character> minusc = new ArrayList<Character>();
        ArrayList<Character> signos = new ArrayList<Character>();
        ArrayList<Character> nums = new ArrayList<Character>();

        for (int i = 0; i < cadMayusc.length(); i++) {
            char caracter = cadMayusc.charAt(i);
            mayusc.add(caracter);
        }

        for (int i = 0; i < cadMinusc.length(); i++) {
            char caracter = cadMinusc.charAt(i);
            minusc.add(caracter);
        }

        for (int i = 0; i < cadSignos.length(); i++) {
            char caracter = cadSignos.charAt(i);
            signos.add(caracter);
        }

        for (int i = 0; i < cadNumeros.length(); i++) {
            char caracter = cadNumeros.charAt(i);
            nums.add(caracter);
        }

        int cont = 1;
        String passkey = "";
        while (cont <= 16) {
            if (cont == 15) {
                int random4 = generaNumAleatorio(0, 31);
                passkey = passkey + signos.get(random4);
                cont++;
            }
            int random0 = generaNumAleatorio(1, 3);

            switch (random0) {
                case 1: {
                    int random1 = generaNumAleatorio(0, 25);
                    passkey = passkey + mayusc.get(random1);
                    cont++;
                    break;
                }
                case 2: {
                    int random2 = generaNumAleatorio(0, 25);
                    passkey = passkey + minusc.get(random2);
                    cont++;
                    break;
                }
                case 3: {
                    int random3 = generaNumAleatorio(0, 9);
                    passkey = passkey + nums.get(random3);
                    cont++;
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unexpected value: " + random0);
            }
        }

        System.out.println("Tu nueva contraseña es: " + passkey);

        return passkey;
    }

    // Clase para añadir texto de sugerencia en JTextField
    static class PlaceholderTextField extends JTextField {
        private String placeholder = "Escribe la plataforma...";

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            this.setForeground(Color.GRAY);
            this.setText(placeholder);
            this.setFont(new Font("SansSerif", Font.ITALIC, 12));
            
            this.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (getText().equals(placeholder)) {
                        setText("");
                        setForeground(Color.BLACK);
                        setFont(new Font("SansSerif", Font.PLAIN, 12));
                    }
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (getText().isEmpty()) {
                        setForeground(Color.GRAY);
                        setText(placeholder);
                        setFont(new Font("SansSerif", Font.ITALIC, 12));
                    }
                }
            });
        }
    }
}
