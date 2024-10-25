import { Component, OnInit, NgModule } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user-service.service';
import { User } from '../model/user';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service.service';
import { UserDetails } from '../model/user-details';


@Component({
  selector: 'app-user-logout',
  templateUrl: './user-logout.component.html',
  styleUrl: './user-logout.component.css'
})
export class UserLogoutComponent {

  constructor(
    private route: ActivatedRoute, 
    private router: Router, 
    private userService: UserService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
  ) 
  {
  }

  ngOnInit() {
    this.authService.decodeEmail();
    let loggeduser = this.authService.getLoggedUser();
    console.log(loggeduser);
    this.authService.logout();
    this.gotoHome();
  }

  gotoHome() {
    this.router.navigate(['/']);
    console.log("Uspeo logout");
  }
}
