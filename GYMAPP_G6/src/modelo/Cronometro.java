package modelo;

import javax.swing.JLabel;

public class Cronometro extends Thread {
	private int minutos = 0;
	private int segundos = 0;
	private boolean started = false;
	private boolean finished = false;

	private boolean running = false;
	private JLabel lblCronometro;

	public Cronometro(JLabel lblCronometro) {
		this.lblCronometro = lblCronometro;
	}

	@Override
	public void run() {
		while (started) {
			if (running) {
				segundos++;

				if (segundos == 60) {
					minutos++;
					segundos = 0;
				}
				if (minutos == 60) {
					minutos = 0;
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
		if (!started) {
			started = true;
			running = true;
			start();
		}
	}

	public void reset() {
		minutos = 0;
		segundos = 0;
	}

	public void pause() {
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

	public void finishProcess() {
		started = false;
	}
}
