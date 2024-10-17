package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.Principal;

public class Controlador implements ActionListener, ListSelectionListener {

	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelRegistro vistaRegistro;

	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaLogin = vistaLogin;
		
		this.inicializarControlador();

	}

	private void inicializarControlador() {
		
		this.vistaLogin.getBtnSignUp().addActionListener(this);
		this.vistaLogin.getBtnSignUp()
				.setActionCommand(Principal.enumAcciones.PANEL_REGISTRO.toString());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case PANEL_REGISTRO:
			System.out.println(accion+"  aaaaaa" );
			this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_REGISTRO);
			
			break;
		case PANEL_LOGIN:
			System.out.println(accion+"  aaaaaa" );
			this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_LOGIN);
			break;

		default:
			System.out.println(accion+" default" );
			break;

		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		System.out.println("Cambiar valor");
	}
}
