import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.awt.Font;
import javax.swing.SwingConstants;

/* Program: 1410_Final
 * Package: 
 * Module:  Gui
 * Incept:  Apr 7, 2019
 * Author:  Scott Brown (skb)
 */


/** The Hangman game GUI, implemented using an internal JFrame for a cleaner interface
 * 
 * TODO: Splash, prompt player name/difficulty, hi score board, etc.
 * 
 * @author skb
 *
 */
public class HMGui {
	private JFrame thisFrame;
	private JPanel contentPane;
	private JButton[] btnLetters;
	private JPnlStickman pnlStickman; 
	private JLabel lblWord;
	private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String validLtrs = alphabet;
	private ButtonListener btnListener;

	/** Sets the word display (below the letter grid), adding spaces between characters
	 * 
	 * @param word The word to display
	 */
	public void setWord(String word) {
		if (word.length() < 1) return;
		
		word = word.toUpperCase();
		char[] a = new char[word.length() * 2 - 1];
		a[0] = word.charAt(0);
		int s, d;
		for (s = d = 1; s < word.length(); s++) {
			a[d++] = ' ';				
			a[d++] = word.charAt(s);			
		}
		lblWord.setText(new String(a));	
	}

	
	/** Returns a string containing all of the characters in the alphabet
	 * defined in this class
	 * 
	 * @return The currently defined alphabet
	 */
	public String getAlphabet() {
		return alphabet;
	};
	
	
	/** Defines which grid letter buttons to enable when getLetter() is called. All other
	 * buttons will be disabled.
	 * 
	 * @param ltrs A string of letters to be marked as enabled at the next call to getLetter
	 */
	public void setValidLetters(String ltrs) {
		validLtrs = ltrs.toUpperCase();		
	}
	
	
	
	
	/** Enables the letter array and waits for the user to click one, then
	 * disables the array and returns the (uppercase) letter selected.
	 * @return Selected upper case letter
	 */
	public char getLetter() {
		// Enable the grid, set a sync latch, and wait for the button event handler to run
		btnListener.buttonLatch = new CountDownLatch(1);
		enableGrid();
		try {
			btnListener.buttonLatch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();			
		}
		disableGrid();
		return btnListener.selectedLtr;		
	}

	
	/** Cause the Stickman panel to be redrawn with the given score
	 * 
	 * @param score Number of stickman parts to display (clamped 0-6)
	 */
	public void setScore(int s) {
		pnlStickman.setScore(s);
	}

	
	
	
	/* Enable grid buttons for each letter in validLtrs */
	private void enableGrid() {
		for (char c : validLtrs.toCharArray()) {
			int i = alphabet.indexOf(c);
			if (i >= 0)
				btnLetters[i].setEnabled(true);
		}
	}
	
