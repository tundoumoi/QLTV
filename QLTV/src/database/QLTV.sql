﻿﻿-- bảng accoun
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
    title NVARCHAR(200),
    author NVARCHAR(100),
    publisher NVARCHAR(100),
    publishedDate VARCHAR(100),
    price FLOAT CHECK (price >= 0),
    quantity INT CHECK (quantity >= 0),
    type NVARCHAR(200), -- VD : Cười, Kinh dị, Kiến thức
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
    discountRate INT NOT NULL, 
    Vdescription VARCHAR(255) NOT NULL,  
    minPurchase DECIMAL(10,2) NOT NULL,
);

--Bảng Bill
CREATE TABLE Bill (
    billID INT identity(1,1) PRIMARY KEY,
    bookId VARCHAR(50) not null,
    Cid VARCHAR(50) not null,
    Eid VARCHAR(50),
    billTime DATETIME,
    unitPrice FLOAT,
    FOREIGN KEY (bookId) REFERENCES Book(bookId),
    FOREIGN KEY (Cid) REFERENCES Customer(Cid),
    FOREIGN KEY (Eid) REFERENCES Employee(Eid)
);
go
INSERT INTO Account (AccountId, username, APass) VALUES
(1, 'nambautroi', 'namfanbacmeo'),
(2, 'ladititi', 'dititi'),
(3, 'hoangluu', 'hoangluu217'),
(4,'tundaumoi','tundaumoi1'),
(5,'manhthang','manhthang1'),
(6,'employee','employee1'),
(7,'employee2','employee2'),
(8,'employee3','employee3'),
(9,'customer','customer1'),
(10,'customer2','customer2')
    INSERT INTO Voucher (discountRate, Vdescription, minPurchase)
    VALUES 
    (10, 'Giam 10% khi mua tu $5', 9.99),
    (20, 'Giam 20% khi mua tu $20', 19.99),
    (50, 'Giam 50% khi mua tu $50', 49.99)



INSERT INTO Admin (ADid, Aname, Assn, ADbirthDate, ADgender, ADphoneNumber, ADemail, ADaddress, AccountId)
VALUES 
('AD001', 'Phan Nhat Nam', '123456789', '1990-05-15', 'Nam', '0987654321', 'namphan@gmail.com', 'Hà Nội', 1),
('AD002', 'Dang Thanh Tung', '987654321', '1992-07-20', 'Nam', '0912345678', 'tungdang@gmail.com', 'TP.HCM', 2),
('AD003', 'Luong Dang Hoang Luu', '456789123', '1988-09-10', 'Nam', '0901122334', 'luuhoang@gmail.com', 'Đà Nẵng', 3)
INSERT INTO Admin (ADid, Aname, Assn, ADbirthDate, ADgender, ADphoneNumber, ADemail, ADaddress, AccountId)
VALUES 
('AD004', 'Tuan dau moi', '321654987', '1995-12-01', 'Nam', '0933221144', 'tuanmoi@gmail.com', 'Cần Thơ', 4),
('AD005', 'Manh Thang', '159753468', '1993-03-25', 'Nam', '0977885566', 'manhthang@gmail.com', 'Hải Phòng', 5)




