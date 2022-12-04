package com.mvn.simple.usecase.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DeploymentInfo {
	private String name;
	private String topRightCoordinate;
	@Builder.Default
	private List<String> mowersInfo = new ArrayList();
}
