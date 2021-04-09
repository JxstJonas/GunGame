# GunGame
Minecraft GunGame MiniGame/FFA. Spigot version 1.8.8. Database: MongoDB and MySQL

[Spigot](https://www.spigotmc.org/resources/gungame-wie-auf-minesucht-1-8-x.91119/)

## Infos
Used Java version for development is `1.8.0_261`.
The tested version is Spigot 1.8.8.
I expect you can use it also for Paper
The following database are supported: `MongoDB` and `MySQL`

## Setup
How to setup the FFA MiniGame. 
The following commands you can use: 
`/setup spawn` - set the Spawn
`/setup pos1` - Protect your spawn (Required `pos2`)
`/setup pos2` - Protect your spawn (Required `pos1`).

Open your `/plugins/GunGame` Direction and edit the `config.yml`.

##### MongoDB

If you have a MongoDB database, follow this one.
To open a MongoDB connection you set the value of the database to 
mongodb (`database: 'mysql'`). If you have a localhost MongoDB database 
leave the `connectionString` empty if you have a `connectionString` added
it there.

##### MySQL

If you have a MySQL database, follow this one.
To Open a MySQL connection you must set the `mysql...` to your personal values,
if you don't have a MySQL Password leave the empty.


Now you can start **or** reload the Server to connect the database.
