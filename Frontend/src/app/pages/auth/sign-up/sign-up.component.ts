import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignupDate } from '../../../data/SignupData';
import { ApiService } from '../../../core/services/api.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html'
})
export class SignUpComponent implements OnInit {

  signupData: SignupDate = new SignupDate();

  form: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  })

  constructor(
    private formBuilder: FormBuilder,
    private apiService: ApiService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit() { }

  signup() {
    if(this.form.invalid) return;
    this.apiService.signup(this.signupData).subscribe(
      (res) => {
        this.toastr.success(res.message);
        this.router.navigateByUrl('/login');
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
