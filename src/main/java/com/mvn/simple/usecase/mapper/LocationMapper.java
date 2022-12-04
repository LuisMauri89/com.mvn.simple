package com.mvn.simple.usecase.mapper;

import com.mvn.simple.infrastructure.util.Action;
import com.mvn.simple.infrastructure.util.Direction;
import com.mvn.simple.infrastructure.util.OnInvalidMovementStrategy;

/*
 * Helpers to parse input string into DTO properties.
 */
public class LocationMapper {

	public Action toAction(final String instruction) {
		switch (instruction) {
		case "L":
			return Action.TURN_LEFT;
		case "R":
			return Action.TURN_RIGHT;
		case "M":
			return Action.MOVE_FORWARD;
		default:
			throw new IllegalArgumentException("Error unexpected value: " + instruction);
		}
	}

	public Direction toDirection(final String direction) {
		switch (direction) {
		case "N":
			return Direction.NORTH;
		case "E":
			return Direction.EAST;
		case "S":
			return Direction.SOUTH;
		case "W":
			return Direction.WEST;
		default:
			throw new IllegalArgumentException("Error unexpected value: " + direction);
		}
	}

	public OnInvalidMovementStrategy toStrategy(final String strategy) {
		switch (strategy) {
		case "ATL":
			return OnInvalidMovementStrategy.ALWAYS_TURN_LEFT;
		case "ATR":
			return OnInvalidMovementStrategy.ALWAYS_TURN_RIGHT;
		case "AHP":
			return OnInvalidMovementStrategy.ALWAYS_HOLD_POSITION;
		default:
			throw new IllegalArgumentException("Error unexpected value: " + strategy);
		}
	}
}
