package BINF8380_Lab6;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

import BINF8380_Lab4.FastaParserNew;


public class SeqReduce {
	
	private static void mapMaker(List<FastaParserNew> fastaList ) throws Exception{

		Map<String,Map<String,Integer>> fullMap = new LinkedHashMap<String,Map<String,Integer>>();
		SortedSet<String> idList  = new TreeSet<String>();
		for( FastaParserNew seq : fastaList) {
			String sequence = seq.getSequence();
			StringTokenizer headerToken = new StringTokenizer(seq.getHeader());
			headerToken.nextToken();
			String header = headerToken.nextToken();
			idList.add(header);
			Map<String, Integer> sampleMap = fullMap.get(sequence);
			if(sampleMap == null) {
				sampleMap = new HashMap<String, Integer>();
				sampleMap.put(header, 1);
				fullMap.put(sequence, sampleMap);
			}else {
			Integer count = sampleMap.get(header);
			if(count == null) {
				count = 0;
			}
			count++;
			sampleMap.put(header, count);
			}
		}
		
		System.out.println("Number of Sequences " + fullMap.size());
		PrintStream out = new PrintStream(new FileOutputStream("/Users/mitchnodzak/binf8380lab6.output.txt"));
		System.setOut(out);
		out.println("SAMPLE\t" + String.join("\t", fullMap.keySet()));
		Iterator<String> namesItr = idList.iterator();
		while(namesItr.hasNext()) {
			String name = namesItr.next(); 
			out.print(name+"\t");
			Iterator<String> mapKeys = fullMap.keySet().iterator();
			while(mapKeys.hasNext()) {
				Map<String, Integer> getCounts = fullMap.get(mapKeys.next());
				if (getCounts.containsKey(name)){
					out.print(getCounts.get(name)+"\t");
				}
				else {
					out.print(0+"\t");
				}
				
			}
			out.print("\n");
		}

		out.flush();    
		out.close();
	}
	
	public static void main(String[] args) throws Exception {
		List<FastaParserNew> fastaList = FastaParserNew.readFastaFile("/Users/mitchnodzak/seqsIn.txt");
		SeqReduce.mapMaker(fastaList);
		
	}
}
