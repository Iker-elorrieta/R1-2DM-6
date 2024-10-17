package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.Contacto;
import modelo.Usuario;
import vista.Principal;

public class Controlador implements ActionListener, ListSelectionListener {

    private vista.Principal vistaPrincipal;
    private vista.PanelLogin vistaLogin;
    private vista.PanelRegistro vistaRegistro;

    public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelRegistro vistaRegistro ) {
        this.vistaPrincipal = vistaPrincipal;
        this.vistaLogin = vistaLogin;
        this.vistaRegistro = vistaRegistro;

        this.inicializarControlador();
    }

    private void inicializarControlador() {
        this.vistaLogin.getBtnSignUp().addActionListener(this);
        this.vistaLogin.getBtnSignUp()
            .setActionCommand(Principal.enumAcciones.PANEL_REGISTRO.toString());
        
        this.vistaLogin.getBtnLogin().addActionListener(this);
        this.vistaLogin.getBtnLogin()
            .setActionCommand(Principal.enumAcciones.INICIAR_SESION.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

        switch (accion) {
            case PANEL_LOGIN:
                this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_LOGIN);
                break;
            case PANEL_REGISTRO:
                this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_REGISTRO);
                break;
                
            case INICIAR_SESION:
            	this.login();
                break;
                
        }
    }
    
    
    
    
   
    private void login() {
        String usuario = this.vistaLogin.gettFUsuario().getText();
        String password = new String(this.vistaLogin.getTFContrasena().getPassword());

        // Compruebo si los datos están vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
        	JOptionPane.showMessageDialog(null, "Introduce el usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el usuario desde Firestore
        Usuario user = new Usuario().ObtenerContacto(usuario);

        // Comprobar si el usuario existe
        if (user != null) {
            // Comparar la contraseña
            if (user.getPassword().equals(password)) {
            	JOptionPane.showMessageDialog(null, "Inicio de sesión correcto. \nBienvenid@ " + user.getUsuario(), "Información", JOptionPane.INFORMATION_MESSAGE);
           
                // Aquí puedes continuar con el flujo del programa
            } else {
            	JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
        	 JOptionPane.showMessageDialog(null, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Cambiar valor");
    }
}
