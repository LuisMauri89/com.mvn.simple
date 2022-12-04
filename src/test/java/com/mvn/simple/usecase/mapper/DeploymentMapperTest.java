package com.mvn.simple.usecase.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mvn.simple.domain.mapper.CollisionsMapper;
import com.mvn.simple.domain.service.CustomDeployment;
import com.mvn.simple.infrastructure.util.Action;
import com.mvn.simple.infrastructure.util.Direction;
import com.mvn.simple.infrastructure.util.OnInvalidMovementStrategy;
import com.mvn.simple.usecase.model.DeploymentInfo;

@ExtendWith(MockitoExtension.class)
public class DeploymentMapperTest {
	
	@Mock
	DeploymentInfo info;
	@Mock
	LocationMapper locationMapper;
	@Mock
	CollisionsMapper collisionsMapper;
	
	private DeploymentMapper mapper = new DeploymentMapper();
	
	@Test
	public void givenCorrectInfo_thenReturnDeploymentsCorrectly() throws Exception {
		when(locationMapper.toAction("L")).thenReturn(Action.TURN_LEFT);
	    when(locationMapper.toAction("M")).thenReturn(Action.HOLD_POSITION);
	    when(locationMapper.toDirection("N")).thenReturn(Direction.NORTH);
		when(info.getName()).thenReturn("deployment-file-name");
		when(info.getTopRightCoordinate()).thenReturn("5 5");
		when(info.getMowersInfo()).thenReturn(Arrays.asList("1 2 N", "LMLMLMLMM"));
		
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
		when(locationMapper.toAction("L")).thenReturn(Action.TURN_LEFT);
	    when(locationMapper.toAction("M")).thenReturn(Action.HOLD_POSITION);
	    when(locationMapper.toDirection("N")).thenReturn(Direction.NORTH);
		when(info.getName()).thenReturn("deployment-file-name");
		when(info.getTopRightCoordinate()).thenReturn("5 5");
		when(info.getMowersInfo()).thenReturn(Arrays.asList("1 2 N ATR", "LMLMLMLMM"));
		when(locationMapper.toStrategy("ATR")).thenReturn(OnInvalidMovementStrategy.ALWAYS_TURN_RIGHT);
		
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
		when(info.getTopRightCoordinate()).thenReturn("5 5 7");
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenCoordinate_whenInvalidNumberString_thenThrowException(){
		when(info.getName()).thenReturn("deployment-file-name");
		when(info.getTopRightCoordinate()).thenReturn("5 M");
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenMowersInfo_whenInvalidNumberOfArguments_thenThrowException(){
		when(info.getName()).thenReturn("deployment-file-name");
		when(info.getTopRightCoordinate()).thenReturn("5 5");
		when(info.getMowersInfo()).thenReturn(Arrays.asList("1 2 N ATR", "LMLMLMLMM", "WRONG"));
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
	
	@Test
	public void givenMowersInfo_whenInvalidNumberString_thenThrowException(){
		when(info.getName()).thenReturn("deployment-file-name");
		when(info.getTopRightCoordinate()).thenReturn("5 5");
		when(info.getMowersInfo()).thenReturn(Arrays.asList("M 2 N ATR", "LMLMLMLMM"));
		
		Assertions.assertThrows(Exception.class, () -> {
			mapper.toCustomDeployment(info, locationMapper, collisionsMapper);
		});
	}
}
