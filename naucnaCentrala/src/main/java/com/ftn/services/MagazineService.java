package com.ftn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.repository.MagazineRepository;

@Service
public class MagazineService {
	
	@Autowired
	MagazineRepository magazineRepository;

}
