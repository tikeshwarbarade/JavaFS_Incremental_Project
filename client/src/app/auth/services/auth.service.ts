import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../../educonnect/models/User';
import { UserRegistrationDTO } from '../../educonnect/models/UserRegistrationDTO';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl: string = `${window.location.origin}/context.html/user/login`;
  private registerUrl: string = `${window.location.origin}/context.html/user/register`;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  login(user: Partial<User>): Observable<{ [key: string]: string }> {
    return this.http.post<{ [key: string]: string }>(
      this.loginUrl,
      user,
      this.httpOptions
    );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    localStorage.removeItem('username');
  }

  createUser(user: UserRegistrationDTO): Observable<any> {
    return this.http.post<any>(
      this.registerUrl,
      user,
      this.httpOptions
    );
  }
}
