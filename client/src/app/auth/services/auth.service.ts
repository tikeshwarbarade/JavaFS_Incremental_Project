import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../../educonnect/models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiBaseUrl: string = this.getApiBaseUrl();

  private loginUrl: string = `${this.apiBaseUrl}user/login`;
  private registerUrl: string = `${this.apiBaseUrl}user/register`;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  private getApiBaseUrl(): string {
    const origin = window.location.origin;
    const pathname = window.location.pathname;

    const proxyMatch = pathname.match(/^(.*\/proxy\/)5000\//);

    if (proxyMatch) {
      return `${origin}${proxyMatch[1]}3000/`;
    }

    if (origin.includes('localhost:5000') || origin.includes('127.0.0.1:5000')) {
      return 'http://localhost:3000/';
    }

    return `${origin}/context.html/`;
  }

  login(user: Partial<User>): Observable<any> {
    return this.http.post<any>(this.loginUrl, user, this.httpOptions);
  }

  createUser(user: any): Observable<string> {
    return this.http.post(
      this.registerUrl,
      user,
      {
        headers: this.httpOptions.headers,
        responseType: 'text'
      }
    );
  }

  saveLoginResponse(response: any): void {
    if (!response) {
      return;
    }

    const token = response.token || response.jwt || response.jwtToken;

    if (token) {
      localStorage.setItem('token', token);
    }

    let role = response.role || response.roles;

    if (!role && token) {
      try {
        const payload = this.decodeToken(token);

        role = payload.role || payload.roles;

        if (payload.userId !== undefined && payload.userId !== null) {
          localStorage.setItem('userId', String(payload.userId));
        }

        if (payload.studentId !== undefined && payload.studentId !== null) {
          localStorage.setItem('studentId', String(payload.studentId));
        }

        if (payload.teacherId !== undefined && payload.teacherId !== null) {
          localStorage.setItem('teacherId', String(payload.teacherId));
        }

        if (payload.sub) {
          localStorage.setItem('username', payload.sub);
        }
      } catch (error) {
        console.error('Unable to decode token:', error);
      }
    }

    if (role) {
      localStorage.setItem('role', role);
    }

    if (response.userId !== undefined && response.userId !== null) {
      localStorage.setItem('userId', String(response.userId));
    }

    if (response.studentId !== undefined && response.studentId !== null) {
      localStorage.setItem('studentId', String(response.studentId));
    }

    if (response.teacherId !== undefined && response.teacherId !== null) {
      localStorage.setItem('teacherId', String(response.teacherId));
    }

    if (response.username) {
      localStorage.setItem('username', response.username);
    }
  }

  private decodeToken(token: string): any {
    const tokenParts = token.split('.');

    if (tokenParts.length !== 3) {
      return {};
    }

    const payload = tokenParts[1];

    const normalizedPayload = payload
      .replace(/-/g, '+')
      .replace(/_/g, '/');

    const decodedPayload = atob(normalizedPayload);

    return JSON.parse(decodedPayload);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string {
    return localStorage.getItem('role') || '';
  }

  getUserId(): number {
    return Number(localStorage.getItem('userId') || 0);
  }

  getStudentId(): number {
    return Number(localStorage.getItem('studentId') || 0);
  }

  getTeacherId(): number {
    return Number(localStorage.getItem('teacherId') || 0);
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    localStorage.removeItem('studentId');
    localStorage.removeItem('teacherId');
    localStorage.removeItem('username');
  }
}