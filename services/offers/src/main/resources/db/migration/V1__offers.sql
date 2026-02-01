CREATE TABLE IF NOT EXISTS offers (
  id BIGINT PRIMARY KEY,
  product_id BIGINT NOT NULL,
  discount_percent FLOAT NOT NULL
);
