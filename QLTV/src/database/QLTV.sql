﻿-- bảng accoun
create DataBase QLTV
use QLTV
CREATE TABLE Account (
	AccountId INT PRIMARY KEY,
	username VARCHAR(50) UNIQUE NOT NULL,
	APass VARCHAR(255) NOT NULL
);

-- Bảng Admin (kế thừa Person)
CREATE TABLE Admin (
    ADid VARCHAR(50) PRIMARY KEY,
	Aname VARCHAR(50),
	Assn VARCHAR(50),
	ADbirthDate DATE,
    ADgender NVARCHAR(10),
	ADphoneNumber NVARCHAR(10),
	ADemail NVARCHAR(50),
    ADaddress VARCHAR(255),
    AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);
SELECT * FROM Account a inner join admin ad on a.AccountId = ad.AccountId
-- Bảng Employee (kế thừa Person)
CREATE TABLE Employee (
    Eid VARCHAR(50) PRIMARY KEY,
	Ename VARCHAR(50),
	Essn VARCHAR(50),
    EbirthDate DATE,
    Egender NVARCHAR(10),
	EphoneNumber NVARCHAR(10),
	EDemail NVARCHAR(50),
    Eaddress VARCHAR(255),
    Eposition NVARCHAR(50),
    Esalary FLOAT,
    EstartDate DATE ,
	AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);

-- Bảng Customer (kế thừa Person)
-- thieu sdt email account id
CREATE TABLE Customer (
    Cid VARCHAR(50) PRIMARY KEY,
	Cname VARCHAR(50),
	Cssn VARCHAR(50),
	CbirthDate DATE,
    Cgender NVARCHAR(10),
	CphoneNumber NVARCHAR(10),
	Cemail NVARCHAR(50),
    Caddress VARCHAR(255),
    CtotalPayment FLOAT DEFAULT 0,--(giá trị của typeCard + totalpurchase)tăng lên khi mua sách và làm thẻ
	AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);

-- Bảng CustomerBuy (kế thừa Customer)
CREATE TABLE CustomerBuy (
    Cid VARCHAR(50) PRIMARY KEY,
    totalPurchase FLOAT DEFAULT 0,--dựa trên mua sách    <500.000: member; < 1.000.000:vip ; > 1.000.000 : platinum
    membershipLevel NVARCHAR(50),
	FOREIGN KEY (Cid) REFERENCES Customer(Cid) ON DELETE CASCADE
);

-- Bảng CustomerBorrow (kế thừa Customer)
CREATE TABLE CustomerBorrow (
    cardId VARCHAR(50) PRIMARY KEY,
    Cid VARCHAR(50) NOT NULL,
    typeCard NVARCHAR(50), -- Đồng : 50.000, Bạc 100000, Vàng : 200000
    cardExpiry DATE, --hêt tiền 1 tháng là cook 
    registrationDate DATE,
	cardValue FLOAT DEFAULT 0,--(gán giá trị theo typeCard) sau đó trừ đi khi thuê
    borrowLimit INT CHECK (borrowLimit >= 0),--Đồng 10 , bạc 15 , vàng no limit
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
    borrowDate DATE,
    endDate DATE,
	FOREIGN KEY (cardId) REFERENCES CustomerBorrow(cardId) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE
);

-- Bảng BuyBook (mối quan hệ giữa CustomerBuy và Book)
CREATE TABLE BuyBook (
    orderId VARCHAR(50) PRIMARY KEY,
    Cid VARCHAR(50) NOT NULL,
    bookId VARCHAR(50),
    quantity INT CHECK (quantity > 0),
    totalPrice FLOAT CHECK (totalPrice >= 0),
    purchaseDate DATE,
    FOREIGN KEY (Cid) REFERENCES CustomerBuy(Cid) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE
);



