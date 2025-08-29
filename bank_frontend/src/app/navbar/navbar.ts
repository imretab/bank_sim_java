import { Component } from '@angular/core';
import { RouterModule,Router } from '@angular/router';
import { Auth } from '../auth';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {
  constructor(private auth: Auth, private router: Router){}
  removeLi(){
    if(this.auth.isTokenValid() == false){
      return true;
    }
    return false;
  }
  logout(){
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
