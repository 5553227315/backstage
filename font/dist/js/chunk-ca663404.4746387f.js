(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-ca663404"],{1276:function(e,t,a){"use strict";var i=a("2ba4"),n=a("c65b"),l=a("e330"),r=a("d784"),o=a("44e7"),c=a("825a"),s=a("1d80"),m=a("4840"),u=a("8aa5"),p=a("50c4"),d=a("577e"),h=a("dc4a"),g=a("4dae"),f=a("14c3"),v=a("9263"),x=a("9f7f"),y=a("d039"),b=x.UNSUPPORTED_Y,w=4294967295,k=Math.min,F=[].push,S=l(/./.exec),C=l(F),N=l("".slice),T=!y((function(){var e=/(?:)/,t=e.exec;e.exec=function(){return t.apply(this,arguments)};var a="ab".split(e);return 2!==a.length||"a"!==a[0]||"b"!==a[1]}));r("split",(function(e,t,a){var l;return l="c"=="abbc".split(/(b)*/)[1]||4!="test".split(/(?:)/,-1).length||2!="ab".split(/(?:ab)*/).length||4!=".".split(/(.?)(.?)/).length||".".split(/()()/).length>1||"".split(/.?/).length?function(e,a){var l=d(s(this)),r=void 0===a?w:a>>>0;if(0===r)return[];if(void 0===e)return[l];if(!o(e))return n(t,l,e,r);var c,m,u,p=[],h=(e.ignoreCase?"i":"")+(e.multiline?"m":"")+(e.unicode?"u":"")+(e.sticky?"y":""),f=0,x=new RegExp(e.source,h+"g");while(c=n(v,x,l)){if(m=x.lastIndex,m>f&&(C(p,N(l,f,c.index)),c.length>1&&c.index<l.length&&i(F,p,g(c,1)),u=c[0].length,f=m,p.length>=r))break;x.lastIndex===c.index&&x.lastIndex++}return f===l.length?!u&&S(x,"")||C(p,""):C(p,N(l,f)),p.length>r?g(p,0,r):p}:"0".split(void 0,0).length?function(e,a){return void 0===e&&0===a?[]:n(t,this,e,a)}:t,[function(t,a){var i=s(this),r=void 0==t?void 0:h(t,e);return r?n(r,t,i,a):n(l,d(i),t,a)},function(e,i){var n=c(this),r=d(e),o=a(l,n,r,i,l!==t);if(o.done)return o.value;var s=m(n,RegExp),h=n.unicode,g=(n.ignoreCase?"i":"")+(n.multiline?"m":"")+(n.unicode?"u":"")+(b?"g":"y"),v=new s(b?"^(?:"+n.source+")":n,g),x=void 0===i?w:i>>>0;if(0===x)return[];if(0===r.length)return null===f(v,r)?[r]:[];var y=0,F=0,S=[];while(F<r.length){v.lastIndex=b?0:F;var T,q=f(v,b?N(r,F):r);if(null===q||(T=k(p(v.lastIndex+(b?F:0)),r.length))===y)F=u(r,F,h);else{if(C(S,N(r,y,F)),S.length===x)return S;for(var R=1;R<=q.length-1;R++)if(C(S,q[R]),S.length===x)return S;F=y=T}}return C(S,N(r,y)),S}]}),!T,b)},"14c3":function(e,t,a){var i=a("da84"),n=a("c65b"),l=a("825a"),r=a("1626"),o=a("c6b6"),c=a("9263"),s=i.TypeError;e.exports=function(e,t){var a=e.exec;if(r(a)){var i=n(a,e,t);return null!==i&&l(i),i}if("RegExp"===o(e))return n(c,e,t);throw s("RegExp#exec called on incompatible receiver")}},"25f0":function(e,t,a){"use strict";var i=a("e330"),n=a("5e77").PROPER,l=a("6eeb"),r=a("825a"),o=a("3a9b"),c=a("577e"),s=a("d039"),m=a("ad6d"),u="toString",p=RegExp.prototype,d=p[u],h=i(m),g=s((function(){return"/a/b"!=d.call({source:"a",flags:"b"})})),f=n&&d.name!=u;(g||f)&&l(RegExp.prototype,u,(function(){var e=r(this),t=c(e.source),a=e.flags,i=c(void 0===a&&o(p,e)&&!("flags"in p)?h(e):a);return"/"+t+"/"+i}),{unsafe:!0})},"2d5b":function(e,t,a){"use strict";var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[[a("el-skeleton",{staticStyle:{width:"100%",position:"absolute","z-index":"1","margin-top":"-55px","background-color":"white"},attrs:{loading:e.loading,animated:"",count:1}},[a("template",{slot:"template"},[a("div",{staticStyle:{display:"flex","flex-direction":"row"}},[a("el-skeleton-item",{staticStyle:{height:"20px",width:"90px",margin:"5px 0 5px 8px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"80px",margin:"5px 0 5px 8px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"30px",margin:"5px 0 5px 23px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"85px",margin:"5px 0 5px 10px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"70px",margin:"5px 0 5px 16px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"280px",margin:"5px 0 5px 32px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"150px",margin:"5px 0 5px 20px"},attrs:{variant:"text"}})],1)])],2)],[a("el-skeleton",{staticStyle:{width:"100%",position:"absolute","z-index":"1","margin-top":"-30px","background-color":"white"},attrs:{loading:e.loading,animated:"",count:4}},[a("template",{slot:"template"},[a("div",{staticStyle:{display:"flex","flex-direction":"row"}},[a("el-skeleton-item",{staticStyle:{height:"20px",width:"90px","margin-left":"8px","margin-top":"20px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"80px","margin-left":"8px","margin-top":"20px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"30px","margin-left":"23px","margin-top":"20px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"85px","margin-left":"10px","margin-top":"20px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"70px","margin-left":"16px","margin-top":"20px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"280px","margin-left":"32px","margin-top":"20px"},attrs:{variant:"text"}}),a("el-skeleton-item",{staticStyle:{height:"20px",width:"150px","margin-left":"20px","margin-top":"20px"},attrs:{variant:"text"}})],1)])],2)]],2)},n=[],l={name:"Skeleton",props:{loading:Boolean}},r=l,o=a("2877"),c=Object(o["a"])(r,i,n,!1,null,"bf6e324a",null);t["a"]=c.exports},"44e7":function(e,t,a){var i=a("861d"),n=a("c6b6"),l=a("b622"),r=l("match");e.exports=function(e){var t;return i(e)&&(void 0!==(t=e[r])?!!t:"RegExp"==n(e))}},"4dae":function(e,t,a){var i=a("da84"),n=a("23cb"),l=a("07fa"),r=a("8418"),o=i.Array,c=Math.max;e.exports=function(e,t,a){for(var i=l(e),s=n(t,i),m=n(void 0===a?i:a,i),u=o(c(m-s,0)),p=0;s<m;s++,p++)r(u,p,e[s]);return u.length=p,u}},8418:function(e,t,a){"use strict";var i=a("a04b"),n=a("9bf2"),l=a("5c6c");e.exports=function(e,t,a){var r=i(t);r in e?n.f(e,r,l(0,a)):e[r]=a}},"8aa5":function(e,t,a){"use strict";var i=a("6547").charAt;e.exports=function(e,t,a){return t+(a?i(e,t).length:1)}},d784:function(e,t,a){"use strict";a("ac1f");var i=a("e330"),n=a("6eeb"),l=a("9263"),r=a("d039"),o=a("b622"),c=a("9112"),s=o("species"),m=RegExp.prototype;e.exports=function(e,t,a,u){var p=o(e),d=!r((function(){var t={};return t[p]=function(){return 7},7!=""[e](t)})),h=d&&!r((function(){var t=!1,a=/a/;return"split"===e&&(a={},a.constructor={},a.constructor[s]=function(){return a},a.flags="",a[p]=/./[p]),a.exec=function(){return t=!0,null},a[p](""),!t}));if(!d||!h||a){var g=i(/./[p]),f=t(p,""[e],(function(e,t,a,n,r){var o=i(e),c=t.exec;return c===l||c===m.exec?d&&!r?{done:!0,value:g(t,a,n)}:{done:!0,value:o(a,t,n)}:{done:!1}}));n(String.prototype,e,f[0]),n(m,p,f[1])}u&&c(m[p],"sham",!0)}},f4f5:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticStyle:{margin:"10px 0"}},[a("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入影院名称","suffix-icon":"el-icon-search"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.load.apply(null,arguments)}},model:{value:e.cinemaName,callback:function(t){e.cinemaName=t},expression:"cinemaName"}}),a("el-input",{staticClass:"ml-10",staticStyle:{width:"200px"},attrs:{placeholder:"请输入影院地址","suffix-icon":"el-icon-search"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.load.apply(null,arguments)}},model:{value:e.cinemaAddress,callback:function(t){e.cinemaAddress=t},expression:"cinemaAddress"}}),a("el-button",{staticClass:"ml-10",attrs:{type:"primary"},on:{click:e.load}},[e._v("搜索")]),a("el-button",{staticClass:"ml-5",attrs:{type:"warning"},on:{click:e.reload}},[e._v("重置")])],1),a("div",{staticStyle:{margin:"10px 0"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.handleOpen("create",null)}}},[e._v("新增 "),a("i",{staticClass:"el-icon-circle-plus-outline"})]),a("el-dialog",{attrs:{title:e.dialogTitle,visible:e.addOrUpdataVisible,width:"50%"},on:{"update:visible":function(t){e.addOrUpdataVisible=t},close:e.closeDialog}},[a("el-form",{ref:"cinemaForm",attrs:{model:e.cinemaForm,"label-width":"130px",rules:e.rules}},[a("el-form-item",{attrs:{label:"影院品牌：",prop:"cinemaBrand"}},[a("el-input",{staticStyle:{width:"85%"},attrs:{autocomplete:"off",placeholder:"非连锁请填‘其他’","aria-required":"true"},model:{value:e.cinemaForm.cinemaBrand,callback:function(t){e.$set(e.cinemaForm,"cinemaBrand",t)},expression:"cinemaForm.cinemaBrand"}})],1),a("el-form-item",{attrs:{label:"影院名称：",prop:"cinemaName"}},[a("el-input",{staticStyle:{width:"85%"},attrs:{autocomplete:"off",placeholder:"请输入影院名称","aria-required":"true"},model:{value:e.cinemaForm.cinemaName,callback:function(t){e.$set(e.cinemaForm,"cinemaName",t)},expression:"cinemaForm.cinemaName"}})],1),a("el-form-item",{attrs:{label:"影院商圈：",prop:"cinemaBd"}},[a("el-input",{staticStyle:{width:"85%"},attrs:{autocomplete:"off",placeholder:"不靠近商圈，请填‘其他’","aria-required":"true"},model:{value:e.cinemaForm.cinemaBd,callback:function(t){e.$set(e.cinemaForm,"cinemaBd",t)},expression:"cinemaForm.cinemaBd"}})],1),a("div",{staticStyle:{display:"flex","flex-direction":"row",width:"87.5%"}},[a("el-form-item",{attrs:{label:"影院地址：",prop:"cinemaProvince"}},[a("el-select",{attrs:{placeholder:"请选择所在省份","aria-required":"true"},on:{change:e.changeProvinceName},model:{value:e.cinemaForm.cinemaProvince,callback:function(t){e.$set(e.cinemaForm,"cinemaProvince",t)},expression:"cinemaForm.cinemaProvince"}},e._l(e.provinceNameList,(function(e){return a("el-option",{key:e.name,attrs:{label:e.name,value:e.name}})})),1)],1),a("el-form-item",{staticClass:"ml-10",attrs:{"label-width":"auto",prop:"cinemaCity"}},[a("el-select",{attrs:{"aria-required":"true",placeholder:"请选择所在市区"},on:{change:e.changeCityName},model:{value:e.cinemaForm.cinemaCity,callback:function(t){e.$set(e.cinemaForm,"cinemaCity",t)},expression:"cinemaForm.cinemaCity"}},e._l(e.cityNameList,(function(e){return a("el-option",{key:e.name,attrs:{label:e.name,value:e.name}})})),1)],1),a("el-form-item",{staticClass:"ml-10",attrs:{"label-width":"auto",prop:"cinemaCounty"}},[a("el-select",{attrs:{"aria-required":"true",placeholder:"请选择所在县级"},on:{change:e.changeCountyName},model:{value:e.cinemaForm.cinemaCounty,callback:function(t){e.$set(e.cinemaForm,"cinemaCounty",t)},expression:"cinemaForm.cinemaCounty"}},e._l(e.countyNameList,(function(e){return a("el-option",{key:e.name,attrs:{label:e.name,value:e.name}})})),1)],1)],1),a("el-form-item",{attrs:{label:"详情地址：",prop:"cinemaDetailed"}},[a("el-input",{staticStyle:{width:"85%",display:"flex","align-items":"center","justify-content":"center"},attrs:{placeholder:""===this.cinemaForm.cinemaCounty?"请选择所在县级":"请输入下级详细地址",autocomplete:"off","aria-required":"true",disabled:""===this.cinemaForm.cinemaCounty},on:{change:e.getll},model:{value:e.cinemaForm.cinemaDetailed,callback:function(t){e.$set(e.cinemaForm,"cinemaDetailed",t)},expression:"cinemaForm.cinemaDetailed"}},[a("i",{class:""===this.cinemaForm.cinemaLocation?"el-input__icon el-icon-close":"el-input__icon el-icon-check",staticStyle:{"font-weight":"800"},style:"color:"+(""===this.cinemaForm.cinemaLocation?"#F56C6C":"#67C23A"),attrs:{slot:"suffix"},slot:"suffix"})])],1),a("el-form-item",{attrs:{label:"改签：",prop:"isRebook"}},[a("el-select",{staticStyle:{width:"85%"},attrs:{"aria-required":"true",placeholder:"请选择能否改签"},model:{value:e.cinemaForm.isRebook,callback:function(t){e.$set(e.cinemaForm,"isRebook",t)},expression:"cinemaForm.isRebook"}},[a("el-option",{attrs:{label:"可以",value:"1"}}),a("el-option",{attrs:{label:"不可以",value:"0"}})],1)],1),a("el-form-item",{attrs:{label:"退票：",prop:"isReticket"}},[a("el-select",{staticStyle:{width:"85%"},attrs:{"aria-required":"true",placeholder:"请选择能否退票"},model:{value:e.cinemaForm.isReticket,callback:function(t){e.$set(e.cinemaForm,"isReticket",t)},expression:"cinemaForm.isReticket"}},[a("el-option",{attrs:{label:"可以",value:"1"}}),a("el-option",{attrs:{label:"不可以",value:"0"}})],1)],1),a("el-form-item",{attrs:{label:"影厅类型：",prop:"cinemaType"}},[a("el-select",{staticStyle:{width:"85%"},attrs:{"aria-required":"true",multiple:"",placeholder:"请选择类型"},model:{value:e.cinemaForm.cinemaType,callback:function(t){e.$set(e.cinemaForm,"cinemaType",t)},expression:"cinemaForm.cinemaType"}},e._l(e.typeList,(function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),a("el-form-item",{attrs:{label:"影院电话：",prop:"cinemaTel"}},[a("el-select",{staticStyle:{width:"85%"},attrs:{multiple:"",filterable:"","allow-create":"","default-first-option":"",placeholder:"请填写电话","aria-required":"true"},model:{value:e.cinemaForm.cinemaTel,callback:function(t){e.$set(e.cinemaForm,"cinemaTel",t)},expression:"cinemaForm.cinemaTel"}},e._l(e.telLiest,(function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1)],1),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:e.handleClose}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.save()}}},[e._v("确 定")])],1)],1),a("el-button",{staticClass:"ml-10",attrs:{type:"primary"},on:{click:e.exp}},[e._v("导出 "),a("i",{staticClass:"el-icon-download"})])],1),a("el-table",{attrs:{data:e.tableData,border:"",stripe:"","header-cell-style":{background:"#f1f5f8",borderColor:"#CECECE"}}},[a("el-table-column",{attrs:{prop:"cinemaBrand",label:"影院品牌",width:"100"}}),a("el-table-column",{attrs:{prop:"cinemaName",label:"影院名称",width:"300"}}),a("el-table-column",{attrs:{prop:"cinemaBd",label:"影院商圈",width:"200"}}),a("el-table-column",{attrs:{prop:"cinemaAddress",label:"地址",width:"450"}}),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"success"},on:{click:function(a){return e.handleOpen("update",t.row)}}},[e._v(" 编辑 "),a("i",{staticClass:"el-icon-edit-outline"})]),a("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"danger"},on:{click:function(a){return e.deleteopen(t.row.cinemaId)}}},[e._v(" 删除 "),a("i",{staticClass:"el-icon-remove-outline"})])]}}])})],1),a("Skeleton",{attrs:{loading:e.loading}}),a("div",{staticClass:"block",staticStyle:{padding:"10px 0"}},[a("el-pagination",{attrs:{"current-page":e.pageNum,"page-sizes":[5,10,15,20],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)},n=[],l=(a("ac1f"),a("1276"),a("d3b7"),a("25f0"),a("2d5b")),r=a("863e"),o={name:"Cinema",components:{Skeleton:l["a"]},data:function(){return{loading:!0,tableData:[],total:0,cinemaName:"",cinemaAddress:"",addOrUpdataVisible:!1,dialogTitle:"",cinemaForm:this.emptycinemaForm(),typeList:[{value:"4K厅",label:"4K厅"},{value:"4D厅",label:"4D厅"},{value:"巨幕厅",label:"巨幕厅"},{value:"4DX厅",label:"4DX厅"},{value:"IMAX厅",label:"IMAX厅"},{value:"ReaID厅",label:"ReaID厅"},{value:"LUXE巨幕厅",label:"LUXE巨幕厅"},{value:"ReaID 6FL厅",label:"ReaID 6FL厅"},{value:"CGS中国巨幕厅",label:"CGS中国巨幕厅"},{value:"DTS:X临境音厅",label:"DTS:X临境音厅"},{value:"Dolby Cinema厅",label:"Dolby Cinema厅"}],provinceNameList:[{name:"1"}],cityNameList:[],countyNameList:[],telLiest:[{value:"",label:""}],rules:{cinemaName:[{required:!0,message:"请输入电影名称",trigger:"blur"}],isRebook:[{required:!0,message:"请选择能否改签",trigger:"change"}],isReticket:[{required:!0,message:"请选择能否退票",trigger:"blur"}],cinemaBrand:[{required:!0,message:"请输入影院品牌",trigger:"blur"}],cinemaProvince:[{required:!0,message:"请选择省份",trigger:"change"}],cinemaCity:[{required:!0,message:"请选择市级",trigger:"change"}],cinemaCounty:[{required:!0,message:"请选择县级级",trigger:"change"}],cinemaDetailed:[{required:!0,message:"请输入详细地址",trigger:"blur"}],cinemaBd:[{required:!0,message:"请输入影院商圈",trigger:"blur"}],cinemaType:[{required:!0,message:"请选择类型",trigger:"change"}]},pageNum:1,pageSize:5}},created:function(){var e="https://www.mxnzp.com/api/address/list?app_id=vqjzvfqgiaufgavr&app_secret=OGhYZmVwYUhYVjQrYmVMYnowYWIxUT09";this.jsget(e),this.load()},methods:{handleOpen:function(e,t){var a=this;this.addOrUpdataVisible=!0,"create"===e?(this.dialogTitle="添加影院",this.cinemaForm=this.emptycinemaForm(),setTimeout((function(){a.$refs.cinemaForm.clearValidate()}),10)):"update"===e&&(t.cinemaType=t.cinemaType.split(","),t.cinemaTel=t.cinemaTel.split(","),this.cinemaForm=t,this.dialogTitle="编辑影院资料")},changeProvinceName:function(e){var t=this,a="https://www.mxnzp.com/api/address/search?type=0&value="+e+"&app_id=vqjzvfqgiaufgavr&app_secret=OGhYZmVwYUhYVjQrYmVMYnowYWIxUT09",i=new XMLHttpRequest;i.open("GET",a),i.send(null);var n=this;new Promise((function(e,t){i.onload=function(){n.json=JSON.parse(this.response),1===n.json.code?e(n.json):t("JSON:Some errors have occurred")}})).then((function(e){console.log("获取城市列表",e.data[0].pchilds),t.cityNameList=e.data[0].pchilds})).catch((function(e){console.log("err=>",e)}))},changeCityName:function(e){var t=this,a="https://www.mxnzp.com/api/address/search?type=1&value="+e+"&app_id=vqjzvfqgiaufgavr&app_secret=OGhYZmVwYUhYVjQrYmVMYnowYWIxUT09",i=new XMLHttpRequest;i.open("GET",a),i.send(null);var n=this;new Promise((function(e,t){i.onload=function(){n.json=JSON.parse(this.response),1===n.json.code?e(n.json):t("JSON:Some errors have occurred")}})).then((function(e){console.log("获取区列表",e.data[0].pchilds[0].cchilds),t.countyNameList=e.data[0].pchilds[0].cchilds})).catch((function(e){console.log("err=>",e)}))},getll:function(){var e=this,t=this.cinemaForm.cinemaProvince+this.cinemaForm.cinemaCity+this.cinemaForm.cinemaCounty+this.cinemaForm.cinemaDetailed;console.log(t),this.$jsonp("https://apis.map.qq.com/ws/geocoder/v1/",{address:t,key:"5XRBZ-FZI6S-46IOA-6ZLZ6-54W7Z-PTBSD",output:"jsonp"}).then((function(a){console.log("success",a),e.cinemaForm.cinemaLocation=a.result.location.lng+","+a.result.location.lat,e.cinemaForm.cinemaAddress=t,console.log(e.cinemaForm),e.$message.success("查询成功！坐标为"+e.cinemaForm.cinemaLocation)})).catch((function(e){console.log("err",e)}))},changeCountyName:function(){},closeDialog:function(){var e=this;this.cinemaForm=this.emptycinemaForm(),setTimeout((function(){e.$refs.cinemaForm.clearValidate()}),10),this.load()},emptycinemaForm:function(){return{cinemaId:"",cinemaName:"",isRebook:"",isReticket:"",cinemaType:"",cinemaAddress:"",cinemaTel:"",cinemaBrand:"",cinemaBd:"",cinemaProvince:"",cinemaCity:"",cinemaCounty:"",cinemaDetailed:"",cinemaLocation:""}},handleClose:function(){var e=this;this.$confirm("确认关闭？").then((function(t){e.addOrUpdataVisible=!1})).catch((function(e){}))},exp:function(){window.open("http://".concat(r["a"],":9090/cinema/export"))},save:function(){var e=this;this.$refs["cinemaForm"].validate((function(t){if(!t)return!1;e.cinemaForm.cinemaType=e.cinemaForm.cinemaType.toString(),e.cinemaForm.cinemaTel=e.cinemaForm.cinemaTel.toString(),console.log(e.cinemaForm),e.request.post("/cinema",e.cinemaForm).then((function(t){"200"===t.code?(e.$message.success(t.msg),e.load()):e.$message.error(t.msg),e.addOrUpdataVisible=!1,e.cinemaForm=e.emptycinemaForm(),setTimeout((function(){e.$refs.cinemaForm.clearValidate()}),10)}))}))},deleteopen:function(e){var t=this;this.$confirm("此操作将永久删除该电影, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){t.request.delete("/cinema/"+e).then((function(e){"200"===e.code?(t.$message({type:"success",message:e.msg}),t.load()):t.$message({type:"error",message:e.msg})}))})).catch((function(){t.$message({type:"info",message:"已取消删除"})}))},load:function(){var e=this;this.request("/cinema/cinemaPage",{params:{pageNum:this.pageNum,pageSize:this.pageSize,cinemaName:this.cinemaName}}).then((function(t){console.log(t),e.tableData=t.data.records,e.total=t.data.total,e.loading=!1}))},reload:function(){this.cinemaName="",this.cinemaAddress="",this.load()},handleSizeChange:function(e){console.log(e),this.pageSize=e,this.load()},handleCurrentChange:function(e){console.log(e),this.pageNum=e,this.load()},jsget:function(e){var t=this,a=new XMLHttpRequest;a.open("GET",e),a.send(null);var i=this;new Promise((function(e,t){a.onload=function(){i.json=JSON.parse(this.response),1===i.json.code?e(i.json):t("JSON:Some errors have occurred")}})).then((function(e){console.log("获取成功"),console.log(e),t.provinceNameList=e.data})).catch((function(e){console.log("err=>",e)}))}}},c=o,s=a("2877"),m=Object(s["a"])(c,i,n,!1,null,"338f0975",null);t["default"]=m.exports}}]);
//# sourceMappingURL=chunk-ca663404.4746387f.js.map