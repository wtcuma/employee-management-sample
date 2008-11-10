    create table Employee (
        id  serial not null,
        firstName varchar(20),
        lastName varchar(20),
        phoneNumber varchar(30),
        positionId int4,
        status char(1),
        primary key (id)
    );

    create table Position (
        id int4 not null,
        name varchar(1024),
        primary key (id)
    );

    create index employee_positionId_idx on Employee (positionId);

    alter table Employee 
        add constraint FK4AFD4ACED0BFD862 
        foreign key (positionId) 
        references Position;
