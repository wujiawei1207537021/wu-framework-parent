"use strict";(self.webpackChunklazy_ui=self.webpackChunklazy_ui||[]).push([[1450],{41450:function(e,l,a){a.r(l),a.d(l,{default:function(){return o}});var u=a(66252),t=a(2262);var o={__name:"UsersEditRoute",props:{showDialog:{type:Boolean,default:!0},title:{type:String,default:"添加"},rowData:{type:Object,default:null}},emits:["closeDialog"],setup(e,{emit:l}){const a={name:[{required:!0,message:"请输入用户名",trigger:"blur"}],userName:[{required:!0,message:"请输入账户",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}],role:[{required:!0,message:"请选择角色",trigger:"change"}]},o=e,r=l,{title:d,rowData:s}=(0,t.BK)(o),m=()=>{r("closeDialog",!1)},i=(0,t.iH)(null),n=(0,t.qj)({name:"",userName:"",password:"",role:"",status:1}),{userName:p,name:w,password:c,role:f,status:b}=(0,t.BK)(n),v=(0,t.iH)([]);s.value&&(w.value=s.value.name,p.value=s.value.userName,c.value=s.value.password,f.value=s.value.role,b.value=s.value.status);(async()=>{const{code:e,data:l}=await VE_API.system.roleList({page:1,size:10},{Global:!1});if("0"===e){const{list:e}=l;v.value=e}})();return(e,l)=>{const o=(0,u.up)("el-input"),r=(0,u.up)("el-form-item"),g=(0,u.up)("el-option"),_=(0,u.up)("el-select"),V=(0,u.up)("el-radio-button"),y=(0,u.up)("el-radio-group"),U=(0,u.up)("el-button"),W=(0,u.up)("el-form");return(0,u.wg)(),(0,u.j4)(W,{model:n,ref_key:"formRef",ref:i,rules:a,"label-width":"80px",inline:!1},{default:(0,u.w5)((()=>[(0,u.Wm)(r,{label:"账号",prop:"name"},{default:(0,u.w5)((()=>[(0,u.Wm)(o,{modelValue:(0,t.SU)(w),"onUpdate:modelValue":l[0]||(l[0]=e=>(0,t.dq)(w)?w.value=e:null),placeholder:"",clearable:""},null,8,["modelValue"])])),_:1}),(0,u.Wm)(r,{label:"用户名",prop:"userName"},{default:(0,u.w5)((()=>[(0,u.Wm)(o,{modelValue:(0,t.SU)(p),"onUpdate:modelValue":l[1]||(l[1]=e=>(0,t.dq)(p)?p.value=e:null),placeholder:"",clearable:""},null,8,["modelValue"])])),_:1}),(0,u.Wm)(r,{label:"密码",prop:"password"},{default:(0,u.w5)((()=>[(0,u.Wm)(o,{modelValue:(0,t.SU)(c),"onUpdate:modelValue":l[2]||(l[2]=e=>(0,t.dq)(c)?c.value=e:null),"show-password":"",placeholder:"",clearable:""},null,8,["modelValue"])])),_:1}),(0,u.Wm)(r,{label:"角色",prop:"role"},{default:(0,u.w5)((()=>[(0,u.Wm)(_,{style:{width:"100%"},modelValue:(0,t.SU)(f),"onUpdate:modelValue":l[3]||(l[3]=e=>(0,t.dq)(f)?f.value=e:null),placeholder:"",clearable:""},{default:(0,u.w5)((()=>[((0,u.wg)(!0),(0,u.iD)(u.HY,null,(0,u.Ko)(v.value,(e=>((0,u.wg)(),(0,u.j4)(g,{key:e.id,label:e.name,value:e.id,disabled:0==e.status},null,8,["label","value","disabled"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,u.Wm)(r,{label:"状态"},{default:(0,u.w5)((()=>[(0,u.Wm)(y,{modelValue:(0,t.SU)(b),"onUpdate:modelValue":l[4]||(l[4]=e=>(0,t.dq)(b)?b.value=e:null)},{default:(0,u.w5)((()=>[(0,u.Wm)(V,{label:1},{default:(0,u.w5)((()=>[(0,u.Uk)("启用")])),_:1}),(0,u.Wm)(V,{label:0},{default:(0,u.w5)((()=>[(0,u.Uk)("停用")])),_:1})])),_:1},8,["modelValue"])])),_:1}),(0,u.Wm)(r,null,{default:(0,u.w5)((()=>[(0,u.Wm)(U,{onClick:l[5]||(l[5]=e=>m())},{default:(0,u.w5)((()=>[(0,u.Uk)("取消")])),_:1}),(0,u.Wm)(U,{type:"primary",onClick:l[6]||(l[6]=e=>{i.value.validate((async e=>{if(!e)return console.log("error submit!!"),!1;{let e;e="添加"===d.value?await VE_API.system.userAdd(n):await VE_API.system.userEdit({id:s.value.id,...n});const{code:l}=e;"0"===l&&m()}}))})},{default:(0,u.w5)((()=>[(0,u.Uk)("确定")])),_:1})])),_:1})])),_:1},8,["model"])}}}}}]);