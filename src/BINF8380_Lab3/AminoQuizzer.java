package BINF8380_Lab3;
import java.util.Random;
import java.util.Scanner;

public class AminoQuizzer {

	public static String[] SHORT_NAMES = { 
			"A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V" 
	};

	public static String[] FULL_NAMES = {
			"alanine","arginine", "asparagine", "aspartic acid", "cysteine",
			"glutamine",  "glutamic acid","glycine" ,"histidine", "isoleucine",
			"leucine",  "lysine", "methionine", "phenylalanine", "proline", 
			"serine","threonine","tryptophan", "tyrosine", "valine"
	};

	static Scanner getInput = new Scanner(System.in);

	public static void main(String[] args){
		long beginTime = System.currentTimeMillis();
		long timesUp = beginTime +30*1000;
		int score = 0;
		Random rand = new Random();
		Aminogame:
		while (System.currentTimeMillis()< timesUp) {
			for (int k = 0;k < 20; k++) {
				int amino = rand.nextInt(20);
				System.out.println("What is the one-letter code for: "+FULL_NAMES[amino]);
				String usrGuess = getInput.next().toUpperCase();
				if( usrGuess.matches(SHORT_NAMES[amino])){
					score++;
					System.out.println("Correct! Your current score is: " +score);
					long timeElapsed = (System.currentTimeMillis() - beginTime);
					float timegone = timeElapsed/1000f;
					System.out.println(timegone+" seconds have elapsed!");
				}
				else {
					score--;
					System.out.println("Wrong! Your current score is: " +score);
					System.out.println("You lose! Study more and start over!");
					break Aminogame;
				}
				
			}
		
		}
	getInput.close();
	System.out.println("..............");
	System.out.println("Good job! Time is up!");
	System.out.println("Your final score was: "+score+ "points!");

	}
}
