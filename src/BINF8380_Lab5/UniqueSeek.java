package BINF8380_Lab5;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;


import BINF8380_Lab4.FastaParserNew;

public class UniqueSeek {
	public List<FastaParserNew> fastaList;
	public List<String> seqList;
	
	public UniqueSeek(String filename) {
		try {
			this.fastaList = FastaParserNew.readFastaFile(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void writeUnique() throws Exception {
		List<String> seqList = new ArrayList<String>();
		final Map<String, Integer> countMap = new HashMap<String, Integer>();
		for( FastaParserNew fs : fastaList){
			seqList.add(fs.getSequence().toString());
		}
		for (String seq : seqList) {
		    countMap.put(seq, 1 + (countMap.containsKey(seq) ? countMap.get(seq) : 0));
		}
		List<String> keyList = new ArrayList<String>(countMap.keySet());
		Collections.sort(keyList, new Comparator<String>() {
		    @Override
		    public int compare(String x, String y) {
		        return countMap.get(x) - countMap.get(y);
		    }
		});
		
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);
		for( String key : keyList){
			out.println(">"+countMap.get(key));
			out.println(key);
		}
		out.close();
	}

	public static void main(String[] args) throws Exception {
		UniqueSeek unique = new UniqueSeek("/Users/mitchnodzak/fasta.test.fasta");
		unique.writeUnique();
	
	}

}