SELECT a.AccountId, a.username , a.APass FROM Account a inner join admin ad on a.AccountId = ad.AccountId
--delete from account;
select * from Account;
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0001','Goat Brothers','By Colton, Larry','Doubleday','January,1993','8.79','223',' History , General','English'),
('B0002','The Missing Person','By Grumbach, Doris','Putnam Pub Group','March,1981','4.99','373',' Fiction , General','English'),
('B0003','Dont Eat Your Heart Out Cookbook','By Piscatella, Joseph C.','Workman Pub Co','April,1983','4.99','427',' Cooking , Reference','English'),
('B0004','When Your Corporate Umbrella Begins to Leak: A Handbook for White Collar Re-Employment','By Davis, Paul D.','Natl Pr Books','May,1991','4.99','608','','English'),
('B0005','Amy Spangler Breastfeeding : A Parent Guide','By Spangler, Amy','Amy Spangler','June,1997','5.32','866','','English'),
('B0006','The Foundation of Leadership: Enduring Principles to Govern Our Lives','By Short, Bo','Excalibur Press','July,1997','6.06','966','','English'),
('B0007','Chicken Soup for the Soul: 101 Stories to Open the Heart and Rekindle the Spirit','By Canfield, Jack (COM) and Hansen, Mark Victor (COM)','Health Communications Inc','August,1993','4.99','877',' Self-help , Personal Growth , Self-Esteem','English'),
('B0008','Journey Through Heartsongs','By Stepanek, Mattie J. T.','VSP Books','September,2001','19.96','913',' Poetry , General','English'),
('B0009','In Search of Melancholy Baby','By Aksyonov, Vassily, Heim, Michael Henry, and Bouis, Antonina W.','Random House','October,1987','4.99','826',' Biography & Autobiography , General','English'),

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0010','Christmas Cookies','By Eakin, Katherine M. and Deaman, Joane (EDT)','Oxmoor House','November,1986','12.98','552',' Cooking , General','English'),
('B0011','The Dieter Guide to Weight Loss During Sex','By Smith, Richard','Workman Publishing Company','December,1978','4.99','768',' Health & Fitness , Diet & Nutrition , Diets','English'),
('B0012','Germs : Biological Weapons and America Secret War','By Miller, Judith, Engelberg, Stephen, and Broad, William J.','Simon & Schuster','January,2001','4.99','581',' Technology & Engineering , Military Science','English'),
('B0013','The Genesis of Ethics','By Visotzky, Burton L.','Crown','February,1996','4.99','598',' Religion , Ethics','English'),
('B0014','The Good Book: Reading the Bible with Mind and Heart','By Gomes, Peter J.','Harper Perennial','March,1998','5.29','813',' Religion , Biblical Biography , General','English'),
('B0015','All over but the Shoutin','By Bragg, Rick','Vintage','April,1998','4.89','438',' Biography & Autobiography , Personal Memoirs','English'),
('B0016','Oilers and Sweepers and Other Stories','By Dennison, George','Random House','May,1979','5','959','','English'),
('B0017','Prince William','By Garner, Valerie','Benford Books','June,1998','4.99','46','','English'),
('B0018','The Emperor New Mind','By Penrose, Roger','Penguin Books','July,1991','4.99','211',' Philosophy , General','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0019','Touching Fire: Erotic Writings by Women','By Thornton, Louise, Sturtevant, Jan, and Sumrall, Amber Coverdale (EDT)','Carroll & Graf Pub','August,1990','5.29','915','','English'),
('B0020','Hill Rat: Blowing the Lid Off Congress','By Jackley, John L.','Regnery Publishing, Inc.','September,1992','4.99','701',' Political Science , General','English'),
('B0021','The Great ABC Treasure Hunt: A Hidden Picture Alphabet Book (Time-Life Early Learning Program)','By Time-Life for Children (Firm) (COR), Singer, Muff, and Hoggan, Pat (ILT)','Time Life Education','October,1991','5.29','400',' Juvenile Nonfiction , General','English'),
('B0022','Personality of the Cat','By Aymar, Brandt (EDT)','Bonanza Books','November,1978','5.41','283',' Pets , Cats , General','English'),
('B0023','Murdering Mr. Monti: A Merry Little Tale of Sex and Violence','By Viorst, Judith','Simon & Schuster','December,1994','5.29','283',' Fiction , General','English'),
('B0024','In Re Alger Hiss:  Petition for a Writ of Error Coram Nobis','By Edith Tiger','Hill & Wang','January,1979','10.99','942','','English'),
('B0025','Black Holes and Baby Universes and Other Essays','By Hawking, Stephen W.','Bantam','February,1993','5.29','624',' Biography & Autobiography , General','English'),
('B0026','Relativity: The Special and the General Theory','By Albert Einstein','Three Rivers Press','March,1961','8.79','794','','English'),
('B0027','Betrayal : How the Clinton Administration Undermined American Security','By Gertz, Bill','Regnery Pub','April,1999','4.99','972',' Political Science , General','English'),
('B0028','Shadow Song','By Kay, Terry','Atria Books','May,1994','4.99','272',' Fiction , General','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0029','Undercurrents: A Therapist Reckoning With Her Own Depression','By Manning, Martha','HarperCollins','June,1995','5.29','943',' Psychology , General','English'),
('B0031','The Kiss: A Memoir','By Harrison, Kathryn','Random House','August,1997','5.29','70',' Family & Relationships , Love & Romance','English'),
('B0032','Codebreakers Victory: How the Allied Cryptogaphers Won World War II','By Haufler, Hervie','NAL Trade','September,2003','5.29','509',' History , Military , World War II','English'),
('B0033','A Manual for Writers of Term Papers, Theses, and Dissertations, Fifth Edition','By Turabian, Kate L.','University of Chicago Press','October,1987','4.99','425','','English'),
('B0034','The Price of Loyalty: George W. Bush, the White House, and the Education of Paul O Neill','By Suskind, Ron','Simon & Schuster','November,2004','8.79','181',' Political Science , Government , Executive Branch','English'),
('B0035','Best New American Voices 2003','By Kulka, John (EDT), Danford, Natalie (EDT), and Oates, Joyce Carol (EDT)','Harvest Books','December,2002','5.29','89',' Fiction , Anthologies (multiple authors)','English'),
('B0036','Escape from the CIA: How the CIA Won and Lost the Most Important KGB Spy Ever to Defect to the U.S.','By Kessler, Ronald','Pocket Books','January,1991','5.29','682',' Political Science , General','English'),
('B0037','Meditations: On the Monk Who Dwells in Daily Life','By Moore, Thomas','HarperCollins','February,1994','4.99','242',' Religion , General','English'),
('B0038','Links Lore','By Stevens, Peter F.','POTOMAC BOOKS','March,2000','5.29','549',' Sports & Recreation , History','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0039','Jackie by Josie: A Novel','By Preston, Caroline','Scribner','April,1998','4.99','127',' Fiction , Literary','English'),
('B0040','Joshua and the City','By Girzone, Joseph F.','Doubleday','May,1995','5.29','934',' Religion , Inspirational','English'),
('B0041','The Book of Courtly Love: The Passionate Code of the Troubadours','By Hopkins, Andrea','HarperCollins','June,1994','5.29','723','','English'),
('B0042','How Good Do We Have to Be? A New Understanding of Guilt and Forgiveness','By Kushner, Harold S.','Back Bay Books','July,1997','4.99','787',' Religion , Psychology of Religion','English'),
('B0044','Majorca: Culture and Life','By Konemann Inc. (EDT)','Konemann','September,2000','8.83','600',' Photography , Subjects & Themes , Regional','English'),
('B0045','Written by Herself: Autobiographies of American Women: An Anthology','By Conway, Jill Ker (EDT)','Vintage','October,1992','4.99','651',' Biography & Autobiography , Women','English'),
('B0046','The Universe of Galaxies','By Hodge, Paul','W H Freeman & Co','November,1984','5.29','635','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0047','Ice Bound: A Doctor Incredible Battle For Survival at the South Pole','By Nielsen, Jerri and Vollers, Maryanne','Miramax','December,2002','4.89','51',' Biography & Autobiography , Women','English'),
('B0049','Me and Ted Against the World : The Unauthorized Story of the Founding of CNN','By Schonfeld, Reese','Harperbusiness','February,2001','4.99','997',' Business & Economics , Management','English'),
('B0050','Magnet Therapy: The Pain Cure Alternative','By Lawrence, Ronald Melvin, Plowden, Judith, and Rosch, Paul','Prima Pub','March,1998','5.29','647',' Medical , Holistic Medicine','English'),
('B0052','George Meany And His Times: A Biography','By Robinson, Archie','Simon & Schuster','May,1982','4.99','169',' Biography & Autobiography , General','English'),
('B0053','American Dreams: Lost & Found','By Terkel, Studs','Pantheon','June,1980','10.99','432','','English'),
('B0054','Sharing the Pie : A Citizen Guide to Wealth and Power','By Brodner, Steve (ILT) and Brouwer, Steve','Holt Paperbacks','July,1998','5.29','382',' Business & Economics , Economic Conditions','English'),
('B0055','Love, Love, and Love','By Bernhard, Sandra','HarperCollins','August,1993','5.29','807','','English'),
('B0056','Conflicting Accounts: The Creation and Crash of the Saatchi and Saatchi Advertising Empire','By Goldman, Kevin','Touchstone','September,1998','5.29','177',' Business & Economics , Advertising & Promotion','English'),
('B0057','What Went Wrong at Enron: Everyone Guide to the Largest Bankruptcy in U.S. History','By Fusaro, Peter C. and Miller, Ross M.','Wiley','October,2002','5.29','528',' Business & Economics , Finance , General','English'),
('B0059','From the Silent Earth: A Report on the Greek Bronze Age','By Joseph Alsop','Harper & Row','December,1964','6.22','300','','English'),
('B0060','Panic Disorder and Its Treatment (Medical Psychiatry Series)','By Pollack, Mark H. (EDT) and Rosenbaum, J. F. (EDT)','Informa Healthcare','January,1998','5.41','961',' Psychology , Psychopathology , General','English'),
('B0061','Henry VIII (English Monarchs Series)','By Scarisbrick, J. J.','University of California Press','February,1972','5.47','967',' Biography & Autobiography , Military','English'),
('B0062','The Moral Intelligence of Children','By Coles, Robert','Random House','March,1997','5.29','798',' Psychology , General','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0063','Concordance to the New English Bible, New Testament,','By Elder, E','Zondervan Pub. House','April,1965','10.99','726','','English'),
('B0064','Life at the Edge: Readings from Scientific American Magazine','By Gould, James L. and Gould, Carol Grant (EDT)','W H Freeman & Co','May,1989','4.99','527','','English'),
('B0065','Cook Healthy: Cook Quick','By Wesler, Cathy A.','UNKNO','January,1995','5.18','733',' Cooking , Health & Healing , Low Fat','English'),
('B0066','Images Of War: The Artist Vision of World War II','By McCormick, Ken','Orion Books','October,1990','10.02','492','','English'),
('B0067','Becoming Soul Mates: Cultivating Spiritual Intimacy in the Early Years of Marriage','By Parrott, Les','Zondervan','October,1995','5.29','638',' Family & Relationships , Reference','English'),
('B0068','Care of the Soul : A Guide for Cultivating Depth and Sacredness in Everyday Life','By Moore, Thomas','HarperPerennial','January,1994','4.99','514',' Self-help , General','English'),
('B0069','Victims of Progress','By Bodley, John H.','Cummings Pub.Co.','September,1975','4.99','503','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0070','Inside the Tornado: Marketing Strategies from Silicon Valley Cutting Edge','By Moore, Geoffrey A.','Harper Business','October,1995','5.29','82',' Business & Economics , Marketing , General','English'),
('B0071','Sunset at Rosalie: A Novel','By McLaughlin, Ann L.','Daniel & Daniel Pub','June,1996','5','142',' Fiction , General','English'),
('B0072','The Girlfriends Guide to Surviving the First Year of Motherhood, Packaging May Vary','By Iovine, Vicki','TarcherPerigee','October,1997','4.99','765',' Family & Relationships , Parenting , Motherhood','English'),
('B0073','Sherlock Holmes and the Red Demon by John H. Watson, M.D.','By Watson, John H. and Millett, Larry (EDT)','Viking','September,1996','4.99','224',' Fiction , Mystery & Detective , General','English'),
('B0076','Proverbs For The People','By Price-Thompson, Tracy (EDT) and Stovall, Taressa (EDT)','Kensington','June,2003','5.88','521',' Fiction , Anthologies (multiple authors)','English'),
('B0077','Great American Countryside','By Landi, Val','Scribner','July,1982','10.99','676',' Travel , United States , General','English'),
('B0078','Rob Whitlock: A Pioneer Boy in Old Ohio','By Jackson, Kathryn','Simon and Schuster','January,1951','12.51','481','','English'),
('B0079','British Hospitals (Britain in Pictures)','By A. G. L. Ives','London : Collins','January,1948','18','569','','English'),
('B0080','An essay on morals','By Wylie, Philip','Rinehart & Company, Inc','January,1947','4.99','589','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0081','Kid, You Sing My Songs of Love, and Loss, and Hope','By Wyse, Lois and Rogers, Lilla (ILT)','Crown','February,1991','5.29','569',' Humor , Form , Limericks & Verse','English'),
('B0082','Re-Inventing the Corporation: Transforming Your Job and Your Company for the New Information Society','By Naisbitt, John and Aburdene, Patricia','Little Brown & Co','September,1985','5.29','572',' Business & Economics , General','English'),
('B0084','The Practical Stylist','By Baker, Sheridan Warner','Crowell','January,1973','4.99','805','','English'),
('B0085','The Periodic Kingdom: A Journey Into The Land Of The Chemical Elements (Science Masters Series)','By Atkins, P. W.','Basic Books','July,1995','4.99','445',' Science , Applied Sciences','English'),
('B0086','Titanic','By Kirkland, Douglas, Marsh, Ed W., and Kirkland, Douglas (PHT)','Harper Paperbacks','October,1997','8.79','72',' Performing Arts , Film , General','English'),
('B0088','Civilization III: Instruction Manual','By Meier, Sid','Infogames Interactive','January,2001','4.99','641','','English'),
('B0089','Alcatraz 46;: The anatomy of a classic prison tragedy,','By Don DeNevi, Philip Bergen','Leswing Press','January,1974','5.29','883','','English'),
('B0090','Elvis in the Morning','By Buckley, William F.','Harvest Books','June,2002','5.29','467',' Fiction , Fantasy , Historical','English'),
('B0091','Fat Free, Flavor Full: Dr. Gabe Mirkin Guide to Losing Weight and Living Longer','By Mirkin, Gabe and Rich, Diana','Little Brown & Co','January,1995','4.99','446',' Health & Fitness , Diet & Nutrition , Diets','English'),
('B0092','White Gold Wielder - Book Three of The Second Chronicles of Thomas Covenant','By Donaldson, Stephen R.','Del Rey','March,1983','4.99','722',' Fiction , Fantasy , General','English'),
('B0093','From Image to Likeness: A Jungian Path in the Gospel Journey','By Grant, W. Harold, Thompson, Magdala, and Clarke, Thomas E.','Paulist Press','January,1983','4.99','524',' Psychology , Movements , Jungian','English'),
('B0094','ON THE WING: The Life of Birds: From Feathers to Flight','By Brooks, Bruce','Scribner','September,1989','19.25','183',' Science , General','English'),
('B0095','Bringing Down the House: The Inside Story of Six M.I.T. Students Who Took Vegas for Millions','By Mezrich, Ben','Atria','September,2003','4.99','63',' Games & Activities , Gambling , Table','English'),
('B0096','Future Space: Beyond Earth','By Quigley, Sebastian (ILT) and Jefferis, David','Tangerine Pr','June,2001','5.29','549','','English'),
('B0097','The young Jefferson, 1743-1789,','By Bowers, Claude Gernade','Houghton Mifflin Company','January,1945','5.29','684','','English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0098','Mayo Clinic On Hearing: Strategies for Managing Hearing Loss, Dizziness and Other Ear Problems ("MAYO CLINIC ON" SERIES)','By Olsen, Wayne, Ph.D. (EDT)','Kensington Pub Corp','October,2003','5.29','244',' Health & Fitness , Hearing & Speech','English'),
('B0099','Trust Me, Mom-Everyone Else Is Going!: The New Rules for Mothering Adolescent Girls','By Cohen-Sandler, Roni, Ph.D.','Penguin Books','February,2003','4.99','116',' Family & Relationships , Parenting , General','English'),
('B0100','I Hate School: How to Hang in and When to Drop Out','By Wirths, Claudine G. and Bowman-Kruhm, Mary','Trophy Pr','September,1986','5.29','808',' Young Adult Nonfiction , General','English'),
('B0101','The Quotable Ronald Reagan','By Reagan, Ronald and Hannaford, Peter','Regnery Publishing, Inc.','January,1999','5.44','346',' Reference , Quotations','English'),
('B0102','Rooster crows for day,','By Burman, Ben Lucien','E.P. Dutton & company, inc','January,1945','21.95','414','','English'),
('B0103','Leadership Is an Art','By De Pree, Max','Dell','August,1990','4.99','425',' Business & Economics , General','English'),
('B0104','What is Going On?: Personal Essays','By McCall, Nathan','Random House','September,1997','5.29','963',' Social Science , Ethnic Studies , African American Studies','English'),
('B0105','Dark Fields of the Republic: Poems 1991-1995','By Rich, Adrienne Cecile','W. W. Norton & Company','September,1995','5.29','564',' Poetry , American , General','English'),
('B0106','Creating an Inclusive School','By Villa, Richard A. (EDT) and Thousand, Jacqueline S. (EDT)','Assn for Supervision & Curriculum','November,1995','5.29','865',' Education , Special Education , General','English'),
('B0107','Edit Yourself : A manual for everyone who works with words','By Ross-Larson, Bruce','Norton*(ww Norton Co','October,1985','5.29','966','','English'),
('B0108','Standing for Something: 10 Neglected Virtues That Will Heal Our Hearts and Homes','By Hinckley, Gordon B. and Wallace, Mike (FRW)','Crown','February,2000','4.99','541',' Religion , Christianity , Church of Jesus Christ of Latter-day Saints (Mormon)','English'),
('B0109','North Carolina Ghosts and Legends','By Roberts, Nancy','University of South Carolina Press','August,1991','5.29','388',' Social Science , Folklore & Mythology','English'),
('B0110','Balkan Odyssey a personal account of the international peace efforts following the breakup of the former Yugoslavia','By Owen, David','Harcourt','March,1996','5.12','617','','English'),
('B0111','Straight Talk about Death for Teenagers: How to Cope with Losing Someone You Love','By Grollman, Earl A.','Beacon Press','April,1993','8.79','98',' Young Adult Nonfiction , Social Topics , Death & Dying','English'),
('B0112','Understanding Thomas Jefferson','By Halliday, E. M.','Harper Perennial','February,2002','4.99','308',' Biography & Autobiography , Presidents & Heads of State','English'),
('B0113','Hey Mom! I am Hungry!: Great-Tasting, Low-Fat, Easy Recipes to Feed Your Family','By Powter, Susan','Atria Books','January,1997','5.29','450',' Cooking , Health & Healing , Low Fat','English'),
('B0114','Changes in the Land: Indians, Colonists and the Ecology of New England','By Cronon, William','Hill and Wang','July,1983','4.99','880',' Science , Life Sciences , Ecology','English'),
('B0115','An East Wind Coming','By Arthur Byron Cover','Berkley','November,1979','5.29','847','','English'),
('B0116','Going the Other Way: Lessons from a Life In and Out of Major League Baseball','By Bean, Billy and Bull, Chris','Da Capo Press','March,2004','4.99','641',' Sports & Recreation , Baseball , Essays & Writings','English'),
('B0117','Consider Your Options: Get the Most from Your Equity Compensation','By Thomas, Kaye A.','Fairmark Press','January,2000','5.29','591',' Business & Economics , Personal Finance , General','English'),
('B0118','Molecular Cell Biology','By Lodish, Harvey F., Baltimore, David, and Berk, Arnold (CON)','W H Freeman & Co (Sd)','March,1995','11.44','756',' Science , Life Sciences , Biology','English'),
('B0119','Majendie Cat','By Fowlkes, Frank V.','Harcourt','May,1986','5.29','913','','English'),
('B0120','First Things First','By Covey, Stephen R., Merrill, A. Roger, and Merrill, Rebecca R.','Simon & Schuster','January,1994','5.58','352',' Psychology , General','English'),
('B0121','Best Friends: The True Story of the World is Most Beloved Animal Sanctuary','By Glen, Samantha and Moore, Mary Tyler (INT)','Kensington Books','February,2001','4.89','664',' Nature , Animals , General','English'),
('B0122','Prudens futuri: The US Army War College, 1901-1967','By Pappas, George S.','Alumni Association of the US Army War College','January,1967','6.24','869','','English'),
('B0123','Joshua: A Parable for Today','By Girzone, Joseph F.','MacMillan Publishing Company.','September,1987','5.29','153','','English'),
('B0124','Things Not Seen and Other Stories','By Williams, Lynna','Little Brown & Co','May,1992','5.29','740',' Fiction , General','English'),
('B0125','Putting the One Minute Manager to Work: How to Turn the 3 Secrets into Skills','By Blanchard, Kenneth H. and Lorber, Robert','Jossey-Bass Inc Pub','February,1984','5.29','304','','English'),
('B0126','The Spiritual Life of Children','By Coles, Robert','HarperOne','October,1991','4.99','640',' Psychology , Developmental , Child','English'),
('B0127','Constancia and Other Stories for Virgins','By Fuentes, Carlos and Christensen, Thomas (TRN)','Farrar, Straus and Giroux','April,1990','5.29','223',' Fiction , Short Stories (single author)','English'),
('B0128','May I Have This Dance?','By Rupp, Joyce and Veeder, Judith (ILT)','Ave Maria Pr','July,1992','4.89','451',' Religion , Devotional','English'),
('B0129','Meditations from Conversations with God: An Uncommon Dialogue, Book 1 (Conversations with God Series)','By Walsch, Neale Donald','TarcherPerigee','September,1997','5.29','187',' Religion , Meditations','English'),
('B0130','Washington bowed','By McKeldin, Theodore R','Maryland Historical Society','January,1956','16.16','791','','English'),
('B0131','Chagall','By Compton, Susan P.','"Harry N. Abrams, Inc."','May,1985','10.99','883',' Art , General','English'),
('B0132','Ride a Pale Horse','By MacInnes, Helen','Harcourt','October,1984','5.29','77',' Fiction , Mystery & Detective , General','English'),
('B0133','Listening to Prozac: A Psychiatrist Explores Antidepressant Drugs and the Remaking of the Self','By Kramer, Peter D.','Viking Adult','June,1993','4.99','235',' Psychology , Psychopathology , Depression','English'),
('B0134','The Big Garage on Clear Shot: Growing Up, Growing Old, and Going Fishing at the End of the Road','By Bodett, Tom','William Morrow & Co','September,1990','4.99','896',' Fiction , General','English'),
('B0135','Life Stories','By Newbold, Heather (EDT)','University of California Press','April,2000','4.99','143',' Science , Environmental Science','English'),
('B0136','Houghton Mifflin Invitations to Literature: Student Anthology Level 1.3 Share 1997 (Invitations to Lit 1997)','By Hm (COR)','Houghton Mifflin','October,1996','5.94','636',' Juvenile Nonfiction , Language Arts , General','English'),
('B0137','Grow Greener: Ten Steps to a Richer Life','By Hoxton, Rob','Robinson Hoxton Publishing, LLC','November,2001','5.29','748','','English'),
('B0138','Frames of Reference: Looking at American Art, 1900-1950: Works from the Whitney Museum of American Art','By Fraser, Kennedy, Whitney Museum of American Art (COR), Weinberg, Adam D., and Venn, Beth','University of California Press','January,2000','6.42','645',' Art , History , General','English'),
('B0139','Remembering Main Street: An American Album','By Ross, Pat','Studio','November,1994','10.99','751',' History , United States , General','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0140', 'Bi An Duoi Lop Tro Tan', 'Nguyen Van A', 'NXB Tre', 'July,2015', 10.5, 12, 'Kinh di', 'Tieng Viet'),
('B0141', 'Duong Den Thanh Cong', 'Tran Minh Hoang', 'NXB Lao Dong', 'September,2018', 9.8, 10, 'Kien thuc', 'Tieng Viet'),
('B0142', 'Hanh Trinh Vo Dinh', 'Le Thanh Tu', 'NXB Kim Dong', 'March,2017', 8.9, 8, 'Phieu luu', 'Tieng Viet'),
('B0143', 'Bong Dem Vinh Cuu', 'Pham Thi Lan', 'NXB Van Hoc', 'November,2020', 12, 5, 'Kinh di', 'Tieng Viet'),
('B0144', 'Cuoc Song Khong Gioi Han', 'Hoang Anh Tuan', 'NXB Tre', 'May,2016', 11.5, 6, 'Kien thuc', 'Tieng Viet'),
('B0145', 'Bi Kip Thanh Cong', 'Do Thi Ngoc', 'NXB Thanh Nien', 'February,2019', 9.9, 11, 'Ky nang song', 'Tieng Viet'),
('B0146', 'Hen Nhau Ngay Ay', 'Mai Xuan Hoa', 'NXB Kim Dong', 'August,2022', 7.5, 9, 'Tinh cam', 'Tieng Viet'),
('B0147', 'Vung Dat Bi An', 'Trinh Huu Nam', 'NXB Van Hoc', 'June,2014', 10.5, 7, 'Phieu luu', 'Tieng Viet'),
('B0148', 'Cuoc Chien Khong Hoi Ket', 'Ngo Thanh Hai', 'NXB Quan Doi', 'December,2013', 12.5, 5, 'Lich su', 'Tieng Viet'),
('B0149', 'Ban Tay Mau', 'Vu Dinh Lam', 'NXB Tre', 'April,2018', 9.5, 6, 'Kinh di', 'Tieng Viet'),
('B0150', 'Tam Ly Hoc Dam Dong', 'Le Huu Phuoc', 'NXB Tong Hop', 'October,2021', 13.5, 10, 'Tam ly', 'Tieng Viet'),
('B0151', 'Vuot Qua So Phan', 'Tran Ngoc Minh', 'NXB Phu Nu', 'January,2015', 8.9, 4, 'Tu truyen', 'Tieng Viet'),
('B0152', 'Chinh Phuc Giac Mo', 'Dinh Hoang Phong', 'NXB Thanh Nien', 'July,2017', 9.9, 6, 'Ky nang song', 'Tieng Viet'),
('B0153', 'Am Anh', 'Ho Thanh Tam', 'NXB Van Hoc', 'June,2019', 10.8, 8, 'Kinh di', 'Tieng Viet'),
('B0154', 'Mat Ma Phuong Dong', 'Pham Van Hau', 'NXB Tong Hop', 'March,2016', 12.9, 5, 'Trinh tham', 'Tieng Viet'),
('B0155', 'Cau Chuyen Tinh Yeu', 'Nguyen Hong Nhung', 'NXB Kim Dong', 'May,2023', 8.5, 12, 'Tinh cam', 'Tieng Viet'),
('B0156', 'Nhung Buoc Chan Dau Tien', 'Vo Hoai Nam', 'NXB Lao Dong', 'November,2014', 9.9, 9, 'Ky nang song', 'Tieng Viet'),
('B0157', 'Bong Hinh Nguoi Xua', 'Trinh Hoang Son', 'NXB Van Hoc', 'August,2021', 10.9, 7, 'Lang man', 'Tieng Viet'),
('B0158', 'Nhat Ky Ke Sat Nhan', 'Nguyen Dang Khoa', 'NXB Tre', 'February,2018', 11.5, 6, 'Kinh di', 'Tieng Viet'),
('B0159', 'Ky Uc Mot Thoi', 'Do Minh Tam', 'NXB Kim Dong', 'December,2022', 7.8, 10, 'Tu truyen', 'Tieng Viet'),
('B0160', 'Hanh Trinh Ky La', 'Lam Quang Dai', 'NXB Van Hoc', 'September,2017', 10.5, 8, 'Phieu luu', 'Tieng Viet'),
('B0161', 'Lua Thieng', 'Vu Thi Minh', 'NXB Thanh Nien', 'April,2020', 8.9, 9, 'Lich su', 'Tieng Viet'),
('B0162', 'Ke Thu Ben Trong', 'Nguyen Quoc Bao', 'NXB Tong Hop', 'June,2016', 12.2, 5, 'Trinh tham', 'Tieng Viet'),
('B0163', 'Ban Giao Huong Mua Thu', 'Tran Thi Hai Yen', 'NXB Phu Nu', 'January,2019', 9.8, 6, 'Tinh cam', 'Tieng Viet'),
('B0164', 'Cuoc Chien Trong Bong Toi', 'Hoang Trong Nghia', 'NXB Quan Doi', 'November,2015', 12.5, 4, 'Lich su', 'Tieng Viet'),
('B0165', 'Nhung Giac Mo Co That', 'Le Minh Tuan', 'NXB Kim Dong', 'February,2021', 8.9, 10, 'Tam ly', 'Tieng Viet'),
('B0166', 'Vi Vua Cuoi Cung', 'Pham Ngoc Khanh', 'NXB Van Hoc', 'May,2014', 11.5, 5, 'Lich su', 'Tieng Viet'),
('B0167', 'Anh Sang Trong Dem', 'Ngo Huy Hoang', 'NXB Thanh Nien', 'December,2017', 10.2, 8, 'Kinh di', 'Tieng Viet'),
('B0168', 'Me Cung Bi An', 'Vo Quoc Dung', 'NXB Tong Hop', 'October,2018', 12, 6, 'Trinh tham', 'Tieng Viet'),
('B0169', 'Nhung Buoc Chan Hoang Da', 'Dinh Hoai Bao', 'NXB Tre', 'September,2019', 10.9, 9, 'Phieu luu', 'Tieng Viet'),
('B0170', 'Bong Ma Tren Doi', 'Nguyen Thanh Son', 'NXB Kim Dong', 'August,2016', 9.9, 7, 'Kinh di', 'Tieng Viet'),
('B0171', 'Chuyen Tinh Khong Ten', 'Le Thi Mai', 'NXB Van Hoc', 'June,2023', 8.7, 12, 'Tinh cam', 'Tieng Viet'),
('B0172', 'Nguoi Dan Ong Huyen Bi', 'Tran Van Nam', 'NXB Thanh Nien', 'March,2021', 12.9, 5, 'Trinh tham', 'Tieng Viet'),
('B0173', 'Lac Loi Giua Hai The Gioi', 'Hoang Minh Anh', 'NXB Tong Hop', 'July,2015', 11.8, 7, 'Khoa hoc vien tuong', 'Tieng Viet'),
('B0174', 'Di san trong bong toi', 'Oliver Sinclair', 'Kodansha', 'July,2015', 5.5, 8, 'Bi an', 'Tieng Nhat'),
('B0175', 'Manh moi cuoi cung', 'Rachel Dawson', 'Shinchosha', 'September,2018', 5.8, 6, 'Kinh di', 'Tieng Nhat'),
('B0176', 'Duoi tan tich', 'Edward Harris', 'Kadokawa Shoten', 'May,2012', 6.0, 5, 'Tieu thuyet lich su', 'Tieng Nhat'),
('B0177', 'Nhat ky bi mat', 'Emma Clarkson', 'Shueisha', 'December,2020', 5.2, 10, 'Lang man', 'Tieng Nhat'),
('B0178', 'Vuong quoc bi nguyen rua', 'William Everett', 'Gentosha', 'July,2021', 6.5, 4, 'Gia tuong', 'Tieng Nhat'),
('B0179', 'Buoc chan lang le', 'Julia Bennett', 'Kodansha', 'February,2016', 5.6, 7, 'Bi an', 'Tieng Nhat'),
('B0180', 'Vuong mien sat', 'Marcus Holt', 'Hayakawa Shobo', 'June,2019', 6.3, 6, 'Gia tuong', 'Tieng Nhat'),
('B0181', 'Loi thi tham bi lang quen', 'Charlotte Evans', 'Futabasha', 'October,2015', 5.4, 8, 'Kinh di', 'Tieng Nhat'),
('B0182', 'Dong chay thoi gian', 'Hannah Richardson', 'Shodensha', 'April,2023', 6.0, 5, 'Khoa hoc vien tuong', 'Tieng Nhat'),
('B0183', 'Kho bau an giau', 'Benjamin Scott', 'Bungei Shunju', 'March,2014', 5.3, 9, 'Phieu luu', 'Tieng Nhat'),
('B0184', 'Giao uoc vinh cuu', 'Daniel Stevenson', 'Kadokawa Shoten', 'September,2022', 5.9, 3, 'Gia tuong', 'Tieng Nhat'),
('B0185', 'Chen doc', 'Catherine Morris', 'Shinchosha', 'November,2010', 5.55, 7, 'Bi an', 'Tieng Nhat'),
('B0186', 'The gioi bong toi', 'Vincent Howard', 'Hayakawa Shobo', 'July,2017', 6.2, 6, 'Gia tuong', 'Tieng Nhat'),
('B0187', 'Nguoi vieng tham luc nua dem', 'Sophia Grant', 'Futabasha', 'June,2013', 5.45, 5, 'Kinh di', 'Tieng Nhat'),
('B0188', 'Thanh dia cuoi cung', 'Patrick Wallace', 'Kodansha', 'December,2019', 5.95, 4, 'Chinh kich', 'Tieng Nhat'),
('B0189', 'Bi an dinh thu Blackwood', 'Theresa Carter', 'Kadokawa Shoten', 'October,2021', 5.75, 5, 'Kinh di', 'Tieng Nhat'),
('B0190', 'Canh cua dinh menh', 'Nathaniel Brooks', 'Shodensha', 'May,2015', 6.4, 3, 'Gia tuong', 'Tieng Nhat'),
('B0191', 'Loi tien tri bong toi', 'Catherine Andrews', 'Bungei Shunju', 'September,2012', 5.8, 6, 'Kinh di', 'Tieng Nhat'),
('B0192', 'Loi nguyen cua Hoang de', 'Frederick Langley', 'Hayakawa Shobo', 'February,2020', 6.7, 4, 'Gia tuong', 'Tieng Nhat'),
('B0193', 'Su that bi lang quen', 'Melissa Quinn', 'Futabasha', 'July,2014', 5.35, 9, 'Bi an', 'Tieng Nhat'),
('B0194', 'Vuong quoc phan loan', 'David Thornton', 'Shinchosha', 'May,2023', 6.8, 3, 'Kinh di', 'Tieng Nhat'),
('B0195', 'Hieu truong', 'Eliza Montgomery', 'Kadokawa Shoten', 'August,2011', 5.7, 8, 'Tieu thuyet lich su', 'Tieng Nhat'),
('B0196', 'Thoat khoi xieng xich', 'Samantha Price', 'Shodensha', 'January,2016', 5.6, 7, 'Chinh kich', 'Tieng Nhat'),
('B0197', 'Xuong dia nguc', 'Alexander Foster', 'Hayakawa Shobo', 'June,2021', 5.95, 5, 'Kinh di', 'Tieng Nhat'),
('B0198', 'Cuoc tham hiem bac', 'Richard Coleman', 'Futabasha', 'April,2009', 5.65, 6, 'Bi an', 'Tieng Nhat'),
('B0199', 'Su im lang vinh cuu', 'Jonathan Nash', 'Shinchosha', 'August,2018', 5.85, 5, 'Tam ly kinh di', 'Tieng Nhat'),
('B0200', 'Loi nguyen mua dong', 'Isabel Norton', 'Kadokawa Shoten', 'January,2022', 6.25, 3, 'Gia tuong', 'Tieng Nhat'),
('B0201', 'Bi mat bi lang quen', 'Derek Walsh', 'Bungei Shunju', 'November,2015', 5.55, 7, 'Bi an', 'Tieng Nhat'),
('B0202', 'Khu rung ma thuat', 'Sophia Henderson', 'Hayakawa Shobo', 'March,2013', 6.5, 4, 'Gia tuong', 'Tieng Nhat'),
('B0203', 'Tieng than khoc ao anh', 'Vincent Ellis', 'Shodensha', 'May,2016', 5.7, 6, 'Kinh di', 'Tieng Nhat'),
('B0204', 'Ngon lua ben trong', 'Amelia Hudson', 'Futabasha', 'April,2020', 6.1, 5, 'Phieu luu', 'Tieng Nhat'),
('B0205','The Mozart Companion a Symposium by leading Mozart Scholar','By Landon, Harold R.','W W Norton & Co Inc','November,1969','4.99','773','','English'),
('B0206','Joshua and the Children: A Parable','By Joseph F. Girzone','Collier Books','October,1991','5.29','560','','English'),
('B0207','Red Square','By Smith, Martin Cruz','Random House','October,1992','4.99','899',' Fiction , General','English'),
('B0208','Buffalo Gordon','By Lewis, J. P. Sinclair','Forge Books','February,2001','23.11','552',' History , Military , United States','English'),
('B0209','Certain Poor Shepherds: A Christmas Tale','By Thomas, Elizabeth Marshall and Davidson, Andrew (ILT)','Simon & Schuster','November,1996','5.29','750',' Fiction , General','English'),
('B0210','The New Building Your Mate is Self-Esteem','By Rainey, Dennis and Rainey, Barbara','Thomas Nelson','July,1995','4.99','412',' Family & Relationships , Marriage & Long-Term Relationships','English'),
('B0211','Rogue Regimes: Terrorism and Proliferation','By Tanter, Raymond','Palgrave Macmillan','February,1999','58.61','25',' Political Science , International Relations , Arms Control','English'),
('B0212','The Abuse Excuse: And Other Cop-Outs, Sob Stories, and Evasions of Responsibility','By Dershowitz, Alan M.','Little Brown & Co','October,1994','5.47','397',' Social Science , General','English'),
('B0213','Houghton Mifflin Invitations to Literature: Student Anthology Level 1.4 Surprise 1997 (Invitations to Lit 1997)','By Hm (COR)','Houghton Mifflin','October,1996','6.12','446',' Juvenile Nonfiction , Language Arts , General','English'),
('B0214','The Pharaohs of Ancient Egypt (Megascope Series)','By Derouin, Claire','Barrons Juveniles','April,1998','4.99','904','','English'),
('B0215','1776-1976: Zweihundert Jahre deutsch-amerikanische Beziehungen = two hundred years of German-American relations : eine Dokumentation (German Edition)','By Piltz, Thomas','H. Moos','January,1975','10.99','729','','English'),
('B0216','Dannybird','By Philip Macht','Maxrom Press','January,1983','4.99','103','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0217','Technical Manual and Dictionary of Classical Ballet (Dover Books on Dance)','By Grant, Gail','Dover Publications','January,1982','7.71','364',' Performing Arts , Dance , Classical & Ballet','English'),
('B0218','Life and Death in Shanghai','By Cheng, Nien','Penguin','May,1988','5.29','663',' Biography & Autobiography , Personal Memoirs','English'),
('B0219','100 Favorite Roses (100 Favorite Series)','By Dunn, Teri','Metro Books','May,2000','5.29','490',' Gardening , Flowers , Annuals','English'),
('B0220','All Around The Town','By Clark, Mary Higgins','Simon & Schuster','January,1992','4.99','522',' Fiction , Thrillers , Suspense','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0221','New World Visions of Household Gods and Sacred Places: American Art and the Metropolitan Museum of Art 1650-1914','By Scully, Vincent','NEW YORK GRAPHIC SOCIETY','January,1988','13.12','68',' Art , General','English'),
('B0222','New Stories from the South 2001: The Year Best','By Ravenel, Shannon','Algonquin Books','September,2001','5.29','414',' Fiction , Southern','English'),
('B0223','Orchids as House Plants','By Northen, Rebecca Tyson','Dover Publications','June,1976','4.99','284',' Gardening , Flowers , Orchids','English'),
('B0224','The History of the American Sailing Navy: The Ships and Their Development','By Chapelle, Howard Irving','Bonanza Books','January,1949','29.29','636','','English'),
('B0225','The Experience of Opera (Norton Library, N706)','By Lang, Paul Henry','W. W. Norton & Company','November,1973','5.29','16',' Music , Genres & Styles , Opera','English'),
('B0226','The pattern of Soviet power','By Snow, Edgar','Random House','January,1945','5.29','894','','English'),
('B0227','The World of Science Fiction, 1926-1976: The History of a Subculture','By Del Rey, Lester','Ballantine Books','January,1979','4.99','685','','English'),
('B0228','Passionate Paradox; the Life of Marie Stopes','By briant, keith','w.w. norton','January,1962','5.47','957','','English'),
('B0229','The Law','By Vailland, Roger','Knopf','January,1958','12.99','190','','English'),
('B0230','Horsewatching: Why does a horse whinny and everything else you ever wanted to know','By Morris, Desmond','Crown','April,1989','4.99','996',' General"','English'),
('B0231','Linda Mccartney Home Cooking','By McCartney, Linda','Arcade Publishing','August,1992','4.99','877',' Cooking , Reference','English'),
('B0232','Herblock special for today','By Block, Herbert','Simon and Schuster','January,1958','11.48','439','','English'),
('B0233','Beginners Cookbook (Home Library)','By','Whitecap Books - AcP Publishing','June,1994','6.16','227',' Cooking , General','English'),
('B0234','Ghost Light: A Memoir','By Rich, Frank','Random House Trade Paperbacks','October,2001','5.29','148',' Biography & Autobiography , Entertainment & Performing Arts','English'),
('B0235','The Sands of Time: A Hermux Tantamoq Adventure','By Hoeye, Michael','Putnam Juvenile','September,2002','5.29','667',' Young Adult Fiction , Fantasy , General','English'),
('B0236','Murder at the Pentagon','By Truman, Margaret','Random House','April,1992','4.99','83',' Fiction , Mystery & Detective , General','English'),
('B0237','The Goomba Book of Love','By Schirripa, Steve and Fleming, Charles','Clarkson Potter','October,2003','5.09','386',' Humor , General','English'),
('B0238','Stanley Elkin Greatest Hits','By Elkin, Stanley','E. P. Dutton','November,1980','21.95','287',' Fiction , General','English'),
('B0239','Griffin & Sabine: An Extraordinary Correspondence','By Bantock, Nick','Chronicle Books','September,1991','5.29','167',' Fiction , Romance , General','English'),
('B0240','Wine in Everyday Cooking: "Cooking with Wine for Family and Friends"','By Ballard, Patricia','Wine Appreciation Guild','May,1995','5.29','643',' Cooking , Beverages , Wine & Spirits','English'),
('B0241','Leading Lady: The World and Theatre of Katharine Cornell','By Mosel, Ted','Little, Brown and Company','January,1978','10.99','529','','English'),
('B0242','Invasion of the Mind Swappers From Asteroid 6!','By Helquist, Brett (ILT) and Howe, James','Atheneum Books for Young Readers','July,2002','5.29','278',' Juvenile Fiction , Readers , Chapter Books','English'),
('B0243','The march of democracy','By Adams, James Truslow','C. Scribner sons','January,1932','5.54','994','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0244','Einstein Monsters','By Amis, Martin','Harmony','May,1987','15.4','571',' Fiction , General','English'),
('B0245','How To Live Through A Bad Day: 7 Powerful Insights From Christ Words on the Cross','By Hayford, Jack W.','Thomas Nelson Publishers','September,2001','4.99','669',' Religion , Christian Life , General','English'),
('B0246','The Ideals Country Kitchen Cookbook','By Kronschnab','Ideals Publishing','June,1975','4.99','541',' Cooking , Reference','English'),
('B0247','The Passenger from Scotland Yard: A Victorian Detective Novel','By Wood, H. Freeman','Dover Pubns','March,1981','12.51','736',' Fiction , General','English'),
('B0248','The 27-Ingredient Chili Con Carne Murders','By Pickard, Nancy','Delacorte Press','December,1992','5.29','87',' Fiction , Mystery & Detective , General','English'),
('B0249','Looking for Alaska','By Jenkins, Peter','St. Martin Press','November,2001','5.94','611',' Travel , United States , West , Pacific (AK, CA, HI, OR, WA)','English'),
('B0250','Give Us This Day','By Tudor, Tasha (ILT)','Philomel','October,1987','4.99','156',' Juvenile Fiction , Religious , Christian , General','English'),
('B0251','Crossing Over Jordan','By Brown, Linda Beatrice','One World/Ballantine','January,1995','4.99','33',' Fiction , General','English'),
('B0252','Sculpture & carving at Washington Cathedral','By Feller, Richard T','Cathedral Church of Saint Peter and Saint Paul','January,1976','14.62','216','','English'),
('B0253','When Birds Could Talk And Bats Could Sing','By Moser, Barry (ILT) and Hamilton, Virginia','Blue Sky Press','March,1996','5.41','489','','English'),
('B0254','Line of Fire: Continuing the Saga of the Corps','By Griffin, W. E. B.','G. P. Putnam Sons','January,1992','8.79','665',' Fiction , War & Military','English'),
('B0255','Sun and Shadow','By Green, Julie','Antietam Pr','October,1989','5','42',' Poetry , General','English'),
('B0256','Heartstring Quilts: Quilts Made Easy (Quilting)','By','Oxmoor House','May,1996','5.29','236','','English'),
('B0257','Crooked Hearts','By Boswell, Robert','Alfred A. Knopf','May,1987','5','123','','English'),
('B0258','The Lower Depths and Other Plays','By Gorky, Maksim','Yale University Press','September,1959','5.29','762',' Drama , European , General','English'),
('B0259','Chanticleer;: The poems of Terry Wise','By Terry Wise','McClure Press','January,1968','8.16','947','','English'),
('B0260','You Know You are A Republican/Democrat If...','By Benjamin, Frank','Sourcebooks Hysteria','August,2004','5.29','423',' Political Science , Political Process , Political Parties','English'),
('B0261','From Mama is Kitchen','By Smith, Catharine P. (EDT)','Ideals Publishing','June,1976','5.29','862',' Cooking , General','English'),
('B0262','The Silence Now: New and Uncollected Early Poems','By Sarton, May','W. W. Norton & Company','February,1990','5.29','804',' Poetry , American , General','English'),
('B0263','Especially for Mothers','By Rice, Helen Steiner','Barbour Publishing, Incorporated','March,1996','5.29','385',' Poetry , Subjects & Themes , Inspirational & Religious','English'),
('B0264','The Big Bite Book of Pizzas','By Jansz, Meg','Smithmark Pub','September,1994','5.51','544',' Cooking , General','English'),
('B0265','Stories In An Almost Classical Mode','By Brodkey, Harold.','Knopf','September,1988','10.99','479',' Fiction , Short Stories (single author)','English'),
('B0266','Princess Margaret','By Hope, Alice','F. Muller','January,1955','8.16','302','','English'),
('B0267','Msieu Robin : Lyrics and legends of Jean Baptiste and his friends','By Wallace Bruce Amsbary','Reilly & Lee','January,1925','7.49','876','','English'),
('B0268','With or Without and Other Stories','By Dickinson, Charles','Knopf','April,1987','6.97','527','','English'),
('B0269','Parenting Isnot for Cowards: Dealing Confidently With the Frustrations of Child-Rearing','By Dobson, James C.','Word','January,1987','4.99','277',' House & Home , General','English'),
('B0270','Frisky Business: All About Being Owned by a Cat','By Brethwaite, Chris, Ahern, Lee Ann, and Bridgeman, Bill et al','Hallmark Cards','April,1990','5.29','193','','English'),
('B0271','The Seacoast of Bohemia','By Freeling, Nicolas','Mysterious Pr','June,1995','13.12','756',' Fiction , Mystery & Detective , General','English'),
('B0272','The Arrival Kit: A Guide for your Journey in the Kingdom of God','By Ralph W. Neighbour Jr.','Touch Publications','January,1994','5.29','594','','English'),
('B0273','The Catholic Girl is Guide to Sex','By Anderson, Melinda, Murray, Kathleen, and Arnold, Alli (ILT)','Harmony','September,2003','5.29','246',' Self-help , Sexual Instruction','English'),
('B0274','In the Shadow of the Ark','By Provoost, Anne and Nieuwenhuizen, John','Arthur A. Levine Books','August,2004','5.47','600',' Young Adult Fiction , Religious , General','English'),
('B0275','The Supernaturalist','By Colfer, Eoin','Hyperion','May,2004','4.99','599',' Young Adult Fiction , Science Fiction , General','English'),
('B0276','U and I: A True Story','By Baker, Nicholson','Random House','April,1991','5.29','250',' Literary Criticism , General','English'),
('B0277','Love Invents Us','By Amy Bloom','Pan Books Ltd','April,1997','4.99','254','','English'),
('B0278','Zero Db and Other Stories','By Bell, Madison Smartt','Ticknor & Fields','January,1987','5','932',' Fiction , General','English'),
('B0279','The Heart of a Leader','By Blanchard, Kenneth H.','David C. Cook','June,1999','4.99','242',' Religion , Inspirational','English'),
('B0280','Hefner','By Brady, Frank','MACMILLAN','January,1974','5.29','113','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0281','The Secrets of Savvy Networking: How to Make the Best Connections for Business and Personal Success','By RoAne, Susan','Grand Central Publishing','April,1993','5.29','538',' Business & Economics , General','English'),
('B0282','A Shaker Dozen','By Rocheleau, Paul (ILT) and Homsen, Kathleen','Chronicle Books','July,1999','5.29','198',' Juvenile Nonfiction , Concepts , Counting & Numbers','English'),
('B0283','Personal History','By Graham, Katharine','Alfred A. Knopf','February,1997','5.29','938',' Biography & Autobiography , Editors, Journalists, Publishers','English'),
('B0284','Friendship with God: an uncommon dialogue','By Walsch, Neale Donald','Putnam','October,1999','4.99','406',' Religion , Spirituality','English'),
('B0286','13 Things You Gotta Know to Make it as a Christian (Powerlink Student Devotional)','By McDowell, Josh and Hostetler, Bob','Thomas Nelson','November,1992','4.99','483',' Young Adult Nonfiction , Religious , Christian , General','English'),
('B0287','Dave Barry Guide to Marriage and/or Sex','By Barry, Dave','Rodale Books','January,2000','4.99','527',' Humor , Form , Comic Strips & Cartoons','English'),
('B0288','The Festival Cookbook: Four Seasons of Favorites','By Good, Phyllis Pellman','Good Books','May,2013','5.94','334',' Cooking , Seasonal','English'),
('B0289','Shella','By Vachss, Andrew H.','Knopf','March,1993','4.99','768',' Fiction , Mystery & Detective , General','English'),
('B0290','What To Cook When You Think There is Nothing in the House To Eat: More Than 175 Easy Recipes And Meal Ideas','By Schwartz, Arthur','Harper Perennial','February,2000','5.29','403',' Cooking , General','English'),
('B0291','Little Vegetarian Feasts: Main-Dish Grains','By Shulman, Martha Rose and Drechsler, Debbie (ILT)','Bantam','May,1993','10.98','69',' Cooking , General','English'),
('B0292','Stir-Fry (Sunset Creative Cooking Library)','By Sunset Books','Sunset Pub Co','October,1994','5.29','937',' Cooking , Methods , Wok','English'),
('B0293','Collected Stories of Reynolds Price','By Price, Reynolds','Scribner','June,1993','6.24','435',' Fiction , Short Stories (single author)','English'),
('B0294','DIY Girl','By Gesue, Monica (ILT) and Bonnell, Jennifer','Puffin','June,2003','5.29','359',' Young Adult Nonfiction , Crafts & Hobbies','English'),
('B0295','The Divine Pastime: Theatre Essays','By Clurman, Harold','MacMillan Publishing Company.','January,1974','5.29','85','','English'),
('B0296','What is Bred in the Bone (Cornish Trilogy)','By Davies, Robertson','Viking','November,1985','4.99','827',' Fiction , Biographical','English'),
('B0297','Mastering the Zone: The Next Step in Achieving SuperHealth and Permanent Fat Loss','By Sears, Barry and Goodbody, Mary','Regan Books','January,1997','4.99','532',' Health & Fitness , Diet & Nutrition , Diets','English'),
('B0298','North Carolina Lighthouses','By David Stick','Dept of Cultural Resources & History','June,1984','4.99','411','','English'),
('B0299','Analyzing Performance Problems, or You Really Oughta Wanna','By Robert Frank Mager, Peter Pipe','Pitman Management and Training','January,1984','5.29','277','','English'),
('B0300','Reviving Ophelia: Saving the Selves of Adolescent Girls (Ballantine Reader Circle)','By Pipher, Mary Bray','Ballantine Books','February,1995','4.99','904',' Family & Relationships , Life Stages , Adolescence','English'),
('B0301','Napoleon and Hitler','By Seward, Desmond','Viking','May,1989','5.36','647',' History , Europe , Western','English'),
('B0302','Encounter and Encouragement: A Bicentennial Review of German-American Relations','By Joachim Hans Schwelien','Bonner Universitats Buchdruckerei','January,1976','4.99','89','','English'),
('B0303','Too Busy to Clean','By Barrett, Patti','Random House Value Publishing','February,1994','5.72','168',' House & Home , Cleaning & Caretaking','English'),
('B0304','Keeping Your Kids Catholic: It May Seem Impossible but It Can Be Done','By Bert Ghezzi','Servant Pubns','October,1989','4.99','292',' Religion , General','English'),
('B0305','Vines (Burpee American Gardening Series)','By Bales, Suzanne Frutig','Macmillan General Reference','February,1995','5.29','269',' Gardening , General','English'),
('B0306','Roads to Antietam','By Schildt, John W.','Burd Street Pr','May,1997','5.29','813',' History , Military , Wars & Conflicts (Other)','English'),
('B0308','The Art of Cooking with Herbs & Spices','By Miloradovich, Milo','Doubleday','January,1950','5.29','426','','English'),
('B0309','The uncommon cookbook','By Mellinkoff, Ruth','W. Ritchie Press','January,1968','24.26','93','','English'),
('B0310','List Your Self: Listmaking as the Way to Self-Discovery','By Segalove, Ilene and Velick, Paul Bob','Andrews McMeel Publishing','September,1996','4.99','872',' Body, Mind & Spirit , Inspiration & Personal Growth','English'),
('B0311','The Insider is Guide to Getting Published: Why They Always Reject Your Manuscript and What You Can Do About It','By Boswell, John','Main Street Books','December,1996','4.99','869',' Reference , General','English'),
('B0312','Walking the Cat by Tommy " Tip" Paine: Gordon Liddy Is My Muse II','By Batchelor, John Calvin','Linden Pub','April,1991','5.8','358',' Fiction , General','English'),
('B0313','The Theory of the Modern Stage: An Introduction to Modern Theatre and Drama','By Eric  Bentley','Penguin Books','November,1976','4.99','778','','English'),
('B0314','Mistler is Exit','By Begley, Louis','Knopf','September,1998','4.99','711',' Fiction , General','English'),
('B0315','Small Patchwork & Quilting','By Linsley, Leslie, Aron, Jon (PHT), and Savonen, Robby (ILT)','Meredith Pr','August,1993','10.99','709',' Art , General','English'),
('B0316','A Portfolio of Outdoor Furnishing Ideas (Portfolio Ofideas)','By Cowles Creative Publishing, Cy Decosse Inc','Cowles Creative Publishing','December,1996','4.99','473',' Architecture , Interior Design , General','English'),
('B0317','Cooking With Helen McCully Beside You','By McCully, Helen','Random House','January,1970','8.79','1','','English'),
('B0318','Christopher Idone Glorious American Food','By Idone, Christopher','Random House','December,1985','10.99','141',' Cooking , General','English'),
('B0319','Family portrait','By Bowen, Catherine Drinker','Little Brown','January,1970','6.72','793','','English'),
('B0320','The Whole Shebang: A State-of-the-Universe(s) Report','By Ferris, Timothy','Simon & Schuster, Inc.','January,1997','8.29','635',' Science , Cosmology','English'),
('B0321','Eating Well for Optimum Health: The Essential Guide to Bringing Health and Pleasure Back to Eating','By Weil, Andrew','William Morrow Paperbacks','March,2001','4.99','735',' Health & Fitness , Diet & Nutrition , Nutrition','English'),
('B0322','Teverton Hall','By Gillespie, Jane','St Martins Pr','March,1984','4.99','536',' Fiction , Romance , Historical','English'),
('B0323','Inference of guilt','By Greene, Harris','Doubleday','January,1982','36.2','99','','English'),
('B0324','Lines in the Sand: Desert Storm and the Remaking of the Arab World','By Amos, Deborah','Simon & Schuster','July,1992','5.51','575',' History , General','English'),
('B0325','The Witling','By Vinge, Vernor','Bluejay','May,1986','20.85','730',' Fiction , Science Fiction , General','English'),
('B0326','The Courage to Be Rich: Creating a Life of Material and Spiritual Abundance','By Orman, Suze','Riverhead Hardcover','March,1999','4.99','574',' Business & Economics , Personal Finance , Taxation','English'),
('B0327','Our Nature','By Gilbert, Bill','University of Nebraska Press','August,1987','5.29','997',' Science , General','English'),
('B0328','The Gift of Sex: A Guide to Sexual Fulfillment','By Penner, Clifford and Penner, Joyce','Thomas Nelson Inc','June,1981','4.99','440',' Religion , Christian Life , General','English'),
('B0329','Basic Principles of Classical Ballet','By Vaganova, A. Ia.','Dover Publications','June,1969','4.99','931',' Performing Arts , Dance , Classical & Ballet','English'),
('B0330','The Biblical Road to Blessing','By Hinn, Benny','Thomas Nelson Inc','October,1996','4.99','732',' Religion , Christian Life , Spiritual Growth','English'),
('B0331','Remembered Laughter: The Life of Noel Coward. Orig Pub in Great Britain Under Title: Life of Noel Coward','By Lesley, Cole','Random House','November,1976','10.99','142','','English'),
('B0332','Peanuts: A Golden Celebration: The Art and the Story of the World is Best-Loved Comic Strip','By Schulz, Charles M. and Larkin, David (EDT)','HarperCollins','September,1999','10.99','8',' Art , Techniques , Cartooning','English'),
('B0333','Other loyalties;: A politics of personality','By Brower, Brock','Atheneum','January,1968','8','719','','English'),
('B0334','The Language of Life: A Festival of Poets','By Moyers, Bill D., Grubin, David, and Haba, James','Bantam Doubleday Dell','July,1995','10.99','948',' Poetry , General','English'),
('B0335','The Shutterfly Guide to Great Digital Photos (CLS.EDUCATION)','By Johnson, Dave and Housenbold, Jeffrey','McGraw-Hill Professional','November,2008','4.99','452',' Computers , Programming , Games','English'),
('B0336','Henry VIII and His Court','By Williams, Neville','Macmillan Pub Co','October,1971','8.79','274',' History , General','English'),
('B0337','The New Grove Mozart','By Sadie, Stanley','W W Norton & Co Inc','February,1983','5.29','868',' Biography & Autobiography , Composers & Musicians','English'),
('B0338','Short bike rides in New Jersey','By Santelli, Robert','Globe Pequot Press','January,1988','4.99','405','','English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0339','Pledged: The Secret Life of Sororities','By Robbins, Alexandra','Hachette Books','July,2005','4.99','672',' Social Science , Women is Studies','English'),
('B0340','Smoky Mountains Trout Fishing Guide','By Kirk, Don','Menasha Ridge Press','May,1995','4.99','303',' Sports & Recreation , Fishing','English'),
('B0341','A Thing or Two About Soccer','By Buckley, James, Teitelbaum, Michael, and Wolff, Rick (FRW)','A Thing or Two Media','March,2001','5.29','472',' Sports & Recreation , Soccer','English'),
('B0342','Guide to Cults and New Religions','By Enroth, Ronald','Intervarsity Pr','March,1983','4.99','283',' Religion , Cults','English'),
('B0343','Preparatory Piano Literature: Origianl Keyboard Classics With Optional Teacher Duets, Elementary (Developing Artist)','By Faber, Randall (COP), Faber, Nancy (COM), and Hansen, Jeanne (CON)','F J H Music Co','December,1990','4.99','779',' Music , Musical Instruments , Piano & Keyboard','English'),
('B0344','Winter Has Lasted Too Long','By Kavanaugh, James J','Dutton','June,1977','5.29','497','','English'),
('B0345','Iron Man: The Cal Ripken, Jr. Story','By Rosenfeld, Harvey','St Martins Pr','September,1995','4.99','914',' Sports & Recreation , Baseball , General','English'),
('B0346','The Only Way I Know','By Ripken, Cal, Jr. and Bryan, Mike','Viking Adult','May,1997','4.99','40',' Biography & Autobiography , Sports','English'),
('B0347','Knots on a Counting Rope (Reading Rainbow Books)','By Rand, Ted (ILT), Martin, Bill, and Archambault, John','Square Fish','September,1997','4.99','9',' Juvenile Fiction , Disabilities & Special Needs','English'),
('B0348','New Ways With Fresh Flowers: 50 Inspirational Projects for Contemporary Floral Designers','By Barnett, Fiona','Smithmark Pub','January,1996','10.99','314','','English'),
('B0349','QPB Encyclopedia of Word & Phrase Origins','By Hendrickson, Robert','Facts on File Inc.','January,1998','8.14','232','','English'),
('B0350','German A La Cartoon','By Small, Albert H. (EDT)','McGraw-Hill Education','November,1990','5.29','888',' Foreign Language Study , German','English'),
('B0351','The Complete Idiot is Guide to Getting Published','By Bykofsky, Sheree and Sander, Jennifer Basye','Alpha','June,1998','5.29','363',' Language Arts & Disciplines , Publishing','English'),
('B0352','Death Gets a Time-Out (Mommy-Track Mysteries)','By Waldman, Ayelet','Berkley Hardcover','July,2003','4.99','292',' Fiction , Mystery & Detective , Women Sleuths','English'),
('B0353','The Reader is Companion to South Africa','By Ryan, Alan (EDT)','Harvest Books','February,1999','4.99','170',' Travel , Africa , General','English'),
('B0354','Dealing With People You Canot Stand','By Brinkman, Rick and Kirschner, Rick','McGraw-Hill Companies','August,1994','4.99','632','','English'),
('B0355','Operation Redemption: A Vision of Hope in an Age of Turmoil','By Trevelyan, George','E. P. Dutton','May,1985','5.29','443','','English'),
('B0356','Let Freedom Ring: Winning the War of Liberty over Liberalism','By Hannity, Sean','William Morrow Paperbacks','February,2004','4.99','775',' Political Science , Terrorism','English'),
('B0357','Hamlet (Oxford World is Classics)','By Shakespeare, William and Hibbard, G. R. (EDT)','Oxford University Press','June,1998','5.29','563',' Drama , Shakespeare','English'),
('B0358','The Playboy of the Western World and Other Plays: Riders to the Sea; The Shadow of the Glen; The Tinker Wedding; The Well of the Saints; The Playboy ... of the Sorrows (Oxford World is Classics)','By Synge, John Millington and Saddlemyer, Ann (EDT)','Oxford University Press','November,1998','4.99','24',' Drama , European , English, Irish, Scottish, Welsh','English'),
('B0359','Grandparenthood','By Westheimer, Ruth K. and Kaplan, Steven','Routledge','March,2000','5.29','552',' Social Science , Gerontology','English'),
('B0360','Twelve','By Kittredge, Elaine','Optext','June,1983','5.79','495','','English'),
('B0361','Cycles of Fire: Stars, Galaxies and the Wonder of Deep Space','By Hartmann, William K.','Workman Pub Co','October,1987','6.38','976','','English'),
('B0362','The Dancing Healers: A Doctor is Journey of Healing with Native Americans','By Hammerschlag, Carl A.','HarperOne','September,1989','4.99','648',' Religion , Ethnic & Tribal','English'),
('B0363','Time Exposure: A Photographic Record of the Dinosaur Age','By Dixon, Dougal and Burton, Jane','Beaufort Books','September,1984','5.29','396',' Juvenile Nonfiction , General','English'),
('B0364','The Way of the Trout: Anglers, Wild Fish and Running Water','By Montgomery, M. R. and Brown-Wing, Katherine (ILT)','Avon Books','April,1993','5.37','912','','English'),
('B0365','Hope Was Here (Newbery Honor Book)','By Bauer, Joan','G.P. Putnam Sons Books for Young Readers','September,2000','5.29','303',' Young Adult Fiction , Fantasy , General','English'),
('B0366','A Tale of Two Cities (Oxford World is Classics)','By Dickens, Charles and Sanders, Andrew (EDT)','Oxford University Press','June,1998','5.29','372',' Fiction , Classics','English'),
('B0367','Frank Sinatra: An American Legend','By Sinatra, Nancy','Readers Digest Assn','September,1998','8.24','451','','English'),
('B0368','WP8 - Theory Lessons - Level 2 - Bastien Piano Library','By James Bastien','Kjos Music Company','January,1976','5.29','846','','English'),
('B0369','Who Owns America?','By Hickel, Walter J.','Prentice Hall','January,1971','5.29','956','','English'),
('B0370','Bloodsong','By Neimark, Jill','Random House','August,1993','5.35','121',' Fiction , General','English'),
('B0371','The Vampire Book: The Encyclopedia of the Undead','By Melton, J. Gordon','Visible Ink Press','November,1998','8.35','786',' Body, Mind & Spirit , Occultism','English'),
('B0372','A Midsummer Night Dream (Oxford World is Classics)','By Shakespeare, William and Holland, Peter (EDT)','Oxford University Press','June,1998','5.29','111',' Drama , European , English, Irish, Scottish, Welsh','English'),
('B0373','Bob Miller is Calc for the Clueless: Calc I (Bob Miller Clueless Series)','By Miller, Robert','McGraw-Hill','November,1997','4.99','33',' Mathematics , Calculus','English'),
('B0374','Mariel of Redwall','By Jacques, Brian and Chalk, Gary (ILT)',' General"','March,1999','5.29','460',' Fantasy ','English'),
('B0375','The Body Book: A Fantastic Voyage to the World Within','By Bodanis, David','Little, Brown and Company','September,1984','10.99','833',' Medical , General','English'),
('B0376','Nerilka Story (Dragonriders of Pern Series)','By McCaffrey, Anne','Del Rey','January,1987','4.99','855',' Fiction , Fantasy , General','English'),
('B0377','Americas: The Changing Face of Latin America and the Caribbean (A Main Selection of the History Book Club)','By Winn, Peter','University of California Press','March,1995','6.6','523',' Social Science , Anthropology , Cultural & Social','English'),
('B0378','Mayo Clinic On Vision And Eye Health: Practical Answers on Glaucoma, Cataracts, Macular Degeneration & Other Conditions ("MAYO CLINIC ON" SERIES)','By Mayo Clinic (COR) and Buettner, Helmut (EDT)','Kensington Pub Corp','May,2002','5.29','627',' Health & Fitness , Health Care Issues','English'),
('B0379','Parrot in the Oven: Mi vida (Cover May Vary)','By Martinez, Victor','HarperCollins Espanol','December,2004','5.29','728',' Young Adult Fiction , Classics','English'),
('B0380','A Passion for Golf: The Best of Golf Writing','By Garrity, John (INT) and Bishop, Schuyler (EDT)','Thomas Dunne Books','June,1998','5.58','517',' Sports & Recreation , Golf','English'),
('B0381','The Way of the Wizard: Twenty Spiritual Lessons for Creating the Life You Want','By Chopra, Deepak','Harmony','December,1995','4.99','964',' Religion , Spirituality','English'),
('B0382','Handmade for Profit!: Hundreds of Secrets to Success in Selling Arts and Crafts','By Brabec, Barbara','M. Evans & Company','October,1996','5.29','660',' Crafts & Hobbies , Reference','English'),
('B0383','Totline Teaching Tales ~ Short-Short Stories ~ Simple Stories For Young Children Plus Seasonal Activities','By Warren, Jean and McKinnon, Elizabeth S. (EDT)','Warren Publishing House, Inc.','July,1987','5.29','926','','English'),
('B0384','Treasury Whole Language Literature Ideas (Troll Teacher Idea Books)','By Reeves, Barbara','Troll Communications','January,1998','5.29','313','','English'),
('B0385','Meet the Author (Troll Professional Series)','By Orzakis, Laurie','Troll Pr','June,1992','5.29','159','','English'),
('B0386','Adventuring Along the Southeast Coast: The Sierra Club Guide to the Low Country, Beaches, and Barrier Islands of North Carolina, South Carolina, and (Sierra Club Adventure Travel Guides)','By Bowen, John','Sierra Club Books','March,1993','5.29','259',' Travel , United States , South , South Atlantic (DC, DE, FL, GA, MD, NC, SC, VA, WV)','English'),
('B0387','Totline Teaching Tales ~ Teeny-Tiny Folktales ~ Simple Folktales For Young Children Plus Flannelboard Patterns','By Warren, Jean','Warren Publishing House, Inc.','July,1987','5.29','241','','English'),
('B0388','Discovering Oceans, Lakes, Ponds and Puddles','By Frame, Jeron Ashford and Holladay, Scott (ILT)','Chariot Victor Pub','August,1994','5.29','407',' Young Adult Nonfiction , Science & Nature , Earth Sciences','English'),
('B0389','Game Birds Of North America','By Rue, Leonard Lee','Outdoor Life','January,1973','10.99','584','','English'),
('B0390','The Birdwatcher Companion','By MacKay, Barry Kent','Key Porter Books','April,1994','13.13','490','','English'),
('B0391','Stalking birds with color camera,','By Allen, Arthur A','National Geographic Society','January,1961','15.4','307','','English'),
('B0392','Glorious French Food: A Fresh Approach to the Classics','By Peterson, James','John Wiley & Sons','August,2002','9.5','727',' Cooking , Regional & Ethnic , French','English'),
('B0393','The Portrait of a Lady (Oxford World is Classics)','By James, Henry and Bradbury, Nicola (EDT)','Oxford University Press','May,1998','5.29','928',' Fiction , Classics','English'),
('B0394','Wilderness kingdom, Indian life in the Rocky Mountains: 1840-1847;: The journals & paintings of Nicolas Point','By Point, Nicolas','Holt, Rinehart and Winston','January,1967','17.38','419','','English'),
('B0395','Managing Transitions: Making The Most Of Change','By Bridges, William','Da Capo Lifelong Books','September,1991','4.99','181',' Business & Economics , Management','English'),
('B0396','False Assumptions','By Cloud, Henry and Townsend, John','Zondervan','March,1994','5.27','713','','English'),
('B0397','Mortgaging The Earth: The World Bank, Environmental Impoverishment, and the Crisis of Development','By Rich, Bruce','Beacon Pr','February,1994','5.64','431',' Science , Environmental Science','English'),
('B0398','Banff Springs the Story of a Hotel','By Bart Robinson','Summerthought, Limited','January,1989','5.29','787','','English'),
('B0399','Origins Of History','By Butterfield, Herbert','Basic Books','September,1981','5.29','568','','English'),
('B0400','What Bird Did That?: A Driver is Guide to Some Common Birds of North America','By Silver, Burton','Ten Speed Press','March,2004','4.99','399',' Pets , Birds','English'),
('B0401','A Separate Peace with Connections (HRW Library)','By Holt Mcdougal (COR)','Holt McDougal','September,1999','5.29','571',' Young Adult Fiction , General','English'),
('B0402','Inner Revolution','By Thurman, Robert A. F.','Riverhead Books','March,1999','4.99','648',' Religion , Buddhism , General','English'),
('B0403','Lost Cities of the Maya (Discoveries)','By Baudez, Claude F. and Picasso, Sydney','Harry N. Abrams','March,1992','5.29','132',' Social Science , Archaeology','English'),
('B0404','Life in Ancient Egypt','By Erman, Adolf','Dover Publications','June,1971','4.99','72',' History , General','English'),
('B0405','25 Bicycle Tours in New Jersey','By Zatz, Aline and Zatz, Joel','Countryman Pr','May,1988','5.29','663',' Travel , United States , Northeast , Middle Atlantic (NJ, NY, PA)','English'),
('B0406','The World of the Ancient Maya','By Henderson, John S.','Cornell Univ Pr','December,1997','10.99','629',' Social Science , Archaeology','English'),
('B0407','A delicately personal matter: A J.D. Mulroy mystery novel','By Werry, Richard R.','Dodd, Mead','January,1986','5.29','28','','English'),
('B0408','Terrorism and Democracy','By Turner, Stansfield','Houghton Mifflin Harcourt','July,1991','4.99','395','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0409','A Son of Thunder: Patrick Henry and the American Republic','By Mayer, Henry','Grove Press','June,2001','5.35','991',' Biography & Autobiography , Political','English'),
('B0410','Cam Jansen and the Triceratops Pops Mystery #15','By Adler, David A. and Natti, Susanna (ILT)','Viking Books for Young Readers','October,1995','4.99','302',' Juvenile Fiction , Readers , Chapter Books','English'),
('B0411','Summerland','By Chabon, Michael','Hyperion Books','October,2002','6.06','296',' Juvenile Fiction , Fantasy & Magic','English'),
('B0412','Three Gospels','By Price, Reynolds (EDT)','Scribner','May,1996','5.29','593',' Religion , Biblical Studies , General','English'),
('B0413','Creative Conversations: The Writer is Complete Guide to Conducting Interviews','By Schumacher, Michael','Writer is Digest Books','March,1990','5.29','51',' Language Arts & Disciplines , General','English'),
('B0414','Doing It','By Burgess, Melvin','Henry Holt and Co. (BYR)','May,2004','5.29','833',' Young Adult Fiction , Social Themes , Emotions & Feelings','English'),
('B0415','The Wind in the Willows (Children is Classics)','By Kenneth Grahame','Gallery Books','November,1989','4.99','770','','English'),
('B0416','500 Full-Size Patchwork Patterns','By Malone, Maggie','Sterling Pub Co Inc','December,1899','5.29','600',' Art , General','English'),
('B0417','Cases in managerial finance (The Dryden Press series in finance)','By Eugene F Brigham','Dryden Press','January,1983','11.01','193','','English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0418','Stress And The Family: Coping With Normative Transitions (Psychosocial Stress Series)','By McCubbin, Hamilton I. (EDT)','Routledge','March,1983','5.29','248',' Psychology , Psychotherapy , Couples & Family','English'),
('B0419','Demon of the Waters: The True Story of the Mutiny on the Whaleship Globe','By Gibson, Gregory','Little Brown','May,2002','4.99','377',' History , Modern , 19th Century','English'),
('B0421','Struggle for Intimacy (Adult Children of Alcoholics series)','By Woititz, Janet Geringer','Health Communications Inc','August,1986','5.29','943',' Self-help , Codependency','English'),
('B0422','550 Home Landscaping Ideas','By Fell, Derek and Erler, Catriona','Simon & Schuster','February,1992','10.99','160',' Gardening , General','English'),
('B0423','Money Harmony: Resolving Money Conflicts in Your Life and Relationships','By Mellan, Olivia','Walker & Co','May,1994','16.49','715',' Family & Relationships , General','English'),
('B0424','Who Says Elephants Canot Dance?:  Inside IBM Historic Turnaround','By Gerstner, Louis V., Jr.','Harper Business','December,2002','4.99','115',' Business & Economics , Management','English'),
('B0425','The Expert Guide To Backyard Birdfeeding','By Adler, Bill, Hughes, Heide, and Hughes, Heidi','Crown Publishers, Inc.','January,1990','4.99','556',' Science , General','English'),
('B0426','Totline 123 Puppets (1 2 3 Series:  Language Ages 2-6)','By Warren, Jean','Warren Publishing House, Inc.','April,1989','5.29','147','','English'),
('B0427','American Artists On Art: From 1940 To 1980 (Icon Editions)','By Johnson, Ellen H. (EDT)','Routledge','August,1982','5.29','614',' Art , Criticism & Theory','English'),
('B0428','Swan watch','By Budd Schulberg','Delacorte Press','January,1975','5.47','22','','English'),
('B0429','The American musical theater;: A consideration (A CBS Legacy collection book)','By Engel, Lehman','Distributed by the Macmillan Co','January,1967','10.99','796','','English'),
('B0430','The Princess Test (Princess Tales)','By Andersen, Hans Christian, Levine, Gail Carson, Elliott, Mark (ILT), and Elliott, Mark','HarperCollins','March,1999','10.99','282',' Juvenile Fiction , Royalty','English'),
('B0431','The Nature of Theatre.','By Roberts, Vera Mowry','HarperCollins','January,1971','7.85','419','','English'),
('B0432','Interactive Bulletin Boards','By Prizzi, Elaine and Hoffmann, Jeanne','Lake Pub Co','June,1984','4.99','421','','English'),
('B0433','Parthian Words','By Storm Jameson','Harper & Row','January,1971','5.86','344','','English'),
('B0434','Best Cat Stories','By O Mara, Lesley and Geldart, William Geldart (ILT)','Wings','June,1992','5.29','488',' Pets , Cats , General','English'),
('B0435','Religion in America: An historical account of the development of American religious life','By Hudson, Winthrop Still','Scribner','January,1981','12.99','412','','English'),
('B0436','Why Canot I Fall in Love? A 12-Step Program','By Boteach, Shmuel','William Morrow','May,2001','5.29','76',' Religion , Judaism , General','English'),
('B0437','Wolf in the Shadows (A Sharon Mccone Mystery)','By Muller, Marcia','Mysterious Pr','July,1993','5.53','36',' Fiction , Mystery & Detective , General','English'),
('B0438','Just Build the Ark and the Animals Will Come:: Children on Bible Stories','By Heller, David','Villard','March,1994','5.29','156',' Religion , General','English'),
('B0439','A Man for All Seasons: A Play in Two Acts','By Bolt, Robert','Vintage','April,1990','5.29','431',' Drama , European , English, Irish, Scottish, Welsh','English'),
('B0440','Jeremy: The Tale of an Honest Bunny','By Karon, Jan and Weidner, Teri (ILT)','Viking Juvenile','January,2000','4.99','254',' Juvenile Fiction , Religious , Christian , General','English'),
('B0441','Invisible No More: The Secret Lives of Women Over 50','By Fisher, Renee, Kramer, Joyce, Peelen, Jean, and Llc, Invisible No More','iUniverse','June,2005','5','827',' Body, Mind & Spirit , Inspiration & Personal Growth','English'),
('B0442','Harrap Spanish and English Pocket Dictionary (Harrap Dictionaries)','By Alvarez Garcia, Teresa (EDT), Rodger, Liam (EDT), and Ron Diaz, Elena (EDT)','McGraw-Hill','June,2005','4.99','841',' Reference , Dictionaries','English'),
('B0443','The National Air and Space Museum','By Bryan, C. D. B.','Abrams','January,1979','11.71','435','','English'),
('B0444','Free Flight: From Airline Hell to a New Age of Travel','By Fallows, James M.','PublicAffairs','June,2001','4.99','664',' Business & Economics , Industries , Hospitality, Travel & Tourism','English'),
('B0445','The Book of Massage: The Complete Step-by-Step Guide To Eastern And Western Techniques','By Lidell, Lucinda, Thomas, Sara, Cooke, Carola Beresford, and Porter, Anthony','Simon & Schuster','December,1984','4.99','232','','English'),
('B0446','The practice of social research','By Babbie, Earl','Wadsworth Pub. Co','January,1983','6.72','752','','English'),
('B0447','Endangered Pleasures: In Defense of Naps, Bacon, Martinis, Profanity, and Other Indulgences','By Holland, Barbara','William Morrow Paperbacks','June,2000','5.29','348',' Health & Fitness , Sexuality','English'),
('B0448','Hosting the Birds: How to Attract Birds to Nest in Your Yard','By Mahnken, Jan','Garden Way Pub Co','April,1989','5.29','277','','English'),
('B0449','Families on the Fault Line','By Rubin, Lillian B.','Harper Perennial','December,1994','5.29','426',' Social Science , General','English'),
('B0450','Our Last Best Shot: Guiding our Children Through Early Adolescence','By Stepp, Laura Sessions','Riverhead Books','August,2001','4.99','67',' Family & Relationships , Parenting , General','English'),
('B0451','Intermediate Algebra Functions and Authentic Applications','By Lehmann, Jay','Prentice Hall College Div','December,1999','7.4','987',' Mathematics , Algebra , General','English'),
('B0452','The Clustering of America','By Weiss, Michael J.','HarperCollins','October,1989','4.99','845',' Social Science , General','English'),
('B0453','Nelson Rockefeller,: A biography','By Morris, Joe Alex','Harper','January,1960','14.62','27','','English'),
('B0454','The PreHistory of The Far Side: A 10th Anniversary Exhibit (Volume 14)','By Larson, Gary','Andrews And McMeel Publishing','September,1989','8.79','612',' Humor , Form , Comic Strips & Cartoons','English'),
('B0455','Double Buckeyes: A Story of the Way America Used to Be','By Shuster, Bud','White Mane Pub','September,1999','5','2',' Juvenile Fiction , Historical , General','English'),
('B0456','Investment Biker: Around the World With Jim Rogers','By Rogers, Jim','Adams Media Corp','August,1995','5.29','782',' Business & Economics , General','English'),
('B0457','Lyndon Baines Johnson: Late a President of the United States: Memorial Services in the Congress of the United States and tributes in eulogy','By Various','U.S. Congress, Joint Committee on Printing','January,1973','19.95','239','','English'),
('B0458','Readings in art history','By Spencer, Harold','Scribner','January,1976','5.29','886','','English'),
('B0459','Shakedown: Exposing The Real Jesse Jackson','By Timmerman, Kenneth R.','Regnery Publishing, Inc.','March,2002','8.79','341',' Political Science , Civil Rights','English'),
('B0460','The Man Who Saw Through Time (The Scribner Library, Lyceum Editions, No. SL429)','By Eiseley, Loren C.','Charles Scribner Sons','April,1973','5.29','275',' Philosophy , General','English'),
('B0461','Speaking Out for Animals: True Stories About People Who Rescue Animals','By Stallwood, Kim W. (EDT)','Lantern Books','December,2019','5.29','3',' Nature , Animals , General','English'),
('B0462','Message from Forever: A Novel of Aboriginal Wisdom','By Morgan, Marlo','Harper','June,1998','4.99','319',' Fiction , Literary','English'),
('B0463','A Prophet In His Own Country: The Triumphs and Defeats of Adlai E. Stevenson','By Kenneth S. Davis','Doubleday','January,1957','36.61','886','','English'),
('B0464','Hacking Exposed: Network Security Secrets & Solutions, Third Edition (Hacking Exposed)','By McClure, Stuart, Scambray, Joel, and Kurtz, George','Mcgraw-Hill Osborne Media','September,2001','6.78','541',' Computers , Security , General','English'),
('B0465','Aditi: The Living Arts of India','By Adams, Robert and Sethi, Rajeev (EDT)','Smithsonian Institution Press','May,1985','10.99','355','','English'),
('B0466','Shuffle Up and Deal: The Ultimate No Limit Texas Hold em Guide (World Poker Tour)','By Sexton, Mike','William Morrow Paperbacks','March,2005','4.99','658',' Games & Activities , Card Games , Poker','English'),
('B0467','Practical Small Gardens','By McHoy, Peter','Hermes House','January,2003','8.79','905',' Gardening , Vegetables','English'),
('B0468','A Taste for Love: A Romantic Cookbook for Two','By Harbison, Elizabeth M. and McGowan, Mary','Gramercy','February,1996','5.29','446',' Cooking , Courses & Dishes , General','English'),
('B0469','Venom of Argus','By Avery, Richard','Fawcett','August,1976','4.99','281','','English'),
('B0470','The Closing of the American Mind','By Bloom, Allan','Simon & Schuster','May,1988','4.99','249',' Social Science , General','English'),
('B0471','Fight Fat After Forty','By Peeke, Pamela','Viking Adult','April,2000','4.99','187',' Health & Fitness , General','English'),
('B0472','On the Edge of Cliff','By Pritchett, V. S.','Random House','October,1979','5.29','394','','English'),
('B0473','Romans','By Navigators','NavPress','December,2018','4.99','166',' Religion , Biblical Studies , New Testament , Paul Letters','English'),
('B0474','Ainot You Glad You Joined the Republicans?: A Short History of the Gop','By Batchelor, John Calvin','Henry Holt & Co','May,1996','5.76','96',' Political Science , General','English'),
('B0475','The Professional: Lyndon B. Johnson','By White, William Smith','Houghton Mifflin','January,1964','12.12','526','','English'),
('B0476','Donot Know Much About the Bible: Everything You Need to Know About the Good Book but Never Learned','By Davis, Kenneth C.','William Morrow','September,1998','8.79','126',' Religion , Biblical Studies , General','English'),
('B0477','The Power of Nice','By Dale, Jim, Shapiro, Ronald M., and Jankowski, Mark A.','Wiley','September,1998','7.37','303',' Business & Economics , Marketing , General','English'),
('B0478','Bringing Out the Best in Your Child: 80 Ways to Focus on Every Kid Strengths','By Tobias, Cynthia Ulrich and Funk, Carol','Vine Books','February,1997','5.29','407','','English'),
('B0479','Dave Barry Stay Fit and Healthy Until You are Dead','By Barry, Dave','Rodale Books','May,2000','4.99','193',' Humor , Topic , Sports','English'),
('B0480','Language and Myth','By Cassirer, Ernst','Dover Publications','June,1953','14.62','935',' Philosophy , Religious','English'),
('B0481','Ripken: Cal on Cal','By Ripken, Cal, Jr., Iooss, Walter (PHT), and Vancil, Mark (EDT)','Summit Publishing Group','January,1997','8.79','513',' Biography & Autobiography , General','English'),
('B0482','German in 32 Lessons (Gimmick Series)','By Adrienne','W W Norton & Co Inc','January,1979','5.29','71','','English'),
('B0483','24 Essential Lessons for Investment Success: Learn the Most Important Investment Techniques from the Founder of Investor is Business Daily','By O Neil, William J.','McGraw-Hill','December,1999','4.99','148',' Business & Economics , Personal Finance , Investing','English'),
('B0484','Many Waters','By L Engle, Madeleine','Laurel Leaf','August,1987','5.29','819','','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0485','The Last Debate:  A Novel of Politics and Journalism','By Lehrer, James, Smith, Peter (EDT), and Osnos, Peter','Random House','August,1995','4.99','163',' Fiction , Political','English'),
('B0486','Parents in Charge: Setting Healthy, Loving Boundaries for You and Your Child','By Chidekel, Dana','Simon & Schuster','January,2002','5.29','784',' Family & Relationships , Parenting , General','English'),
('B0487','Prep (Junior Library Guild Selection)','By Coburn, Jake','Dutton Juvenile','September,2003','5.29','798',' Young Adult Fiction , Social Themes , Friendship','English'),
('B0488','This Lullaby','By Dessen, Sarah','Speak','March,2004','4.99','19',' Young Adult Fiction , Social Themes , Friendship','English'),
('B0489','No mean city,: An inquiry into civic greatness,','By McKeldin, Theodore R','Author / Maryland Historical Society','January,1964','10.1','739','','English'),
('B0490','ROLLING THUNDER: Jet Combat From WW II to the Gulf War','By Rendall, Ivan','Free Press','May,1999','5.41','893',' Technology & Engineering , Military Science','English'),
('B0491','The Everything Cover Letter Book (Everything)','By Graber, Steven and Lipsman, Mark','Everything','April,2000','5.35','964',' Business & Economics , General','English'),
('B0492','Norman Mark is Chicago: Walking, bicycling & driving tours of the city','By Mark, Norman','Chicago Review Press','January,1987','8.85','685','','English'),
('B0493','Believing in Jesus','By Foley, Leonard','St. Anthony Messenger Press','January,1985','5.29','984','','English'),
('B0494','Irish Druids and Old Irish Religions','By Bonwick, James','Hippocrene Books','June,1986','4.99','686',' Religion , Ethnic & Tribal','English'),
('B0495','Eat Smart for a Healthy Heart Cookbook','By Cooley, Denton A., M.D. and Moore, Carolyn E.','Barron Educational Series, inc','March,1992','6.12','375',' Cooking , Health & Healing , General','English'),
('B0496','I Can Print A to Z (Wipe-off Activity Book--Reusable)','By Enterprises Incorporated Trend','Trend Enterprises Inc','January,1997','5.29','204','','English'),
('B0497','1-2-3 Murals: Simple Murals to Make from Children Open-Ended Art','By Warren, Jean','Warren Publishing House, Inc.','April,1989','5.29','227','','English'),
('B0498','A Book of Americans','By Benet, Rosemary','Farrar and Rinehart, inc','January,1933','15.4','687','','English'),
('B0499','The Pleasures of Watching Birds','By Oberman, Lola and Swan, Joy (ILT)','Walker & Co','June,1991','5.29','659',' Science , General','English'),
('B0500','Young Men and Fire','By MacLean, Norman','University of Chicago Press','January,1992','4.99','742',' Nature , Plants , Trees','English'),
('B0501','The Heritage of British Literature','By Bowen, Elizabeth','Thames & Hudson','August,1986','10.99','486','','English'),
('B0502','Creating Sanctuary: A New Approach to Gardening in the Washington Metropolitan Area','By Mitchell, Sherry','Epm Pubns Inc','December,1996','5.29','638',' Gardening , Landscape','English'),
('B0503','Spain (Culture Shock! A Survival Guide to Customs & Etiquette)','By Graff, Marie Louise','Graphic Arts Center Publishing Company','June,2001','7.29','327',' Travel , Europe , Spain & Portugal','English'),
('B0504','The Metropolitan Museum of Art: The Cloisters; the Building and the Collection of Medieval Art in Fort Tryon Park','By James Joseph Rorimer','The Metropolitan Museum of Art','January,1951','4.99','430','','English'),
('B0505','Second Treatise of Government','By John Locke','Prentice Hall','June,1952','5.29','532','','English'),
('B0506','Worldwide Wonders','By Harris, Tina','Education Insights','January,1986','5.29','895','','English'),
('B0507','Essential History of Art','By Editor','Dempsey','January,2000','8.79','966','','English'),
('B0508','Where to Find It in the Bible: The Ultimate A to Z Resource','By Ken Anderson','Thomas Nelson','April,1996','8.79','545','','English'),
('B0509','The German Panzers, from Mark I to Mark V "Panther" - Armor Series 2','By Feist, Uwe','Aero Publishers','January,1966','26.29','469','','English'),
('B0510','What Makes a Goya a Goya?','By Muhlberger, Richard','Viking Juvenile','December,1994','4.99','893',' Young Adult Nonfiction , Art , General','English'),
('B0511','How to Be Your Dog is Best Friend: A Training Manual for Dog Owners','By Monks, New Skete','Little Brown','October,1978','5.29','137','','English'),
('B0512','Let Freedom Ring: Winning the War of Liberty over Liberalism','By Hannity, Sean','William Morrow','August,2002','4.99','633',' Political Science , Terrorism','English'),
('B0513','When All Hell Breaks Loose','By Spencer, Camika','St. Martin Griffin','October,2000','5.29','781',' Fiction , Humorous , General','English'),
('B0514','Gorgias (The Penguin Classics, L94)','By Plato and Hamilton, Walter','Viking Pr','June,1971','5.29','595',' Philosophy , General','English'),
('B0515','Quiet Times for Couples','By Wright, H. Norman','Harvest House Publishers','July,1997','5.41','331',' Religion , Devotional','English'),
('B0516','Herds of Thunder, Manes of Gold','By Coville, Bruce','Doubleday Books for Young Readers','May,1991','5.29','868','','English'),
('B0517','Transitions: Making Sense of Life is Changes','By Bridges, William','Da Capo Lifelong Books','January,1980','4.99','101',' Psychology , Developmental , Lifespan Development','English'),
('B0518','Beginner is Guide to Golf','By Lumb, Nick','Gallery Books','August,1989','10.99','78',' Sports & Recreation , Golf','English'),
('B0519','I am the fox,','By Winifred Van Etten','Little, Brown and Company','January,1936','146.13','990','','English'),
('B0520','Secretaries of war and secretaries of the army: Portraits & biographical sketches (CMH pub)','By Bell, William Gardner','For sale by the U.S. G.P.O., Supt. of Docs','January,1992','4.99','191','','English'),
('B0521','A Contest of Faiths: Missionary Women and Pluralism in the America Southwest','By Yohn, Susan M.','Cornell University Press','May,1995','4.99','189',' Religion , General','English'),
('B0522','The Single Parent is Money Guide','By Card, Emily and Kelly, Christie Watts','Macmillan General Reference','October,1996','4.99','266',' Business & Economics , Personal Finance , General','English'),
('B0523','F2f','By Finch, Phillip','Bantam','March,1996','4.99','900',' Fiction , General','English'),

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0524','Freud is Wishful Dream Book','By Welsh, Alexander','Princeton University Press','September,1994','18','334',' Literary Collections , American , General','English'),
('B0525','Ingenious Inventions of Domestic Utility','By Bragdon, Allen D.','HarperCollins','December,1989','5.29','680','','English'),
('B0526','Complete Idiot is Guide to Parenting Your Teenager (The Complete Idiot is Guide)','By Kelly, Kate','Alpha','September,1996','5.29','226',' Social Science , Sociology , Marriage & Family','English'),
('B0527','The Antiques Care & Repair Handbook','By Jackson, Albert and Day, David','Knopf','May,1984','4.99','243',' Antiques & Collectibles , Care & Restoration','English'),
('B0528','Great Pyramid: Its Secrets & Mysteries Revealed','By Smyth, Charles Piazzi','Bell Publishing Co.','October,1978','6.36','837',' History , World','English'),
('B0529','Holy War Inc. Inside the Secret World of Osama Bin Laden','By Bergen, Peter L.','Free Press','November,2001','4.99','418',' Political Science , International Relations , General','English'),
('B0530','Flavor','By DiSpirito, Rocco, Leutwyler, Henry, and Sherer, Kris','Hyperion','November,2003','7.45','287',' Cooking , Courses & Dishes , General','English'),
('B0531','Cinderella Cinderella Mini Book: Mini version (Fay is Fairy Tales)','By Wegman, William, Kismaric, Carole, and Heiferman, Marvin','Hyperion Books For Children','October,1999','5.29','593',' Juvenile Fiction , Fairy Tales & Folklore , General','English'),
('B0532','After the Boxes Are Unpacked','By Miller, Susan','Focus on the Family','April,1998','4.99','930',' Religion , Christian Living , Women is Interests','English'),
('B0533','The Big Book of Gardening Skills','By Gardenway Book (EDT)','Garden Way Pub Co','February,1993','10.99','197',' Gardening , Reference','English'),
('B0534','Eakins','By Schendler, Sylvan','Little Brown','January,1967','6.83','550','','English'),
('B0535','Race relations (Prentice-Hall sociology series)','By Kitano, Harry H. L.','Prentice Hall','January,1974','4.99','567','','English'),
('B0536','The Washington ethnic food store guide','By Lawson, Jim C','Ardmore Publications','January,1992','12.99','880','','English')
INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0538','Getting Started in Powerboating','By Armstrong, Bob','International Marine/Ragged Mountain Press','May,2005','5.29','399',' Sports & Recreation , Boating','English'),
('B0539','Worth the Fighting For: A Memoir','By McCain, John and Salter, Mark','Random House','September,2002','4.99','562',' Biography & Autobiography , Political','English'),
('B0541','All the Way Home','By Giff, Patricia Reilly','Delacorte Books for Young Readers','October,2001','5.29','689',' Juvenile Fiction , Family , General','English'),
('B0542','Totline 123 Rhymes Stories & Songs ~ Open-Ended Language (1-2-3 Series) Ages 3-6','By Warren, Jean','Warren Publishing House, Inc.','March,1992','5.29','707','','English'),
('B0544','The Book of Ch I: Harnessing the Healing Forces of Energy','By Wildish, Paul','Tuttle Pub','October,2000','13.98','629',' Religion , Spirituality','English'),
('B0545','Being Little in Cambridge When Everyone Else Was Big','By Abbott, Eleanor Hallowell','D. Appleton-Century company, incorporated','January,1936','29.29','880','','English'),
('B0546','The Hero Within','By Pearson, Carol S.','HarperOne','May,1989','5.29','233','','English'),
('B0547','Western','By Yerby, Frank','Doubleday','August,1982','16.16','850',' Fiction , General','English'),
('B0548','Numbers (Wipe-Off Activity Books)','By','Learning Horizons','May,2002','5.29','865','','English'),
('B0549','Baroque Painting (Barron is Art Handbooks: Yellow Series)','By Parramon is Editorial Team (COR)','Barron is Educational Series, inc','August,1998','5.29','703',' Art , History , General','English'),
('B0551','Pierre Franey is Cooking In America','By Franey, Pierre, Jarrett, Lauren (ILT), and Flaste, Richard','Knopf','September,1994','5.29','668',' Cooking , Regional & Ethnic , American , General','English'),
('B0552','Greek Art (New Revised Edition)','By Boardman, John','Thames & Hudson','May,1985','5.29','313','','English'),
('B0553','Utopia (Crofts Classics)','By More, Thomas, Sir, Saint and Ogden, H. V. S. (EDT)','Appleton-Century-Crofts','January,1991','5.29','223',' History , United States , General','English'),
('B0554','Bias: A CBS Insider Exposes How the Media Distort the News','By Goldberg, Bernard','Regnery Publishing, Inc.','February,2001','4.99','611',' Performing Arts , Business Aspects','English'),
('B0555','Plain and Simple: A Woman is Journey to the Amish','By Bender, Sue','HarperOne','October,1989','5.29','140',' Religion , Christianity , Amish','English'),
('B0556','Twentieth-century art from the Nelson Aldrich Rockefeller collection','By LIEBERMAN, Nelson A','Museum of Modern Art, NY','January,1969','49.97','410','','English'),
('B0557','7 Simple Steps to Unclutter Your Life','By Smallin, Donna','Storey Books','January,2000','5.29','49',' Self-help , Self-Management , Stress Management','English'),
('B0558','Virginia trout streams: A guide to fishing the Blue Ridge watershed','By Slone, Harry','Backcountry Publications','January,1991','5.29','623','','English'),
('B0559','How to Fool Fish With Feathers: The Incompleat Guide to Fly-Fishing','By MacNelly, Jeff and Margolis, Jon','Simon & Schuster','February,1992','5.29','715',' Humor , General','English'),
('B0560','Tom Brown Schooldays (Oxford World is Classics)','By Sanders, Andrew, Hughes, Arthur, and Hughes, Thomas','Oxford University Press','August,1999','4.99','670',' Juvenile Fiction , Classics','English'),
('B0561','Metamorphoses (Oxford World is Classics)','By Ovid, Melville, A. D. (TRN), and Kenney, E. J. (INT)','Oxford University Press','July,1998','4.99','916',' Poetry , European , General','English'),
('B0562','Germinal (Oxford World is Classics)','By Zola, Emile, Collier, Peter (TRN), and Lethbridge, Robert (TRN)','Oxford University Press','August,1998','5.29','759',' Fiction , Classics','English'),
('B0563','The California Coastal Resource Guide','By California Coastal Commission, Caughman, Madge, and Ginsberg, Joanne S.','University of California Press','November,1987','10.99','724',' Nature , Reference','English'),
('B0564','Tools and Gadgets (Historic Communities (Paperback))','By Kalman, Bobbie','Crabtree Classics','October,1992','5.29','200',' Juvenile Nonfiction , Lifestyles , Farm & Ranch Life','English'),
('B0565','Matilda','By Gallico, Paul','Coward-McCann Inc.','January,1970','16.76','154','','English'),
('B0567','Where No One Has Gone Before: A History in Pictures (Star Trek)','By Dillard, J. M. and Dillar, J. M.','Pocket Books','November,1994','8.29','702',' Fiction , Science Fiction , General','English'),
('B0568','Superdog: Raising the Perfect Canine Companion','By Fox, Michael W.','Howell Book House','July,1996','5.29','531',' Pets , Dogs , General','English'),
('B0569','Football America: Celebrating Our National Passion','By Barber, Phil and Didinger, Ray','Turner Pub','October,1996','7.62','343',' Sports & Recreation , Football','English'),
('B0570','In Our Own Image: Building an Artificial Person','By Caudill, Maureen','Oxford University Press','October,1992','4.99','350','','English'),
('B0571','Death and Deconstruction','By Fleming, Anne','St Martins Pr','October,1995','4.99','589',' Fiction , General','English'),
('B0572','1990 BEST-RECIPES YEARBOOK','By "Better Homes and Gardens"','Meredith Publishing Co.','January,1990','5.76','984','','English'),
('B0573','People of the High Plateau','By Berman, Carl','Howell Press@inc','September,1988','10.99','529','','English'),
('B0574','The Education of the Heart: Readings and Sources for Care of the Soul, Soul Mates, and The Re-Enchantment of Everyday Life','By Moore, Thomas (EDT)','Harper Perennial','October,1997','5.29','675',' Religion , General','English'),
('B0575','Legends: Conversations With Baseball Greats','By Rust, Art and Marley, Michael','McGraw-Hill','August,1989','6.23','160',' Biography & Autobiography , General','English'),
('B0576','Out to Canaan (Book 4 of the Mitford Years)','By Karon, Jan','Penguin Books','January,1998','4.99','793',' Fiction , Religious','English'),
('B0577','Management and Organizational Behavior','By Hunsaker, Phillip L., Cook, Curtis W., and Coffey, Robert E.','Richard d Irwin','December,1996','7.27','153',' Business & Economics , Management','English'),
('B0578','What Every Woman Needs to Know about Menopause: The Years Before, During, and After','By Minkin, Mary Jane, M.D. and Wright, Carol V.','Yale University Press','September,1997','10.99','571',' Medical , General','English'),
('B0579','Garfield Book of Cat Names','By Davis, Jim and Wallace, Carol','Ballantine Books','June,1988','5.29','884',' Humor , Topic , Animals','English'),
('B0580','Introductory clinical pharmacology','By Jeanne C Scherer','Lippincott','January,1975','5.37','154','','English'),
('B0581','Decorating with Wreaths, Garlands, Topiaries & Bouquets','By Mays, Steven (PHT) and Sterbenz, Carol Endler','Gramercy','August,1995','4.99','70','','English'),
('B0582','Prince Charles','By Brown, Michele','Crown','August,1980','5.29','438','','English'),
('B0583','Laura Ashley Color: Using Color to Decorate Your Home','By Berry, Susan, Laura Ashley (Firm) (COR), Ashley, Laura, and Kuscenko, Valerie (EDT)','Harmony','November,1995','7.13','559','','English'),
('B0584','Shark Tank: Greed, Politics, and the Collapse of Finley Kumble, One of America is Largest Law Firms','By Eisler, Kim Isaac','St Martins Pr','March,1990','5.29','374',' Law , Legal Profession','English'),
('B0585','Antiques as an Investment','By Rush, Richard H.','Bonanza Books','January,1968','8.79','565','','English'),
('B0586','The Real War on Crime: Report of the National Criminal Justice Commission, The','By National Criminal Justice Commission (U. S.) (COR) and Donziger, Steven A. (EDT)','Harper Perennial','March,1996','5.29','457',' Political Science , General','English'),
('B0587','Ernie And His Merry Monsters (Sesame Street Good-Night Stories)','By Michaela Muntean','Golden Book','January,1997','5.29','406','','English'),
('B0588','The Quarterlifer is Companion : How to Get on the Right Career Path, Control Your Finances, and Find the Support Network You Need to Thrive','By Wilner, Abby and Stocker, Catherine','McGraw-Hill','July,2005','5.29','388',' Self-help , General','English'),
('B0589','Leaving Town Alive: Confessions of an Arts Warrior','By Frohnmayer, John','Houghton Mifflin Harcourt','April,1993','4.99','493',' Political Science , General','English'),
('B0590','A Show of Hands: Say It in Sign Language','By Sullivan, Mary Beth and Bourke, Linda','Trophy Pr','May,1985','5.29','357',' Juvenile Nonfiction , General','English'),
('B0591','Silent Sons: A Book for and About Men','By Ackerman, Robert J.','Touchstone','October,1994','4.99','986',' Psychology , General','English'),
('B0592','Walker Evans: A Biography','By Evans, Walker and Rathbone, Belinda','Mariner Books','April,2000','5.29','109',' Biography & Autobiography , Artists, Architects, Photographers','English')

