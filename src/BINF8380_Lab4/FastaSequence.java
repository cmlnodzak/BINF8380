package BINF8380_Lab4;

import java.util.List;
	

public class FastaSequence {

	public static void main(String[] args) throws Exception {
		List<FastaParserNew> fastaList = FastaParserNew.readFastaFile("/Users/mitchnodzak/fasta.test.fasta");
		for( FastaParserNew fs : fastaList){
			System.out.println("The header is: "+fs.getHeader());
			System.out.println("Your sequence: "+fs.getSequence());
			System.out.println("The GC content is: "+fs.getGCcontent());
		}
	}
}