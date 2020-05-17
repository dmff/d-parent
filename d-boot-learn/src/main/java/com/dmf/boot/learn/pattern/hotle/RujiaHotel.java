package com.dmf.boot.learn.pattern.hotle;

public class RujiaHotel extends AbstractHotel{

	public RujiaHotel() {
		super("rujia",3);
		getRoomFeeStrategy().weekdayOrdinaryPrice(160);
		getRoomFeeStrategy().weekdayVipPrice(110);
		getRoomFeeStrategy().weekendOrdinaryPrice(60);
		getRoomFeeStrategy().weekendVipPrice(50);
	}

}
