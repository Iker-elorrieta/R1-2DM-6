package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class PanelEjercicios extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnReturn, btnStartPause; // Botón para iniciar y pausar
	private JLabel lblWorkout, lblImgEjer, lblEjercicio, lblMainTimer, lblInfoSeries, lblCountdown; // Cuenta regresiva
																									// de series
	private JTextArea txtAreaDescripcion;

	// Variables de tiempo
	// private int elapsedTime = 0; // Tiempo transcurrido
	// private int countdownTime = 5; // Tiempo de cuenta regresiva para las series

	/**
	 * Create the panel.
	 */
	public PanelEjercicios() {

		setBounds(0, 0, 880, 560);
		setLayout(null);
		setBackground(new Color(141, 204, 235)); // Fondo claro en tonos azules

		// Etiqueta de título
		lblWorkout = new JLabel("");
		lblWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkout.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkout.setBounds(577, 21, 293, 83);
		add(lblWorkout);

		// Botón de regresar
		btnReturn = new JButton("Atrás");
		btnReturn.setVisible(false);
		btnReturn.setBounds(20, 490, 120, 40);
		btnReturn.setBackground(new Color(70, 130, 180)); // Azul
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(btnReturn);

		// Imagen del ejercicio
		lblImgEjer = new JLabel("");
		lblImgEjer.setBounds(572, 206, 259, 237);
		lblImgEjer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Borde para la imagen
		add(lblImgEjer);

		// Etiqueta para nombre del ejercicio
		lblEjercicio = new JLabel("");
		lblEjercicio.setBounds(195, 26, 300, 30);
		lblEjercicio.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblEjercicio);

		// Cronómetro principal
		lblMainTimer = new JLabel("00:00");
		lblMainTimer.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblMainTimer.setBounds(20, 234, 189, 60);
		lblMainTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainTimer.setForeground(new Color(30, 121, 166)); // Azul
		add(lblMainTimer);

		// Botón de iniciar/pausar
		btnStartPause = new JButton("Iniciar");
		btnStartPause.setVisible(false);
		btnStartPause.setBounds(389, 483, 120, 50);
		btnStartPause.setBackground(new Color(10, 75, 128)); // Azul
		btnStartPause.setForeground(Color.WHITE);
		btnStartPause.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStartPause.setFocusPainted(false);
		btnStartPause.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		add(btnStartPause);

		// Información de series
		lblInfoSeries = new JLabel("Series: 1 / 3 (Repeticiones: )");
		lblInfoSeries.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoSeries.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblInfoSeries.setBounds(280, 262, 259, 30);
		lblInfoSeries.setForeground(new Color(30, 121, 166)); // Azul
		add(lblInfoSeries);

		// Cuenta regresiva para series
		lblCountdown = new JLabel("Cuenta regresiva: 5");
		lblCountdown.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountdown.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCountdown.setBounds(280, 303, 300, 30);
		lblCountdown.setForeground(new Color(70, 130, 180)); // Azul
		lblCountdown.setVisible(false); // Ocultar inicialmente
		add(lblCountdown);

		txtAreaDescripcion = new JTextArea();
		txtAreaDescripcion.setWrapStyleWord(true);
		txtAreaDescripcion.setLineWrap(true);
		txtAreaDescripcion.setEditable(false);
		txtAreaDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtAreaDescripcion.setBorder(new LineBorder(new Color(30, 121, 166), 2));
		txtAreaDescripcion.setBounds(280, 91, 244, 159);
		add(txtAreaDescripcion);
	}

	// Método para formatear el tiempo
	/*
	 * private String formatTime(int seconds) { int minutes = seconds / 60; seconds
	 * = seconds % 60; return String.format("%02d:%02d", minutes, seconds); }
	 */

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}

	public JLabel getLblWorkout() {
		return lblWorkout;
	}

	public void setLblWorkout(JLabel lblWorkout) {
		this.lblWorkout = lblWorkout;
	}

	public JLabel getLblImgEjer() {
		return lblImgEjer;
	}

	public void setLblImgEjer(JLabel lblImgEjer) {
		this.lblImgEjer = lblImgEjer;
	}

	public JLabel getLblEjercicio() {
		return lblEjercicio;
	}

	public void setLblEjercicio(JLabel lblEjercicio) {
		this.lblEjercicio = lblEjercicio;
	}

	public JTextArea getTxtAreaDescripcion() {
		return txtAreaDescripcion;
	}

	public void setTxtAreaDescripcion(JTextArea txtAreaDescripcion) {
		this.txtAreaDescripcion = txtAreaDescripcion;
	}

}