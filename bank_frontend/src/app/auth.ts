import { Injectable } from '@angular/core';
import {jwtDecode, JwtPayload} from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private tokenKey = 'accessToken';

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getLoggedInUser(): JwtPayload | null {
    const token = localStorage.getItem('accessToken');
    if (!token) return null;
  
    try {
      return jwtDecode<JwtPayload>(token);
    } catch (err) {
      console.error("Invalid token", err);
      return null;
    }
  }

  isTokenValid(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const decoded = jwtDecode<JwtPayload>(token);

      if (!decoded.exp) return false;

      const expirationDate = new Date(0);
      expirationDate.setUTCSeconds(decoded.exp);

      return expirationDate > new Date();
    } catch (e) {
      return false;
    }
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }
}