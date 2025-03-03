use manager_n1224c1;

create table Department
(
    department_id   Integer auto_increment primary key,
    department_name nvarchar(30)
);

create table Employee
(
    id            BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name          nvarchar(50),
    date_of_birth Date,
    gender        varchar(20),
    salary        Decimal,
    phone         varchar(20),
    department_id Integer,
    FOREIGN KEY (department_id) REFERENCES Department (department_id)
);
