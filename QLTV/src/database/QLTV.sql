create DataBase QLTV
use QLTV

CREATE TABLE Account (
	AccountId INT PRIMARY KEY,
	username NVARCHAR(50) UNIQUE NOT NULL,
	APass VARCHAR(255) NOT NULL
);


CREATE TABLE Admin (
    ADid VARCHAR(50) PRIMARY KEY,
	Aname NVARCHAR(50),
	Assn VARCHAR(50),
	ADbirthDate DATE,
    ADgender NVARCHAR(10),
	ADphoneNumber NVARCHAR(10),
	ADemail NVARCHAR(50),
    ADaddress NVARCHAR(255),
    AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);


CREATE TABLE Employee (
    Eid VARCHAR(50) PRIMARY KEY,
	Ename NVARCHAR(50),
	Essn VARCHAR(50),
    EbirthDate DATE,
    Egender NVARCHAR(10),
	EphoneNumber NVARCHAR(10),
	EDemail NVARCHAR(50),
    Eaddress NVARCHAR(255),
    Eposition NVARCHAR(50),
    Esalary FLOAT,
    EstartDate DATE ,
	AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);


CREATE TABLE Customer (
    Cid VARCHAR(50) PRIMARY KEY,
	Cname NVARCHAR(50),
	Cssn VARCHAR(50),
	CbirthDate DATE,
    Cgender NVARCHAR(10),
	CphoneNumber NVARCHAR(10),
	Cemail NVARCHAR(50),
    Caddress NVARCHAR(255),
    CtotalPayment FLOAT DEFAULT 0,
	AccountId INT,
	FOREIGN KEY (AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE
);


CREATE TABLE CustomerBuy (
    Cid VARCHAR(50) PRIMARY KEY,
    totalPurchase FLOAT DEFAULT 0,
    membershipLevel NVARCHAR(50),
	FOREIGN KEY (Cid) REFERENCES Customer(Cid) ON DELETE CASCADE
);


CREATE TABLE CustomerBorrow (
    cardId VARCHAR(50) PRIMARY KEY,
    Cid VARCHAR(50) NOT NULL,
    typeCard NVARCHAR(50),
    cardExpiry DATE, 
    registrationDate DATE,
	cardValue FLOAT DEFAULT 0,
    borrowLimit INT CHECK (borrowLimit >= 0),
    FOREIGN KEY (Cid) REFERENCES Customer(Cid) ON DELETE CASCADE
);


CREATE TABLE Book (
    bookId VARCHAR(50) PRIMARY KEY,
	isbn VARCHAR(50),
    title NVARCHAR(200),
    author NVARCHAR(100),
    publisher NVARCHAR(100),
    publishedDate VARCHAR(100),
    price FLOAT CHECK (price >= 0),
    quantity INT CHECK (quantity >= 0),
    type NVARCHAR(200), 
    language NVARCHAR(20) 
); 


CREATE TABLE BorrowBook (
    borrowId INT PRIMARY KEY IDENTITY,
    cardId VARCHAR(50),
    bookId VARCHAR(50),
    borrowDate DATE,
    endDate DATE,
	FOREIGN KEY (cardId) REFERENCES CustomerBorrow(cardId) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE
);


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


CREATE TABLE Report (
    reportId INT PRIMARY KEY IDENTITY,
    customerId VARCHAR(50),
    bookId VARCHAR(50),
    reportDate DATE DEFAULT GETDATE(),
    content NVARCHAR(MAX),
    FOREIGN KEY (customerId) REFERENCES Customer(Cid) ON DELETE CASCADE,
    FOREIGN KEY (bookId) REFERENCES Book(bookId) ON DELETE CASCADE

);


CREATE TABLE Voucher (
    discountRate INT NOT NULL, 
    Vdescription NVARCHAR(255) NOT NULL,  
    minPurchase DECIMAL(10,2) NOT NULL,
);


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


INSERT INTO Account (AccountId, username, APass) VALUES
(1, 'nhatnam', 'nhatnam123'),
(2, 'thanhtung', 'dititi1'),
(3, 'hoangluu', 'hoangluu217'),
(4, 'vantuan', 'tuan123'),
(5, 'manhthang','manhthang1'),
(6, 'thimay','may123'),
(7, 'dung', 'dung2301'),
(8, 'namne','nam0608'),
(9, 'anvan','an8386'),
(10, 'binh','binh123'),
(11, 'thang','thang2004'),
(12, 'thihuyen','huyen0923'),
(13, 'vanquyet','vanquyet1')



INSERT INTO Voucher (discountRate, Vdescription, minPurchase)
VALUES 
    (10, N'Giảm 10% khi mua từ $5', 9.99),
    (20, N'Giảm 20% khi mua từ $20', 19.99),
    (50, N'Giảm 50% khi mua từ $50', 49.99)


INSERT INTO Admin (ADid, Aname, Assn, ADbirthDate, ADgender, ADphoneNumber, ADemail, ADaddress, AccountId)
VALUES 
('AD001', N'Phan Nhật Nam', '123456789', '2004-05-15', 'Nam', '0987654321', 'namphan@gmail.com', N'Hà Nội', 1),
('AD002', N'Đặng Thanh Tùng', '987654321', '2004-07-20', 'Nam', '0912345678', 'tungdang@gmail.com', 'TP.HCM', 2),
('AD003', N'Lương Đặng Hoàng Lưu', '456789123', '2004-09-10', 'Nam', '0945325675', 'luuhoang@gmail.com', N'Đà Nẵng', 3),
('AD004', N'Văn Tuấn', '456324128', '2004-04-13', 'Nam', '0935322675', 'vantuan@gmail.com', N'Đà Nẵng', 4),
('AD005', N'Phan Mạnh Thắng', '987543521', '2004-08-05', 'Nam', '0935618324', 'manhthang@gmail.com', N'Đà Nẵng', 5)


INSERT INTO Book (bookId, isbn, title, author, publisher, publishedDate, price, quantity, type, language) VALUES
('B0001', '978-2667025379', 'Goat Brothers','Colton, Larry','Doubleday','January,1993',8.79,223,' History , General','English'),
('B0002', '978-9447032982', 'The Missing Person','Grumbach, Doris','Putnam Pub Group','March,1981','4.99','373',' Fiction , General','English'),
('B0003', '978-7634912782', 'Dont Eat Your Heart Out Cookbook','Piscatella, Joseph C.','Workman Pub Co','April,1983','4.99','427',' Cooking , Reference','English'),
('B0004', '978-1561930452', 'When Your Corporate Umbrella Begins to Leak: A Handbook for White Collar Re-Employment','Davis, Paul D.','Natl Pr Books','May,1991','4.99','608','','English'),
('B0005', '978-3678921722', 'Amy Spangler Breastfeeding : A Parent Guide','Spangler, Amy','Amy Spangler','June,1997','5.32','866','','English'),
('B0006', '978-1019882227', 'The Foundation of Leadership: Enduring Principles to Govern Our Lives','Short, Bo','Excalibur Press','July,1997','6.06','966','','English'),
('B0007', '978-8307464289', 'Chicken Soup for the Soul: 101 Stories to Open the Heart and Rekindle the Spirit','Canfield, Jack (COM) and Hansen, Mark Victor (COM)','Health Communications Inc','August,1993','4.99','877',' Self-help , Personal Growth , Self-Esteem','English'),
('B0008', '978-0263602812', 'Journey Through Heartsongs','Stepanek, Mattie J. T.','VSP Books','September,2001','19.96','913',' Poetry , General','English'),
('B0009', '978-9574492830', 'In Search of Melancholy Baby','Aksyonov, Vassily, Heim, Michael Henry, and Bouis, Antonina W.','Random House','October,1987','4.99','826',' Biography & Autobiography , General','English'),
('B0010', '978-3511895063', 'Christmas Cookies','Eakin, Katherine M. and Deaman, Joane (EDT)','Oxmoor House','November,1986','12.98','552',' Cooking , General','English'),
('B0011', '978-5382586056', 'The Dieter Guide to Weight Loss During Sex','Smith, Richard','Workman Publishing Company','December,1978','4.99','768',' Health & Fitness , Diet & Nutrition , Diets','English'),
('B0012', '978-4730410596', 'Germs : Biological Weapons and America Secret War','Miller, Judith, Engelberg, Stephen, and Broad, William J.','Simon & Schuster','January,2001','4.99','581',' Technology & Engineering , Military Science','English'),
('B0013', '978-0207691200', 'The Genesis of Ethics','Visotzky, Burton L.','Crown','February,1996','4.99','598',' Religion , Ethics','English'),
('B0014', '978-0707770421', 'The Good Book: Reading the Bible with Mind and Heart','Gomes, Peter J.','Harper Perennial','March,1998','5.29','813',' Religion , Biblical Biography , General','English'),
('B0015', '978-3402485481', 'All over but the Shoutin','Bragg, Rick','Vintage','April,1998','4.89','438',' Biography & Autobiography , Personal Memoirs','English'),
('B0016', '978-1095446610', 'Oilers and Sweepers and Other Stories','Dennison, George','Random House','May,1979','5','959','','English'),
('B0017', '978-7143110000', 'Prince William','Garner, Valerie','Benford Books','June,1998','4.99','46','','English'),
('B0018', '978-5197837171', 'The Emperor New Mind','Penrose, Roger','Penguin Books','July,1991','4.99','211',' Philosophy , General','English'),
('B0019', '978-8290527128', 'Touching Fire: Erotic Writings by Women','Thornton, Louise, Sturtevant, Jan, and Sumrall, Amber Coverdale (EDT)','Carroll & Graf Pub','August,1990','5.29','915','','English'),
('B0020', '978-7204273061', 'Hill Rat: Blowing the Lid Off Congress','Jackley, John L.','Regnery Publishing, Inc.','September,1992','4.99','701',' Political Science , General','English'),
('B0021', '978-1618519540', 'The Great ABC Treasure Hunt: A Hidden Picture Alphabet Book (Time-Life Early Learning Program)','Time-Life for Children (Firm) (COR), Singer, Muff, and Hoggan, Pat (ILT)','Time Life Education','October,1991','5.29','400',' Juvenile Nonfiction , General','English'),
('B0022', '978-2485404664', 'Personality of the Cat','Aymar, Brandt (EDT)','Bonanza Books','November,1978','5.41','283',' Pets , Cats , General','English'),
('B0023', '978-9066319552', 'Murdering Mr. Monti: A Merry Little Tale of Sex and Violence','Viorst, Judith','Simon & Schuster','December,1994','5.29','283',' Fiction , General','English'),
('B0024', '978-8230393152', 'In Re Alger Hiss:  Petition for a Writ of Error Coram Nobis','Edith Tiger','Hill & Wang','January,1979','10.99','942','','English'),
('B0025', '978-0458794139', 'Black Holes and Baby Universes and Other Essays','Hawking, Stephen W.','Bantam','February,1993','5.29','624',' Biography & Autobiography , General','English'),
('B0026', '978-7394890680', 'Relativity: The Special and the General Theory','Albert Einstein','Three Rivers Press','March,1961','8.79','794','','English'),
('B0027', '978-6235519154', 'Betrayal : How the Clinton Administration Undermined American Security','Gertz, Bill','Regnery Pub','April,1999','4.99','972',' Political Science , General','English'),
('B0028', '978-1350036888', 'Shadow Song','Kay, Terry','Atria Books','May,1994','4.99','272',' Fiction , General','English'),
('B0029', '978-6954558020', 'Undercurrents: A Therapist Reckoning With Her Own Depression','Manning, Martha','HarperCollins','June,1995','5.29','943',' Psychology , General','English'),
('B0031', '978-0359400969', 'The Kiss: A Memoir','Harrison, Kathryn','Random House','August,1997','5.29','70',' Family & Relationships , Love & Romance','English'),
('B0032', '978-2854013151', 'Codebreakers Victory: How the Allied Cryptogaphers Won World War II','Haufler, Hervie','NAL Trade','September,2003','5.29','509',' History , Military , World War II','English'),
('B0033', '978-7189483985', 'A Manual for Writers of Term Papers, Theses, and Dissertations, Fifth Edition','Turabian, Kate L.','University of Chicago Press','October,1987','4.99','425','','English'),
('B0034', '978-9858681685', 'The Price of Loyalty: George W. Bush, the White House, and the Education of Paul O Neill','Suskind, Ron','Simon & Schuster','November,2004','8.79','181',' Political Science , Government , Executive Branch','English'),
('B0035', '978-1215315766', 'Best New American Voices 2003','Kulka, John (EDT), Danford, Natalie (EDT), and Oates, Joyce Carol (EDT)','Harvest Books','December,2002','5.29','89',' Fiction , Anthologies (multiple authors)','English'),
('B0036', '978-9035847951', 'Escape from the CIA: How the CIA Won and Lost the Most Important KGB Spy Ever to Defect to the U.S.','Kessler, Ronald','Pocket Books','January,1991','5.29','682',' Political Science , General','English'),
('B0037', '978-8006165187', 'Meditations: On the Monk Who Dwells in Daily Life','Moore, Thomas','HarperCollins','February,1994','4.99','242',' Religion , General','English'),
('B0038', '978-9695478990', 'Links Lore','Stevens, Peter F.','POTOMAC BOOKS','March,2000','5.29','549',' Sports & Recreation , History','English'),
('B0039', '978-2299651972', 'Jackie by Josie: A Novel','Preston, Caroline','Scribner','April,1998','4.99','127',' Fiction , Literary','English'),
('B0040', '978-6507353883', 'Joshua and the City','Girzone, Joseph F.','Doubleday','May,1995','5.29','934',' Religion , Inspirational','English'),
('B0041', '978-6565723256', 'The Book of Courtly Love: The Passionate Code of the Troubadours','Hopkins, Andrea','HarperCollins','June,1994','5.29','723','','English'),
('B0042', '978-0438470156', 'How Good Do We Have to Be? A New Understanding of Guilt and Forgiveness','Kushner, Harold S.','Back Bay Books','July,1997','4.99','787',' Religion , Psychology of Religion','English'),
('B0044', '978-5445326262', 'Majorca: Culture and Life','Konemann Inc. (EDT)','Konemann','September,2000','8.83','600',' Photography , Subjects & Themes , Regional','English'),
('B0045', '978-2859224799', 'Written by Herself: Autobiographies of American Women: An Anthology','Conway, Jill Ker (EDT)','Vintage','October,1992','4.99','651',' Biography & Autobiography , Women','English'),
('B0046', '978-5198500158', 'The Universe of Galaxies','Hodge, Paul','W H Freeman & Co','November,1984','5.29','635','','English'),
('B0047', '978-3554958041', 'Ice Bound: A Doctor Incredible Battle For Survival at the South Pole','Nielsen, Jerri and Vollers, Maryanne','Miramax','December,2002','4.89','51',' Biography & Autobiography , Women','English'),
('B0049', '978-2445831987', 'Me and Ted Against the World : The Unauthorized Story of the Founding of CNN','Schonfeld, Reese','Harperbusiness','February,2001','4.99','997',' Business & Economics , Management','English'),
('B0050', '978-6065797872', 'Magnet Therapy: The Pain Cure Alternative','Lawrence, Ronald Melvin, Plowden, Judith, and Rosch, Paul','Prima Pub','March,1998','5.29','647',' Medical , Holistic Medicine','English'),
('B0052', '978-8402392244', 'George Meany And His Times: A Biography','Robinson, Archie','Simon & Schuster','May,1982','4.99','169',' Biography & Autobiography , General','English'),
('B0053', '978-6564634869', 'American Dreams: Lost & Found','Terkel, Studs','Pantheon','June,1980','10.99','432','','English'),
('B0054', '978-9693655969', 'Sharing the Pie : A Citizen Guide to Wealth and Power','Brodner, Steve (ILT) and Brouwer, Steve','Holt Paperbacks','July,1998','5.29','382',' Business & Economics , Economic Conditions','English'),
('B0055', '978-5331141082', 'Love, Love, and Love','Bernhard, Sandra','HarperCollins','August,1993','5.29','807','','English'),
('B0056', '978-3869739521', 'Conflicting Accounts: The Creation and Crash of the Saatchi and Saatchi Advertising Empire','Goldman, Kevin','Touchstone','September,1998','5.29','177',' Business & Economics , Advertising & Promotion','English'),
('B0057', '978-6952051085', 'What Went Wrong at Enron: Everyone Guide to the Largest Bankruptcy in U.S. History','Fusaro, Peter C. and Miller, Ross M.','Wiley','October,2002','5.29','528',' Business & Economics , Finance , General','English'),
('B0059', '978-6271846214', 'From the Silent Earth: A Report on the Greek Bronze Age','Joseph Alsop','Harper & Row','December,1964','6.22','300','','English'),
('B0060', '978-4117568076', 'Panic Disorder and Its Treatment (Medical Psychiatry Series)','Pollack, Mark H. (EDT) and Rosenbaum, J. F. (EDT)','Informa Healthcare','January,1998','5.41','961',' Psychology , Psychopathology , General','English'),
('B0061', '978-6892176861', 'Henry VIII (English Monarchs Series)','Scarisbrick, J. J.','University of California Press','February,1972','5.47','967',' Biography & Autobiography , Military','English'),
('B0062', '978-4020031336', 'The Moral Intelligence of Children','Coles, Robert','Random House','March,1997','5.29','798',' Psychology , General','English'),
('B0063', '978-8814291193', 'Concordance to the New English Bible, New Testament,','Elder, E','Zondervan Pub. House','April,1965','10.99','726','','English'),
('B0064', '978-4141177803', 'Life at the Edge: Readings from Scientific American Magazine','Gould, James L. and Gould, Carol Grant (EDT)','W H Freeman & Co','May,1989','4.99','527','','English'),
('B0065', '978-4937527595', 'Cook Healthy: Cook Quick','Wesler, Cathy A.','UNKNO','January,1995','5.18','733',' Cooking , Health & Healing , Low Fat','English'),
('B0066', '978-2025150271', 'Images Of War: The Artist Vision of World War II','McCormick, Ken','Orion Books','October,1990','10.02','492','','English'),
('B0067', '978-3572537927', 'Becoming Soul Mates: Cultivating Spiritual Intimacy in the Early Years of Marriage','Parrott, Les','Zondervan','October,1995','5.29','638',' Family & Relationships , Reference','English'),
('B0068', '978-2016159809', 'Care of the Soul : A Guide for Cultivating Depth and Sacredness in Everyday Life','Moore, Thomas','HarperPerennial','January,1994','4.99','514',' Self-help , General','English'),
('B0069', '978-4921900332', 'Victims of Progress','Bodley, John H.','Cummings Pub.Co.','September,1975','4.99','503','','English'),
('B0070', '978-7451608458', 'Inside the Tornado: Marketing Strategies from Silicon Valley Cutting Edge','Moore, Geoffrey A.','Harper Business','October,1995','5.29','82',' Business & Economics , Marketing , General','English'),
('B0071', '978-8037541382', 'Sunset at Rosalie: A Novel','McLaughlin, Ann L.','Daniel & Daniel Pub','June,1996','5','142',' Fiction , General','English'),
('B0072', '978-3612544287', 'The Girlfriends Guide to Surviving the First Year of Motherhood, Packaging May Vary','Iovine, Vicki','TarcherPerigee','October,1997','4.99','765',' Family & Relationships , Parenting , Motherhood','English'),
('B0073', '978-1208271828', 'Sherlock Holmes and the Red Demon by John H. Watson, M.D.','Watson, John H. and Millett, Larry (EDT)','Viking','September,1996','4.99','224',' Fiction , Mystery & Detective , General','English'),
('B0076', '978-1314258087', 'Proverbs For The People','Price-Thompson, Tracy (EDT) and Stovall, Taressa (EDT)','Kensington','June,2003','5.88','521',' Fiction , Anthologies (multiple authors)','English'),
('B0077', '978-6915886036', 'Great American Countryside','Landi, Val','Scribner','July,1982','10.99','676',' Travel , United States , General','English'),
('B0078', '978-3409331440', 'Rob Whitlock: A Pioneer Boy in Old Ohio','Jackson, Kathryn','Simon and Schuster','January,1951','12.51','481','','English'),
('B0079', '978-5788456455', 'British Hospitals (Britain in Pictures)','A. G. L. Ives','London : Collins','January,1948','18','569','','English'),
('B0080', '978-4053557045', 'An essay on morals','Wylie, Philip','Rinehart & Company, Inc','January,1947','4.99','589','','English'),
('B0081', '978-4340026538', 'Kid, You Sing My Songs of Love, and Loss, and Hope','Wyse, Lois and Rogers, Lilla (ILT)','Crown','February,1991','5.29','569',' Humor , Form , Limericks & Verse','English'),
('B0082', '978-2393412478', 'Re-Inventing the Corporation: Transforming Your Job and Your Company for the New Information Society','Naisbitt, John and Aburdene, Patricia','Little Brown & Co','September,1985','5.29','572',' Business & Economics , General','English'),
('B0084', '978-6105048777', 'The Practical Stylist','Baker, Sheridan Warner','Crowell','January,1973','4.99','805','','English'),
('B0085', '978-4922975731', 'The Periodic Kingdom: A Journey Into The Land Of The Chemical Elements (Science Masters Series)','Atkins, P. W.','Basic Books','July,1995','4.99','445',' Science , Applied Sciences','English'),
('B0086', '978-9064823963', 'Titanic','Kirkland, Douglas, Marsh, Ed W., and Kirkland, Douglas (PHT)','Harper Paperbacks','October,1997','8.79','72',' Performing Arts , Film , General','English'),
('B0088', '978-7367932952', 'Civilization III: Instruction Manual','Meier, Sid','Infogames Interactive','January,2001','4.99','641','','English'),
('B0089', '978-8868763285', 'Alcatraz 46;: The anatomy of a classic prison tragedy,','Don DeNevi, Philip Bergen','Leswing Press','January,1974','5.29','883','','English'),
('B0090', '978-4281321606', 'Elvis in the Morning','Buckley, William F.','Harvest Books','June,2002','5.29','467',' Fiction , Fantasy , Historical','English'),
('B0091', '978-4578258351', 'Fat Free, Flavor Full: Dr. Gabe Mirkin Guide to Losing Weight and Living Longer','Mirkin, Gabe and Rich, Diana','Little Brown & Co','January,1995','4.99','446',' Health & Fitness , Diet & Nutrition , Diets','English'),
('B0092', '978-4020362687', 'White Gold Wielder - Book Three of The Second Chronicles of Thomas Covenant','Donaldson, Stephen R.','Del Rey','March,1983','4.99','722',' Fiction , Fantasy , General','English'),
('B0093', '978-0763525601', 'From Image to Likeness: A Jungian Path in the Gospel Journey','Grant, W. Harold, Thompson, Magdala, and Clarke, Thomas E.','Paulist Press','January,1983','4.99','524',' Psychology , Movements , Jungian','English'),
('B0094', '978-3368598270', 'ON THE WING: The Life of Birds: From Feathers to Flight','Brooks, Bruce','Scribner','September,1989','19.25','183',' Science , General','English'),
('B0095', '978-8817653522', 'Bringing Down the House: The Inside Story of Six M.I.T. Students Who Took Vegas for Millions','Mezrich, Ben','Atria','September,2003','4.99','63',' Games & Activities , Gambling , Table','English'),
('B0096', '978-4580874989', 'Future Space: Beyond Earth','Quigley, Sebastian (ILT) and Jefferis, David','Tangerine Pr','June,2001','5.29','549','','English'),
('B0097', '978-7004514585', 'The young Jefferson, 1743-1789,','Bowers, Claude Gernade','Houghton Mifflin Company','January,1945','5.29','684','','English'),
('B0098', '978-2511418292', 'Mayo Clinic On Hearing: Strategies for Managing Hearing Loss, Dizziness and Other Ear Problems ("MAYO CLINIC ON" SERIES)','Olsen, Wayne, Ph.D. (EDT)','Kensington Pub Corp','October,2003','5.29','244',' Health & Fitness , Hearing & Speech','English'),
('B0099', '978-4563018158', 'Trust Me, Mom-Everyone Else Is Going!: The New Rules for Mothering Adolescent Girls','Cohen-Sandler, Roni, Ph.D.','Penguin Books','February,2003','4.99','116',' Family & Relationships , Parenting , General','English'),
('B0100', '978-3351234696', 'I Hate School: How to Hang in and When to Drop Out','Wirths, Claudine G. and Bowman-Kruhm, Mary','Trophy Pr','September,1986','5.29','808',' Young Adult Nonfiction , General','English'),
('B0101', '978-8450056958', 'The Quotable Ronald Reagan','Reagan, Ronald and Hannaford, Peter','Regnery Publishing, Inc.','January,1999','5.44','346',' Reference , Quotations','English'),
('B0102', '978-1007903351', 'Rooster crows for day,','Burman, Ben Lucien','E.P. Dutton & company, inc','January,1945','21.95','414','','English'),
('B0103', '978-8343089419', 'Leadership Is an Art','De Pree, Max','Dell','August,1990','4.99','425',' Business & Economics , General','English'),
('B0104', '978-5806710313', 'What is Going On?: Personal Essays','McCall, Nathan','Random House','September,1997','5.29','963',' Social Science , Ethnic Studies , African American Studies','English'),
('B0105', '978-2004294985', 'Dark Fields of the Republic: Poems 1991-1995','Rich, Adrienne Cecile','W. W. Norton & Company','September,1995','5.29','564',' Poetry , American , General','English'),
('B0106', '978-8815383905', 'Creating an Inclusive School','Villa, Richard A. (EDT) and Thousand, Jacqueline S. (EDT)','Assn for Supervision & Curriculum','November,1995','5.29','865',' Education , Special Education , General','English'),
('B0107', '978-6830280320', 'Edit Yourself : A manual for everyone who works with words','Ross-Larson, Bruce','Norton*(ww Norton Co','October,1985','5.29','966','','English'),
('B0108', '978-0539625539', 'Standing for Something: 10 Neglected Virtues That Will Heal Our Hearts and Homes','Hinckley, Gordon B. and Wallace, Mike (FRW)','Crown','February,2000','4.99','541',' Religion , Christianity , Church of Jesus Christ of Latter-day Saints (Mormon)','English'),
('B0109', '978-7374502469', 'North Carolina Ghosts and Legends','Roberts, Nancy','University of South Carolina Press','August,1991','5.29','388',' Social Science , Folklore & Mythology','English'),
('B0110', '978-8416228712', 'Balkan Odyssey a personal account of the international peace efforts following the breakup of the former Yugoslavia','Owen, David','Harcourt','March,1996','5.12','617','','English'),
('B0111', '978-2130110203', 'Straight Talk about Death for Teenagers: How to Cope with Losing Someone You Love','Grollman, Earl A.','Beacon Press','April,1993','8.79','98',' Young Adult Nonfiction , Social Topics , Death & Dying','English'),
('B0112', '978-3689029631', 'Understanding Thomas Jefferson','Halliday, E. M.','Harper Perennial','February,2002','4.99','308',' Biography & Autobiography , Presidents & Heads of State','English'),
('B0113', '978-6916952423', 'Hey Mom! I am Hungry!: Great-Tasting, Low-Fat, Easy Recipes to Feed Your Family','Powter, Susan','Atria Books','January,1997','5.29','450',' Cooking , Health & Healing , Low Fat','English'),
('B0114', '978-3334314168', 'Changes in the Land: Indians, Colonists and the Ecology of New England','Cronon, William','Hill and Wang','July,1983','4.99','880',' Science , Life Sciences , Ecology','English'),
('B0115', '978-5763997328', 'An East Wind Coming','Arthur Byron Cover','Berkley','November,1979','5.29','847','','English'),
('B0116', '978-4261834859', 'Going the Other Way: Lessons from a Life In and Out of Major League Baseball','Bean, Billy and Bull, Chris','Da Capo Press','March,2004','4.99','641',' Sports & Recreation , Baseball , Essays & Writings','English'),
('B0117', '978-4415315501', 'Consider Your Options: Get the Most from Your Equity Compensation','Thomas, Kaye A.','Fairmark Press','January,2000','5.29','591',' Business & Economics , Personal Finance , General','English'),
('B0118', '978-6495405002', 'Molecular Cell Biology','Lodish, Harvey F., Baltimore, David, and Berk, Arnold (CON)','W H Freeman & Co (Sd)','March,1995','11.44','756',' Science , Life Sciences , Biology','English'),
('B0119', '978-6431743930', 'Majendie Cat','Fowlkes, Frank V.','Harcourt','May,1986','5.29','913','','English'),
('B0120', '978-1398878603', 'First Things First','Covey, Stephen R., Merrill, A. Roger, and Merrill, Rebecca R.','Simon & Schuster','January,1994','5.58','352',' Psychology , General','English'),
('B0121', '978-9645006538', 'Best Friends: The True Story of the World is Most Beloved Animal Sanctuary','Glen, Samantha and Moore, Mary Tyler (INT)','Kensington Books','February,2001','4.89','664',' Nature , Animals , General','English'),
('B0122', '978-8100332211', 'Prudens futuri: The US Army War College, 1901-1967','Pappas, George S.','Alumni Association of the US Army War College','January,1967','6.24','869','','English'),
('B0123', '978-8748856518', 'Joshua: A Parable for Today','Girzone, Joseph F.','MacMillan Publishing Company.','September,1987','5.29','153','','English'),
('B0124', '978-1949127477', 'Things Not Seen and Other Stories','Williams, Lynna','Little Brown & Co','May,1992','5.29','740',' Fiction , General','English'),
('B0125', '978-6884563318', 'Putting the One Minute Manager to Work: How to Turn the 3 Secrets into Skills','Blanchard, Kenneth H. and Lorber, Robert','Jossey-Bass Inc Pub','February,1984','5.29','304','','English'),
('B0126', '978-0858402306', 'The Spiritual Life of Children','Coles, Robert','HarperOne','October,1991','4.99','640',' Psychology , Developmental , Child','English'),
('B0127', '978-4511475954', 'Constancia and Other Stories for Virgins','Fuentes, Carlos and Christensen, Thomas (TRN)','Farrar, Straus and Giroux','April,1990','5.29','223',' Fiction , Short Stories (single author)','English'),
('B0128', '978-3425207161', 'May I Have This Dance?','Rupp, Joyce and Veeder, Judith (ILT)','Ave Maria Pr','July,1992','4.89','451',' Religion , Devotional','English'),
('B0129', '978-7178814252', 'Meditations from Conversations with God: An Uncommon Dialogue, Book 1 (Conversations with God Series)','Walsch, Neale Donald','TarcherPerigee','September,1997','5.29','187',' Religion , Meditations','English'),
('B0130', '978-2978164569', 'Washington bowed','McKeldin, Theodore R','Maryland Historical Society','January,1956','16.16','791','','English'),
('B0131', '978-6439401958', 'Chagall','Compton, Susan P.','"Harry N. Abrams, Inc."','May,1985','10.99','883',' Art , General','English'),
('B0132', '978-3480801872', 'Ride a Pale Horse','MacInnes, Helen','Harcourt','October,1984','5.29','77',' Fiction , Mystery & Detective , General','English'),
('B0133', '978-0961953686', 'Listening to Prozac: A Psychiatrist Explores Antidepressant Drugs and the Remaking of the Self','Kramer, Peter D.','Viking Adult','June,1993','4.99','235',' Psychology , Psychopathology , Depression','English'),
('B0134', '978-3133525910', 'The Big Garage on Clear Shot: Growing Up, Growing Old, and Going Fishing at the End of the Road','Bodett, Tom','William Morrow & Co','September,1990','4.99','896',' Fiction , General','English'),
('B0135', '978-9710650723', 'Life Stories','Newbold, Heather (EDT)','University of California Press','April,2000','4.99','143',' Science , Environmental Science','English'),
('B0136', '978-1350036837', 'Houghton Mifflin Invitations to Literature: Student Anthology Level 1.3 Share 1997 (Invitations to Lit 1997)','Hm (COR)','Houghton Mifflin','October,1996','5.94','636',' Juvenile Nonfiction , Language Arts , General','English'),
('B0137', '978-8267361286', 'Grow Greener: Ten Steps to a Richer Life','Hoxton, Rob','Robinson Hoxton Publishing, LLC','November,2001','5.29','748','','English'),
('B0138', '978-4454032164', 'Frames of Reference: Looking at American Art, 1900-1950: Works from the Whitney Museum of American Art','Fraser, Kennedy, Whitney Museum of American Art (COR), Weinberg, Adam D., and Venn, Beth','University of California Press','January,2000','6.42','645',' Art , History , General','English'),
('B0139', '978-6041351162', 'Remembering Main Street: An American Album','Ross, Pat','Studio','November,1994','10.99','751',' History , United States , General','English'),
('B0141', '978-3173698447', N'Dường Đến Thành Công', N'Trần Minh Hoàng', N'NXB Lao Động', 'September,2018', 9.8, 10, N'Kiến thức', N'Tiếng Việt'),
('B0142', '978-6789063755', N'Hành Trình Vô Định', N'Lê Thanh Tú', N'NXB Kim Đồng', 'March,2017', 8.9, 8, N'Phiêu lưu', N'Tiếng Việt'),
('B0143', '978-5275968708', N'Bóng Đêm Vĩnh Cửu', N'Phạm Thị Lan', N'NXB Văn Học', 'November,2020', 12, 5, N'Kinh dị', N'Tiếng Việt'),
('B0144', '978-4634660091', N'Cuộc Sống Không Giới Hạn', N'Hoàng Anh Tuấn', N'NXB Trẻ', 'May,2016', 11.5, 6, N'Kiến thức', N'Tiếng Việt'),
('B0145', '978-4480248227', N'Bí Kíp Thành Công', N'Đỗ Thị Ngọc', N'NXB Thanh Niên', 'February,2019', 9.9, 11, N'Kỹ năng sống', N'Tiếng Việt'),
('B0146', '978-7297232171', N'Hẹn Nhau Ngày Ấy', N'Mai Xuân Hoa', N'NXB Kim Đồng', 'August,2022', 7.5, 9, N'Tình cảm', N'Tiếng Việt'),
('B0147', '978-4812450415', N'Vùng Đất Bí Ẩn', N'Trịnh Hữu Nam', N'NXB Văn Học', 'June,2014', 10.5, 7, N'Phiêu lưu', N'Tiếng Việt'),
('B0148', '978-8923603692', N'Cuộc Chiến Không Hồi Kết', N'Ngô Thanh Hải', N'NXB Quân Đội', 'December,2013', 12.5, 5, N'Lịch sử', N'Tiếng Việt'),
('B0149', '978-9885255703', N'Bàn Tay Máu', N'Vũ Đình Lâm', N'NXB Trẻ', 'April,2018', 9.5, 6, N'Kinh dị', N'Tiếng Việt'),
('B0150', '978-5216027003', N'Tâm Lý Học Đám Đông', N'Lê Hữu Phước', N'NXB Tổng Hợp', 'October,2021', 13.5, 10, N'Tâm lý', N'Tiếng Việt'),
('B0151', '978-0439943468', N'Vượt Qua Số Phận', N'Trần Ngọc Minh', N'NXB Phụ Nữ', 'January,2015', 8.9, 4, N'Tự truyện', N'Tiếng Việt'),
('B0152', '978-2665839744', N'Chinh Phục Giấc Mơ', N'Đinh Hoàng Phong', N'NXB Thanh Niên', 'July,2017', 9.9, 6, N'Kỹ năng sống', N'Tiếng Việt'),
('B0153', '978-3986003957', N'Ám Ảnh', N'Hồ Thanh Tâm', N'NXB Văn Học', 'June,2019', 10.8, 8, N'Kinh dị', N'Tiếng Việt'),
('B0154', '978-9131245814', N'Mật Mã Phương Đông', N'Phạm Văn Hậu', N'NXB Tổng Hợp', 'March,2016', 12.9, 5, N'Trinh thám', N'Tiếng Việt'),
('B0155', '978-4912992884', N'Câu Chuyện Tình Yêu', N'Nguyễn Hồng Nhung', N'NXB Kim Đồng', 'May,2023', 8.5, 12, N'Tình cảm', N'Tiếng Việt'),
('B0156', '978-7043032523', N'Những Bước Chân Đầu Tiên', N'Võ Hoài Nam', N'NXB Lao Động', 'November,2014', 9.9, 9, N'Kỹ năng sống', N'Tiếng Việt'),
('B0157', '978-1135943394', N'Bóng Hình Người Xưa', N'Trịnh Hoàng Sơn', N'NXB Văn Học', 'August,2021', 10.9, 7, N'Lãng mạn', N'Tiếng Việt'),
('B0158', '978-4533936258', N'Nhật Ký Kẻ Sát Nhân', N'Nguyễn Đăng Khoa', N'NXB Trẻ', 'February,2018', 11.5, 6, N'Kinh dị', N'Tiếng Việt'),
('B0159', '978-0967678522', N'Ký Ức Một Thời', N'Đỗ Minh Tâm', N'NXB Kim Đồng', 'December,2022', 7.8, 10, N'Tự truyện', N'Tiếng Việt'),
('B0160', '978-7114614199', N'Hành Trình Kỳ Lạ', N'Lâm Quang Đại', N'NXB Văn Học', 'September,2017', 10.5, 8, N'Phiêu lưu', N'Tiếng Việt'),
('B0161', '978-8149447718', N'Lửa Thiêng', N'Vũ Thị Minh', N'NXB Thanh Niên', 'April,2020', 8.9, 9, N'Lịch sử', N'Tiếng Việt'),
('B0162', '978-0404495354', N'Kẻ Thù Bên Trong', N'Nguyễn Quốc Bảo', N'NXB Tổng Hợp', 'June,2016', 12.2, 5, N'Trinh thám', N'Tiếng Việt'),
('B0163', '978-7449402524', N'Bản Giao Hưởng Mùa Thu', N'Trần Thị Hải Yến', N'NXB Phụ Nữ', 'January,2019', 9.8, 6, N'Tình cảm', N'Tiếng Việt'),
('B0164', '978-1254337869', N'Cuộc Chiến Trong Bóng Tối', N'Hoàng Trọng Nghĩa', N'NXB Quân Đội', 'November,2015', 12.5, 4, N'Lịch sử', N'Tiếng Việt'),
('B0165', '978-2623216945', N'Những Giấc Mơ Có Thật', N'Lê Minh Tuấn', N'NXB Kim Đồng', 'February,2021', 8.9, 10, N'Tâm lý', N'Tiếng Việt'),
('B0166', '978-2720996667', N'Vị Vua Cuối Cùng', N'Phạm Ngọc Khánh', N'NXB Văn Học', 'May,2014', 11.5, 5, N'Lịch sử', N'Tiếng Việt'),
('B0167', '978-2600544573', N'Ánh Sáng Trong Đêm', N'Ngô Huy Hoàng', N'NXB Thanh Niên', 'December,2017', 10.2, 8, N'Kinh dị', N'Tiếng Việt'),
('B0168', '978-7961183779', N'Mê Cung Bí Ẩn', N'Võ Quốc Dũng', N'NXB Tổng Hợp', 'October,2018', 12, 6, N'Trinh thám', N'Tiếng Việt'),
('B0169', '978-7645221946', N'Những Bước Chân Hoang Dã', N'Đinh Hoài Bảo', N'NXB Trẻ', 'September,2019', 10.9, 9, N'Phiêu lưu', N'Tiếng Việt'),
('B0170', '978-3454691389', N'Bóng Ma Trên Đồi', N'Nguyễn Thanh Sơn', N'NXB Kim Đồng', 'August,2016', 9.9, 7, N'Kinh dị', N'Tiếng Việt'),
('B0171', '978-7037887503', N'Chuyện Tình Không Tên', N'Lê Thị Mai', N'NXB Văn Học', 'June,2023', 8.7, 12, N'Tình cảm', N'Tiếng Việt'),
('B0172', '978-9076344448', N'Người Đàn Ông Huyền Bí', N'Trần Văn Nam', N'NXB Thanh Niên', 'March,2021', 12.9, 5, N'Trinh thám', N'Tiếng Việt'),
('B0173', '978-2169693111', N'Lạc Lối Giữa Hai Thế Giới', N'Hoàng Minh Anh', N'NXB Tổng Hợp', 'July,2015', 11.8, 7, N'Khoa học viễn tưởng', N'Tiếng Việt'),
('B0174', '978-7713948318',  N'Di sản trong bóng tối', 'Oliver Sinclair', 'Kodansha', 'July,2015', 5.5, 8, N'Bí ẩn', N'Tiếng Nhật'),
('B0175', '978-3035046147',  N'Mảnh mối cuối cùng', 'Rachel Dawson', 'Shinchosha', 'September,2018', 5.8, 6, N'Kinh dị', N'Tiếng Nhật'),
('B0176', '978-9598679829',  N'Dưới tàn tích', 'Edward Harris', 'Kadokawa Shoten', 'May,2012', 6.0, 5, N'Tiểu thuyết lịch sử', N'Tiếng Nhật'),
('B0177', '978-0988690240',  N'Nhật ký bí mật', 'Emma Clarkson', 'Shueisha', 'December,2020', 5.2, 10, N'Lãng mạn', N'Tiếng Nhật'),
('B0178', '978-7661571457',  N'Vương quốc bị nguyền rủa', 'William Everett', 'Gentosha', 'July,2021', 6.5, 4, N'Giả tưởng', N'Tiếng Nhật'),
('B0179', '978-6497495491',  N'Bước chân lặng lẽ', 'Julia Bennett', 'Kodansha', 'February,2016', 5.6, 7, N'Bí ẩn', N'Tiếng Nhật'),
('B0180', '978-7142471455',  N'Vương miện sắt', 'Marcus Holt', 'Hayakawa Shobo', 'June,2019', 6.3, 6, N'Giả tưởng', N'Tiếng Nhật'),
('B0181', '978-7335921272',  N'Lời thì thầm bị lãng quên', 'Charlotte Evans', 'Futabasha', 'October,2015', 5.4, 8, N'Kinh dị', N'Tiếng Nhật'),
('B0182', '978-2067497550',  N'Dòng chảy thời gian', 'Hannah Richardson', 'Shodensha', 'April,2023', 6.0, 5, N'Khoa học viễn tưởng', N'Tiếng Nhật'),
('B0183', '978-8372018107',  N'Kho báu ẩn giấu', 'Benjamin Scott', 'Bungei Shunju', 'March,2014', 5.3, 9, N'Phiêu lưu', N'Tiếng Nhật'),
('B0184', '978-0768767217',  N'Giao ước vĩnh cửu', 'Daniel Stevenson', 'Kadokawa Shoten', 'September,2022', 5.9, 3, N'Giả tưởng', N'Tiếng Nhật'),
('B0185', '978-3467526229',  N'Chén độc', 'Catherine Morris', 'Shinchosha', 'November,2010', 5.55, 7, N'Bí ẩn', N'Tiếng Nhật'),
('B0186', '978-8746668253',  N'Thế giới bóng tối', 'Vincent Howard', 'Hayakawa Shobo', 'July,2017', 6.2, 6, N'Giả tưởng', N'Tiếng Nhật'),
('B0187', '978-8719800523',  N'Người viếng thăm lúc nửa đêm', 'Sophia Grant', 'Futabasha', 'June,2013', 5.45, 5, N'Kinh dị', N'Tiếng Nhật'),
('B0188', '978-8465244863',  N'Thánh địa cuối cùng', 'Patrick Wallace', 'Kodansha', 'December,2019', 5.95, 4, N'Chính kịch', N'Tiếng Nhật'),
('B0189', '978-6224985863',  N'Bí ẩn dinh thự Blackwood', 'Theresa Carter', 'Kadokawa Shoten', 'October,2021', 5.75, 5, N'Kinh dị', N'Tiếng Nhật'),
('B0190', '978-0059935991',  N'Cánh cửa định mệnh', 'Nathaniel Brooks', 'Shodensha', 'May,2015', 6.4, 3, N'Giả tưởng', N'Tiếng Nhật'),
('B0191', '978-5481787048',  N'Lời tiên tri bóng tối', 'Catherine Andrews', 'Bungei Shunju', 'September,2012', 5.8, 6, N'Kinh dị', N'Tiếng Nhật'),
('B0192', '978-4866868559',  N'Lời nguyền của Hoàng đế', 'Frederick Langley', 'Hayakawa Shobo', 'February,2020', 6.7, 4, N'Giả tưởng', N'Tiếng Nhật'),
('B0193', '978-0401123433',  N'Sự thật bị lãng quên', 'Melissa Quinn', 'Futabasha', 'July,2014', 5.35, 9, N'Bí ẩn', N'Tiếng Nhật'),
('B0194', '978-9113364958',  N'Vương quốc phản loạn', 'David Thornton', 'Shinchosha', 'May,2023', 6.8, 3, N'Kinh dị', N'Tiếng Nhật'),
('B0195', '978-1541903003',  N'Hiệu trưởng', 'Eliza Montgomery', 'Kadokawa Shoten', 'August,2011', 5.7, 8, N'Tiểu thuyết lịch sử', N'Tiếng Nhật'),
('B0196', '978-0392802746',  N'Thoát khỏi xiềng xích', 'Samantha Price', 'Shodensha', 'January,2016', 5.6, 7, N'Chính kịch', N'Tiếng Nhật'),
('B0197', '978-5664189249',  N'Xuống địa ngục', 'Alexander Foster', 'Hayakawa Shobo', 'June,2021', 5.95, 5, N'Kinh dị', N'Tiếng Nhật'),
('B0198', '978-2348516917',  N'Cuộc thám hiểm Bắc', 'Richard Coleman', 'Futabasha', 'April,2009', 5.65, 6, N'Bí ẩn', N'Tiếng Nhật'),
('B0199', '978-2342773823',  N'Sự im lặng vĩnh cửu', 'Jonathan Nash', 'Shinchosha', 'August,2018', 5.85, 5, N'Tâm lý kinh dị', N'Tiếng Nhật'),
('B0200', '978-4780732397',  N'Lời nguyền mùa đông', 'Isabel Norton', 'Kadokawa Shoten', 'January,2022', 6.25, 3, N'Giả tưởng', N'Tiếng Nhật'),
('B0201', '978-1583326550',  N'Bí mật bị lãng quên', 'Derek Walsh', 'Bungei Shunju', 'November,2015', 5.55, 7, N'Bí ẩn', N'Tiếng Nhật'),
('B0202', '978-1252467325',  N'Khu rừng ma thuật', 'Sophia Henderson', 'Hayakawa Shobo', 'March,2013', 6.5, 4, N'Giả tưởng', N'Tiếng Nhật'),
('B0203', '978-6324631084',  N'Tiếng than khóc ảo ảnh', 'Vincent Ellis', 'Shodensha', 'May,2016', 5.7, 6, N'Kinh dị', N'Tiếng Nhật'),
('B0204', '978-8171230299',  N'Ngọn lửa bên trong', 'Amelia Hudson', 'Futabasha', 'April,2020', 6.1, 5, N'Phiêu lưu', N'Tiếng Nhật'),
('B0205', '978-3027534201', 'The Mozart Companion a Symposium by leading Mozart Scholar','Landon, Harold R.','W W Norton & Co Inc','November,1969','4.99','773','','English'),
('B0206', '978-2134815396', 'Joshua and the Children: A Parable','Joseph F. Girzone','Collier Books','October,1991','5.29','560','','English'),
('B0207', '978-6765190487', 'Red Square','Smith, Martin Cruz','Random House','October,1992','4.99','899',' Fiction , General','English'),
('B0208', '978-1496185021', 'Buffalo Gordon','Lewis, J. P. Sinclair','Forge Books','February,2001','23.11','552',' History , Military , United States','English'),
('B0209', '978-5046099087', 'Certain Poor Shepherds: A Christmas Tale','Thomas, Elizabeth Marshall and Davidson, Andrew (ILT)','Simon & Schuster','November,1996','5.29','750',' Fiction , General','English'),
('B0210', '978-6078208901', 'The New Building Your Mate is Self-Esteem','Rainey, Dennis and Rainey, Barbara','Thomas Nelson','July,1995','4.99','412',' Family & Relationships , Marriage & Long-Term Relationships','English'),
('B0211', '978-3546206183', 'Rogue Regimes: Terrorism and Proliferation','Tanter, Raymond','Palgrave Macmillan','February,1999','58.61','25',' Political Science , International Relations , Arms Control','English'),
('B0212', '978-9754836469', 'The Abuse Excuse: And Other Cop-Outs, Sob Stories, and Evasions of Responsibility','Dershowitz, Alan M.','Little Brown & Co','October,1994','5.47','397',' Social Science , General','English'),
('B0213', '978-8712349026', 'Houghton Mifflin Invitations to Literature: Student Anthology Level 1.4 Surprise 1997 (Invitations to Lit 1997)','Hm (COR)','Houghton Mifflin','October,1996','6.12','446',' Juvenile Nonfiction , Language Arts , General','English'),
('B0214', '978-3167297294', 'The Pharaohs of Ancient Egypt (Megascope Series)','Derouin, Claire','Barrons Juveniles','April,1998','4.99','904','','English'),
('B0215', '978-9933746146', '1776-1976: Zweihundert Jahre deutsch-amerikanische Beziehungen = two hundred years of German-American relations : eine Dokumentation (German Edition)','Piltz, Thomas','H. Moos','January,1975','10.99','729','','English'),
('B0216', '978-3309595711', 'Dannybird','Philip Macht','Maxrom Press','January,1983','4.99','103','','English'),
('B0217', '978-8362664209', 'Technical Manual and Dictionary of Classical Ballet (Dover Books on Dance)','Grant, Gail','Dover Publications','January,1982','7.71','364',' Performing Arts , Dance , Classical & Ballet','English'),
('B0218', '978-0063145624', 'Life and Death in Shanghai','Cheng, Nien','Penguin','May,1988','5.29','663',' Biography & Autobiography , Personal Memoirs','English'),
('B0219', '978-7307920934', '100 Favorite Roses (100 Favorite Series)','Dunn, Teri','Metro Books','May,2000','5.29','490',' Gardening , Flowers , Annuals','English'),
('B0220', '978-1187656795', 'All Around The Town','Clark, Mary Higgins','Simon & Schuster','January,1992','4.99','522',' Fiction , Thrillers , Suspense','English'),
('B0221', '978-2788786070', 'New World Visions of Household Gods and Sacred Places: American Art and the Metropolitan Museum of Art 1650-1914','Scully, Vincent','NEW YORK GRAPHIC SOCIETY','January,1988','13.12','68',' Art , General','English'),
('B0222', '978-5739695483', 'New Stories from the South 2001: The Year Best','Ravenel, Shannon','Algonquin Books','September,2001','5.29','414',' Fiction , Southern','English'),
('B0223', '978-7238876507', 'Orchids as House Plants','Northen, Rebecca Tyson','Dover Publications','June,1976','4.99','284',' Gardening , Flowers , Orchids','English'),
('B0224', '978-4687669984', 'The History of the American Sailing Navy: The Ships and Their Development','Chapelle, Howard Irving','Bonanza Books','January,1949','29.29','636','','English'),
('B0225', '978-1412268138', 'The Experience of Opera (Norton Library, N706)','Lang, Paul Henry','W. W. Norton & Company','November,1973','5.29','16',' Music , Genres & Styles , Opera','English'),
('B0226', '978-9642652382', 'The pattern of Soviet power','Snow, Edgar','Random House','January,1945','5.29','894','','English'),
('B0227', '978-2985823182', 'The World of Science Fiction, 1926-1976: The History of a Subculture','Del Rey, Lester','Ballantine Books','January,1979','4.99','685','','English'),
('B0228', '978-3406202950', 'Passionate Paradox; the Life of Marie Stopes','briant, keith','w.w. norton','January,1962','5.47','957','','English'),
('B0229', '978-6130499085', 'The Law','Vailland, Roger','Knopf','January,1958','12.99','190','','English'),
('B0230', '978-7159784026', 'Horsewatching: Why does a horse whinny and everything else you ever wanted to know','Morris, Desmond','Crown','April,1989','4.99','996',' General"','English'),
('B0231', '978-2179551217', 'Linda Mccartney Home Cooking','McCartney, Linda','Arcade Publishing','August,1992','4.99','877',' Cooking , Reference','English'),
('B0232', '978-3822495294', 'Herblock special for today','Block, Herbert','Simon and Schuster','January,1958','11.48','439','','English'),
('B0233', '978-0022008657', 'Beginners Cookbook (Home Library)','By','Whitecap Books - AcP Publishing','June,1994','6.16','227',' Cooking , General','English'),
('B0234', '978-7408517669', 'Ghost Light: A Memoir','Rich, Frank','Random House Trade Paperbacks','October,2001','5.29','148',' Biography & Autobiography , Entertainment & Performing Arts','English'),
('B0235', '978-6649869844', 'The Sands of Time: A Hermux Tantamoq Adventure','Hoeye, Michael','Putnam Juvenile','September,2002','5.29','667',' Young Adult Fiction , Fantasy , General','English'),
('B0236', '978-9854251731', 'Murder at the Pentagon','Truman, Margaret','Random House','April,1992','4.99','83',' Fiction , Mystery & Detective , General','English'),
('B0237', '978-8709097475', 'The Goomba Book of Love','Schirripa, Steve and Fleming, Charles','Clarkson Potter','October,2003','5.09','386',' Humor , General','English'),
('B0238', '978-8509039003', 'Stanley Elkin Greatest Hits','Elkin, Stanley','E. P. Dutton','November,1980','21.95','287',' Fiction , General','English'),
('B0239', '978-2056980537', 'Griffin & Sabine: An Extraordinary Correspondence','Bantock, Nick','Chronicle Books','September,1991','5.29','167',' Fiction , Romance , General','English'),
('B0240', '978-3331015203', 'Wine in Everyday Cooking: "Cooking with Wine for Family and Friends"','Ballard, Patricia','Wine Appreciation Guild','May,1995','5.29','643',' Cooking , Beverages , Wine & Spirits','English'),
('B0241', '978-0607040191', 'Leading Lady: The World and Theatre of Katharine Cornell','Mosel, Ted','Little, Brown and Company','January,1978','10.99','529','','English'),
('B0242', '978-9849310672', 'Invasion of the Mind Swappers From Asteroid 6!','Helquist, Brett (ILT) and Howe, James','Atheneum Books for Young Readers','July,2002','5.29','278',' Juvenile Fiction , Readers , Chapter Books','English'),
('B0243', '978-4738577693', 'The march of democracy','Adams, James Truslow','C. Scribner sons','January,1932','5.54','994','','English'),
('B0244', '978-1533758836', 'Einstein Monsters','Amis, Martin','Harmony','May,1987','15.4','571',' Fiction , General','English'),
('B0245', '978-2136113924', 'How To Live Through A Bad Day: 7 Powerful Insights From Christ Words on the Cross','Hayford, Jack W.','Thomas Nelson Publishers','September,2001','4.99','669',' Religion , Christian Life , General','English'),
('B0246', '978-7575709693', 'The Ideals Country Kitchen Cookbook','Kronschnab','Ideals Publishing','June,1975','4.99','541',' Cooking , Reference','English'),
('B0247', '978-0419462836', 'The Passenger from Scotland Yard: A Victorian Detective Novel','Wood, H. Freeman','Dover Pubns','March,1981','12.51','736',' Fiction , General','English'),
('B0248', '978-6654803044', 'The 27-Ingredient Chili Con Carne Murders','Pickard, Nancy','Delacorte Press','December,1992','5.29','87',' Fiction , Mystery & Detective , General','English'),
('B0249', '978-4919305380', 'Looking for Alaska','Jenkins, Peter','St. Martin Press','November,2001','5.94','611',' Travel , United States , West , Pacific (AK, CA, HI, OR, WA)','English'),
('B0250', '978-2744937245', 'Give Us This Day','Tudor, Tasha (ILT)','Philomel','October,1987','4.99','156',' Juvenile Fiction , Religious , Christian , General','English'),
('B0251', '978-8765007769', 'Crossing Over Jordan','Brown, Linda Beatrice','One World/Ballantine','January,1995','4.99','33',' Fiction , General','English'),
('B0252', '978-1960973430', 'Sculpture & carving at Washington Cathedral','Feller, Richard T','Cathedral Church of Saint Peter and Saint Paul','January,1976','14.62','216','','English'),
('B0253', '978-9997906914', 'When Birds Could Talk And Bats Could Sing','Moser, Barry (ILT) and Hamilton, Virginia','Blue Sky Press','March,1996','5.41','489','','English'),
('B0254', '978-0225590512', 'Line of Fire: Continuing the Saga of the Corps','Griffin, W. E. B.','G. P. Putnam Sons','January,1992','8.79','665',' Fiction , War & Military','English'),
('B0255', '978-9820621406', 'Sun and Shadow','Green, Julie','Antietam Pr','October,1989','5','42',' Poetry , General','English'),
('B0256', '978-3455670084', 'Heartstring Quilts: Quilts Made Easy (Quilting)','By','Oxmoor House','May,1996','5.29','236','','English'),
('B0257', '978-5771278857', 'Crooked Hearts','Boswell, Robert','Alfred A. Knopf','May,1987','5','123','','English'),
('B0258', '978-1776315963', 'The Lower Depths and Other Plays','Gorky, Maksim','Yale University Press','September,1959','5.29','762',' Drama , European , General','English'),
('B0259', '978-9237785705', 'Chanticleer;: The poems of Terry Wise','Terry Wise','McClure Press','January,1968','8.16','947','','English'),
('B0260', '978-4955728644', 'You Know You are A Republican/Democrat If...','Benjamin, Frank','Sourcebooks Hysteria','August,2004','5.29','423',' Political Science , Political Process , Political Parties','English'),
('B0261', '978-8820663920', 'From Mama is Kitchen','Smith, Catharine P. (EDT)','Ideals Publishing','June,1976','5.29','862',' Cooking , General','English'),
('B0262', '978-7441446415', 'The Silence Now: New and Uncollected Early Poems','Sarton, May','W. W. Norton & Company','February,1990','5.29','804',' Poetry , American , General','English'),
('B0263', '978-6106574025', 'Especially for Mothers','Rice, Helen Steiner','Barbour Publishing, Incorporated','March,1996','5.29','385',' Poetry , Subjects & Themes , Inspirational & Religious','English'),
('B0264', '978-0840457002', 'The Big Bite Book of Pizzas','Jansz, Meg','Smithmark Pub','September,1994','5.51','544',' Cooking , General','English'),
('B0265', '978-5705859862', 'Stories In An Almost Classical Mode','Brodkey, Harold.','Knopf','September,1988','10.99','479',' Fiction , Short Stories (single author)','English'),
('B0266', '978-9127271179', 'Princess Margaret','Hope, Alice','F. Muller','January,1955','8.16','302','','English'),
('B0267', '978-1979735204', 'Msieu Robin : Lyrics and legends of Jean Baptiste and his friends','Wallace Bruce Amsbary','Reilly & Lee','January,1925','7.49','876','','English'),
('B0268', '978-2782386317', 'With or Without and Other Stories','Dickinson, Charles','Knopf','April,1987','6.97','527','','English'),
('B0269', '978-3368523168', 'Parenting Isnot for Cowards: Dealing Confidently With the Frustrations of Child-Rearing','Dobson, James C.','Word','January,1987','4.99','277',' House & Home , General','English'),
('B0270', '978-3791800658', 'Frisky Business: All About Being Owned by a Cat','Brethwaite, Chris, Ahern, Lee Ann, and Bridgeman, Bill et al','Hallmark Cards','April,1990','5.29','193','','English'),
('B0271', '978-9299997820', 'The Seacoast of Bohemia','Freeling, Nicolas','Mysterious Pr','June,1995','13.12','756',' Fiction , Mystery & Detective , General','English'),
('B0272', '978-8766988108', 'The Arrival Kit: A Guide for your Journey in the Kingdom of God','Ralph W. Neighbour Jr.','Touch Publications','January,1994','5.29','594','','English'),
('B0273', '978-9315925375', 'The Catholic Girl is Guide to Sex','Anderson, Melinda, Murray, Kathleen, and Arnold, Alli (ILT)','Harmony','September,2003','5.29','246',' Self-help , Sexual Instruction','English'),
('B0274', '978-6665531364', 'In the Shadow of the Ark','Provoost, Anne and Nieuwenhuizen, John','Arthur A. Levine Books','August,2004','5.47','600',' Young Adult Fiction , Religious , General','English'),
('B0275', '978-0165402498', 'The Supernaturalist','Colfer, Eoin','Hyperion','May,2004','4.99','599',' Young Adult Fiction , Science Fiction , General','English'),
('B0276', '978-5518993811', 'U and I: A True Story','Baker, Nicholson','Random House','April,1991','5.29','250',' Literary Criticism , General','English'),
('B0277', '978-2880276650', 'Love Invents Us','Amy Bloom','Pan Books Ltd','April,1997','4.99','254','','English'),
('B0278', '978-1833960999', 'Zero Db and Other Stories','Bell, Madison Smartt','Ticknor & Fields','January,1987','5','932',' Fiction , General','English'),
('B0279', '978-8643383598', 'The Heart of a Leader','Blanchard, Kenneth H.','David C. Cook','June,1999','4.99','242',' Religion , Inspirational','English'),
('B0280', '978-4951638033', 'Hefner','Brady, Frank','MACMILLAN','January,1974','5.29','113','','English'),
('B0281', '978-0285723148', 'The Secrets of Savvy Networking: How to Make the Best Connections for Business and Personal Success','RoAne, Susan','Grand Central Publishing','April,1993','5.29','538',' Business & Economics , General','English'),
('B0282', '978-3006884719', 'A Shaker Dozen','Rocheleau, Paul (ILT) and Homsen, Kathleen','Chronicle Books','July,1999','5.29','198',' Juvenile Nonfiction , Concepts , Counting & Numbers','English'),
('B0283', '978-3142948028', 'Personal History','Graham, Katharine','Alfred A. Knopf','February,1997','5.29','938',' Biography & Autobiography , Editors, Journalists, Publishers','English'),
('B0284', '978-5545578710', 'Friendship with God: an uncommon dialogue','Walsch, Neale Donald','Putnam','October,1999','4.99','406',' Religion , Spirituality','English'),
('B0286', '978-4813744261', '13 Things You Gotta Know to Make it as a Christian (Powerlink Student Devotional)','McDowell, Josh and Hostetler, Bob','Thomas Nelson','November,1992','4.99','483',' Young Adult Nonfiction , Religious , Christian , General','English'),
('B0287', '978-8454356448', 'Dave Barry Guide to Marriage and/or Sex','Barry, Dave','Rodale Books','January,2000','4.99','527',' Humor , Form , Comic Strips & Cartoons','English'),
('B0288', '978-7307990851', 'The Festival Cookbook: Four Seasons of Favorites','Good, Phyllis Pellman','Good Books','May,2013','5.94','334',' Cooking , Seasonal','English'),
('B0289', '978-0539165582', 'Shella','Vachss, Andrew H.','Knopf','March,1993','4.99','768',' Fiction , Mystery & Detective , General','English'),
('B0290', '978-4035368924', 'What To Cook When You Think There is Nothing in the House To Eat: More Than 175 Easy Recipes And Meal Ideas','Schwartz, Arthur','Harper Perennial','February,2000','5.29','403',' Cooking , General','English'),
('B0291', '978-8509827894', 'Little Vegetarian Feasts: Main-Dish Grains','Shulman, Martha Rose and Drechsler, Debbie (ILT)','Bantam','May,1993','10.98','69',' Cooking , General','English'),
('B0292', '978-9134093992', 'Stir-Fry (Sunset Creative Cooking Library)','Sunset Books','Sunset Pub Co','October,1994','5.29','937',' Cooking , Methods , Wok','English'),
('B0293', '978-3228220131', 'Collected Stories of Reynolds Price','Price, Reynolds','Scribner','June,1993','6.24','435',' Fiction , Short Stories (single author)','English'),
('B0294', '978-1417606836', 'DIY Girl','Gesue, Monica (ILT) and Bonnell, Jennifer','Puffin','June,2003','5.29','359',' Young Adult Nonfiction , Crafts & Hobbies','English'),
('B0295', '978-9611332706', 'The Divine Pastime: Theatre Essays','Clurman, Harold','MacMillan Publishing Company.','January,1974','5.29','85','','English'),
('B0296', '978-1006545028', 'What is Bred in the Bone (Cornish Trilogy)','Davies, Robertson','Viking','November,1985','4.99','827',' Fiction , Biographical','English'),
('B0297', '978-9232391155', 'Mastering the Zone: The Next Step in Achieving SuperHealth and Permanent Fat Loss','Sears, Barry and Goodbody, Mary','Regan Books','January,1997','4.99','532',' Health & Fitness , Diet & Nutrition , Diets','English'),
('B0298', '978-8257799029', 'North Carolina Lighthouses','David Stick','Dept of Cultural Resources & History','June,1984','4.99','411','','English'),
('B0299', '978-8354833862', 'Analyzing Performance Problems, or You Really Oughta Wanna','Robert Frank Mager, Peter Pipe','Pitman Management and Training','January,1984','5.29','277','','English'),
('B0300', '978-1227802131', 'Reviving Ophelia: Saving the Selves of Adolescent Girls (Ballantine Reader Circle)','Pipher, Mary Bray','Ballantine Books','February,1995','4.99','904',' Family & Relationships , Life Stages , Adolescence','English');


INSERT INTO Customer (Cid, Cname, Cssn, CbirthDate, Cgender, CphoneNumber, Cemail, Caddress, CtotalPayment, AccountId) VALUES
('C001', N'Nguyễn Văn An', '123456789', '2000-05-20', 'Nam', '0905123456', 'nguyenvana@gmail.com', N'Đà Nẵng', 500000, 9),
('C002', N'Trần Thị Bình', '987654321', '2001-09-15', N'Nữ', '0912987654', 'tranthib@gmail.com', N'Hà Nội', 200000, 10),
('C003', N'Nguyễn Mạnh Thắng', '654542345', '2004-09-15', 'Nam', '0976534213', 'thangmanh@gmail.com', N'TP Hồ Chí Minh', 200000, 11),
('C004', N'Võ Thị Huyền', '546342348', '2004-09-15', N'Nữ', '0967548564', 'huyenthivo@gmail.com', N'Đà Nẵng', 100000, 12),
('C005', N'Đặng Văn Quyết', '675435643', '2004-09-15', 'Nam', '0967785436', 'vanquyet@gmail.com', N'Đà Nẵng', 100000, 13)



INSERT INTO CustomerBorrow (cardId, Cid, typeCard, cardExpiry, registrationDate, cardValue, borrowLimit) VALUES
('CB001', 'C001', 'Member', '2025-04-01', '2025-03-01', 50000, 10),
('CB002', 'C002', 'Vip', '2025-04-05', '2025-03-05', 100000, 15)


INSERT INTO CustomerBuy (Cid, totalPurchase, membershipLevel) VALUES
('C001', 120000, 'Member'),
('C002', 600000, 'Vip')


INSERT INTO Employee (Eid, Ename, Essn, EbirthDate, Egender, EphoneNumber, EDemail, Eaddress, Eposition, Esalary, EstartDate, AccountId) 
VALUES 
('E001', N'Phan Thị Mây', '123456789', '1990-05-15', N'Nữ', '0901234567', 'thimay@gmail.com', N'Đà Nẵng', N'Pha chế', 2000.0, '2025-03-28', 6),
('E002', N'Nguyễn Văn Dũng', '987654321', '1995-08-22', N'Nam', '0912345678', 'nvd@gmail.com', N'Đà Nẵng', N'Đứng quầy', 1500.0, '2025-03-28', 7),
('E003', N'Trần Văn Nam', '112233445', '1988-02-10', N'Nam', '0923456789', 'namtran@gmail.com', N'Đà Nẵng', N'Chạy bàn', 1800.0, '2015-03-28', 8)

