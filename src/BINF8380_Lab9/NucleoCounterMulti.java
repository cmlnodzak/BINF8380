package BINF8380_Lab9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class NucleoCounterMulti{
		private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors() + 1;
		private static AtomicInteger adenosine = new AtomicInteger(0);
		private static AtomicInteger thymine = new AtomicInteger(0);
		private static AtomicInteger cytosine = new AtomicInteger(0);
		private static AtomicInteger guanine = new AtomicInteger(0);
		private static AtomicInteger unknown = new AtomicInteger(0);
		private Semaphore semaphore;
		private File file;
		
	 NucleoCounterMulti(File file, Semaphore semaphore) {
		this.file = file;
		this.semaphore = semaphore;
		}	
		private static class Worker implements Runnable{
			private Semaphore semaphore;
			private File file;
			Worker(NucleoCounterMulti nucleo){
				file = nucleo.file;
				semaphore = nucleo.semaphore;
			}
			
		@Override
		public void run() {
			try {
				BufferedReader readfasta = new BufferedReader(new FileReader(file));
				for (String fasLine = readfasta.readLine(); fasLine != null; fasLine = readfasta.readLine()) {
					if(!fasLine.startsWith(">")) {
						getBases(fasLine);
					}
				}
				readfasta.close();
				semaphore.release();
			} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
		private static void getBases(String fas) {
			for(int i = 0;i < fas.length() ; i++ ) {
				char base = fas.charAt(i);
				if(base == 'G'){
					guanine.incrementAndGet();
				}else if(base == 'C') {
					cytosine.incrementAndGet();
				}else if(base == 'T') {
					thymine.incrementAndGet();
				}else if(base == 'A') {
					adenosine.incrementAndGet();
				}else {
					unknown.incrementAndGet();
				}
			}
		}	

public static void main(String[] args) throws InterruptedException {
	long startTime = System.currentTimeMillis();
	Semaphore semaphore = new Semaphore(NUM_THREADS);
	File[] listofFiles = new File("/Users/mitchnodzak/adenomasRelease/fasta").listFiles();
	for (File file : listofFiles) {
			semaphore.acquire();
			NucleoCounterMulti scanFile = new NucleoCounterMulti(file, semaphore);
			Worker work = new Worker(scanFile);
			new Thread(work).start();
	}
	
	int num = 0;
	while(num < NUM_THREADS)
	{
		semaphore.acquire();
		num++;
	}
	
	System.out.println("Count of A: "+adenosine.get());
	System.out.println("Count of T: "+thymine.get());
	System.out.println("Count of C: "+cytosine.get());
	System.out.println("Count of G: "+guanine.get());
	System.out.println("Unknown nucleotides: "+unknown.get());
	long stopTime = System.currentTimeMillis();
	float elapsedTime = ((stopTime - startTime)/1000f);
     System.out.println("The multithreaded method took: "+elapsedTime+ " seconds to run.");
     System.out.println("Number of threads: "+NUM_THREADS);
}
}
