package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.Usuario;
import modelo.Usuario.IdiomaPreferido;
import modelo.Usuario.TemaPreferido;
import javax.swing.JLabel;
import vista.Principal;

public class Controlador implements ActionListener, ListSelectionListener {

	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelRegistro vistaRegistro;

	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelRegistro vistaRegistro) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaLogin = vistaLogin;
		this.vistaRegistro = vistaRegistro;

		this.inicializarControlador();
	}

	private void inicializarControlador() {
		this.vistaLogin.getBtnSignUp().addActionListener(this);
		this.vistaLogin.getBtnSignUp().setActionCommand(Principal.enumAcciones.PANEL_REGISTRO.toString());

		this.vistaLogin.getBtnLogin().addActionListener(this);
		this.vistaLogin.getBtnLogin().setActionCommand(Principal.enumAcciones.INICIAR_SESION.toString());

		this.vistaRegistro.getBtnRegistroSignUp().addActionListener(this);
		this.vistaRegistro.getBtnRegistroSignUp().setActionCommand(Principal.enumAcciones.REGISTRAR_USUARIO.toString());

		this.vistaRegistro.getBtnReturn().addActionListener(this);
		this.vistaRegistro.getBtnReturn().setActionCommand(Principal.enumAcciones.PANEL_LOGIN.toString());
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
		case REGISTRAR_USUARIO:
			this.registrarUsuario();
			
			break;
		case INICIAR_SESION:
			this.login();
			break;
		}
	}

	// METODOS

	private void login() {
		String usuario = this.vistaLogin.gettFUsuario().getText();
		String password = new String(this.vistaLogin.gettFContrasena().getPassword());

		// Compruebo si los datos están vacíos
		if (usuario.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Introduce el usuario y contraseña.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Obtener el usuario desde Firestore
		Usuario user = new Usuario().ObtenerContacto(usuario);

		// Comprobar si el usuario existe
		if (user != null) {
			// Comparar la contraseña
			if (user.getPassword().equals(password)) {
				JOptionPane.showMessageDialog(null, "Inicio de sesión correcto. \nBienvenid@ " + user.getUsuario(),
						"Información", JOptionPane.INFORMATION_MESSAGE);

				// Aquí puedes continuar con el flujo del programa
			} else {
				JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// REGISTRO DE NUEVO USUARIO EN LA BBDD (Crear el objeto para posteriormente
	// pasarselo a la clase Usuario)
	private void registrarUsuario() {

		Usuario nuevoUsuario = new Usuario();
		// Recojo los datos de los TextFields del panelInsertar y los guardo en
		// variables
		String nombre = this.vistaRegistro.gettFRegistroNombre().getText();
		String apellido = this.vistaRegistro.gettFRegistroApellido().getText();
		String email = this.vistaRegistro.gettFRegistroEmail().getText();
		String user = this.vistaRegistro.gettFRegistroUser().getText();

		String password = new String(this.vistaRegistro.getpFRegistroPassword().getPassword());

		IdiomaPreferido idioma = (IdiomaPreferido) this.vistaRegistro.getcBRegistroIdioma().getSelectedItem();
		TemaPreferido tema = (TemaPreferido) this.vistaRegistro.getcBRegistroTema().getSelectedItem();

		Date fechaNacimiento = this.vistaRegistro.getFechaNacimientoCalendar().getDate();

		nuevoUsuario.setNombre(nombre);
		nuevoUsuario.setApellido(apellido);
		nuevoUsuario.setEmail(email);
		nuevoUsuario.setUser(user);
		nuevoUsuario.setPassword(password);
		nuevoUsuario.setIdiomaPreferido(idioma);
		nuevoUsuario.setTemaPreferido(tema);
		nuevoUsuario.setFechaNacimiento(fechaNacimiento);

		nuevoUsuario.crearUsuario();
	}
	
	
	

	@Override
	public void valueChanged(ListSelectionEvent e) {
		System.out.println("Cambiar valor");
	}

}