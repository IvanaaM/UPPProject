import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { MagazineService } from '../services/magazine.service';
import { PdfDto } from '../modelDTO/PdfDto';
import { PaperService } from '../services/paper.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(protected router: Router,private paperService: PaperService, private magazineService: MagazineService, private userService: UserService) { }

  logged: any;
  instance: String;
  title: String;
  procIn: String;
  formFields = null;
  formFieldsDto = null;
  taskId: null;
  enumValues= [];
  pdfFile: File = null;
  urlPdf: any;
  comm: String= '';
  paper = [];

  ngOnInit() {

    this.logged = JSON.parse(localStorage.getItem('logged'));
    this.instance = JSON.parse(localStorage.getItem('instance'));


    this.userService.getTaskForUser(this.instance).subscribe(res => {
      console.log(res);
      this.createForm(res);

        if(res == null){
            alert('Rok za ispravku rada je istekao');
         } else {
            this.title = 'Ispravka PDF rada';
      
            this.paperService.getPdfForPaper(this.taskId).subscribe(r => {

              document.querySelector('iframe').src = r.pdf;
              
              console.log(r);
          
                this.paperService.getEditorComm(this.instance).subscribe(data =>{
                
                   this.comm = data.pdf;

                   this.paperService.getPaperData(this.instance).subscribe(f => {
                    console.log("Ovo je rad"  + f);
                      this.paper = f;
                    
                   });

              });
      });
      }
    })
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      o.push({fieldId : property, fieldValue : value[property]});

      }
      const pdfModel = new PdfDto(this.urlPdf);

      this.magazineService.postPdfBytes(JSON.parse(localStorage.getItem('instance')), pdfModel).subscribe(r => {

        console.log(r);
  
    for (let pap in this.paper) {
      console.log(pap);

      //o.push({fieldId : pap.fieldId, fieldValue : pap.fieldValue});

    }

      this.magazineService.postMagazineData(o, this.formFieldsDto.taskId, 'paper').subscribe(res => {
        
        // korisnik je izmenio rad
          this.router.navigateByUrl('');
        });
        
      });
    }

    
createForm(res){

  this.formFields = res.formFields;
  this.procIn = res.processInstanceId;
  this.formFieldsDto = res;

  this.taskId = res.taskId;

  //localStorage.setItem('instance', JSON.stringify(this.procIn));

  this.formFields.forEach( (field) =>{
    
    if( field.type.name=='enum'){
      this.enumValues = Object.keys(field.type.values);
    }
  });

}

handlePDF(event){

  var reader = new FileReader();
  reader.readAsDataURL(event.target.files[0]);
  console.log(reader);
  this.pdfFile = <File>event.target.files[0];

  reader.onload = (e) => {
    this.urlPdf = e.target['result'];
   // console.log(this.urlPdf);
    
  //document.querySelector('iframe').src = this.urlPdf;
  }

}
  
  

}
