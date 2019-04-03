package com.esprow.stock.market.simulator.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TimeFormatter {

private static final DateTimeFormatter FORMATTER;
	
	static {
		FORMATTER = DateTimeFormatter
			    .ofPattern("HH:mm:ss");
	}
	
	public static String getString(LocalDateTime dateTime) {
		return dateTime.format(FORMATTER);
	}
}
