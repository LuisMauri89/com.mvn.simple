package com.mvn.simple.infrastructure.util;

import java.util.Arrays;
import java.util.List;

public class DirectionHelper {
	public static Direction nextDirection(final Action turn, final Direction direction) {
		List<Direction> directions = Arrays.asList(
				Direction.WEST, 
				Direction.NORTH, 
				Direction.EAST, 
				Direction.SOUTH);
		
		int current = directions.indexOf(direction);
		int next = turn == Action.TURN_LEFT ? -1 : 1;
		return directions.get((current + next + 4) % 4);
	}
}
