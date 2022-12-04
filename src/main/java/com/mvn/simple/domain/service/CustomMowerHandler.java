package com.mvn.simple.domain.service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.mvn.simple.domain.model.Mower;
import com.mvn.simple.infrastructure.util.Action;
import com.mvn.simple.infrastructure.util.Direction;
import com.mvn.simple.infrastructure.util.DirectionHelper;
import com.mvn.simple.infrastructure.util.OnInvalidMovementStrategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomMowerHandler implements MowerHandler {
	private Mower mower;
	@Builder.Default
	private List<Action> instructions = new ArrayList<>();
	@Builder.Default
	private OnInvalidMovementStrategy strategy = OnInvalidMovementStrategy.ALWAYS_HOLD_POSITION;
	
	@Override
	public void execute(final int xSize, final int ySize, final List<Point> collisions) {
		for(int i = 0; i < instructions.size(); i++) {
			Action instructionToFollow = instructions.get(i);
			
			while (instructionToFollow != Action.HOLD_POSITION 
					&& !isValidInstruction(instructions.get(i), 
							mower.getPosition(), 
							mower.getDirection(), 
							collisions, 
							xSize, ySize)) {
				
				Action onStrategy = followStrategy();
				if(onStrategy == Action.HOLD_POSITION) {
					instructionToFollow = Action.HOLD_POSITION;
				}else {
					mower.headDirection(DirectionHelper.nextDirection(
							onStrategy, 
							mower.getDirection()));
				}
			}
			
			if(instructionToFollow == Action.HOLD_POSITION) {
				mower.printOnHold();
				break;
			}
			
			if(instructionToFollow == Action.MOVE_FORWARD) {
				mower.moveTo(potentialMove(mower.getPosition(), mower.getDirection()));
			}else {
				mower.headDirection(DirectionHelper.nextDirection(
						instructionToFollow, 
						mower.getDirection()));
			}
		}
	}
	
	@Override
	public Point getPosition() {
		return mower.getPosition();
	}
	
	@Override
	public Direction getDirection() {
		return mower.getDirection();
	}
	
	private boolean isValidInstruction(
			final Action instruction, 
			final Point position, 
			final Direction direction, 
			final List<Point> collisions, 
			final int xSize, 
			final int ySize) {
		return isInRange(instruction, 
				position, 
				direction, 
				xSize, ySize) 
				&& isCollisionSave(instruction, 
						position, 
						direction, 
						collisions);
	}
	
	private boolean isInRange(
			final Action instruction, 
			final Point position, 
			final Direction direction, 
			final int xSize, 
			final int ySize) {
		if(instruction == Action.MOVE_FORWARD) {
			switch (direction) {
			case NORTH:
				return position.getY() + 1 <= ySize - 1;
			case EAST:
				return position.getX() + 1 <= xSize - 1;
			case SOUTH:
				return position.getY() - 1 >= 0;
			case WEST:
				return position.getX() - 1 >= 0;
			}
		}
		
		return true;
	}
	
	private boolean isCollisionSave(
			final Action instruction, 
			final Point position, 
			final Direction direction, 
			final List<Point> collisions) {
		if(instruction == Action.MOVE_FORWARD) {
			Point potentialMove = potentialMove(position, direction);
			return !collisions.contains(potentialMove);
		}
		
		return true;
	}
	
	private Point potentialMove(final Point position, final Direction direction) {
		int x = (int) position.getX();
		int y = (int) position.getY();
		
		switch (direction) {
		case NORTH:
			return new Point(x, y + 1);
		case EAST:
			return new Point(x + 1, y);
		case SOUTH:
			return new Point(x, y - 1);
		case WEST:
			return new Point(x - 1, y);
		default:
			return null;
		}
	}
	
	private Action followStrategy() {
		switch (strategy) {
		case ALWAYS_TURN_LEFT:
			return Action.TURN_LEFT;
		case ALWAYS_TURN_RIGHT:
			return Action.TURN_RIGHT;
		default:
			return Action.HOLD_POSITION;
		}
	}
}
