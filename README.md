# _A Comedy of Eros | Hair Salon_

#### _An app for hair salon employees to schedule clients, Sept 23, 2016_

#### By _**Sara Jensen**_

## Description

_**The owner of the hair salon may add a list of stylists, and for each stylist, add clients who see that stylist. The stylists work independently, so each client only belongs to a single stylist.**_

## Setup/Installation Requirements

_To run app locally:_
* _Make sure you have the Java Development Kit (JDK) and a Java Runtime Environment (JRE) installed._
* _Install Gradle_
* _Clone https://github.com/thejensen/java_dictionary to your desktop_
* _In terminal, execute $gradle run_
* _In your browser (preferably latest version of Chrome), go to localhost:4567._
* _If the app doesn't initialize in the browser, contact me!_

_To recreate the hair-salon database in PSQL:_
* _CREATE DATABASE hair-salon;_
* _CREATE TABLE stylists (id serial PRIMARY KEY, name varchar, description varchar);_
* _CREATE TABLE clients (id serial PRIMARY KEY, name varchar, description varchar);_

## User Stories

* _As a salon employee, I need to be able to see a list of all our stylists._
* _As an employee, I need to be able to select a stylist, see their details, and see a list of all clients that belong to that stylist._
* _As an employee, I need to add new stylists to our system when they are hired._
* _As an employee, I need to be able to add new clients to a specific stylist._
* _As an employee, I need to be able to update a stylist's details._
* _As an employee, I need to be able to update a client's details._
* _As an employee, I need to be able to delete a stylist if they're no longer employed here._
* _As an employee, I need to be able to delete a client if they no longer visit our salon._

## Specifications

| Behavior | Input | Output |
| --- | --- | --- |
| Program returns a list of stylists | Dana, Marilyn, Javier | Dana, Marilyn, Javier |
| Program returns the details and client list of a particular stylist | Dana | Dana; Specializes in braids and curly hair; Kahlief Adams, Jill Adams, Megan O'Leary |
| Program stores/returns new stylists | Trevor | Dana, Marilyn, Javier, Trevor |
| Program stores/returns new clients added to the stylist client list | Dana; Ero Gray | Dana; Kahlief Adams, Jill Adams, Megan O'Leary, Ero Gray |
| Program updates existing details for a stylist | Specializes in updos | Dana; Specializes in updos |
| Program stores client details | Kahlief Adams; Male prefers short and faded cut | Kahlief Adams; male prefers short and faded cut |
| Program edits client details | Male growing out cut | Kahlief Adams; Male growing out cut |
| Program deletes stylist from database | -Dana | Marilyn, Javier, Trevor |
| Program deletes client from stylist list | -Kahlief Adams | Jill Adams, Megan O'Leary, Ero Gray |

## Known Bugs

_No known bugs._

## Support and contact details

_If you run into any issues, have questions, ideas or concerns, or want to make a contribution to the code, contact me at jensen.sara.e@gmail.com._

## Technologies Used

_Java, Spark, Velocity, jUnit, Postgres/PSQL._

## Future Development

* _Include a form where users may search for a stylist by name. Use a SQL query to perform the database search, then display a list of all results._
* _Include a form where users may also search for a client by name. Use a SQL query to perform the database search, then display a list of all results._
* _Add a <select> drop down field that allows a user to select which Stylist a Client belongs to._
* _Add a detail view page for each Client. Include additional properties to record additional notes and details the salon may want to record about each Client._

### License

Copyright (c) 2016 **_MIT License_**
