package com.dmf.boot.learn.pattern.rabbit;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author dmf
 */
public class RabbitHome {

	private Set<Rabbit> rabbits = new CopyOnWriteArraySet<>();
//	private Set<Rabbit> rabbits = new HashSet<>();

	public RabbitHome() {
		addRabbit(new Rabbit(1, this));
	}
	
	public void addRabbit(Rabbit rabbit){
		rabbits.add(rabbit);
	}
	
	public void removeRabbit(Rabbit rabbit){
		rabbits.remove(rabbit);
	}
	
	public int getTotalRabbitCount(int current){
		for(int i=1;i<=current;i++){
			Iterator<Rabbit> it = rabbits.iterator();
			while(it.hasNext()){
				Rabbit rabbit = it.next();
				rabbit.giveRabbit(i);
				rabbit.die(i);
			}
		}
		return rabbits.size();
	}

	public static void main(String[] args) {
		RabbitHome rabbitHome = new RabbitHome();
		int current = 5;
		int total = rabbitHome.getTotalRabbitCount(current);
		System.out.println(current+"月的兔子总数:"+total);
	}
}
