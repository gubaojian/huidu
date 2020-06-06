const log4js = require('./log4js-config');
const logger = log4js.getLogger("mysql-trending-repos-stargazers");

var mysqlTrendingReposStargazers = {
    insertStargazersNotExist : function(connection, repos, stargazer, callback){
        logger.info("check trending repos stargazer is exist " + stargazer.link  + " repo  " + repos.id);
        connection.query("select * from `trending_repos_stargazers` where `repos_id` = ? and `name`  = ?  ", [repos.id, stargazer.name],  function(error, results){
            if(error){
                logger.error(error);
                callback(error);
                return;
            }
            if(!results ||  results.length <= 0){
                  var  stargazerItem =  {
                    "url":   stargazer.link,
                    "name" : stargazer.name,
                    "repos_id":   repos.id,
                    "status"  : 0,
                    "gmt_create" : new Date(),
                    "gmt_modified" : new Date()
                  };
                  connection.query("insert  into `trending_repos_stargazers` set ? ",   stargazerItem,  function(error, results,   fields){
                       if(error){
                            logger.error(error);
                        }
                       logger.info("insert trending repos stargazers " +  JSON.stringify(stargazerItem));
                       callback(error);
                  });
            }else{
                logger.info("trending repos stargazers already exist " + stargazer.link  + " repo  " + repos.id) ;
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
            logger.info("updateTrendingReposFetchUrl success " + fetch_url);
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





module.exports =  mysqlTrendingReposStargazers;