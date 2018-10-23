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

  // creating array of product type
  products: Array<Product> = [];
  
  ngOnInit() {
    //initializing this array with all the products available i.e basically 3 products only(pre, post, dongle)
    this.products = this.productService.allProducts();
    console.log(this.products);
  }

}
