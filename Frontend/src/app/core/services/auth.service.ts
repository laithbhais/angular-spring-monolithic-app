import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(
    private router: Router,
    private tokenService: TokenService
  ) {}

  isLoggedIn(): boolean {
    return !!this.tokenService.getToken();
  }

  login(token: string): void {
    this.tokenService.saveToken(token);
    this.router.navigateByUrl('/dashboard');
  }

  logout(): void {
    this.tokenService.clearToken();
    this.router.navigateByUrl('/login');
  }
}
