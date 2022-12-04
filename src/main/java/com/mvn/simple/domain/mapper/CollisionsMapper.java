package com.mvn.simple.domain.mapper;

import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;

import com.mvn.simple.domain.service.MowerHandler;

public class CollisionsMapper {

	public List<Point> toCollisions(List<MowerHandler> mowersHandlers, MowerHandler mowersHandler){
		return mowersHandlers.stream()
				.filter(mh -> mh != mowersHandler)
				.map(mh -> mh.getPosition())
				.collect(Collectors.toList());
	}
}
