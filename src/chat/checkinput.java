package chat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class checkinput extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			checkinput dialog = new checkinput();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public checkinput() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPleaseCheckYour_1 = new JLabel("Please check your ");
			lblPleaseCheckYour_1.setBounds(43, 13, 330, 43);
			lblPleaseCheckYour_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 34));
			contentPanel.add(lblPleaseCheckYour_1);
		}
		{
			JLabel lblPleaseCheckYour = new JLabel("ip,port and name,");
			lblPleaseCheckYour.setBounds(43, 69, 330, 43);
			lblPleaseCheckYour.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 36));
			contentPanel.add(lblPleaseCheckYour);
		}
		{
			JButton okButton = new JButton("OK");
			okButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
			okButton.setBounds(158, 193, 111, 29);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JLabel lblTheyCantBe = new JLabel("they can't be empty.");
			lblTheyCantBe.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 36));
			lblTheyCantBe.setBounds(43, 125, 354, 43);
			contentPanel.add(lblTheyCantBe);
		}
	}

}
