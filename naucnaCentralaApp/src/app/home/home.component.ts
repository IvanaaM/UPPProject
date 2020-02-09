import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from '../modelDTO/login';
import { element } from '@angular/core/src/render3';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  log= [];
  show: boolean = false;
  list: [];
  hasTasks: boolean = true;
  noboody: any;

  constructor(protected router: Router, private userService: UserService) {

   }

  ngOnInit() {

    this.log = JSON.parse(localStorage.getItem('roles'));

    if (this.log != null){
      this.noboody = false;
    //  console.log(this.log);
      this.log.forEach(element => {

        if(element.toString() == 'U'){
          this.show = true;
        } else {
          this.show = false;
        }
      });
     
     } else { 
       this.show = false;
       this.noboody = true;
     }
/*
     const procIn = JSON.parse(localStorage.getItem('instance'));
     if(procIn != null){
     this.userService.checkHasTask(procIn).subscribe(res => {
       console.log(res);
       if(res!=null){
        this.hasTasks = false;
       }
     });
   
    } else {
      this.hasTasks = false;
    }
    
*/
  }

  report(){
    this.router.navigateByUrl('/chooseMagazine');
}

// proveriti da li postoji korigovanje 
changePaper(){

  this.router.navigateByUrl('profil');

}

}
