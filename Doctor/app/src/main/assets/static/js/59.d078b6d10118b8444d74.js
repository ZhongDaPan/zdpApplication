webpackJsonp([59],{J5qw:function(e,n,t){"use strict";function i(e){t("Rw50")}Object.defineProperty(n,"__esModule",{value:!0});var a=t("3cXf"),r=t.n(a),o=t("pKZN"),s=t("8pLc"),A=(o.a,s.a,{components:{back:o.a,noData:s.a},data:function(){return{search:"",page:1,haveMore:!0,list:[],orderInfo:this.$store.state.pInfo,user:this.$store.state.userInfo,pageShow:!1}},created:function(){this.pull(!1)},mounted:function(){this.getMore()},beforeDestroy:function(){window.onscroll=null},methods:{pull:function(e){var n=this;this.haveMore&&this.$post("PlatFormAPI/KnowledgeBase/GetDrHealthKnowledgeForPage",this.$pick({DrId:this.user.Data.User.DoctorId,OrgIds:this.user.Data.User.OrgId,Key:this.search,PageIndex:this.page,PageSize:15})).then(function(t){n.pageShow=!0,!t.ReturnData||t.ReturnData.length<15?n.haveMore=!1:n.haveMore=!0,e?t.ReturnData.forEach(function(e){n.list.push(e)}):n.list=t.ReturnData})},toPreview:function(e){this.$bridge.callhandler("createWebView",e.LinkURL)},getMore:function(){var e=this,n=this.$refs.el,t=null;window.onscroll=function(){clearTimeout(t),t=setTimeout(function(){var t=document.documentElement.scrollTop||window.pageYOffset||document.body.scrollTop;n.scrollHeight-t-window.innerHeight<100&&e.haveMore&&(e.page+=1,e.pull(!0))},100)}},send:function(e){var n={Title:e.ServicePackName,Content:[{Type:"Text",ContentElem:{Text:e.Title}}],ExtData:{Type:301,Data:{LinkURL:e.LinkURL}},IsSystem:!1};this.$bridge.callhandler("Health",r()(n)),this.$bridge.callhandler("Back")},searchFor:function(){this.haveMore=!0,this.page=1,this.pull(!1)}}}),c=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("div",{ref:"el",staticClass:"pdt"},[t("div",{staticClass:"title"},[t("back"),e._v(" "),t("span",{staticClass:"name"},[e._v("健康宣教")]),e._v(" "),t("a")],1),e._v(" "),t("div",{staticClass:"search"},[t("input",{directives:[{name:"model",rawName:"v-model",value:e.search,expression:"search"}],attrs:{type:"search",placeholder:"搜索健康宣教"},domProps:{value:e.search},on:{blur:e.searchFor,keyup:function(n){return"button"in n||!e._k(n.keyCode,"enter",13,n.key,"Enter")?e.searchFor(n):null},input:function(n){n.target.composing||(e.search=n.target.value)}}}),e._v(" "),t("span",{staticClass:"sBtn",on:{click:e.searchFor}},[e._v("搜索")])]),e._v(" "),!e.list.length&&e.pageShow?t("no-data",{attrs:{txt:"暂无健康宣教"}}):e._e(),e._v(" "),e._l(e.list,function(n,i){return t("div",{key:i,staticClass:"card"},[t("p",{on:{click:function(t){e.toPreview(n)}}},[t("span",[e._v(e._s(n.Title))]),e._v(" "),t("span",[e._v(e._s(1==n.FieldType?"公共":"个人"))])]),e._v(" "),t("span",{staticClass:"btn",on:{click:function(t){e.send(n)}}},[e._v("发送")])])})],2)},l=[],d={render:c,staticRenderFns:l},B=d,f=t("vSla"),p=i,b=f(A,B,!1,p,"data-v-110af5d9",null);n.default=b.exports},LeYb:function(e,n,t){n=e.exports=t("UTlt")(!0),n.push([e.i,"\n.search[data-v-110af5d9] {\n  height: 1.33333rem;\n  background: #fff;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  position: relative;\n  border: 1px solid #ececec;\n}\n.search input[data-v-110af5d9] {\n    height: 60%;\n    margin: 0 0.4rem;\n    background: #fff;\n    border-radius: 30px;\n    padding: 0 0.4rem;\n    border: 1px solid #ececec;\n}\n.search .sBtn[data-v-110af5d9] {\n    min-width: 1.6rem;\n    height: 0.8rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    background: #306bce;\n    color: #fff;\n    border-radius: 20px;\n    margin: 0 0.26667rem;\n}\n.card[data-v-110af5d9] {\n  background: #fff;\n  border-bottom: 1px solid #ececec;\n  padding: 0 0.4rem;\n  min-height: 1.6rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.card p[data-v-110af5d9] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-orient: vertical;\n    -webkit-box-direction: normal;\n    -webkit-flex-direction: column;\n            flex-direction: column;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    height: 100%;\n    -webkit-justify-content: space-around;\n            justify-content: space-around;\n    padding: 0.13333rem 0;\n}\n.card p span[data-v-110af5d9]:last-child {\n      font-size: 13px;\n      color: #999;\n}\n.card .btn[data-v-110af5d9] {\n    color: #ffa416;\n    border: 1px solid #ffa416;\n    border-radius: 8px;\n    padding: 0.10667rem 0.26667rem;\n}\n","",{version:3,sources:["D:/yys/src/page/table/health.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,iBAAiB;EACjB,qBAAqB;EACrB,sBAAsB;EACtB,cAAc;EACd,0BAA0B;EAC1B,4BAA4B;UACpB,oBAAoB;EAC5B,yBAAyB;EACzB,gCAAgC;UACxB,wBAAwB;EAChC,mBAAmB;EACnB,0BAA0B;CAC3B;AACD;IACI,YAAY;IACZ,iBAAiB;IACjB,iBAAiB;IACjB,oBAAoB;IACpB,kBAAkB;IAClB,0BAA0B;CAC7B;AACD;IACI,kBAAkB;IAClB,eAAe;IACf,qBAAqB;IACrB,sBAAsB;IACtB,cAAc;IACd,0BAA0B;IAC1B,4BAA4B;YACpB,oBAAoB;IAC5B,yBAAyB;IACzB,gCAAgC;YACxB,wBAAwB;IAChC,oBAAoB;IACpB,YAAY;IACZ,oBAAoB;IACpB,qBAAqB;CACxB;AACD;EACE,iBAAiB;EACjB,iCAAiC;EACjC,kBAAkB;EAClB,mBAAmB;EACnB,qBAAqB;EACrB,sBAAsB;EACtB,cAAc;EACd,0BAA0B;EAC1B,4BAA4B;UACpB,oBAAoB;CAC7B;AACD;IACI,qBAAqB;IACrB,sBAAsB;IACtB,cAAc;IACd,6BAA6B;IAC7B,8BAA8B;IAC9B,+BAA+B;YACvB,uBAAuB;IAC/B,oBAAoB;IACpB,gBAAgB;YACR,QAAQ;IAChB,aAAa;IACb,sCAAsC;YAC9B,8BAA8B;IACtC,sBAAsB;CACzB;AACD;MACM,gBAAgB;MAChB,YAAY;CACjB;AACD;IACI,eAAe;IACf,0BAA0B;IAC1B,mBAAmB;IACnB,+BAA+B;CAClC",file:"health.vue",sourcesContent:["\n.search[data-v-110af5d9] {\n  height: 1.33333rem;\n  background: #fff;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  position: relative;\n  border: 1px solid #ececec;\n}\n.search input[data-v-110af5d9] {\n    height: 60%;\n    margin: 0 0.4rem;\n    background: #fff;\n    border-radius: 30px;\n    padding: 0 0.4rem;\n    border: 1px solid #ececec;\n}\n.search .sBtn[data-v-110af5d9] {\n    min-width: 1.6rem;\n    height: 0.8rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    background: #306bce;\n    color: #fff;\n    border-radius: 20px;\n    margin: 0 0.26667rem;\n}\n.card[data-v-110af5d9] {\n  background: #fff;\n  border-bottom: 1px solid #ececec;\n  padding: 0 0.4rem;\n  min-height: 1.6rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.card p[data-v-110af5d9] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-orient: vertical;\n    -webkit-box-direction: normal;\n    -webkit-flex-direction: column;\n            flex-direction: column;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    height: 100%;\n    -webkit-justify-content: space-around;\n            justify-content: space-around;\n    padding: 0.13333rem 0;\n}\n.card p span[data-v-110af5d9]:last-child {\n      font-size: 13px;\n      color: #999;\n}\n.card .btn[data-v-110af5d9] {\n    color: #ffa416;\n    border: 1px solid #ffa416;\n    border-radius: 8px;\n    padding: 0.10667rem 0.26667rem;\n}\n"],sourceRoot:""}])},Rw50:function(e,n,t){var i=t("LeYb");"string"==typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);t("FIqI")("e15f8610",i,!0,{})}});
//# sourceMappingURL=59.d078b6d10118b8444d74.js.map