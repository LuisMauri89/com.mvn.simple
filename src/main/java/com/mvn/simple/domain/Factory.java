package com.mvn.simple.domain;

import java.util.ArrayList;
import java.util.List;
import com.mvn.simple.domain.service.Deployment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Factory {
	@Builder.Default
	private List<Deployment> deployments = new ArrayList<>();

	/*
	 * Each deployment represents a simulation of a single green grass plateau with
	 * its group of mowers.
	 */
	public void runDeployments() {
		deployments.forEach(deployment -> { 
			deployment.deploy();
			System.out.println(" ");
		});
	}
}
