package com.ftn.controller;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import com.ftn.model.FormFieldsDto;
import com.ftn.model.FormSubmissionDto;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;

@RestController
@RequestMapping("/editor")
public class EditorController {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	
	@GetMapping(path="/createMagazine", produces="application/json") 
	public @ResponseBody FormFieldsDto getTask() {
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("ProcessKreiranjaCasopisa");
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		System.out.println("Task je " + task.getId());
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
				
		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	
	}
	
	@PostMapping(path = "/post/{taskId}/{type}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId, @PathVariable String type) {
		
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		System.out.println("Ovo je tip " + type);
		System.out.println("Ovo je proces " + taskId);
	
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, type, dto);
		formService.submitTaskForm(taskId, map);
		//taskService.complete(taskId);
		System.out.println("Zavrsio");
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/getTask/{procIn}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskNO(@PathVariable String procIn) {
			
		
		Task task = taskService.createTaskQuery().processInstanceId(procIn).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), procIn, properties);
	
	}
	
	@GetMapping(path = "/get/{procIn}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTask(String procIn) {
	System.out.println("helo");
		Task task = taskService.createTaskQuery().processInstanceId(procIn).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), procIn, properties);
	
	}
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			System.out.println(temp.getFieldId());
			System.out.println(temp.getFieldValue());
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	

}
