package principal;

import controlador.Controlador;
import modelo.Backup;

public class Main {
	public static void main(String[] args) {
		try {
			// Creamos el objeto vista (Principal)
			vista.Principal ventanaPrincipal = new vista.Principal();
			ventanaPrincipal.setVisible(true);

			// Crear el controlador con las referencias correctas a los paneles existentes
			new Controlador(ventanaPrincipal, ventanaPrincipal.getPanelLogin(), ventanaPrincipal.getPanelRegistro(),
					ventanaPrincipal.getPanelWorkouts(), ventanaPrincipal.getPanelEjercicios());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}