create table car
(
    id    serial primary key,
    brand text  not null,
    model text  not null,
    price money not null
);


insert into car(brand, model, price)
values ('audi', 'rs', '10000000');

insert into car(brand, model, price)
values ('bmw', 'm5', '20000000');

insert into car(brand, model, price)
values ('vw', 'boro', '5000000');

create table person
(
    id             serial primary key,
    name           varchar not null,
    age            integer not null,
    driver_license boolean,
    car_id         integer not null references car (id)
);

insert into person(name, age, driver_license, car_id)
values ('Ivan', '25', 'on', '1'),
       ('Kiril', '24', 'on', '2'),
       ('Svetlana', '23', 'on', '3');