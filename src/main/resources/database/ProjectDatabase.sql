DROP DATABASE IF EXISTS apex_construction;
CREATE DATABASE  apex_construction;

USE  apex_construction;

CREATE TABLE Customer (
                          CustomerId VARCHAR(10) PRIMARY KEY,
                          Name VARCHAR(255) NOT NULL,
                          Address VARCHAR(255) NOT NULL,
                          Contact_Tel VARCHAR(20) NOT NULL
);

CREATE TABLE Machine (
                         MachineID VARCHAR(100) PRIMARY KEY,
                         Name VARCHAR(255) NOT NULL,
                         Availability BOOLEAN NOT NULL,
                         Status VARCHAR(255) NOT NULL,
                         QtyOnHand INT NOT NULL
);

CREATE TABLE Repair (
                        RepairID VARCHAR(100) PRIMARY KEY,
                        MachineID VARCHAR(100) NOT NULL,
                        Qty INT NOT NULL,
                        FOREIGN KEY (MachineID) REFERENCES Machine(MachineID)
                            ON UPDATE CASCADE
                            ON DELETE CASCADE
);

CREATE TABLE Material (
                          MaterialID VARCHAR(100) PRIMARY KEY,
                          Name VARCHAR(255) NOT NULL,
                          Qty_On_Hand INT NOT NULL,
                          ModelNo VARCHAR(255) NOT NULL,
                          Amount DECIMAL (20 , 5) NOT NULL
);

CREATE TABLE Payment (
                         PaymentID VARCHAR (100) PRIMARY KEY,
                         Method VARCHAR(255) NOT NULL
);

CREATE TABLE Employee (
                          EmployeeID VARCHAR (100) PRIMARY KEY,
                          Name VARCHAR(255) NOT NULL,
                          Role VARCHAR(255) NOT NULL,
                          Address VARCHAR(255) NOT NULL,
                          Salary DECIMAL(10,2) NOT NULL,
                          ContactNo VARCHAR (20) NOT NULL,
                          Attendance VARCHAR(255) NOT NULL
);

CREATE TABLE UserAccount (
                             UserID VARCHAR(100) PRIMARY KEY,
                             Full_Name VARCHAR(100) NOT NULL,
                             User_name VARCHAR(255) NOT NULL,
                             Password VARCHAR(255) NOT NULL,
                             EmployeeID VARCHAR (100) NOT NULL,
                             Email VARCHAR (225) NOT NULL,
                             FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
                                 ON UPDATE CASCADE
                                 ON DELETE CASCADE
);

CREATE TABLE Supplier (
                          SupplierID VARCHAR (100) PRIMARY KEY,
                          Name VARCHAR(255) NOT NULL,
                          Address VARCHAR(255) NOT NULL,
                          Email VARCHAR (255) NOT NULL,
                          ContactNo VARCHAR(20) NOT NULL
);

CREATE TABLE Project (
                         ProjectID VARCHAR (100) PRIMARY KEY,
                         Name VARCHAR(255) NOT NULL,
                         Description VARCHAR(255) NOT NULL,
                         CustomerID VARCHAR (100) NOT NULL,
                         StartDate DATE NOT NULL,
                         EndDate DATE NOT NULL ,
                         PaymentID VARCHAR (100) NOT NULL,
                         UserID VARCHAR (100) NOT NULL ,
                         FOREIGN KEY (PaymentID) REFERENCES Payment(PaymentID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
                         FOREIGN KEY (UserID) REFERENCES UserAccount(UserID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
                         FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerId)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE ProjectMachineDetails (
                                       ProjectID VARCHAR (100) NOT NULL,
                                       MachineID VARCHAR (100) NOT NULL,
                                       FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID)
                                           ON UPDATE CASCADE
                                           ON DELETE CASCADE,
                                       FOREIGN KEY (MachineID) REFERENCES Machine(MachineID)
                                           ON UPDATE CASCADE
                                           ON DELETE CASCADE
);

CREATE TABLE ProjectMaterialDetails (
                                        ProjectID  VARCHAR (100) NOT NULL,
                                        MaterialID  VARCHAR (100) NOT NULL,
                                        Qty INT,
                                        FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID)
                                            ON UPDATE CASCADE
                                            ON DELETE CASCADE,
                                        FOREIGN KEY (MaterialID) REFERENCES Material(MaterialID)
                                            ON UPDATE CASCADE
                                            ON DELETE CASCADE
);

