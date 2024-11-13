package vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

import modelo.Ejercicio;
import modelo.Serie;
import modelo.Workout;

public class PanelEjercicios extends JPanel {

	private static final long serialVersionUID = 1L;

	private Workout selectedWorkout;
	private JTextArea txtAreaDescripcion;
	private JLabel lblWorkout, lblCTiempoE, lblCWorkout, lblCDescanso, lblNombreSerie, lblImgEjer, lblCSerie,
			lblCProximaSerie, lblLaProximaSerie, lblEjercicio, lblRepeticiones, lblTiempoDescansoTexto;

	private JButton btnStart, btnPause, btnNext, btnReturn, btnExit; // Botón para iniciar y pausar

	private ArrayList<JLabel> grupoCronometros;
	private JLabel lblDescripcion;

	/**
	 * Create the panel.
	 */
	public PanelEjercicios() {

		setBounds(0, 0, 880, 560);
		setLayout(null);
		setBackground(new Color(141, 204, 235)); // Fondo claro en tonos azules

		// Cronómetro principal
		lblCWorkout = new JLabel("Cron principal");
		lblCWorkout.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblCWorkout.setBounds(20, 234, 189, 60);
		lblCWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblCWorkout.setForeground(new Color(30, 121, 166)); // Azul
		add(lblCWorkout);

		lblDescripcion = new JLabel("Ejercicio - Descripción");
		lblDescripcion.setBounds(280, 90, 244, 30);
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcion.setForeground(new Color(30, 121, 166));
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblDescripcion);

		// Etiqueta de título
		lblWorkout = new JLabel("Workout");
		lblWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkout.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblWorkout.setBounds(505, 11, 365, 60);
		add(lblWorkout);

