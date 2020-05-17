package com.dmf.boot.learn.pattern.hotle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HotelContext {

	private List<Hotel> hotellist = new ArrayList<Hotel>();

	public HotelContext() {
		hotellist.addAll(Arrays.asList(
				new SevenDayHotel(),new RujiaHotel(),new HantingHotel()));
	}
	
	public Hotel perfectHotel(Calendar start,Calendar end,boolean isVip){
		Hotel prefectHotel = hotellist.get(0);
		for(int i=0;i<hotellist.size();i++){
			Hotel hotel = hotellist.get(i);
			int fee = hotel.fee(start, end, isVip);
			int level = hotel.level();
			
			int prefectFee = prefectHotel.fee(start, end, isVip);
			int prefectLevel = prefectHotel.level();
			
			if (fee<prefectFee||(fee==prefectFee && level>prefectLevel)) {
				prefectHotel = hotel;
			}
		}
		return prefectHotel;
	}
	
}
