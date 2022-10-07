create table public."Donation_Post" (
  post_id numeric primary key not null,
  post_title text not null,
  post_description text,
  post_date date not null,
  user_id text,
  medicine_production_date date not null,
  medicine_validity_months numeric not null,
  medicine_validity_days numeric not null,
  medicine_validity_years numeric not null,
  user_location_id numeric not null
);

create table public."Medicine" (
  medicine_id numeric primary key not null,
  medicine_name text not null,
  package_id numeric not null,
  medicine_description text,
  inserting_date date not null,
  categor_id numeric not null
);

create table public."Packaging" (
  packaging_id numeric,
  packaging_type text primary key not null
);

