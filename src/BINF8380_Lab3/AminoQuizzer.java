package BINF8380_Lab3;
import java.util.Random;
//import java.util.Timer;
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

public static long beginTime = System.currentTimeMillis();

public static void main(String[] args){
	//long timesUp = 3000;
	long timeElapsed = 0;
	Random rand = new Random();
	while (timeElapsed <= 3000) {
	for (int k = 0;k < 20; k++) {
		int amino = rand.nextInt(20);
		System.out.println("What is the one-letter abbreviation for: "+FULL_NAMES[amino]);
		timeElapsed = System.currentTimeMillis() - beginTime;
	}
		//String usrGuess = System.console().readLine().toUpperCase();
		//if (usrGuess.matches(SHORT_NAMES[amino])) {
	//		k++;
	//		System.out.println("Eureka! You have used "+timeElapsed+ "milliseconds");
			}
		}		
}