		lblCTiempoE = new JLabel("tiempo ejercicio");
		lblCTiempoE.setBounds(56, 387, 69, 14);
		lblCTiempoE.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblCTiempoE);

		lblCDescanso = new JLabel("tiempo descanso");
		lblCDescanso.setBounds(174, 357, 69, 14);
		lblCDescanso.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblCDescanso);

		btnExit = new JButton("Salir");
		btnExit.setForeground(Color.WHITE);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExit.setFocusPainted(false);
		btnExit.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnExit.setBackground(new Color(10, 75, 128));
		btnExit.setBounds(750, 480, 120, 50);
		add(btnExit);

		// Botón de iniciar/pausar
		btnStart = new JButton("Iniciar");
		btnStart.setBounds(389, 483, 120, 50);
		btnStart.setBackground(new Color(10, 75, 128)); // Azul
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnStart);

		btnPause = new JButton("Pausar");
		btnPause.setForeground(Color.WHITE);
		btnPause.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPause.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnPause.setBackground(new Color(255, 94, 94));
		btnPause.setBounds(389, 483, 120, 50);
		btnPause.setVisible(false);
		add(btnPause);

		btnNext = new JButton("Siguiente ejercicio");
		btnNext.setBounds(450, 413, 120, 50);
		btnNext.setBackground(new Color(10, 75, 128)); // Azul
		btnNext.setForeground(Color.WHITE);
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNext.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnNext);

		// Botón de regresar
		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(20, 490, 120, 40);
		btnReturn.setBackground(new Color(10, 75, 128)); // Azul
		btnReturn.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(btnReturn);

		lblNombreSerie = new JLabel("nombre serie");
		lblNombreSerie.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreSerie.setBounds(280, 289, 259, 30);
		add(lblNombreSerie);

		// Imagen del ejercicio
		lblImgEjer = new JLabel("");
		lblImgEjer.setBounds(573, 211, 259, 237);
		lblImgEjer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Borde para la imagen
		add(lblImgEjer);

		lblCSerie = new JLabel("tiempo serie");
		lblCSerie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCSerie.setBounds(46, 305, 94, 14);

		lblLaProximaSerie = new JLabel("La próxima serie comenzará en");
		lblLaProximaSerie.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLaProximaSerie.setBounds(20, 131, 250, 35);
		add(lblLaProximaSerie);

		lblCProximaSerie = new JLabel("00:05");
		lblCProximaSerie.setBounds(79, 164, 100, 35);
		lblCProximaSerie.setHorizontalAlignment(SwingConstants.CENTER);
		lblCProximaSerie.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblCProximaSerie);

		// Cuenta regresiva para series
		/*
		 * lblCountdown = new JLabel("Cuenta regresiva: 5");
		 * lblCountdown.setHorizontalAlignment(SwingConstants.CENTER);
		 * lblCountdown.setFont(new Font("Tahoma", Font.BOLD, 16));
		 * lblCountdown.setBounds(280, 338, 300, 30); lblCountdown.setForeground(new
		 * Color(70, 130, 180)); // Azul add(lblCountdown);
		 */

		txtAreaDescripcion = new JTextArea();
		txtAreaDescripcion.setWrapStyleWord(true);
		txtAreaDescripcion.setLineWrap(true);
		txtAreaDescripcion.setEditable(false);
		txtAreaDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtAreaDescripcion.setBorder(new LineBorder(new Color(30, 121, 166), 2));
		txtAreaDescripcion.setBounds(280, 131, 244, 159);
		add(txtAreaDescripcion);

		lblTiempoDescansoTexto = new JLabel("Tiempo de descanso:");
		lblTiempoDescansoTexto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTiempoDescansoTexto.setBounds(20, 359, 144, 13);
		add(lblTiempoDescansoTexto);

		// Etiqueta para nombre del ejercicio
		lblEjercicio = new JLabel("");
		lblEjercicio.setBounds(195, 26, 300, 30);
		lblEjercicio.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblEjercicio);
		
		lblRepeticiones = new JLabel("Repeticiones :");
		lblRepeticiones.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepeticiones.setForeground(new Color(30, 121, 166));
		lblRepeticiones.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRepeticiones.setBounds(281, 323, 259, 30);
		add(lblRepeticiones);

	}
	
	public void cambiarVentana(Ejercicio ejercicio) {
		btnStart.setVisible(true);
		btnPause.setVisible(false);
		btnNext.setVisible(false);
		lblLaProximaSerie.setVisible(true);
		lblCProximaSerie.setVisible(true);
		

		grupoCronometros = new ArrayList<JLabel>();
		lblCWorkout.setText("00:00");
		lblCTiempoE.setText("00:00");
		lblCProximaSerie.setText("00:05");
		txtAreaDescripcion.setText(selectedWorkout.getDescripcion());
		lblTiempoDescansoTexto.setText("Tiempo de descanso " +  ejercicio.getTiempoDescanso() + " seg" );
		
		lblDescripcion.setText(ejercicio.getNombre() + "- Descripción:");
		lblWorkout.setText("Workout: " + selectedWorkout.getNombre());

		lblCDescanso.setText(
				String.format("%02d:%02d", ((int) selectedWorkout.getListaEjercicios().get(0).getTiempoDescanso() / 60), // min
						((int) selectedWorkout.getListaEjercicios().get(0).getTiempoDescanso() % 60)));// seg

		for (Serie serie : ejercicio.getListaSeries()) {
			lblCSerie.setText((String.format("%02d:%02d", ((int) serie.getTiempo() / 60), // min
					((int) serie.getTiempo() % 60))));
			add(lblCSerie);

			grupoCronometros.add(lblCSerie);
		}
	}

	public Workout getSelectedWorkout() {
		return selectedWorkout;
	}

	public void setSelectedWorkout(Workout selectedWorkout) {
		this.selectedWorkout = selectedWorkout;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTextArea getTxtAreaDescripcion() {
		return txtAreaDescripcion;
	}

	public JLabel getLblWorkout() {
		return lblWorkout;
	}

	public JLabel getLblCTiempoE() {
		return lblCTiempoE;
	}

	public JLabel getLblCWorkout() {
		return lblCWorkout;
	}

	public JLabel getLblCDescanso() {
		return lblCDescanso;
	}

	public JLabel getLblNombreSerie() {
		return lblNombreSerie;
	}

	public JLabel getLblImgEjer() {
		return lblImgEjer;
	}

	public JLabel getLblCSerie() {
		return lblCSerie;
	}

	public JLabel getLblCProximaSerie() {
		return lblCProximaSerie;
	}

	public JLabel getLblLaProximaSerie() {
		return lblLaProximaSerie;
	}

	public JLabel getLblEjercicio() {
		return lblEjercicio;
	}

	public JLabel getLblRepeticiones() {
		return lblRepeticiones;
	}

	public JLabel getLblTiempoDescansoTexto() {
		return lblTiempoDescansoTexto;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public JButton getBtnPause() {
		return btnPause;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public JButton getBtnExit() {
		return btnExit;
	}

	public ArrayList<JLabel> getGrupoCronometros() {
		return grupoCronometros;
	}

	public JLabel getLblDescripcion() {
		return lblDescripcion;
	}


	
	
	

}