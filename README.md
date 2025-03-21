# transaction
运行步骤如下：
在IntellijIDEA 命令行下输入命令：
mvn package && 
docker build -t bank-app . &&
docker run -p 8888:8888 bank-app
