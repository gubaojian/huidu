const fs = require('fs');
const mysql = require('mysql');

var connection = mysql.createConnection({
    host :   "192.168.2.125",
    user : "root",
   password : "Mysql79GU",
   database : "reader"
});

module.exports =  connection;