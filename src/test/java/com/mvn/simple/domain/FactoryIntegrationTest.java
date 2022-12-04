package com.mvn.simple.domain;

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
import com.mvn.simple.domain.mapper.CollisionsMapper;
import com.mvn.simple.domain.model.RoboticMower;
import com.mvn.simple.domain.service.CustomDeployment;
import com.mvn.simple.domain.service.CustomMowerHandler;
import com.mvn.simple.domain.service.Deployment;
import com.mvn.simple.domain.service.MowerHandler;
import com.mvn.simple.infrastructure.util.Action;
import com.mvn.simple.infrastructure.util.Direction;

public class FactoryIntegrationTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	List<Deployment> deployments;
	Factory factory;

	@BeforeEach
	public void before() {
		System.setOut(new PrintStream(outContent));

		List<MowerHandler> mowersHandlers = new ArrayList<>();
		mowersHandlers.add(CustomMowerHandler.builder()
				.mower(RoboticMower.builder().currentPosition(new Point(1, 2))
						.direction(Direction.NORTH).build())
				.instructions(Arrays.asList(
						Action.TURN_LEFT, Action.MOVE_FORWARD, Action.TURN_LEFT, Action.MOVE_FORWARD,
						Action.TURN_LEFT, Action.MOVE_FORWARD, Action.TURN_LEFT, Action.MOVE_FORWARD, 
						Action.MOVE_FORWARD, Action.HOLD_POSITION))
				.build());
		mowersHandlers.add(CustomMowerHandler.builder()
				.mower(RoboticMower.builder().currentPosition(new Point(3, 3))
						.direction(Direction.EAST).build())
				.instructions(Arrays.asList(
						Action.MOVE_FORWARD, Action.MOVE_FORWARD, Action.TURN_RIGHT, Action.MOVE_FORWARD,
						Action.MOVE_FORWARD, Action.TURN_RIGHT, Action.MOVE_FORWARD, Action.TURN_RIGHT, 
						Action.TURN_RIGHT, Action.MOVE_FORWARD, Action.HOLD_POSITION))
				.build());

		deployments = Arrays.asList(CustomDeployment.builder()
				.xSize(6)
				.ySize(6)
				.mowersHandlers(mowersHandlers)
				.collisionsMapper(new CollisionsMapper())
				.build());
		factory = Factory.builder().deployments(deployments).build();
	}

	@AfterEach
	public void after() {
		System.setOut(originalOut);
	}

	@Test
	public void givenInput_thenLogOutputCorrectly() {
		factory.runDeployments();
		boolean containsMowers1PositionText = outContent.toString().contains("1 3 N");
		boolean containsMowers2PositionText = outContent.toString().contains("5 1 E");
		assertTrue(containsMowers1PositionText);
		assertTrue(containsMowers2PositionText);
	}
}
