create table if not exists user_location
(
    user_location_id numeric not null,
    user_id          numeric not null,
    user_street      text    not null,
    user_city        text    not null,
    user_state       text    not null,
    user_country     text    not null,
    location_lat     numeric,
    location_long    numeric
);

create table if not exists state
(
    id           integer              not null,
    constraint state_pk
        primary key (id),
    arabic_name  text                 not null,
    english_name text                 not null,
    country_id   integer              not null,
    active       boolean default true not null
);

create table if not exists city
(
    arabic_name  text                 not null,
    id           integer              not null,
    constraint city_pk
        primary key (id),
    state_id     integer              not null,
    english_name text                 not null,
    active       boolean default true not null,
    CONSTRAINT foreign key city_state_fk (state_id) references state (id)
);

alter table city
    owner to postgres;

create table if not exists user_authorities
(
    uid          integer not null,
    authority_id integer not null
);

alter table user_authorities
    owner to postgres;

create table if not exists authority
(
    authority_id   integer not null,
    constraint authority_pk
        primary key (authority_id),
    authority_name text    not null
);


create table if not exists user_state
(
    state_id integer not null,
    constraint user_state_pk
        primary key (state_id),
    state    text    not null
);

alter table user_state
    owner to postgres;

create table if not exists food_unit
(
    id           integer not null,
    constraint food_unit_pk
        primary key (id),
    active       boolean not null,
    description  text,
    arabic_name  text,
    english_name text,
    name         varchar(255)
);

create table if not exists medicine_unit
(
    id     integer not null,
    constraint medicine_unit_pk
        primary key (id),
    name   text    not null,
    active boolean not null
);


create table if not exists need
(
    donation_need                 integer not null,
    constraint donation_need_pk
        primary key (donation_need),
    donation_type_id              integer not null,
    donation_need_date            date    not null,
    donation_need_expiration_date date    not null
);

create table if not exists charity_location
(
    uid           integer           not null,
    location_lat  numeric,
    location_long numeric,
    address       text              not null,
    city_id       integer           not null,
    state_id      integer           not null,
    country_id    integer default 1 not null
);

alter table charity_location
    owner to postgres;

create table if not exists global_message
(
    id        bigint auto_increment
        primary key,
    active    boolean not null,
    code_     varchar(255),
    language_ varchar(255),
    message_  varchar(255)
);

alter table global_message
    owner to postgres;

create table if not exists announcement
(
    active      boolean      not null,
    id          integer      not null,
    constraint announcement_pk
        primary key (id),
    title       varchar(100) not null,
    description varchar(250),
    start_date  date         not null,
    end_date    date         not null
);

create table if not exists donation_property
(
    id             integer              not null,
    constraint donation_residence_pk
        primary key (id),
    active         boolean default true not null,
    donation_id    integer              not null,
    rooms          integer              not null,
    bathrooms      integer default 0    not null,
    kitchens       integer default 0    not null,
    available_from date                 not null,
    available_to   date                 not null
);

create table if not exists user
(
    id            bigint auto_increment primary key,
    active        boolean not null,
    email         varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    password      varchar(255),
    phone_number  varchar(255),
    user_state_id integer,
    username      varchar(255)
);



create table if not exists donation
(
    id                   bigint auto_increment
        primary key,
    active               boolean not null,
    date                 timestamp,
    description          varchar(255),
    expiration_date      timestamp,
    title                varchar(255),
    category             text    not null,
    city_id              bigint,
    constraint foreign key donation_city_fk
        references city (id),
    communication_method text    not null,
    state                text,
    uid                  bigint,
    constraint foreign key donation_user_fk
        references user (id),
);



create table if not exists medicine_category
(
    id     integer not null,
    constraint medicine_category_pk
        primary key (id),
    active boolean not null,
    name   text    not null
);

alter table medicine_category
    owner to postgres;

create table if not exists medicine
(
    id             integer              not null
        primary key,
    category_id    integer,
    constraint foreign key
        medicine_medicine_category_id_fk references
            medicine_category (id),
    unit_id        integer              not null, constraint
    medicine_medicine_unit_fk
    references
    medicine_unit,
    name           varchar(255),
    active         boolean default true not null,
    info           text                 not null,
    inserting_date integer
);

