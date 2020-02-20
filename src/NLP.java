import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.TextArea;

public class NLP {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NLP window = new NLP();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws FileNotFoundException
	 */
	public NLP() throws FileNotFoundException {

		String content = new Scanner(new File("C:\\Users\\kaana\\Novel-Samples\\BÝLÝM ÝÞ BAÞINDA.txt"))
				.useDelimiter("\\Z").next();
		
		content = content
		//Add your file paths here!!!!!
				+ new Scanner(new File("C:\\Users\\kaana\\Novel-Samples\\BOZKIRDA.txt")).useDelimiter("\\Z").next()
				+ new Scanner(new File("C:\\Users\\kaana\\Novel-Samples\\DEÐÝÞÝM.txt")).useDelimiter("\\Z").next()
				+ new Scanner(new File("C:\\Users\\kaana\\Novel-Samples\\DENEMELER.txt")).useDelimiter("\\Z").next()
				+ new Scanner(new File("C:\\Users\\kaana\\Novel-Samples\\UNUTULMUÞ DÝYARLAR.txt")).useDelimiter("\\Z")
						.next();

		String[] words = content.replaceAll("\\p{Punct}", "").toLowerCase().split("\\s+");
		;
		List<countingArray> countArray = new ArrayList<countingArray>();
		System.out.println(words.length);
		boolean ifExists = false;
		initialize(words, countArray, ifExists);

	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize(String[] words, List<countingArray> countArray, boolean ifExists) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		TextArea textArea = new TextArea();
		textArea.setRows(100);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.VERTICAL;
		gbc_textArea.gridwidth = 11;
		gbc_textArea.anchor = GridBagConstraints.NORTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 2;
		frame.getContentPane().add(textArea, gbc_textArea);

		JButton diButton = new JButton("Digram");
		diButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(digram(words, countArray, ifExists));
			}
		});
		GridBagConstraints gbc_diButton = new GridBagConstraints();
		gbc_diButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_diButton.insets = new Insets(0, 0, 5, 5);
		gbc_diButton.gridx = 3;
		gbc_diButton.gridy = 1;
		frame.getContentPane().add(diButton, gbc_diButton);

		JButton triButton = new JButton("Trigram");
		triButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(trigram(words, countArray, ifExists));
			}
		});
		GridBagConstraints gbc_triButton = new GridBagConstraints();
		gbc_triButton.anchor = GridBagConstraints.EAST;
		gbc_triButton.insets = new Insets(0, 0, 5, 5);
		gbc_triButton.gridx = 6;
		gbc_triButton.gridy = 1;
		frame.getContentPane().add(triButton, gbc_triButton);

		JButton uniButton = new JButton("Unigram");
		uniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.setText(unigram(words, countArray, ifExists));
			}
		});
		GridBagConstraints gbc_uniButton = new GridBagConstraints();
		gbc_uniButton.anchor = GridBagConstraints.WEST;
		gbc_uniButton.gridwidth = 2;
		gbc_uniButton.insets = new Insets(0, 0, 5, 5);
		gbc_uniButton.gridx = 0;
		gbc_uniButton.gridy = 1;
		frame.getContentPane().add(uniButton, gbc_uniButton);

	}

	private String unigram(String[] words, List<countingArray> countArray, boolean ifExists) {
		countArray.clear();
		String returnString = "Total number of words = " + words.length + "\n";
		for (int i = 0; i < words.length; i++) {
			ifExists = false;
			for (int j = 0; j < countArray.size(); j++) {
				if (words[i].equals(countArray.get(j).getWord())) {
					countArray.get(j).setCount(countArray.get(j).getCount() + 1);
					ifExists = true;
				}
			}

			if (!ifExists) {
				countArray.add(new countingArray(words[i], 1));
			}
		}
		for (int i = 0; i < countArray.size(); i++) {
			if (countArray.get(i).getCount() < 70) {
				countArray.remove(i);
				i--;
			}

		}
		for (int i = 0; i < 100; i++) {
			int max = 0;
			int max_id = 0;
			for (int j = 0; j < countArray.size(); j++) { // arraysize azalacaðý için sýkýntý çýkabilir
				if (countArray.get(j).getCount() > max) {
					max = countArray.get(j).getCount();
					max_id = j;
				}

			}
			returnString = returnString + (i + 1) + "-) " + countArray.get(max_id).getWord() + " - "
					+ countArray.get(max_id).getCount() + "\n";
			System.out.println(
					(i + 1) + " " + countArray.get(max_id).getWord() + " " + countArray.get(max_id).getCount());
			countArray.remove(max_id);
		}
		
		return returnString;

	}

	private String digram(String[] words, List<countingArray> countArray, boolean ifExists) {
		countArray.clear();
		String returnString = "Total number of two word combinations = " + (words.length - 1) + "\n";
		for (int i = 0; i < words.length - 1; i++) {
			ifExists = false;
			String twoWords = words[i] + " " + words[i + 1];
			for (int j = 0; j < countArray.size(); j++) {
				if (twoWords.equals(countArray.get(j).getWord())) {
					countArray.get(j).setCount(countArray.get(j).getCount() + 1);
					ifExists = true;
				}
			}

			if (!ifExists) {
				countArray.add(new countingArray(twoWords, 1));
			}
		}
		for (int i = 0; i < countArray.size(); i++) {
			if (countArray.get(i).getCount() < 5) {
				countArray.remove(i);
				i--;
			}

		}
		for (int i = 0; i < 100; i++) {
			int max = 0;
			int max_id = 0;
			for (int j = 0; j < countArray.size(); j++) { // arraysize azalacaðý için sýkýntý çýkabilir
				if (countArray.get(j).getCount() > max) {
					max = countArray.get(j).getCount();
					max_id = j;
				}

			}
			returnString = returnString + (i + 1) + "-) " + countArray.get(max_id).getWord() + " - "
					+ countArray.get(max_id).getCount() + "\n";
			/*System.out.println(
					(i + 1) + " " + countArray.get(max_id).getWord() + " " + countArray.get(max_id).getCount());
			countArray.remove(max_id);*/
		}

		return returnString;

	}

	private String trigram(String[] words, List<countingArray> countArray, boolean ifExists) {
		countArray.clear();
		String returnString = "Total number of three word combinations = " + (words.length - 1) + "\n";
		for (int i = 0; i < words.length - 2; i++) {
			ifExists = false;
			String threeWords = words[i] + " " + words[i + 1] + " " + words[i + 2];
			for (int j = 0; j < countArray.size(); j++) {
				if (threeWords.equals(countArray.get(j).getWord())) {
					countArray.get(j).setCount(countArray.get(j).getCount() + 1);
					ifExists = true;
				}
			}

			if (!ifExists) {
				countArray.add(new countingArray(threeWords, 1));
			}
		}

		for (int i = 0; i < 100; i++) {
			int max = 0;
			int max_id = 0;
			for (int j = 0; j < countArray.size(); j++) { // arraysize azalacaðý için sýkýntý çýkabilir
				if (countArray.get(j).getCount() > max) {
					max = countArray.get(j).getCount();
					max_id = j;
				}

			}
			returnString = returnString + (i + 1) + "-) " + countArray.get(max_id).getWord() + " - "
					+ countArray.get(max_id).getCount() + "\n"; // WRONG
			System.out.println(
					(i + 1) + " " + countArray.get(max_id).getWord() + " " + countArray.get(max_id).getCount());
			countArray.remove(max_id);
		}
		return returnString;

	}
}
