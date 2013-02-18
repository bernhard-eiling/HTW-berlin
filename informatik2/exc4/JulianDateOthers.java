import java.util.*;

/**
 * The abstract data type JulianDate
 * 
 * @version 1.0 @ 15.11.2006
 * @author Tobias Preuﬂ, s0516424 - Niels Richter, s0517512
 */
public class JulianDate {

	/**
	 * Calculating the day out of a String in the form dd-mm-yyyy
	 * 
	 * @param date The date of the date to perfom on
	 * @return Day as a number
	 */
	public int stringToDay(String date) throws NumberFormatException {
		return Integer.parseInt(date.substring(0, 2));
	}
	
	/**
	 * Calculating the month out of a String in the form dd-mm-yyyy
	 * 
	 * @param date The month of the date to perfom on
	 * @return Month as a number
	 */
	public int stringToMonth(String date) throws NumberFormatException {
		return Integer.parseInt(date.substring(3, 5));
	}
	
	/**
	 * Calculating the year out of a String in the form dd-mm-yyyy
	 * 
	 * @param date The year of the date to perfom on
	 * @return Year as a number
	 */
	public int stringToYear(String date) throws NumberFormatException {
		return Integer.parseInt(date.substring(6, 10));
	}

	/**
	 * Calculates the Julian Day, based on given days, month, years
	 * based on an algorithm developed by Henry F. Fliegel and Thomas C. Van Flandern
	 * 
	 * @param day The day of the date we would calculate
	 * @param month The month of the date we would calculate
	 * @param year The year of the date we would calculate
	 * @return JulianDay, an integer number
	 * @see <a href="http://bcn.boulder.co.us/y2k/y2kbcalc.htm">Algorithm of Henry F. Fliegel and Thomas C. Van Flandern</a>
	 */
	public int calcJulian (int day, int month, int year) {
		return (1461*(year+4800+(month-14)/12))/4+(367*(month-2-12*((month -14)/12)))/12-(3*((year+4900+(month-14)/12)/100))/4+day-32075;
	}
	
	/**
	 * Handling the calculation of the JulianDay by an given date in format dd-mm-yyyy
	 * 
	 * @param date Date which should be calculated
	 * @return JulianDay, an integer number
	 */
	public int getJulian (String date) throws NumberFormatException {
		return calcJulian(stringToDay(date),stringToMonth(date),stringToYear(date));
	}
	
	/**
	 * Check if two strings are equal, here used for comparing to dates
	 * 
	 * @param firstDate First date in format dd-mm-yyyy
	 * @param secondDate Second date in format dd-mm-yyyy
	 * @return True or False
	 */
	public boolean equal(String firstDate, String secondDate) {
		return (firstDate.equals(secondDate))? true : false;
	}
	
	/**
	 * Compares two dates which is the older
	 * 
	 * @param firstDate First date in format dd-mm-yyyy
	 * @param secondDate Second date in format dd-mm-yyyy
	 * @return String, describing the proportion between the dates
	 */
	public String compare(String firstDate, String secondDate) throws NumberFormatException {
		String op = "";
		if(equal(firstDate,secondDate)) {
			op = " = ";
		} else if(getJulian(firstDate) - getJulian(secondDate) > 0) {
			op = " > ";
		} else {
			op = " < ";
		}
		return firstDate + op + secondDate;
	}
	
	/**
	 * Calculates the weekday of an given date in format dd-mm-yyyy
	 * 
	 * @param date Date which should be calculated
	 * @return Day of the week as a String
	 */
	public String getWeekday (String date) throws NumberFormatException {
		String[] weekdays = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		return weekdays[(getJulian(date) % 7)];
	}
	
	/**
	 * Initialises the Time-Engine and returning the current day
	 * 
	 * @return The current day as an String in format dd-mm-yyyy
	 */
	public String getToday () {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		java.text.SimpleDateFormat today = new java.text.SimpleDateFormat("dd-MM-yyyy");
		today.setTimeZone(TimeZone.getDefault());
		return today.format(cal.getTime());
	}
	
	/**
	 * Checks, if today is the birthday of given date
	 * 
	 * @param date Date which should be calculated
	 * @return True or False
	 */
	public boolean isBirthday (String date) throws NumberFormatException {
		int year = stringToYear(getToday());
		int month = stringToMonth(date);
		int day = stringToDay(date);
		return (getJulian(getToday()) - calcJulian (day,month,year) == 0)? true : false;
	}
	
	/**
	 * Calculates the days between the current day and a given one
	 * 
	 * @param date Date which should be calculated
	 * @return Numbers of days between current day and the given one
	 */
	public int getAge (String date) throws NumberFormatException {
		return getJulian(getToday()) - getJulian(date);
	}

	/**
	 * Main-method, setting up and catching possible exceptions
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//JulianDate julia = new JulianDate();
		String error = "The date were not in the format dd.mm.yyyy or dd-mm-yyyy!";
	    try { 
			// Check JulianDate
			//System.out.println(julia.getJulian("01-01-2001"));
			
			// Check Weekday
			// (Special-Message if today is birthday)
	    	//System.out.println(julia.isBirthday("15.11.1981"));
			//System.out.println(julia.getWeekday("01-01-2001"));
			
			//System.out.println(julia.compare("01-01-2001","01-01-2001"));
			
			// Who is older?
			//System.out.println(julia.compare("01-01-2001","01-01-2001"));
			
	    } 
	    catch (StringIndexOutOfBoundsException ex) { 
	    		System.out.print(error); 
	    }
	    catch (NumberFormatException ex) { 
	    		System.out.print(error); 
		} 
	}
}
