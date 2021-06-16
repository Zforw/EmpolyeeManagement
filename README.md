# EmpolyeeManagement
员工管理系统

实现功能：登录、添加、查找、编辑、删除、保存

### 准备工作
[SWT下载（64位）](https://download.eclipse.org/eclipse/downloads/drops4/S-4.20M1-202104071800/)

[MySql驱动下载](https://dev.mysql.com/downloads/connector/j/)

MySql配置 修改`src/pers/zforw/empmgr/employee/HR.java`中的`DB_url, username, password`

修改`src/pers/zforw/empmgr/main/Main.java`中的文件路径`filePath`

### 注意
驱动类名`Class.forName("com.mysql.cj.jdbc.Driver");`


在Mac OS上使用SWT：

- 终端命令: java -XstartOnFirstThread -jar xxx.jar
- 编译器Configuration VM options: -XstartOnFirstThread