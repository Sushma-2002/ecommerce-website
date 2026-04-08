import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../models/product';
import { CartItem } from '../models/cart-item';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private items: CartItem[] = [];
  private cartSubject = new BehaviorSubject<CartItem[]>([]);
  cart$ = this.cartSubject.asObservable();

  add(product: Product, quantity: number = 1): void {
    const existing = this.items.find((i) => i.product.id === product.id);
    if (existing) {
      existing.quantity += quantity;
    } else {
      this.items.push({ product, quantity });
    }
    this.emit();
  }

  remove(productId: number): void {
    this.items = this.items.filter((i) => i.product.id !== productId);
    this.emit();
  }

  updateQuantity(productId: number, quantity: number): void {
    const item = this.items.find((i) => i.product.id === productId);
    if (item) {
      if (quantity <= 0) {
        this.remove(productId);
      } else {
        item.quantity = quantity;
        this.emit();
      }
    }
  }

  getItems(): CartItem[] {
    return [...this.items];
  }

  getQuantity(productId: number): number {
    const item = this.items.find((i) => i.product.id === productId);
    return item ? item.quantity : 0;
  }

  getTotalCount(): number {
    return this.items.reduce((sum, i) => sum + i.quantity, 0);
  }

  getTotalPrice(): number {
    return this.items.reduce((sum, i) => sum + i.product.price * i.quantity, 0);
  }

  clear(): void {
    this.items = [];
    this.emit();
  }

  private emit(): void {
    this.cartSubject.next(this.getItems());
  }
}
