package BINF8380_Lab7;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class AminoGuiQuiz extends JFrame{
	private static String[] SHORT_NAMES = { 
			"A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V" 
	};
	private static String[] FULL_NAMES = {
			"alanine","arginine", "asparagine", "aspartic acid", "cysteine",
			"glutamine",  "glutamic acid","glycine" ,"histidine", "isoleucine",
			"leucine",  "lysine", "methionine", "phenylalanine", "proline", 
			"serine","threonine","tryptophan", "tyrosine", "valine"
	};
	private static final long serialVersionUID = -5465077034163085045L;
	private Label timeLabel;
	private Label scoreLabel;
	private Label questionLabel;
	private Label answerLabel;
	private TextField questionField = new TextField(25);
	private TextField responseField = new TextField(10);
	private TextField timeField = new TextField(10);
	private TextField scoreField = new TextField(10);
	private JButton startButton = new JButton("Begin Quiz!");
	private JButton cancelButton = new JButton("End Quiz!");
	protected Random rand = new Random();
	private int score = 0;
	protected int amino;
	private Integer time = 30;
	private boolean cancel = false;	
	
	private class responseListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String letterIn = responseField.getText().toUpperCase();
			if(letterIn.matches(SHORT_NAMES[amino]) && !cancel){
				score++;
				scoreField.setText(score+"");
				responseField.setText("");
				amino = rand.nextInt(20);
				questionField.setText(FULL_NAMES[amino]);
			}else if(!letterIn.matches(SHORT_NAMES[amino])) {
				responseField.setText("");
			}else if(cancel) {
				responseField.setText("");
				responseField.setEditable(false);
			}
		}
	}
			
	private class questionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Thread questionThread = new Thread() {
				public void run() {
				amino = rand.nextInt(20);
				questionField.setText(FULL_NAMES[amino]);		
		}
	};
	questionThread.start();
	}
	}
	public AminoGuiQuiz(String title){
		super(title);
		setLayout(new FlowLayout());
		setSize(500,400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timeLabel = new Label("Time remaining: ");
		add(timeLabel);
		add(timeField);
		timeField.setEditable(false);
		scoreLabel = new Label("Your current score: ");
		add(scoreLabel);
		add(scoreField);
		scoreField.setText(score+"");
		scoreField.setEditable(false);
		questionLabel = new Label("What is the one-letter code for the following Amino Acid? ");
		add(questionLabel);
		add(questionField);
		questionField.addActionListener(new questionListener());
		questionField.setEditable(false);
		answerLabel = new Label("One-Letter Answer: ");
		add(answerLabel);
		add(responseField);
		responseField.addActionListener(new responseListener());
		add(startButton);
		startButton.addActionListener(new ActionListener() {
			@Override
	         public void actionPerformed(ActionEvent evt) {
				startButton.setEnabled(false);
	            cancel = false;
	            score=0;
	            scoreField.setText(score+"");
	            responseField.setEditable(true);
	            amino = rand.nextInt(20);
	            questionField.setText(FULL_NAMES[amino]);
	            timeField.setText(time+"");
	            Thread timeThread = new Thread() {
	               @Override
	               public void run() { 
	                  while( time >= 0 && !cancel) {
	                     timeField.setText(time + "");
	                     --time;
	                     try {
	                    	 	sleep(1000);
	                     } catch (InterruptedException e) {
	                    	 e.printStackTrace();
	                     }
	                  }
	                  timeField.setText("GAME OVER");
	                  responseField.setEditable(false);
	                  time = 30;
	                  cancel = true;
	                  startButton.setEnabled(true);
	               }
	            };
			timeThread.start();
		}
	});
	            
		add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel = true;
				responseField.setEditable(false);
				timeField.setText("GAME OVER");
				time = 30;
				startButton.setEnabled(true);
			}
		
	        });
		
		setVisible(true);
	}
			
	public static void main(String[] args) {
		new AminoGuiQuiz("The Amino Acid Quiz Game!");
	}





}
