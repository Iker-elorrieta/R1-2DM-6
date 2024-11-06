package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class PanelEjercicios extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnReturn, btnStart, btnPause; // Botón para iniciar y pausar
	private JLabel lblWorkout, lblImgEjer, lblEjercicio, lblMainTimer, lblSeries, lblRepeticiones, lblCountdown; // Cuenta regresiva
	private boolean isPaused = false;
	private boolean isRunning = false;																								// de series
	private JTextArea txtAreaDescripcion;

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
		lblWorkout.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblWorkout.setBounds(505, 11, 365, 60);
		add(lblWorkout);

		// Botón de regresar
		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(20, 490, 120, 40);
		btnReturn.setBackground(new Color(10, 75, 128)); // Azul
		btnReturn.setBorder(new LineBorder(new Color(255, 255, 255), 2));
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
		lblMainTimer = new JLabel("");
		lblMainTimer.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblMainTimer.setBounds(20, 234, 189, 60);
		lblMainTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainTimer.setForeground(new Color(30, 121, 166)); // Azul
		add(lblMainTimer);

		// Botón de iniciar/pausar
		btnStart = new JButton("Iniciar");
		btnStart.setBounds(389, 483, 120, 50);
		btnStart.setBackground(new Color(10, 75, 128)); // Azul
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setFocusPainted(false);
		btnStart.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnStart);

		// Información de series
		lblSeries = new JLabel("Series: 1 / 3 ");
		lblSeries.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeries.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSeries.setBounds(280, 262, 259, 30);
		lblSeries.setForeground(new Color(30, 121, 166)); // Azul
		add(lblSeries);

		// Cuenta regresiva para series
		lblCountdown = new JLabel("Cuenta regresiva: 5");
		lblCountdown.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountdown.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCountdown.setBounds(280, 338, 300, 30);
		lblCountdown.setForeground(new Color(70, 130, 180)); // Azul
		add(lblCountdown);

		txtAreaDescripcion = new JTextArea();
		txtAreaDescripcion.setWrapStyleWord(true);
		txtAreaDescripcion.setLineWrap(true);
		txtAreaDescripcion.setEditable(false);
		txtAreaDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtAreaDescripcion.setBorder(new LineBorder(new Color(30, 121, 166), 2));
		txtAreaDescripcion.setBounds(280, 91, 244, 159);
		add(txtAreaDescripcion);
		
		lblRepeticiones = new JLabel("Repeticiones :");
		lblRepeticiones.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepeticiones.setForeground(new Color(30, 121, 166));
		lblRepeticiones.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRepeticiones.setBounds(280, 297, 259, 30);
		add(lblRepeticiones);
		
		btnPause = new JButton("Pausar");
		btnPause.setForeground(Color.WHITE);
		btnPause.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPause.setFocusPainted(false);
		btnPause.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnPause.setBackground(new Color(10, 75, 128));
		btnPause.setBounds(389, 483, 120, 50);
		btnPause.setVisible(false);
		add(btnPause);
	}


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

	public JLabel getLblSeries() {
		return lblSeries;
	}

	public void setLblSeries(JLabel lblSeries) {
		this.lblSeries = lblSeries;
	}

	
	public JButton getBtnStart() {
		return btnStart;
	}

	public void setBtnStart(JButton btnStart) {
		this.btnStart = btnStart;
	}
	

	public JButton getBtnPause() {
		return btnPause;
	}


	public void setBtnPause(JButton btnPause) {
		this.btnPause = btnPause;
	}


	public JLabel getLblRepeticiones() {
		return lblRepeticiones;
	}

	public void setLblRepeticiones(JLabel lblRepeticiones) {
		this.lblRepeticiones = lblRepeticiones;
	}

	public JLabel getLblCountdown() {
		return lblCountdown;
	}

	public void setLblCountdown(JLabel lblCountdown) {
		this.lblCountdown = lblCountdown;
	}


	public JLabel getLblMainTimer() {
		return lblMainTimer;
	}


	public void setLblMainTimer(JLabel lblMainTimer) {
		this.lblMainTimer = lblMainTimer;
	}


	public boolean isPaused() {
		return isPaused;
	}


	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}


	public boolean isRunning() {
		return isRunning;
	}


	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	
}