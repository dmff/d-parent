package com.dmf.boot.learn.pattern.rabbit;

import lombok.Data;

/**
 * @author dmf
 */
@Data
public class Rabbit {


	private volatile int birthMonth;
	private RabbitHome rabbitHome;
	
	public Rabbit(int birthMonth, RabbitHome rabbitHome) {
		super();
		this.birthMonth = birthMonth;
		this.rabbitHome = rabbitHome;
	}


	public int getBirthMonth() {
		return birthMonth;
	}
    

	public RabbitHome getRabbitHome() {
		return rabbitHome;
	}

	public void giveRabbit(int currentMonth){
		if (currentMonth-this.birthMonth>=3) {
			Rabbit baby1 = new Rabbit(currentMonth, rabbitHome);
			Rabbit baby2 = new Rabbit(currentMonth, rabbitHome);
			rabbitHome.addRabbit(baby1);
			rabbitHome.addRabbit(baby2);
		}
	}
	
	public void die(int currentMonth){
		if (currentMonth-birthMonth==5) {
			rabbitHome.removeRabbit(this);
		}
	}


}