	/* Set all grid buttons disabled */
	private void disableGrid() {
		for (int i = 0; i < btnLetters.length; i++)
			btnLetters[i].setEnabled(false);
	}

	
	/* Event handler class for the letter grid buttons */
	private class ButtonListener implements ActionListener {
		protected CountDownLatch buttonLatch;
		protected char selectedLtr;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			// save the selected letter
			selectedLtr = alphabet.charAt(((Integer)(b.getClientProperty(0))).intValue());
			// and notify the waiting thread that we have data for it		
			if (buttonLatch != null) buttonLatch.countDown();
		}
	}
	
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Gui frame = new Gui();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/** Create the frame and initialize its components */
	public HMGui() {
		thisFrame = new JFrame(); 
		thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisFrame.setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		thisFrame.setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel pnlLeft = new JPanel();
		pnlLeft.setLayout(new GridLayout(2, 1, 0, 0));
		contentPane.add(pnlLeft);
		
		initPnlAlpha(pnlLeft);
		initPnlWord(pnlLeft);		
		initPnlStickman();		
	}
	
	/** Direct line to the HMGui's JFrame setVisible method
	 * @see java.awt.Window#setVisible(boolean)
	 */
	public void setVisible(boolean v) {
		thisFrame.setVisible(v);
	}

	/* Init the Stickman panel (most of that is done inside JPnlStickman) */
	private void initPnlStickman() {
		pnlStickman = new JPnlStickman();
		contentPane.add(pnlStickman);
	}

	/* Init the button grid representing the alphabet selector */
	private void initPnlAlpha(JPanel parent) {
		JPanel PnlAlpha = new JPanel();
		parent.add(PnlAlpha);
		btnListener = new ButtonListener();
		// if alphabet is longer than 30 we're in trouble
		PnlAlpha.setLayout(new GridLayout(6, 5, 0, 0));
		btnLetters = new JButton[alphabet.length()];
		for (int i = 0; i < alphabet.length(); i++) {
			JButton b = new JButton("" + alphabet.charAt(i));
			b.putClientProperty(0, i);
			b.setForeground(Color.BLUE);
			b.setEnabled(false);
			b.addActionListener(btnListener);
			PnlAlpha.add(b);
			btnLetters[i] = b; 
		}
	}
	
	/* Init the panel that shows the current word guess */
	private void initPnlWord(JPanel parent) {
		lblWord = new JLabel();
		lblWord.setFont(new Font("Tahoma", Font.PLAIN, 22));		
		lblWord.setHorizontalAlignment(SwingConstants.CENTER);
		//lblWord.setText("L _ R _ M   _ P S _ M");
		parent.add(lblWord);
	}	
	
	

	/** Implements the Stickman JPanel and its drawing code
	 * 
	 * @author skb
	 *
	 */
	protected class JPnlStickman extends JPanel {
		private int score = 0;
		private JLabel lblScore;
		
		public JPnlStickman() {
			setLayout(new GridLayout(1,  1));
			lblScore = new JLabel();
			lblScore.setVerticalAlignment(SwingConstants.BOTTOM);
			lblScore.setHorizontalAlignment(SwingConstants.CENTER);
			lblScore.setFont(new Font("Tahoma", Font.PLAIN, 22));		
			add(lblScore);
			
		}
		
		/** Cause the Stickman panel to be redrawn with the given score
		 * 
		 * @param score Number of stickman parts to display (clamped 0-6)
		 */
		public void setScore(int score) {
			if (score < 0) score = 0;
			if (score > 6) score = 6;
			this.score = score;
			repaint();			
		}
		
		/** Draw circle of radius r centered on [xc,yc] */
		private void drawCircle(Graphics g, int xc, int yc, int r) {		
			g.drawOval(xc - r, yc - r, r * 2, r * 2);		
		}
		private void fillCircle(Graphics g, int xc, int yc, int r) {		
			g.fillOval(xc - r, yc - r, r * 2, r * 2);		
		}

		/** Draw a thick line from [x1,y1]-[x2,y2] */		 
		private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int th) {
			if (th < 1) return;
			if (th == 1) {
				g.drawLine(x1, y1, x2, y2);
				return;				
			}			
			int hth = th / 2;
			fillCircle(g, x1, y1, hth);
			fillCircle(g, x2, y2, hth);			
			if (x1 == x2) {
				if (y1 > y2) { y1 ^= y2; y2 ^= y1; y1 ^= y2; }
				g.fillRect(x1 - hth, y1, th, y2 - y1 + 1);
			} else if (y1 == y2) {
				if (x1 > x2) { x1 ^= x2; x2 ^= x1; x1 ^= x2; }
				g.fillRect(x1, y1 - hth, x2 - x1 + 1, th);
			} else {
				double theta = Math.atan((double)(y2 - y1) / (double)(x2 - x1));
				theta += Math.PI / 2;
				int xx = (int)(Math.cos(theta) * hth);
				int yy = (int)(Math.sin(theta) * hth);
				int[] xa = { x1-xx, x1+xx, x2+xx, x2-xx };
				int[] ya = { y1-yy, y1+yy, y2+yy, y2-yy };
				g.fillPolygon(xa, ya, 4);				
			}
		}

		/** Draw a thick circle of radius r centered on [xc,yc] */
		private void drawThickCircle(Graphics g, int xc, int yc, int r, int th) {
			double t1, t2;
			double d2r = Math.atan(1) / 45.0;
			int as = 360 / 36;
			int x1, y1, x2, y2;
			
			t1 = 0;
			x1 = (int)(xc + Math.cos(t1) * r);
			y1 = (int)(yc + Math.sin(t1) * r);			
			for (int a = as; a <= 360; a += as) {
				t2 = d2r * a;
				x2 = (int)(xc + Math.cos(t2) * r);
				y2 = (int)(yc + Math.sin(t2) * r);
				drawThickLine(g, x1, y1, x2, y2, th);
				t1 = t2; x1 = x2; y1 = y2;				
			}
		}


		@Override
		public void paint(java.awt.Graphics g) {
			// TODO Auto-generated method stub
			lblScore.setText("Remaining: " + (6 - score));
			super.paint(g);

			int bx = 162, by = 10;
			
			g.setColor(Color.BLACK);
			drawThickLine(g, bx - 150, by + 250, bx - 150, by, 9);
			drawThickLine(g, bx - 150, by, bx, by, 9);
			drawThickLine(g, bx, by, bx, by + 20, 9);
			
			g.setColor(Color.RED);
			switch (score) {
			case 6:	drawThickLine(g, bx, by + 230, bx + 50, by + 330, 9); // right leg
			case 5:	drawThickLine(g, bx, by + 230, bx - 50, by + 330, 9); // left leg
			case 4:	drawThickLine(g, bx, by + 120, bx + 70, by + 180, 9); // right arm
			case 3:	drawThickLine(g, bx, by + 120, bx - 70, by + 180, 9); // left arm
			case 2:	drawThickLine(g, bx, by + 100, bx, by + 230, 9); // body
			case 1:	drawThickCircle(g, bx, by + 60, 40, 9); // head
			}
		}		
	}
	
	
	
	////////////////////////////////////////////////////////////////////////
	// Options prompt pop-up
	////////////////////////////////////////////////////////////////////////
	private String pName;
	private int pDiff = -1;
	
	/** Pops a modal dialog to get the player's name and difficulty selection
	 * 
	 * @return Player name - may be null if player closed the prompt instead of hitting OK
	 * @see #promptPlayerDifficulty()
	 */
	public String promptPlayerName() {
		HMGuiOptions dialog = null;
		try {
			dialog = new HMGuiOptions(thisFrame, true);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pName = dialog.getName();
		pDiff = dialog.getDiff();
		return pName;
	}
	
	/** Gets the player difficulty setting.
	 * Return value is not valid until after promptPlayerName() has been called.
	 * 
	 * @return Difficulty setting (1=easy 2=medium 3=hard)
	 * @see #promptPlayerName()
	 */
	
	public int promptPlayerDifficulty() {
		return pDiff;
	}

	
	
	////////////////////////////////////////////////////////////////////////
	// High score pop-up
	////////////////////////////////////////////////////////////////////////
	/** Pop a window to display the high score board. Display will be sorted
	 * on descending score values. Maximum of ten names displayed. Both arrays
	 * should be the same length.
	 * 
	 * @param names List of names to display
	 * @param scores List of scores corresponding to names
	 */
	public void showScoreboard(String[] names, int[] scores) {
		// Here's our required generic collection class...
		List<PScore> topScores = new ArrayList<>();
		int i, j, listLen = 0;
		
		if (names != null && scores != null) {
			listLen = Math.min(names.length, scores.length);		
			for (i = 0; i < names.length; i++) {
				j = (i < listLen) ? scores[i] : -1;
				topScores.add(new PScore(names[i], j));
			}
			topScores.sort(new PScore());
		}
		
		try {
			HMGuiScoreboard dialog = new HMGuiScoreboard(thisFrame, true);
			listLen = Math.min(listLen, 10);
			for (i = 0; i < listLen; i++) {
				String n = topScores.get(i).name;
				j = Math.min(15, n.length());				
				String s = String.format("%-15s %8d", n.substring(0, j), topScores.get(i).score);
				dialog.addScore(s);
			}
			dialog.setVisible(true);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class PScore implements Comparator<PScore> {
		protected String name;
		protected int score;
		
		public PScore() { }
		public PScore(String name, int score) {
			this.name = name;
			this.score = score;
		}

		// Sort on descending score, then ascending name
		@Override
		public int compare(PScore o1, PScore o2) {
			int i = o2.score - o1.score;
			if (i == 0) i = o1.name.compareToIgnoreCase(o2.name);
			return i;
		}
	}


}
