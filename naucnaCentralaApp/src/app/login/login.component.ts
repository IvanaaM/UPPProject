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
    
    this.userService.login(log).subscribe( res => {
      console.log(res);

      if(res != null){
        localStorage.setItem('logged', JSON.stringify(res));
        //window.location.reload();
        this.router.navigateByUrl('');
      } else {
        alert("Neuspesno logovanje, kredencijali se ne poklapaju");
      }

    });

  }

  again(){
    window.location.reload();
  }

}
