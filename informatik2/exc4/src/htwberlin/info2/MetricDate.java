package htwberlin.info2;

public class MetricDate extends JulianDate{
	
	public int day;
	public int week;
	public int month;
	public int year;
	
	MetricDate(int d, int w, int m, int y) {
		this.day = d;
		this.week = w;
		this.month = m;
		this.year = y;
	}

	// by calculating the modulo we extract the right position of a single number which represents day/week/month/year
	MetricDate julianToMetric(int julian) {
		int temp = julian;
		this.year = julian / 1000;
		temp = julian % 1000;
		this.month = temp / 100;
		temp = temp % 100;
		this.week = temp / 10;
		this.day = temp % 10;
		
		return this;
	}
	
	// by multiplying multiples of 10 we can push the single number representing the day/week/month/year to the right position in the julianDate integer
	int metricToJulian(MetricDate metric) {
		return metric.year * 1000 + metric.month * 100 + metric.week * 10 + metric.day;
	}
	
	MetricDate howOldMetric(int d, int m, int y) {
		this.julianToMetric(this.getJulianDate(d, m, y).date);
		return this;
	}
}
