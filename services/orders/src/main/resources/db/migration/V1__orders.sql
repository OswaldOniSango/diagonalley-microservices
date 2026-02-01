CREATE TABLE IF NOT EXISTS orders (
  id BIGINT PRIMARY KEY,
  user_email VARCHAR(180) NOT NULL,
  total_amount FLOAT NOT NULL,
  status VARCHAR(40) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items (
  id BIGINT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  unit_price FLOAT NOT NULL
);
