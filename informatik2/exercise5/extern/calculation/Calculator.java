/*
 * Calculator Interface
 *
 * Developed for "Rethinking CS101", a project of Lynn Andrea Stein's AP Group.
 * For more information, see <a href="http://www.ai.mit.edu/projects/cs101">the
 * CS101 homepage</a> or email <las@ai.mit.edu>.
 *
 * Copyright (C) 1996 Massachusetts Institute of Technology.
 * Please do not redistribute without obtaining permission.
 */
package calculation;

/**
 * This is the interface for a basic four-function calculator, as seen
 * from the perspective of its logic.
 *
 * <P>This interface defines an enumerated type, buttonIDs (including
 * 0...9, which represent themselves), an array of ButtonLabels
 * suitable for displaying on the GUI, and the three access functions
 * by which the logic can manipulate the Calculator: getButton,
 * getText, and setText.
 *
 * <P>Copyright (c) 1998 Massachusetts Institute of Technology
 *
 * @author:  Emil Sit, sit@mit.edu
 * @author:  Lynn Andrea Stein, las@ai.mit.edu
 * @version: $Id: Calculator.java,v 1.4 1998/07/24 16:37:14 tparnell Exp $
 *
 * @see CalculatorGUI
 */
public interface Calculator {
  // buttonID constants.
  // The numbers 0 through 9 should serve as buttonIDs for the respective buttons. 
  /**
   * No operation in progress.
   */
  public static final int NO_OP  = -1;

  /**
   * Calculator division.
   */
  public static final int OP_DIV = 10;

  /**
   * Calculator multiplication.
   */
  public static final int OP_MUL = 11;

  /**
   * Calculator addition.
   */
  public static final int OP_ADD = 12;

  /**
   * Calculator subtraction.
   */
  public static final int OP_SUB = 13;

  /**
   * Calculator = button.
   */
  public static final int EQUALS = 14;

  /**
   * Calculator clear button.
   */
  public static final int CLEAR  = 15;

  /**
   * ( - operning braked
   */
  public static final int OPEN = 16; 

  /**
   * ( - closing braked
   */
  public static final int CLOSE = 17;
  
  /**
   * DEL one character
   */
  public static final int ERASE = 18;

  /**
   * Special infix2postfix-button
   */
  public static final int IN2POSTFIX = 19; 

  /**
   * Special button for fraction calculation
   */
  public static final int CALC_FRACTION = 20; 
  
  /**
   * Special button for double dot
   */
  public static final int COLON = 21;
  
  /**
   * Special button for power of
   */
  public static final int POWER = 22;
  
  /**
   * One more than the biggest button index.
   */
  public static final int LAST = 23;
  
  /**
   * An array for the button's labels to deal with dispatch cleanly.
   * You can use this to get the name of the button (i.e., a String).
   */
  public static String[] ButtonLabels = new String[Calculator.LAST];
    
  /**
   * Get the next Button pressed.  The return value will be an int
   * between 0 and 9 (if the button was a number) or one of the
   * Calculator constants.
   *
   * @return the next button to be handled.
   */
  public int getButton();

  /**
   * Get the label for the given Button ID.  The argument 
   * should be an int between 0 and 9 (if the button was 
   * a number) or one of the Calculator constants, 
   * otherwise the empty string ("") will be returned.
   *
   * @return the button label as a String.
   */
  public String getButtonLabel(int buttonID);

  /**
   * Get the text currently displayed on the Calculator.
   *
   * @return the text as a String.
   */
  public String getText();

  /**
   * Set the text currently displayed on the Calculator.
   *
   * @param newText the text to be displayed.
   */
  public void setText( String newText );

}

/* Comments:
 *
 * History:
 *     $Log: Calculator.java,v $
 *     Revision 1.4  1998/07/24 16:37:14  tparnell
 *     Placate new javadoc behavior
 *
 *     Revision 1.3  1998/07/06 19:08:52  tparnell
 *     *** empty log message ***
 *
 *     Revision 1.2  1998/06/05 05:19:26  craigh
 *     added getButtonLabel() to Calculator interface.  Implemented the
 *     method in CalculatorGUI, and made use of it in ButtonHandler.
 *
 *     Revision 1.1  1998/02/26 17:25:44  tparnell
 *     Reconstruction from hard drive failure.  Everything appears intact.
 *
 *     Revision 1.3  1997/10/05 21:11:19  shong
 *     Updated for fall97, to Java 1.1
 *     changed GUI, using 1.1 Event Model
 *
 *     Revision 1.2  1997/07/16 14:15:20  tparnell
 *     *** empty log message ***
 *
 *     Revision 1.2  1996/10/04 16:20:20  las
 *     Transformed Calculator into an application and made it a package.  See
 *     STAFF_SETUP for which files are public.  To run, use Calculator.Main.
 *
 *     Specifics:
 *         Added Main.java, which starts the calculator program (both
 *     CalculatorGUI and ButtonHandler);
 *         Made Calculator an interface;
 *         Moved GUI implementation (previously in Calculator) to
 *     CalculatorGUI.
 *         Added clear button, which looks pretty gross right now.  (It can
 *     be deleted in a single line, though.)
 *
 *     Revision 1.1.1.1  1996/07/18 17:26:12  sit
 *     Import from summer 6.80s web tree
 *
 *
 */