-- Bảng Report (Khách hàng báo cáo về sách)
CREATE TABLE Report (
    reportId INT PRIMARY KEY IDENTITY,
    customerId VARCHAR(50),
    bookId VARCHAR(50),
    reportDate DATE DEFAULT GETDATE(),
    content NVARCHAR(MAX),
    FOREIGN KEY (customerId) REFERENCES Customer(Cid) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE

);
-- Bảng voucher
CREATE TABLE Voucher (
	VoucherID VARCHAR(10) PRIMARY KEY, 
	discountRate Int CHECK (discountRate > 0 AND discountRate <= 100),--(cho Nam mua ssach)
	quantity INT Check(quantity > 0),
    startDate DATE,
    endDate DATE,
    Vdescription NVARCHAR(255)
)
--Bảng Bill
CREATE TABLE Bill (
    billID INT identity(1,1) PRIMARY KEY,
    bookId VARCHAR(50) not null,
	Cid VARCHAR(50) not null,
    Eid VARCHAR(50),
	VoucherID VARCHAR(10),
    billTime DATETIME,
    unitPrice FLOAT,
    FOREIGN KEY (bookId) REFERENCES Book(bookId),
	FOREIGN KEY (Cid) REFERENCES Customer(Cid),
    FOREIGN KEY (Eid) REFERENCES Employee(Eid),
	FOREIGN KEY (VoucherID) REFERENCES Voucher(VoucherID) ON DELETE CASCADE
);
INSERT INTO Admin (ADid, Aname, Assn, ADbirthDate, ADgender, ADphoneNumber, ADemail, ADaddress, AccountId)
VALUES 
('AD001', 'Phan Nhat Nam', '123456789', '1990-05-15', 'Nam', '0987654321', 'namphan@gmail.com', 'Hà Nội', 1),
('AD002', 'Dang Thanh Tung', '987654321', '1992-07-20', 'Nam', '0912345678', 'tungdang@gmail.com', 'TP.HCM', 2),
('AD003', 'Luong Dang Hoang Luu', '456789123', '1988-09-10', 'Nam', '0901122334', 'luuhoang@gmail.com', 'Đà Nẵng', 3),
('AD004', 'Tuan dau moi', '321654987', '1995-12-01', 'Nam', '0933221144', 'tuanmoi@gmail.com', 'Cần Thơ', 4),
('AD005', 'Manh Thang', '159753468', '1993-03-25', 'Nam', '0977885566', 'manhthang@gmail.com', 'Hải Phòng', 5);


INSERT INTO Account (AccountId, username, APass) VALUES
(1, 'nambautroi', 'namfanbacmeo'),
(2, 'ladititi', 'dititi'),
(3, 'hoangluu', 'hoangluu217'),
(4,'tundaumoi','tundaumoi1'),
(5,'manhthang','manhthang1'),
(6,'employee','employee1'),
(7,'customer','customer1');

