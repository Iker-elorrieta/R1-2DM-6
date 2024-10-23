package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.Ejercicio;
import modelo.Usuario;
import modelo.Usuario.IdiomaPreferido;
import modelo.Usuario.TemaPreferido;
import modelo.Workout;
import vista.Principal;

public class Controlador implements ActionListener {

	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelRegistro vistaRegistro;
	private vista.PanelWorkouts vistaWorkouts;

	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelRegistro vistaRegistro,
			vista.PanelWorkouts vistaWorkouts) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaLogin = vistaLogin;
		this.vistaRegistro = vistaRegistro;
		this.vistaWorkouts = vistaWorkouts;

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

		this.vistaWorkouts.getBtnReturn().addActionListener(this);
		this.vistaWorkouts.getBtnReturn().setActionCommand(Principal.enumAcciones.PANEL_LOGIN.toString());

		this.vistaWorkouts.getWorkoutList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // Para evitar llamadas dobles
					obtenerEjercicios();
					cargarInfoWorkout();
				}
			}
		});

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
		case PANEL_WORKOUTS:
			this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_WORKOUTS);
		default:

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

		Usuario user = new Usuario().obtenerUsuario(usuario);

		if (user == null) {
			JOptionPane.showMessageDialog(null, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!user.getPassword().equals(password)) {
			JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Inicio de sesión correcto. \nBienvenid@ " + user.getUsuario(),
				"Información", JOptionPane.INFORMATION_MESSAGE);

		cargarWorkouts(user, Principal.enumAcciones.PANEL_WORKOUTS);

		this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_WORKOUTS);
		this.vistaLogin.gettFUsuario().setText("");
		this.vistaLogin.gettFContrasena().setText("");
	}

	private void registrarUsuario() {

		Usuario nuevoUsuario = new Usuario();

		// RECOGER DATOS
		String nombre = this.vistaRegistro.gettFRegistroNombre().getText();
		String apellido = this.vistaRegistro.gettFRegistroApellido().getText();
		String email = this.vistaRegistro.gettFRegistroEmail().getText();
		String user = this.vistaRegistro.gettFRegistroUser().getText();

		String password = new String(this.vistaRegistro.getpFRegistroPassword().getPassword());

		IdiomaPreferido idioma = (IdiomaPreferido) this.vistaRegistro.getcBRegistroIdioma().getSelectedItem();
		TemaPreferido tema = (TemaPreferido) this.vistaRegistro.getcBRegistroTema().getSelectedItem();

		Date fechaNacimiento = this.vistaRegistro.getFechaNacimientoCalendar().getDate();

		// COMPROBACIÓN DE CAMPOS VACIOS
		if (nombre.isEmpty() || email.isEmpty() || user.isEmpty() || password.isEmpty() || fechaNacimiento == null) {
			JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// COMPROBACIÓN DE USUARIO EXISTENTE
		if (nuevoUsuario.obtenerUsuario(user) != null) {
			JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// COMPROBACIÓN DE FORMATO EMAIL
		String emailFormato = "^[\\w-](?!.*\\.\\.)[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,7}$";
		if (!email.matches(emailFormato)) {
			JOptionPane.showMessageDialog(null, "Formato de email incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		nuevoUsuario.setNombre(nombre);
		nuevoUsuario.setApellido(apellido);
		nuevoUsuario.setEmail(email);
		nuevoUsuario.setUser(user);
		nuevoUsuario.setPassword(password);
		nuevoUsuario.setIdiomaPreferido(idioma);
		nuevoUsuario.setTemaPreferido(tema);
		nuevoUsuario.setFechaNacimiento(fechaNacimiento);

		this.vistaRegistro.gettFRegistroNombre().setText("");
		this.vistaRegistro.gettFRegistroApellido().setText("");
		this.vistaRegistro.gettFRegistroEmail().setText("");
		this.vistaRegistro.gettFRegistroUser().setText("");
		this.vistaRegistro.getpFRegistroPassword().setText("");
		this.vistaRegistro.getFechaNacimientoCalendar().setDate(new Date());

		nuevoUsuario.crearUsuario();

		JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.", "Información",
				JOptionPane.INFORMATION_MESSAGE);
		this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_LOGIN);
	}

	private void cargarWorkouts(Usuario usuario, Principal.enumAcciones accion) {
		Workout workouts = new Workout();
		ArrayList<Workout> listaWorkouts = workouts.obtenerWorkouts((long) usuario.getNivelUsuario());

		vistaWorkouts.getWorkoutListModel().clear();

		for (Workout workout : listaWorkouts) {
			String workoutInfo = workout.getId() + ": " + workout.getNombre() + " (Nivel " + workout.getNivel() + ")";
			String url = workout.getVideoUrl();
			String descripcion = workout.getDescripcion();
			vistaWorkouts.addWorkout(workoutInfo, url, descripcion);
		}
	}

	private void cargarInfoWorkout() {
		String workoutSeleccionado = vistaWorkouts.getWorkoutList().getSelectedValue();
		String urlWorkout = vistaWorkouts.getWorkoutUrl(workoutSeleccionado);
		String descripcionWorkout = vistaWorkouts.getWorkoutDescripcion(workoutSeleccionado);

		if (workoutSeleccionado == null)
			return;

		// CARGAR URL

		if (urlWorkout == null || urlWorkout.isEmpty()) {
			vistaWorkouts.getBtnVideo().setEnabled(false);
			return;
		}

		for (ActionListener al : vistaWorkouts.getBtnVideo().getActionListeners()) {
			vistaWorkouts.getBtnVideo().removeActionListener(al);
		}

		vistaWorkouts.getBtnVideo().setEnabled(true);

		vistaWorkouts.getBtnVideo().addActionListener(event -> {
			try {
				Desktop.getDesktop().browse(new URI(urlWorkout));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		// CARGAR DESCRIPCIÓN

		if (descripcionWorkout == null || descripcionWorkout.isEmpty()) {
			vistaWorkouts.getBtnDescripcion().setEnabled(false);
			return;
		}

		for (ActionListener al : vistaWorkouts.getBtnDescripcion().getActionListeners()) {
			vistaWorkouts.getBtnDescripcion().removeActionListener(al);
		}

		vistaWorkouts.getBtnDescripcion().setEnabled(true);

		int max = 50;

		StringBuilder resultado = new StringBuilder(descripcionWorkout);

		if (max > resultado.length())
			max = resultado.length();

		for (int i = max; i < resultado.length(); i += max) {

			while (i > 0 && resultado.charAt(i - 1) != ' ') {
				i--;
			}

			if (i > 0 && i < resultado.length()) {
				resultado.replace(i, i + 1, "\n");
			}
		}

		String a = resultado.toString();
		vistaWorkouts.getBtnDescripcion().addActionListener(event -> {
			JOptionPane.showMessageDialog(null, a, "Descripción", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private void obtenerEjercicios() {
		String workoutSeleccionado = vistaWorkouts.getWorkoutList().getSelectedValue();

		if (workoutSeleccionado == null)
			return;

		// Extraer el ID del workout (asumiendo que el ID está antes de ": " en el
		// string)
		String workoutId = workoutSeleccionado.split(":")[0];

		// Limpiar la lista de ejercicios actual
		vistaWorkouts.getEjersListModel().clear();

		// Obtener los ejercicios para el workout seleccionado
		Ejercicio ejercicioModel = new Ejercicio();
		ArrayList<Ejercicio> listaEjercicios = ejercicioModel.obtenerEjercicios(workoutId);

		// Añadir los ejercicios al modelo de la lista de ejercicios
		for (Ejercicio ejercicio : listaEjercicios) {
			String ejercicioInfo = ejercicio.getNombre();
			vistaWorkouts.getEjersListModel().addElement(ejercicioInfo);
		}

	}

}