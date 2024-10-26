import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserLoginComponent } from './user-login/user-login.component';
import { AuthGuardService } from './service/auth-guard.service';
import { UserLogoutComponent } from './user-logout/user-logout.component';
import { UserFormComponent } from './user-form/user-form.component';

const routes: Routes = [
  //{ path: 'users', component: UserListComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: UserLoginComponent },
  { path: 'logout', component: UserLogoutComponent },
  { path: 'signup', component: UserFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
