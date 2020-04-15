
1.sum
=====

（1）sum()函数里面的参数是列名的时候，是计算列名的值的相加，而不是有值项的总数。
（2）sum(条件表达式)，如果记录满足条件表达式就加1，统计满足条件的行数

 

2.count
=====
（1）COUNT()函数里面的参数是列名的的时候,那么会计算有值项的次数。（NULL 不计入， 但是''值计入）
（2）COUNT(*)可以计算出行数，包括null
（3）COUNT（1）也可以计算出行数，1在这里代表一行
（4）COUNT(column)对特定的列的值具有的行数进行计算，不包含NULL值
（5）COUNT(条件表达式)，不管记录是否满足条件表达式，只要非NULL就加1

select   sum(1) as 所有条数，sum(a=2) as 符合条件的条数，sum(a=1) as 符合条件的条数  from 表 group  by
SELECT TYPE ,sum(a>2) num from(select type , pid ,count(*) a from t_test group by type,pid) a group by type; 
