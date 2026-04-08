import { Component } from '@angular/core';
import { CartService } from '../services/cart.service';
import { CartItem } from '../models/cart-item';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent {
  items: CartItem[] = [];

  constructor(private cartService: CartService) {
    this.cartService.cart$.subscribe((items) => (this.items = items));
  }

  get totalPrice(): number {
    return this.cartService.getTotalPrice();
  }

  get totalCount(): number {
    return this.cartService.getTotalCount();
  }

  updateQuantity(productId: number, qty: number): void {
    this.cartService.updateQuantity(productId, qty);
  }

  remove(productId: number): void {
    this.cartService.remove(productId);
  }
}
