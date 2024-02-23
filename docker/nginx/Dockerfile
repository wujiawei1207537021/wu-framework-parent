FROM nginx:1.13.0-alpine
#add  dist /usr/share/nginx/html/

##将nginx配置模板复制过去到指定目录
COPY default.template /etc/nginx/conf.d/

#COPY project-config-template.json /usr/share/nginx/html/

#将docker环境下的命令行路径切换到/etc/nginx/conf.d下执行
#WORKDIR /etc/nginx/conf.d
WORKDIR /usr/share/nginx/html/
#添加环境变量的写入

#ENTRYPOINT envsubst  < default.template > default.conf && cat default.conf && nginx -g 'daemon off;'
ENTRYPOINT envsubst  < project-config-template.json > project-config.json && cat project-config.json && nginx -g 'daemon off;'
