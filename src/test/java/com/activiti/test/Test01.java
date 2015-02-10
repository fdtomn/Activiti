package com.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Test;

public class Test01 {

	@Test
	public void testProcessEngine() {
		//classpath 下面搜索activiti.cfg.xml,并基于配置文件构建引擎
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//		ProcessEngines.init();
//		ProcessEngines.destroy();
//		System.out.println(processEngine);
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		repositoryService.createDeployment()
			.addClasspathResource("workflow/holidayRequest.bpmn").deploy();
		
		System.out.println(repositoryService.createProcessDefinitionQuery().count());
	}

}
