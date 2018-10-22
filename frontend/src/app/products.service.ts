import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Product } from './Product';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http : HttpClient, private router : Router) { }

  products : Array<Product> = [];

  getProductsFromDb(){
    
    this.http.get<Array<Product>>("http://localhost:8081/VoizFonicaBackend/ProductServlet").subscribe(
      (response) => {
        this.products = response;
        console.log(this.products);
        return this.products;
      },
      (error) => {

      }
    );
  }
}
