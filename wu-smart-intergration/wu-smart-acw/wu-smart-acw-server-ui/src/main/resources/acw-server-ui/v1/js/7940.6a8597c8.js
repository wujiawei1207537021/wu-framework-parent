"use strict";(self.webpackChunklazy_ui=self.webpackChunklazy_ui||[]).push([[7940,289],{28049:function(e,a,l){l.r(a),l.d(a,{default:function(){return c}});l(70560);var r=l(66252),s=l(2262),u=l(49963),d=l(32393),o=l(85729),t=l(33907),p=l(22201);const n={class:"ve_container"},m=(e=>((0,r.dD)("data-v-21b59d3e"),e=e(),(0,r.Cn)(),e))((()=>(0,r._)("h1",null,"Lazy-UI",-1)));var i={__name:"Register",setup(e){const a={username:[{required:!0,message:"请输入用户名",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}],checkPassword:[{required:!0,message:"密码确认",trigger:"blur"}]},{proxy:l}=(0,r.FN)(),i=(0,t.oR)(),c=(0,p.tv)(),w=(0,s.qj)({username:"",password:"",checkPassword:""}),{username:f,password:_,checkPassword:h}=(0,s.BK)(w),y=(0,s.iH)(null),g=(0,s.iH)(!1);sessionStorage.clear(),i.dispatch(`app/${d.L4}`,""),c.options.isAddDynamicMenuRoutes=!1;const v=()=>{w.password===w.checkPassword?y.value.validate((async e=>{if(e){const e=await VE_API.system.register(w);0===e.code&&(i.dispatch(`app/${d.L4}`,e.data),i.dispatch(`app/${d.TR}`,w.username),g.value=!0,c.push({name:"AppMain"}))}})):l.$message({type:"error",message:"请确认，两次输入密码是否一致!"})};return(e,l)=>{const d=(0,r.up)("router-link"),t=(0,r.up)("Avatar"),p=(0,r.up)("el-icon"),i=(0,r.up)("el-input"),c=(0,r.up)("el-form-item"),W=(0,r.up)("Key"),b=(0,r.up)("el-button"),k=(0,r.up)("el-form"),U=(0,r.up)("el-card");return(0,r.wg)(),(0,r.j4)((0,s.SU)(o.default),null,{default:(0,r.w5)((()=>[(0,r._)("div",n,[(0,r.Wm)(U,{"body-style":{background:"rgba(0,0,0,0.15)"}},{default:(0,r.w5)((()=>[(0,r.Wm)(d,{style:{float:"right"},to:{path:"login"}},{default:(0,r.w5)((()=>[(0,r.Uk)("登录")])),_:1}),m,(0,r.Wm)(u.uT,{name:"el-fade-in-linear"},{default:(0,r.w5)((()=>[(0,r.wy)((0,r.Wm)(k,{model:w,rules:a,class:"ve_form",ref_key:"ref_form",ref:y,inline:!1,onKeyup:(0,u.D2)(v,["enter"])},{default:(0,r.w5)((()=>[(0,r.Wm)(c,{prop:"username",label:"注册账号"},{default:(0,r.w5)((()=>[(0,r.Wm)(i,{modelValue:(0,s.SU)(f),"onUpdate:modelValue":l[0]||(l[0]=e=>(0,s.dq)(f)?f.value=e:null),modelModifiers:{trim:!0},placeholder:"注册账号"},{prepend:(0,r.w5)((()=>[(0,r.Wm)(p,{size:20},{default:(0,r.w5)((()=>[(0,r.Wm)(t)])),_:1})])),_:1},8,["modelValue"])])),_:1}),(0,r.Wm)(c,{prop:"password",label:"账号密码"},{default:(0,r.w5)((()=>[(0,r.Wm)(i,{modelValue:(0,s.SU)(_),"onUpdate:modelValue":l[1]||(l[1]=e=>(0,s.dq)(_)?_.value=e:null),modelModifiers:{trim:!0},"show-password":"",placeholder:"账号密码"},{prepend:(0,r.w5)((()=>[(0,r.Wm)(p,{size:20},{default:(0,r.w5)((()=>[(0,r.Wm)(W)])),_:1})])),_:1},8,["modelValue"])])),_:1}),(0,r.Wm)(c,{prop:"checkPassword",label:"密码确认"},{default:(0,r.w5)((()=>[(0,r.Wm)(i,{modelValue:(0,s.SU)(h),"onUpdate:modelValue":l[2]||(l[2]=e=>(0,s.dq)(h)?h.value=e:null),modelModifiers:{trim:!0},"show-password":"",placeholder:"密码确认"},{prepend:(0,r.w5)((()=>[(0,r.Wm)(p,{size:20},{default:(0,r.w5)((()=>[(0,r.Wm)(W)])),_:1})])),_:1},8,["modelValue"])])),_:1}),(0,r.Wm)(c,null,{default:(0,r.w5)((()=>[(0,r.Wm)(b,{class:"ve_submit",type:"primary",onClick:v},{default:(0,r.w5)((()=>[(0,r.Uk)(" 注册 ")])),_:1})])),_:1})])),_:1},8,["model","onKeyup"]),[[u.F8,!g.value]])])),_:1})])),_:1},8,["body-style"])])])),_:1})}}};var c=(0,l(48118).Z)(i,[["__scopeId","data-v-21b59d3e"]])}}]);