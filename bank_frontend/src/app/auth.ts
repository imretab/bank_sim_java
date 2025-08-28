import { Injectable } from '@angular/core';
import {jwtDecode,  JwtPayload } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private tokenKey = 'accessToken';

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isTokenValid(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const decoded = jwtDecode<JwtPayload>(token);

      if (!decoded.exp) return false;

      const expirationDate = new Date(0);
      expirationDate.setUTCSeconds(decoded.exp);

      return expirationDate > new Date(); // valid if in the future
    } catch (e) {
      return false;
    }
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }
}