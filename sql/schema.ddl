    create table Employee (
        id int generated always as identity,
        firstName varchar(20),
        lastName varchar(20),
        phoneNumber varchar(30),
        positionId int,
        status char(1),
        primary key (id)
    );

    create table Position (
        id int not null,
        name varchar(1024),
        primary key (id)
    );

    create index employee_positionId_idx on Employee (positionId);

    alter table Employee 
        add constraint FK4AFD4ACED0BFD862 
        foreign key (positionId) 
        references Position;
