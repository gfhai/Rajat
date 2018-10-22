import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Customer } from '../Customer';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private http : HttpClient, private router : Router) { }

  ngOnInit() {
    }
    customer : Customer;
  login(f:NgForm){
    this.customer = new Customer();
   this.customer.email = f.value.email;
   this.customer.password = f.value.password;
   
   this.http.post<{message:number, customer : Customer}>("http://localhost:8081/VoizFonicaBackend/LoginServlet",{"email":this.customer.email,"password":this.customer.password}).subscribe(
    (response) => {
      if (response.message==1) {
        alert("Login Success");
        console.log(response.customer);
      } else {
        alert("Login Unsuccessful");
      }
    },
    (error) => {
      alert("Somethings went wrong!!!!  plz try again")
    }

   );
  }
}
