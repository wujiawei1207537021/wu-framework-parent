# wu-framework-parent


    安装
        brew  install gpg
    创建
        gpg --gen-key
    发送
        gpg --keyserver hkp://pool.sks-keyservers.net --send-keys D63ABA42B8ABC9DFCED412B7BB95A422DD6915C1
        
        host pool.sks-keyservers.net
        
        gpg --keyserver hkp://209.244.105.201 --send-keys D63ABA42B8ABC9DFCED412B7BB95A422DD6915C1

 mvn clean install deploy -P oss -X

export GPG_TTY=$(tty)