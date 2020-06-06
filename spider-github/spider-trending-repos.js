const connection = require('./common/mysql-connection-config');
const mysqlTrendingRepos =  require('./common/mysq-trending-repos');
const log4js = require('./common/log4js-config');
const logger = log4js.getLogger("spider-trending-repos");
const child_process = require('child_process');




var spiderTrendingRepos = {
    startNextReposSpider: function startNextReposSpider() {
        var self = this;
        mysqlTrendingRepos.selectTrendingRepos(connection, function(repos){
             if(repos){
                  self.spiderReposStargazers(repos);
             }else{
                logger.info('none repos need spider ');
                connection.end();
             }
        })
    },
    spiderReposStargazers : function spiderReposStargazers(repos){
        var self = this;
         if(!repos){
             return;
         }
         var cmd = 'node spider-repos-stargazers.js ' + repos.url;
         logger.info(cmd);
         var spider = child_process.exec(cmd);
         spider.stdout.on('data', (data) => {
              console.log(`${data}`);
          });
        
          spider.on('exit', function () {
            logger.debug(repos.url  +  ' spider end, try next page or url');
            setTimeout(function(){ //
                self.startNextReposSpider();
            }, 2000)
          });
    }
};
connection.connect();
spiderTrendingRepos.startNextReposSpider();