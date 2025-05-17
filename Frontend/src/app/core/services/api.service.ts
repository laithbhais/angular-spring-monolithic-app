import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginData } from '../../data/LoginData';
import { SignupDate } from '../../data/SignupData';
import { ZoomMeetingRequest } from '../../data/ZoomMeeting';

@Injectable({providedIn: 'root'})
export class ApiService {

  private apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) { }

  public login(loginData: LoginData): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/auth/login`, loginData, { withCredentials: true });
  }

  public signup(signupDate: SignupDate): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/auth/signup`, signupDate);
  }

  public getUserProfile(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/profile`);
  }

  public loginWithGoogle() {
    window.location.href = `${this.apiUrl}/auth/google`;
  }

  public listMeetings(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/zoom/meetings`);
  }
  
  public createMeeting(zoomMeeting: ZoomMeetingRequest): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/zoom/meeting`, zoomMeeting);
  }

  public refreshToken(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/auth/refresh-token`, {}, { withCredentials: true });
  }
}
