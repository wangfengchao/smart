package com.smart.svm

/**
  * Created by fc.w on 2017/11/21.
  */
object DataProccess {

  /* mysql数据导入Hive
 sqoop import  --connect jdbc:mysql://172.16.13.1:3306/spider --username root --password 123456 --query '
 select h.estateId AS estate_id , h.houseId AS house_id, h.otherHouseId AS other_house_id, h.textSimilarityDegree AS  text_sim_degree, h.imageSimilarityDegree AS img_sim_degree, CASE h.`status` WHEN 2 THEN 1 WHEN 4 THEN 0 END AS class, date(h.createTime) AS dt
 FROM stg_merge_sim_house h where h.`status` in(2, 4) and $CONDITIONS
 ' \
 --split-by dt \
 --hive-import -m 1 \
--target-dir /user/hive/warehouse/wkdm.db/dm_wkzf_sim_house \
--hive-table wkdm.dm_wkzf_sim_house

  * */


 /* 数据格式处理
insert overwrite local directory '/home/wyp/wyp' ROW FORMAT DELIMITED FIELDS TERMINATED BY ' '
select class, concat(house_id, ':', text_sim_degree), concat(other_house_id, ':', img_sim_degree) from dm_wkzf_sim_house where house_id < other_house_id
union all
select class, concat(other_house_id, ':', text_sim_degree), concat(house_id, ':', img_sim_degree) from dm_wkzf_sim_house where house_id > other_house_id;
 * */

}
