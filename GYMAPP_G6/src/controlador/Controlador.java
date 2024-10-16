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

    public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelRegistro vistaRegistro ) {
        this.vistaPrincipal = vistaPrincipal;
        this.vistaLogin = vistaLogin;
        this.vistaRegistro = vistaRegistro;

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
            case PANEL_LOGIN:
                this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_LOGIN);
                break;
            case PANEL_REGISTRO:
                this.vistaPrincipal.visualizarPaneles(Principal.enumAcciones.PANEL_REGISTRO);
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Cambiar valor");
    }
}
