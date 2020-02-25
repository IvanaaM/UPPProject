import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SimpleQuery } from '../modelDTO/SimpleQuery';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  
  constructor(private httpClient: HttpClient) { }

  sendData(a: SimpleQuery[]) {
 //   const token = localStorage.getItem('logged');
  //  const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post('http://localhost:8080/search/search', a) as Observable<any>
  }
}
