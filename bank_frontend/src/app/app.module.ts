import { Component, NgModule } from '@angular/core';
import { LoginPage } from './login-page/login-page';
import { Register } from './register/register';
import { AppRoutingModule } from './app.routes';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    LoginPage,
    Register
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: []
})
export class AppModule { }