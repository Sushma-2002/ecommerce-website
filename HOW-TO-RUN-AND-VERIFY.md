# How to Run & Verify the Shopping Website

## 1. Start the Backend (Spring Boot)

Open a terminal and run:

```bash
cd springboot-backend
mvn spring-boot:run
```

**Wait until you see** something like:
```
Started LoginApiApplication in X.XXX seconds
```

- Backend runs at: **http://localhost:8080**
- Quick check: open **http://localhost:8080/api/products** in a browser — you should see JSON list of products.

---

## 2. Start the Frontend (Angular)

Open a **second** terminal and run:

```bash
cd login-app
npm start
```

**Wait until you see** something like:
```
✔ Compiled successfully.
** Angular Live Development Server is listening on localhost:4200 **
```

- Frontend runs at: **http://localhost:4200**

---

## 3. Verify in the Browser

1. Open **http://localhost:4200** in your browser.

2. **Discover** (product listing)
   - You should see the product grid (e.g. Wireless Headphones, Running Shoes, etc.).
   - Use the **Category** dropdown to filter.
   - Click **Add to cart** on any product.

3. **Cart**
   - Click **Cart** in the header (badge should show the number of items).
   - Change quantity with **−** / **+**, or **Remove** an item.
   - Click **Proceed to checkout**.

4. **Checkout**
   - Enter name, email, and shipping address.
   - Click **Place order**.
   - You should see a **Thank you!** message with order number and total.

5. **Login** (optional)
   - Click **Login** in the header.
   - Use **admin** / **admin** (or any username with password **password**).
   - After login you should see the store (Bazaar) and **Logout** instead of **Login**.

---

## Quick Backend-Only Check

If you only want to confirm the backend:

- **Products:** http://localhost:8080/api/products  
- **H2 console:** http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:shopdb`, user: `sa`, password: empty)

---

## If Something Fails

| Issue | What to do |
|-------|-------------|
| Backend: "Cannot find Java" or Maven errors | Install JDK 17 and Maven, then run `mvn spring-boot:run` again. |
| Frontend: "ng not found" or npm errors | Run `npm install` in `login-app`, then `npm start`. |
| Frontend shows "Cannot reach server" or products don’t load | Ensure the backend is running on port 8080 first, then refresh the app. |
| CORS errors in browser console | Backend already allows `http://localhost:4200`. Restart backend and refresh. |
