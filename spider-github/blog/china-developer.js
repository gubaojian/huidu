const fs = require('fs');

var citiesMap = {
    '杭州' : ['Hangzhou', 'hangzhuo', 'Hang Zhou', '杭州',   'hangzou',  'hanghzou',  'HZ'],
    '上海' : ['Shanghai', '上海', 'Shang Hai',  'shang.hai',  'Shang,hai',  '魔都'],
    '深圳' : ['Shenzhen', '深圳',  'Shen Zhen',  'shen.zhen',   'SZ',  'Shengzhen',  'shenzen'],
    '广州' : ['GuangZhou', '广州', 'Guang Zhou',  'GZ'],
    '北京' : ['Beijing', 'Beijing', 'BeJing',  'bj',  'Bei Jing',  '朝阳',  'Beijin', '北京', '帝都',  '朝阳区',  '望京',  '西城区'],
    '南京' : ['Nanjing', '南京'],
    '成都' : ['Chengdu', '成都'],
    '郑州' :['zhengzhou', '郑州',  'Zheng Zhou'],
    '西安' :['xian',  '西安',  'Xi\'an',   'Xi‘an’'],
    '厦门' :['xiamen', '厦门'],
    '武汉' :['wuhan', '武汉'],
    '苏州' :['suzhou', '苏州'],
    '天津' :['tianjin', '天津'],
    '长沙' :['changsha', '长沙'],
    '珠海' : ['zhuhai', '珠海'],
    '大连' : ['dalian', '大连'],
    '重庆' : ['chongqing', '重庆'],
    '香港' : ['Hong Kong', '香港', 'xianggang'],
    '台湾' : ['Taiwan',  '台湾'],
    '洛阳' : ['LuoYang', '洛阳'],
    '沈阳' : ['shenyang', '沈阳'],
    '福州' : ['fuzhou', '福州'],
    '合肥' : ['heifei', 'hefei',  '合肥'],
    '河北' :  ['河北'],
    '河南' : ['河南', 'Henan'],
    '南昌'  : ['NanChang', '南昌',   'nan chang'],
    '青岛' : ['青岛',  'qingdao'],
    '新加坡' : ['Singapore', '新加坡'],
    '长春':  ['长春',  'changchun',  'chang chun'],
    '宁波' : ['宁波',  'ningbo'],
    '其他' :['China',  '中国', 'chinese']
  };
  

