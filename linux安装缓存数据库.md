#linux 安装redis缓存数据库
###linux联网
### 下载安装包
    wget http://download.redis.io/releases/redis-4.0.11.tar.gz 
    tar xzf redis-4.0.11.tar.gz
    //安装gcc-c++环境
    yum install gcc-c++
    cd redis-4.0.11
    make  //编译
    make install PREFIX=/usr/local/redis

    //临时启动  ./redis-server
   
    //配置文件后台启动   复制解压的redis-4.0.11.tar.gz中的redis.conf文件
    cp redis.conf  /usr/local/redis/bin/
    cd /usr/local/redis/bin
    vi redis.conf    //修改IP127.O.1.1 为0.0.0.0  修改密码requirepass 1030406963
                     还可以修改端口号
    //启动redis服务
     ./redis-server redis.conf
    redis-cli -h 192.168.139.128 -p 6379 -a 1030406963
###在用软件RedisDesktopManager连接虚拟机中centos中redis服务，centos中是可以上网的，并且在外部主机是可以ping的，为此查了很多原因，也修改了redis.conf文件中的bind127.0.0.1也不行，后来查到原来是防火墙的问题，所以需要禁用防火墙
停止使用firewall
    >systemctl stop firewalld.service #停止firewall
禁止在开机启动
    >systemctl disable firewalld.service 
如果不行再执行端口映射
    firewall-cmd --zone=public --add-port=6379/tcp --permanent


   

