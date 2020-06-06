const htmlparser = require("htmlparser2");
const fetchCommon = require('./common/fetch-common');
const connection = require('./common/mysql-connection-config');
const mysqlTrendingRepos =  require('./common/mysq-trending-repos');
const log4js = require('./common/log4js-config');
const logger = log4js.getLogger("today-trending-repos");


const todayTrendingReposUrl  = "https://github.com/trending?spoken_language_code=zh";
const githubHost = "https://github.com";



var todayTrendingRepos = {
    sourceReposItems : [],
    reposItems : [],
    parseReposFromHtml : function parseReposFromHtml(html){
        var self = this;
        var start = false;
        var startArticle = false;
        var startDesc = false;
        var startRepos = false;
        var item = {};
        var parser = new htmlparser.Parser({
            onopentag: function(tagname, attribs){
                if(tagname == "article"  && attribs["class"] == "Box-row"){
                    startArticle = true;
                }
                if(startArticle && tagname == 'p' &&  attribs['class'] == "col-9 text-gray my-1 pr-4"){
                      startDesc = true;
                }
    
                if(startArticle && tagname == 'a' && !item.url){
                    var href = attribs['href'];
                    if(href.indexOf("/stargazers") < 0 
                        && href.indexOf("/network/mem") < 0
                        && href.indexOf("/login?") < 0){
                        item.url = githubHost +  href;
                    }
                }
            },
            ontext: function(text){
                if(startDesc){
                    item.desc = text.trim();
                }
            },
            onclosetag: function(tagname){
               if(tagname == "article"){
                    startArticle = false;
                    logger.info(JSON.stringify(item, null, 2));
                    self.reposItems.push(item);
                    item = {};
               }
               if(startArticle){
                    if(tagname == 'p' && startDesc){
                        startDesc = false;
                    }
               }
            }
        }, {decodeEntities: true});
        parser.write(html);
        parser.end();
        logger.info("end" + JSON.stringify(self.reposItems));
        self.sourceReposItems = JSON.parse(JSON.stringify(self.reposItems));
        self.doInsertReposToMysql();
     },
     doFetchTodayTrendingReos : function doFetchTodayTrendingReos(url){
         var self = this;
         fetchCommon.fetchUrl(url,  function(body){
            self.parseReposFromHtml(body);
         }, function(error){
             logger.error(error);
         });
    },
    connectionMysql :  function connectionMysql(){
        connection.connect(function(error) {
              if(error){
                 logger.error(error);
              }
          });
    },
    doInsertReposToMysql : function doInsertReposToMysql(){
        var self = this;
         if(self.reposItems.length <= 0){
             connection.end();
             logger.info("doInsertReposToMysql complete, total number "  + self.sourceReposItems.length);
             return;
         }
         var repos = self.reposItems.pop();
         mysqlTrendingRepos.insertTrendingReposIfNotExist(connection, repos, function (error){
              if(error){
                  return;
               }
               self.doInsertReposToMysql();
         });
    },
       
}



todayTrendingRepos.connectionMysql();
todayTrendingRepos.doFetchTodayTrendingReos(todayTrendingReposUrl);
  

