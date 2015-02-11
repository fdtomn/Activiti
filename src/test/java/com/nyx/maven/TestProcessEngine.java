package com.nyx.maven;

import java.text.SimpleDateFormat;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

public class TestProcessEngine {

	private ProcessEngine processEngine = null;
	
	@Before
	public void before() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}

	/**
	 * 1.发布流程规则
	 * 发布成功后操作3张表:
	 * 	1.act_ge_bytearray	二进制数据表 (规则文件和图片文件)
	 * 	2.act_re_procedef 	流程定义数据表
	 * 	3.act_re_deployment	部署信息表
	 * @throws Exception
	 */
	@Test
	public void deployFlow() throws Exception {
		//1.1使用ProcessEngine获取服务对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//1.2创建发布对象
		DeploymentBuilder builder = repositoryService.createDeployment();
		//1.3指定发布的规则
		builder
			.name("请假流程")
			.addClasspathResource("workflow/holidayRequest.bpmn").addClasspathResource("workflow/holidayRequest.png");
		//1.4发布流程规则
		builder.deploy();
	}
	
	/**
	 * 2.启动流程实例
	 * 	启动成功后3张表的数据发生变化
	 * 	1.act_ru_identitylink	运行时流程人员表,任务节点参与者的相关信息
	 * 	2.act_ru_task			运行时任务节点表
	 * 	3.act_ru_execution		运行时流程执行实例表
	 * @throws Exception
	 */
	@Test
	public void startProcess() throws Exception {
		//2.1获取运行服务对象
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//2.2启动流程实例
		//key启动实例
//		runtimeService.startProcessInstanceByKey("holidayRequest");
		//id启动实例
		runtimeService.startProcessInstanceById("holidayRequest:2:5004");
	}
	
	/**
	 * 3.查询任务
	 * 	
	 * @throws Exception
	 */
	@Test
	public void queryTask() throws Exception {
		//3.1获取任务服务对象
		TaskService taskService = processEngine.getTaskService();
		//3.2查询条件
		String userId = "王五";
		//3.3创建查询对象并添加查询条件
		TaskQuery taskAssignee = taskService.createTaskQuery().taskAssignee(userId);
		//3.4返回所有任务
		List<Task> tasks = taskAssignee.list();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(Task task:tasks){
			System.out.println("ID:"+task.getId());
			System.out.println("name:"+task.getName());
			System.out.println("assignee"+task.getAssignee());
			System.out.println("createtime:"+sdf.format(task.getCreateTime()));
		}
	}
	
	/**
	 * 4.办理任务
	 * 	对应的历史数据表有相应的变化
	 * @throws Exception
	 */
	@Test
	public void completeTask() throws Exception {
		//4.1获取任务服务对象
		TaskService taskService = processEngine.getTaskService();
		
		//4.2办理条件
		String taskId = "12502";
		
		//4.3办理任务
		taskService.complete(taskId);
		
		
	}
	
	@Test
	public void historyTask() throws Exception {
//		HistoryService historyService = processEngine.getHistoryService();
//		List<HistoricDetail> datas = historyService.createHistoricDetailQuery().processInstanceId("holidayRequest:1:4").formProperties().list();
//		System.out.println("size:"+datas.size());
//		for(HistoricDetail data:datas){
//			System.out.println(data.getId());
//		}
		
//		List<HistoricIdentityLink> datas = historyService.getHistoricIdentityLinksForTask("2501");
//		System.out.println(datas.size());
	}
	
}
