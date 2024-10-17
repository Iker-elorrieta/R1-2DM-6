package vista;

import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import modelo.Usuario.IdiomaPreferido;
import modelo.Usuario.TemaPreferido;

public class PanelRegistro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tFRegistroNombre, tFRegistroApellido, tFRegistroEmail, tFRegistroUser;
	private JPasswordField pFRegistroPassword;
	private JComboBox<IdiomaPreferido> cBRegistroIdioma;
	private JComboBox<TemaPreferido> cBRegistroTema;
	private JButton btnRegistroSignUp, btnReturn;
	private JDateChooser fechaNacimientoCalendar;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// LÍMITES DE FECHAS
	Calendar ahoraMismo = Calendar.getInstance();
	int ano = ahoraMismo.get(Calendar.YEAR);
	int mes = ahoraMismo.get(Calendar.MONTH) + 1;
	int dia = ahoraMismo.get(Calendar.DATE);
	String minString = (ano - 100) + "-" + mes + "-" + dia;
	String maxString = ano + "-" + mes + "-" + dia;

	public PanelRegistro() {
		setBounds(0, 0, 880, 560);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REGISTRO");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(307, 62, 265, 84);
		add(lblNewLabel);

		JLabel lblRegistroName = new JLabel("Nombre:");
		lblRegistroName.setBounds(171, 170, 79, 25);
		add(lblRegistroName);

		JLabel lblRegistroSurname = new JLabel("Apellido:");
		lblRegistroSurname.setBounds(171, 225, 79, 25);
		add(lblRegistroSurname);

		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaDeNacimiento.setBounds(171, 351, 128, 25);
		add(lblFechaDeNacimiento);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(171, 285, 79, 25);
		add(lblEmail);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(501, 170, 79, 25);
		add(lblUsuario);

		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(501, 225, 79, 25);
		add(lblPassword);

		JLabel lblIdioma = new JLabel("Idioma:");
		lblIdioma.setBounds(501, 285, 79, 25);
		add(lblIdioma);

		JLabel lblTema = new JLabel("Tema:");
		lblTema.setBounds(501, 351, 79, 25);
		add(lblTema);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(294, 351, 175, 20);
		add(fechaNacimientoCalendar);

		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);

		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setMinSelectableDate(dateFormat.parse(minString));
			fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tFRegistroNombre = new JTextField();
		tFRegistroNombre.setBounds(242, 172, 86, 20);
		add(tFRegistroNombre);
		tFRegistroNombre.setColumns(10);

		tFRegistroApellido = new JTextField();
		tFRegistroApellido.setBounds(242, 227, 86, 20);
		add(tFRegistroApellido);
		tFRegistroApellido.setColumns(10);

		tFRegistroEmail = new JTextField();
		tFRegistroEmail.setBounds(242, 287, 86, 20);
		add(tFRegistroEmail);
		tFRegistroEmail.setColumns(10);

		tFRegistroUser = new JTextField();
		tFRegistroUser.setBounds(589, 172, 86, 20);
		add(tFRegistroUser);
		tFRegistroUser.setColumns(10);

		pFRegistroPassword = new JPasswordField();
		pFRegistroPassword.setBounds(590, 227, 85, 20);
		add(pFRegistroPassword);

		cBRegistroIdioma = new JComboBox<>(IdiomaPreferido.values());
		cBRegistroIdioma.setBounds(590, 286, 85, 22);
		add(cBRegistroIdioma);

		cBRegistroTema = new JComboBox<>(TemaPreferido.values());
		cBRegistroTema.setBounds(590, 352, 85, 22);
		add(cBRegistroTema);

		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(20, 516, 89, 23);
		add(btnReturn);

		btnRegistroSignUp = new JButton("Crear cuenta");
		btnRegistroSignUp.setBounds(376, 439, 128, 39);
		add(btnRegistroSignUp);

	}

	public JTextField gettFRegistroNombre() {
		return tFRegistroNombre;
	}

	public void settFRegistroNombre(JTextField tFRegistroNombre) {
		this.tFRegistroNombre = tFRegistroNombre;
	}

	public JTextField gettFRegistroApellido() {
		return tFRegistroApellido;
	}

	public void settFRegistroApellido(JTextField tFRegistroApellido) {
		this.tFRegistroApellido = tFRegistroApellido;
	}

	public JTextField gettFRegistroEmail() {
		return tFRegistroEmail;
	}

	public void settFRegistroEmail(JTextField tFRegistroEmail) {
		this.tFRegistroEmail = tFRegistroEmail;
	}

	public JTextField gettFRegistroUser() {
		return tFRegistroUser;
	}

	public void settFRegistroUser(JTextField tFRegistroUser) {
		this.tFRegistroUser = tFRegistroUser;
	}

	public JPasswordField getpFRegistroPassword() {
		return pFRegistroPassword;
	}

	public void setpFRegistroPassword(JPasswordField pFRegistroPassword) {
		this.pFRegistroPassword = pFRegistroPassword;
	}

	public JComboBox<IdiomaPreferido> getcBRegistroIdioma() {
		return cBRegistroIdioma;
	}

	public void setcBRegistroIdioma(JComboBox<IdiomaPreferido> cBRegistroIdioma) {
		this.cBRegistroIdioma = cBRegistroIdioma;
	}

	public JComboBox<TemaPreferido> getcBRegistroTema() {
		return cBRegistroTema;
	}

	public void setcBRegistroTema(JComboBox<TemaPreferido> cBRegistroTema) {
		this.cBRegistroTema = cBRegistroTema;
	}

	public JButton getBtnRegistroSignUp() {
		return btnRegistroSignUp;
	}

	public void setBtnRegistroSignUp(JButton btnRegistroSignUp) {
		this.btnRegistroSignUp = btnRegistroSignUp;
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}

	public JDateChooser getFechaNacimientoCalendar() {
		return fechaNacimientoCalendar;
	}

	public void setFechaNacimientoCalendar(JDateChooser fechaNacimientoCalendar) {
		this.fechaNacimientoCalendar = fechaNacimientoCalendar;
	}

}