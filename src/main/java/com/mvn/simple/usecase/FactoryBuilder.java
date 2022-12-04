package com.mvn.simple.usecase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.mvn.simple.domain.Factory;
import com.mvn.simple.domain.mapper.CollisionsMapper;
import com.mvn.simple.domain.service.Deployment;
import com.mvn.simple.usecase.mapper.DeploymentMapper;
import com.mvn.simple.usecase.mapper.LocationMapper;
import com.mvn.simple.usecase.model.DeploymentInfo;

public class FactoryBuilder {

	public static Factory buildFactory(
			final String deploymentsPath, 
			final DeploymentMapper deploymentMapper,
			final LocationMapper locationMapper, 
			final CollisionsMapper collisionsMapper) throws Exception {
		List<DeploymentInfo> deploymentsInfo = new ArrayList<>();

		File deploymentsDirectory = new File(deploymentsPath);
		File[] deploymentFiles = deploymentsDirectory.listFiles();

		for (int i = 0; i < deploymentFiles.length; i++) {
			if (deploymentFiles[i].isFile()) {
				DeploymentInfo info = DeploymentInfo.builder().build();
				info.setName(deploymentFiles[i].getName());
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(deploymentFiles[i]));
					String line = reader.readLine();

					if (line != null) {
						info.setTopRightCoordinate(line);
					}

					line = reader.readLine();
					while (line != null) {
						info.getMowersInfo().add(line);
						line = reader.readLine();
					}

					deploymentsInfo.add(info);
					reader.close();
				} catch (IOException e) {
					throw new Exception("Error reading input file", e);
				}
			}
		}
		
		List<Deployment> customDeployments = new ArrayList<>();
		for(int i = 0; i < deploymentsInfo.size(); i++) {
			try {
				customDeployments.add(deploymentMapper.toCustomDeployment(
						deploymentsInfo.get(i), 
						locationMapper, 
						collisionsMapper));
			} catch (Exception e) {
				throw new Exception("Error parsing input file | " + e.getMessage(), e);
			}
		}

		return Factory.builder()
				.deployments(customDeployments)
				.build();
	}
}
