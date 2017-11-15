package BINF8380_Lab4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class FastaParserNew{
	private String header;
	private String sequence;
	public FastaParserNew(String header, String sequence) {
		this.header = header;
		this.sequence = sequence;
	}
	public String getHeader() {
		return header.split(">")[1];
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getSequence() {
		return sequence;
	}

	public float getGCcontent() {
		float GCpercent = 0;
		float GCees = 0;
		float len = this.sequence.length();
		for(int i =0;i < this.sequence.length() ; i++ ) {
			char base = this.sequence.charAt(i);
			if(base == 'G' || base == 'C') {
				GCees++;
			}
		}
		GCpercent = GCees / len;
		return GCpercent;
	}
	public static FastaParserNew fastaSequence(String header, String seq) {
		return new FastaParserNew( header, seq);
	}
	
	public static List<FastaParserNew> readFastaFile(String filepath) throws Exception{
		BufferedReader readfasta = new BufferedReader(new FileReader(filepath));
		List<FastaParserNew> result = new ArrayList<>();
		StringBuffer h = new StringBuffer();
		StringBuffer s = new StringBuffer();
		String fasLine = "";
		while ((fasLine  = readfasta.readLine()) != null) 
			{	
			if (h.length() != 0 && fasLine.startsWith(">")) {
				result.add(fastaSequence(h.toString(),s.toString()));	
				h.setLength(0);
				s.setLength(0);
				h.append(fasLine);
			}
			else if (fasLine.startsWith(">") ) {
				h.append(fasLine);
				}
			else if (h.length() != 0 && !fasLine.startsWith(">")) {
				s.append(fasLine);
				}
			}
			result.add(fastaSequence(h.toString(),s.toString()));
		readfasta.close();
		return result;
		} 
	

	public static void main(String[] args) throws Exception{
	}
}
