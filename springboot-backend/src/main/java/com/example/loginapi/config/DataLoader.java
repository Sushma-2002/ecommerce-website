package com.example.loginapi.config;

import com.example.loginapi.entity.Product;
import com.example.loginapi.entity.User;
import com.example.loginapi.repository.ProductRepository;
import com.example.loginapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DataLoader(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", "admin", "admin@shop.com"));
        }

        productRepository.deleteAll();
        productRepository.save(new Product("Wireless Headphones", "Noise-cancelling over-ear headphones with 30h battery", 7499.00, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400", "Electronics"));
        productRepository.save(new Product("Running Shoes", "Lightweight breathable running shoes for all terrains", 10999.00, "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400", "Sports"));
        productRepository.save(new Product("Stainless Steel Water Bottle", "1L insulated bottle, keeps drinks cold 24h", 2999.00, "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=400", "Accessories"));
        productRepository.save(new Product("Backpack", "Laptop compartment, water-resistant, 25L", 4999.00, "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400", "Accessories"));
        productRepository.save(new Product("Smart Watch", "Fitness tracking, heart rate, GPS, 7-day battery", 16999.00, "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400", "Electronics"));
        productRepository.save(new Product("Desk Lamp", "LED adjustable brightness, USB charging port", 3899.00, "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=400", "Home"));
        productRepository.save(new Product("Coffee Maker", "Programmable 12-cup drip coffee maker", 6699.00, "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400", "Home"));
        productRepository.save(new Product("Yoga Mat", "Non-slip, eco-friendly TPE, 6mm thick", 2499.00, "https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?w=400", "Sports"));
        // Electronics
        productRepository.save(new Product("Bluetooth Speaker", "Portable 20W stereo sound, 12h playback, waterproof", 3499.00, "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=400", "Electronics"));
        productRepository.save(new Product("Power Bank", "20000mAh dual USB, fast charging, compact", 1999.00, "https://images.unsplash.com/photo-1609091839311-d5365f9ff1c5?w=400", "Electronics"));
        productRepository.save(new Product("Earbuds", "True wireless, 24h total battery, touch controls", 4499.00, "https://images.unsplash.com/photo-1598331668826-20cecc596b86?w=400", "Electronics"));
        productRepository.save(new Product("Tablet Stand", "Adjustable aluminium stand for tablet and phone", 1299.00, "https://images.unsplash.com/photo-1625842268584-8f3296236761?w=400", "Electronics"));
        productRepository.save(new Product("Webcam", "1080p HD, built-in mic, for work and streaming", 5499.00, "https://images.unsplash.com/photo-1650017067794-80fd3a99a104?w=400", "Electronics"));
        // Home appliances
        productRepository.save(new Product("Electric Kettle", "1.8L, boil-dry protection, 360° base", 1899.00, "https://images.unsplash.com/photo-1563822249365-2c3a64f00d6a?w=400", "Home"));
        productRepository.save(new Product("Air Fryer", "5.5L digital, 8 presets, non-stick basket", 7999.00, "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=400", "Home"));
        productRepository.save(new Product("Microwave Oven", "23L solo, 800W, child safety lock", 6499.00, "https://images.unsplash.com/photo-1585659722983-3a675dabf23d?w=400", "Home"));
        productRepository.save(new Product("Vacuum Cleaner", "Cordless stick, 30min runtime, 2-in-1 handheld", 9999.00, "https://images.unsplash.com/photo-1558317374-067fb5f30001?w=400", "Home"));
        productRepository.save(new Product("Steam Iron", "Non-stick soleplate, 1800W, dry and steam", 2499.00, "https://images.unsplash.com/photo-1566204773863-cf63e6d4ab88?w=400", "Home"));
        productRepository.save(new Product("Mixer Grinder", "750W, 3 jars, multi-purpose grinding", 4299.00, "https://images.unsplash.com/photo-1603073163307-66d7450dc157?w=400", "Home"));
        productRepository.save(new Product("Toaster", "4-slice, 6 browning levels, crumb tray", 2799.00, "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400", "Home"));
        productRepository.save(new Product("Air Purifier", "HEPA filter, 3-speed, for rooms up to 300 sq ft", 8999.00, "https://images.unsplash.com/photo-1616486338812-3dadae4d4aac?w=400", "Home"));
    }
}
