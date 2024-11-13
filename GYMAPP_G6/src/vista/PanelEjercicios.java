package vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

import controlador.Controlador;
import modelo.Ejercicio;
import modelo.Serie;
import modelo.Workout;

public class PanelEjercicios extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnReturn, btnStart, btnPause, btnNext, btnExit; // Botón para iniciar y pausar
	private JLabel lblWorkout, lblImgEjer, lblEjercicio, lblTiempoEjer, lblMainTimer, lblSeries, lblRepeticiones, lblCountdown; 
	private boolean isPaused = false;
	private boolean isRunning = false; 
	private JTextArea txtAreaDescripcion;
	private JLabel lblSerieCount, lblDescanso; 
	private ArrayList<JLabel> grupoCronometros;
	private Workout selectedWorkout;
	
	JLabel lblCronNextSerie;
	JLabel lblNextSerie;

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
		lblImgEjer.setBounds(573, 211, 259, 237);
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
		btnPause.setBackground(new Color(255, 94, 94));
		btnPause.setBounds(389, 483, 120, 50);
		btnPause.setVisible(false);
		add(btnPause);
		
		btnNext = new JButton("siguiente");
		btnNext.setBounds(450, 413, 89, 23);
		add(btnNext);
		
		btnExit = new JButton("Salir");
		btnExit.setForeground(Color.WHITE);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExit.setFocusPainted(false);
		btnExit.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnExit.setBackground(new Color(10, 75, 128));
		btnExit.setBounds(750, 480, 120, 50);
		add(btnExit);
		
		lblSerieCount = new JLabel("000");
		lblSerieCount.setBounds(46, 305, 94, 14);
		add(lblSerieCount);
		
		lblDescanso = new JLabel("00");
		lblDescanso.setBounds(56, 330, 94, 14);
		add(lblDescanso);
		
		lblTiempoEjer = new JLabel("New label");
		lblTiempoEjer.setBounds(56, 387, 46, 14);
		add(lblTiempoEjer);
		
		lblCronNextSerie = new JLabel("Siguiente Serie");
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

	public JButton getBtnExit() {
		return btnExit;
	}

	public void setBtnExit(JButton btnExit) {
		this.btnExit = btnExit;
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

	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}

	public JLabel getLblSerieCount() {
		return lblSerieCount;
	}

	public void setLblSerieCount(JLabel lblSerieCount) {
		this.lblSerieCount = lblSerieCount;
	}

	public JLabel getLblDescanso() {
		return lblDescanso;
	}

	public void setLblDescanso(JLabel lblDescanso) {
		this.lblDescanso = lblDescanso;
	}
	
	public JLabel getLblCronSerie() {
		return lblCronNextSerie;
	}

	public void setLblCronSerie(JLabel lblCronNextSerie) {
		this.lblCronNextSerie = lblCronNextSerie;
	}

	public JLabel getLblNextSerie() {
		return lblNextSerie;
	}

	public void setLblNextSerie(JLabel lblNextSerie) {
		this.lblNextSerie = lblNextSerie;
	}

	public ArrayList<JLabel> getGrupoCronometros() {
		return grupoCronometros;
	}
	
	public Workout getSelectedWorkout() {
		return selectedWorkout;
	}

	public void setSelectedWorkout(Workout selectedWorkout) {
		this.selectedWorkout = selectedWorkout;
	}

	public void cambiarVentana(Ejercicio ejercicio) {
		btnStart.setVisible(true);
		btnNext.setVisible(false);
		btnPause.setVisible(false);

		
		int labelAltura = 24;
		int margenEntrePanelSeires = 15;
		
		grupoCronometros = new ArrayList<JLabel>();
		lblMainTimer.setText("00:00");
		lblTiempoEjer.setText("00:00");
		lblCronNextSerie.setText("00:05");
	
		
		txtAreaDescripcion.setText(ejercicio.getNombre() + " - Descripción");
		lblWorkout.setText("Workout " + selectedWorkout.getNombre());
		
		lblDescanso.setText(String.format("%02d:%02d", ((int) selectedWorkout.getListaEjercicios().get(0).getTiempoDescanso() / 60), // min
				((int) selectedWorkout.getListaEjercicios().get(0).getTiempoDescanso() % 60)));// seg
		
		for(Serie serie : ejercicio.getListaSeries()) {
			lblSeries = new JLabel(serie.getNombreSerie());
			lblSeries.setBounds(280, 262, 259, 30);
			this.add(lblSeries);
			
			lblImgEjer = new JLabel(ejercicio.getFoto());
			lblImgEjer.setBounds(573, 211, 259, 237);
			this.add(lblImgEjer);
			lblImgEjer.setIcon(new ImageIcon(new ImageIcon(ejercicio.getFoto()).getImage()
					.getScaledInstance(lblImgEjer.getWidth(), lblImgEjer.getHeight(), Image.SCALE_SMOOTH)));
			
			lblSerieCount = new JLabel("");
			lblSerieCount.setBounds(334, labelAltura, 100, 14);
			lblSerieCount.setText((String.format("%02d:%02d", ((int) serie.getTiempo() / 60), // min
					((int) serie.getTiempo() % 60))));// seg
			this.add(lblSerieCount);
			
			grupoCronometros.add(lblSerieCount);
			labelAltura += lblImgEjer.getHeight() + margenEntrePanelSeires;
		}
		lblEjercicio.setText(ejercicio.getNombre());
	    txtAreaDescripcion.setText(ejercicio.getDescripcion());
	    lblRepeticiones.setText("Repeticiones: " + ejercicio.getNumReps());
	}
	
		
		
		/*
			conjuntoDeCronometros.add(lblCSerie);
			labelAltura += lblImgSerie.getHeight() + margenEntrePanelSeires;

			if (labelAltura > panelMenu.getHeight() - 20) {
				panelMenu.setPreferredSize(new Dimension(400, labelAltura + margenEntrePanelSeires));
			}
			panelMenu.revalidate();
			panelMenu.repaint();
		}*/
	
}