import { Component, OnInit, NgModule } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user-service.service';
import { User } from '../model/user';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service.service';
import { UserDetails } from '../model/user-details';


@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css',
})
export class UserLoginComponent {
  createUserForm: FormGroup;
  submitted = false;
  userDetails: UserDetails;

  constructor(
    private route: ActivatedRoute, 
    private router: Router, 
    private userService: UserService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
  ) 
  {
    this.userDetails = new UserDetails();
  }

  ngOnInit() {
    this.createUserForm = this.formBuilder.group(
      {
        email: ['', Validators.required],
        password: ['', Validators.required]
      }
    );
  }

  get f() {
    return this.createUserForm.controls;
  }

  onSubmit() {
    console.log("submit clicked");
    this.submitted = true;

    if (this.createUserForm.invalid) {
      return;
    }
    this.userDetails.email = this.createUserForm.controls['email'].value;
    this.userDetails.password = this.createUserForm.controls['password'].value;
    console.log(
      'SUCCESS!! :-)\n\n' + JSON.stringify(this.createUserForm.controls['email'].value + "   " +  this.createUserForm.controls['password'].value, null, 6)
    );
    this.authService.login(this.userDetails).subscribe(result => this.gotoUserList)
    //this.userService.save(this.user).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    //this.router.navigate(['/users']);
    console.log("Uspeo login");
  }
}
