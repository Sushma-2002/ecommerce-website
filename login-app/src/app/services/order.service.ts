import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface OrderItemRequest {
  productId: number;
  quantity: number;
}

export interface OrderRequest {
  userId?: number;
  customerName: string;
  customerEmail: string;
  shippingAddress: string;
  paymentMethod?: string;
  cardNumber?: string;
  cardExpiry?: string;
  cardCvv?: string;
  items: OrderItemRequest[];
}

export interface OrderItemResponse {
  productId: number;
  productName: string;
  unitPrice: number;
  quantity: number;
}

export interface OrderResponse {
  id: number;
  customerName: string;
  customerEmail: string;
  shippingAddress: string;
  paymentMethod?: string;
  cardLast4?: string;
  createdAt: string;
  totalAmount: number;
  items: OrderItemResponse[];
}

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private apiUrl = `${environment.apiUrl}/orders`;

  constructor(private http: HttpClient) {}

  create(order: OrderRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(this.apiUrl, order);
  }

  getAll(): Observable<OrderResponse[]> {
    return this.http.get<OrderResponse[]>(this.apiUrl);
  }

  getById(id: number): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(`${this.apiUrl}/${id}`);
  }
}