INSERT INTO Customer (CustomerId, Name, Address, Contact_Tel) VALUES
                                                                  ('CUST001', 'Eva Miles', '123 Main Street, Colombo', '076-9720785'),
                                                                  ('CUST002', 'Liam Brown', '456 Oak Ave , Maradankadawala', '075-8565259'),
                                                                  ('CUST003', 'Sophia Davis', '789 Pine Lane , Gampaha', '078-5462359'),
                                                                  ('CUST004', 'Noah Wilson', '101 Elm Road , Maha Nuwara', '077-3695231'),
                                                                  ('CUST005', 'Olivia Garcia', '234 Birch Read Kandy,', '075-4562135'),
                                                                  ('CUST006', 'Ethan Rodriguez', '567 Maple Street , Jafna', '072-2951327'),
                                                                  ('CUST007', 'Ava Martinez', '890 Willow Street , Kaluthara', '071-4653785'),
                                                                  ('CUST008', 'Jackson Thomas', '123 Cedar Ave , Galen Bindunu Wawa', '076-0120356'),
                                                                  ('CUST009', 'Isabella Jackson', '456 Redwood Lane , Kandy', '075-5869457'),
                                                                  ('CUST010', 'Aiden White', '789 Spruce Road , Wallawaththa', '076-5689975');

INSERT INTO Machine (MachineID, Name, Availability, Status, QtyOnHand) VALUES
                                                                           ('MACH001', 'Lathe', TRUE, 'Operational', 10),
                                                                           ('MACH002', 'Milling Machine', TRUE, 'In Use', 5),
                                                                           ('MACH003', 'Drill Press', FALSE, 'Under Maintenance', 2),
                                                                           ('MACH004', 'CNC Router', TRUE, 'Operational', 8),
                                                                           ('MACH005', 'Welding Machine', FALSE, 'Under Maintenance', 1),
                                                                           ('MACH006', 'Grinder', TRUE, 'Operational', 12),
                                                                           ('MACH007', 'Band Saw', TRUE, 'In Use', 3),
                                                                           ('MACH008', 'Surface Grinder', FALSE, 'Under Repair', 0),
                                                                           ('MACH009', 'Plasma Cutter', TRUE, 'Operational', 6),
                                                                           ('MACH010', '3D Printer', TRUE, 'Operational', 4);

INSERT INTO Repair (RepairID, MachineID , Qty) VALUES
                                                   ('REP001', 'MACH001',0),
                                                   ('REP002', 'MACH002',0),
                                                   ('REP003', 'MACH003',1),
                                                   ('REP004', 'MACH004',0),
                                                   ('REP005', 'MACH005',2),
                                                   ('REP006', 'MACH006',0),
                                                   ('REP007', 'MACH007',0),
                                                   ('REP008', 'MACH008',3),
                                                   ('REP009', 'MACH009',0),
                                                   ('REP010', 'MACH010',0);

INSERT INTO Material (MaterialID, Name, Qty_On_Hand, ModelNo, Amount) VALUES
                                                                          ('MAT001', 'Steel Rod', 500, 'SR-100', 15.99),
                                                                          ('MAT002', 'Aluminum Sheet', 1000, 'AS-200', 25.50),
                                                                          ('MAT003', 'Copper Wire', 2000, 'CW-300', 10.75),
                                                                          ('MAT004', 'Plastic Granules', 800, 'PG-400', 8.25),
                                                                          ('MAT005', 'Rubber Gasket', 300, 'RG-500', 5.00),
                                                                          ('MAT006', 'Glass Panel', 150, 'GP-600', 30.00),
                                                                          ('MAT007', 'Wood Plank', 600, 'WP-700', 12.50),
                                                                          ('MAT008', 'Ceramic Tile', 400, 'CT-800', 18.75),
                                                                          ('MAT009', 'Fabric Roll', 250, ' FR-900', 20.00),
                                                                          ('MAT010', 'Metal Pipe', 350, 'MP-1000', 22.50);

