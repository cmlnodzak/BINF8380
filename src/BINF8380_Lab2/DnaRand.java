/* BINF 8380 - Advanced Programming
 * Conor Nodzak
 * Lab 2 
 */

package BINF8380_Lab2;

import java.util.Random;
import java.util.ArrayList;
public class DnaRand {
	
	public static void main(String[] args){
		ArrayList<String> seqList = new ArrayList<String>();
		int tripleA = 0;
		Random dnarandom = new Random();
		for(int count =0; count < 1000; count++) {
			String myDna = "";
			for(int nucleo = 0 ; nucleo < 3 ; nucleo++) {
				int nucDna = dnarandom.nextInt(4);
				switch(nucDna) {
					case 0 : myDna = myDna + 'A';
					break;
					case 1 : myDna = myDna + 'G';
					break;
					case 2 : myDna = myDna + 'C';
					break;
					case 3 : myDna = myDna + 'T';
					break;
					default : System.out.println("Error: Index out of bounds. Check range of random number generator.");
				}
				
			}
			seqList.add(myDna);
			System.out.println();
			}
		for(int i = 0; i < 1000; i++){
			System.out.println(seqList.get(i));
			if(seqList.get(i).matches("AAA")) {
				tripleA++;
				
				}
		}
		int deviation = tripleA - 15;
		System.out.println("There were "+ tripleA+ " randomly generated 3-mer's of 'AAA' found. ");
		System.out.println("The expected frequency of the three-mer 'AAA' would be (1/64), or about 15 in 1000.");	
		System.out.println("This run deviated from the expected result by "+ deviation+".");
		if(deviation == 0 ) {
			System.out.println("This is equal to the expected result!");
		}else if(deviation >= 0 && deviation <= 5 || deviation <= 0 && deviation >= -5) {
			System.out.println("This is somewhat close to the expected result.");
		}else {
			System.out.println("This is not close to the expected result.");
		}
		
	}
}

