package com.mvn.simple.domain.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvn.simple.domain.model.RoboticMower;
import com.mvn.simple.infrastructure.util.Action;
import com.mvn.simple.infrastructure.util.Direction;
import com.mvn.simple.infrastructure.util.OnInvalidMovementStrategy;

public class CustomMowerHandlerTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	private RoboticMower mower;
	private List<Action> instructions;
	private CustomMowerHandler customMowerHandler;

	@BeforeEach
	public void before() {
		System.setOut(new PrintStream(outContent));
		mower = RoboticMower.builder()
				.currentPosition(new Point(1, 2))
				.direction(Direction.NORTH)
				.build();
		instructions = Arrays.asList(Action.TURN_LEFT, Action.MOVE_FORWARD, Action.TURN_LEFT, Action.MOVE_FORWARD,
				Action.TURN_LEFT, Action.MOVE_FORWARD, Action.MOVE_FORWARD, Action.HOLD_POSITION);

		customMowerHandler = CustomMowerHandler.builder().mower(mower).instructions(instructions)
				.strategy(OnInvalidMovementStrategy.ALWAYS_HOLD_POSITION).build();
	}
	
	@AfterEach
	public void after() {
		System.setOut(originalOut);
	}
	
	@Test
	public void givenInput_thenLogOutputCorrectly() {
		customMowerHandler.execute(6, 6, new ArrayList<>());
		boolean containsPositionText = outContent.toString().contains(
				"2 1 E");
		assertTrue(containsPositionText);
	}
	
	@Test
	public void givenInvalidInput_whenStrategyTurnLeft_thenLogOutputCorrectly() {
		mower = RoboticMower.builder()
				.currentPosition(new Point(5, 5))
				.direction(Direction.NORTH)
				.build();
		instructions = Arrays.asList(Action.MOVE_FORWARD, Action.TURN_LEFT, Action.MOVE_FORWARD,
				Action.MOVE_FORWARD, Action.MOVE_FORWARD, Action.HOLD_POSITION);

		customMowerHandler = CustomMowerHandler.builder().mower(mower).instructions(instructions)
				.strategy(OnInvalidMovementStrategy.ALWAYS_TURN_LEFT).build();
		
		customMowerHandler.execute(6, 6, new ArrayList<>());
		boolean containsPositionText = outContent.toString().contains(
				"4 2 S");
		assertTrue(containsPositionText);
	}
	
	@Test
	public void givenCollision_whenStrategyTurnLeft_thenLogOutputCorrectly() {
		mower = RoboticMower.builder()
				.currentPosition(new Point(3, 3))
				.direction(Direction.NORTH)
				.build();
		instructions = Arrays.asList(Action.MOVE_FORWARD, Action.TURN_LEFT, Action.MOVE_FORWARD,
				Action.MOVE_FORWARD, Action.MOVE_FORWARD, Action.HOLD_POSITION);

		customMowerHandler = CustomMowerHandler.builder().mower(mower).instructions(instructions)
				.strategy(OnInvalidMovementStrategy.ALWAYS_TURN_LEFT).build();
		
		customMowerHandler.execute(6, 6, Arrays.asList(new Point(3, 4)));
		boolean containsPositionText = outContent.toString().contains(
				"2 0 S");
		assertTrue(containsPositionText);
	}
}
