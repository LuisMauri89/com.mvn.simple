package com.mvn.simple.usecase.mapper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mvn.simple.domain.mapper.CollisionsMapper;
import com.mvn.simple.domain.model.RoboticMower;
import com.mvn.simple.domain.service.CustomDeployment;
import com.mvn.simple.domain.service.CustomMowerHandler;
import com.mvn.simple.domain.service.MowerHandler;
import com.mvn.simple.infrastructure.util.Action;
import com.mvn.simple.infrastructure.util.OnInvalidMovementStrategy;
import com.mvn.simple.usecase.model.DeploymentInfo;

public class DeploymentMapper {

	/*
	 * Handles all the parsing from the input string into actual DTO models.
	 */
	public CustomDeployment toCustomDeployment(final DeploymentInfo info, final LocationMapper locationMapper,
			final CollisionsMapper collisionsMapper) throws Exception {
		List<String> topRightCoordinate = Arrays.asList(info.getTopRightCoordinate().split(" ")).stream()
				.map(s -> s.trim()).collect(Collectors.toList());

		if (topRightCoordinate.size() != 2) {
			throw new Exception("Invalid arguments, error reading top right coordinate");
		}

		CustomDeployment deployment = CustomDeployment.builder().name(info.getName()).collisionsMapper(collisionsMapper)
				.build();
		try {
			deployment.setXSize(Integer.parseInt(topRightCoordinate.get(0)) + 1);
			deployment.setYSize(Integer.parseInt(topRightCoordinate.get(1)) + 1);
		} catch (NumberFormatException e) {
			throw new Exception("Invalid arguments, error parsing top right coordinate", e);
		}

		deployment.setMowersHandlers(mapMowersHandlers(info, locationMapper));
		return deployment;
	}

	private List<MowerHandler> mapMowersHandlers(final DeploymentInfo info, final LocationMapper locationMapper)
			throws Exception {
		if (info.getMowersInfo().size() % 2 != 0) {
			throw new Exception("Invalid arguments, error reading mower's information");
		}

		List<MowerHandler> mowersHandlers = new ArrayList<>();
		for (int i = 0; i < info.getMowersInfo().size(); i += 2) {
			List<String> positionAndDirection = Arrays.asList(info.getMowersInfo().get(i).split(" ")).stream()
					.map(s -> s.trim()).collect(Collectors.toList());

			String mowerInstructions = info.getMowersInfo().get(i + 1);

			RoboticMower mower = RoboticMower.builder().build();
			OnInvalidMovementStrategy strategy = OnInvalidMovementStrategy.ALWAYS_HOLD_POSITION;
			try {
				mower.setCurrentPosition(new Point(Integer.parseInt(positionAndDirection.get(0)),
						Integer.parseInt(positionAndDirection.get(1))));
				mower.setDirection(locationMapper.toDirection(positionAndDirection.get(2)));

				if (positionAndDirection.size() == 4) {
					strategy = locationMapper.toStrategy(positionAndDirection.get(3));
				}
			} catch (NumberFormatException e) {
				throw new Exception("Invalid arguments, error parsing mower's position", e);
			} catch (IllegalArgumentException e) {
				throw new Exception("Invalid arguments, error parsing mower's direction", e);
			}

			List<Action> instructions = new ArrayList<>();
			for (int j = 0; j < mowerInstructions.length(); j++) {
				instructions.add(locationMapper.toAction(Character.toString(mowerInstructions.charAt(j))));
			}

			instructions.add(Action.HOLD_POSITION);
			CustomMowerHandler handler = CustomMowerHandler.builder().mower(mower).instructions(instructions)
					.strategy(strategy).build();

			mowersHandlers.add(handler);
		}

		return mowersHandlers;
	}
}
