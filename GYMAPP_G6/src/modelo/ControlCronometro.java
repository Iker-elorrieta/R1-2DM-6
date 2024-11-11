package modelo;

import java.util.Date;

import javax.swing.JOptionPane;

import controlador.Controlador;
import vista.PanelEjercicios;
import vista.Principal;

public class ControlCronometro extends Thread {
	private PanelEjercicios pEjercicio;
	private Workout workoutSelect;
	private Cronometro cPrincipal;
	private CronometroRegresivo cDescanso;
	private Cronometro cEjercicio;
	private CronometroRegresivo cSerie;
	private int contadorEjercicio = 0;
	private int contadorSerie = 0;
	boolean siguientePulsado = false;
	boolean finalizar = false;
	private Usuario usuarioLogeado;
	private Controlador controlador;

	

	public ControlCronometro(PanelEjercicios pEjercicio, Workout workoutSelect, Cronometro cPrincipal,
			CronometroRegresivo cDescanso, Cronometro cEjercicio, CronometroRegresivo cSerie, int contadorEjercicio,
			int contadorSerie, boolean siguientePulsado, boolean finalizar, Usuario usuarioLogeado,
			Controlador controlador) {
		this.pEjercicio = pEjercicio;
		this.workoutSelect = workoutSelect;
		this.cPrincipal = cPrincipal;
		this.cDescanso = cDescanso;
		this.cEjercicio = cEjercicio;
		this.cSerie = cSerie;
		this.contadorEjercicio = contadorEjercicio;
		this.contadorSerie = contadorSerie;
		this.siguientePulsado = siguientePulsado;
		this.finalizar = finalizar;
		this.usuarioLogeado = usuarioLogeado;
		this.controlador = controlador;
	}

	@Override
	public void run() {
		Ejercicio ejercicioActual = workoutSelect.getListaEjercicios().get(contadorEjercicio);
		cPrincipal.iniciar();
		cEjercicio.iniciar();
		cSerie.iniciar();
		while (contadorEjercicio < workoutSelect.getListaEjercicios().size() && !finalizar) {
			ejercicioActual = workoutSelect.getListaEjercicios().get(contadorEjercicio);

			while (contadorSerie < ejercicioActual.getListaSeries().size() && !finalizar) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Si la serie ha finalizado
				if (!cSerie.isAlive()) {
					if (!finalizar) {
						contadorSerie++;
						// Tras finalizar la ultima serie por algun motivo entra en el bucle para
						// controlar obligamos a salir
						if (contadorSerie >= ejercicioActual.getListaSeries().size() || finalizar) {
							break;
						}
						iniciarDescanso();

						// Los campos que varian seran el descanso y la serie
						cDescanso = new CronometroRegresivo(pEjercicio.getLblDescanso(),
								workoutSelect.getListaEjercicios().get(contadorEjercicio).getTiempoDescanso());
						// Actulizamos el descanso en panel
						pEjercicio.getLblDescanso().setText(String.format("%02d:%02d",
								((int) workoutSelect.getListaEjercicios().get(contadorEjercicio).getTiempoDescanso() / 60), // min
								((int) workoutSelect.getListaEjercicios().get(contadorEjercicio).getTiempoDescanso() % 60)));

						// Inicializamos la siguiente serie pero la detenomos hasta que lo active
						cSerie = new CronometroRegresivo(pEjercicio.getGrupoCronometros().get(contadorSerie),
								ejercicioActual.getListaSeries().get(contadorSerie).getTiempo());
						cSerie.iniciar();
						cSerie.pause();
						pEjercicio.getBtnStart().setVisible(true);
					}
				}
			}
			if (!finalizar) {
				// No se por que pero la ultima serie no se ejecuta el descanso como deberia
				iniciarDescanso();
				// Reiniciar variables para el siguiente ejercicio
				contadorSerie = -1; // por algun motivo al salir del bucle y vuelve a entrar automaticametnte accede
									// a la linea 54 y accede per
				contadorEjercicio++;
				pEjercicio.getBtnStart().setVisible(false);
				pEjercicio.getBtnPause().setVisible(false);

				if (contadorEjercicio < workoutSelect.getListaEjercicios().size() && !finalizar) {
					pEjercicio.getBtnNext().setVisible(true);

					while (!siguientePulsado) {
						// No hara nada hasta que pulse siguiente
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					Siguiente();
					// tras finalizar reseteamos para la ronda siguietne
					siguientePulsado = false;
				}
			}
		}
		if (!finalizar) {
			finalizarProceso();
			controlador.visualizarPanel(Principal.enumAcciones.PANEL_WORKOUTS);;
		}
	}

	public void finalizarProceso() {
		finalizar = true;
		String nombreEjercicio = "";
		for (int i = 0; i < contadorEjercicio; i++) {
			nombreEjercicio += workoutSelect.getListaEjercicios().get(i).getNombre() + "\n";
		}
		String tiempoRealizacion = pEjercicio.getLblWorkout().getText().toString();
		double porcentajeRealizacion = (100 * contadorEjercicio) / workoutSelect.getListaEjercicios().size();
		String texto = String.format("Tiempo total del ejercicio %s ejercicio realizado %s Porcentaje %s  bien hecho",
				tiempoRealizacion, nombreEjercicio, porcentajeRealizacion);

		Historial historial = new Historial(workoutSelect, new Date(), porcentajeRealizacion, tiempoRealizacion);
		historial.mIngresarHistorico(usuarioLogeado.getEmail(), workoutSelect);
		usuarioLogeado.insertarNuevoItemHistorial(historial);
		if (cPrincipal != null) {
			cPrincipal.finishProcess();
			cDescanso.finishProcess();
			cEjercicio.finishProcess();
			cSerie.finishProcess();
		}
		JOptionPane.showMessageDialog(null, texto);
		// El cambio de ventana

	}

	public void pausar() {
		cEjercicio.pause();
		cSerie.pause();
		cPrincipal.pause();
		pEjercicio.getBtnStart().setVisible(true);
		pEjercicio.getBtnPause().setVisible(false);
	}

	public void play() {
		// La primera ejecucion seria desencadenar todo el proceso
		if (!cPrincipal.isAlive()) {
			this.start();
		}
		// Activar la serie
		if (cSerie != null) {
			cSerie.activate();
			cEjercicio.activate();
			cPrincipal.activate();

		}
		pEjercicio.getBtnStart().setVisible(false);
		pEjercicio.getBtnPause().setVisible(true);
	}

	private void iniciarDescanso() {
		cDescanso.iniciar();
		pEjercicio.getBtnStart().setVisible(false);
		pEjercicio.getBtnPause().setVisible(false);
		// Esperamos hasta que el proceso termine
		while (cDescanso.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	// Al pulsar boton siguiente
	public void Siguiente() {
		siguientePulsado = true;
		cEjercicio.reset();
		pEjercicio.cambiarVentana(workoutSelect.getListaEjercicios().get(contadorEjercicio));
		pEjercicio.getBtnStart().setVisible(true);
		pEjercicio.getBtnNext().setVisible(false);

	}
}
