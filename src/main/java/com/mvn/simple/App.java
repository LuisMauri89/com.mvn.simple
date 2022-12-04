package com.mvn.simple;

import com.mvn.simple.domain.Factory;
import com.mvn.simple.domain.mapper.CollisionsMapper;
import com.mvn.simple.usecase.FactoryBuilder;
import com.mvn.simple.usecase.mapper.DeploymentMapper;
import com.mvn.simple.usecase.mapper.LocationMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	// Dependencies to be injected
    	DeploymentMapper deploymentMapper = new DeploymentMapper();
    	LocationMapper locationMapper = new LocationMapper();
    	CollisionsMapper collisionsMapper = new CollisionsMapper();
    	
        // Factory factory = FactoryBuilder.buildFactory(args[0], deploymentMapper, locationMapper);
    	Factory factory = FactoryBuilder.buildFactory(
    			"/home/mauri/cases", 
    			deploymentMapper, 
    			locationMapper, 
    			collisionsMapper);
        factory.runDeployments();
    }
}
