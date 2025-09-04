import { RouterModule, Routes } from '@angular/router';
import { LoginPage } from './login-page/login-page';
import { Register } from './register/register';
import { NgModule } from '@angular/core';
import { HomePage } from './home-page/home-page';
import { AuthGuard } from './auth-guard';
import { Messages } from './messages/messages';
import { ProfilePage } from './profile-page/profile-page';

export const routes: Routes = [
  { path: 'login', component: LoginPage },
  { path: 'register', component: Register },
  { path:'home',component: HomePage, canActivate:[AuthGuard]},
  { path:'messages',component:Messages, canActivate:[AuthGuard]},
  {path:'profile',component:ProfilePage,canActivate:[AuthGuard]}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}