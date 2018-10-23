import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../Product';
import { ProductsService } from '../products.service';
import { Customer } from '../Customer';
import { CustomerService } from '../customer.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-buy',
  templateUrl: './buy.component.html',
  styleUrls: ['./buy.component.css']
})
export class BuyComponent implements OnInit {

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, private productService: ProductsService, private customerService: CustomerService) { }

  //object of model class
  product: Product;
  customer: Customer;

  ngOnInit() {
    //getting the particular product id which the user has clicked
    let productId = this.activatedRoute.snapshot.params["id"];

    //getting info of the particular product by passing product id and storing it in the product object
    this.product = this.productService.getProduct(productId);

    //getting the details(object) of current logged in customer
    this.customer = this.customerService.getCustomer();

  }

  //once the customer clicks on pay button this method will be called
  makePayment() {
    this.http.post<{ message: number }>("http://localhost:8081/VoizFonicaBackend/CustomerPurchaseServlet", {"productId": this.product.productId,"email":this.customer.email,"productName":this.product.productName}).subscribe(
      (response) => {
        if (response.message == 1) {
          alert("Thank you for purchasing " + this.product.productName + " /n Your 10 digit " + this.product.productName + " will be emailed to you in " + this.customer.email);
        } else {
          alert("Payment failed");
        }
      },
      (error) => {
        alert("Due to some technical problem payment cannot be done, please try again");
      }
    );



  }
}
