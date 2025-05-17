import { Injectable } from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, switchMap, throwError } from 'rxjs';
import { TokenService } from '../services/token.service';
import { AuthService } from '../services/auth.service';
import { ApiService } from '../services/api.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private apiService: ApiService    
  ) {}
  intercept(httpRequest: HttpRequest<any>, httpHandler: HttpHandler): Observable<HttpEvent<any>> {

    if (httpRequest.url.includes(`/api/v1/auth`)) {
      return httpHandler.handle(httpRequest);
    }

    // Clone the request to add the withCredentials option
    httpRequest = httpRequest.clone({
      withCredentials: true // This is crucial for sending cookies
    });

    const token = this.tokenService.getToken();

    if (!token) {
      return this.handleRequest(httpRequest, httpHandler);
    }

    if (this.tokenService.isTokenExpired()) {
      return this.handleExpiredToken(httpRequest, httpHandler);
    }

    return this.handleRequest(this.addAuthHeader(httpRequest, token), httpHandler);
  }

  private handleExpiredToken(httpRequest: HttpRequest<any>, httpHandler: HttpHandler): Observable<HttpEvent<any>> {
    return this.apiService.refreshToken().pipe(
      switchMap((res) => {
        this.tokenService.saveToken(res.token);
        return this.handleRequest(this.addAuthHeader(httpRequest, res.token), httpHandler);
      }),
      catchError((error) => {
        this.authService.logout();
        return throwError(() => error);
      })
    );
  }

  private handleRequest(httpRequest: HttpRequest<any>, httpHandler: HttpHandler): Observable<HttpEvent<any>> {
    return httpHandler.handle(httpRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.authService.logout();
        }
        return throwError(() => error);
      })
    );
  }

  private addAuthHeader(httpRequest: HttpRequest<any>, token: string): HttpRequest<any> {
    return httpRequest.clone({setHeaders: { Authorization: `Bearer ${token}`}});
  }

}
