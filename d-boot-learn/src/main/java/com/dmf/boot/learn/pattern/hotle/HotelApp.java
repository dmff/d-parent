package com.dmf.boot.learn.pattern.hotle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class HotelApp {

	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		String[] strs = line.split("\\,");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDate = strs[0];
		String endDate = strs[1];
		boolean isVip =Boolean.parseBoolean(strs[2]);
		
		Calendar start = Calendar.getInstance();
		start.setTime(dateFormat.parse(startDate));
		Calendar end = Calendar.getInstance();
		end.setTime(dateFormat.parse(endDate));
		
		HotelContext context = new HotelContext();
		Hotel perfectHotel = context.perfectHotel(start, end, isVip);
		
		System.out.println(perfectHotel);
		scanner.close();
	}
}