SELECT a.AccountId, a.username , a.APass FROM Account a inner join admin ad on a.AccountId = ad.AccountId
delete from account;
select * from Account;
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B101', 'The Hidden Legacy', 'Oliver Sinclair', 'Pearson Books', '2013-06-15', 12.99, 10, 'Mystery', 'English'),
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B102', 'The Final Clue', 'Rachel Dawson', 'HarperCollins', '2016-09-22', 15.50, 8, 'Thriller', 'English'),
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B103', 'Beneath the Ruins', 'Edward Harris', 'Vintage Press', '2009-04-10', 18.75, 5, 'Historical Fiction', 'English'),
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B104', 'The Secret Diary', 'Emma Clarkson', 'Penguin Books', '2018-11-30', 9.99, 12, 'Romance', 'English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B105', 'The Forsaken Kingdom', 'William Everett', 'Orbit Publishing', '2020-07-12', 22.00, 7, 'Fantasy', 'English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B106', 'Silent Footsteps', 'Julia Bennett', 'Simon & Schuster', '2014-03-25', 14.50, 6, 'Mystery', 'English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B107', 'The Iron Crown', 'Marcus Holt', 'Tor Books', '2017-08-19', 20.75, 9, 'Fantasy', 'English'),
('B108', 'Forgotten Whispers', 'Charlotte Evans', 'Dark Horse', '2012-12-05', 13.40, 11, 'Horror', 'English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B109', 'Tides of Time', 'Hannah Richardson', 'Del Rey', '2019-05-14', 16.99, 4, 'Science Fiction', 'English'),
('B110', 'The Hidden Cove', 'Benjamin Scott', 'Macmillan', '2011-02-18', 12.20, 15, 'Adventure', 'English'),
('B111', 'The Eternal Pact', 'Daniel Stevenson', 'Orbit Publishing', '2023-01-10', 19.99, 3, 'Fantasy', 'English'),
('B112', 'The Poisoned Chalice', 'Katherine Morris', 'Penguin Books', '2006-06-28', 14.30, 6, 'Mystery', 'English'),
('B113', 'Shadow Realm', 'Vincent Howard', 'Tor Books', '2015-09-07', 21.50, 10, 'Fantasy', 'English'),
('B114', 'The Midnight Visitor', 'Sophia Grant', 'HarperCollins', '2008-07-21', 13.00, 7, 'Thriller', 'English'),
('B115', 'The Last Sanctuary', 'Patrick Wallace', 'Random House', '2014-11-10', 17.25, 9, 'Drama', 'English'),
('B116', 'The Haunting of Blackwood Manor', 'Teresa Carter', 'Dark Horse', '2021-03-18', 15.90, 5, 'Horror', 'English'),
('B117', 'Gates of Destiny', 'Nathaniel Brooks', 'Del Rey', '2010-08-26', 22.10, 4, 'Fantasy', 'English'),
('B118', 'Dark Prophecy', 'Catherine Andrews', 'Penguin Books', '2007-04-13', 18.40, 8, 'Thriller', 'English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B119', 'The Emperor’s Curse', 'Frederick Langley', 'Tor Books', '2019-12-20', 24.50, 3, 'Fantasy', 'English'),
('B120', 'The Vanishing Truth', 'Melissa Quinn', 'Simon & Schuster', '2013-09-29', 11.75, 12, 'Mystery', 'English'),
('B121', 'Rogue Nation', 'David Thornton', 'HarperCollins', '2022-06-11', 26.30, 6, 'Thriller', 'English'),
('B122', 'The Crimson Tide', 'Eliza Montgomery', 'Macmillan', '2009-10-05', 16.40, 8, 'Historical Fiction', 'English'),
('B123', 'Broken Chains', 'Samantha Price', 'Vintage Press', '2011-01-27', 14.10, 10, 'Drama', 'English'),
('B124', 'Into the Abyss', 'Alexander Foster', 'Dark Horse', '2018-05-23', 18.00, 5, 'Horror', 'English'),
('B125', 'The Silver Dagger', 'Richard Coleman', 'Penguin Books', '2005-09-18', 15.20, 6, 'Mystery', 'English'),
('B126', 'Eternal Silence', 'Jonathan Nash', 'Simon & Schuster', '2016-03-07', 17.80, 7, 'Psychological Thriller', 'English'),
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B127', 'The Winter’s Curse', 'Isabelle Norton', 'Orbit Publishing', '2020-12-01', 21.00, 4, 'Fantasy', 'English'),
('B128', 'Secrets of the Forgotten', 'Derek Walsh', 'HarperCollins', '2014-08-14', 14.50, 9, 'Mystery', 'English'),
('B129', 'The Enchanted Forest', 'Sophia Henderson', 'Tor Books', '2012-02-09', 22.75, 5, 'Fantasy', 'English'),
('B130', 'The Phantom’s Lament', 'Vincent Ellis', 'Dark Horse', '2017-06-17', 15.90, 7, 'Horror', 'English'),
('B131', 'The Fire Within', 'Amelia Hudson', 'Macmillan', '2019-07-30', 19.99, 6, 'Adventure', 'English'),
('B132', 'Darkened Skies', 'Francis Yates', 'Del Rey', '2006-04-25', 17.40, 4, 'Science Fiction', 'English'),
('B133', 'The Time Traveler’s Shadow', 'Lucas Barrett', 'Simon & Schuster', '2013-11-13', 18.99, 8, 'Science Fiction', 'English'),
('B134', 'The Blackened Rose', 'Margaret Underwood', 'Vintage Press', '2010-06-19', 12.50, 10, 'Romance', 'English'),
('B135', 'Legends of the Lost City', 'Charles Duncan', 'HarperCollins', '2015-05-10', 23.00, 5, 'Adventure', 'English'),
('B136', 'The Burning Horizon', 'Eleanor Sanders', 'Macmillan', '2021-09-28', 20.20, 6, 'Drama', 'English'),
('B137', 'The Outlaw’s Revenge', 'Henry Thornton', 'Penguin Books', '2008-12-11', 16.80, 9, 'Historical Fiction', 'English'),
('B138', 'Frozen Echoes', 'Jessica Holloway', 'Tor Books', '2016-02-05', 21.10, 4, 'Fantasy', 'English'),
('B139', 'The Twisted Path', 'Gregory Adams', 'Simon & Schuster', '2012-07-22', 15.70, 8, 'Thriller', 'English'),
('B140', 'Bi An Duoi Lop Tro Tan', 'Nguyen Van A', 'NXB Tre', '2015-07-10', 105000, 12, 'Kinh di', 'Viet'),
('B141', 'Duong Den Thanh Cong', 'Tran Minh Hoang', 'NXB Lao Dong', '2018-09-12', 98000, 10, 'Kien thuc', 'Viet'),
('B142', 'Hanh Trinh Vo Dinh', 'Le Thanh Tu', 'NXB Kim Dong', '2017-03-21', 89000, 8, 'Phieu luu', 'Viet'),
('B143', 'Bong Dem Vinh Cuu', 'Pham Thi Lan', 'NXB Van Hoc', '2020-11-05', 120000, 5, 'Kinh di', 'Viet'),
('B144', 'Cuoc Song Khong Gioi Han', 'Hoang Anh Tuan', 'NXB Tre', '2016-05-18', 115000, 6, 'Kien thuc', 'Viet'),
('B145', 'Bi Kip Thanh Cong', 'Do Thi Ngoc', 'NXB Thanh Nien', '2019-02-08', 99000, 11, 'Ky nang song', 'Viet'),
('B146', 'Hen Nhau Ngay Ay', 'Mai Xuan Hoa', 'NXB Kim Dong', '2022-08-14', 75000, 9, 'Tinh cam', 'Viet'),
('B147', 'Vung Dat Bi An', 'Trinh Huu Nam', 'NXB Van Hoc', '2014-06-20', 105000, 7, 'Phieu luu', 'Viet'),
('B148', 'Cuoc Chien Khong Hoi Ket', 'Ngo Thanh Hai', 'NXB Quan Doi', '2013-12-10', 125000, 5, 'Lich su', 'Viet'),
('B149', 'Ban Tay Mau', 'Vu Dinh Lam', 'NXB Tre', '2018-04-25', 95000, 6, 'Kinh di', 'Viet'),
('B150', 'Tam Ly Hoc Dam Dong', 'Le Huu Phuoc', 'NXB Tong Hop', '2021-10-01', 135000, 10, 'Tam ly', 'Viet'),
('B151', 'Vuot Qua So Phan', 'Tran Ngoc Minh', 'NXB Phu Nu', '2015-01-30', 89000, 4, 'Tu truyen', 'Viet'),
('B152', 'Chinh Phuc Giac Mo', 'Dinh Hoang Phong', 'NXB Thanh Nien', '2017-07-22', 99000, 6, 'Ky nang song', 'Viet'),
('B153', 'Am Anh', 'Ho Thanh Tam', 'NXB Van Hoc', '2019-06-18', 108000, 8, 'Kinh di', 'Viet'),
('B154', 'Mat Ma Phuong Dong', 'Pham Van Hau', 'NXB Tong Hop', '2016-03-12', 129000, 5, 'Trinh tham', 'Viet'),
('B155', 'Cau Chuyen Tinh Yeu', 'Nguyen Hong Nhung', 'NXB Kim Dong', '2023-05-05', 85000, 12, 'Tinh cam', 'Viet'),
('B156', 'Nhung Buoc Chan Dau Tien', 'Vo Hoai Nam', 'NXB Lao Dong', '2014-11-27', 99000, 9, 'Ky nang song', 'Viet'),
('B157', 'Bong Hinh Nguoi Xua', 'Trinh Hoang Son', 'NXB Van Hoc', '2021-08-10', 109000, 7, 'Lang man', 'Viet'),
('B158', 'Nhat Ky Ke Sat Nhan', 'Nguyen Dang Khoa', 'NXB Tre', '2018-02-20', 115000, 6, 'Kinh di', 'Viet'),
('B159', 'Ky Uc Mot Thoi', 'Do Minh Tam', 'NXB Kim Dong', '2022-12-03', 78000, 10, 'Tu truyen', 'Viet'),
('B160', 'Hanh Trinh Ky La', 'Lam Quang Dai', 'NXB Van Hoc', '2017-09-15', 105000, 8, 'Phieu luu', 'Viet'),
('B161', 'Lua Thieng', 'Vu Thi Minh', 'NXB Thanh Nien', '2020-04-22', 89000, 9, 'Lich su', 'Viet'),
('B162', 'Ke Thu Ben Trong', 'Nguyen Quoc Bao', 'NXB Tong Hop', '2016-06-05', 122000, 5, 'Trinh tham', 'Viet'),
('B163', 'Ban Giao Huong Mua Thu', 'Tran Thi Hai Yen', 'NXB Phu Nu', '2019-01-18', 98000, 6, 'Tinh cam', 'Viet'),
('B164', 'Cuoc Chien Trong Bong Toi', 'Hoang Trong Nghia', 'NXB Quan Doi', '2015-11-10', 125000, 4, 'Lich su', 'Viet'),
('B165', 'Nhung Giac Mo Co That', 'Le Minh Tuan', 'NXB Kim Dong', '2021-02-28', 89000, 10, 'Tam ly', 'Viet'),
('B166', 'Vi Vua Cuoi Cung', 'Pham Ngoc Khanh', 'NXB Van Hoc', '2014-05-22', 115000, 5, 'Lich su', 'Viet'),
('B167', 'Anh Sang Trong Dem', 'Ngo Huy Hoang', 'NXB Thanh Nien', '2017-12-07', 102000, 8, 'Kinh di', 'Viet'),
('B168', 'Me Cung Bi An', 'Vo Quoc Dung', 'NXB Tong Hop', '2018-10-15', 120000, 6, 'Trinh tham', 'Viet'),
('B169', 'Nhung Buoc Chan Hoang Da', 'Dinh Hoai Bao', 'NXB Tre', '2019-09-25', 109000, 9, 'Phieu luu', 'Viet'),
('B170', 'Bong Ma Tren Doi', 'Nguyen Thanh Son', 'NXB Kim Dong', '2016-08-13', 99000, 7, 'Kinh di', 'Viet'),
('B171', 'Chuyen Tinh Khong Ten', 'Le Thi Mai', 'NXB Van Hoc', '2023-06-10', 87000, 12, 'Tinh cam', 'Viet'),
('B172', 'Nguoi Dan Ong Huyen Bi', 'Tran Van Nam', 'NXB Thanh Nien', '2021-03-12', 129000, 5, 'Trinh tham', 'Viet'),
('B173', 'Lac Loi Giua Hai The Gioi', 'Hoang Minh Anh', 'NXB Tong Hop', '2015-07-08', 118000, 7, 'Khoa hoc vien tuong', 'Viet'),
('B174', 'Di san trong bong toi', 'Oliver Sinclair', 'Kodansha', '2015-07-10', 1500, 8, 'Bi an', 'Tieng Nhat'),
('B175', 'Manh moi cuoi cung', 'Rachel Dawson', 'Shinchosha', '2018-09-15', 1800, 6, 'Kinh di', 'Tieng Nhat'),
('B176', 'Duoi tan tich', 'Edward Harris', 'Kadokawa Shoten', '2012-05-20', 2000, 5, 'Tieu thuyet lich su', 'Tieng Nhat'),
('B177', 'Nhat ky bi mat', 'Emma Clarkson', 'Shueisha', '2020-12-03', 1200, 10, 'Lang man', 'Tieng Nhat'),
('B178', 'Vuong quoc bi nguyen rua', 'William Everett', 'Gentosha', '2021-07-07', 2500, 4, 'Gia tuong', 'Tieng Nhat'),
('B179', 'Buoc chan lang le', 'Julia Bennett', 'Kodansha', '2016-02-28', 1600, 7, 'Bi an', 'Tieng Nhat'),
('B180', 'Vuong mien sat', 'Marcus Holt', 'Hayakawa Shobo', '2019-06-22', 2300, 6, 'Gia tuong', 'Tieng Nhat'),
('B181', 'Loi thi tham bi lang quen', 'Charlotte Evans', 'Futabasha', '2015-10-10', 1400, 8, 'Kinh di', 'Tieng Nhat'),
('B182', 'Dong chay thoi gian', 'Hannah Richardson', 'Shodensha', '2023-04-15', 2000, 5, 'Khoa hoc vien tuong', 'Tieng Nhat'),
('B183', 'Kho bau an giau', 'Benjamin Scott', 'Bungei Shunju', '2014-03-20', 1300, 9, 'Phieu luu', 'Tieng Nhat'),
('B184', 'Giao uoc vinh cuu', 'Daniel Stevenson', 'Kadokawa Shoten', '2022-09-30', 1900, 3, 'Gia tuong', 'Tieng Nhat'),
('B185', 'Chen doc', 'Catherine Morris', 'Shinchosha', '2010-11-12', 1550, 7, 'Bi an', 'Tieng Nhat'),
('B186', 'The gioi bong toi', 'Vincent Howard', 'Hayakawa Shobo', '2017-07-07', 2200, 6, 'Gia tuong', 'Tieng Nhat'),
('B187', 'Nguoi vieng tham luc nua dem', 'Sophia Grant', 'Futabasha', '2013-06-14', 1450, 5, 'Kinh di', 'Tieng Nhat'),
('B188', 'Thanh dia cuoi cung', 'Patrick Wallace', 'Kodansha', '2019-12-05', 1950, 4, 'Chinh kich', 'Tieng Nhat'),
('B189', 'Bi an dinh thu Blackwood', 'Theresa Carter', 'Kadokawa Shoten', '2021-10-31', 1750, 5, 'Kinh di', 'Tieng Nhat'),
('B190', 'Canh cua dinh menh', 'Nathaniel Brooks', 'Shodensha', '2015-05-25', 2400, 3, 'Gia tuong', 'Tieng Nhat'),
('B191', 'Loi tien tri bong toi', 'Catherine Andrews', 'Bungei Shunju', '2012-09-08', 1800, 6, 'Kinh di', 'Tieng Nhat'),
('B192', 'Loi nguyen cua Hoang de', 'Frederick Langley', 'Hayakawa Shobo', '2020-02-18', 2700, 4, 'Gia tuong', 'Tieng Nhat'),
('B193', 'Su that bi lang quen', 'Melissa Quinn', 'Futabasha', '2014-07-19', 1350, 9, 'Bi an', 'Tieng Nhat'),
('B194', 'Vuong quoc phan loan', 'David Thornton', 'Shinchosha', '2023-05-21', 2800, 3, 'Kinh di', 'Tieng Nhat'),
('B195', 'Hieu truong', 'Eliza Montgomery', 'Kadokawa Shoten', '2011-08-23', 1700, 8, 'Tieu thuyet lich su', 'Tieng Nhat'),
('B196', 'Thoat khoi xieng xich', 'Samantha Price', 'Shodensha', '2016-01-14', 1600, 7, 'Chinh kich', 'Tieng Nhat'),
('B197', 'Xuong dia nguc', 'Alexander Foster', 'Hayakawa Shobo', '2021-06-11', 1950, 5, 'Kinh di', 'Tieng Nhat'),
('B198', 'Cuoc tham hiem bac', 'Richard Coleman', 'Futabasha', '2009-04-09', 1650, 6, 'Bi an', 'Tieng Nhat'),
('B199', 'Su im lang vinh cuu', 'Jonathan Nash', 'Shinchosha', '2018-08-30', 1850, 5, 'Tam ly kinh di', 'Tieng Nhat'),
('B200', 'Loi nguyen mua dong', 'Isabel Norton', 'Kadokawa Shoten', '2022-01-12', 2250, 3, 'Gia tuong', 'Tieng Nhat'),
('B201', 'Bi mat bi lang quen', 'Derek Walsh', 'Bungei Shunju', '2015-11-09', 1550, 7, 'Bi an', 'Tieng Nhat'),
('B202', 'Khu rung ma thuat', 'Sophia Henderson', 'Hayakawa Shobo', '2013-03-17', 2500, 4, 'Gia tuong', 'Tieng Nhat'),
('B203', 'Tieng than khoc ao anh', 'Vincent Ellis', 'Shodensha', '2016-05-28', 1700, 6, 'Kinh di', 'Tieng Nhat'),
('B204', 'Ngon lua ben trong', 'Amelia Hudson', 'Futabasha', '2020-04-21', 2100, 5, 'Phieu luu', 'Tieng Nhat'),


