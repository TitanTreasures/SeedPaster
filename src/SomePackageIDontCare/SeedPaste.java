package SomePackageIDontCare;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;


public class SeedPaste implements KeyListener,ActionListener{

	JButton button1, button2;
	JTextField textField1, textField2, textField3, textField4, textField5, textField6;
	int counter = 0;
	int highlightCounter;
	ArrayList<String> array = new ArrayList<String>();
	Font font1 = new Font("Courier", Font.BOLD, 14);

	public void SeedPasteRun(){
		
		JFrame frame = new JFrame("SeedPaster V2.1 by Alexander Sørensen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,300);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass()
				.getClassLoader().getResource("SeedPaster/resources/icon.png")));
		int y = 50;
		JLabel label4 = new JLabel("Players");
		label4.setBounds(50,y-20, 200,20);
		textField4 = new JTextField();
		textField4.setBounds(50,y, 100,20);
		textField4.addKeyListener(this);
		JLabel label5 = new JLabel("Ranges:");
		label5.setBounds(200,y-20, 200,20);
		textField5 = new JTextField();
		textField5.setBounds(200,y, 300,20);
		textField5.setFont(new Font(textField5.getFont().getFontName(), Font.BOLD, textField5.getFont().getSize()));
		textField5.setHorizontalAlignment(JTextField.CENTER);
		textField5.addKeyListener(this);
		textField5.setEditable(false);
		JLabel label6 = new JLabel("BYEs:");
		label6.setBounds(550,y-20, 100,20);
		textField6 = new JTextField();
		textField6.setBounds(550,y, 100,20);
		textField6.setFont(new Font(textField5.getFont().getFontName(), Font.BOLD, textField5.getFont().getSize()));
		textField6.setHorizontalAlignment(JTextField.CENTER);
		textField6.addKeyListener(this);
		textField6.setEditable(false);
		
		
		JLabel label1 = new JLabel("Min (included):");
		label1.setBounds(50,y*2-20, 150,20);
		textField1 = new JTextField();
		textField1.setBounds(50,y*2, 100,20);
		textField1.addKeyListener(this);
		JLabel label2 = new JLabel("Max (included):");
		label2.setBounds(200,y*2-20, 150,20); 
		textField2 = new JTextField();
		textField2.setBounds(200,y*2, 100,20);
		textField2.addKeyListener(this);
		JLabel label3 = new JLabel("Output:");
		label3.setBounds(50,y*3-20, 150,20); 
		textField3 = new JTextField();
		textField3.setBounds(50,y*3, 700,30);
		textField3.setFont(new Font(textField3.getFont().getFontName(), Font.BOLD, textField3.getFont().getSize()));
		textField3.setHorizontalAlignment(JTextField.CENTER);
		textField3.addKeyListener(this);
		textField3.setEditable(false);
		button1 = new JButton("Reset");
		button1.setBounds(50,y*4, 100,20);
		button1.addActionListener(this);
		button1.addKeyListener(this);
		button2 = new JButton("Copy Next");
		button2.setBounds(150,y*4, 100,20);
		button2.addActionListener(this);
		button2.addKeyListener(this);
		frame.getContentPane().add(label1);
		frame.getContentPane().add(label2);
		frame.getContentPane().add(label3);
		frame.getContentPane().add(label4);
		frame.getContentPane().add(label5);
		frame.getContentPane().add(label6);
		frame.getContentPane().add(textField1);
		frame.getContentPane().add(textField2);
		frame.getContentPane().add(textField3);
		frame.getContentPane().add(textField4);
		frame.getContentPane().add(textField5);
		frame.getContentPane().add(textField6);
		frame.getContentPane().add(button1);
		frame.getContentPane().add(button2);
		frame.setLayout(null);  
		frame.setVisible(true);
	}

	public void ClipBoardUpdate() {
		if(array.size()>0) {
			String s = "";
			System.out.println(counter + " " + array.size());
			if(counter < array.size()) {
				s = array.get(counter);
				StringSelection selection = new StringSelection(s);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
				UpdateTextField();
				HighlightText();
				counter++;
			}
		}
	}

	public void CreateNewArray() {
		Reset();
		int s1 = 0, s2 = 0;
		array.clear();
		counter = 0;
		try {
			s1 = Integer.parseInt(textField1.getText());
			s2 = Integer.parseInt(textField2.getText());
			for(int i = s1; i < s2+1; i++) {
				array.add(i + "");
			}
			Collections.shuffle(array);
			ClipBoardUpdate();
		}catch(Exception ex) {
			textField3.setText("Not a number!?");
			textField1.setText("");
			textField2.setText("");
		}
	}

	private void UpdateTextField() {
		String s = "";
		boolean b = true;
		for (int i = 0; i < array.size(); i++) {
			if(b) {
				s += "|";
			}else {
				s += ",";
			}
			s += " " + array.get(i) + " ";
			b = !b;
		}
		s+= "|";
		textField3.setText(s);
			}
	
	private void HighlightText() {
		Highlighter highlighter = textField3.getHighlighter();
        HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);          
        int p0 = textField3.getText().indexOf(array.get(counter),highlightCounter);
        int p1 = p0 + array.get(counter).length();
        highlightCounter = p1;
        try {
            highlighter.removeAllHighlights();
            highlighter.addHighlight(p0, p1, painter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
	}
	private void Reset() {
		array.clear();
		counter = 0;
		highlightCounter = 0;
	}
	
	private void ResetAll() {
		Reset();
		textField1.setText("");
		textField2.setText("");
		textField3.setText("");
		textField4.setText("");
		textField5.setText("");
		textField6.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button1) {
			ResetAll();
		}
		else if(e.getSource() == button2) {
			ClipBoardUpdate();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER){
			if(!textField4.getText().isEmpty()) {
				CalculateRanges();
			}
			if(!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
				CreateNewArray();				
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_C && e.isControlDown()){
			ClipBoardUpdate();
		}               
	}

	private void CalculateRanges() {
		double players = 0;
		int bye = 0;
		String s = "";
		try {
			players = Double.parseDouble(textField4.getText());
			while(((players/8)%1) != 0) {
				players++;
				bye++;
			}
			int n = (int)(players/4);
			s += 1 + " to " + n + " | " + (n+1) + " to " + n*2 +  " | " + ((n*2)+1) + " to " + n*3 + " | " + ((n*3)+1) + " to " + n*4;
			textField6.setText(bye + "");
		}catch(Exception e) {
			e.printStackTrace();
			s = "Wrong input";
		}
		textField5.setText(s);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
	
	}

}
