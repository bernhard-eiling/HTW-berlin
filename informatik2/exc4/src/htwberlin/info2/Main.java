package htwberlin.info2;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JulianDate julian = new JulianDate();
		System.out.println(julian.getJulianDate(03,05,2012).date);
		System.out.println(julian.isBirthday(31, 05, 1986));
		System.out.println(julian.getWeekDayBirth(31, 05, 1986));
		julian.howsOlder(31, 05, 1986, 23, 04, 1990);
		
		MetricDate metric = new MetricDate(0, 0, 0, 0);
		MetricDate test = metric.julianToMetric(245123);
		System.out.println("Day: " + test.day + " | Week: " + test.week + " | Month: " + test.month + " | Year: " + test.year);
		System.out.println(metric.metricToJulian(test));
		
		test.howOldMetric(06, 06, 1984);
		System.out.println("Day: " + test.day + " | Week: " + test.week + " | Month: " + test.month + " | Year: " + test.year);
		
	}
}
