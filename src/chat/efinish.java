package chat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class efinish extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			efinish dialog = new efinish();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public efinish() {
		getContentPane().setBackground(Color.PINK);
		getContentPane().setLayout(null);
		{
			JLabel lblSuccess = new JLabel("encrypt file is completed.");
			lblSuccess.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 31));
			lblSuccess.setBounds(28, 49, 404, 115);
			getContentPane().add(lblSuccess);
		}
		{
			JButton btnOk = new JButton("ok");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnOk.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
			btnOk.setBounds(153, 177, 99, 27);
			getContentPane().add(btnOk);
		}
		setBounds(100, 100, 450, 300);
	}

}
