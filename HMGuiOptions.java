import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import java.awt.Dimension;

/* Program: 1410_Final
 * Package: 
 * Module:  PName
 * Incept:  Apr 7, 2019
 * Author:  Scott Brown (skb)
 */

public class HMGuiOptions extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JRadioButton rdoEasy, rdoMed, rdoHard;

	/** Create the dialog */
	public HMGuiOptions(JFrame parent, boolean modal) {
		super(parent, modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 250);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new GridLayout(2, 1, 0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		JPanel pnlTop = new JPanel();
		pnlTop.setLayout(new FlowLayout());
		contentPanel.add(pnlTop);
		
		pnlTop.add(new JLabel("Your Name:"));
		txtName = new JTextField();
		txtName.setColumns(15);
		pnlTop.add(txtName);
		
		
		JPanel pnlBot = new JPanel();
		pnlBot.setLayout(new GridLayout(2, 3, 0, 0));
		contentPanel.add(pnlBot);
		
		JLabel lblDif = new JLabel("Select Difficulty:");
		pnlBot.add(new JLabel(" "));
		pnlBot.add(lblDif);
		pnlBot.add(new JLabel(" "));

		rdoEasy = new JRadioButton("Easy");
		rdoEasy.setHorizontalAlignment(SwingConstants.CENTER);
		rdoEasy.setSelected(true);		
		pnlBot.add(rdoEasy);

		rdoMed = new JRadioButton("Medium");
		rdoMed.setHorizontalAlignment(SwingConstants.CENTER);
		pnlBot.add(rdoMed);

		rdoHard = new JRadioButton("Hard");
		rdoHard.setHorizontalAlignment(SwingConstants.CENTER);
		pnlBot.add(rdoHard);
		
	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdoEasy);
	    group.add(rdoMed);
	    group.add(rdoHard);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new OKListener(this));
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}
	
	private String pName = null;
	private int pDiff = -2;
	
	/** Return the player name
	 * @return Name, or null if user cancelled
	 */
	public String getName() { return pName; }
	
	/** Return the player selected difficulty (1-3, or -1 if uninitialized)
	 * @return Difficulty (1-3, or -1 if user cancelled)
	 */
	public int getDiff() { return pDiff; }
	
	private class OKListener implements ActionListener {
		HMGuiOptions parent;
		
		public OKListener(HMGuiOptions parent) {
			this.parent = parent;			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			parent.pName = txtName.getText();
			if (parent.rdoEasy.isSelected()) parent.pDiff = 1;
			else if (parent.rdoMed.isSelected()) parent.pDiff = 2;
			else if (parent.rdoHard.isSelected()) parent.pDiff = 3;
			else parent.pDiff = 999;
			parent.setVisible(false);			
		}
		
	}

}
