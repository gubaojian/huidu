const webutil = require("./webutil.js")
const htmlparser = require("htmlparser2");
const fs = require('fs');


var stargazers  = [];
var urls = [];
function parseStargazers(html){
  var start = false;
  var item = {};
  var nextLink = {};
  var parser = new htmlparser.Parser({
      onopentag: function(name, attribs){
          styleStart = false;
          if(name == "a"){
              if(attribs["data-hovercard-type"] = "user"
                 && attribs["data-octo-click"] == "hovercard-link-click"){
                  var href = attribs.href;
                  item.link = "https://github.com" + href;
                  start  = true;
              }else if(attribs["rel"] == "nofollow" && attribs["class"] == "btn btn-outline BtnGroup-item"){
                  nextLink.isNextPage = true;
                  nextLink.url = attribs.href;
              }
          }
      },
      ontext: function(text){
        if(start){
           item.name = text;
           stargazers.push(item);
           item = {};
        }
        if(nextLink.isNextPage && text == 'Next'){
             urls.push(nextLink.url);
        }
      },
      onclosetag: function(tagname){
         start  = false;
         nextLink = {};
      }
  }, {decodeEntities: true});
  parser.write(html);
  parser.end();
}


function fetchStargazers(url){
  webutil.fetchUrl(url,  function(body){
      parseStargazers(body);
      if(urls.length > 0){
        setTimeout(function(){
          var nextUrl = urls.pop();
          console.log(nextUrl);
          fetchStargazers(nextUrl);
          fs.writeFileSync(pageConfig.dataName, JSON.stringify(stargazers, null, 2));
        }, 1000);
      }else{
        console.log(url);
        fs.writeFileSync(pageConfig.dataName, JSON.stringify(stargazers, null, 2));
        fs.writeFileSync(pageConfig.path + pageConfig.stateFile,  url);
      }
  }, function(error){
       console.log(stargazers.length);
       fs.writeFileSync(pageConfig.dataName, JSON.stringify(stargazers, null, 2));
  });
}



//
var pageConfig  = {
   path : "data/12306/",
   url : "https://github.com/testerSunshine/12306/stargazers",
   dataName : "data/12306/stargazers.json",
   stateFile : "stateFile.dat"
}


var stateFile = pageConfig.path + pageConfig.stateFile;

if(!fs.existsSync(pageConfig.path)){
   fs.mkdirSync(pageConfig.path);
}

if(fs.existsSync(stateFile)){
   var currentUrl = fs.readFileSync(pageConfig.path + pageConfig.stateFile, "utf-8");
   pageConfig.url = currentUrl;
   console.log("resume from url " + pageConfig.url);
   if(fs.existsSync(pageConfig.dataName)){
     var  data = fs.readFileSync(pageConfig.dataName, "utf-8");
     stargazers = JSON.parse(data);
   }
}

fetchStargazers(pageConfig.url);


//fetchStargazers("https://github.com/apache/incubator-weex/stargazers?after=Y3Vyc29yOnYyOpO5MjAxNy0wMi0xNlQxMDoyMDo1OCswODowMADOBMJY8Q%3D%3D");
