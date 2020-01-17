package com.ftn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
//import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.ftn.model.FormFieldsDto;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Role;
import com.ftn.modelDTO.Login;
import com.ftn.modelDTO.LoginDTO;
import com.ftn.model.UserCustom;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;


@RestController
@RequestMapping("/user")
public class UserController {
	
	private static String processInstanceString = "";
	
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
	
	@GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {
		
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_Registracije");
		
		processInstanceString = pi.getId();

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("TaskRegistracija").singleResult();
		
		System.out.println("Task je " + task.getId());
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
				
		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	
	}
	
	@GetMapping(path = "/getTask", produces = "application/json")
    public @ResponseBody FormFieldsDto getTask() {
			
		System.out.println("Instanca je " + processInstanceString);
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceString).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), processInstanceString, properties);
	
	}
	
	@PostMapping(path = "/postNO/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postNO(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		
		runtimeService.setVariable(processInstanceString, "naucneOblasti", dto);
		
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		formService.submitTaskForm(taskId, map);
		System.out.println("Zavrsio");
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		    // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);
		
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration", dto);
		formService.submitTaskForm(taskId, map);
		//taskService.complete(taskId);
		System.out.println("Zavrsio");
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path="/confirmMail", produces="application/json")
	public String confirmNotification() {
		
		System.out.println("usao");
		
		Execution execution = runtimeService.createExecutionQuery().
				processInstanceId(processInstanceString).activityId("Prijem").singleResult();
		
		runtimeService.signal(execution.getId());
		
		System.out.println("izvrsio");
		
		return "Uspesna registracija!";
	}
	
	@PostMapping(path="/login", produces="application/json")
	public Login login(@RequestBody LoginDTO loginDTO) {
		
		System.out.println("usao login " + loginDTO.getUsername());
		
		User u = identityService.createUserQuery().userId(loginDTO.getUsername()).singleResult();
		
		if(u == null) {
			return null;
		} else {
			System.out.println(u);
			
			UserCustom user = userRepository.findByUsername(loginDTO.getUsername());
			//ako se podudaraju
			if (BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
				
				ArrayList<String> roles = new ArrayList<String>();
				for(Role role : user.getRoles()) {
					roles.add(role.getName().toString());
				}
				
			
				return new Login(user.getUsername(), roles);
			}
			
			return null;
		}
		
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
