IF DB_ID(N'restaurant_pos') IS NULL
BEGIN
    CREATE DATABASE restaurant_pos;
END
GO

USE restaurant_pos;
GO

IF OBJECT_ID(N'dbo.tblUsedService', N'U') IS NOT NULL DROP TABLE dbo.tblUsedService;
IF OBJECT_ID(N'dbo.tblChiTietGiaoDich', N'U') IS NOT NULL DROP TABLE dbo.tblChiTietGiaoDich;
IF OBJECT_ID(N'dbo.tblGiaoDich', N'U') IS NOT NULL DROP TABLE dbo.tblGiaoDich;
IF OBJECT_ID(N'dbo.tblDichVu', N'U') IS NOT NULL DROP TABLE dbo.tblDichVu;
IF OBJECT_ID(N'dbo.tblKhachHang', N'U') IS NOT NULL DROP TABLE dbo.tblKhachHang;
IF OBJECT_ID(N'dbo.tblBan', N'U') IS NOT NULL DROP TABLE dbo.tblBan;
IF OBJECT_ID(N'dbo.dishes', N'U') IS NOT NULL DROP TABLE dbo.dishes;
IF OBJECT_ID(N'dbo.users', N'U') IS NOT NULL DROP TABLE dbo.users;
GO

CREATE TABLE dbo.users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    full_name NVARCHAR(100),
    position VARCHAR(50)
);
GO

CREATE TABLE dbo.dishes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    type NVARCHAR(50) NOT NULL,
    price DECIMAL(18,2) NOT NULL,
    image NVARCHAR(255),
    description NVARCHAR(MAX),
    status NVARCHAR(20) NOT NULL
);
GO

CREATE TABLE dbo.tblBan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenBan NVARCHAR(50) NOT NULL,
    kieu NVARCHAR(50),
    khuVuc NVARCHAR(100)
);
GO

CREATE TABLE dbo.tblKhachHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenKH NVARCHAR(100) NOT NULL,
    soDT VARCHAR(20)
);
GO

CREATE TABLE dbo.tblDichVu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenDichVu NVARCHAR(100) NOT NULL,
    donGia DECIMAL(18,2) NOT NULL
);
GO

CREATE TABLE dbo.tblGiaoDich (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ngayGiaoDich DATETIME NOT NULL,
    tongTien DECIMAL(18,2) NOT NULL,
    idBan INT NULL,
    idKhachHang INT NULL,
    idUser INT NULL,
    CONSTRAINT FK_tblGiaoDich_tblBan FOREIGN KEY (idBan) REFERENCES dbo.tblBan(id) ON DELETE SET NULL,
    CONSTRAINT FK_tblGiaoDich_tblKhachHang FOREIGN KEY (idKhachHang) REFERENCES dbo.tblKhachHang(id) ON DELETE SET NULL,
    CONSTRAINT FK_tblGiaoDich_users FOREIGN KEY (idUser) REFERENCES dbo.users(id) ON DELETE SET NULL
);
GO

CREATE TABLE dbo.tblChiTietGiaoDich (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idGiaoDich INT NOT NULL,
    idMonAn INT NULL,
    soLuong INT NOT NULL,
    giaBan DECIMAL(18,2) NOT NULL,
    CONSTRAINT FK_tblChiTietGiaoDich_tblGiaoDich FOREIGN KEY (idGiaoDich) REFERENCES dbo.tblGiaoDich(id) ON DELETE CASCADE,
    CONSTRAINT FK_tblChiTietGiaoDich_dishes FOREIGN KEY (idMonAn) REFERENCES dbo.dishes(id) ON DELETE SET NULL
);
GO

CREATE TABLE dbo.tblUsedService (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idGiaoDich INT NOT NULL,
    idDichVu INT NULL,
    soLuong INT NOT NULL,
    thanhTien DECIMAL(18,2) NOT NULL,
    CONSTRAINT FK_tblUsedService_tblGiaoDich FOREIGN KEY (idGiaoDich) REFERENCES dbo.tblGiaoDich(id) ON DELETE CASCADE,
    CONSTRAINT FK_tblUsedService_tblDichVu FOREIGN KEY (idDichVu) REFERENCES dbo.tblDichVu(id) ON DELETE SET NULL
);
GO

INSERT INTO dbo.users(username, password, full_name, position)
VALUES ('admin', '123456', N'Nguyễn Văn Quản Lý', 'Manager');
GO

INSERT INTO dbo.dishes(name, type, price, image, description, status)
VALUES
(N'Phở bò', N'Món chính', 65000, N'', N'Phở bò truyền thống', N'Hiện'),
(N'Phở gà', N'Món chính', 55000, N'', N'Phở gà truyền thống', N'Hiện'),
(N'Phở hải sản', N'Món chính', 75000, N'', N'Phở hải sản', N'Hiện'),
(N'Nem cuốn', N'Khai vị', 30000, N'', N'Nem cuốn khai vị', N'Hiện'),
(N'Lẩu Thái', N'Lẩu', 250000, N'', N'Lẩu Thái hải sản', N'Hiện');
GO

INSERT INTO dbo.tblBan (tenBan, kieu, khuVuc) VALUES
(N'Bàn 01', N'Bàn 4 người', N'Khu A'),
(N'Bàn 02', N'Bàn 4 người', N'Khu A'),
(N'Bàn 03', N'Bàn VIP', N'Khu VIP'),
(N'Bàn 04', N'Bàn 2 người', N'Khu B');
GO

INSERT INTO dbo.tblKhachHang (tenKH, soDT) VALUES
(N'Nguyễn Khách Lẻ', '0912345678'),
(N'Phạm Khách VIP', '0988888888');
GO

INSERT INTO dbo.tblDichVu (tenDichVu, donGia) VALUES
(N'Khăn lạnh', 3000),
(N'Nước suối', 10000),
(N'Bia Sài Gòn', 20000);
GO

SET IDENTITY_INSERT dbo.tblGiaoDich ON;
INSERT INTO dbo.tblGiaoDich (id, ngayGiaoDich, tongTien, idBan, idKhachHang, idUser) VALUES
(1, '2026-05-10T12:30:00', 143000, 1, 1, 1),
(2, '2026-05-15T19:00:00', 313000, 1, 2, 1),
(3, '2026-05-20T18:30:00', 103000, 2, 1, 1),
(4, '2026-05-25T20:00:00', 95000, 3, NULL, 1);
SET IDENTITY_INSERT dbo.tblGiaoDich OFF;
GO

INSERT INTO dbo.tblChiTietGiaoDich (idGiaoDich, idMonAn, soLuong, giaBan) VALUES
(1, 1, 2, 50000),
(1, 4, 1, 30000),
(2, 5, 1, 250000),
(2, 4, 1, 30000),
(3, 2, 2, 45000),
(4, 3, 1, 75000);
GO

INSERT INTO dbo.tblUsedService (idGiaoDich, idDichVu, soLuong, thanhTien) VALUES
(1, 1, 1, 3000),
(1, 2, 1, 10000),
(2, 1, 1, 3000),
(2, 3, 1, 20000),
(2, 2, 1, 10000),
(3, 1, 1, 3000),
(3, 2, 1, 10000),
(4, 3, 1, 20000);
GO
