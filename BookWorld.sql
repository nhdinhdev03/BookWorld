USE [master]
GO

CREATE DATABASE BookWorld
GO 

USE BookWorld
GO

-- TẠO BẢNG
CREATE TABLE [Users] (
	[username] VARCHAR(50) NOT NULL,
	[password] VARCHAR(50) NOT NULL,
	[full_name] NVARCHAR(100) NOT NULL,
	[phone] VARCHAR(12) ,
	[email] VARCHAR(255) NOT NULL,
	[address] NVARCHAR(MAX) ,
	[image] NVARCHAR(100),
	[admin] BIT ,
	[activated] BIT 
);
GO

CREATE TABLE [Authors] (
	[id] BIGINT IDENTITY(1,1) NOT NULL,
	[author_name] NVARCHAR(255) ,
	[date_of_birth] DATE NOT NULL,
	[nationality] NVARCHAR(50) NOT NULL,
	[description] NVARCHAR(MAX)
);
GO

CREATE TABLE [Publishers] (
	[id] BIGINT IDENTITY(1,1) NOT NULL,
	[publisher_name] NVARCHAR(255) NOT NULL,
	[address] NVARCHAR(MAX) NOT NULL,
	[city] NVARCHAR(MAX ) NOT NULL,
	[country] NVARCHAR(MAX) NOT NULL,
	[email] NVARCHAR(255) NOT NULL,
	[phone] VARCHAR(12) NOT NULL
);
GO
CREATE TABLE [Categories] (
	[id] BIGINT IDENTITY(1,1) NOT NULL,
	[category_name] NVARCHAR(MAX)
);
GO
CREATE TABLE [Books] (
	[id] VARCHAR(30) NOT NULL,
	title NVARCHAR(MAX) NOT NULL,
	[author_id] BIGINT NOT NULL,
	[category_id] BIGINT NOT NULL,
	[publisher_id] BIGINT NOT NULL,
	[created_date] DATETIME NOT NULL,
	[price] FLOAT NOT NULL,
	[quantity] INT NOT NULL,
	[image] NVARCHAR(MAX) NOT NULL,
	[available] BIT ,
	[description] NVARCHAR(MAX) ,
);
GO
CREATE TABLE [Carts] (
	[id] BIGINT NOT NULL,
	[user_id] VARCHAR(50) NOT NULL,
	[book_id] NVARCHAR(50) NOT NULL,
	[total_price] FLOAT ,
	[quantity] INT 
)
CREATE TABLE [Authors_Books] (
	[author_id] BIGINT NOT NULL,
	[book_id] VARCHAR(30) NOT NULL,
);
GO
CREATE TABLE [Favorites] (
	[id] BIGINT IDENTITY(1,1) NOT NULL,
	[user_id] VARCHAR(50) NOT NULL,
	[book_id] VARCHAR(30) NOT NULL,
	[liked_date] DATETIME NOT NULL
);
GO 

CREATE TABLE [Shares] (
	[id] BIGINT IDENTITY(1,1) NOT NULL,
	[user_id] VARCHAR(50) NOT NULL,
	[book_id] VARCHAR(30) NOT NULL,
	[email] VARCHAR(255) NOT NULL,
	[shared_date] DATETIME NOT NULL
);
GO
CREATE TABLE [Orders] (
	[id] VARCHAR(30) NOT NULL,
	[user_id] VARCHAR(50) NOT NULL,
	[order_date] DATETIME NOT NULL,
	[status] INT NOT NULL,
	[payment_method] INT NOT NULL,
	[cancellation_date] DATETIME ,
	[cancellation_reason] NVARCHAR(MAX) ,
);
GO 

CREATE TABLE [Order_Details] (
	[id] BIGINT IDENTITY(1,1) NOT NULL,
	[orders_id] VARCHAR(30) NOT NULL,
	[book_id] VARCHAR(30) NOT NULL,
	[total_price] FLOAT NOT NULL,
	[quantity] INT NOT NULL
); 
GO
-- KHÓA CHÍNH

ALTER TABLE dbo.Users 
ADD CONSTRAINT PK_Users PRIMARY KEY(username)
GO 

ALTER TABLE dbo.Authors
ADD CONSTRAINT PK_Authors PRIMARY KEY(id)
GO

ALTER TABLE dbo.Authors_Books
ADD CONSTRAINT PK_Authors_Books PRIMARY KEY(author_id,book_id)
GO 

