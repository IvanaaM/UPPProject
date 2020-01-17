package com.ftn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.repository.PaymentTypeRepository;

@Service
public class PaymentTypeService {

	@Autowired
	PaymentTypeRepository ptRepository;
}
