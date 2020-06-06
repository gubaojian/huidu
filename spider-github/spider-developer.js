const htmlparser = require("htmlparser2");
const fs = require('fs');
const github_api_token = "d2ac64cc15ad6f73b1a54832690e29864fe3f03c";
const github_apit_url =  "https://api.github.com/users/" + item.name + "?access_token=" + github_api_token;



var spiderDeveloper = {
    fetchNextUser : function(){}
};



function fetchNextUser(){
    if(repos.length > 0){
      item = repos.shift();
      while(repos.length > 0 && !item){
         item = repos.shift();
      }
      fetchUserInfo(item);
    }
}

var saveCount = 0;
//const token = "4a7e33c2dcbb904222b07b3b058251333ef391de";
const token = "d2ac64cc15ad6f73b1a54832690e29864fe3f03c";
//const token = "4519425f5d6d7063bdd331ce61339f41564f42dd";
function fetchUserInfo(item){
  if(!item){
    console.log("empty items " + repos.length);
    return;
  }
  var apiInfoUrl = "https://api.github.com/users/" + item.name + "?access_token=" + token;
  if(users[item.name]){
     console.log("skip user " + item.name  + "" + repos.length);
     fetchNextUser();
     return;
  }
  console.log("fetch " + apiInfoUrl + " remain " + repos.length);
  var startFetch = new Date().getTime();
  webutil.fetchUrl(apiInfoUrl,  function(body){
      //console.log(body);
      var userInfo = JSON.parse(body);
      if(userInfo.message && userInfo.message != "Not Found"){
          console.log(body  + " " + JSON.stringify(item));
          process.exit(8);
          return;
      }
      var endFetch = new Date().getTime();
      console.log("fetch used " + (endFetch - startFetch));
      startFetch = null;
      endFetch = null;
      users[item.name] = userInfo;
      saveCount++;
      if(saveCount >= MAX_SAVE_FILE_COUNT || repos.length == 0){
        var start = new Date().getTime();
        fs.writeFileSync(userFileName, JSON.stringify(users));
        var end = new Date().getTime();
        console.log("save used " + (end - start));
        saveCount = 0;
      }
      userInfo = null;
      fetchNextUser();
  }, function(error){
      console.log(error);
      process.exit(8);
  });
  apiInfoUrl = null;
}


//
//var dataFileName = "/Users/furture/code/gubaojianblog/blogspider/data/dubbo/stargazers.json";

var args = process.argv.splice(2);
//var dataFileName = "/Users/furture/code/gubaojianblog/blogspider/data/free-programming/stargazers.json";
var dataFileName = args[0];
var dataPath  =  "user/data/";
var userFileName = dataPath + "user.json";
var data = fs.readFileSync(dataFileName, "utf-8");
var repos = JSON.parse(data);
var users = {};
if(fs.existsSync(userFileName)){
   var userFileData = fs.readFileSync(userFileName, "utf-8");
   users = JSON.parse(userFileData) || {};
   userFileData = null;
   fs.writeFileSync("user/data/user_" +  new Date().getTime() + ".json", JSON.stringify(users));
}

while(repos.length > 0 && repos[repos.length -1] == undefined){
     repos.pop();
}

while(true && repos.length > 0){
  var item = repos.shift();
  if(users[item.name]){
      continue;
  }
  fetchUserInfo(item);
  if(repos.length > 0){
     fetchUserInfo(repos.shift());
  }
  if(repos.length > 0){
     fetchUserInfo(repos.shift());
  }
  break;
}