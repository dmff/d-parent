package com.dmf.boot.learn.pattern.hotle;

public class HantingHotel extends AbstractHotel{

	public HantingHotel() {
		super("hanting",5);
		getRoomFeeStrategy().weekdayOrdinaryPrice(220);
		getRoomFeeStrategy().weekdayVipPrice(100);
		getRoomFeeStrategy().weekendOrdinaryPrice(150);
		getRoomFeeStrategy().weekendVipPrice(90);
	}

}
