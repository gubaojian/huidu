const fetch = require('node-fetch');

function fetchUrl(url, callback, error){
  if(url.startsWith("//")){
     url ="https:" + url;
  }
  fetch(url, {headers:{
    "user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36",
    "Cookie" : "_ga=GA1.2.740843508.1553238495; _octo=GH1.1.813602093.1553238495; tz=Asia%2FShanghai; user_session=KG0335eypT1XDynPZ8TqwbL7z_ee0yU_0NEjMdfUsxhf9AZ-; __Host-user_session_same_site=KG0335eypT1XDynPZ8TqwbL7z_ee0yU_0NEjMdfUsxhf9AZ-; logged_in=yes; dotcom_user=gubaojian; _device_id=99811691a40ca836f54ec9205c3c6a55; has_recent_activity=1; _gat=1; _gh_sess=Wm9IaWI1eWhjdzJNQ0liSnZlOGtHSXExdHl5ekZVcU03UTQ1MzFOMXZMNHBoQkpWUjBhM2tMN3hpL2JXN0RrOVg3eml3eEF3RHVvMWdMZDIraE55OE5jc1JvclVyT25DeGFNbVJLVHlaeXhEY1lPbkZsTWczL05lVy9zQTBIUkNvckZNQ0hXdm81Q0JWUXdJK3JTM1ExcFdRMURoQkRYUUJMNG53dnNkMFc2SUFueVhRZlZEM3crOFVON1VZcnd0cE9ZSjl6VUVicGNNaVcwVjFkLytQUDhtZGYxNVprSHh1ZlVvcmNRWTJzbkt3K0p1NkhycUJIMmFrbmNLQk8xVmRhOFB0OCtpcnh5bzJta2Nya0tMNndzOFYreHUrSytsVVhLZUhEOUhOWmVQejVJWVJBZEM5TEFhcEVzcjZWbmVTNEx5bm9GRXNBNUp3ajdFRmhXckhRPT0tLVh2WHg3cmRlWS9QaE84YnhGd0FNdVE9PQ%3D%3D--dccd593a1406a42c72e58b35e174ca6670cd3e80"
     }})
      .then(res => res.text())
      .then(body => {
          callback(body);
      }, err => {
          console.log(err);
          if(error){
             error(err);
          }
      });
}

var webutil = {};
webutil.fetchUrl = fetchUrl;

module.exports = webutil;
