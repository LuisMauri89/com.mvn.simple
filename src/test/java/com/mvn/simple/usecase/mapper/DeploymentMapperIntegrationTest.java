package com.mvn.simple.usecase.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvn.simple.domain.mapper.CollisionsMapper;
import com.mvn.simple.domain.service.CustomDeployment;
import com.mvn.simple.infrastructure.util.Direction;
import com.mvn.simple.infrastructure.util.OnInvalidMovementStrategy;
import com.mvn.simple.usecase.model.DeploymentInfo;

public class DeploymentMapperIntegrationTest {

	DeploymentInfo info;
	LocationMapper locationMapper;
	CollisionsMapper collisionsMapper;
	
	private DeploymentMapper mapper = new DeploymentMapper();
	
	@BeforeEach
	void init( ) {
	    info = DeploymentInfo.builder()
	    		.name("deployment-file-name")
	    		.topRightCoordinate("5 5")
	    		.mowersInfo(Arrays.asList("1 2 N", "LMLMLMLMM"))
	    		.build();
	    locationMapper = new LocationMapper();
	    collisionsMapper = new CollisionsMapper();
	}
	
	@Test
	public void givenCorrectInfo_thenReturnDeploymentsCorrectly() throws Exception {
		CustomDeployment deployment = mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		
		assertEquals(6, deployment.getXSize());
		assertEquals(6, deployment.getYSize());
		assertEquals(1, deployment.getMowersHandlers().size());
		assertEquals(1, (int) deployment.getMowersHandlers().get(0).getPosition().getX());
		assertEquals(2, (int) deployment.getMowersHandlers().get(0).getPosition().getY());
		assertEquals(Direction.NORTH, deployment.getMowersHandlers().get(0).getDirection());
	}
	
	@Test
	public void givenCorrectInfo_whenStrategyProvided_thenReturnDeploymentsCorrectly() throws Exception {
		info.setMowersInfo(Arrays.asList("1 2 N ATR", "LMLMLMLMM"));
		CustomDeployment deployment = mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		
		assertEquals(6, deployment.getXSize());
		assertEquals(6, deployment.getYSize());
		assertEquals(1, deployment.getMowersHandlers().size());
		assertEquals(1, (int) deployment.getMowersHandlers().get(0).getPosition().getX());
		assertEquals(2, (int) deployment.getMowersHandlers().get(0).getPosition().getY());
		assertEquals(Direction.NORTH, deployment.getMowersHandlers().get(0).getDirection());
		assertEquals(OnInvalidMovementStrategy.ALWAYS_TURN_RIGHT, 
				deployment.getMowersHandlers().get(0).getStrategy());
	}
	
	@Test
	public void givenCoordinate_whenInvalidNumberOfArguments_thenThrowException(){
		info.setTopRightCoordinate("5 ");
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenCoordinate_whenInvalidNumberString_thenThrowException(){
		info.setTopRightCoordinate("M 5");
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenMowersInfo_whenInvalidNumberOfArguments_thenThrowException(){
		info.setMowersInfo(Arrays.asList("1 2 N ATR", "LMLMLMLMM", "WRONG"));
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenMowersInfo_whenInvalidNumberString_thenThrowException(){
		info.setMowersInfo(Arrays.asList("M 2 N ATR", "LMLMLMLMM"));
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenMowersInfo_whenInvalidDirectionString_thenThrowException(){
		info.setMowersInfo(Arrays.asList("1 2 T ATR", "LMLMLMLMM"));
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenMowersInfo_whenInvalidStrategyString_thenThrowException(){
		info.setMowersInfo(Arrays.asList("1 2 N ATM", "LMLMLMLMM"));
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
}
