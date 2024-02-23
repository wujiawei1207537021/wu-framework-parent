"use strict";(self.webpackChunklazy_ui=self.webpackChunklazy_ui||[]).push([[7776],{60269:function(e,t){t.Z={description:"tts",type:"1",icon:"Service",name:"tts控制台"}},67776:function(e,t,a){a.r(t),a.d(t,{default:function(){return d}});a(98858),a(61318),a(33228);var n=a(66252),r=a(2262),l=a(3577),o=a(60269);const u={class:"ve_home"},i={key:0},s=["src"],c={data:()=>({description:"TTS汉字转换语音",buttons:{search:{name:"查询"}},type:"1",icon:"Pointer",name:"TTS汉字转换语音",parentMenu:o.Z})};var d=Object.assign(c,{__name:"TtsChineseCharactersTimbre2Voice",setup(e){const t=(0,r.iH)(null),a=(0,r.iH)(null),o=(0,r.qj)({text:"欢迎来到TTS测试",timbreCode:"",size:10,current:1,total:0}),c=(0,r.iH)([]),{text:d,timbreCode:f}=(0,r.BK)(o);return(0,n.bv)((async()=>{await(async()=>{let e=await VE_API.system.ttsTimbreFindList();e.data.map((e=>{e.label=e.name,e.value=e.code})),c.value=e.data?e.data:[]})()})),(e,v)=>{const m=(0,n.up)("el-input"),p=(0,n.up)("el-form-item"),b=(0,n.up)("el-option"),h=(0,n.up)("el-select"),y=(0,n.up)("el-button"),g=(0,n.up)("el-form");return(0,n.wg)(),(0,n.iD)("div",u,[(0,n.Wm)(g,{ref_key:"queryForm",ref:t,inline:!0,model:o},{default:(0,n.w5)((()=>[(0,n.Wm)(p,{label:"汉字",prop:"text"},{default:(0,n.w5)((()=>[(0,n.Wm)(m,{clearable:"",modelValue:(0,r.SU)(d),"onUpdate:modelValue":v[0]||(v[0]=e=>(0,r.dq)(d)?d.value=e:null),placeholder:"汉字"},null,8,["modelValue"])])),_:1}),(0,n.Wm)(p,{label:"音色",property:"timbreCode"},{default:(0,n.w5)((()=>[(0,n.Wm)(h,{modelValue:(0,r.SU)(f),"onUpdate:modelValue":v[1]||(v[1]=e=>(0,r.dq)(f)?f.value=e:null),placeholder:"音色",filterable:"",clearable:""},{default:(0,n.w5)((()=>[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(c.value,(e=>((0,n.wg)(),(0,n.j4)(b,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,n.Wm)(p,null,{default:(0,n.w5)((()=>[(0,n.Wm)(y,{type:"primary",onClick:v[2]||(v[2]=e=>(async()=>{a.value&&window.URL.revokeObjectURL(a.value);let e=await VE_API.system.ttsChineseCharactersTimbreTextToBytes(o,{responseType:"blob"});a.value=window.URL.createObjectURL(new Blob([e.data]))})())},{default:(0,n.w5)((()=>[(0,n.Uk)((0,l.zw)(e.buttons.search.name),1)])),_:1})])),_:1})])),_:1},8,["model"]),a.value?((0,n.wg)(),(0,n.iD)("div",i,[(0,n._)("audio",{ref:"audio",src:a.value,controls:"controls"},null,8,s)])):(0,n.kq)("",!0)])}}})},50926:function(e,t,a){var n=a(23043),r=a(69985),l=a(6648),o=a(44201)("toStringTag"),u=Object,i="Arguments"===l(function(){return arguments}());e.exports=n?l:function(e){var t,a,n;return void 0===e?"Undefined":null===e?"Null":"string"==typeof(a=function(e,t){try{return e[t]}catch(e){}}(t=u(e),o))?a:i?l(t):"Object"===(n=l(t))&&r(t.callee)?"Arguments":n}},62148:function(e,t,a){var n=a(98702),r=a(72560);e.exports=function(e,t,a){return a.get&&n(a.get,t,{getter:!0}),a.set&&n(a.set,t,{setter:!0}),r.f(e,t,a)}},23043:function(e,t,a){var n={};n[a(44201)("toStringTag")]="z",e.exports="[object z]"===String(n)},34327:function(e,t,a){var n=a(50926),r=String;e.exports=function(e){if("Symbol"===n(e))throw new TypeError("Cannot convert a Symbol value to a string");return r(e)}},21500:function(e){var t=TypeError;e.exports=function(e,a){if(e<a)throw new t("Not enough arguments");return e}},98858:function(e,t,a){var n=a(11880),r=a(68844),l=a(34327),o=a(21500),u=URLSearchParams,i=u.prototype,s=r(i.append),c=r(i.delete),d=r(i.forEach),f=r([].push),v=new u("a=1&a=2&b=3");v.delete("a",1),v.delete("b",void 0),v+""!="a=2"&&n(i,"delete",(function(e){var t=arguments.length,a=t<2?void 0:arguments[1];if(t&&void 0===a)return c(this,e);var n=[];d(this,(function(e,t){f(n,{key:t,value:e})})),o(t,1);for(var r,u=l(e),i=l(a),v=0,m=0,p=!1,b=n.length;v<b;)r=n[v++],p||r.key===u?(p=!0,c(this,r.key)):m++;for(;m<b;)(r=n[m++]).key===u&&r.value===i||s(this,r.key,r.value)}),{enumerable:!0,unsafe:!0})},61318:function(e,t,a){var n=a(11880),r=a(68844),l=a(34327),o=a(21500),u=URLSearchParams,i=u.prototype,s=r(i.getAll),c=r(i.has),d=new u("a=1");!d.has("a",2)&&d.has("a",void 0)||n(i,"has",(function(e){var t=arguments.length,a=t<2?void 0:arguments[1];if(t&&void 0===a)return c(this,e);var n=s(this,e);o(t,1);for(var r=l(a),u=0;u<n.length;)if(n[u++]===r)return!0;return!1}),{enumerable:!0,unsafe:!0})},33228:function(e,t,a){var n=a(67697),r=a(68844),l=a(62148),o=URLSearchParams.prototype,u=r(o.forEach);n&&!("size"in o)&&l(o,"size",{get:function(){var e=0;return u(this,(function(){e++})),e},configurable:!0,enumerable:!0})}}]);