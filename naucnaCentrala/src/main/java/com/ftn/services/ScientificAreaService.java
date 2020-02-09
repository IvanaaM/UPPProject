package com.ftn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.ScientificArea;
import com.ftn.repository.ScientificAreaRepository;

@Service
public class ScientificAreaService {

	@Autowired
	ScientificAreaRepository saRepository;

	public ScientificArea findByName(String fieldValue) {
	
		return saRepository.findByName(fieldValue);
	}
}
