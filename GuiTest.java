
/* Program: 1410_Final
 * Package: 
 * Module:  GuiTest
 * Incept:  Apr 7, 2019
 * Author:  Scott Brown (skb)
 */

public class GuiTest {
	
	public static void main(String[] args) {
		HMGui t = new HMGui();
		t.setVisible(true);	
		
		// demo user info prompt
		System.out.printf("Prompting name...");
		String name = t.promptPlayerName();
		int diff = t.promptPlayerDifficulty();
		System.out.printf("done; prompt got name=\"%s\" diff=%d\n", name, diff);
		
		// demo scoreboard possible fail modes		
		{ 	System.out.printf("Popping scoreboard (null)...");
			t.showScoreboard(null, null);
			System.out.printf("done\n");
		}
		{	String sc_t[] = { "Thomas", "RichardExcessivelyLongUserName", "Harold", "Larry", "Moe", "Curly", "Jeb", "Alan", 
				"Carl", "Neil", "John", "Paul", "Ringo", "Freddy", "Waylon"	};
			int sc_i[] = { 3904, 8336, 9540, 2321, 8535, 4524, 5474, 5474, 6134, 3431, 5264, 100, 2071, 8564, 7912 };
			System.out.printf("Popping scoreboard (balanced long)...");
			t.showScoreboard(sc_t, sc_i);
			System.out.printf("done\n");
		}
		{	String sc_t[] = { "Thomas", "Richard" };
			int sc_i[] = { 3904, 8336 };
			System.out.printf("Popping scoreboard (short)...");
			t.showScoreboard(sc_t, sc_i); 
			System.out.printf("done\n");
		}
		{	String sc_t[] = { "Thomas", "Richard", "Harold", "Larry", "Moe", "Curly", "Jeb", "Alan" }; 
			int sc_i[] = { 3904, 8336 };
			System.out.printf("Popping scoreboard (unbalanced 1)...");
			t.showScoreboard(sc_t, sc_i);
			System.out.printf("done\n");
		}
		{	String sc_t[] = { "Thomas", "Richard" };
			int sc_i[] = { 3904, 8336, 9540, 2321, 8535, 4524, 5474, 5474, 6134, 3431, 5264, 100, 2071, 8564, 7912 };
			System.out.printf("Popping scoreboard (unbalanced 2)...");
			t.showScoreboard(sc_t, sc_i);
			System.out.printf("done\n");
		}
		
		// demo basic gui functions
		// loop user input, moving letters from valid-string to word-string, and cycling the score value		
		String unusedLetters = t.getAlphabet();
		String guessedLetters = "";
		int score = 0;
		for (;;) {
			t.setValidLetters(unusedLetters);
			t.setWord(guessedLetters);
			t.setScore(score);
			if (++score > 6) score = 0;
			char c = t.getLetter();
			int i = unusedLetters.indexOf(c);
			if (i >= 0) {
				unusedLetters = unusedLetters.substring(0,  i) + unusedLetters.substring(i + 1);
				guessedLetters += c;
			}
		}
	}
}
