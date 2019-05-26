/*常用的查询sql*/

explain SELECT * FROM reader.article where status = 0 order by gmt_create desc, sort desc;


explain SELECT * FROM reader.article a, reader.article_category_mapper m where m.category_id in(50, 30, 50,50) and a.id = m.article_id  and status = 0 order by a.gmt_create desc, a.sort desc;




explain SELECT * FROM reader.category where reverse(pinyin) like reverse('%a') 

explain SELECT * FROM reader.category where pinyin like 'gu%' and status =0 order by gmt_create desc, sort desc


explain SELECT * FROM reader.feed where status = 0 order by id desc;

explain SELECT * FROM reader.feed where url = 'xxx' and status = 0 order by id desc;


explain SELECT * FROM reader.feed where site = 'xxx' and status = 0 order by id desc;

explain SELECT * FROM reader.task where type = 0 and status = 0 and schedule_time < now() order by schedule_time desc, priority desc ;