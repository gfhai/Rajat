import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../products.service';
import { Product } from '../Product';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  constructor(private productService: ProductsService, private http: HttpClient) { }

  products: Array<Product> = [];
  ngOnInit() {
    //  this.products = this.productService.getProductsFromDb();
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
