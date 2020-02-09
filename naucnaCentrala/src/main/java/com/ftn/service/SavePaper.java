package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.ChargeType;
import com.ftn.enums.PaymentTypes;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.model.Paper;
import com.ftn.model.UserCustom;
import com.ftn.modelDTO.PdfDto;
import com.ftn.services.MagazineService;
import com.ftn.services.PaperService;
import com.ftn.services.UserService;
import com.ftn.services.ScientificAreaService;

@Service
public class SavePaper implements JavaDelegate {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	PaperService paperService;

	@Autowired
	ScientificAreaService scientificAreaService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String username = (String) execution.getProcessInstance().getVariable("user");
		
		List<FormSubmissionDto> fsd = (List<FormSubmissionDto>) execution.getVariable("paper");

		UserCustom u = userService.findByUsername(username);
		
		List<FormSubmissionDto> list =  (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("chooseMagazine");

		Magazine m = magazineService.findByName(list.get(0).getFieldValue()); 
		
		Paper p = new Paper();
		
		for(FormSubmissionDto formField : fsd) {
			
			if(formField.getFieldId().equals("naslov")) {
				p.setTitle(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("apstrakt")) {
				p.setApstract(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("koautori")) {
				p.setCoauthors(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("kljucniPojmovi")) {
				p.setKeywords(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("naucnaOblast")) {
				p.setScientificArea(scientificAreaService.findByName(formField.getFieldValue()));
			}
		}
		
		PdfDto pd = (PdfDto) execution.getProcessInstance().getVariable("pdfUrl");
		
		p.setAuthor(u);
		p.setMagazine(m);
		p.setPathPdf(pd.getPdf());
		
		paperService.savePaper(p);
	}

}
