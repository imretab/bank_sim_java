import { Component } from '@angular/core';
import { Auth } from '../auth';

@Component({
  selector: 'app-home-page',
  imports: [],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css'
})
export class HomePage {
  constructor(private auth: Auth){}
  user: any;
  value: string | undefined;
  ngOnInit() {
    this.user = this.auth.getLoggedInUser();
    console.log("Logged-in user:", this.user);
    this.value = this.user.sub;
  }

}
