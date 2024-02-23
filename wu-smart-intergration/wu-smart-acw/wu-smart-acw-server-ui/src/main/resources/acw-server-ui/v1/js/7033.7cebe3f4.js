"use strict";(self.webpackChunklazy_ui=self.webpackChunklazy_ui||[]).push([[7033],{67033:function(e,l,a){a.r(l),a.d(l,{default:function(){return n}});var u=a(66252),t=a(2262);var n={__name:"DatabaseSchemaDeriveView",props:{showDialog:{type:Boolean,default:!0},title:{type:String,default:"添加"},rowData:{type:Object,default:null}},emits:["closeDialog"],setup(e,{emit:l}){const a={schemaName:[{required:!0,message:"请输入数据库库名",trigger:"blur"}],instanceId:[{required:!0,message:"请输入数据库服务器",trigger:"blur"}],tableName:[{required:!0,message:"请输入字符集",trigger:"blur"}],columnName:[{required:!1,message:"请输入排序规则",trigger:"blur"}]},n=e,m=l,{title:o}=(0,t.BK)(n),s=()=>{m("closeDialog",!1)},d=(0,t.iH)(null),i=(0,t.iH)(null),r=(0,t.qj)({schemaName:"",instanceId:"",tableName:"",columnName:""}),{schemaName:c,instanceId:b,tableName:p,columnName:v}=(0,t.BK)(r),w=(0,t.iH)(null),f=(0,t.iH)(null),g=(0,t.iH)(null),h=async()=>{let e=await VE_API.system.findDatabaseTableColumnList({instanceId:r.instanceId,schemaName:r.schemaName,tableName:r.tableName});const{code:l,data:a}=e;0===l&&(a.map((e=>{e.label=e.columnName,e.value=e.columnName})),i.value=a||[])},y=async()=>{let e;if(null==b.value)return;e=await VE_API.system.tableList({instanceId:r.instanceId,schemaName:r.schemaName});const{code:l,data:a}=e;0===l&&(a.map((e=>{e.label=e.tableName,e.value=e.tableName})),g.value=a)},N=async()=>{let e=await VE_API.system.schemaList({instanceId:r.instanceId});const{code:l}=e;0===l&&(e.data.map((e=>{e.label=e.schemaName,e.value=e.schemaName})),f.value=e.data?e.data:[])};(0,u.bv)((async()=>{VE_API.system.databaseInstanceList().then((e=>{e.data.map((e=>{e.label=e.instanceName,e.value=e.id})),w.value=e.data?e.data:[]}))}));return(l,n)=>{const m=(0,u.up)("el-option"),_=(0,u.up)("el-select"),V=(0,u.up)("el-form-item"),I=(0,u.up)("el-form"),k=(0,u.up)("el-button"),D=(0,u.up)("el-dialog");return(0,u.wg)(),(0,u.j4)(D,{title:(0,t.SU)(o),"append-to-body":"","destroy-on-close":"","model-value":e.showDialog,onClose:n[6]||(n[6]=e=>s())},{footer:(0,u.w5)((()=>[(0,u._)("span",null,[(0,u.Wm)(k,{onClick:n[4]||(n[4]=e=>s())},{default:(0,u.w5)((()=>[(0,u.Uk)("取消")])),_:1}),(0,u.Wm)(k,{type:"primary",onClick:n[5]||(n[5]=e=>{d.value.validate((async e=>{if(!e)return console.log("error submit!!"),!1;{let e=await VE_API.system.schemaDeriveView(r);const{code:l}=e;0===l&&s()}}))})},{default:(0,u.w5)((()=>[(0,u.Uk)("确定")])),_:1})])])),default:(0,u.w5)((()=>[(0,u.Wm)(I,{model:r,ref_key:"formRef",ref:d,rules:a,"label-width":"80px",inline:!1},{default:(0,u.w5)((()=>[(0,u.Wm)(V,{label:"数据库实例",prop:"instanceId"},{default:(0,u.w5)((()=>[(0,u.Wm)(_,{modelValue:(0,t.SU)(b),"onUpdate:modelValue":n[0]||(n[0]=e=>(0,t.dq)(b)?b.value=e:null),placeholder:"数据库实例",onChange:N},{default:(0,u.w5)((()=>[((0,u.wg)(!0),(0,u.iD)(u.HY,null,(0,u.Ko)(w.value,(e=>((0,u.wg)(),(0,u.j4)(m,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,u.Wm)(V,{label:"数据库库名",prop:"schemaName"},{default:(0,u.w5)((()=>[(0,u.Wm)(_,{clearable:"",modelValue:(0,t.SU)(c),"onUpdate:modelValue":n[1]||(n[1]=e=>(0,t.dq)(c)?c.value=e:null),placeholder:"数据库名",onChange:y,filterable:""},{default:(0,u.w5)((()=>[((0,u.wg)(!0),(0,u.iD)(u.HY,null,(0,u.Ko)(f.value,(e=>((0,u.wg)(),(0,u.j4)(m,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,u.Wm)(V,{label:"数据库表名",prop:"sortingRules"},{default:(0,u.w5)((()=>[(0,u.Wm)(_,{clearable:"",modelValue:(0,t.SU)(p),"onUpdate:modelValue":n[2]||(n[2]=e=>(0,t.dq)(p)?p.value=e:null),placeholder:"数据库表名",onChange:h,filterable:""},{default:(0,u.w5)((()=>[((0,u.wg)(!0),(0,u.iD)(u.HY,null,(0,u.Ko)(g.value,(e=>((0,u.wg)(),(0,u.j4)(m,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,u.Wm)(V,{label:"字段名称",prop:"columnName"},{default:(0,u.w5)((()=>[(0,u.Wm)(_,{modelValue:(0,t.SU)(v),"onUpdate:modelValue":n[3]||(n[3]=e=>(0,t.dq)(v)?v.value=e:null),placeholder:"字段名称",filterable:""},{default:(0,u.w5)((()=>[((0,u.wg)(!0),(0,u.iD)(u.HY,null,(0,u.Ko)(i.value,(e=>((0,u.wg)(),(0,u.j4)(m,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1})])),_:1},8,["model"])])),_:1},8,["title","model-value"])}}}}}]);