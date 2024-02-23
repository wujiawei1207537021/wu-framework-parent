#### [介绍](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-easy-parent/wu-easy-excel-starter)

    wu-easy-excel-starter是一款面向对象的excel、文件导出框架。
    实现的功能：
        1）针对Java中任何对象直接返回给web，在返回web过程自动将对象转换成想要的数据（Excel、File）
        2）前端传一个Excel过来，后端接口接收到的其实已经是Bean了

### 快速导出excel模块

    依赖引入
     <dependency>
        <groupId>com.wu</groupId>
        <artifactId>wu-easy-excel-starter</artifactId>
        <version>1.2.2-JDK17-SNAPSHOT</version>
     </dependency>

### 基本用法

     @Data
     public class UseExcel {
     
         @EasyExcelFiled(name = "id")
         private Integer id;
     
         @EasyExcelFiled(name = "当前时间")
         private LocalDateTime currentTime;
     
         @EasyExcelFiled(name = "描述")
         private String desc;
     
         @EasyExcelFiled(name = "类型")
         private String type;
     }
    
        @EasyExcel(fileName = "导出数据")
        @GetMapping("/run/{size}")
        public List<UseExcel> run(@PathVariable Integer size) {
            List<UseExcel> useUserExcelList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                UseExcel useExcel = new UseExcel();
                useExcel.setCurrentTime(LocalDateTime.MAX);
                useExcel.setDesc("默认方式导出数据");
                useExcel.setId(i);
                useExcel.setType("默认方式双注解导出");
                useUserExcelList.add(useExcel);
            }
            return useUserExcelList;
        }

### 自定义字段注解用法

    @Data
    public class UseExcel {
    
        @JSONField(name = "id")
        private Integer id;
    
        @JSONField(name = "当前时间")
        private LocalDateTime currentTime;
    
        @JSONField(name = "描述")
        private String desc;
    
        @JSONField(name = "类型")
        private String type;
    }
    @EasyExcel(fileName = "非原生注解导出数据", filedColumnAnnotation = JSONField.class,filedColumnAnnotationAttribute = "name",multipleSheet = true, limit = 10,sheetShowContext = EasyExcel.SheetShowContext.TEXT)
    @GetMapping("/run2/{size}")
    public List<UseExcel> run2(@PathVariable Integer size) {
        List<UseExcel> useUserExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseExcel useExcel = new UseExcel();
            useExcel.setCurrentTime(LocalDateTime.MAX);
            useExcel.setDesc("自定义字段注解方式导出数据");
            useExcel.setId(i);
            useExcel.setType("自定义字段注解导出");
            useUserExcelList.add(useExcel);
        }
        return useUserExcelList;
    }         

- 说明 filedColumnAnnotation 从 JSONField注解的name属性中获取表头并导出数据
- multipleSheet 导出的数据分多个sheet(工作簿)
- limit每个sheet 有10条数据
- sheetShowContext每个sheet的名称使用EasyExcel.SheetShowContext.TEXT 中文

### 自定义字段注解+自定控制器注解用法

    @Data
    public class UseExcel {
    
        @JSONField(name = "id")
        private Integer id;
    
        @JSONField(name = "当前时间")
        private LocalDateTime currentTime;
    
        @JSONField(name = "描述")
        private String desc;
    
        @JSONField(name = "类型")
        private String type;
    }

- 自定意义EasyExcel注解

###

    @Target({ElementType.TYPE,ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @EasyExcel(useAnnotation = false,filedColumnAnnotation = JSONField.class)
    public @interface EasyExcelTemp {
    
        /**
         * 文件名称
         * @return String
         */
        @AliasFor(annotation = EasyExcel.class,attribute = "fileName")
        String fileName();
    }

- 使用方法

##

    @EasyExcelTemp(fileName = "自定义注解导出")
    @GetMapping("/run4/{size}")
    public List<UseExcel> run4(@PathVariable Integer size) {
        List<UseExcel> useUserExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseExcel useExcel = new UseExcel();
            useExcel.setCurrentTime(LocalDateTime.MAX);
            useExcel.setDesc("自定义注解导出");
            useExcel.setId(i);
            useExcel.setType("自定义注解导出");
            useUserExcelList.add(useExcel);
        }
        return useUserExcelList;
    }

## 新增垂直合并excel

    @Data
    @EasySmart(perfectTable = true)
    public class UseExcel {

    @EasyExcelFiled(name = "原生注解-id")
    @JSONField(name = "JSONField注解-id")
    private Integer excelId;

    @EasySmartField(name = "`current_time`")
    @EasyExcelFiled(name = "原生注解-当前时间")
    @JSONField(name = "JSONField注解-当前时间")
    private LocalDateTime currentTime;

    @EasySmartField(name = "`orderByDesc`")
    @EasyExcelFiled(name = "原生注解-描述",fieldMerge = EasyExcelFiled.EasyExcelFieldMerge.VERTICAL)
    @JSONField(name = "JSONField注解-描述")
    private String desc;

    @EasyExcelFiled(name = "原生注解-类型",fieldMerge = EasyExcelFiled.EasyExcelFieldMerge.VERTICAL)
    @JSONField(name = "JSONField注解-类型")
    private String type;

    @EasyExcelFiled(name = "原生注解-是否删除")
    @JSONField(name = "JSONField注解-是否删除")
    private boolean isDelete;
    }

#### 新增Excel 导入支持时间格式
    新增注解 @EasyFile 支持导出字符串、文件到mvc框架二进制流文件
    支持对象中含有对象
    支持对象中含有集合导出自动合并集合
