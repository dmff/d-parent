package com.dmf.boot.learn.pattern.yuesefu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {


	private int number;
	private Person leftPerson;
	private Person rightPerson;

	
	public void callNumber(int num,Rule rule){
		if (leftPerson==this&&rightPerson==this) {
			System.out.println("我是最后一个人，我的号码是:"+number+"!");
			return;
		}
		if (num==rule.getMaxNumber()) {
			leave();
			num = 1;
		}else{
			num++;
		}

		rightPerson.callNumber(num, rule);
	}
	
	public void leave(){
		leftPerson.setRightPerson(rightPerson);
		rightPerson.setLeftPerson(leftPerson);
		System.out.println("出圈人:"+number);
	}
	
}