INSERT INTO Book (bookId, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0593','Meteorology today: An introduction to weather, climate, and the environment','By Ahrens, C. Donald','West Pub. Co','January,1988','7.35','713','','English'),
('B0595','Americas Glorious Quilts','By Duke, Dennis','Hugh Lauter Levin Associates','January,1997','10.56','185','','English'),
('B0596','Cars 1886-1930','By Georgano, G. N.','Beekman House','January,1985','7.67','611',' Science , General','English'),
('B0597','Dr Jekyll and Mr Hyde and Weir of Hermiston (Oxford World is Classics)','By Stevenson, Robert Louis and Letley, Emma','Oxford University Press','July,1998','5.29','554',' Young Adult Fiction , Horror','English'),
('B0598','Spirited Americans: A Commentary on America is Optimists-From the Puritans to the Cybr-Century','By Jeffcoat, A. E.','Winslow House Books','August,2000','4.99','943',' History , United States , General','English'),
('B0599','What Went Wrong?: Western Impact and Middle Eastern Response','By Lewis, Bernard','Oxford University Press','December,2001','8.79','110',' History , Middle East , General','English')

SELECT * FROM Book



INSERT INTO Customer (Cid, Cname, Cssn, CbirthDate, Cgender, CphoneNumber, Cemail, Caddress, CtotalPayment, AccountId) VALUES
('C001', 'Nguyen Van An', '123456789', '1995-05-20', 'Male', '0905123456', 'nguyenvana@example.com', 'Ha Noi', 500000, 9),
('C002', 'Tran Thi Bình', '987654321', '1998-09-15', 'Female', '0912987654', 'tranthib@example.com', 'TP Ho Chi Minh', 200000, 10)


INSERT INTO CustomerBorrow (cardId, Cid, typeCard, cardExpiry, registrationDate, cardValue, borrowLimit) VALUES
('CB001', 'C001', 'Member', '2024-04-01', '2024-03-01', 50000, 10),
('CB002', 'C002', 'Vip', '2024-04-05', '2024-03-05', 100000, 15)


INSERT INTO CustomerBuy (Cid, totalPurchase, membershipLevel) VALUES
('C001', 120000, 'Member'),
('C002', 600000, 'Vip')

INSERT INTO Employee (Eid, Ename, Essn, EbirthDate, Egender, EphoneNumber, EDemail, Eaddress, Eposition, Esalary, EstartDate, AccountId) 
VALUES 
('E001', 'nam bau troi', '123456789', '1990-05-15', N'Nam', '0901234567', 'nva@example.com', 'Hà Nội', N'forever fan bac meo', 2000.0, '2020-06-01', 6),
('E002', 'la dititi', '987654321', '1995-08-22', N'Nam', '0912345678', 'ttb@example.com', 'TP Hồ Chí Minh', N'lam cho vui', 1500.0, '2021-03-10', 7),
('E003', 'hoang luu', '112233445', '1988-02-10', N'Nam', '0923456789', 'lvc@example.com', 'Đà Nẵng', N'Kế toán', 1800.0, '2019-11-20', 8)
--('E004', 'thang lo dai', '556677889', '1992-11-05', N'Nam', '0934567890', 'ptd@example.com', 'Cần Thơ', N'Nhân sự', 1700.0, '2022-07-15', 4),
--('E005', 'tun dau moi', '667788990', '1985-07-30', N'Nam', '0945678901', 'hme@example.com', 'Hải Phòng', N'Bán hàng', 1600.0, '2018-09-25', 5);



select * from Book
--delete from CustomerBuy
--delete from CustomerBorrow
select * from CustomerBuy
SELECT a.AccountId,a.userName , a.Apass FROM Account a join customer cus on cus.AccountID = a.AccountId
select * from Customer
select * from admin
select * from Account
select * from Employee
--delete from Customer
--delete from Account
--delete from Book
--drop table Book