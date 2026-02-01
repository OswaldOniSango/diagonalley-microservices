CREATE TABLE IF NOT EXISTS cart_items (
  id BIGINT PRIMARY KEY,
  user_email VARCHAR(180) NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  unit_price FLOAT NOT NULL
);
