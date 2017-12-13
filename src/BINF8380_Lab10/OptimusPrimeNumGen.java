package BINF8380_Lab10;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


public class OptimusPrimeNumGen extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JTextArea aTextField = new JTextArea();
	private final JButton primeButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private volatile boolean cancel = false;
	private final OptimusPrimeNumGen thisFrame;
	private final int NUM_PROCS = Runtime.getRuntime().availableProcessors();
	private final ConcurrentSkipListSet<Integer> primes = new ConcurrentSkipListSet<Integer>();
	private long startTime;
	private final ConcurrentHashMap<Integer,Integer> primeMap = new ConcurrentHashMap<Integer,Integer>();
	
	private OptimusPrimeNumGen(String title)
	{
		super(title);
		this.thisFrame = this;
		cancelButton.setEnabled(false);
		aTextField.setEditable(false);
		setSize(500, 500);
		setLocationRelativeTo(null);
		//kill java VM on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(primeButton,  BorderLayout.SOUTH);
		getContentPane().add(cancelButton,  BorderLayout.EAST);
		getContentPane().add( new JScrollPane(aTextField),  BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		OptimusPrimeNumGen png = new OptimusPrimeNumGen("Primer Number Generator");
		
		// don't add the action listener from the constructor
		png.addActionListeners();
		png.setVisible(true);

	}
	
	private class CancelOption implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			cancel = true;
		}
	}
	private void addActionListeners()
	{
		cancelButton.addActionListener(new CancelOption());
	
		primeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					String num = JOptionPane.showInputDialog("Enter a large integer");
					Integer max = null;	
					try
					{
						max = Integer.parseInt(num);
						for(int x=1; x < max; x++) {
							primeMap.put(x,x);
						}
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(thisFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
					if( max != null)
					{
						aTextField.setText("");
						primeButton.setEnabled(false);
						cancelButton.setEnabled(true);
						cancel = false;
						new Thread(new UserInput()).start();
						startTime = System.currentTimeMillis();
					}
					
				}});
		}

	private class Processor implements Runnable {
	    private Semaphore sem;
		private ConcurrentHashMap<Integer,Integer> map;
	    public Processor(Semaphore sem, ConcurrentHashMap<Integer,Integer> map) {
	        this.sem = sem;
	        this.map = map;
	    }
	    @Override
	    public void run() {
	        try {
	        		int i = 0;
	        		sem.acquire();
	            long lastUpdate = System.currentTimeMillis();
	            while(!map.isEmpty()) {
	            		for (Iterator<Entry<Integer, Integer>> iter = map.entrySet().iterator(); iter.hasNext(); ) {
	            		Entry<Integer, Integer> entry = iter.next();
	            		i = entry.getKey();
					if(isPrime(i))
						{ 
						primes.add(i);
						//update ConcurrentHashMap, reduce size to be tested.
						map.remove(i, i);
						}
	            		}
	            }
				if( System.currentTimeMillis() - lastUpdate > 500)
				{
					final String outString= "Found " + primes.size() + " in " + i ;
					SwingUtilities.invokeLater( new Runnable()
					{
						@Override
						public void run()
							{
								aTextField.setText(outString);
						
							}
					});
					}
	        		}catch (InterruptedException e) {
	            e.printStackTrace();
	        }finally{
				sem.release();
			}
	    }
	}

	private class UserInput implements Runnable
	{
		@Override
		public void run()
		{
			Semaphore sem = new Semaphore(NUM_PROCS);
			for(int x=0;x < NUM_PROCS;x++)
			{
				try {
					sem.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Processor process =  new Processor(sem, primeMap);
				new Thread(process).start();
			}
			
			final StringBuffer primebuff = new StringBuffer();
			for(int prime : primes) {
			primebuff.append(prime + "\n");
			}
			primebuff.append("This process took "+ (System.currentTimeMillis() - startTime) / 1000f + "seconds to complete.\n");
			if( cancel)
				primebuff.append("cancelled");
			SwingUtilities.invokeLater( new Runnable()
			{
				@Override
				public void run()
				{
					cancel = false;
					primeButton.setEnabled(true);
					cancelButton.setEnabled(false);
					aTextField.setText( (cancel ? "cancelled " : "") +  primebuff.toString());
				}
			});
		}
	}
	// end run
// end UserInput

	private boolean isPrime(int i)
	{
		for( int x=2; x < i -1; x++)
			if( i % x == 0  )
				return false;
		
		return true;
	}
}

