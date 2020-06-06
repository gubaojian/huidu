const log4js = require('./log4js-config');
const logger = log4js.getLogger("mysql-developer-dao");


var mysqlDeveloperDao = {

    insertDeveloperIfNotExist : function insertDeveloperIfNotExist(connection, item, callback){
            logger.info("check user is exist user_id " + item.id);  
            connection.query("select * from `developer` where `user_id`  = ?", [item.id],  function(error, results){
                if(error){
                    logger.error(error);
                    callback(error);
                    return;
                }
                if(!results ||  results.length <= 0){
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
                    connection.query("insert  into `developer` set ? ",   user,  function(error, results,   fields){
                        if(error){
                            logger.error("insert user user_id error " + item.id);  
                            logger.error(error);
                            callback(error);
                            return;
                        }
                        logger.info("insert user user_id " + item.id);  
                        callback(null);
                    });
                }else{
                    logger.info("check user is already exist user_id " + item.id);  
                    callback(null);
                }
            });
        }
}


module.exports =  mysqlDeveloperDao;