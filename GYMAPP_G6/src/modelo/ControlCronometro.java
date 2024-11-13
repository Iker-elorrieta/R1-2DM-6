package modelo;

import java.util.Date;

import javax.swing.JOptionPane;

import controlador.Controlador;
import vista.PanelEjercicios;
import vista.Principal;

public class ControlCronometro extends Thread {
	private PanelEjercicios vistaEjercicio;
	private Workout selectedWorkout;
	private Cronometro cPrincipal;
	private CronometroRegresivo cDescanso;
	private Cronometro cEjercicio;
	private CronometroRegresivo cSerie;
	private int contEjercicio = 0;
	private int contSerie = 0;
	boolean nextClicked = false;
	boolean finalizar = false;
	private Usuario usuarioLogeado;
	private Controlador controlador;
	private boolean botonIniciarSeriePulsado = true;
	private boolean serieFinalizada = true;
	private final double segundosCuentaRegresiva =5;


	

	public ControlCronometro(PanelEjercicios vistaEjercicio, Controlador controlador, Usuario usuarioLogeado, Workout selectedWorkout, 
			Cronometro cPrincipal, CronometroRegresivo cDescanso, Cronometro cEjercicio, CronometroRegresivo cSerie) {
		this.vistaEjercicio = vistaEjercicio;
		this.selectedWorkout = selectedWorkout;
		this.cPrincipal = cPrincipal;
		this.cDescanso = cDescanso;
		this.cEjercicio = cEjercicio;
		this.cSerie = cSerie;
		this.usuarioLogeado = usuarioLogeado;
		this.controlador = controlador;
	}

	@Override
	public void run() {
		Ejercicio ejercicioActual = selectedWorkout.getListaEjercicios().get(contEjercicio);
		cPrincipal.iniciar();
		cEjercicio.iniciar();
		cSerie.iniciar();
		cSerie.detener();
		while (contEjercicio < selectedWorkout.getListaEjercicios().size() && !finalizar) {
			ejercicioActual = selectedWorkout.getListaEjercicios().get(contEjercicio);

			while (contSerie < ejercicioActual.getListaSeries().size() && !finalizar) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(botonIniciarSeriePulsado && contEjercicio != 1) {
					cSerie.detener();
					iniciarCuentaRegresiva();
				}

				if (!cSerie.isAlive()) {
					if (!finalizar) {
						contSerie++;
						if (contSerie >= ejercicioActual.getListaSeries().size() || finalizar) {
							break;
						}
						startDescanso();

						cDescanso = new CronometroRegresivo(vistaEjercicio.getLblCDescanso(),
								selectedWorkout.getListaEjercicios().get(contEjercicio).getTiempoDescanso());
						// Actulizamos el descanso en panel
						vistaEjercicio.getLblCDescanso().setText(String.format("%02d:%02d",
								((int) selectedWorkout.getListaEjercicios().get(contEjercicio).getTiempoDescanso() / 60), // min
								((int) selectedWorkout.getListaEjercicios().get(contEjercicio).getTiempoDescanso() % 60)));

						// Inicializamos la siguiente serie pero la detenomos hasta que lo active
						cSerie = new CronometroRegresivo(vistaEjercicio.getGrupoCronometros().get(contSerie),
								ejercicioActual.getListaSeries().get(contSerie).getTiempo());
						cSerie.iniciar();
						cSerie.detener();
						serieFinalizada = true;
						vistaEjercicio.getBtnStart().setVisible(true);
					}
				}
			}
			if (!finalizar) {
				
				startDescanso();
				contSerie = -1; 
				contEjercicio++;
				
				vistaEjercicio.getBtnStart().setVisible(false);
				vistaEjercicio.getBtnPause().setVisible(false);
				
				if (contEjercicio < selectedWorkout.getListaEjercicios().size() && !finalizar) {
					vistaEjercicio.getBtnNext().setVisible(true);

					while (!nextClicked) {
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
		if (!finalizar) {
			finalizarProceso();
			controlador.visualizarPanel(Principal.enumAcciones.PANEL_WORKOUTS);;
		}
	}

	public void finalizarProceso() {
		botonIniciarSeriePulsado = false;
		serieFinalizada = false;
		
		finalizar = true;
		String nombreEjercicio = "";
		for (int i = 0; i < contEjercicio; i++) {
			nombreEjercicio += "\n - " + selectedWorkout.getListaEjercicios().get(i).getNombre();
		}
		
		String tiempoRealizacion = vistaEjercicio.getLblCWorkout().getText().toString();
		double porcentajeRealizacion = (100 * contEjercicio) / selectedWorkout.getListaEjercicios().size();
		String texto = String.format("Tiempo total del ejercicio: %s \r\nPorcentaje de realización de ejercicios %s \r\nEjercicios: %s\r\n¡Buen trabajo!",
				tiempoRealizacion, porcentajeRealizacion, nombreEjercicio);

		
		
		Historial historial = new Historial(selectedWorkout, new Date(), porcentajeRealizacion, tiempoRealizacion);
		historial.mIngresarHistorico(usuarioLogeado.getEmail(), selectedWorkout);
		usuarioLogeado.insertarNuevoItemHistorial(historial);
		if (cPrincipal != null) {
			cPrincipal.terminarProceso();
			cDescanso.terminarProceso();
			cEjercicio.terminarProceso();
			cSerie.terminarProceso();
		}
		JOptionPane.showMessageDialog(null, texto);
	}

	public void pausar() {
		cEjercicio.detener();
		cSerie.detener();
		cPrincipal.detener();
		vistaEjercicio.getBtnStart().setVisible(true);
		vistaEjercicio.getBtnPause().setVisible(false);
	}

	public void play() {
		if (!cPrincipal.isAlive()) {
			this.start();
		}
		if (cSerie != null) {
			cSerie.activate();
			cEjercicio.activate();
			cPrincipal.activate();
			if(serieFinalizada) {
				botonIniciarSeriePulsado = true;
				serieFinalizada = false;
			} else {
			}
		} 
		vistaEjercicio.getBtnStart().setVisible(false);
		vistaEjercicio.getBtnPause().setVisible(true);
	}

	
	private void iniciarCuentaRegresiva() {
	    vistaEjercicio.getLblCProximaSerie().setText("00:05");
	    vistaEjercicio.getLblCProximaSerie().setVisible(true);
	    vistaEjercicio.getLblLaProximaSerie().setVisible(true);
	    vistaEjercicio.getBtnPause().setVisible(false);

	    CronometroRegresivo cuentaAtras = new CronometroRegresivo(vistaEjercicio.getLblCProximaSerie(), segundosCuentaRegresiva);
	    cuentaAtras.iniciar();

	    while (cuentaAtras.isAlive()) {
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    vistaEjercicio.getBtnPause().setVisible(true);
	    vistaEjercicio.getLblCProximaSerie().setVisible(false);
	    vistaEjercicio.getLblLaProximaSerie().setVisible(false);
        cSerie.activate();
        botonIniciarSeriePulsado = false; 
	}
	
	
	private void startDescanso() {
		cDescanso.iniciar();
		vistaEjercicio.getBtnStart().setVisible(false);
		vistaEjercicio.getBtnPause().setVisible(false);
		while (cDescanso.isAlive()) {
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
		cEjercicio.reset();
		cEjercicio.detener();
		vistaEjercicio.cambiarVentana(selectedWorkout.getListaEjercicios().get(contEjercicio));
		vistaEjercicio.getBtnStart().setVisible(true);
		vistaEjercicio.getBtnNext().setVisible(false);

	}
}
