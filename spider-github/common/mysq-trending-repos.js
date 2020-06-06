const log4js = require('./log4js-config');
const logger = log4js.getLogger("mysql-trending-repos");

var mysqlTrendingRepos = {
    insertTrendingReposIfNotExist : function(connection, repos, callback){
        logger.info("check trending repos is exist " + repos.url);
        connection.query("select * from `trending_repos` where `url`  = ?", [repos.url],  function(error, results){
            if(error){
                logger.error(error);
                callback(error);
                return;
            }
            if(!results ||  results.length <= 0){
                  var  reposItem =  {
                    "url":   repos.url,
                    "desc":   repos.desc,
                    "language_code" : "zh",
                    "status"  : 0,
                    "gmt_create" : new Date(),
                    "gmt_modified" : new Date()
                  };
                  connection.query("insert  into `trending_repos` set ? ",   reposItem,  function(error, results,   fields){
                       if(error){
                            logger.error(error);
                        }
                       logger.info("insert trending repos  " +  JSON.stringify(reposItem));
                       callback(error);
                  });
            }else{
                logger.info("trending repos already exist " + repos.url);
                callback(error);
            }
        });
    },
    selectTrendingRepos : function(connection, callback){
        connection.query("SELECT * FROM reader.trending_repos where status = 0 limit 0, 1", [],  function(error, results){
            if(error){
                logger.error(error);
                callback(null);
                return;
            }
            if(!results ||  results.length <= 0){
                  callback(null);
            }else{
                callback(results[0]);
            }
        });
    },
    getTrendingReposByUrl : function(connection, url, callback){
        connection.query("SELECT * FROM reader.trending_repos where url = ?", [url],  function(error, results){
            if(error){
                logger.error(error);
                callback(null);
                return;
            }
            if(!results ||  results.length <= 0){
                  callback(null);
            }else{
                callback(results[0]);
            }
        });
    },
    updateTrendingReposFetchUrl : function(connection, repos, fetch_url, callback){
        connection.query("update reader.trending_repos set fetch_url = ?, fetch_time =  ? where id = ?;", [fetch_url, new Date(), repos.id],  function(error, results){
            if(error){
                logger.error(error);
                callback(error);
                return;
            }
            logger.info("updateTrendingReposFetchUrl success " + fetch_url );
            callback(null);
        });
    },
    markTrendingReposFetchFinished : function(connection, repos, callback){
        connection.query("update reader.trending_repos set status = 1  where id = ?;", [repos.id],  function(error, results){
            if(error){
                logger.error(error);
                callback(error);
                return;
            }
            logger.info("markTrendingReposFetchFinished success " + repos.url);
            callback(null);
        });
    },
};





module.exports =  mysqlTrendingRepos;