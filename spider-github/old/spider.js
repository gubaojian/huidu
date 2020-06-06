const child_process = require('child_process');



function startSpider(){
  var spider = child_process.exec('node stargazers.js');

  spider.stdout.on('data', (data) => {
    console.log(`stdout: ${data}`);
  });

  spider.on('exit', function () {
    console.log('App Has Quit, Restart');
    setTimeout(function(){
        startSpider();
    }, 20000)
  });
}

startSpider();
