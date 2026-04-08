import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { UserService, UserProfile } from '../services/user.service';
import { OrderService, OrderResponse } from '../services/order.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  profile: UserProfile | null = null;
  orders: OrderResponse[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.errorMessage = '';

    this.userService.getProfile().subscribe({
      next: (p) => {
        this.profile = p;
        this.loadOrders();
      },
      error: (err) => {
        this.loading = false;
        if (err.status === 401) {
          this.authService.logout();
          this.router.navigate(['/login']);
        } else {
          this.errorMessage = 'Could not load profile.';
        }
      },
    });
  }

  loadOrders(): void {
    this.orderService.getAll().subscribe({
      next: (orders) => {
        this.orders = orders;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    const d = new Date(dateStr);
    return d.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  formatPayment(method?: string, last4?: string): string {
    if (!method) return '';
    const m = method.replace(/_/g, ' ').toLowerCase();
    if (last4) return `${m} •••• ${last4}`;
    return m;
  }
}
