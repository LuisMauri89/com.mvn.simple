package com.mvn.simple.domain.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvn.simple.domain.model.RoboticMower;

public class CustomDeploymentTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	private CustomDeployment customDeployment;

	@BeforeEach
	public void before() {
		System.setOut(new PrintStream(outContent));

		List<MowerHandler> mowersHandlers = new ArrayList<>();
		mowersHandlers.add(CustomMowerHandler.builder()
				.mower(RoboticMower.builder().currentPosition(new Point(3, 2)).build()).build());
		mowersHandlers.add(CustomMowerHandler.builder()
				.mower(RoboticMower.builder().currentPosition(new Point(3, 1)).build()).build());
		mowersHandlers.add(CustomMowerHandler.builder()
				.mower(RoboticMower.builder().currentPosition(new Point(3, 1)).build()).build());
		customDeployment = CustomDeployment.builder().mowersHandlers(mowersHandlers).build();
	}

	@AfterEach
	public void after() {
		System.setOut(originalOut);
	}

	@Test
	public void givenInput_whenInvalidDeployment_thenSkeepAndLogWarning() {
		customDeployment.deploy();
		boolean containsSkeepWaringText = outContent.toString().contains(
				"Deployment will be ignore due to collisions problems in the initial set of mower's positions");
		assertTrue(containsSkeepWaringText);
	}
}
