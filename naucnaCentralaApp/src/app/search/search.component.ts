import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { SearchService } from '../services/search.service';
import { PdfDto } from '../modelDTO/PdfDto';
import { SimpleQuery } from '../modelDTO/SimpleQuery';
import { AbstractControl, FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  
  public data: boolean = false;
  public items= [];

  public form: FormGroup;

  public magazine: AbstractControl;
  public magazineOperation: AbstractControl;
  public magazinePhrase: AbstractControl;

  public keywords: AbstractControl;
  public keywordsOperation: AbstractControl;
  public keywordsPhrase: AbstractControl;
  
  public title: AbstractControl;
  public titleOperation: AbstractControl;
  public titlePhrase: AbstractControl;

  public firstNameAuthor: AbstractControl;
  public firstNameAuthorOperation: AbstractControl;
  public firstNameAuthorPhrase: AbstractControl;
  
  public lastNameAuthor: AbstractControl;
  public lastNameAuthorOperation: AbstractControl;
  public lastNameAuthorPhrase: AbstractControl;

  public text: AbstractControl;
  public textPhrase: AbstractControl;
  public textOperation: AbstractControl;

  public area: AbstractControl;
  public areaOperation: AbstractControl;
  public areaPhrase: AbstractControl;

  constructor(protected router: Router, private fb: FormBuilder, private searchService: SearchService) { 

    this.form = this.fb.group({
      'magazine': [''],
      'magazineOperation': [false],
      'magazinePhrase': [false],

      'keywords': [''],
      'keywordsOperation': [false],
      'keywordsPhrase': [false],

      'title': [''],
      'titleOperation': [false],
      'titlePhrase': [false],

      'firstNameAuthor': [''],
      'firstNameAuthorOperation': [false],
      'firstNameAuthorPhrase': [false],

      'lastNameAuthor': [''],
      'lastNameAuthorOperation': [false],
      'lastNameAuthorPhrase': [false],

      'area': [''],
      'areaOperation': [false],
      'areaPhrase': [false],

      'text': [''],
      'textOperation': [false],
      'textPhrase': [false],
    });


    this.magazine = this.form.controls['magazine'];
    this.magazineOperation = this.form.controls['magazineOperation'];
    this.magazinePhrase = this.form.controls['magazinePhrase'];

    this.title = this.form.controls['title'];
    this.titleOperation = this.form.controls['titleOperation'];
    this.titlePhrase = this.form.controls['titlePhrase'];

    this.keywords = this.form.controls['keywords'];
    this.keywordsOperation = this.form.controls['keywordsOperation'];
    this.keywordsPhrase = this.form.controls['keywordsPhrase'];

    this.area = this.form.controls['area'];
    this.areaOperation = this.form.controls['areaOperation'];
    this.areaPhrase = this.form.controls['areaPhrase'];

    this.text = this.form.controls['text'];
    this.textOperation = this.form.controls['textOperation'];
    this.textPhrase = this.form.controls['textPhrase'];

    this.firstNameAuthor = this.form.controls['firstNameAuthor'];
    this.firstNameAuthorOperation = this.form.controls['firstNameAuthorOperation'];
    this.firstNameAuthorPhrase = this.form.controls['firstNameAuthorPhrase'];
    
    this.lastNameAuthor = this.form.controls['lastNameAuthor'];
    this.lastNameAuthorOperation = this.form.controls['lastNameAuthorOperation'];
    this.lastNameAuthorPhrase = this.form.controls['lastNameAuthorPhrase'];

  }

  ngOnInit() {
    
  }

 search(){
let o = [];
    this.data = true;

    const list = [];
  
    const a = new SimpleQuery();

    //magazin
    a.field = "magazine";
    a.value = this.magazine.value;
    a.operation = this.magazineOperation.value;
    a.phrase = this.magazinePhrase.value;

    list.push(a);

    const b = new SimpleQuery();

    //naslov
    b.field = "title";
    b.value = this.title.value;
    b.operation = this.titleOperation.value;
    b.phrase = this.titlePhrase.value;

    list.push(b);

    const c = new SimpleQuery();

    //ime
    c.field = "firstNameAuthor";
    c.value = this.firstNameAuthor.value;
    c.operation = this.firstNameAuthorOperation.value;
    c.phrase = this.firstNameAuthorPhrase.value;

    list.push(c);

    const k = new SimpleQuery();

    //prezime
    k.field = "lastNameAuthor";
    k.value = this.lastNameAuthor.value;
    k.operation = this.lastNameAuthorOperation.value;
    k.phrase = this.lastNameAuthorPhrase.value;
    
    list.push(k);

    const d = new SimpleQuery();

    //keywords
    d.field = "keywords";
    d.value = this.keywords.value;
    d.operation = this.keywordsOperation.value;
    d.phrase = this.keywordsPhrase.value;

    list.push(d);

    const e = new SimpleQuery();

    //text
    e.field = "text";
    e.value = this.text.value;
    e.operation = this.textOperation.value;
    e.phrase = this.textPhrase.value;

    list.push(e);

    const f = new SimpleQuery();

    //area
    f.field = "area";
    f.value = this.area.value;
    f.operation = this.areaOperation.value;
    f.phrase = this.areaPhrase.value;

    list.push(f);

    console.log(list);
        
    
    this.searchService.sendData(list).subscribe(res => {
      console.log(res);
      this.items = res;
    });
    
    }
   


}
