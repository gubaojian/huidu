const fs = require('fs');


var stargazers = [];

for(var i=0; i<10; i++){
  var content = fs.readFileSync("stargazers" + i + ".json", "utf-8");
  var items = JSON.parse(content);
  for(var index in items){
     var item = items[index];
     stargazers.push(item);
  }
}


 fs.writeFileSync("stargazers.json", JSON.stringify(stargazers, null, 2));

console.log(stargazers.length);
