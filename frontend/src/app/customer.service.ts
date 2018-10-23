import { Injectable } from '@angular/core';
import { Customer } from './Customer';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor() { }

  customer : Customer;

  private customerLoginUpdate = new BehaviorSubject(false);
  currentLoginUpdate = this.customerLoginUpdate.asObservable();

  changeUserUpdate(message: boolean) {
    this.customerLoginUpdate.next(message)
  }

  setCustomer(customer : Customer){
    this.customer = customer;
    console.log(this.customer);
  }

  getCustomer(){
    return this.customer;
  }
}
