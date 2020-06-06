const fs = require('fs');
const mysql = require('mysql');



var connection = mysql.createConnection({
     host :   "192.168.2.125",
     user : "root",
    password : "Mysql79GU",
    database : "reader"
});

connection.connect();

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

function mysqlInsertUserItem(item, isEnd){
            var user =  {
              "login":  item.login,
              "user_id":   item.id,
              "node_id": item.node_id,
              "avatar_url":  item.avatar_url,
              "gravatar_id":  item.gravatar_id,
              "url":  item.url,
              "html_url":  item.html_url,
              "followers_url": item.followers_url,
              "following_url": item.following_url,
              "gists_url": item.gists_url,
              "starred_url": item.starred_url,
              "subscriptions_url": item.subscriptions_url,
              "organizations_url": item.organizations_url,
              "repos_url": item.repos_url,
              "events_url": item.events_url,
              "received_events_url": item.received_events_url,
              "type": item.type,
              "site_admin": item.site_admin,
              "name": item.name,
              "company": item.company,
              "blog": item.blog,
              "location": item.location,
              "email": item.email,
              "hireable": item.hireable,
              "bio": item.bio,
              "public_repos": item.public_repos,
              "public_gists": item.public_gists,
              "followers": item.followers,
              "following": item.following,
              "data_json" : JSON.stringify(item),
              "created_at": new Date(Date.parse(item.created_at)),
              "updated_at":new Date( Date.parse(item.updated_at )),
              "gmt_create" : new Date(),
              "gmt_modified" : new Date()
            };
          console.log("prepare count " +  (index + 1)  + " total " + keys.length);  
          connection.query("select * from `developer` where `user_id`  = ?", [user.user_id],  function(error, results){
            if(error){
                console.log(error);
            }
              if(!results ||  results.length <= 0){
                    connection.query("insert  into `developer` set ? ",   user,  function(error, results,   fields){
                      console.log("insert count " +  (index + 1)    + " total " + keys.length);
                    if(isEnd){
                        connection.end();
                    }
                     doNextUserInsert();
                });
              }else{
                console.log("insert count " +  (index + 1)  + " total " + keys.length);
                  if(isEnd){
                      connection.end();
                 }
                 doNextUserInsert();
              }
          });
}



function doNextUserInsert(){
     index++;
     if(index >= keys.length){
          return;
     }
     var key = keys[index];
     var item = users[key];
     var isEnd = (index == (keys.length - 1));
     mysqlInsertUserItem(item,   isEnd);
}

var key = keys[index];
var item = users[key];
mysqlInsertUserItem(item,   false);




