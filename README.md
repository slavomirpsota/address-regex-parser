# Custom SQL like evaluator

I made this as a work assignment, where the request was to migrate Excel sheet data via REST to external system

## Description

Problem: mapping Excel sheet row data to Java object using Apache Poi where Excel data are parsed as String, formatting of the address is not consistent,
as all the data are broken into 25 Excel sheets coming from different branches (200k entries altogether). I needed to come up with a viable fast solution to parse addresses correctly for
most of the addresses format found in Excel, edge-cases were handled later separately. At the end of the migration we had only handful (43) of entries not formatted correctly (these were logged with filename, row/column number). 
I used this separate project for initial implementation of regex logic.

#### Working operations:

+ parsing name,
+ academic titles,
+ orientational/conscription number,
+ street number,
+ municipality,
+ zip code

#### Working address formats:

+ Street StreetNo., Municipality (Ulica Svornosti 39, Bratislava), 
+ Street StreetNo., Zip Municipality (Lopatova 32, 821 01 Bratislava), 
+ Zip, Municipality, Street StreetNo. (08001, Prešov, Poštová 38)
+ Municipality StreetNo (Spišská Nová Ves 233)

**Addresses and names are fictional**

## Getting Started

### Dependencies

* JDK 17
* apache commons lang 3
* lombok

## Author
Slavomir Psota