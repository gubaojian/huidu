const htmlparser = require("htmlparser2");
const fetchCommon = require('./common/fetch-common');
const fs = require('fs');
const connection = require('./common/mysql-connection-config');
const mysqlTrendingRepos =  require('./common/mysq-trending-repos');
const mysqlTrendingReposStargazers =  require('./common/mysq-trending-repos-stargazers');
const log4js = require('./common/log4js-config');
const logger = log4js.getLogger("spider-repos-stargazers");



var arguments = process.argv.splice(2);

if(arguments.length <= 0){
    return;
}
var url = arguments[0];
logger.debug(" start spider url " + url);
var spiderReposStargazers = {
    nextPageUrls : [],
    repos : null,
    closeConnection : function closeConnection(){
        connection.end();
    },
    fetchStargazers : function fetchStargazers(url){
        var self = this;
        mysqlTrendingRepos.getTrendingReposByUrl(connection, url, function(repos){
             if(repos){
                 self.repos = repos;
                 var fetchUrl = repos.fetch_url;
                 if(!fetchUrl){
                     fetchUrl = repos.url + "/stargazers";
                 }
                 self.fetchPageStargazers(fetchUrl);
             }else{
                 connection.end();
             }
        })
    },
    fetchPageStargazers: function fetchPageStargazers(url){
        var self = this;
        logger.info("fetchPageStargazers " + url);
        fetchCommon.fetchUrl(url,  function(body){
            var stargazersItems = self.parsePageStargazers(body);
            self.saveStargazersItems(stargazersItems, function(){
                setTimeout(function(){
                    mysqlTrendingRepos.updateTrendingReposFetchUrl(connection, self.repos, url, function(error){
                        logger.info("next page url " + self.nextPageUrls.length);
                        if(self.nextPageUrls.length > 0){
                            var nextUrl = self.nextPageUrls.pop();
                            logger.info("next page url " + nextUrl  + " " + self.nextPageUrls.length);
                            self.fetchPageStargazers(nextUrl);
                        }else{
                            mysqlTrendingRepos.markTrendingReposFetchFinished(connection, self.repos, function(error){
                                    self.closeConnection();
                            });
                        }
                    });
                }, 1000);
            });
        }, function(error){
              logger.error(error);
              self.closeConnection();
        });
    },
    parsePageStargazers :  function  parsePageStargazers(html){
        var self = this;
        var start = false;
        var item = {};
        var nextLink = {};
        var stargazersItems = [];
        var parser = new htmlparser.Parser({
            onopentag: function(name, attribs){
                if(name == "a"){
                    if(attribs["data-hovercard-type"] = "user"
                       && attribs["data-octo-click"] == "hovercard-link-click"){
                        var href = attribs.href;
                        item.link = "https://github.com" + href;
                        start  = true;
                    }else if(attribs["rel"] == "nofollow" && attribs["class"] == "btn btn-outline BtnGroup-item"){
                        nextLink.isNextPage = true;
                        nextLink.url = attribs.href;
                        logger.info("next page url " + nextLink.url);
                    }
                }
            },
            ontext: function(text){
              if(start){
                 item.name = text;
                 stargazersItems.push(item);
                 item = {};
              }
              if(nextLink.isNextPage && text == 'Next'){
                   self.nextPageUrls.push(nextLink.url);
                   logger.info("self next page url " + nextLink.url);
              }
            },
            onclosetag: function(tagname){
               start  = false;
               nextLink = {};
            }
        }, {decodeEntities: true});
        parser.write(html);
        parser.end();
        return stargazersItems;
    },
    saveStargazersItems : function saveStargazersItems(items, callback){
        var self = this;
        if(!items || items.length <= 0){
            callback();
            return;
        }
        var item = items.pop();
        mysqlTrendingReposStargazers.insertStargazersNotExist(connection, self.repos, item, function(error){
              if(error){
                  logger.error(error);
                  callback();
              }else{
                  self.saveStargazersItems(items, callback);
              }
        })
    }

  
};

connection.connect();
spiderReposStargazers.fetchStargazers(url);