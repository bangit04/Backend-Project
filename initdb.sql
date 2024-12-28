-- Tạo kiểu ENUM cho các trường
CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE', 'OTHER');
CREATE TYPE user_type_enum AS ENUM ('OWNER', 'ADMIN', 'USER');
CREATE TYPE user_status_enum AS ENUM ('NONE', 'ACTIVE', 'INACTIVE');
CREATE TYPE payment_method_enum AS ENUM ('MOMO', 'VNPAY');

-- Table: user
CREATE TABLE "user" (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR,
    last_name VARCHAR,
    gender gender_enum,
    date_of_birth DATE,
    phone VARCHAR,
    email VARCHAR,
    username VARCHAR,
    password VARCHAR,
    type user_type_enum,
    status user_status_enum,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
-- Table: address
create table "address"(
	id BIGINT primary key,
    apartmentNumber VARCHAR,
   	floor VARCHAR,
    building VARCHAR,
   	street_number VARCHAR,
    street VARCHAR,
    city VARCHAR,
    country VARCHAR,
    address_type INT,
   	user_id INT REFERENCES "user" (id),
   	created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: role
CREATE TABLE role (
    id BIGINT PRIMARY KEY,
    name TEXT,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: user_has_role
CREATE TABLE user_has_role (
    user_id BIGINT REFERENCES "user" (id),
    role_id BIGINT REFERENCES role (id),
    PRIMARY KEY (user_id, role_id)
);

-- Table: permission
CREATE TABLE permission (
    id BIGINT PRIMARY KEY,
    method TEXT,
    url VARCHAR,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: role_has_permission
CREATE TABLE role_has_permission (
    role_id BIGINT REFERENCES role (id),
    permission_id BIGINT REFERENCES permission (id),
    PRIMARY KEY (role_id, permission_id)
);

-- Table: group
CREATE TABLE "group" (
    id BIGINT PRIMARY KEY,
    name TEXT,
    role_id BIGINT REFERENCES role (id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: group_has_user
CREATE TABLE group_has_user (
    user_id BIGINT REFERENCES "user" (id),
    group_id BIGINT REFERENCES "group" (id),
    PRIMARY KEY (user_id, group_id)
);

-- Table: category
CREATE TABLE category (
    id BIGINT PRIMARY KEY,
    name TEXT,
    description TEXT
);

-- Table: unit
CREATE TABLE unit (
    id BIGINT PRIMARY KEY,
    name TEXT,
    description TEXT
);

-- Table: product
CREATE TABLE product (
    id BIGINT PRIMARY KEY,
    name TEXT,
    category_id BIGINT REFERENCES category (id),
    unit_id BIGINT REFERENCES unit (id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: inventory
CREATE TABLE inventory (
    id BIGINT PRIMARY KEY,
    product_id BIGINT REFERENCES product (id),
    category_id BIGINT REFERENCES category (id),
    unit_id BIGINT REFERENCES unit (id),
    quantity INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: order
CREATE TABLE "order" (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES "user" (id),
    order_date TIMESTAMP,
    status INT,
    payment_status INT,
    amount DECIMAL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: order_item
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY,
    order_id BIGINT REFERENCES "order" (id),
    product_id BIGINT REFERENCES product (id),
    quantity INT,
    price DECIMAL
);

-- Table: payment
CREATE TABLE payment (
    id BIGINT PRIMARY KEY,
    order_id BIGINT REFERENCES "order" (id),
    date TIMESTAMP,
    method payment_method_enum,
    amount DECIMAL,
    status INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: shipping
CREATE TABLE shipping (
    id BIGINT PRIMARY KEY,
    order_id BIGINT REFERENCES "order" (id),
    address VARCHAR,
    method VARCHAR,
    status INT,
    date TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table: review
CREATE TABLE review (
    id BIGINT PRIMARY KEY,
    product_id BIGINT REFERENCES product (id),
    user_id BIGINT REFERENCES "user" (id),
    rating INT,
    review_text TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO users (id, first_name, last_name, gender, date_of_birth, phone, email, username, password, type, status, created_at, updated_at) VALUES
(1, 'Nguyen', 'An', 'MALE', '1990-05-12', '0123456789', 'nguyen.an@email.com', 'nguyenan', 'password123', 'ADMIN', 'ACTIVE', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(2, 'Tran', 'Bich', 'FEMALE', '1992-07-20', '0123456790', 'tran.bich@email.com', 'tranbich', 'password456', 'USER', 'ACTIVE', '2024-01-02 11:00:00', '2024-01-02 11:00:00'),
(3, 'Le', 'Cao', 'MALE', '1985-10-15', '0123456791', 'le.cao@email.com', 'lecao', 'password789', 'USER', 'INACTIVE', '2024-01-03 12:00:00', '2024-01-03 12:00:00'),
(4, 'Pham', 'Duy', 'MALE', '1988-03-05', '0123456792', 'pham.duy@email.com', 'phamduy', 'password321', 'ADMIN', 'ACTIVE', '2024-01-04 13:00:00', '2024-01-04 13:00:00'),
(5, 'Hoang', 'Thu', 'FEMALE', '1995-08-30', '0123456793', 'hoang.thu@email.com', 'hoangthu', 'password654', 'USER', 'ACTIVE', '2024-01-05 14:00:00', '2024-01-05 14:00:00'),
(6, 'Nguyen', 'Hieu', 'MALE', '1982-06-17', '0123456794', 'nguyen.hieu@email.com', 'nguyenhieu', 'password987', 'USER', 'ACTIVE', '2024-01-06 15:00:00', '2024-01-06 15:00:00'),
(7, 'Truong', 'My', 'FEMALE', '1993-01-11', '0123456795', 'truong.my@email.com', 'truongmy', 'password222', 'ADMIN', 'ACTIVE', '2024-01-07 16:00:00', '2024-01-07 16:00:00'),
(8, 'Vu', 'Duc', 'MALE', '1991-09-23', '0123456796', 'vu.duc@email.com', 'vuduc', 'password333', 'USER', 'INACTIVE', '2024-01-08 17:00:00', '2024-01-08 17:00:00'),
(9, 'Pham', 'Lan', 'FEMALE', '1996-12-02', '0123456797', 'pham.lan@email.com', 'phamlan', 'password444', 'ADMIN', 'ACTIVE', '2024-01-09 18:00:00', '2024-01-09 18:00:00'),
(10, 'Nguyen', 'Nhat', 'MALE', '1990-04-14', '0123456798', 'nguyen.nhat@email.com', 'nguyennhat', 'password555', 'USER', 'ACTIVE', '2024-01-10 19:00:00', '2024-01-10 19:00:00'),
(11, 'Mai', 'Thanh', 'FEMALE', '1987-02-22', '0123456799', 'mai.thanh@email.com', 'maithanh', 'password666', 'USER', 'ACTIVE', '2024-01-11 20:00:00', '2024-01-11 20:00:00'),
(12, 'Duong', 'Linh', 'FEMALE', '1992-11-17', '0123456800', 'duong.linh@email.com', 'duonglinh', 'password777', 'ADMIN', 'ACTIVE', '2024-01-12 21:00:00', '2024-01-12 21:00:00'),
(13, 'Bui', 'Hoang', 'MALE', '1994-01-07', '0123456801', 'bui.hoang@email.com', 'buihoang', 'password888', 'USER', 'INACTIVE', '2024-01-13 22:00:00', '2024-01-13 22:00:00'),
(14, 'Nguyen', 'Sang', 'MALE', '1984-10-10', '0123456802', 'nguyen.sang@email.com', 'nguyensang', 'password999', 'USER', 'ACTIVE', '2024-01-14 23:00:00', '2024-01-14 23:00:00'),
(15, 'Tran', 'Tuan', 'MALE', '1990-09-25', '0123456803', 'tran.tuan@email.com', 'trantuan', 'password112', 'ADMIN', 'ACTIVE', '2024-01-15 00:00:00', '2024-01-15 00:00:00'),
(16, 'Vo', 'Vinh', 'MALE', '1987-12-11', '0123456804', 'vo.vinh@email.com', 'vovinh', 'password223', 'USER', 'ACTIVE', '2024-01-16 01:00:00', '2024-01-16 01:00:00'),
(17, 'Luong', 'Tuyet', 'FEMALE', '1993-04-01', '0123456805', 'luong.tuyet@email.com', 'luongtuyet', 'password334', 'ADMIN', 'ACTIVE', '2024-01-17 02:00:00', '2024-01-17 02:00:00'),
(18, 'Chau', 'Tung', 'MALE', '1989-08-25', '0123456806', 'chau.tung@email.com', 'chautung', 'password445', 'USER', 'INACTIVE', '2024-01-18 03:00:00', '2024-01-18 03:00:00'),
(19, 'Nguyen', 'Cuong', 'MALE', '1997-05-05', '0123456807', 'nguyen.cuong@email.com', 'nguyencuong', 'password556', 'USER', 'ACTIVE', '2024-01-19 04:00:00', '2024-01-19 04:00:00'),
(20, 'Tran', 'Ngoc', 'FEMALE', '1998-12-30', '0123456808', 'tran.ngoc@email.com', 'tranngoc', 'password667', 'ADMIN', 'ACTIVE', '2024-01-20 05:00:00', '2024-01-20 05:00:00');
