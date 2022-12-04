package com.mvn.simple.domain.service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mvn.simple.domain.mapper.CollisionsMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomDeployment implements Deployment {
	private String name;
	private int xSize;
	private int ySize;
	@Builder.Default
	private List<MowerHandler> mowersHandlers = new ArrayList<>();
	private CollisionsMapper collisionsMapper;

	@Override
	public void deploy() {
		mowersHandlers.forEach(mowersHandler -> {
			if (!isValidDeployment()) {
				System.out.println(
						"Deployment will be ignore due to collisions problems in the initial set of mower's positions");
				return;
			}

			List<Point> collisions = collisionsMapper.toCollisions(mowersHandlers, mowersHandler);
			mowersHandler.execute(xSize, ySize, collisions);
		});
	}
	
	/*
	 * Check if there is not collision among all the initial positions of the
	 * mowers.
	 */
	private boolean isValidDeployment() {
		Set<Point> positions = new HashSet<Point>();

		for (int i = 0; i < mowersHandlers.size(); i++) {
			boolean isNew = positions.add(mowersHandlers.get(i).getPosition());
			if (!isNew) {
				return false;
			}
		}
		return true;
	}
}
