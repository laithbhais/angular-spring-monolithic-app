import { Injectable } from '@angular/core';
import { jwtDecode, JwtPayload } from 'jwt-decode';

@Injectable({
    providedIn: 'root',
})
export class TokenService {
    private readonly TOKEN_KEY = 'auth_token';

    constructor() { }

    saveToken(token: string): void {
        localStorage.setItem(this.TOKEN_KEY, token);
    }

    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    clearToken(): void {
        localStorage.removeItem(this.TOKEN_KEY);
    }

    extractClaims(): JwtPayload | null {
        const token = this.getToken();
        if (!token) return null;
        try {
            return jwtDecode<JwtPayload>(token);
        } catch (err) {
            return null;
        }
    }

    isTokenExpired(): boolean {
        const token = this.getToken();
        if (!token) return true;

        const decoded = jwtDecode<JwtPayload>(token);
        const expirationTime = decoded.exp! * 1000;  // Convert to milliseconds
        return Date.now() >= expirationTime;
    }
}
