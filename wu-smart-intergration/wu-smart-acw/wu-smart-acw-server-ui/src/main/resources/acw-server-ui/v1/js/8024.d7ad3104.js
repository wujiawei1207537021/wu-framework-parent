"use strict";(self.webpackChunklazy_ui=self.webpackChunklazy_ui||[]).push([[8024],{14111:function(e,t){t.Z={description:"系统管理",type:"1",icon:"Tools",name:"系统管理"}},28024:function(e,t,a){a.r(t),a.d(t,{default:function(){return p}});a(70560),a(98858),a(61318),a(33228);var n=a(66252),r=a(3577),o=a(49963),l=a(2262),u=a(14111);const i={class:"vu_content"},s=(e=>((0,n.dD)("data-v-9fdb2128"),e=e(),(0,n.Cn)(),e))((()=>(0,n._)("span",null,"添加请求头",-1))),d=["id","src"],c={class:"content-item"},m={data:()=>({description:"动态iframe",buttons:{search:{name:"查询"},add:{name:"添加"},edit:{name:"编辑"},del:{name:"删除"},dictionaryData:{name:"查看翻译详细数据",toPath:!0}},type:"1",icon:"Microphone",name:"动态iframe",parentMenu:u.Z})};var f=Object.assign(m,{__name:"DynamicIframe",setup(e){const t=(0,l.iH)([]),a=(e,t,a)=>{let n=a.src,r=new XMLHttpRequest;r.open("GET",n,!0),r.responseType="blob",r.setRequestHeader("Accept","*/*"),t.forEach((e=>{r.setRequestHeader(e[0],e[1])})),r.onreadystatechange=()=>{r.readyState===r.DONE&&(console.log("xhr.response",r.response),200===r.status&&(e.src=URL.createObjectURL(r.response)))},r.send()};(0,n.bv)((async()=>{}));return(e,l)=>{const u=(0,n.up)("el-input"),m=(0,n.up)("el-button"),f=(0,n.up)("el-empty"),p=(0,n.up)("el-form-item");return(0,n.wg)(),(0,n.iD)("div",i,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(t.value,(e=>((0,n.wg)(),(0,n.iD)("view",{key:e.id,class:"content-item"},[(0,n._)("span",null,"窗口"+(0,r.zw)(e.id),1),(0,n.Wm)(u,{modelValue:e.tilt,"onUpdate:modelValue":t=>e.tilt=t,placeholder:"窗口描述",clearable:""},null,8,["modelValue","onUpdate:modelValue"]),(0,n._)("div",null,[s,(0,n.Wm)(m,{onClick:t=>(e=>{let t=e.headers;console.log("当前iframe请求头"+t),t.push({name:"",value:""})})(e),type:"primary",style:{float:"right"}},{default:(0,n.w5)((()=>[(0,n.Uk)("增加 ")])),_:2},1032,["onClick"]),(0,n.wy)((0,n.Wm)(f,null,null,512),[[o.F8,0===e.headers.length]]),((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(e.headers,((t,r)=>((0,n.wg)(),(0,n.j4)(p,{key:r},{default:(0,n.w5)((()=>[(0,n.Wm)(u,{modelValue:e.headers[r].name,"onUpdate:modelValue":t=>e.headers[r].name=t,placeholder:"请求头key"},null,8,["modelValue","onUpdate:modelValue"]),(0,n.Wm)(u,{modelValue:e.headers[r].value,"onUpdate:modelValue":t=>e.headers[r].value=t,placeholder:""},null,8,["modelValue","onUpdate:modelValue"]),(0,n.Wm)(m,{onClick:t=>((e,t)=>{let n=e.headers;console.log("当前iframe请求头"+n),n.splice(t,1);const r=document.querySelector("#"+e.dynamicIframeId);a(r,[["token1","header"]],e)})(e,r),type:"danger",style:{float:"right"}},{default:(0,n.w5)((()=>[(0,n.Uk)("删除 ")])),_:2},1032,["onClick"])])),_:2},1024)))),128))]),(0,n.Wm)(u,{modelValue:e.src,"onUpdate:modelValue":t=>e.src=t,placeholder:"窗口地址",clearable:""},null,8,["modelValue","onUpdate:modelValue"]),(0,n._)("iframe",{id:e.dynamicIframeId,src:e.src,style:{height:"100%",width:"100%",margin:"0",border:"0"},frameborder:"0",scrolling:"no"},"\n            ",8,d),(0,n.Wm)(m,{onClick:t=>(e=>{const t=document.querySelector("#"+e.dynamicIframeId);a(t,[["token1","header"]],e)})(e)},{default:(0,n.w5)((()=>[(0,n.Uk)(" 预览 ")])),_:2},1032,["onClick"])])))),128)),(0,n._)("view",c,[(0,n.Wm)(m,{type:"primary",onClick:l[0]||(l[0]=e=>(console.log("111"),void t.value.push({id:"默认ID",src:"https://www.baidu.com",tilt:"默认表头",headers:[],dynamicIframeId:"dynamicIframeId_2"})))},{default:(0,n.w5)((()=>[(0,n.Uk)("添加iframe ")])),_:1})])])}}});var p=(0,a(48118).Z)(f,[["__scopeId","data-v-9fdb2128"]])},50926:function(e,t,a){var n=a(23043),r=a(69985),o=a(6648),l=a(44201)("toStringTag"),u=Object,i="Arguments"===o(function(){return arguments}());e.exports=n?o:function(e){var t,a,n;return void 0===e?"Undefined":null===e?"Null":"string"==typeof(a=function(e,t){try{return e[t]}catch(e){}}(t=u(e),l))?a:i?o(t):"Object"===(n=o(t))&&r(t.callee)?"Arguments":n}},62148:function(e,t,a){var n=a(98702),r=a(72560);e.exports=function(e,t,a){return a.get&&n(a.get,t,{getter:!0}),a.set&&n(a.set,t,{setter:!0}),r.f(e,t,a)}},23043:function(e,t,a){var n={};n[a(44201)("toStringTag")]="z",e.exports="[object z]"===String(n)},34327:function(e,t,a){var n=a(50926),r=String;e.exports=function(e){if("Symbol"===n(e))throw new TypeError("Cannot convert a Symbol value to a string");return r(e)}},21500:function(e){var t=TypeError;e.exports=function(e,a){if(e<a)throw new t("Not enough arguments");return e}},98858:function(e,t,a){var n=a(11880),r=a(68844),o=a(34327),l=a(21500),u=URLSearchParams,i=u.prototype,s=r(i.append),d=r(i.delete),c=r(i.forEach),m=r([].push),f=new u("a=1&a=2&b=3");f.delete("a",1),f.delete("b",void 0),f+""!="a=2"&&n(i,"delete",(function(e){var t=arguments.length,a=t<2?void 0:arguments[1];if(t&&void 0===a)return d(this,e);var n=[];c(this,(function(e,t){m(n,{key:t,value:e})})),l(t,1);for(var r,u=o(e),i=o(a),f=0,p=0,h=!1,v=n.length;f<v;)r=n[f++],h||r.key===u?(h=!0,d(this,r.key)):p++;for(;p<v;)(r=n[p++]).key===u&&r.value===i||s(this,r.key,r.value)}),{enumerable:!0,unsafe:!0})},61318:function(e,t,a){var n=a(11880),r=a(68844),o=a(34327),l=a(21500),u=URLSearchParams,i=u.prototype,s=r(i.getAll),d=r(i.has),c=new u("a=1");!c.has("a",2)&&c.has("a",void 0)||n(i,"has",(function(e){var t=arguments.length,a=t<2?void 0:arguments[1];if(t&&void 0===a)return d(this,e);var n=s(this,e);l(t,1);for(var r=o(a),u=0;u<n.length;)if(n[u++]===r)return!0;return!1}),{enumerable:!0,unsafe:!0})},33228:function(e,t,a){var n=a(67697),r=a(68844),o=a(62148),l=URLSearchParams.prototype,u=r(l.forEach);n&&!("size"in l)&&o(l,"size",{get:function(){var e=0;return u(this,(function(){e++})),e},configurable:!0,enumerable:!0})}}]);