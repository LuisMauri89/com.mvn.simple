package com.mvn.simple.domain.model;

import java.awt.Point;
import com.mvn.simple.infrastructure.util.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RoboticMower implements Mower{
	private Point currentPosition;
	private Direction direction;
	
	@Override
	public void moveTo(final Point position) {
		currentPosition = position;
	}
	
	@Override
	public void headDirection(final Direction direction) {
		this.direction = direction;
		
	}
	
	@Override
	public Point getPosition() {
		return currentPosition;
	}

	@Override
	public void printOnHold() {
		System.out.println(String.format("%s %s %s", 
				(int) currentPosition.getX(), 
				(int) currentPosition.getY(), 
				direction.toString().charAt(0)));
	}
	
	
}
