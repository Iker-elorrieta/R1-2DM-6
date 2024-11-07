package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;

public class PanelHistorico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnReturn;
	private JTable table;

	public PanelHistorico() {

		setBounds(0, 0, 880, 560);
		setBackground(new Color(173, 216, 230)); // Fondo azul claro
		setLayout(null);

		// Etiqueta de título

		JLabel lblWorkouts = new JLabel("Histórico de workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkouts.setForeground(new Color(10, 75, 128)); // Azul oscuro
		lblWorkouts.setBounds(252, 20, 375, 36);
		add(lblWorkouts);

		// Botón de regresar
		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(33, 480, 160, 40);
		btnReturn.setBackground(new Color(10, 75, 128)); // Azul
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReturn.setFocusPainted(false);
		btnReturn.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnReturn);
		
		table = new JTable();
		table.setBounds(87, 85, 705, 360);
		add(table);

	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}
}