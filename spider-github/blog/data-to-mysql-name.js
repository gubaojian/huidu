const fs = require('fs');
const mysql = require('mysql');



var connection = mysql.createConnection({
      host :   "192.168.2.114",
      user : "root",
      password : "Mysql79GU",
      database : "reader"
});


/** 数据格式
 *  var data = {
    "login": "6",
    "id": 158675,
    "node_id": "MDQ6VXNlcjE1ODY3NQ==",
    "avatar_url": "https://avatars1.githubusercontent.com/u/158675?v=4",
    "gravatar_id": "",
    "url": "https://api.github.com/users/6",
    "html_url": "https://github.com/6",
    "followers_url": "https://api.github.com/users/6/followers",
    "following_url": "https://api.github.com/users/6/following{/other_user}",
    "gists_url": "https://api.github.com/users/6/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/6/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/6/subscriptions",
    "organizations_url": "https://api.github.com/users/6/orgs",
    "repos_url": "https://api.github.com/users/6/repos",
    "events_url": "https://api.github.com/users/6/events{/privacy}",
    "received_events_url": "https://api.github.com/users/6/received_events",
    "type": "User",
    "site_admin": false,
    "name": "Peter Graham",
    "company": "@wealthsimple ",
    "blog": "",
    "location": "Maine",
    "email": "pete@gigadrill.com",
    "hireable": null,
    "bio": null,
    "public_repos": 134,
    "public_gists": 6,
    "followers": 135,
    "following": 164,
    "created_at": "2009-11-26T21:48:59Z",
    "updated_at": "2019-05-31T15:07:07Z"
  };
 * 
 */


var dataFileName = "user/data/user.json";
var data = fs.readFileSync(dataFileName, "utf-8");
var users = JSON.parse(data);
var keys = Object.keys(users);
var index = 0;

function mysqlUpdateUserItem(nick, item, isEnd){
      console.log("prepare count " +  (index + 1)  + " total " + keys.length + " nick " + nick + " user_id " + item.id);  
      connection.query("update `developer` set user_nick = ?, name = ? where `user_id`  = ?", [nick, item.name, item.id],  function(error, results){
          if(error){
              console.log("update developer error " + item.id);
              console.log(error);
              return;
          }
          if(isEnd){
              connection.end();
            }
            doNextUserUpdate();
      });
}



function doNextUserUpdate(){
     index++;
     if(index >= keys.length){
          return;
     }
     var key = keys[index];
     var item = users[key];
     var isEnd = (index == (keys.length - 1));
     mysqlUpdateUserItem(key, item,   isEnd);
}

var key = keys[index];
var item = users[key];

connection.connect();
mysqlUpdateUserItem(key, item,   false);