var companyMap = {
   '阿里巴巴' : ['alibaba inc', 'alibaba', 'taobao', '阿里巴巴',  'tmall', 'aliyun',    'alibaba-inc',  'alibaba.inc',  'alibaba cloud',  'alibaba.com',  '腾讯'],
   '腾讯' : ['tencent', '腾讯',  'wework',  'tencent inc.'],
   '百度' : ['baidu',   'baidu.com',  '百度',  'baidu inc',   'baidu',   'baidu' ],
   '网易' : ['netease', '网易',   'netease games',  'netease, inc.'],
   'microsoft' : ['microsoft'],
   '京东' : ['京东', 'jd',   'jd.com',  '京东商城'],
   '字节跳动' : ['bytedance', '字节跳动',   'bytedance, inc',  '今日头条'],
   'thoughtworks' : ['thoughtworks'],
   'google' : ['google', '谷歌'],
   '华为' : ['huawei', '华为',  'hw'],
   '蚂蚁金服' : ['蚂蚁', 'alipay', 'ant financial',  'antfin',  '蚂蚁金服'],
   '美团' : ['meituan', '美团',  'meituan-dianping',  '美团点评',  'meituan.com',  'dianping',  'dianping.com',   'mtdp'],
   'ibm' : ['ibm'],
   '滴滴' : ['didi', '滴滴',  '滴滴出行',  'didichuxing',   'didi chuxing'],
   '小米' : ['xiaomi', '小米'],
   '携程' : ['ctrip', '携程',   'ctrip.com',   'ctripcorp'],
   'B站' : ['bilibili'],
   '英特尔' : ['intel', '英特尔'],
   '亚信科技' : ['亚信科技', 'asiainfo'] ,
   '科大讯飞'  : ['iflytek'],
   '自由职业者'  : ['freelance', '自由职业者', 'personal', 'self-employed',  '自由职业', '家里蹲',   'jialidun'],
   '中兴'  : ['zte', '中兴'],
   '饿了么'  : ['eleme', 'ele.me',    '饿了么' ,  'eleme'],
   '360'  : ['360',  'qihoo360',   'qihoo'],
   '爱奇艺'  : ['爱奇艺', 'iqiyi',  'iqiyi.com'],
   '金山' : ['kingsoft', '金山'],
   '去哪儿'  : ['qunar', '去哪儿',  'qunar.com'],
   '思爱普'  : ['sap', '思爱普'],
   '微博'  : ['weibo', '微博',  'weibo.com'],
   '搜狐'  : ['sohu', '搜狐',  'sogou',  'soho'],
   '知乎'  : ['zhihu', '知乎'],
   '联想'  : ['lenovo', '联想'],
   '有赞'  : ['有赞', 'youzan'],
   '用友'  : ['yonyou', '用友'],
   'nvidia'  : ['nvidia', ],
   'oracle'  : ['oracle', '甲骨文'],
   '爱立信'  : ['ericsson', '爱立信'],
   '埃森哲'  : ['accenture', '埃森哲'],
   '苏宁'  : ['suning', '苏宁'],
   'yy'  : ['yy',  'yy inc',   'yy.inc'],
   '好未来'  : [ '好未来'],
   '商汤科技'  : ['sensetime', '商汤科技'],
   '诺基亚'  : ['nokia', '诺基亚'],
   'ebay'  : ['ebay', '易贝'],
   '海康威视'  : ['hikvision', '海康威视'],
   '七牛'  : ['七牛', 'qiniu'],
   '平安'  : ['平安', 'pingan'],
   '恒生电子'  : ['hundsun', '恒生电子'],
   '魅族'  : ['meizu', '魅族'],
   'vmware'  : ['vmware'],
   '厦门铃盛'  : ['ringcentral', '厦门铃盛'],
   '红帽'  : ['red hat', '红帽'],
   '虾皮'  : ['shopee', '虾皮'],
   '金蝶'  : ['金碟', 'kingdee'],
   '中国科学院自动化研究所'  : ['casia', '中国科学院自动化研究所'],
   '优刻得'  : ['ucloud', '优刻得'],
   'uber'  : ['uber', '优步'],
   '亚马逊'  : ['aws', '亚马逊',  'amazon.com',   'amazon'],
   'ABC中国'  : ['abc', 'ABC中国'],
   '新华三'  : ['h3c', '新华三'],
   '豆瓣'  : ['豆瓣', 'douban'],
   'apple' : ['apple', '苹果'],
   '烽火通信'  : ['烽火通信', 'fiberhome'],
   '软通动力'  : ['软通动力', 'isoftstone'],
   '微店'  : ['微店', 'weidian'],
   'tcl'  : ['tcl'],
   '视源股份'  : ['视源股份', 'cvte'],
   '中国科学院软件研'  : ['中国科学院软件研', 'iscas'],
   '丁香园'  : ['丁香园', 'dxy'],
   '文思海辉'  : ['文思海辉', 'pactera'],
   '中兴软创'  : [ '中兴软创',   'ztesoft'],
   '美的'  : [ 'midea', '美的'],
   '三星'  : [ 'samsung',  '三星'],
   'shein'  : [ 'shein'],
   '汇丰银行'  : [ 'hsbc'],
   '爱彼迎'  : [ 'airbnb',  '爱彼迎'],
   '陌陌'  : ['momo',  '陌陌'],
   '旷视科技'  : ['旷视科技',  'megvii'],
   '快手'  : ['kwai'],
   '网龙网络'  : ['netdragon',   '网龙网络'],
   '微众银行'  : ['webank'],
   '链家'  : ['lianjia',   '链家'],
   ' vivo'  : [' vivo'],
   '挖财'  : ['wacai',  '挖财'],
   '喜马拉雅'  : ['ximalaya',  '喜马拉雅'],
   '招商银行'  : ['cmb',   ' 招商银行'],
   'daocloud': ['daocloud'],
   '拼多多' : ['pinduoduo',   '拼多多',  'pdd'],
   '途牛' : ['tuniu',   '途牛'],
   'facebook' : ['facebook']
 };



 function getCompany(company){
   for(var  m in companyMap){
        var companyNames = companyMap[m];
        for(var n in companyNames){
             var companyName = companyNames[n];
             if(company.toLowerCase().indexOf(companyName.toLowerCase()) >= 0){
                  return m;
             }
        }
   }
   return company;
 }

