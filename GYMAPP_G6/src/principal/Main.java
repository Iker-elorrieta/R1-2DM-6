package principal;

import controlador.Controlador;

public class Main {
	public static void main(String[] args) {
		try {

			// Creamos el objeto vista.
			vista.Principal ventanaPrincipal = new vista.Principal();
			ventanaPrincipal.setVisible(true);
			
			vista.PanelLogin ventanaLogin = new vista.PanelLogin();
			
			// Creamos en controlador con acceso al modelo y la vista
			new Controlador(ventanaPrincipal,ventanaLogin );

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
