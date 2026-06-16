USE bank;

CREATE TABLE customer (
    customerId INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE bank_account (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    account_type VARCHAR(20) NOT NULL,
    account_balance DECIMAL(15,2) DEFAULT 0,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE transaction (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_id INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES bank_account(account_id)
);