function  hasChinese(str){
   if(/[\u3220-\uFA29]+/.test(str)){
       return true;
   }else{
       if(str.toLowerCase().indexOf('cn') > 0){
           return true;
       }
       if(str.toLowerCase().indexOf('china') > 0){
          return true;
       }
       if(str.toLowerCase().indexOf('chinese') > 0){
         return true;
      }
       return false;
   }
}





var cityStats = {};
var unFindLocations = {};
var dataFileName = "user/data/user.json";
var data = fs.readFileSync(dataFileName, "utf-8");
var users = JSON.parse(data);
var keys = Object.keys(users);
var cities = Object.keys(citiesMap);
var companys = [];
var companyStats = {};

for(var index in keys){
    var key = keys[index];
    var user = users[key];

    if(user.location){
      var find = false;
      for(var  m in cities){
           var city = cities[m];
           var cityNames = citiesMap[city];
           for(var n in cityNames){
                var cityName = cityNames[n];
                if((user.location.toLowerCase().indexOf(cityName.toLowerCase()) >= 0  && cityName.length > 2)
                     ||   (cityName.length == 2 && user.location.indexOf(cityName) >= 0 )){
                     find = true;
                     if(cityStats[city]){
                        cityStats[city]++;
                     }else{
                        cityStats[city] = 1;
                     }
                     break;
                }
           }
      }

      if(!find){
          if(hasChinese(user.location)){
            //console.log(user.location  +  '  zhong  ');  
            if(unFindLocations[user.location]){
                unFindLocations[user.location]++;
            }else{
                unFindLocations[user.location] = 1;
            }
          }else{
             //console.log(user.location  + "  "   );  
          }
      }
      
      
  }

   

    if(user.company){
       var company =  user.company.toLowerCase();
        company = company.replace('@', '');
        company = getCompany(company);
         companys.push(company);
         if( companyStats [company]){
                companyStats [company] = companyStats [company] + 1;
         }else{
                companyStats [company] = 1;
         }
    }
}

var sortCityStats = [];
var keys = Object.keys(cityStats);
for(var i=0; i<keys.length; i++){
       var key = keys[i];
       var num = cityStats[key];
       var item = {key,  num};
       sortCityStats.push(item);
}
sortCityStats.sort(function(a, b){
   return b.num - a.num;
});

// 其它总和
var cityOthersSum = 0;
for(var  key in  unFindLocations){
     cityOthersSum += unFindLocations[key];
}



console.log("中国程序员地域分布排行: ");
for(var i=1;    i < sortCityStats.length;  i++){
      console.log("  " + sortCityStats[i].key   + " : " + sortCityStats[i].num   + ",");
}
console.log("  " + sortCityStats[0].key   + " : " + (sortCityStats[0].num  + cityOthersSum )  + ",");


console.log("\n\n\n\n");

var  sortCompanys = [];
var keys = Object.keys(companyStats);
for(var i=0; i<keys.length; i++){
       var key = keys[i];
       var num = companyStats[key];
       var item = {key,  num};
       sortCompanys.push(item);
}

sortCompanys.sort(function(a, b){
     return b.num - a.num;
});


console.log("中国活跃程序员公司分布排行: ");
var  othersCompany = 0;
for(var i=1;    i <   sortCompanys.length;  i++){
    if(companyMap[sortCompanys[i].key]){
        console.log("  " +sortCompanys[i].key   + " : " +  sortCompanys[i].num   + ",");
    }else{
         othersCompany +=sortCompanys[i].num;
    }
}
console.log("  " + '其它(包含学生)'   + " : " + ( othersCompany )  + ",");


