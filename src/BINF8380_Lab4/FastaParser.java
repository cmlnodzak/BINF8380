package BINF8380_Lab4;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class FastaParser { 
	public static ArrayList<String> fastaData = new ArrayList<String>();
	public static ArrayList<String> readFastaFile(String filepath) throws Exception{
		String fasLine=null;
		BufferedReader readfasta = new BufferedReader(new FileReader(filepath));
		while ((fasLine = readfasta.readLine()) != null) {
			String fasSequence = "";
            if(!fasLine.startsWith(">") ) {
            	fasSequence = fasLine;
            fastaData.add(fasSequence);
            }
			else if ( fasLine.startsWith(">")) {
				
            		String seqHeader = fasLine.split(">")[0];
            		fastaData.add(seqHeader);
            }            
		}
		readfasta.close();
		return fastaData;
         }

	public String getSequence(int x) {
		String sequence = "";
		while(x % 2 != 0) {
			sequence = fastaData.get(x).toString();
		}
		return sequence;
	}
	public double getGCcontent(int x) {
		double GCpercent = 0;
		while(x % 2 != 0) {
		double GCees = 0 ;
		String sequence = fastaData.get(x);
		double len = sequence.length();
		for(int i =0;i < sequence.length() ; i++ ) {
			char base = sequence.charAt(i);
			if(base == 'G' | base == 'C') {
				GCees++;
			}
		}
		GCpercent = GCees / len;
		}
		return GCpercent;
	}
	
	public String getHeader(int x) {
		String header = "";
		while(x % 2 == 0){
			header = fastaData.get(x);
			}
			return header;
		}
	
	
	public static void main(String[] args) throws Exception{
		List<String> myData = FastaParser.readFastaFile("/Users/mitchnodzak/git/BINF8380/src/BINF8380_Lab4/mtDNA_Chry.overlap.unaligned.fasta");
		for( int x=0; x < myData.size(); x++)
		{
			System.out.println(myData.getHeader(x));
			System.out.println(myData.getSequence(x));
			System.out.println(myData.getGCcontent(x));
		}
	}
	
	
}

