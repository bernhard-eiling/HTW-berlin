package eiling.info2.exersice7;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A graphical user interface for the calculator. No calculation is being done
 * here. This class is responsible just for putting up the display on screen. It
 * then refers to the "CalcEngine" to do all the real work.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class UserInterface implements ActionListener {
	private CalcEngine calc;
	private boolean showingAuthor;

	private JFrame frameDec;
	private JFrame frameHex;
	private JTextField display;
	private JTextField displayHex;
	private JLabel status;

	private boolean hex = false;

	/**
	 * Create a user interface.
	 * 
	 * @param engine
	 *            The calculator engine.
	 */
	public UserInterface(CalcEngine engine) {
		calc = engine;
		showingAuthor = true;
		makeFrameDec();
		makeFrameHex();
		frameDec.setVisible(true);
		frameHex.setVisible(false);
	}

	/**
	 * Set the visibility of the interface.
	 * 
	 * @param visible
	 *            true if the interface is to be made visible, false otherwise.
	 */
	public void setVisible(boolean visible) {
		frameDec.setVisible(visible);
	}

	public void setHex(boolean hex) {
		this.hex = hex;
	}
	/**
	 * Make the frame for the user interface.
	 */
	private void makeFrameDec() {
		frameDec = new JFrame(calc.getTitle());

		JPanel contentPane = (JPanel) frameDec.getContentPane();
		contentPane.setLayout(new BorderLayout(8, 8));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		display = new JTextField();
		contentPane.add(display, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(4, 5));
		addButton(buttonPanel, "7");
		addButton(buttonPanel, "8");
		addButton(buttonPanel, "9");
		addButton(buttonPanel, "C");
		addButton(buttonPanel, "*");

		addButton(buttonPanel, "4");
		addButton(buttonPanel, "5");
		addButton(buttonPanel, "6");
		addButton(buttonPanel, "?");
		addButton(buttonPanel, "/");

		addButton(buttonPanel, "1");
		addButton(buttonPanel, "2");
		addButton(buttonPanel, "3");
		buttonPanel.add(new JLabel(" "));
		addButton(buttonPanel, "^");

		addButton(buttonPanel, "0");
		addButton(buttonPanel, "+");
		addButton(buttonPanel, "-");
		addButton(buttonPanel, "=");
		addButton(buttonPanel, "HEX");

		contentPane.add(buttonPanel, BorderLayout.CENTER);

		status = new JLabel(calc.getAuthor());
		contentPane.add(status, BorderLayout.SOUTH);

		frameDec.pack();
	}

	private void makeFrameHex() {
		frameHex = new JFrame(calc.getTitle());

		JPanel contentPaneHex = (JPanel) frameHex.getContentPane();
		contentPaneHex.setLayout(new BorderLayout(8, 8));
		contentPaneHex.setBorder(new EmptyBorder(10, 10, 10, 10));

		displayHex = new JTextField();
		contentPaneHex.add(displayHex, BorderLayout.NORTH);

		JPanel buttonPanelHex = new JPanel(new GridLayout(6, 5));

		addButton(buttonPanelHex, "A");
		addButton(buttonPanelHex, "B");
		addButton(buttonPanelHex, "C");
		buttonPanelHex.add(new JLabel(" "));
		buttonPanelHex.add(new JLabel(" "));

		addButton(buttonPanelHex, "D");
		addButton(buttonPanelHex, "E");
		addButton(buttonPanelHex, "F");
		buttonPanelHex.add(new JLabel(" "));
		buttonPanelHex.add(new JLabel(" "));

		addButton(buttonPanelHex, "7");
		addButton(buttonPanelHex, "8");
		addButton(buttonPanelHex, "9");
		addButton(buttonPanelHex, "C");
		addButton(buttonPanelHex, "*");

		addButton(buttonPanelHex, "4");
		addButton(buttonPanelHex, "5");
		addButton(buttonPanelHex, "6");
		addButton(buttonPanelHex, "?");
		addButton(buttonPanelHex, "/");

		addButton(buttonPanelHex, "1");
		addButton(buttonPanelHex, "2");
		addButton(buttonPanelHex, "3");
		buttonPanelHex.add(new JLabel(" "));
		addButton(buttonPanelHex, "^");

		addButton(buttonPanelHex, "0");
		addButton(buttonPanelHex, "+");
		addButton(buttonPanelHex, "-");
		addButton(buttonPanelHex, "=");
		addButton(buttonPanelHex, "DEC");

		contentPaneHex.add(buttonPanelHex, BorderLayout.CENTER);

		status = new JLabel(calc.getAuthor());
		contentPaneHex.add(status, BorderLayout.SOUTH);

		frameHex.pack();
	}

	/**
	 * Add a button to the button panel.
	 * 
	 * @param panel
	 *            The panel to receive the button.
	 * @param buttonText
	 *            The text for the button.
	 */
	private void addButton(Container panel, String buttonText) {
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		panel.add(button);
	}

	/**
	 * An interface action has been performed. Find out what it was and handle
	 * it.
	 * 
	 * @param event
	 *            The event that has occured.
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if (command.equals("0") || command.equals("1") || command.equals("2")
				|| command.equals("3") || command.equals("4")
				|| command.equals("5") || command.equals("6")
				|| command.equals("7") || command.equals("8")
				|| command.equals("9")) {
			int number = Integer.parseInt(command);
			calc.numberPressed(number);
		} else if (command.equals("+")) {
			calc.plus();
		} else if (command.equals("-")) {
			calc.minus();
		} else if (command.equals("*")) {
			calc.multi();
		} else if (command.equals("/")) {
			calc.div();
		} else if (command.equals("=")) {
			calc.equals();
		} else if (command.equals("^")) {
			calc.power();
		} else if (command.equals("C")) {
			calc.clear();
		} else if (command.equals("?")) {
			showInfo();
		} else if (command.equals("HEX")) {
			this.setHex(true);
			calc.setBase(16);
			calc.clear();
			frameDec.setVisible(false);
			frameHex.setVisible(true);
			
		} else if (command.equals("DEC")) {
			this.setHex(false);
			calc.setBase(10);
			calc.clear();
			frameHex.setVisible(false);
			frameDec.setVisible(true);

		} else if (command.equals("A")) {
			calc.numberPressed(10);
		} else if (command.equals("B")) {
			calc.numberPressed(11);
		} else if (command.equals("C")) {
			calc.numberPressed(12);
		} else if (command.equals("D")) {
			calc.numberPressed(13);
		} else if (command.equals("E")) {
			calc.numberPressed(14);
		} else if (command.equals("F")) {
			calc.numberPressed(15);
		}

		// else unknown command.

		redisplay();
	}

	/**
	 * Update the interface display to show the current value of the calculator.
	 */
	private void redisplay() {
		System.out.println(calc.getDisplayValue());
		if(hex == false) {
			display.setText(calc.getDisplayValue());
		} else {
			displayHex.setText(calc.getDisplayValue());
		}
	}

	/**
	 * Toggle the info display in the calculator's status area between the
	 * author and version information.
	 */
	private void showInfo() {
		if (showingAuthor)
			status.setText(calc.getVersion());
		else
			status.setText(calc.getAuthor());

		showingAuthor = !showingAuthor;
	}
}
