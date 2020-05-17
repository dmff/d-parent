package com.dmf.boot.learn.pattern.hotle;

import java.util.Calendar;

public class AbstractHotel implements Hotel{
	
	private int level;
	
	private String name;
	private RoomFeeStrategy roomFeeStrategy = new RoomFeeStrategy();
	public AbstractHotel( String name,int level) {
		super();
		this.level = level;
		this.name = name;
	}


	@Override
	public int level() {
		return level;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public RoomFeeStrategy getRoomFeeStrategy() {
		return roomFeeStrategy;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	@Override
	public int fee(Calendar start, Calendar end, boolean isVip) {
		return roomFeeStrategy.fee(start, end, isVip);
	}
    
	@Override
	public String toString() {
		return " Hotel [level=" + level + ", name=" + name + "]";
	}
	
}
