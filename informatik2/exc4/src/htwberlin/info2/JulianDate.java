package htwberlin.info2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.lang.Math.*;

public class JulianDate {
	
	int date;
	
	// basic algorithm to get julian date
	// provided by Wikipedia
	public JulianDate getJulianDate(int day, int month, int year) {
		
		float a = (12 - month) / 12;
		float y = year + 4800 - a;
		float m = month + 12 * a - 3;
		
		this.date = (int)(day + (153 * m + 2) / 5 + 365 * y + y/4 - y /100 + y/400 - 32045);
		
		return this;
	}
	
	// by initialising a calendar object we can get day/month/year of the current time and return it as an integer
	public int getTodayDay() {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat day = new SimpleDateFormat("dd");
		return Integer.parseInt(day.format(rightNow.getTime()));
	}
	
	public int getTodayMonth() {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat month = new SimpleDateFormat("MM");
		return Integer.parseInt(month.format(rightNow.getTime()));
	}
	
	public int getTodayYear() {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		return Integer.parseInt(year.format(rightNow.getTime()));
	}
	
	// for determing the birthday we only need day and month
	// if delta of the current day and month and the day and month of the birthday is zero its the birthday
	boolean isBirthday(int d, int m, int y) {
		
		if(getTodayDay() - d == 0 && getTodayMonth() - m == 0) {
			System.out.println("It´s your birthday!");
			return true;
		} else {
			return false;
		}
	}
	
	// by simple subtraction of the days since birth and the current amount of days we get the days the person is living
	// if daysLiving mod 100 equals zero the days the person lives ist dividable by 100
	int howOld(int d, int m, int y) {
		JulianDate julianToday = getJulianDate(getTodayDay(), getTodayMonth(), getTodayYear());
		JulianDate julianBirthday = getJulianDate(d, m, y);
		int daysLiving = julianToday.date - julianBirthday.date;
		if(daysLiving%100 == 0) {
			System.out.println("The number of days you are living is dividable by 100!");
		}
		return daysLiving;
	}
	
	// since the first day of the julianDate System ist a monday we can easily calculate teh weekdya of any julianDate by mod 7
	// the default is never returned
	String getWeekDayBirth(int d, int m, int y) {
		JulianDate julianBirthday = getJulianDate(d, m, y);
		int weekdayIndex = julianBirthday.date % 7;
		switch(weekdayIndex) {
		case 0: return "Monday";
		case 1: return "Tuesday";
		case 2: return "Wednesday";
		case 3: return "Thursday";
		case 4: return "Friday";
		case 5: return "Saturday";
		case 6: return "Sunday";
		default: return null;
		}
	}
	
	// calculates the delta of both persons days of living
	// hows older gets determined by looking up if the delta ist positiv, negativ or equal
	void howsOlder(int d1, int m1, int y1, int d2, int m2, int y2) {
		int person1 = howOld(d1, m1, y1);
		int person2 = howOld(d2, m2, y2);
		int delta = person1 - person2;
		if(delta > 0) {
			System.out.println("Person 1 ist " + delta + " days older.");
		} else if (delta < 0) {
			System.out.println("Person 2 ist " + abs(delta) + " days older.");
		} else {
			System.out.println("You are of the same age.");
		}
	}
}
