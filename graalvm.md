### 配置graalvm

    解压
    sudo tar -xzf graalvm-ce-java<version>-darwin-amd64-<version>.tar.gz
    安装
    sudo mv graalvm-ce-java<version>-<version> /Library/Java/JavaVirtualMachines
    验证
    /Library/Java/JavaVirtualMachines/graalvm-ce-java11-22.3.1/Contents/Home/bin/java -version
    安装
    /Library/Java/JavaVirtualMachines/graalvm-ce-java11-22.3.1/Contents/Home/bin/gu install native-image
    /Library/Java/JavaVirtualMachines/graalvm-ce-java11-22.3.1/Contents/Home/bin/gu list native-image --version
   
    sudo open ~/.zshrc    
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-java11-22.3.1/Contents/Home
    source ~/.zshrc
    
    sudo open ~/.bash_profile
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-java11-22.3.1/Contents/Home
    source ~/.bash_profile