ALTER TABLE dbo.Publishers 
ADD CONSTRAINT PK_Publishers PRIMARY KEY (id)
GO
ALTER TABLE dbo.Categories
ADD CONSTRAINT PK_Categories PRIMARY KEY(id)
GO

ALTER TABLE dbo.Books
ADD CONSTRAINT PK_Books PRIMARY KEY (id)
GO

ALTER TABLE dbo.Favorites 
ADD CONSTRAINT PK_Favorites PRIMARY KEY(id)
GO

ALTER TABLE dbo.Shares 
ADD CONSTRAINT PK_Shares PRIMARY KEY (id)
GO

ALTER TABLE dbo.Orders 
ADD CONSTRAINT PK_Orders PRIMARY KEY(id)
GO

ALTER TABLE dbo.Order_Details
ADD CONSTRAINT PK_Order_Details PRIMARY KEY (id)
GO 

ALTER TABLE dbo.Carts 
ADD CONSTRAINT PK_Carts PRIMARY KEY(id)
GO 

-- KHÓA NGOẠI

ALTER TABLE dbo.Authors_Books
ADD CONSTRAINT FK_Authors_Authors_Books
FOREIGN KEY(author_id) REFERENCES dbo.Authors(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Authors_Books
ADD CONSTRAINT FK_Books_Authors_Books
FOREIGN KEY(book_id) REFERENCES dbo.Books(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Books
ADD CONSTRAINT FK_Publishers_Books
FOREIGN KEY(publisher_id) REFERENCES dbo.Publishers(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO 

ALTER TABLE dbo.Books
ADD CONSTRAINT FK_Categories_Books 
FOREIGN KEY (category_id) REFERENCES dbo.Categories(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Favorites
ADD CONSTRAINT FK_Books_Favorites 
FOREIGN KEY(book_id) REFERENCES dbo.Books(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Favorites
ADD CONSTRAINT FK_Users_Favorites
FOREIGN KEY ([user_id]) REFERENCES dbo.Users(username) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Shares
ADD CONSTRAINT FK_Books_Shares 
FOREIGN KEY (book_id) REFERENCES dbo.Books(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Shares
ADD CONSTRAINT FK_Users_Shares
FOREIGN KEY ([user_id])REFERENCES dbo.Users(username) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Orders
ADD CONSTRAINT FK_Orders_Users
FOREIGN KEY ([user_id]) REFERENCES dbo.Users(username) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Order_Details
ADD CONSTRAINT FK_Order_Order_Details
FOREIGN KEY (orders_id) REFERENCES dbo.Orders(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Order_Details 
ADD CONSTRAINT FK_Books_Order_Details
FOREIGN KEY (book_id) REFERENCES dbo.Books(id) ON UPDATE CASCADE ON DELETE NO ACTION
GO

ALTER TABLE dbo.Carts
ADD CONSTRAINT FK_Users_Carts 
FOREIGN KEY([user_id]) REFERENCES dbo.Users(username) ON UPDATE CASCADE ON DELETE NO ACTION

ALTER TABLE dbo.Carts
ADD CONSTRAINT FK_Books_Carts 
FOREIGN KEY([book_id]) REFERENCES dbo.Books(id) ON UPDATE CASCADE ON DELETE NO ACTION

/* Users */
INSERT INTO dbo.Users
(username,[password],full_name,phone,email,[address],[image],[admin],activated)
VALUES
(  
	  N'admin', N'', N'Super Admin', N'1123123123', N'admin@gmail.com', N'admin_CT', NULL, 1, 1,
	  N'user3', N'12345678', N'Nguyễn Hoàng Dinh', N'23125126125', N'dinhnhpc03073@fpt.edu.vn', N'1', N'1', 0, 1,
	  N'user2', N'12345678', N'huu tai', N'13131313', N'tainvhpc03208@fpt.edu.vn', N'user', N'img', 0, 1,
	  N'user1', N'12345678', N'Super User', N'0123456779', N'nguyentaint979@gmail.com', N'user1_CT', N'img', 0, 1
  )
/* /Users */

/* Authors */
INSERT INTO dbo.Authors
( author_name,date_of_birth,nationality, [description])
VALUES
  ( N'Hoàng dinh', CAST(N'2003-08-01' AS Date), N'Việt nam123', N'ông bụt1',
    N'Minh Khôi2', CAST(N'2003-12-30' AS Date), N'Việt nam', N'ông già',
	N'Dale Carnegie', CAST(N'1888-11-24' AS Date), N'Việt nam', N'Dale Carnegie là một nhà văn và diễn giả nổi ',
	N'Vũ Trọng Phụng', CAST(N'1912-10-22' AS Date), N'Việt nam', N'Vũ Trọng Phụng là một nhà văn xuất sắc của Việt Nam, tác phẩm của ông thường phản ánh thực tế xã hội và nhân văn.',
	N'Nguyễn Nhật Ánh', CAST(N'1955-05-07' AS Date), N'Việt nam', N'Nguyễn Nhật Ánh là một nhà văn nổi tiếng của Việt Nam, tác phẩm của ông thường đậm chất hài hước và chân thực.',
	N'Nguyễn Huy Thiệp', CAST(N'1950-10-10' AS Date), N'Việt nam', N'Nguyễn Huy Thiệp là một nhà văn nổi tiếng của Việt Nam, tác phẩm của ông thường đặt câu hỏi về cuộc sống và tình yêu.',
	N'Phạm Nhật Ánh', CAST(N'1966-05-20' AS Date), N'Việt nam', N'Phạm Nhật Ánh là một nhà văn nổi tiếng của Việt Nam, tác phẩm của ông thường mang tính nhân văn và cảm động.',
	N'Nguyễn Du', CAST(N'1765-01-03' AS Date), N'Việt nam', N'Nguyễn Du là một nhà văn và nhà thơ nổi tiếng của Việt Nam, tác phẩm của ông có ảnh hưởng sâu sắc đến văn hóa dân tộc.',
	N'Tô Hoài', CAST(N'1920-11-24' AS Date), N'Việt nam', N'Tô Hoài là một nhà văn nổi tiếng của Việt Nam, tác phẩm của ông thường mang tính giáo dục và truyền cảm hứng.',
	N'Nam Cao', CAST(N'1915-08-21' AS Date), N'Việt nam', N'Nam Cao là một nhà văn xuất sắc của Việt Nam, tác phẩm của ông thường khắc họa cuộc sống đầy thăng trầm của con người.',
	N'Nguyễn Thế Đức', CAST(N'1970-03-12' AS Date), N'Việt nam', N'Nguyễn Thế Đức là một nhà văn và nhà báo nổi tiếng của Việt Nam, tác phẩm của ông thường tập trung vào giáo dục trẻ em.',
    N'Ngô Tất Tố', CAST(N'1897-11-14' AS Date), N'Việt nam', N'Ngô Tất Tố là một nhà văn và nhà nghiên cứu văn học nổi tiếng của Việt Nam, tác phẩm của ông thường sắc bén',
	N'', CAST(N'2023-06-01' AS Date), N'', N'',
	N'huutai', CAST(N'2023-06-30' AS Date), N'Đức', N'321',
    N'Hoàng dinh', CAST(N'2023-06-10' AS Date), N'Việt Nam', N'1',
	N'Minh Khôi', CAST(N'2023-06-10' AS Date), N'Hoa Kỳ', N'sa'   
    )
/* /Authors */

/* Publishers */
INSERT INTO dbo.Publishers
( publisher_name,[address],city,country,email,phone)
VALUES
( 
	N'NXB Trẻ', N'Sóc trăng', N'Cần thơ11', N'Việt nam', N'nxb@gmail.com', N'0945747051',
	N'NXB Văn học', N'Đồng tháp', N'Cần thơ', N'Việt nam', N'nxb@gmail.com', N'012345678',
	N'NXB Kim Đồng', N'An Giang', N'Cần thơ', N'Hoa Kỳ', N'nxb@gmail.com', N'012345678',
	N'NXB Tự Hiếu1', N'nguyen van linh', N'Cần Thờ', N'Việt Nam', N'tainvpc03208@fpt.edu.vn', N'0945747051',
	N'1', N'nguyen van linh', N'Cần Thờ', N'Việt Nam', N'nguyentaint979@gmail.com', N'0945747051',
	N'11111111111111', N'nguyen van linh', N'Cần Thờ', N'Việt Nam', N'nguyentaint979@gmail.com', N'0945747051',
	N'NXB Trẻ', N'nguyen van linh', N'Cầ', N'Việt Nam', N'nguyentaint979@gmail.com', N'0945747051',
	N'NXB Trẻ', N'nguyen van linh', N'Cần Thờ', N'Việt Nam', N'1232139@gmail.com', N'0945747051',
	N'NXB Trẻ', N'nguyen van linh', N'Cần Thờ', N'Việt Nam', N'nguyentaint979@gmail.com', N'0945747051',
	N'NXB Trẻ', N'nguyen van linh', N'Cần Thờ', N'Việt Nam', N'nguyentaint979@gmail.com', N'0945747051'
)
/* /Publishers */


/* Categories */
INSERT INTO dbo.Categories
 (category_name)
VALUES
	(
	 N'Tiểu thuyết',
	 N'Khoa học viễn tưởng',
	 N'Tâm lý học ',
	 N'Kinh doanh và quản lý',
	 N'Tự truyện và hồi ký',
	 N'Lịch sử',
	 N'Văn học Việt Nam',
	 N'Văn học nước ngoài',
	 N'Huyền bí và ma quái',
	 N'Kỹ năng sống',
	 N'Y học và sức khỏe',
	 N'Thiếu nhi',
	 N'Tôn giáo và tâm linh',
	 N'Nghệ thuật và thiết kế',
	 N'Triết học và tư duy',
	 N'Khoa học và công nghệ',
	 N'Du lịch và khám phá',
	 N'Đời sống gia đình',
	 N'Âm nhạc và nghệ thuật biểu diễn',
	 N'Thể thao và thể thao'
	)
/* /Categories */

INSERT INTO dbo.Books
(id,title,category_id,publisher_id,created_date,price,quantity,[image],[description])
VALUES
(  
	N'B001', N'Harry Potter và Hòn đá Phù thủy', 2, 1, CAST(N'2023-06-02T00:00:00.000' AS DateTime), 250000, 1, N'Sach1.jpg', 1, N'Cuốn sách đầu tiên trong loạt truyện "Harry Potter" của J.K. Rowling, mở đầu cho cuộc phiêu lưu kỳ diệu của cậu bé phù thủy Harry Potter.',
	N'B002', N'Số Đỏ', 2, 3, CAST(N'1936-06-02T00:00:00.000' AS DateTime), 120000, 1, N'Sach3.jpg', 1, N' "Số Đỏ" là một tiểu thuyết của nhà văn Vũ Trọng Phụng, được coi là một tác phẩm kinh điển trong văn học Việt Nam. Tác phẩm xoay quanh câu chuyện về cuộc sống xã hội và những bất công, hiểm nguy trong xã hội thời kỳ thuộc địa.',
	N'B003', N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 3, 4, CAST(N'1975-06-02T00:00:00.000' AS DateTime), 90000, 1, N'Sach4.jpg', 1, N'Cuốn sách được viết bởi nhà văn Nguyễn Nhật Ánh, là một tiểu thuyết dành cho tuổi mới lớn. Nó kể về cuộc sống của một cậu bé trên quê hương miền Trung Việt Nam trong những năm đầu thập kỷ 1970, với tình yêu đầu đời và những trải nghiệm tuổi trẻ.',
	N'B004', N'Chiếc Lược Ngà', 1, 3, CAST(N'1964-06-02T00:00:00.000' AS DateTime), 150000, 1, N'Sach5.jpg', 1, N' "Chiếc Lược Ngà" là tác phẩm của nhà văn Tô Hoài. Đây là một bộ truyện ngắn tập hợp các câu chuyện đan xen nhau, đưa người đọc đến với những khung cảnh, nhân vật, và câu chuyện tình yêu trong cuộc sống thường nhật.',
	N'B005', N'Tôi Là Bêtô', 2, 1, CAST(N'2000-06-02T00:00:00.000' AS DateTime), 85000, 1, N'Sach6.jpg', 1, N'Cuốn sách do nhà văn Trần Đăng Khoa sáng tác, nói về cuộc đời của một nhân vật tên là Bêtô, một người trẻ tuổi trong xã hội hiện đại với những nỗi buồn và khó khăn trong cuộc sống.',
	N'B006', N'Truyện Kiều', 2, 4, CAST(N'1820-06-02T00:00:00.000' AS DateTime), 110000, 1, N'Sach7.jpg', 1, N'"Truyện Kiều" là một tác phẩm kinh điển của văn học Việt Nam, được sáng tác bởi nhà thơ Nguyễn Du. Tác phẩm nói về cuộc đời và bi kịch của Kiều, một người phụ nữ thông minh và tài giỏi, đối mặt với những khó khăn trong cuộc sống.',
	N'B007', N'Hai số phận', 2, 3, CAST(N'2023-06-02T00:00:00.000' AS DateTime), 110000, 1, N'Sach8.jpg', 1, N'Với tên gọi "Hai số phận", có thể là tên một cuốn sách, nhưng không có thông tin cụ thể về tác giả hoặc nội dung của tác phẩm này.',
	N'B008', N'Những Cô Gái Rừng Xanh', 2, 3, CAST(N'2023-06-02T00:00:00.000' AS DateTime), 95000, 1, N'Sach9.jpg', 1, N'Tên "Những Cô Gái Rừng Xanh" cũng không được đưa ra bởi một tác giả hoặc tác phẩm cụ thể.',
	N'B009', N'Nỗi Buồn Chiến Tranh', 2, 4, CAST(N'2023-06-02T00:00:00.000' AS DateTime), 85000, 1, N'Sach10.jpg', 1, N'Tên "Nỗi Buồn Chiến Tranh" không được liên kết với một tác phẩm cụ thể hoặc tác giả.',
	N'B010', N'Dế Mèn Phiêu Lưu Ký', 2, 4, CAST(N'1941-06-02T00:00:00.000' AS DateTime), 70000, 1, N'Sach2.jpg', 1, N'Cuốn sách do nhà văn Tô Hoài sáng tác, kể về hành trình phiêu lưu của Dế Mèn, một con côn trùng nhỏ bé, trong thế giới tự nhiên đầy thú vị và hiểm nguy.',
	N'B011', N'Ngày của Triffids', 1, 4, CAST(N'1951-03-12T00:00:00.000' AS DateTime), 180000, 1, N'Sach11.jpg', 1, N'Một tiểu thuyết viễn tưởng của John Wyndham về một thế giới sau khi các cây Triffids thông minh và độc hại xâm lược loài người.',
	N'B012', N'Đắc nhân tâm', 1, 1, CAST(N'1936-06-21T00:00:00.000' AS DateTime), 120000, 1, N'Sach12.jpg', 1, N'Cuốn sách tự lực học cơ bản của Dale Carnegie, tập trung vào nghệ thuật giao tiếp hiệu quả và xây dựng mối quan hệ tốt với người khác.',
	N'B013', N'To Kill a Mockingbird', 3, 3, CAST(N'1960-08-25T00:00:00.000' AS DateTime), 150000, 1, N'Sach13.jpg', 1, N'Một tiểu thuyết của Harper Lee kể về cuộc sống và tranh đấu chống lại sự phân biệt chủng tộc trong một thị trấn nhỏ ở Alabama, Mỹ.',
	N'B014', N'Người giàu nhất thành Babylon', 3, 3, CAST(N'1926-04-15T00:00:00.000' AS DateTime), 90000, 1, N'Sach14.jpg', 1, N'Cuốn sách tự giáo dục về tài chính cá nhân và quản lý tiền bạc của George S. Clason, dựa trên nguyên tắc tài chính từ thời Babylon cổ đại.',
	N'B015', N'1984', 1, 1, CAST(N'1949-08-31T00:00:00.000' AS DateTime), 200000, 1, N'Sach15.jpg', 1, N'Một tiểu thuyết của George Orwell về một xã hội tương lai tàn bạo và áp đặt, nơi chính phủ giám sát và kiểm soát tất cả mọi hoạt động của công dân.',
	N'B016', N'The Great Gatsby', 1, 1, CAST(N'1925-06-23T00:00:00.000' AS DateTime), 140000, 1, N'Sach16.jpg', 1, N'Một tiểu thuyết của F. Scott Fitzgerald về cuộc sống xa hoa và tham vọng trong thập kỷ 1920, xoay quanh câu chuyện tình đầy bi kịch của Jay Gatsby và Daisy Buchanan.',
	N'B017', N'The Lord of the Rings', 3, 1, CAST(N'1954-03-27T00:00:00.000' AS DateTime), 300000, 1, N'Sach17.jpg', 1, N'Một tác phẩm của J.R.R. Tolkien về một cuộc hành trình phiêu lưu kỳ diệu, nơi chiến đấu giữa tốt và ác, với nhẫn quyền nắm giữ sự quyết định của thế giới.',
	N'B018', N'Pride and Prejudice', 2, 4, CAST(N'2023-06-11T00:00:00.000' AS DateTime), 110000, 1, N'Sach18.jpg', 1, N'Một tiểu thuyết của Jane Austen về tình yêu, địa vị xã hội và những trở ngại trong việc tìm kiếm hạnh phúc trong thế giới của tầng lớp quý tộc Anh.',
	N'B019', N'The Catcher in the Rye', 3, 3, CAST(N'2023-06-11T00:00:00.000' AS DateTime), 130000, 1, N'Sach19.jpg', 1, N'Một tiểu thuyết của J.D. Salinger về cuộc sống của một cậu bé tuổi teen đầy mâu thuẫn và tìm kiếm ý nghĩa trong một thế giới không thể hiểu được'
    )

/* /Books */

/* Share */
INSERT INTO dbo.Shares
(user_id,book_id,email,shared_date)
VALUES
(   
	 N'user1', N'B001', N'hoangdinh@gmail.com', CAST(N'2022-12-12T00:00:00.000' AS DateTime),
	 N'user1', N'B002', N'tuhieu@gmail.com', CAST(N'2022-12-12T00:00:00.000' AS DateTime),
	 N'user1', N'B010', N'huutai@gmail.com', CAST(N'2022-12-12T00:00:00.000' AS DateTime)
 )
/* /Share */

/* Favorite */
INSERT INTO dbo.Favorites
(user_id,book_id,liked_date)
VALUES
( 
	 N'user1', N'B001', CAST(N'2012-12-12T00:00:00.000' AS DateTime),
	 N'user1', N'B002', CAST(N'2012-12-12T00:00:00.000' AS DateTime),
	 N'user1', N'B003', CAST(N'2012-12-12T00:00:00.000' AS DateTime),
	 N'user1', N'B010', CAST(N'2023-06-11T14:48:46.697' AS DateTime),
	 N'user1', N'B004', CAST(N'2023-06-11T14:59:04.330' AS DateTime),
	 N'user1', N'B005', CAST(N'2023-06-11T15:00:21.960' AS DateTime),
	 N'user1', N'B007', CAST(N'2023-06-11T15:01:00.660' AS DateTime),
	 N'user1', N'B006', CAST(N'2023-06-11T15:02:21.773' AS DateTime),
	 N'user1', N'B009', CAST(N'2023-06-11T15:02:38.730' AS DateTime),
	 N'huutai', N'B006', CAST(N'2023-06-11T15:16:01.903' AS DateTime),
	 N'huutai', N'B007', CAST(N'2023-06-11T15:16:10.917' AS DateTime),
	 N'huutai', N'B010', CAST(N'2023-06-11T15:17:23.133' AS DateTime)
    )
/* /Favorite */

/* Oders */
INSERT INTO dbo.Orders
(id,user_id,order_date,status,payment_method,cancellation_date,cancellation_reason)
VALUES
(   
	N'D001121223', N'user1', CAST(N'2023-06-06T00:00:00.000' AS DateTime), 2, 1, CAST(N'2023-12-13T00:00:00.000' AS DateTime), N'Hủy',
	N'D002121223', N'user1', CAST(N'2022-12-12T00:00:00.000' AS DateTime), 1, 1, CAST(N'2022-12-12T00:00:00.000' AS DateTime), N'Hủy',
	N'D003121223', N'user1', CAST(N'2022-12-13T00:00:00.000' AS DateTime), 1, 1, CAST(N'2022-12-12T00:00:00.000' AS DateTime), N'Hủy',
	N'D004121223', N'user1', CAST(N'2022-12-10T00:00:00.000' AS DateTime), 1, 1, CAST(N'2000-12-12T00:00:00.000' AS DateTime), N'Hủy'
    )

/* /Oders */

/* Order_Details */
INSERT INTO dbo.Order_Details
(orders_id,book_id,total_price,quantity)
VALUES
(  
	 N'D002121223', N'B003', 90000, 3,
	 N'D003121223', N'B004', 100000, 5,
	 N'D002121223', N'B004', 100000, 1,
	 N'D004121223', N'B002', 100, 5,
	 N'D003121223', N'B003', 200, 5
    )

/* /Order_Details */