package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.ControlCronometro;
import modelo.Cronometro;
import modelo.CronometroRegresivo;
import modelo.Ejercicio;
import modelo.Serie;
import modelo.Usuario;
import modelo.Usuario.IdiomaPreferido;
import modelo.Usuario.TemaPreferido;
import modelo.Workout;
import vista.PanelEjercicios;
import vista.Principal;

public class Controlador implements ActionListener {

	// Constantes para mensajes de error e información
	private static final String ERROR_TITLE = "Error";
	private static final String INFO_TITLE = "Información";
	private static final String WARNING_TITLE = "Warning";
	private static final String EMPTY_FIELDS_MESSAGE = "Todos los campos son obligatorios.";
	private static final String INVALID_EMAIL_FORMAT_MESSAGE = "Formato de email incorrecto.";
	private static final String USER_EXISTS_MESSAGE = "El usuario ya existe.";
	private static final String USER_NOT_FOUND_MESSAGE = "El usuario no existe.";
	private static final String LOGIN_SUCCESS_MESSAGE = "Inicio de sesión correcto. \nBienvenid@ ";
	private static final String INCORRECT_PASSWORD_MESSAGE = "Contraseña incorrecta.";
	private static final String OFFLINE_MESSAGE = "No dispone de conexión a internet.";
	private static final String COULDNT_FIND_BACKUPS_MESSAGE = "No se han encontrado backups.";
	private static final String CONNECTION_VERIFYING_ERROR_MESSAGE = "Error al verificar la conexión.";

	private Usuario usuarioLogeado;
	private ArrayList<Workout> listaWorkouts;
	private Workout selectedWorkout;
	private Cronometro mainTimer;
	private CronometroRegresivo cronDescanso;
	private Cronometro cronEjercicio;
	private CronometroRegresivo cronSerie;
	private ControlCronometro controlCronometro;
	
	private ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();
	
	private Ejercicio ejercicioActual;

	// Rutas
	private String backupsUsuario = "backups/usuario.dat";
	private String backupsWorkouts = "backups/workouts.dat";

	// Variables
	private boolean online = true;
	private static final String isConnectedCommand = "ping";
	private static final String isConnectedVerifyingWebSite = "google.com";

	private static final String javaCommand = "java";
	private static final String jarFlag = "-jar";
	private static final String backupJarFile = "backupgym.jar";

	// Referencias a las vistas
	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelRegistro vistaRegistro;
	private vista.PanelWorkouts vistaWorkouts;
	private vista.PanelEjercicios vistaEjercicios;
	private vista.PanelHistorico vistaHistorico;

