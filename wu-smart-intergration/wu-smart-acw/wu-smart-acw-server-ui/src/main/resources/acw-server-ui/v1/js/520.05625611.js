"use strict";(self.webpackChunklazy_ui=self.webpackChunklazy_ui||[]).push([[520],{40520:function(e,l,a){a.r(l),a.d(l,{default:function(){return d}});var t=a(66252),u=a(2262),o=a(3577);const n={style:{float:"left"}},s={style:{float:"right",color:"var(--el-text-color-secondary)","font-size":"13px"}};var d={__name:"AcwTableAssociationRelationAnalysisSchemaEdit",props:{showDialog:{type:Boolean,default:!0},title:{type:String,default:"添加"},rowData:{type:Object,default:null}},emits:["closeDialog"],setup(e,{emit:l}){const a={databaseSchemaId:[{required:!0,message:"请输入数据库库名",trigger:"blur"}],instanceId:[{required:!0,message:"请输入数据库服务器",trigger:"blur"}]},d=e,i=l,{title:m,rowData:r}=(0,u.BK)(d),c=()=>{i("closeDialog",!1)},p=(0,u.iH)(null),v=(0,u.qj)({schema:"",instanceId:"",relationThreshold:99,ignoreFieldList:[]}),{schema:b,instanceId:f,relationThreshold:h,ignoreFieldList:w}=(0,u.BK)(v),g=(0,u.iH)(null),y=(0,u.iH)(null),_=(0,u.iH)([]);r.value&&(b.value=r.value.schema,f.value=r.value.instanceId,h.value=r.value.relationThreshold),(0,t.bv)((async()=>{I()}));const V=async()=>{_.value=[];let e=await VE_API.system.findInstanceSchemaColumnList({instanceId:f.value,schemaName:b.value});const{code:l,data:a}=e;0===l&&(a.map((e=>{e.label=e.columnName,e.value=e.columnName})),_.value=a||[])},I=()=>{VE_API.system.databaseInstanceList().then((e=>{e.data.map((e=>{e.label=e.instanceName,e.value=e.id})),g.value=e.data?e.data:[]}))};return(l,d)=>{const i=(0,t.up)("el-option"),r=(0,t.up)("el-select"),I=(0,t.up)("el-form-item"),k=(0,t.up)("el-input"),U=(0,t.up)("el-form"),W=(0,t.up)("el-button"),S=(0,t.up)("el-dialog");return(0,t.wg)(),(0,t.j4)(S,{title:(0,u.SU)(m),"append-to-body":"","destroy-on-close":"","model-value":e.showDialog,onClose:d[7]||(d[7]=e=>c())},{footer:(0,t.w5)((()=>[(0,t._)("span",null,[(0,t.Wm)(W,{onClick:d[5]||(d[5]=e=>c())},{default:(0,t.w5)((()=>[(0,t.Uk)("取消")])),_:1}),(0,t.Wm)(W,{type:"primary",onClick:d[6]||(d[6]=e=>{p.value.validate((async e=>{if(!e)return console.log("error submit!!"),!1;{let e;console.log(v),e=await VE_API.system.acwTableAssociationRelationAnalysisSchema(v);const{code:l}=e;0===l&&c()}}))})},{default:(0,t.w5)((()=>[(0,t.Uk)("确定")])),_:1})])])),default:(0,t.w5)((()=>[(0,t.Wm)(U,{model:v,ref_key:"formRef",ref:p,rules:a,"label-width":"80px",inline:!1},{default:(0,t.w5)((()=>[(0,t.Wm)(I,{label:"数据库实例",prop:"instanceId"},{default:(0,t.w5)((()=>[(0,t.Wm)(r,{modelValue:(0,u.SU)(f),"onUpdate:modelValue":d[0]||(d[0]=e=>(0,u.dq)(f)?f.value=e:null),placeholder:"数据库实例",filterable:"",onChange:d[1]||(d[1]=e=>((e=null)=>{console.log(e),VE_API.system.schemaList({instanceId:f.value}).then((e=>{e.data.map((e=>{e.label=e.schemaName,e.value=e.schemaName})),y.value=e.data?e.data:[]}))})(e))},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(g.value,(e=>((0,t.wg)(),(0,t.j4)(i,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,t.Wm)(I,{label:"数据库库名",prop:"schemaName"},{default:(0,t.w5)((()=>[(0,t.Wm)(r,{modelValue:(0,u.SU)(b),"onUpdate:modelValue":d[2]||(d[2]=e=>(0,u.dq)(b)?b.value=e:null),filterable:"",placeholder:"数据库实例",onChange:V},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(y.value,(e=>((0,t.wg)(),(0,t.j4)(i,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,t.Wm)(I,{label:"忽略解析字段",prop:"ignoreFieldList"},{default:(0,t.w5)((()=>[(0,t.Wm)(r,{modelValue:(0,u.SU)(w),"onUpdate:modelValue":d[3]||(d[3]=e=>(0,u.dq)(w)?w.value=e:null),filterable:"",multiple:"",placeholder:"忽略解析字段"},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(_.value,(e=>((0,t.wg)(),(0,t.j4)(i,{key:e.value,label:e.label,value:e.value},{default:(0,t.w5)((()=>[(0,t._)("span",n,(0,o.zw)(e.label)+"【"+(0,o.zw)(e.columnComment)+"】",1),(0,t._)("span",s,(0,o.zw)(e.tableName),1)])),_:2},1032,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,t.Wm)(I,{label:"解析阀值",prop:"relationThreshold"},{default:(0,t.w5)((()=>[(0,t.Wm)(k,{modelValue:(0,u.SU)(h),"onUpdate:modelValue":d[4]||(d[4]=e=>(0,u.dq)(h)?h.value=e:null),placeholder:"500",clearable:""},null,8,["modelValue"])])),_:1})])),_:1},8,["model"])])),_:1},8,["title","model-value"])}}}}}]);