INSERT INTO Customer (Cid, Cname, Cssn, CbirthDate, Cgender, CphoneNumber, Cemail, Caddress, CtotalPayment, AccountId) VALUES
('C001', 'Nguyen Van An', '123456789', '1995-05-20', 'Nam', '0905123456', 'nguyenvana@example.com', 'Ha Noi', 500000, 1),
('C002', 'Tran Thi Bình', '987654321', '1998-09-15', 'Nu', '0912987654', 'tranthib@example.com', 'TP Ho Chi Minh', 200000, 2),
('C003', 'Le Hoang Cường', '567891234', '2000-12-10', 'Nam', '0923567891', 'lehoangc@example.com', 'Da Nang', 750000, 3),
('C004', 'Pham Van Đạt', '876543219', '1997-07-25', 'Nam', '0934876543', 'phamvand@example.com', 'Can Tho', 1000000, 1),
('C005', 'Bui Thi Yến', '456123789', '1999-03-05', 'Nu', '0945123789', 'buithie@example.com', 'Hai Phong', 450000, 1);

INSERT INTO CustomerBorrow (cardId, Cid, typeCard, cardExpiry, registrationDate, cardValue, borrowLimit) VALUES
('CB001', 'C001', 'Member', '2024-04-01', '2024-03-01', 50000, 10),
('CB002', 'C002', 'Vip', '2024-04-05', '2024-03-05', 100000, 15),
('CB003', 'C003', 'Platinum', '2024-04-10', '2024-03-10', 200000, NULL),
('CB004', 'C004', 'Member', '2024-04-12', '2024-03-12', 50000, 10),
('CB005', 'C005', 'Vip', '2024-04-15', '2024-03-15', 100000, 15);

INSERT INTO CustomerBuy (Cid, totalPurchase, membershipLevel) VALUES
('C001', 120000, 'Member'),
('C002', 600000, 'Vip'),
('C003', 50000, 'Member'),
('C004', 3000000, 'Platinum'),
('C005', 150000, 'Member');

select * from Book
delete from CustomerBuy
delete from CustomerBorrow
select * from CustomerBuy