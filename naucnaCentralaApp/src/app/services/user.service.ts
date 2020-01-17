import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';
import { LoginDTO } from 'src/app/modelDTO/loginDTO';
import { Login } from 'src/app/modelDTO/login';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  

  constructor(private httpClient: HttpClient) { }

  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8080/user/post/".concat(taskId), user, httpOptions) as Observable<any>;
  }

  postNO(o, taskId: string) {
    return this.httpClient.post("http://localhost:8080/user/postNO/".concat(taskId), o, httpOptions) as Observable<any>;
  }

  login(log){
    const body = JSON.stringify(log);
    return this.httpClient.post("http://localhost:8080/user/login", body,  httpOptions) as Observable<any>;
  }

  getLoggedUser(){
    let log = new Login();
    log = JSON.parse(localStorage.getItem('logged'));
    return log;
  }

  logout(){
    
  }
}
