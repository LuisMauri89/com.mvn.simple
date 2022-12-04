package com.mvn.simple.domain.service;

import java.awt.Point;
import java.util.List;

import com.mvn.simple.infrastructure.util.Direction;
import com.mvn.simple.infrastructure.util.OnInvalidMovementStrategy;

public interface MowerHandler {
	void execute(final int xSize, final int ySize, final List<Point> collisions);
	Point getPosition();
	Direction getDirection();
	OnInvalidMovementStrategy getStrategy();
}
