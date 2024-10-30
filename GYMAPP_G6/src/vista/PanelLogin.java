package vista;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tFUsuario;
	private JPasswordField tFContrasena;
	private JButton btnLogin, btnSignUp;
	private JLabel lblImg;


	public PanelLogin() {
		setBackground(new Color(141, 204, 235));
		
		setBounds(0, 0, 900, 600);
		setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(414, 135, 150, 30);
		add(lblUsuario);
		
		tFUsuario = new JTextField();
		tFUsuario.setBounds(574, 137, 206, 30);
		tFUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		add(tFUsuario);
		tFUsuario.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContrasena.setBounds(414, 188, 150, 30);
		add(lblContrasena);
		
		tFContrasena = new JPasswordField();
		tFContrasena.setBounds(574, 190, 206, 30);
		tFContrasena.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		add(tFContrasena);
		
		btnLogin = new JButton("Iniciar sesión");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setBackground(new Color(10, 75, 128));
		btnLogin.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnLogin.setBounds(483, 258, 218, 35);
		btnLogin.setForeground(Color.WHITE);
		add(btnLogin);

		JLabel lblLogin = new JLabel("INICIO DE SESIÓN");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(380, 10, 313, 66);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 30));
		add(lblLogin);
		
		btnSignUp = new JButton("Crear cuenta");
		btnSignUp.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSignUp.setBackground(new Color(10, 75, 128));
		btnSignUp.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnSignUp.setBounds(483, 316, 218, 35);
		btnSignUp.setForeground(Color.WHITE);
		add(btnSignUp);
		
		// Crea un JLabel
        lblImg = new JLabel();
        lblImg.setLocation(53, 87);
        lblImg.setSize(300, 300);
        

	}

	public JTextField gettFUsuario() {
		return tFUsuario;
	}

	public void settFUsuario(JTextField tFUsuario) {
		this.tFUsuario = tFUsuario;
	}

	public JPasswordField gettFContrasena() {
		return tFContrasena;
	}

	public void settFContrasena(JPasswordField tFContrasena) {
		this.tFContrasena = tFContrasena;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JButton getBtnSignUp() {
		return btnSignUp;
	}

	public void setBtnSignUp(JButton btnSignUp) {
		this.btnSignUp = btnSignUp;
	}

	public JLabel getLblImg() {
		return lblImg;
	}

	public void setLblImg(JLabel lblImg) {
		this.lblImg = lblImg;
	}
}

