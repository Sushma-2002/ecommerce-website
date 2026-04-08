import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CartService } from '../services/cart.service';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  checkoutForm: FormGroup;
  loading = false;
  errorMessage = '';
  orderSuccess: { orderId: number; total: number } | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private cartService: CartService,
    private orderService: OrderService,
    private router: Router
  ) {
    this.checkoutForm = this.fb.group({
      customerName: ['', Validators.required],
      customerEmail: ['', [Validators.required, Validators.email]],
      shippingAddress: ['', Validators.required],
      paymentMethod: ['CREDIT_CARD', Validators.required],
      cardNumber: [''],
      cardExpiry: [''],
      cardCvv: [''],
    });
  }

  get showCardFields(): boolean {
    const method = this.checkoutForm.get('paymentMethod')?.value;
    return method === 'CREDIT_CARD' || method === 'DEBIT_CARD';
  }

  ngOnInit(): void {
    if (!this.cartService.getItems().length) {
      this.router.navigate(['/cart']);
    }
  }

  get totalPrice(): number {
    return this.cartService.getTotalPrice();
  }

  get itemsCount(): number {
    return this.cartService.getTotalCount();
  }

  onSubmit(): void {
    this.errorMessage = '';
    this.orderSuccess = null;
    if (this.checkoutForm.invalid) {
      this.checkoutForm.markAllAsTouched();
      return;
    }

    const formVal = this.checkoutForm.value;
    if (this.showCardFields) {
      const digits = (formVal.cardNumber || '').replace(/\D/g, '');
      if (digits.length < 13 || digits.length > 19) {
        this.errorMessage = 'Enter a valid card number (13–19 digits).';
        return;
      }
      if (!formVal.cardExpiry || !/^\d{2}\/\d{2}$/.test(formVal.cardExpiry.replace(/\s/g, ''))) {
        this.errorMessage = 'Enter expiry as MM/YY.';
        return;
      }
      if (!formVal.cardCvv || formVal.cardCvv.length < 3) {
        this.errorMessage = 'Enter a valid CVV (3–4 digits).';
        return;
      }
    }

    const items = this.cartService.getItems().map((i) => ({
      productId: i.product.id,
      quantity: i.quantity,
    }));
    const userId = this.authService.getUserId();
    const body = {
      userId: userId ?? undefined,
      customerName: formVal.customerName,
      customerEmail: formVal.customerEmail,
      shippingAddress: formVal.shippingAddress,
      paymentMethod: formVal.paymentMethod,
      cardNumber: this.showCardFields ? formVal.cardNumber : undefined,
      cardExpiry: this.showCardFields ? formVal.cardExpiry : undefined,
      cardCvv: this.showCardFields ? formVal.cardCvv : undefined,
      items,
    };

    this.loading = true;
    this.orderService.create(body).subscribe({
      next: (order) => {
        this.loading = false;
        this.cartService.clear();
        this.orderSuccess = { orderId: order.id, total: order.totalAmount };
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage =
          err.error?.message || 'Unable to place order. Please try again.';
      },
    });
  }
}
