package BINF8380_Lab6;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.Map.Entry;

import BINF8380_Lab4.FastaParserNew;


public class SeqReduce {
	
	private static void mapMaker(List<FastaParserNew> fastaList ) throws Exception{
		Map<String,Map<String,Integer>> fullMap = new HashMap<String,Map<String,Integer>>();
		
		for( FastaParserNew seq : fastaList){
		    fullMap.put(seq.getSequence().toString(), new HashMap<String,Integer>(){
		    		{
		    			put(seq.getHeader(), 1 + (containsKey(seq.getHeader()) ? get(seq.getHeader()) : 0));
		    		}
		    	});
		    }
		PrintStream out = new PrintStream(new FileOutputStream("/Users/mitchnodzak/binf8380lab6.output.txt"));
		System.setOut(out);
		for (Entry<String, Map<String, Integer>> sample : fullMap.entrySet()) {
			String id = sample.getKey();
		     for (Entry<String,Integer> inner : sample.getValue().entrySet()) {
		    	 out.println(id + "\t" + inner.getKey()+ "\t" +inner.getValue());
		   }
		     }
		out.flush();
		out.close();
	}
	

	public static void main(String[] args) throws Exception {
		List<FastaParserNew> fastaList = FastaParserNew.readFastaFile("/Users/mitchnodzak/seqsIn.txt");
		mapMaker(fastaList);
		
	}
}
