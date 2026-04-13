# Installing Postgres in Ubuntu

To install the **latest PostgreSQL and `psql`** on Ubuntu, the best way is to use the official PostgreSQL repository (it’s more up-to-date than Ubuntu’s default packages).

Here’s a clean, reliable setup 👇

---

# 🚀 1. Update system

```bash
sudo apt update
sudo apt upgrade -y
```

---

# 📦 2. Install required tools

```bash
sudo apt install -y wget ca-certificates
```

---

# 🔑 3. Add PostgreSQL official repository

Import the GPG key:

```bash
wget -qO - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo gpg --dearmor -o /usr/share/keyrings/postgresql.gpg
```

Add the repo:

```bash
echo "deb [signed-by=/usr/share/keyrings/postgresql.gpg] http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" | sudo tee /etc/apt/sources.list.d/pgdg.list
```

---

# 🔄 4. Update package list again

```bash
sudo apt update
```

---

# 🐘 5. Install latest PostgreSQL + psql

To install latest version:

```bash
sudo apt install -y postgresql postgresql-client
```

👉 This installs:

* PostgreSQL server
* `psql` CLI tool

---

# ✅ 6. Verify installation

Check version:

```bash
psql --version
```

Check service:

```bash
sudo systemctl status postgresql
```

---

# 🔐 7. Access PostgreSQL

Switch to postgres user:

```bash
sudo -i -u postgres
```

Open psql:

```bash
psql
```

---

# ⚙️ 8. (Optional) Set password

Inside `psql`:

```sql
ALTER USER postgres PASSWORD 'yourpassword';
```

Exit:

```bash
\q
```

---

# 💡 Pro Tips

### 🔹 Install specific version (if needed)

```bash
sudo apt install postgresql-16
```

### 🔹 Enable auto start

```bash
sudo systemctl enable postgresql
```

### 🔹 Restart service

```bash
sudo systemctl restart postgresql
```

---

# ⚠️ Common mistake

If you install via:

```bash
sudo apt install postgresql
```

(without adding repo)

👉 You’ll get an **older version** from Ubuntu repo.

---

If you want, I can also show you:

* How to connect from your app (Spring Boot, Node, etc.)
* Or how to fully remove old PostgreSQL before reinstalling cleanly

