## **项目介绍**
smart-algorithm：&ensp;&ensp;&emsp;数据结构和leetcode算法模块</br>
smart-crawler： &ensp;&ensp; &ensp;&ensp;&ensp;Java 语言爬虫模块 (可忽略)</br>
smart-magic： &ensp;&ensp;&ensp;&ensp;&emsp; Java 语言爬虫模块 (可忽略)</br>
smart-scala： &ensp;&ensp; &ensp;&ensp;&ensp;&ensp;&ensp;Spark Demo 模块</br>
smart-streaming： &ensp;&ensp;&emsp;Spark Streaming模块  </br>
smart-ml：   &ensp;&ensp;&ensp;&ensp;&emsp;&ensp; &ensp;&ensp;     Spark ML模块</br>

## **smart-algorithm 模块**                                                                                             
1. com.smart.algorithm.leetcode:&ensp;&ensp; &ensp;&ensp; &ensp;&ensp; &ensp;leetcode算法题    </br>                                                                                     
2. com.smart.algorithm.sort: &ensp;&ensp; &ensp;&ensp; &ensp; &ensp;&ensp;&ensp; &ensp;&ensp;常见的排序算法   </br>                                                                                      
3. com.smart.algorithm.tree: &ensp;&ensp; &ensp;&ensp; &ensp;&ensp; &ensp;&ensp; &ensp;&ensp;树算法     </br>                                                                                    
4. com.smart.algorithm.queue: &ensp;&ensp; &ensp; &ensp;&ensp;&ensp; &ensp;&ensp;队列      </br>                                                                                   
5. com.smart.algorithm.links: &ensp;&ensp; &ensp; &ensp;&ensp;&ensp; &ensp;&ensp; &ensp;&ensp;链表        </br>                                                                                 
6. com.smart.algorithm.stack: &ensp;&ensp; &ensp; &ensp;&ensp;&ensp;  &ensp;&ensp;&ensp;&ensp;栈            </br>                                                                             
7. com.smart.algorithm.design: &ensp;&ensp; &ensp; &ensp;&ensp;&ensp; &ensp;&ensp;设计模式          </br>                                                                               
8. com.smart.algorithm.atomic:  &ensp;&ensp; &ensp;&ensp; &ensp;&ensp; &ensp;&ensp;JAVA原子类测试 </br>
9. com.smart.algorithm.lon2latDistance：&ensp;&ensp;计算经纬度两个点的之间的距离的算法</br>
10. com.smart.algorithm.hanzi2pinyin：  &ensp;&ensp;&ensp;&ensp;汉字转拼音算法</br>
11. com.smart.algorithm.strSimilarity： &ensp;&ensp; &ensp;&ensp;两个拼音字符串之间的相似度算法</br>
12. com.smart.algorithm.strSearch：  &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;字符串搜索</br>
13. com.smart.algorithm.design：  &ensp;&ensp;&ensp;&ensp; &ensp;&ensp; &ensp;&ensp;设计模式</br>
14. com.smart.algorithm.coor_conver： &ensp;&ensp;&ensp;&ensp;地图坐标经纬度转换</br>
15. com.smart.algorithm.baiduAI: &ensp;&ensp; &ensp;&ensp; &ensp;&ensp; &ensp;&ensp;百度AI，相同图片检索</br>
16. com.smart.algorithm.https:  &ensp;&ensp;&ensp;&ensp; &ensp;&ensp; &ensp;&ensp; &ensp;&ensp;HTTPS操作</br>
                                                  
## **JAVA爬虫模块**                 
1. 使用原始解析html页面和HttpClient方式爬虫 </br>                                                                                        
2. WebMagic方式爬虫      </br>    

#### **(可忽略)**
    请看epoch    python爬虫项目
                                     
## **smart-scala模块**                 
1. com.smart.spark.KafkaOffsetLauncher: &emsp;&emsp; MySQL方式自定义管理kafka offset偏移量</br>                                                           
2. com.smart.spark.ZKOffsetLauncher: &emsp;&emsp;&ensp;&emsp;ZK方式自定义管理kafka offset偏移量 </br>                                                 
3. com.smart.spark.email: &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp; 发送异常邮件</br>                                                  
4. com.smart.spark.quartz:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp;定时任务，实时监控sparkStreaming APP Id  </br>                                               
5. com.smart.spark.sparkSQL: &ensp;&ensp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; sparkSQL测试Demo </br>                                                 
6. com.smart.spark.logging: &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp;log工具类 </br>           
7. com.smart.scala.actor_test.* : &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp;Actor测试类</br>
8. com.smart.utils.Log4JTest : &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp;&ensp; Log4J按照业务存储日志测试</br>
                                                  
## **smart-streaming模块**                  
1. com.smart.apps.Base_Launcher: &emsp;&emsp; &emsp;&emsp;&emsp; &emsp;&emsp;基本函数测试</br>                                                  
2. com.smart.apps.UpdateStateByKey_Launcher:  &ensp;&emsp;updateStateByKey测试 </br>
3. com.smart.compress.GzipCompress:  &emsp;&emsp; &emsp;&emsp;&emsp;&ensp;Spark程序HDFS数据压缩，并删除HDFS源数据</br>
4. com.smart.spark.Rdd_Test1:   &emsp;&emsp; &emsp;&emsp; &emsp;&emsp; &emsp;&emsp;&ensp;&ensp;Spark RDD 原子操作测试</br>
5. com.smart.hadoop.RunJob:  &emsp;&emsp; &emsp;&emsp; &emsp;&emsp; &emsp;&emsp;&ensp;&ensp; 本地运行Hadoop MapReduce程序</br>
6. com.smart.apps.SparkStreamingLocal:  &emsp;&emsp; &emsp;&emsp;&ensp;&ensp;SparkStreaming 本地运行程序</br>

## **smart-ml**
1. com.smart.bayes： &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;贝叶斯网络
2. com.smart.breeze：  &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;Breeze函数库测试
3. com.smart.cf： &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;协同过滤
4. com.smart.features： &emsp;&emsp;&emsp;&emsp;&ensp;&ensp;特征工程
5. com.smart.FPGrowth： &emsp;&emsp;&ensp;&ensp;&ensp;&ensp;FPGrowth关联规则
6. com.smart.house_portrayal： &emsp;&ensp;房源画像热搜特征处理
7. com.smart.kmeans：&emsp;&emsp;&emsp;&emsp; &ensp;&ensp; 房源画像之热搜房源 && Demo
8. com.smart.recommend:  &emsp;&emsp;&emsp;&ensp;&ensp;&ensp;推荐系统
9. com.smart.regression： &emsp;&emsp;&emsp;&ensp;&ensp;&ensp;逻辑回归&&线性回归
10. com.smart.tf_idf： &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp;TF_IDF归一化处理