alter table medicine
    owner to postgres;

create table if not exists medicine_ingredient
(
    ingredient_id integer          not null,
    medicine_id   integer          not null,
    concentration double precision not null,
    primary key (ingredient_id, medicine_id)
);

alter table medicine_ingredient
    owner to postgres;

create table if not exists packaging
(
    packaging_id   integer not null
        primary key,
    packaging_type varchar(255)
);

alter table packaging
    owner to postgres;

create table if not exists medicine_donation_unit
(
    id     integer generated by default as identity
        primary key,
    active boolean,
    name   varchar(255)
);

alter table medicine_donation_unit
    owner to postgres;

create table if not exists donation_medicine
(
    id                       integer              not null
        constraint donation_medicine_pk
        primary key,
    medicine_id              integer              not null
        constraint fkim2d4l3qa0ux8dk7lx6oxa8ts
        references medicine,
    medicine_expiration_date date                 not null,
    donation_id              integer              not null
        constraint donation_medicine_donation_id_fk
        references donation,
    active                   boolean default true not null,
    unit_id                  integer              not null
        constraint donation_medicine_medicine_unit_null_fk
        references medicine_unit
        constraint fk7nip2dudok2gicut2d2jnutex
        references medicine_donation_unit,
    amount                   double precision     not null
);

alter table donation_medicine
    owner to postgres;

create table if not exists donation_food
(
    id              integer generated always as identity (minvalue 1001)
        constraint donation_food_pk
        primary key,
    expiration_date date                 not null,
    active          boolean default true not null,
    unit_id         integer              not null
        constraint fkorowunxtn73gty38fo9oov493
        references food_unit,
    amount          double precision     not null
);

alter table donation_food
    owner to postgres;

create table if not exists donation_clothes
(
    id             integer              not null
        constraint donation_clothes_pk
        primary key,
    active         boolean default true not null,
    clothes_status text                 not null,
    amount         integer              not null
);

alter table donation_clothes
    owner to postgres;

create table if not exists donation_book
(
    id          integer              not null
        constraint donation_book_pk
        primary key,
    active      boolean default true not null,
    donation_id integer              not null
        constraint donation_book_donation_id_fk
        references donation
);

alter table donation_book
    owner to postgres;

create table if not exists donation_book_item
(
    id               integer              not null
        constraint donation_book_item_pk
        primary key,
    active           boolean default true not null,
    donation_book_id integer              not null
        constraint donation_book_item_fk
        references donation_book,
    name             text                 not null,
    amount           integer              not null,
    status           text                 not null
);

alter table donation_book_item
    owner to postgres;

create table if not exists donation_baggage
(
    id          integer              not null
        constraint donation_baggage_pk
        primary key,
    active      boolean default true not null,
    donation_id integer              not null
        constraint donation_baggage_donation_null_fk
        references donation
);

alter table donation_baggage
    owner to postgres;

create table if not exists donation_baggage_item
(
    id                  integer              not null
        constraint baggage_item_pk
        primary key,
    active              boolean default true not null,
    donation_baggage_id integer              not null
        constraint baggage_item_fk
        references donation_baggage,
    name                text                 not null,
    amount              integer              not null,
    status              text                 not null
);

alter table donation_baggage_item
    owner to postgres;

create table if not exists donation_clothes_item
(
    id                 integer              not null
        constraint donation_clothes_item_pk
        primary key,
    active             boolean default true not null,
    donation_clothe_id integer              not null
        constraint donation_clothes_item_fk
        references donation_clothes,
    name               text                 not null,
    amount             integer              not null,
    status             text                 not null
);

create table if not exists donation_post
(
    post_id                  integer auto_increment
        primary key,
    city_id                  integer,
    medicine_expiration_date timestamp,
    medicine_id              integer,
    post_date                timestamp,
    post_description         varchar(255),
    post_state_id            integer,
    uid                      integer
);

create table if not exists donation_category
(
    id     bigint auto_increment
        primary key,
    active boolean not null,
    name   varchar(255)
);