INSERT INTO Payment (PaymentID, Method) VALUES
                                            ('PAY001', 'Cash'),
                                            ('PAY002', 'Credit Card'),
                                            ('PAY003', 'Check'),
                                            ('PAY004', 'Bank Transfer'),
                                            ('PAY005', 'Online Payment'),
                                            ('PAY006', 'Mobile Payment'),
                                            ('PAY007', 'Invoice'),
                                            ('PAY008', 'Purchase Order'),
                                            ('PAY009', 'Wire Transfer'),
                                            ('PAY010', 'Debit Card');

INSERT INTO Employee (EmployeeID, Name, Role, Address, Salary, ContactNo, Attendance) VALUES
                                                                                          ('EMP001', 'Anjana Heshan', 'Manager', '123 Main St', 50000.00, '076-4810851', '90%'),
                                                                                          ('EMP002', 'Thanuja Sammana', 'Engineer', '456 Oak Ave', 60000.00, '077-4710851', '90%'),
                                                                                          ('EMP003', 'Tharusha Sandaruwan', 'Technician', '789 Pine Ln', 40000.00, '078-1434888', '85%'),
                                                                                          ('EMP004', 'Sainsa Rethmi', 'Accountant', '101 Elm Rd', 55000.00, '070-4839927', '75%'),
                                                                                          ('EMP005', 'Sachini Thakshila', 'Salesman', '234 Birch Blvd', 45000.00, '071-2353785', '80%'),
                                                                                          ('EMP006', 'Nisal Sahansith', 'Marketing Manager', '567 Maple Dr', 65000.00, '075-9535651', '78%'),
                                                                                          ('EMP007', 'Charuka Hansaja', 'IT Specialist', '890 Willow St', 50000.00, '071-6968190', '70%'),
                                                                                          ('EMP008', 'Tharusha Sandaruwan', 'HR Manager', '123 Cedar Ave', 60000.00, '078-1434888', '85%'),
                                                                                          ('EMP009', 'Kamesh Nethsara', 'Production Manager', '456 Redwood Ln', 70000.00, '077-1399828', '89%'),
                                                                                          ('EMP010', 'Sasindu Senevirathne', 'Quality Control', '789 Spruce Rd', 45000.00, '076-3281084', '68%');

INSERT INTO UserAccount (UserID, Full_Name , User_name, Password, EmployeeID , Email) VALUES
    ('USER001','Anjana Heshan', 'Anjana', '1234', 'EMP001','anjanaheshan27@gmail.com');

INSERT INTO Supplier (SupplierID, Name, Address, Email, ContactNo) VALUES
                                                                       ('SUP001', 'Constructo  Components', '123 Industrial Avenue, Colombo, Sri Lanka','constructo@gmail.com', '+94 71 123 4567'),
                                                                       ('SUP002', 'BuildPro Supplies', ' 45 Builders Lane, Kandy, Sri Lanka','buildPro@gmail.com', ' +94 72 234 5678'),
                                                                       ('SUP003', 'Ace Hardware Solutions', '78 Contractor Road, Galle, Sri Lanka','acehardware@gmail.com','+94 73 345 6789'),
                                                                       ('SUP004', 'SteelWorks Suppliers', '12 Foundry Street, Negombo, Sri Lanka','steelworks@gmail.com', ' +94 74 456 7890'),
                                                                       ('SUP005', 'Evergreen Construction Supply', ' 99 Forest Drive, Kurunegala, Sri Lanka','evergreen@gmail.com', '+94 75 567 8901'),
                                                                       ('SUP006', 'PrimeBuild Resources', '56 Quarry Avenue, Jaffna, Sri Lanka','primebuild@gmail.com', '+94 76 678 9012'),
                                                                       ('SUP007', 'Superior Cement & Materials', '88 Concrete Boulevard, Matara, Sri Lanka','superiorcement@gmail.com', '+94 77 789 0123'),
                                                                       ('SUP008', 'Titan Industrial Supplies', ' 102 Heavy Machinery Park, Ratnapura, Sri Lanka','titanindustrial@gmail.com','+94 78 890 1234'),
                                                                       ('SUP009', 'MegaBuild Solutions', '7 Construction Crescent, Anuradhapura, Sri Lanka','megabuildsolution@gmail.com','+94 79 901 2345'),
                                                                       ('SUP010', 'BlueStone Construction Materials', '23 Quarry Circle, Trincomalee, Sri Lanka','bluestone@gmail.com','+94 71 012 3456');

