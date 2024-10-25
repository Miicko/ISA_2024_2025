import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError, delay } from 'rxjs/operators';
import { of } from 'rxjs';
import { UserDetails } from '../model/user-details';
import { User } from '../model/user';
import { UserService } from './user-service.service';



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private headers: HttpHeaders;
  private user: User;
  private userId: number;
  constructor(private http: HttpClient, private userService: UserService) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': '*/*',
    });
    this.user = new User();
  }

  isLoggedIn: boolean = false;
  login(userDetails: UserDetails): Observable<boolean> {
    return this.http.post<any>('http://localhost:8080/api/v1/auth/login', JSON.stringify(userDetails, null, 2), { headers: this.headers})
      .pipe(
        map(response => {
          console.log(response);
          console.log(response.accessToken);
          localStorage.setItem('JWT_Token', response.accessToken);
          this.isLoggedIn = true;
          return true;
        }),
        catchError(error => {
          console.log(error);
          this.isLoggedIn = false;
          return of(false);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('JWT_Token');
    this.isLoggedIn = false;
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn;
  }

  decodeEmail(): string{
    let a = localStorage.getItem('JWT_Token');
    if(a!=null){
      let decodedtoken = JSON.parse(window.atob(a.split('.')[1]))
      console.log(decodedtoken.email);
      return decodedtoken.email;
    }else{
      return '';
    }
  }

  getLoggedUser(): Observable<User>{
    let em = this.decodeEmail();
    if(em != ''){
      let retval = this.userService.findByEmail(em).subscribe(data => {
        let retuser = data;
        this.user.address = retuser.address;
        this.user.email = retuser.email;
        this.user.firstName = retuser.email;
        this.user.lastName = retuser.lastName;
        this.user.myusername = retuser.myusername;
        this.user.id = retuser.id;
      });
      return of(this.user).pipe(delay(500));
    }else
      throw console.error("Error finding user by email!");
      ;
  }
  getLoggedUserId(): Observable<any>{
    let em = this.decodeEmail();
    let retId;
    if(em != ''){
      let retval = this.userService.findByEmail(em).subscribe(data => {
        let retuser = data;
        retId = of(retuser.id);
      });
      return of(retId).pipe(delay(1000));
    }else
      throw console.error("Errror finding user by email!");
      ;
  }

  otherFunc(a : number) : number{
    console.log(a)
    return a;
  }
}
