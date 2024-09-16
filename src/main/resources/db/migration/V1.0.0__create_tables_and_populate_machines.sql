-- Criação da tabela Machine
CREATE TABLE IF NOT EXISTS machine (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    capture_rate DECIMAL(5, 2) NOT NULL CHECK (capture_rate >= 0),
    has_printer BOOLEAN NOT NULL DEFAULT FALSE,
    max_payment_value DECIMAL(10, 2) NOT NULL,
    max_installments_without_interest INT NOT NULL CHECK (max_installments_without_interest >= 0),
    price DECIMAL(6, 2) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE
);

-- Criação da tabela PaymentCategory associada à Machine
CREATE TABLE IF NOT EXISTS machine_payment_category (
    machine_id BIGINT,
    payment_category ENUM('CREDIT_CARD', 'DEBIT_CARD', 'QR_CODE', 'PIX', 'VOUCHER', 'ALL'),
    PRIMARY KEY (machine_id, payment_category),
    FOREIGN KEY (machine_id) REFERENCES machine(id) ON DELETE CASCADE
);

-- Inserção de 5 registros na tabela Machine
INSERT INTO machine (model, capture_rate, has_printer, max_payment_value, max_installments_without_interest, price, is_active) VALUES
('Laranjinha +', 1.5, TRUE, 10000.00, 12, 999.99, TRUE),
('Laranjinha Smart', 2.0, TRUE, 15000.00, 10, 849.50, TRUE),
('Laranjinha + TEF', 1.8, TRUE, 12000.00, 6, 650.00, FALSE),
('Laranjinha PIN PAD', 1.6, TRUE, 8000.00, 3, 499.99, TRUE),
('Mobile RX', 2.2, FALSE, 20000.00, 24, 1199.99, TRUE);

-- Inserção das categorias de pagamento associadas a cada machine criada
INSERT INTO machine_payment_category (machine_id, payment_category) VALUES
((SELECT id FROM machine WHERE model = 'Laranjinha +'), 'CREDIT_CARD'),
((SELECT id FROM machine WHERE model = 'Laranjinha +'), 'DEBIT_CARD'),
((SELECT id FROM machine WHERE model = 'Laranjinha Smart'), 'DEBIT_CARD'),
((SELECT id FROM machine WHERE model = 'Laranjinha Smart'), 'QR_CODE'),
((SELECT id FROM machine WHERE model = 'Laranjinha + TEF'), 'VOUCHER'),
((SELECT id FROM machine WHERE model = 'Mobile RX'), 'PIX'),
((SELECT id FROM machine WHERE model = 'Laranjinha PIN PAD'), 'ALL');