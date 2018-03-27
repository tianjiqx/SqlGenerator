# SqlGenerator
generate some sql

功能：自动化生成sql语句

当前仅支持简单sql，不支持子查询，join on条件只支持单个

使用方法：
增加自定义SqlGenerator的函数，定义需要的基本表个数，where，join，group by等列，调用各个类Select，From,Where生成相应子句，确定想要的sql子句数量，最后组合成一条sql。
具体样例参考produce系列函数
