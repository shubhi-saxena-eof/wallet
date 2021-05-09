# wallet


Requirements to run this locally -
Apache Maven 3
Java version: 11
Postman (Optional)

Postman Request Collection: https://www.getpostman.com/collections/4fcc500cd9d27a26e3cf

Steps:
1. Create User
2. Create Wallet for User
3. Get Balance for User OR
4. Create transaction for two wallets


Assumptions:
1. A user's phone number needs to be unique. In other words, one phone number per user and vice versa.
2. A user can only have at most one wallet.
3. Each wallet can have a personalised minimum balance amount.
4. All transactions take place from one wallet to another. Since we cannot transact with any external entity, an initial balance can be set at the time of wallet creation. This amount needs to be equal to or more than minimum balance amount.
5. Minimum balance amount of wallet, initial balance amount for wallet and transaction amounts can be either zero or positive only.
6. A transaction cannot take place within the same wallet.