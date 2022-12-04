package com.mvn.simple.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mvn.simple.domain.model.RoboticMower;
import com.mvn.simple.domain.service.CustomMowerHandler;
import com.mvn.simple.domain.service.MowerHandler;

public class CollisionsMapperTest {
	
	private CollisionsMapper mapper = new CollisionsMapper();
	
	@Test
	public void givenListOfMowersHandlers_thenReturnPositionPointsCorrectly() {
		CustomMowerHandler current = CustomMowerHandler.builder()
				.mower(RoboticMower.builder()
						.currentPosition(new Point(1, 2))
						.build())
				.build();
		List<MowerHandler> mowersHandlers = new ArrayList<>();
		mowersHandlers.add(CustomMowerHandler.builder()
				.mower(RoboticMower.builder()
						.currentPosition(new Point(3, 2))
						.build())
				.build());
		mowersHandlers.add(current);
		mowersHandlers.add(CustomMowerHandler.builder()
				.mower(RoboticMower.builder()
						.currentPosition(new Point(3, 1))
						.build())
				.build());
		
		List<Point> collisionPointsExpected = Arrays.asList(new Point(3, 2), new Point(3, 1));
		assertEquals(collisionPointsExpected, mapper.toCollisions(mowersHandlers, current));
	}
}
