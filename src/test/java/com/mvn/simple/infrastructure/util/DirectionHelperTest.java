package com.mvn.simple.infrastructure.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DirectionHelperTest {
	
	@Test
	public void givenIndex_thenReturnDirectionCorrectly() {
		Direction west = DirectionHelper.nextDirection(Action.TURN_LEFT, Direction.NORTH);
		Direction south = DirectionHelper.nextDirection(Action.TURN_RIGHT, Direction.EAST);
		Direction edgeSouth = DirectionHelper.nextDirection(Action.TURN_LEFT, Direction.WEST);
		Direction edgeWest = DirectionHelper.nextDirection(Action.TURN_RIGHT, Direction.SOUTH);
		Direction north = DirectionHelper.nextDirection(Action.TURN_LEFT, Direction.EAST);
		Direction east = DirectionHelper.nextDirection(Action.TURN_LEFT, Direction.SOUTH);
		
		assertEquals(Direction.WEST, west);
		assertEquals(Direction.SOUTH, south);
		assertEquals(Direction.SOUTH, edgeSouth);
		assertEquals(Direction.WEST, edgeWest);
		assertEquals(Direction.NORTH, north);
		assertEquals(Direction.EAST, east);
	}
}
