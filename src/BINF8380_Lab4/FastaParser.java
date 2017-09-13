package BINF8380_Lab4;

//import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class FastaParser {
	 //build constructor
	public String header;
	public String sequence; 
	BufferedReader readfastaFile = new BufferedReader(new FileReader("Fastafile.txt"));
	public String getSequence() {
		return sequence;
		}
	public double getGCcontent() {
		int GCees = 0 ;
		for(int x =0;x < sequence.length() ; x++ ) {
			char base = sequence.charAt(x);
			if(base == 'G' | base == 'C') {
				GCees++;
			}
		}
		double GCpercent = GCees / sequence.length();
		return GCpercent;
		}
	public String getHeader() {
		return header;
		}
	
	
	public static void main(String[] args) throws Exception{
		public static List<FastaParser> fastaList = FastaParser.readfastaFile("FastaFile.txt");

		for( FastaParser fas : fastaList)
		{
			System.out.println(fas.getHeader());
			System.out.println(fas.getSequence());
			System.out.println(fas.getGCcontent());
		}
	}

	
}
