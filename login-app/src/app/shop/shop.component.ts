import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { CartService } from '../services/cart.service';
import { Product } from '../models/product';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css'],
})
export class ShopComponent implements OnInit {
  products: Product[] = [];
  loading = true;
  selectedCategory = '';
  cartItems: { [productId: number]: number } = {};

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.cartService.cart$.subscribe(() => this.updateCartQuantities());
  }

  updateCartQuantities(): void {
    const map: { [productId: number]: number } = {};
    this.cartService.getItems().forEach((i) => (map[i.product.id] = i.quantity));
    this.cartItems = { ...map };
  }

  loadProducts(): void {
    this.loading = true;
    const category = this.selectedCategory || undefined;
    this.productService.getAll(category).subscribe({
      next: (data) => {
        this.products = data;
        this.updateCartQuantities();
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  onCategoryChange(): void {
    this.loadProducts();
  }

  addToCart(product: Product): void {
    this.cartService.add(product, 1);
  }

  getQuantity(productId: number): number {
    return this.cartItems[productId] ?? 0;
  }

  increase(product: Product): void {
    this.cartService.add(product, 1);
  }

  decrease(product: Product): void {
    const qty = this.getQuantity(product.id);
    this.cartService.updateQuantity(product.id, qty - 1);
  }
}
