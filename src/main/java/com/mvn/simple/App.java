package com.mvn.simple;

import com.mvn.simple.domain.Factory;
import com.mvn.simple.domain.mapper.CollisionsMapper;
import com.mvn.simple.usecase.FactoryBuilder;
import com.mvn.simple.usecase.mapper.DeploymentMapper;
import com.mvn.simple.usecase.mapper.LocationMapper;

/**
 * Application entry point
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		// Dependencies to be injected
		DeploymentMapper deploymentMapper = new DeploymentMapper();
		LocationMapper locationMapper = new LocationMapper();
		CollisionsMapper collisionsMapper = new CollisionsMapper();

		/*
		 * Uses the first argument passed to the application as the path to locate a
		 * folder with files containing the cases to run.
		 */
		Factory factory = FactoryBuilder.buildFactory(args[0], deploymentMapper, locationMapper, collisionsMapper);

		/*
		 * Uncomment this to run directly from here (in that case comment previous
		 * line). Replace below the path to the parent folder of the cases.
		 */
		// Factory factory =
		// FactoryBuilder.buildFactory("/absolute/path/to/the/cases/folder",
		// deploymentMapper, locationMapper, collisionsMapper);

		factory.runDeployments();
	}
}
