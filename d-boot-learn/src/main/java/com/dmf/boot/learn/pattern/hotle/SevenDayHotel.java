package com.dmf.boot.learn.pattern.hotle;

public class SevenDayHotel extends AbstractHotel{

	public SevenDayHotel() {
		super("SevenDay",4);
		getRoomFeeStrategy().weekdayOrdinaryPrice(110);
		getRoomFeeStrategy().weekdayVipPrice(80);
		getRoomFeeStrategy().weekendOrdinaryPrice(90);
		getRoomFeeStrategy().weekendVipPrice(80);
	}

}
