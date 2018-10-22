import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Customer } from '../Customer';

import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private http : HttpClient, private router:Router) { }

  ngOnInit() {
  }
  customer : Customer;
  register(f:NgForm){
    this.customer = new Customer();
    this.customer.firstName = f.value.firstName;
    this.customer.lastName = f.value.lastName;
    this.customer.email = f.value.email;
    this.customer.aadhaarNo = f.value.aadhaarNo;
    this.customer.mobileNumber = f.value.mobileNumber;
    this.customer.password = f.value.password;
    this.customer.address = f.value.address;
    this.customer.city = f.value.city;
    this.customer.state = f.value.state;
    this.customer.pincode = f.value.pincode;
    console.log(this.customer)

    this.http.post<{message:number}>("http://localhost:8081/VoizFonicaBackend/RegisterServlet",this.customer).subscribe(
  
  (response) =>{
    if (response.message == 1) {
      alert("Registration Successfull!!!!! \n LOGIN NOW");
      this.router.navigate(['/login']);
    } else {
      alert("Registration UnSuccessful");
    }

  },
          
  (error) =>{
    alert("Something went wrong!!!!!!  Plz login again");
  });


  }
}