INSERT INTO Project (ProjectID, Name, Description, CustomerID, StartDate, EndDate, PaymentID, UserID) VALUES
                                                                                                          ('PROJ001', 'Project Alpha', 'Development of a new product', 'CUST001', '2022-01-01', '2022-06-30', 'PAY001', 'USER001'),
                                                                                                          ('PROJ002', 'Project Bravo', 'Marketing campaign for a new service', 'CUST002', '2022-02-01', '2022-08-31', 'PAY002', 'USER001'),
                                                                                                          ('PROJ003', 'Project Charlie', 'IT infrastructure upgrade', 'CUST003', '2022-03-01', '2022-09-30', 'PAY003', 'USER001'),
                                                                                                          ('PROJ004', 'Project Delta', 'Research and development of a new technology', 'CUST004', '2022-04-01', '2022-10-31', 'PAY004', 'USER001'),
                                                                                                          ('PROJ005', 'Project Echo', 'Development of a new software application', 'CUST005', '2022-05-01', '2022-11-30', 'PAY005', 'USER001'),
                                                                                                          ('PROJ006', 'Project Foxtrot', 'Implementation of a new business process', 'CUST006', '2022-06-01', '2022-12-31', 'PAY006', 'USER001'),
                                                                                                          ('PROJ007', 'Project Golf', 'Development of a new hardware device', 'CUST007', '2022-07-01', '2023-01-31', 'PAY007', 'USER001'),
                                                                                                          ('PROJ008', 'Project Hotel', 'Marketing campaign for a new product', 'CUST008', '2022-08-01', '2023-02-28', 'PAY008', 'USER001'),
                                                                                                          ('PROJ009', 'Project India', 'IT infrastructure upgrade', 'CUST009', '2022-09-01', '2023-03-31', 'PAY009', 'USER001'),
                                                                                                          ('PROJ010', 'Project Juliet', 'Research and development of a new technology', 'CUST010', '2022-10-01', '2023-04-30', 'PAY010', 'USER001');

INSERT INTO ProjectMachineDetails (ProjectID, MachineID) VALUES
                                                             ('PROJ001', 'MACH001'),
                                                             ('PROJ002', 'MACH002'),
                                                             ('PROJ003', 'MACH003'),
                                                             ('PROJ004', 'MACH004'),
                                                             ('PROJ005', 'MACH005'),
                                                             ('PROJ006', 'MACH006'),
                                                             ('PROJ007', 'MACH007'),
                                                             ('PROJ008', 'MACH008'),
                                                             ('PROJ009', 'MACH009'),
                                                             ('PROJ010', 'MACH010');

INSERT INTO ProjectMaterialDetails (ProjectID, MaterialID, Qty) VALUES
                                                                    ('PROJ001', 'MAT001', 100),
                                                                    ('PROJ002', 'MAT002', 200),
                                                                    ('PROJ003', 'MAT003', 300),
                                                                    ('PROJ004', 'MAT004', 400),
                                                                    ('PROJ005', 'MAT005', 500),
                                                                    ('PROJ006', 'MAT006', 600),
                                                                    ('PROJ007', 'MAT007', 700),
                                                                    ('PROJ008', 'MAT008', 800),
                                                                    ('PROJ009', 'MAT009', 900),
                                                                    ('PROJ010', 'MAT010', 1000);
