package wordGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Andrew Davis
 * @since 04/13/2019
 * @version 1.0
 * 
 *
 */

public class WordGenerator<k> {
	
	private WordDificulty difficulty;
	private String word;
	private int averageLetterScore;
	private ArrayList<String> dictionary = new ArrayList<String>();
	private final char[] one = new char[] {'A','E','I','O','U','L','N','S','T','R'};
	private final char[] two = {'D','G'};
	private final char[] three = {'B','C','M','P'};
	private final char[] four = {'F','H','V','W','Y'};
	private final char[] five = {'K'};
	private final char[] eight = {'J','X'};
	private final char[] ten = {'Q','Z'};
	private int wordScore;
	private int score;

	/**
	 * Generates a WordGenerator Object with a chosen Difficulty Does not automatically generate the first word.
	 * @param dificulty
	 * @throws Exception 
	 */
	public WordGenerator(WordDificulty difficulty) throws Exception {
		super();
		this.difficulty = difficulty;
		loadDictionary();
		System.out.println("Finished Loading Dictionary");
	}

	/**
	 * @return the difficulty
	 */
	public WordDificulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(WordDificulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
		this.averageScrabblePerLetter();
	}
	
	/**
	 * Generates a score based off of the Scrabble score for each word
	 */
	private int averageScrabblePerLetter()
	{
		int score = 0;
		
		char[] letters = word.toCharArray();
		
		for(Character letter:letters)
		{
				if(this.charContains(one, letter))
					score += 1;
				else if(this.charContains(two, letter))
					score += 2;
				else if(this.charContains(three, letter))
					score += 3;
				else if(this.charContains(four, letter))
					score += 4;
				else if(this.charContains(five, letter))
					score += 5;
				else if(this.charContains(eight, letter))
					score += 8;
				else if(this.charContains(ten, letter))
					score += 10;
		}
		this.setAverageLetterScore(score/word.length());
		return this.averageLetterScore;
	}
	
	/**
	 * Tests if the array given contains the chosen letter
	 * @param array the array of letters that you want to see if the letter is in
	 * @param letter the letter that you want to see if is in the array
	 * @return if the array contains the given letter
	 */
	private boolean charContains(char[] array,char letter)
	{
		for(char letterInArray:array)
		{
			if(letterInArray == letter)
				return true;
		}
	return false;
	}

	/**
	 * @return the averageLetterScore
	 */
	public int getAverageLetterScore() {
		return averageLetterScore;
	}

	/**
	 * @param averageLetterScore the averageLetterScore to set
	 */
	public void setAverageLetterScore(int averageLetterScore) {
		this.averageLetterScore = averageLetterScore;
	}
	
	/**
	 * Loads the Dictionary File into Memory
	 * @throws Exception
	 */
	private void loadDictionary() throws Exception
	{
		Scanner dictionaryFile = new Scanner(new File("src/wordGenerator/words_alpha.txt"));
		while(dictionaryFile.hasNext())
		{
			dictionary.add(dictionaryFile.next().toUpperCase());
		}
		dictionaryFile.close();
	}
	
	/**
	 * Resets what words were already played by the player
	 * @throws Exception 
	 */
	public void resetUsedWords() throws Exception
	{
		dictionary.clear();
		loadDictionary();
	}
	
	/**
	 * Generates a word of any difficulty
	 * @return a word
	 * @throws Exception 
	 */
	public String newWord() throws Exception
	{
		if(dictionary.size() == 0)
			this.resetUsedWords();
		int index = (int) (Math.random()*dictionary.size());
		word = dictionary.get(index);
		dictionary.remove(index);
		averageScrabblePerLetter();
		return word;
	}
	
	/**
	 * Generates a word of the Difficulty chosen for the object. 
	 * @return
	 * @throws Exception 
	 */
	public String newWordWithDifficulty() throws Exception
	{
		boolean wordChosen = false;
		while(wordChosen == false)
		{
			newWord();
			if(this.getAverageLetterScore() == 1 && this.getDifficulty() == WordDificulty.Easy)
				wordChosen = true;
			else if(this.getAverageLetterScore() == 2 && this.getDifficulty() == WordDificulty.Medium)
				wordChosen = true;
			else if(this.getDifficulty() == WordDificulty.Hard && this.checkForDuplicateLetters() == false && this.getAverageLetterScore()>=3)
				wordChosen = true;
		}
		return word;
	}
	
	public boolean checkForDuplicateLetters()
	{
		int count = 0;
		for(char letter1:word.toCharArray())
		{
			count = 0;
			for(char letter2:word.toCharArray())
			{
				if(letter1 == letter2)
					count++;
			}
			if(count >= 2)
				return true;
		}
		return false;
	}
	
	/**
	 * adds up the score of guesses of a letter from the user and adds it to the word score
	 * @param guess the letter that the user has guessed
	 */
	public void addToScore(k guess)
	{
		if(this.charContains(one, guess.toString().charAt(0)))
			wordScore += 1;
		else if(this.charContains(two, guess.toString().charAt(0)))
			wordScore += 2;
		else if(this.charContains(three, guess.toString().charAt(0)))
			wordScore += 3;
		else if(this.charContains(four, guess.toString().charAt(0)))
			wordScore += 4;
		else if(this.charContains(five, guess.toString().charAt(0)))
			wordScore += 5;
		else if(this.charContains(eight, guess.toString().charAt(0)))
			wordScore += 8;
		else if(this.charContains(ten, guess.toString().charAt(0)))
			wordScore += 10;
	}
	
	/**
	 * adds up the score using the guesses that the user has given and applies the right
	 * word multiplier to the word score and then adds it to the main score counter.
	 * @param guess the letter that the user is guessing
	 * @param numberOfGuessesLeft the number of guesses the user had left at the time he/she gets it right
	 */
	public void addToScore(k guess, int numberOfGuessesLeft)
	{
		if(this.charContains(one, guess.toString().charAt(0)))
			wordScore += 1;
		else if(this.charContains(two, guess.toString().charAt(0)))
			wordScore += 2;
		else if(this.charContains(three, guess.toString().charAt(0)))
			wordScore += 3;
		else if(this.charContains(four, guess.toString().charAt(0)))
			wordScore += 4;
		else if(this.charContains(five, guess.toString().charAt(0)))
			wordScore += 5;
		else if(this.charContains(eight, guess.toString().charAt(0)))
			wordScore += 8;
		else if(this.charContains(ten, guess.toString().charAt(0)))
			wordScore += 10;
		score += wordScore*numberOfGuessesLeft;
	}
}
