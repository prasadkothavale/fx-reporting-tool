package config;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public interface Constants {

	final List<String> ARAB_CURRENCY = Arrays.asList("AED", "SAR");
	final List<Integer> GLOBAL_WEEKENDS = Arrays.asList(Calendar.SATURDAY, Calendar.SUNDAY);
	final List<Integer> ARAB_WEEKENDS = Arrays.asList(Calendar.SATURDAY, Calendar.FRIDAY);
	
	final String DATE_FORMAT = "dd/MM/yy";
	final String CSV_SEPARATOR = ",";
	
}
