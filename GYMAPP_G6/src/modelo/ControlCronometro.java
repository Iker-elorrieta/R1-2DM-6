package modelo;

import java.util.Date;

import javax.swing.JOptionPane;

import controlador.Controlador;
import vista.PanelEjercicios;
import vista.Principal;

public class ControlCronometro extends Thread {
	private PanelEjercicios vistaEjercicio;
	private Workout selectedWorkout;
	private Cronometro mainTimer;
	private CronometroRegresivo descansoTimer;
	private Cronometro ejercicioTimer;
	private CronometroRegresivo serieTimer;
	private int contEjercicio = 0;
	private int contSerie = 0;
	boolean nextClicked = false;
	boolean finish = false;
	private Usuario usuarioLogeado;
	private Controlador controlador;


	public ControlCronometro(PanelEjercicios vistaEjercicio, Workout selectedWorkout, Cronometro mainTimer,
			CronometroRegresivo descansoTimer, Cronometro ejercicioTimer, CronometroRegresivo serieTimer,
			Usuario usuarioLogeado, Controlador controlador) {
		this.vistaEjercicio = vistaEjercicio;
		this.selectedWorkout = selectedWorkout;
		this.mainTimer = mainTimer;
		this.descansoTimer = descansoTimer;
		this.ejercicioTimer = ejercicioTimer;
		this.serieTimer = serieTimer;
		this.usuarioLogeado = usuarioLogeado;
		this.controlador = controlador;
	}

	@Override
	public void run() {
		Ejercicio ejercicioActual = selectedWorkout.getListaEjercicios().get(contEjercicio);
		mainTimer.iniciar();
		ejercicioTimer.iniciar();
		serieTimer.iniciar();
		while (contEjercicio < selectedWorkout.getListaEjercicios().size() && !finish) {
			ejercicioActual = selectedWorkout.getListaEjercicios().get(contEjercicio);

			while (contSerie < ejercicioActual.getListaSeries().size() && !finish) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Si la serie ha finalizado
				if (!serieTimer.isAlive()) {
					if (!finish) {
						contSerie++;
						// Tras finalizar la ultima serie por algun motivo entra en el bucle para
						// controlar obligamos a salir
						if (contSerie >= ejercicioActual.getListaSeries().size() || finish) {
							break;
						}
						startDescanso();

						// Los campos que varian seran el descanso y la serie
						descansoTimer = new CronometroRegresivo(vistaEjercicio.getLblDescanso(),
								selectedWorkout.getListaEjercicios().get(contEjercicio).getTiempoDescanso());
						// Actulizamos el descanso en panel
						vistaEjercicio.getLblDescanso().setText(String.format("%02d:%02d",
								((int) selectedWorkout.getListaEjercicios().get(contEjercicio).getTiempoDescanso() / 60), // min
								((int) selectedWorkout.getListaEjercicios().get(contEjercicio).getTiempoDescanso() % 60)));

						// Inicializamos la siguiente serie pero la detenomos hasta que lo active
						serieTimer = new CronometroRegresivo(vistaEjercicio.getGrupoCronometros().get(contSerie),
								ejercicioActual.getListaSeries().get(contSerie).getTiempo());
						serieTimer.iniciar();
						serieTimer.pause();
						vistaEjercicio.getBtnStart().setVisible(true);
					}
				}
			}
			if (!finish) {
				// No se por que pero la ultima serie no se ejecuta el descanso como deberia
				startDescanso();
				// Reiniciar variables para el siguiente ejercicio
				contSerie = -1; // por algun motivo al salir del bucle y vuelve a entrar automaticametnte accede
									// a la linea 54 y accede per
				contEjercicio++;
				vistaEjercicio.getBtnStart().setVisible(false);
				vistaEjercicio.getBtnPause().setVisible(false);

				if (contEjercicio < selectedWorkout.getListaEjercicios().size() && !finish) {
					vistaEjercicio.getBtnNext().setVisible(true);

					while (!nextClicked) {
						// No hara nada hasta que pulse siguiente
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					next();
					nextClicked = false;
				}
			}
		}
		if (!finish) {
			finalizarProceso();
			controlador.visualizarPanel(Principal.enumAcciones.PANEL_WORKOUTS);;
		}
	}

	public void finalizarProceso() {
		finish = true;
		String nombreEjercicio = "";
		for (int i = 0; i < contEjercicio; i++) {
			nombreEjercicio += selectedWorkout.getListaEjercicios().get(i).getNombre() + "\n";
		}
		String tiempoRealizacion = vistaEjercicio.getLblWorkout().getText().toString();
		double porcentajeRealizacion = (100 * contEjercicio) / selectedWorkout.getListaEjercicios().size();
		String texto = String.format("Tiempo total del ejercicio %s ejercicio realizado %s Porcentaje %s  bien hecho",
				tiempoRealizacion, nombreEjercicio, porcentajeRealizacion);

		Historial historial = new Historial(selectedWorkout, new Date(), porcentajeRealizacion, tiempoRealizacion);
		historial.mIngresarHistorico(usuarioLogeado.getEmail(), selectedWorkout);
		usuarioLogeado.insertarNuevoItemHistorial(historial);
		if (mainTimer != null) {
			mainTimer.finishProcess();
			descansoTimer.finishProcess();
			ejercicioTimer.finishProcess();
			serieTimer.finishProcess();
		}
		JOptionPane.showMessageDialog(null, texto);
		// El cambio de ventana

	}

	public void pausar() {
		ejercicioTimer.pause();
		serieTimer.pause();
		mainTimer.pause();
		vistaEjercicio.getBtnStart().setVisible(true);
		vistaEjercicio.getBtnPause().setVisible(false);
	}

	public void play() {
		// La primera ejecucion seria desencadenar todo el proceso
		if (!mainTimer.isAlive()) {
			this.start();
		}
		// Activar la serie
		if (serieTimer != null) {
			serieTimer.activate();
			ejercicioTimer.activate();
			mainTimer.activate();

		}
		vistaEjercicio.getBtnStart().setVisible(false);
		vistaEjercicio.getBtnPause().setVisible(true);
	}

	private void startDescanso() {
		descansoTimer.iniciar();
		vistaEjercicio.getBtnStart().setVisible(false);
		vistaEjercicio.getBtnPause().setVisible(false);
		// Esperamos hasta que el proceso termine
		while (descansoTimer.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	// Al pulsar boton siguiente
	public void next() {
		nextClicked = true;
		ejercicioTimer.reset();
		vistaEjercicio.cambiarVentana(selectedWorkout.getListaEjercicios().get(contEjercicio));
		vistaEjercicio.getBtnStart().setVisible(true);
		vistaEjercicio.getBtnNext().setVisible(false);

	}
}
