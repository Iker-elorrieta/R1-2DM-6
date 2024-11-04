package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.nio.file.spi.FileSystemProvider;
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

	// Constantes para mensajes de error e información
	private static final String ERROR_TITLE = "Error";
	private static final String INFO_TITLE = "Información";
	private static final String EMPTY_FIELDS_MESSAGE = "Todos los campos son obligatorios.";
	private static final String INVALID_EMAIL_FORMAT_MESSAGE = "Formato de email incorrecto.";
	private static final String USER_EXISTS_MESSAGE = "El usuario ya existe.";
	private static final String USER_NOT_FOUND_MESSAGE = "El usuario no existe.";
	private static final String LOGIN_SUCCESS_MESSAGE = "Inicio de sesión correcto. \nBienvenid@ ";
	private static final String INCORRECT_PASSWORD_MESSAGE = "Contraseña incorrecta.";

	// Referencias a las vistas
	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelRegistro vistaRegistro;
	private vista.PanelWorkouts vistaWorkouts;
	private vista.PanelEjercicios vistaEjercicios;

	/**
	 * Constructor del controlador, inicializa las vistas y el controlador
	 *
	 * @param vistaPrincipal  vista del panel Principal
	 * @param vistaLogin      vista del panel Login
	 * @param vistaRegistro   vista del panel de Registro
	 * @param vistaWorkouts   vista del panel de Workouts
	 * @param vistaEjercicios vista del panel de Ejercicios
	 */
	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelRegistro vistaRegistro,
			vista.PanelWorkouts vistaWorkouts, vista.PanelEjercicios vistaEjercicios) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaLogin = vistaLogin;
		this.vistaRegistro = vistaRegistro;
		this.vistaWorkouts = vistaWorkouts;
		this.vistaEjercicios = vistaEjercicios;

		this.inicializarControlador();
	}

	/**
	 * Acciones de los componentes de las vistas
	 */
	private void inicializarControlador() {

		accionesVistaLogin();
		accionesVistaRegistro();
		accionesVistaWorkouts();
	}

	/**
	 * Acciones del panel del Login
	 */
	private void accionesVistaLogin() {
		this.vistaLogin.getBtnSignUp().addActionListener(this);
		this.vistaLogin.getBtnSignUp().setActionCommand(Principal.enumAcciones.PANEL_REGISTRO.toString());

		this.vistaLogin.getBtnLogin().addActionListener(this);
		this.vistaLogin.getBtnLogin().setActionCommand(Principal.enumAcciones.INICIAR_SESION.toString());
	}

	/**
	 * Acciones del panel de Registro
	 */
	private void accionesVistaRegistro() {
		this.vistaRegistro.getBtnRegistroSignUp().addActionListener(this);
		this.vistaRegistro.getBtnRegistroSignUp().setActionCommand(Principal.enumAcciones.REGISTRAR_USUARIO.toString());

		this.vistaRegistro.getBtnReturn().addActionListener(this);
		this.vistaRegistro.getBtnReturn().setActionCommand(Principal.enumAcciones.PANEL_LOGIN.toString());

	}

	/**
	 * Acciones del panel de Workouts
	 */
	private void accionesVistaWorkouts() {
		this.vistaWorkouts.getBtnReturn().addActionListener(this);
		this.vistaWorkouts.getBtnReturn().setActionCommand(Principal.enumAcciones.PANEL_LOGIN.toString());

		// Listener para el elemento seleccionado de la lista de workouts
		this.vistaWorkouts.getWorkoutList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					obtenerEjercicios(); // Obtiene los ejercicios del workout seleccionado
					cargarInfoWorkout();
				}
			}
		});

		this.vistaWorkouts.getBtnStartWorkout().addActionListener(this);
		this.vistaWorkouts.getBtnStartWorkout().setActionCommand(Principal.enumAcciones.PANEL_EJERCICIOS.toString());

	}

	/**
	 * Eventos de las acciones
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());
		switch (accion) {
		case PANEL_LOGIN:
			visualizarPanel(Principal.enumAcciones.PANEL_LOGIN);
			break;
		case PANEL_REGISTRO:
			visualizarPanel(Principal.enumAcciones.PANEL_REGISTRO);
			break;
		case REGISTRAR_USUARIO:
			registrarUsuario();
			break;
		case INICIAR_SESION:
			login();
			break;
		case PANEL_WORKOUTS:
			visualizarPanel(Principal.enumAcciones.PANEL_WORKOUTS);
			break;
		case PANEL_EJERCICIOS:
			mostrarEjercicioSeleccionado();
			visualizarPanel(Principal.enumAcciones.PANEL_EJERCICIOS);
			break;
		default:
			break;
		}
	}

	/**
	 * Visualizar un panel
	 *
	 * @param panel variable para indicar el panel que se visualiza
	 */
	private void visualizarPanel(Principal.enumAcciones panel) {
		this.vistaPrincipal.visualizarPaneles(panel);
	}

	/**
	 * Inicio de sesión
	 */
	private void login() {
		String usuario = this.vistaLogin.gettFUsuario().getText();
		String password = new String(this.vistaLogin.gettFContrasena().getPassword());

		// Comprueba que los campos no están vacíos
		if (usuario.isEmpty() || password.isEmpty()) {
			mostrarErrorDialog(EMPTY_FIELDS_MESSAGE);
			return;
		}

		// Obtiene el usuario y comprueba la contraseña
		Usuario user = new Usuario().obtenerUsuario(usuario);

		if (user == null) {
			JOptionPane.showMessageDialog(null, USER_NOT_FOUND_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!user.getPassword().equals(password)) {
			JOptionPane.showMessageDialog(null, INCORRECT_PASSWORD_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, LOGIN_SUCCESS_MESSAGE + user.getUsuario(), "Información",
				JOptionPane.INFORMATION_MESSAGE);

		limpiarCamposLogin();

		cargarWorkouts(user, Principal.enumAcciones.PANEL_WORKOUTS);

		this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_WORKOUTS);
		this.vistaLogin.gettFUsuario().setText("");
		this.vistaLogin.gettFContrasena().setText("");

		try {
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", "backupgym.jar");
			pb.inheritIO();
			Process process = pb.start();
			System.out.println(process.isAlive());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Vaciar los campos del panelLogin
	 */
	private void limpiarCamposLogin() {
		this.vistaLogin.gettFUsuario().setText("");
		this.vistaLogin.gettFContrasena().setText("");
	}

	/**
	 * Proceso para registrar un nuevo usuario
	 */
	private void registrarUsuario() {

		int nivelPorDefecto = 0;

		Usuario nuevoUsuario = new Usuario();

		// Creo variables para guardar
		String nombre = this.vistaRegistro.gettFRegistroNombre().getText();
		String apellido = this.vistaRegistro.gettFRegistroApellido().getText();
		String email = this.vistaRegistro.gettFRegistroEmail().getText();
		String user = this.vistaRegistro.gettFRegistroUser().getText();

		String password = new String(this.vistaRegistro.getpFRegistroPassword().getPassword());

		IdiomaPreferido idioma = (IdiomaPreferido) this.vistaRegistro.getcBRegistroIdioma().getSelectedItem();
		TemaPreferido tema = (TemaPreferido) this.vistaRegistro.getcBRegistroTema().getSelectedItem();

		Date fechaNacimiento = this.vistaRegistro.getFechaNacimientoCalendar().getDate();

		// Compruebo que los campos no están vacíos
		if (camposRegistroVacios(nombre, email, user, password, fechaNacimiento)) {
			mostrarErrorDialog(EMPTY_FIELDS_MESSAGE);
			return;
		}

		// COMPROBACIÓN DE FORMATO EMAIL
		if (!esEmailValido(email)) {
			mostrarErrorDialog(INVALID_EMAIL_FORMAT_MESSAGE);
			return;
		}

		// COMPROBACIÓN DE USUARIO EXISTENTE
		if (nuevoUsuario.obtenerUsuario(user) != null) {
			mostrarErrorDialog(USER_EXISTS_MESSAGE);
			return;
		}

		// Llena los datos del usuario con las variables y lo registra
		llenarUsuario(nuevoUsuario, nombre, apellido, email, user, password, idioma, tema, fechaNacimiento,
				nivelPorDefecto);
		limpiarCamposRegistro();
		nuevoUsuario.crearUsuario();

		mostrarInfoDialog("Usuario registrado correctamente.");
		visualizarPanel(Principal.enumAcciones.PANEL_LOGIN);

	}

	/**
	 * Comprueba si los datos del registro están vacíos
	 *
	 * @param nombre          texto del textField del nombre
	 * @param email           texto del textField del email
	 * @param user            texto del textField del usuario
	 * @param password        texto del textField de la contraseña
	 * @param fechaNacimiento fecha del textField de fecha de nacimiento
	 * @return devuelve un booleano de si están vacíos o no
	 */
	private boolean camposRegistroVacios(String nombre, String email, String user, String password,
			Date fechaNacimiento) {
		return nombre.isEmpty() || email.isEmpty() || user.isEmpty() || password.isEmpty() || fechaNacimiento == null;
	}

	/**
	 * Comprueba si el formato del email es correcto
	 *
	 * @param email texto del textField del email
	 * @return devuelve un booleano para indicar si es correcto o no
	 */
	private boolean esEmailValido(String email) {
		return email.contains("@") && email.contains(".");
	}

	/**
	 * Muestra un cuadro de diálogo de error
	 *
	 * @param mensaje mensaje a mostrar
	 */
	private void mostrarErrorDialog(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Muestra un cuadro de diálogo de información
	 *
	 * @param mensaje mensaje a mostrar
	 */
	private void mostrarInfoDialog(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, INFO_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Llena un nuevo usuario con los datos obtenidos de la vista
	 *
	 * @param nuevoUsuario    nuevo usuario a llenar
	 * @param nombre          texto del textField del nombre
	 * @param apellido        texto del textField del apellido
	 * @param email           texto del textField del email
	 * @param user            texto del textField del usuario
	 * @param password        texto del textField de la contraseña
	 * @param idioma          idioma preferido del ComboBox
	 * @param tema            tema preferido del ComboBox
	 * @param fechaNacimiento fecha de nacimiento
	 */
	private void llenarUsuario(Usuario nuevoUsuario, String nombre, String apellido, String email, String user,
			String password, IdiomaPreferido idioma, TemaPreferido tema, Date fechaNacimiento, int nivelUsuario) {
		nuevoUsuario.setNombre(nombre);
		nuevoUsuario.setApellido(apellido);
		nuevoUsuario.setEmail(email);
		nuevoUsuario.setUser(user);
		nuevoUsuario.setPassword(password);
		nuevoUsuario.setIdiomaPreferido(idioma);
		nuevoUsuario.setTemaPreferido(tema);
		nuevoUsuario.setFechaNacimiento(fechaNacimiento);
		nuevoUsuario.setNivelUsuario(nivelUsuario);

	}

	// Método para obtener ejercicios
	private void obtenerEjercicios() {
		Workout selectedWorkout = vistaWorkouts.getWorkoutList().getSelectedValue();

		if (selectedWorkout == null) {
			vistaWorkouts.getEjersListModel().clear();
			vistaWorkouts.getBtnStartWorkout().setEnabled(false);
			return;
		}

		String workoutId = selectedWorkout.getId();
		vistaWorkouts.getEjersListModel().clear();

		Ejercicio ejercicioModel = new Ejercicio();
		ArrayList<Ejercicio> listaEjercicios = ejercicioModel.obtenerEjercicios(workoutId);

		// Bucle para añadir todos los ejercicios
		for (Ejercicio ejercicio : listaEjercicios) {
			vistaWorkouts.getEjersListModel().addElement(ejercicio);
		}

		vistaWorkouts.getBtnStartWorkout().setEnabled(true);
	}

	//SACAR POR ID****************************************************************************************
	// Método para mostrar el ejercicio seleccionado
	private void mostrarEjercicioSeleccionado() {

		Workout selectedWorkout = this.vistaWorkouts.getWorkoutList().getSelectedValue();
		if (selectedWorkout == null)
			return;
	
		String nombreWorkout = selectedWorkout.getNombre();
		this.vistaEjercicios.getLblWorkout().setText(nombreWorkout);

		// Muestra el primer ejercicio de la lista de ejercicios del workout
		// seleccionado

		if (vistaWorkouts.getEjersListModel().isEmpty())
			return;
		Ejercicio primerEjercicio = new Ejercicio();
		ArrayList<Ejercicio> ejercicios = primerEjercicio.obtenerEjercicios(selectedWorkout.getId());
		primerEjercicio = ejercicios.getFirst();
		this.vistaEjercicios.getLblEjercicio().setText(primerEjercicio.getNombre());
		this.vistaEjercicios.getTxtAreaDescripcion().setText(primerEjercicio.getDescripcion());
		this.vistaPrincipal.colocarImg(vistaEjercicios.getLblImgEjer(), primerEjercicio.getFoto(), vistaEjercicios);

	}

	/**
	 * Carga los workouts del usuario y los muestra en la lista del panelWorkouts
	 * 
	 * @param usuario usuario que tiene la sesión iniciada
	 * @param accion  panel al que se le atribuye la acción
	 */
	private void cargarWorkouts(Usuario usuario, Principal.enumAcciones accion) {
		Workout workouts = new Workout();
		ArrayList<Workout> listaWorkouts = workouts.obtenerWorkouts((long) usuario.getNivelUsuario());
		vistaWorkouts.getWorkoutListModel().clear();

		// Bucle para añadir cada workout a la JList
		for (Workout workout : listaWorkouts) {
			String url = workout.getVideoUrl();
			String descripcion = workout.getDescripcion();
			vistaWorkouts.addWorkout(workout, url, descripcion);
		}
	}

	/**
	 * Obtiene todos los ejercicios del workout seleccionado y los muesta en la
	 * JList de ejercicios en el panelWorkouts
	 */
	private void cargarInfoWorkout() {
		Workout workoutSeleccionado = vistaWorkouts.getWorkoutList().getSelectedValue();

		if (workoutSeleccionado == null) {
			vistaWorkouts.getBtnDescripcion().setEnabled(false);
			vistaWorkouts.getBtnVideo().setEnabled(false);
			// en vez de hacer esto igual se puede vaciar el objeto
			return;
		}

		String urlWorkout = vistaWorkouts.getWorkoutUrl(workoutSeleccionado.getVideoUrl());
		String descripcionWorkout = vistaWorkouts.getWorkoutDescripcion(workoutSeleccionado.getDescripcion());

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
				resultado.replace(i, i, "\n");
			}
		}

		String a = resultado.toString();
		vistaWorkouts.getBtnDescripcion().addActionListener(event -> {
			JOptionPane.showMessageDialog(null, a, "Descripción", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	/**
	 * Vacía los campos del formulario del registro
	 */
	private void limpiarCamposRegistro() {
		this.vistaRegistro.gettFRegistroNombre().setText("");
		this.vistaRegistro.gettFRegistroApellido().setText("");
		this.vistaRegistro.gettFRegistroEmail().setText("");
		this.vistaRegistro.gettFRegistroUser().setText("");
		this.vistaRegistro.getpFRegistroPassword().setText("");
		this.vistaRegistro.getFechaNacimientoCalendar().setDate(new Date());
	}
}
