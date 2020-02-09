import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PaperService } from '../services/paper.service';
import { AnonymousSubject } from 'rxjs/internal/Subject';
import { Paper } from '../modelDTO/paper';
import { PdfDto } from '../modelDTO/PdfDto';
import { EditorService } from '../services/editor.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-main-editor-page',
  templateUrl: './main-editor-page.component.html',
  styleUrls: ['./main-editor-page.component.css']
})
export class MainEditorPageComponent implements OnInit {

  requests = null;
  list = [];
  formFields = null;
  formFieldsDto = null;
  a: null;
  procIn = "";
  enumValues = [];
  ff = null;
  paper = [];
  taskId = '';
  formatGood: boolean = false;
  mode: any;
  instance: String ='';
  pdfShow: boolean = false;
  title: String = '';
  pdfGood: boolean = false;
  dataShow: boolean = false;

  constructor(protected router: Router, private route: ActivatedRoute, private userService: UserService, private paperService: PaperService, private editorService: EditorService) { }

  // stranica glavnog urednika: treba da mu se prikazu zadaci za obradu radova:
  // dakle, sve AKTIVNE INSTANCE procesa za obradu teksta koje se odnose na
  // trenutno ulogovanog urednika

  ngOnInit() {
    

    this.instance = JSON.parse(localStorage.getItem('instance'));


    this.mode = this.route.snapshot.params.mode;

    if(this.mode == 'Pdf'){

      if(this.route.snapshot.params.type == 'setD'){

        this.title = 'Postavka roka + komentar';
        this.pdfShow = true;
        this.dataShow = true;

        this.userService.checkHasTasks(this.instance).subscribe(data => {

          console.log(data);
          this.taskId =data.taskId;
          this.createForm(data);
        });


      } else{

      this.title = 'Provera PDF-a';
      this.pdfShow = false;
      this.dataShow = true;

      this.paperService.getPdfTaskCheck(this.instance).subscribe(res => {
        
      this.taskId = res.taskId;
      this.createForm(res);

      this.paperService.getPdfForPaper(this.taskId).subscribe(r => {

      console.log(r);
         
       document.querySelector('iframe').src = r.pdf;

      });


      });
    }


   } else {

      
      this.pdfShow = true;
      this.dataShow = false;
      this.title="Provera podataka rada";

    this.paperService.getPapersForIds(this.instance).subscribe(res => {
      console.log(res);

      this.taskId = res.taskId;

      this.createForm(res);

      this.paperService.getPaperData(this.instance).subscribe(r => {
        console.log(r);
        this.paper = r;
      });
    
    });

    }
    
  }


createForm(res){

        this.formFields = res.formFields;
        this.procIn = res.processInstanceId;
        this.formFieldsDto = res;

        //localStorage.setItem('instance', JSON.stringify(this.procIn));

        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });

}

onSubmit(value, form){
  let o = new Array();
  for (var property in value) {
    console.log(property);
    console.log(value[property]);

    if(property == 'tematskiPrihvatljiv'){
      if(value[property]==true){
        this.formatGood = true;
      }
    }
    if(property == 'dobroFormatiran'){
      if(value[property]==true){
        this.pdfGood = true;
      }
    }

    o.push({fieldId : property, fieldValue : value[property]});
    }

if (this.mode == 'Pdf'){

  if(this.route.snapshot.params.type == 'setD'){
    this.paperService.post(this.taskId, o, 'deadlines').subscribe(r => {
     
      this.router.navigateByUrl('');
    });
  } else {

    this.paperService.post(this.taskId, o, 'dobarFormatPDF').subscribe(r => {
      if(this.pdfGood != true){
        
        this.router.navigateByUrl('mainEditor/Pdf/setD'); 
      } else {
        
        this.router.navigateByUrl('prepareReviewers/reviewers'); 
      }
    });
  }


} else {

  this.paperService.post(this.taskId, o, 'temPrihvatljiv').subscribe(res => {
    console.log(res);
    if(this.formatGood == true){
    this.router.navigateByUrl('mainEditor/Pdf');
    } else {
      this.router.navigateByUrl('');
    }
  });
}



  }

  

}





