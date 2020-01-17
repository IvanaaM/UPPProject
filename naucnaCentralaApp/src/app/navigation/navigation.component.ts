import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Login } from '../modelDTO/login';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  log: Login;
  out: boolean = false;
  admin: boolean = false;
  urednik: boolean = false;

  constructor(protected router: Router, private userService: UserService) { 

  }

  ngOnInit() {
    this.log = JSON.parse(localStorage.getItem('logged'));

    if (this.log == null){
      this.out = true;
    } else {
      this.out = false;
    }
    this.checkLogged();
  }

  checkLogged(){
   
  if (this.log != null){
   this.log.roles.forEach(element => {
     console.log('ovo je' + element);
     if (element.toString() == 'ROLE_ADMIN'){
       console.log("jeste");
        this.admin = true;
        this.urednik = false;
     } else if (element.toString() == 'ROLE_EDITOR'){
       this.urednik = true;
       this.admin = false;
     }
   }); 
  }
    
  }

  registracija(){
    this.router.navigateByUrl('/register/user');
  }

  admins(){
    this.router.navigateByUrl('/admin');
  }

  editors(){
    this.router.navigateByUrl('/editor');
  }

  login(){
    this.router.navigateByUrl('/login');
  }

  logout(){
    localStorage.removeItem('logged');
    this.out = true;
    this.urednik = false;
    this.admin = false;
    //window.location.reload();
    this.router.navigateByUrl('/');
  }

}
