package com.mvn.simple.usecase.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mvn.simple.infrastructure.util.Action;

public class LocationMapperTest {

	private LocationMapper mapper = new LocationMapper();

	@Test
	public void givenInputString_thenReturnActionCorrectly() {

		assertEquals(Action.TURN_LEFT, mapper.toAction("L"));
		assertEquals(Action.TURN_RIGHT, mapper.toAction("R"));
		assertEquals(Action.MOVE_FORWARD, mapper.toAction("M"));
	}

	@Test
	public void givenInvalidInputString_thenThrowException() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			assertEquals(Action.MOVE_FORWARD, mapper.toAction("T"));
		});
	}
}
