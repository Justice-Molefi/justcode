import { Injectable } from '@angular/core';
import { CodeRequest } from '../models/dto/code-request';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EditorService {

  baseUrl: string = 'http://localhost:8080'
  constructor(private http: HttpClient) { }

  execute(codeRquest: CodeRequest): Observable<string>{
    return this.http.post(`${this.baseUrl}/execute`, codeRquest, {responseType: 'text'});
  }
}
