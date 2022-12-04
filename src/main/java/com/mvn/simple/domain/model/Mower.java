package com.mvn.simple.domain.model;

import java.awt.Point;
import com.mvn.simple.infrastructure.util.Direction;

public interface Mower {
	void moveTo(final Point position);
	void headDirection(final Direction direction);
	Point getPosition();
	Direction getDirection();
	void printOnHold();
}
