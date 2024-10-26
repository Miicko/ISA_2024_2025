import { Component, OnInit, NgModule } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user-service.service';
import { User } from '../model/user';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css',
})
export class UserFormComponent {
  user: User;
  createUserForm: FormGroup;
  submitted = false;
  passwordsnotmatching = true;

  constructor(
    private route: ActivatedRoute, 
    private router: Router, 
    private userService: UserService,
    private formBuilder: FormBuilder,
  ) 
  {
    this.user = new User();
  }

  ngOnInit() {
    this.createUserForm = this.formBuilder.group(
      {
        email: ['', Validators.required],
        firstname: ['', Validators.required],
        lastname: ['', [Validators.required]],
        password: ['', Validators.required],
        password2: ['', Validators.required],
        address: ['', Validators.required],
        myusername: ['', [Validators.required]],
      },

    );
    console.log("ngoninit");
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

    if(this.createUserForm.controls['password'].value == this.createUserForm.controls['password2'].value){
      this.passwordsnotmatching = false;
    }
    if(this.passwordsnotmatching == true)
      return;
    
    console.log("submit");
    this.user.email = this.createUserForm.controls['email'].value;
    this.user.firstName = this.createUserForm.controls['firstname'].value;
    this.user.lastName = this.createUserForm.controls['lastname'].value;
    this.user.password = this.createUserForm.controls['password'].value;
    this.user.address = this.createUserForm.controls['address'].value;
    this.user.username = this.createUserForm.controls['myusername'].value;
    this.user.role = "USER";
    alert(
      'SUCCESS!! :-)\n\n' + JSON.stringify(this.user, null, 6)
    );
    this.userService.save(this.user).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/']);
  }
}
