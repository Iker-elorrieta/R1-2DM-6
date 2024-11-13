package modelo;

import javax.swing.JLabel;

public class CronometroRegresivo extends Thread {
	private int minutos = 0;
	private int segundos = 0;
	private boolean iniciado = false;
	private boolean running = false;
	private JLabel lblCronometro;
	private double tiempoEjerSegundos;
	boolean finished = false;

	public CronometroRegresivo(JLabel lblVisualizarCronometro, double tiempoEjer) {
		this.lblCronometro = lblVisualizarCronometro;
		this.tiempoEjerSegundos = tiempoEjer;
		this.minutos = ((int) tiempoEjer / 60);
		this.segundos = ((int) tiempoEjer % 60);
	}

	@Override
	public void run() {
		while (iniciado && tiempoEjerSegundos > 0) {
			if (running) {
				tiempoEjerSegundos--;

				if (segundos != 0) {
					segundos--;
				}
				if (segundos == 0 && minutos != 0) {
					minutos--;
					segundos = 60;
				}
				lblCronometro.setText(String.format("%02d:%02d", minutos, segundos));
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}

		finished = true;
	}

	public void iniciar() {
		if (!iniciado) {
			iniciado = true;
			running = true;
			start();
		}
	}

	public void detener() {
		running = false;
	}

	public void activate() {
		running = true;
	}

	public boolean finished() {
		return finished;
	}

	public boolean running() {
		return running;
	}

	public void terminarProceso() {
		iniciado = false;
	}

}
