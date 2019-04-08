import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/* Program: 1410_Final
 * Package: 
 * Module:  HMGuiScores
 * Incept:  Apr 7, 2019
 * Author:  Scott Brown (skb)
 */

public class HMGuiScoreboard extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea txtScores;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			HMGuiScores dialog = new HMGuiScores();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public HMGuiScoreboard(JFrame parent, boolean modal) {
		super(parent, modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		contentPanel.setLayout(gbl_contentPanel);
				
		initTitle();
		initScorebox();

		initBtnPane();
		
	}

	private void initBtnPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Dynamically find the parent frame and hide it
				Component c = ((JButton)e.getSource()).getParent();
				while (c != null && !(c instanceof JDialog))				
					c = c.getParent();
				if (c != null) c.setVisible(false);				
			}
		});
	}

	private void initScorebox() {
		txtScores = new JTextArea();
		txtScores.setFont(new Font("Courier New", Font.BOLD, 16));
		txtScores.setEditable(false);
		GridBagConstraints gbc_txtScores = new GridBagConstraints();
		gbc_txtScores.fill = GridBagConstraints.BOTH;
		gbc_txtScores.gridx = 0;
		gbc_txtScores.gridy = 1;
		contentPanel.add(txtScores, gbc_txtScores);
	}

	private void initTitle() {
		JLabel lblTitle = new JLabel("High Scores");
		lblTitle.setFont(new Font("Freestyle Script", Font.PLAIN, 40));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.NORTH;
		gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		contentPanel.add(lblTitle, gbc_lblTitle);
	}
	
	/** Add text to the displayed TextArea 
	 * 
	 * @param s Text to add
	 */
	public void addScore(String s) {
		String b = txtScores.getText();
		if (b.length() > 0) b += "\n";
		txtScores.setText(b + s);
	}

}
