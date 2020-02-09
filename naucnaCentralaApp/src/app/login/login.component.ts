import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { LoginDTO } from '../modelDTO/loginDTO';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public nameuser: String;
  public pass: String;
  public show: Boolean = false;

  constructor(private userService: UserService, protected router: Router) { }

  ngOnInit() {
  }

  onSubmit(value, form){

    console.log(this.nameuser);
    console.log(this.pass);
    let log = new LoginDTO();
    log.password = this.pass;
    log.username = this.nameuser;
    
    this.userService.login(log).subscribe( user => {
      console.log(user);

      if (user.accessToken == null) {
        alert('Ne postoji korisnik sa unetim kredencijalima!');
        return;
      }

        localStorage.setItem('logged', JSON.stringify(user.accessToken));

        this.userService.checkRoles().subscribe( r => {

          console.log(r);
          localStorage.setItem('roles', JSON.stringify(r));
    
        });
      
        console.log(localStorage);

        //window.location.reload();
        this.router.navigateByUrl('');
    
    });

  }

  again(){
    window.location.reload();
  }

}
