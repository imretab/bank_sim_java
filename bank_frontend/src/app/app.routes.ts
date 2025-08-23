import { RouterModule, Routes } from '@angular/router';
import { LoginPage } from './login-page/login-page';
import { Register } from './register/register';
import { NgModule } from '@angular/core';

export const routes: Routes = [
  { path: 'login', component: LoginPage },
  { path: 'register', component: Register }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}