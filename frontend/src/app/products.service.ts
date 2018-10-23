import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Product } from './Product';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient, private router: Router) { }

  products: Array<Product> = [];

  // connect with the backend and get all products from Db and stores it in the products array
  getProductsFromDb() {
    this.http.get<Array<Product>>("http://localhost:8081/VoizFonicaBackend/ProductServlet").subscribe(
      (response) => {
        // initializing the array with response which we got
        this.products = response;
      },
      (error) => {
        alert("Unable to display products!!!");
      }
    );
  }


  // returns array of all products available
  allProducts() {
    return this.products;
  }

  // returns only a particular product from the products array. It takes productId has parameter so that it can find it from the array
  getProduct(productId: number) {
    return this.products.find(
      (product) => product.productId == productId
    );
  }

}
