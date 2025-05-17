import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../../core/services/api.service';
import { AuthService } from '../../../core/services/auth.service';
import { LoginData } from '../../../data/LoginData';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  loginData: LoginData = new LoginData();
  
  form: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]]
  })

  constructor(
    private formBuilder: FormBuilder,
    private apiService: ApiService,
    private authService: AuthService,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    const queryParams = this.activatedRoute.snapshot.queryParamMap;
    if (queryParams.has('token')) {
      this.authService.login(queryParams.get('token')!);
      this.toastr.success("Login Successfully");
    }
  }

  login() {
    if(this.form.invalid) return;
    this.apiService.login(this.loginData).subscribe(
      (res) => {
        this.authService.login(res.token);
        this.toastr.success("Login Successfully");
      },
      (err) => {
        this.toastr.error(err.error);
      }
    )
  }

  loginWithGoogle(): void {
    this.apiService.loginWithGoogle();
  }
}
