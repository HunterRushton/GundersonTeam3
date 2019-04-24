/**
 * 
 */
package wordGenerator;

/**
 * @author Kaylyn
 *
 */
public class TestClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		WordGenerator wordTest = new WordGenerator(WordDificulty.Medium);
		int count = 0;
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		int six = 0;
		int seven = 0;
		int eight = 0;
		int nine = 0;
		int ten = 0;
		
		while (count<10)
		{
			wordTest.newWordWithDifficulty();
			System.out.println(wordTest.getWord());
			if(wordTest.getAverageLetterScore() == 1)
				one++;
			else if (wordTest.getAverageLetterScore() == 2)
				two++;
			else if (wordTest.getAverageLetterScore() == 3)
				three++;
			else if (wordTest.getAverageLetterScore() == 4)
				four++;
			else if (wordTest.getAverageLetterScore() == 5)
				five++;
			else if (wordTest.getAverageLetterScore() == 6)
				six++;
			else if (wordTest.getAverageLetterScore() == 7)
				seven++;
			else if (wordTest.getAverageLetterScore() == 8)
				eight++;
			else if (wordTest.getAverageLetterScore() == 9)
				nine++;
			else if (wordTest.getAverageLetterScore() == 10)
				ten++;
			count++;
		}
		System.out.printf("one%s%ntwo%s%nthree%s%nfour%s%nfive%s%nsix%s%nseven%s%neight%s%nnine%s%nten%s%n", one,two,three,four,five,six,seven,eight,nine,ten);

	}

}
