import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private customerService: CustomerService) { }
  isLoggedIn: boolean;

  ngOnInit() {
    this.customerService.currentLoginUpdate.subscribe(

      (loginStatus) => {

        if (loginStatus) {
          this.isLoggedIn = true;
        } else {
          this.isLoggedIn = false;
        }

      }

    );
  }






}
