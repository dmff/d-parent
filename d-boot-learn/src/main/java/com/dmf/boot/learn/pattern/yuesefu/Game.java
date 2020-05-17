package com.dmf.boot.learn.pattern.yuesefu;

import java.util.Scanner;

public class Game {

	private Person[] persons;
	private Rule rule;
	private Person startPerson;
	
	public void init(){
		System.out.println("-------欢迎来到约瑟夫的世界!--------");
		ruleInput();
		initPerson();
		
	}
	
	public void go(){
		System.out.println("---------约瑟夫出圈游戏开始啦! --------");
		startPerson.callNumber(1, rule);
	}
	
	public void ruleInput(){
		rule = new Rule();
		Scanner scanner = new Scanner(System.in);
		System.out.println("#请输入约瑟夫环上的总人数:");
		rule.setPersonCount(scanner.nextInt());
		System.out.println("#请输入约瑟夫环出圈叫号:");
		rule.setMaxNumber(scanner.nextInt());
		System.out.println("请输入起始叫号人的序号:");
		rule.setStartPersonNumber(scanner.nextInt());
		
		scanner.close();
	}
	
	public void initPerson(){
		int personCount = rule.getPersonCount();
		persons = new Person[personCount];
		for(int i=0;i<persons.length;i++){
			persons[i] = new Person();
			persons[i].setNumber(i+1);
		}
		
		startPerson = persons[rule.getStartPersonNumber()-1];
		
		for(int i=0;i<persons.length;i++){
			if (i==0) {
				persons[i].setLeftPerson(persons[persons.length-1]);
			}else {
				persons[i].setLeftPerson(persons[i-1]);
			}
			if (i==persons.length-1) {
				persons[i].setRightPerson(persons[0]);
			}else {
				persons[i].setRightPerson(persons[i+1]);
			}
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.init();
		game.go();
	}
}
