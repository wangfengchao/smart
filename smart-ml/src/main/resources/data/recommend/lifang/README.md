数据文件说明
house.txt 
    houseid                 bigint                  房源ID                
    districtid              bigint                  区域ID                
    townid                  bigint                  板块ID                
    sellprice               double                  售价                  
    spacearea               double                  面积                  
    livingroomsum           int                     客厅                  
    bedroomsum              int                     卧室                  
    wcsum                   int                     卫生间                 
    cityid                  bigint                  城市ID 

score.txt   
    guid                    string                  用户唯一标示              
    house_id                string                  房源ID                
    score                   double                  score=sum(用户行为+时间因子)
    
test.txt
    guid                    string                  用户唯一标示              
    house_id                string                  房源ID                
    score                   double                  score=sum(用户行为+时间因子)