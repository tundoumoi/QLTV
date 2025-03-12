-- bảng account
CREATE TABLE Account (
	AccountId INT PRIMARY KEY,
	username VARCHAR(50) UNIQUE NOT NULL,
	APass VARCHAR(255) NOT NULL
);

-- Bảng Admin (kế thừa Person)
CREATE TABLE Admin (
    ADid VARCHAR(50) PRIMARY KEY,
	Aname VARCHAR(50),
	ADbirthDate DATE,
    ADgender NVARCHAR(10),
    ADaddress VARCHAR(255),
    AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);

-- Bảng Employee (kế thừa Person)
CREATE TABLE Employee (
    Eid VARCHAR(50) PRIMARY KEY,
	Ename VARCHAR(50),
    EbirthDate DATE,
    Egender NVARCHAR(10),
    Eaddress NVARCHAR(255),
    Eposition NVARCHAR(50),
    Esalary FLOAT,
    EstartDate DATE,
	AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);

-- Bảng Customer (kế thừa Person)
CREATE TABLE Customer (
    Cid VARCHAR(50) PRIMARY KEY,
	Cname VARCHAR(50),
	CbirthDate DATE,
    Cgender NVARCHAR(10),
    Caddress NVARCHAR(255),
    CphoneNumber VARCHAR(15),
    Cemail VARCHAR(100),
    CtotalPayment FLOAT DEFAULT 0,
	AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);

-- Bảng CustomerBuy (kế thừa Customer)
CREATE TABLE CustomerBuy (
    Cid VARCHAR(50) PRIMARY KEY,
    totalPurchase FLOAT DEFAULT 0,
    membershipLevel NVARCHAR(50),
	FOREIGN KEY (Cid) REFERENCES Customer(Cid) ON DELETE CASCADE
);

-- Bảng CustomerBorrow (kế thừa Customer)
CREATE TABLE CustomerBorrow (
    cardId VARCHAR(50) PRIMARY KEY,
    Cid VARCHAR(50) UNIQUE NOT NULL,
    typeCard NVARCHAR(50), -- Đồng, Bạc, Vàng
    cardExpiry DATE,
    registrationDate DATE,
    expirationDate DATE,
    borrowLimit INT CHECK (borrowLimit >= 0),
    FOREIGN KEY (Cid) REFERENCES Customer(Cid) ON DELETE CASCADE
);

-- Bảng Book
CREATE TABLE Book (
    bookId VARCHAR(50) PRIMARY KEY,
    title NVARCHAR(100),
    author NVARCHAR(100),
    publisher NVARCHAR(100),
    publishedDate DATE,
    price FLOAT CHECK (price >= 0),
    quantity INT CHECK (quantity >= 0),
    type NVARCHAR(50), -- VD : Cười, Kinh dị, Kiến thức
    language NVARCHAR(20) -- VD : Việt, Anh, Nhật
);

-- Bảng BookBorrow (mối quan hệ giữa CustomerBorrow và Book)
CREATE TABLE BorrowBook (
    borrowId INT PRIMARY KEY IDENTITY,
    cardId VARCHAR(50),
    bookId VARCHAR(50),
    customerBorrow VARCHAR(50),
    borrowDate DATE,
    endDate DATE,
    FOREIGN KEY (cardId) REFERENCES CustomerBorrow(cardId) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE
);

-- Bảng BuyBook (mối quan hệ giữa CustomerBuy và Book)
CREATE TABLE BuyBook (
    orderId VARCHAR(50) PRIMARY KEY,
    Cid VARCHAR(50),
    bookId VARCHAR(50),
    quantity INT CHECK (quantity > 0),
    totalPrice FLOAT CHECK (totalPrice >= 0),
    purchaseDate DATE,
    FOREIGN KEY (Cid) REFERENCES CustomerBuy(Cid) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE
);

-- Bảng Promotion (Admin quản lý)
CREATE TABLE Promotion (
	Pid	VARCHAR(10) PRIMARY KEY,
    ADid VARCHAR(50) ,
    Pname NVARCHAR(100),
    discountRate FLOAT CHECK (discountRate >= 0 AND discountRate <= 100),
    startDate DATE,
    endDate DATE,
    Pdescription NVARCHAR(255),
    FOREIGN KEY (ADid) REFERENCES Admin(ADid) ON DELETE SET NULL
);

-- Bảng voucher
CREATE TABLE Voucher (
	VoucherID VARCHAR(10) PRIMARY KEY, 
	Pid VARCHAR(10),
	discountRate INT,
	quantity INT,
	FOREIGN KEY (Pid) REFERENCES Promotion(Pid) ON DELETE CASCADE 
)

-- Bảng Report (Khách hàng báo cáo về sách)
CREATE TABLE Report (
    reportId INT PRIMARY KEY IDENTITY,
    customerId VARCHAR(50),
    bookId VARCHAR(50),
    title NVARCHAR(100),
    reportDate DATE DEFAULT GETDATE(),
    content NVARCHAR(MAX),
    FOREIGN KEY (customerId) REFERENCES Customer(Cid) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE
);
--Bảng Bill
CREATE TABLE Bill (
    invoiceCode INT PRIMARY KEY,
    bookId VARCHAR(50),
	Cid VARCHAR(50),
    employeeCode VARCHAR(50),
    billTime DATETIME,
    unitPrice FLOAT,
    FOREIGN KEY (bookId) REFERENCES Book(bookId),
	FOREIGN KEY (Cid) REFERENCES Customer(Cid),
    FOREIGN KEY (employeeCode) REFERENCES Employee(Eid)
);