	/**
	 * Constructor del controlador, inicializa las vistas y el controlador
	 *
	 * @param vistaPrincipal  vista del panel Principal
	 * @param vistaLogin      vista del panel Login
	 * @param vistaRegistro   vista del panel de Registro
	 * @param vistaWorkouts   vista del panel de Workouts
	 * @param vistaEjercicios vista del panel de Ejercicios
	 * @param vistaHistorico  vista del panel de Historico
	 */
	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelRegistro vistaRegistro,
			vista.PanelWorkouts vistaWorkouts, vista.PanelEjercicios vistaEjercicios,
			vista.PanelHistorico vistaHistorico) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaLogin = vistaLogin;
		this.vistaRegistro = vistaRegistro;
		this.vistaWorkouts = vistaWorkouts;
		this.vistaEjercicios = vistaEjercicios;
		this.vistaHistorico = vistaHistorico;

		this.inicializarControlador();
	}

	/**
	 * Acciones de los componentes de las vistas
	 */
	private void inicializarControlador() {
		accionesVistaLogin();
		accionesVistaRegistro();
		accionesVistaWorkouts();
		accionesVistaEjercicios();
		accionesVistaHistorico();
	}

	/**
	 * Acciones del panel del Login
	 */
	private void accionesVistaLogin() {

		this.vistaLogin.getBtnLogin().addActionListener(this);
		this.vistaLogin.getBtnLogin().setActionCommand(Principal.enumAcciones.INICIAR_SESION.toString());

		this.vistaLogin.getBtnSignUp().addActionListener(this);
		this.vistaLogin.getBtnSignUp().setActionCommand(Principal.enumAcciones.PANEL_REGISTRO.toString());
		this.vistaLogin.getBtnSignUp().setEnabled(false);

		verifyConnection();
		if (online)
			this.vistaLogin.getBtnSignUp().setEnabled(true);
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

		// Listerner para mostrar el panel de histórico
		this.vistaWorkouts.getBtnHistorico().addActionListener(this);
		this.vistaWorkouts.getBtnHistorico().setActionCommand(Principal.enumAcciones.PANEL_HISTORICO.toString());

	}

	/**
	 * Acciones del panel de Workouts
	 */
	private void accionesVistaEjercicios() {
		this.vistaEjercicios.getBtnReturn().addActionListener(this);
		this.vistaEjercicios.getBtnReturn().setActionCommand(Principal.enumAcciones.PANEL_WORKOUTS.toString());
		
		this.vistaEjercicios.getBtnExit().addActionListener(this);
		this.vistaEjercicios.getBtnExit().setActionCommand(Principal.enumAcciones.CERRAR_PROGRAMA.toString());

		this.vistaEjercicios.getBtnStart().addActionListener(this);
		this.vistaEjercicios.getBtnStart().setActionCommand(Principal.enumAcciones.PLAY.toString());

		this.vistaEjercicios.getBtnPause().addActionListener(this);
		this.vistaEjercicios.getBtnPause().setActionCommand(Principal.enumAcciones.PAUSE.toString());

		this.vistaEjercicios.getBtnNext().addActionListener(this);
		this.vistaEjercicios.getBtnNext().setActionCommand(Principal.enumAcciones.NEXT_EJERCICIO.toString());
	}

	/**
	 * Acciones del panel de Histórico
	 */
	private void accionesVistaHistorico() {
		this.vistaHistorico.getBtnReturn().addActionListener(this);
		this.vistaHistorico.getBtnReturn().setActionCommand(Principal.enumAcciones.PANEL_WORKOUTS.toString());
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
			if(selectedWorkout != null) {
				PanelEjercicios vistaEjercicios = this.vistaPrincipal.getPanelEjercicios();
				
				vistaEjercicios.setSelectedWorkout(selectedWorkout);
				vistaEjercicios.cambiarVentana(selectedWorkout.getListaEjercicios().get(0));
				
				mainTimer = new Cronometro(vistaEjercicios.getLblMainTimer());
				cronEjercicio = new Cronometro(vistaEjercicios.getLblCountdown());
				//cronSerie = new CronometroRegresivo(vistaEjercicios.getGrupoCronometros().get(0), selectedWorkout.getListaEjercicios().get(0).getListaSeries().get(0).getTiempo());
				cronDescanso = new CronometroRegresivo(vistaEjercicios.getLblDescanso(), selectedWorkout.getListaEjercicios().get(0).getTiempoDescanso());
				
				controlCronometro = new ControlCronometro(vistaEjercicios, selectedWorkout, mainTimer, cronDescanso, cronEjercicio, cronDescanso, usuarioLogeado, this);
				
				this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_EJERCICIOS);
				
			} else {
				JOptionPane.showMessageDialog(null, "Elige una opción");
			}
			break;
		case PANEL_HISTORICO:
			visualizarPanel(Principal.enumAcciones.PANEL_HISTORICO);
			break;
		case PLAY:
			controlCronometro.play();
			break;
		case PAUSE:
			controlCronometro.pausar();
			break;
		case NEXT_EJERCICIO:
			controlCronometro.next();
			//cambiarAlSiguienteEjercicio();
			break;
		case CERRAR_PROGRAMA:
			System.exit(0);
		default:
			break;
		}
	}

	/**
	 * Visualizar un panel
	 *
	 * @param panel variable para indicar el panel que se visualiza
	 */
	public void visualizarPanel(Principal.enumAcciones panel) {
		this.vistaPrincipal.visualizarPaneles(panel);
	}
	
	
	
	


	public boolean isConnected() throws InterruptedException, IOException {
		ProcessBuilder pb = new ProcessBuilder(isConnectedCommand, isConnectedVerifyingWebSite);
		Process process = pb.start();
		return process.waitFor() == 0;
	}

	public void verifyConnection() {
		try {
			ProcessBuilder pb = new ProcessBuilder(isConnectedCommand, isConnectedVerifyingWebSite);
			Process process = pb.start();
			online = process.waitFor() == 0;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			mostrarErrorDialog(CONNECTION_VERIFYING_ERROR_MESSAGE);
			System.exit(0);
		}

		if (online)
			return;

		mostrarWarningDialog(OFFLINE_MESSAGE);

		if (!backupsFilesExists()) {
			mostrarErrorDialog(COULDNT_FIND_BACKUPS_MESSAGE);
			return;
		}
	}

	public boolean backupsFilesExists() {
		File archivoUsuarios = new File(backupsUsuario);
		File archivoWorkouts = new File(backupsWorkouts);

		return archivoUsuarios.exists() && archivoUsuarios.length() > 0 && archivoWorkouts.exists()
				&& archivoWorkouts.length() > 0;
	}

	/**
	 * Inicio de sesión
	 */
	private void login() {
		String usuario = this.vistaLogin.gettFUsuario().getText();
		String password = new String(this.vistaLogin.gettFContrasena().getPassword());

		if (usuario.isEmpty() || password.isEmpty()) {
			mostrarErrorDialog(EMPTY_FIELDS_MESSAGE);
			return;
		}

		// Obtiene el usuario y comprueba la contraseña
		Usuario user = new Usuario().obtenerUsuario(usuario, online);

		if (user == null) {
			mostrarErrorDialog(USER_NOT_FOUND_MESSAGE);
			return;
		}

		if (!user.getPassword().equals(password)) {
			mostrarErrorDialog(INCORRECT_PASSWORD_MESSAGE);
			return;
		}

		mostrarInfoDialog(LOGIN_SUCCESS_MESSAGE + user.getUsuario());

		limpiarCamposLogin();

		cargarWorkouts(user, Principal.enumAcciones.PANEL_WORKOUTS);

		this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_WORKOUTS);
		this.vistaLogin.gettFUsuario().setText("");
		this.vistaLogin.gettFContrasena().setText("");

		if (!online)
			return;

		try {
			ProcessBuilder pb = new ProcessBuilder(javaCommand, jarFlag, backupJarFile);
			pb.inheritIO();
			pb.start();

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
		if (nuevoUsuario.obtenerUsuario(user, online) != null) {
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


	private boolean camposRegistroVacios(String nombre, String email, String user, String password,
			Date fechaNacimiento) {
		return nombre.isEmpty() || email.isEmpty() || user.isEmpty() || password.isEmpty() || fechaNacimiento == null;
	}

	
	private boolean esEmailValido(String email) {
		return email.contains("@") && email.contains(".");
	}

	private void mostrarErrorDialog(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}


	private void mostrarInfoDialog(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, INFO_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	private void mostrarWarningDialog(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
	}


	private void llenarUsuario(Usuario nuevoUsuario, String nombre, String apellido, String email, String user,
			String password, IdiomaPreferido idioma, TemaPreferido tema, Date fechaNacimiento, int nivelUsuario) {
		nuevoUsuario.setNombre(nombre);
		nuevoUsuario.setApellido(apellido);
		nuevoUsuario.setEmail(email);
		nuevoUsuario.setUsuario(user);
		nuevoUsuario.setPassword(password);
		nuevoUsuario.setIdiomaPreferido(idioma);
		nuevoUsuario.setTemaPreferido(tema);
		nuevoUsuario.setFechaNacimiento(fechaNacimiento);
		nuevoUsuario.setNivelUsuario(nivelUsuario);

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

	// Método para obtener ejercicios
		private void obtenerEjercicios() {
			selectedWorkout = vistaWorkouts.getWorkoutList().getSelectedValue();

			if (selectedWorkout == null) {
				vistaWorkouts.getEjersListModel().clear();
				vistaWorkouts.getBtnStartWorkout().setEnabled(false);
				return;
			}

			String workoutId = selectedWorkout.getId();
			vistaWorkouts.getEjersListModel().clear();

			Ejercicio ejercicioModel = new Ejercicio();
			ArrayList<Ejercicio> listaEjercicios = ejercicioModel.obtenerEjercicios(workoutId, online);
			

			// Bucle para añadir todos los ejercicios
			for (Ejercicio ejercicio : listaEjercicios) {
				vistaWorkouts.getEjersListModel().addElement(ejercicio);
			}

			vistaWorkouts.getBtnStartWorkout().setEnabled(true);
		}

		// Método para mostrar el ejercicio seleccionado
		/*private void mostrarEjercicioSeleccionado() {
		    Workout selectedWorkout = this.vistaWorkouts.getWorkoutList().getSelectedValue();
		    if (selectedWorkout == null) return;

		    this.vistaEjercicios.getLblWorkout().setText(selectedWorkout.getNombre());

		    if (vistaWorkouts.getEjersListModel().isEmpty()) return;

		    ArrayList<Ejercicio> ejercicios = new Ejercicio().obtenerEjercicios(selectedWorkout.getId(), online);
		    if (ejercicios.isEmpty()) return;

		    this.ejercicioActual = ejercicios.get(0); // Guardamos el primer ejercicio
		    this.listaEjercicios = ejercicios; // Guardamos la lista de ejercicios
		    ArrayList<Serie>listaSeries = new Serie().obtenerSeries(ejercicioActual.getId(), online);

		    this.vistaEjercicios.cambiarVentana(ejercicioActual);
		    
//		    this.vistaEjercicios.getLblEjercicio().setText(ejercicioActual.getNombre());
//		    this.vistaEjercicios.getTxtAreaDescripcion().setText(ejercicioActual.getDescripcion());
//		    this.vistaEjercicios.getLblRepeticiones().setText("Repeticiones: " + ejercicioActual.getNumReps());
		    this.vistaPrincipal.colocarImg(vistaEjercicios.getLblImgEjer(), ejercicioActual.getFoto(), vistaEjercicios);
		}*/
		
		/*private void cambiarAlSiguienteEjercicio() {
		    if (this.listaEjercicios == null || this.listaEjercicios.isEmpty()) return;

		    int indiceActual = listaEjercicios.indexOf(this.ejercicioActual);
		    int siguienteIndice = (indiceActual + 1) % listaEjercicios.size();

		    this.ejercicioActual = listaEjercicios.get(siguienteIndice);

		    this.vistaEjercicios.getLblEjercicio().setText(ejercicioActual.getNombre());
		    this.vistaEjercicios.getTxtAreaDescripcion().setText(ejercicioActual.getDescripcion());
		    this.vistaEjercicios.getLblRepeticiones().setText("Repeticiones: " + ejercicioActual.getNumReps());
		    this.vistaPrincipal.colocarImg(vistaEjercicios.getLblImgEjer(), ejercicioActual.getFoto(), vistaEjercicios);
		}*/


		private void cargarWorkouts(Usuario usuario, Principal.enumAcciones accion) {
			Workout workouts = new Workout();
			ArrayList<Workout> listaWorkouts = workouts.obtenerWorkouts((long) usuario.getNivelUsuario(), online);
			vistaWorkouts.getWorkoutListModel().clear();

			// Bucle para añadir cada workout a la JList
			for (Workout workout : listaWorkouts) {
				String url = workout.getVideoUrl();
				String descripcion = workout.getDescripcion();
				vistaWorkouts.addWorkout(workout, url, descripcion);
			}
		}


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
				if (i > 0 && i < resultado.length())
					resultado.replace(i, i, "\n");

			}

			String a = resultado.toString();
			vistaWorkouts.getBtnDescripcion().addActionListener(event -> {
				JOptionPane.showMessageDialog(null, a, "Descripción", JOptionPane.INFORMATION_MESSAGE);
			});
		}
		
		
		/*private void obtenerSeries() {
		    Workout selectedWorkout = vistaWorkouts.getWorkoutList().getSelectedValue();
		    if (selectedWorkout == null) return;

		    String workoutId = selectedWorkout.getId();
		    Ejercicio ejercicioModel = new Ejercicio();
		    Serie serieModel = new Serie();

		    ArrayList<Ejercicio> listaEjercicios = ejercicioModel.obtenerEjercicios(workoutId, online);

		    for (Ejercicio ejercicio : listaEjercicios) {
		        ArrayList<Serie> listaSeries = serieModel.obtenerSeries(ejercicio.getId(), online);
		        for (Serie serie : listaSeries) {
		            System.out.println(serie.getNombreSerie());
		        }
		    }
		}*/


}
