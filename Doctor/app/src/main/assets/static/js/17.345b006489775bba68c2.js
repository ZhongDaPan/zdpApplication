webpackJsonp([17],{"/71g":function(a,t,n){"use strict";function e(a){n("mAdF")}Object.defineProperty(t,"__esModule",{value:!0});var i=n("JpXR"),r=n.n(i),A=n("pKZN"),d=(A.a,{components:{back:A.a},data:function(){return{DrId:this.$route.params.id,name:"",cardName:"",bank:"",number:"",verify:!1,verifyTxt:"",type:"cancel",BankImg:"",start:this.$route.params.start,DrBankCardId:this.$route.params.DrBankCardId,appleTn:!1,AndroidTn:!1,baName:1==this.$route.params.start?"添加银行卡":"修改银行卡",bl:{DisplayPath:"",SavePath:""}}},mounted:function(){var a=this;2==this.start&&this.$get("/PlatFormAPI/Doctor/QueryDoctorBankCardByDrId",{DrId:this.DrId}).then(function(t){null!=t&&void 0!=t&&""!=t&&(console.log(t),a.name=t.Name,a.number=t.CardNo,a.cardName=t.BankName,a.bank=t.OpenBank,a.bl.SavePath=t.BankImg)})},methods:{imgUpdate:function(a){var t=this,n=a.target.files[0];n&&(r()(n).then(function(a){var e=new FileReader;e.readAsDataURL(n);var i=t;e.onload=function(a){i.er=this.result.split("base64,")[1],i.$post("PlatFormAPI/PatientArchives/ReportImageUpload",{Base64Str:this.result.split("base64,")[1]}).then(function(a){i.bl.DisplayPath=a.DisplayPath,i.bl.SavePath=a.SavePath})}}),a.target.value=null)},getImg:function(a){var t=this;this.$bridge.callhandler("APP","Img",function(n){var e=n.split(",")[0].split("=")[1],i=n.split(",")[1].split("=")[1].split("}")[0];t[a].DisplayPath=e,t[a].SavePath=i})},addCardFn:function(){for(var a=this,t=/\S/,n=[{name:this.name,msg:"真实姓名"},{name:this.cardName,msg:"银行名称"},{name:this.bank,msg:"开户行"},{name:this.number,msg:"卡号"}],e=0;e<n.length;e++)if(!t.test(n[e].name))return this.verifyTxt=n[e].msg+"是必填项",void(this.verify=!0);var i=null;i=1==this.start?{DrId:this.DrId,CardNo:this.number,Name:this.name,BankName:this.cardName,OpenBank:this.bank,BankImg:this.bl.SavePath}:{DrBankCardId:this.DrBankCardId,DrId:this.DrId,CardNo:this.number,Name:this.name,BankName:this.cardName,OpenBank:this.bank,BankImg:this.bl.SavePath},this.$post("/PlatFormAPI/Doctor/SaveDoctorBankCard",i).then(function(t){if(t){a.type="1s",1==a.start?a.verifyTxt="添加成功":a.verifyTxt="修改成功",a.verify=!0;var n=a;setTimeout(function(){n.$router.push({path:"/Center/card",name:"card",params:{id:n.DrId}})},500)}})},toUpdate:function(){-1!==navigator.userAgent.indexOf("iPhone")?(this.appleTn=!0,this.AndroidTn=!1):(this.appleTn=!1,this.AndroidTn=!0)}},created:function(){this.toUpdate()}}),o=function(){var a=this,t=a.$createElement,e=a._self._c||t;return e("main",{staticClass:"main pdt",attrs:{id:"income"}},[e("div",{staticClass:"title"},[e("back"),a._v(" "),e("span",{staticClass:"name"},[a._v(a._s(a.baName))]),a._v(" "),e("a")],1),a._v(" "),e("div",[e("div",{staticClass:"man-grid"},[e("div",{staticClass:"item"},[e("span",[a._v("用户姓名")]),a._v(" "),e("span",[e("input",{directives:[{name:"model",rawName:"v-model",value:a.name,expression:"name"}],staticClass:"txt",attrs:{placeholder:"请输入您的真实姓名",contenteditable:"true"},domProps:{value:a.name},on:{input:function(t){t.target.composing||(a.name=t.target.value)}}})])]),a._v(" "),e("div",{staticClass:"item"},[e("span",[a._v("银行名称")]),a._v(" "),e("span",[e("input",{directives:[{name:"model",rawName:"v-model",value:a.cardName,expression:"cardName"}],staticClass:"txt",attrs:{placeholder:"请输入银行名称全称",contenteditable:"true"},domProps:{value:a.cardName},on:{input:function(t){t.target.composing||(a.cardName=t.target.value)}}})])]),a._v(" "),e("div",{staticClass:"item"},[e("span",[a._v("开户行")]),a._v(" "),e("span",[e("input",{directives:[{name:"model",rawName:"v-model",value:a.bank,expression:"bank"}],staticClass:"txt",attrs:{placeholder:"请输入开卡支行",contenteditable:"true"},domProps:{value:a.bank},on:{input:function(t){t.target.composing||(a.bank=t.target.value)}}})])]),a._v(" "),e("div",{staticClass:"item"},[e("span",[a._v("卡号")]),a._v(" "),e("span",[e("input",{directives:[{name:"model",rawName:"v-model",value:a.number,expression:"number"}],staticClass:"txt",attrs:{placeholder:"请输入您的银行卡号2",contenteditable:"true"},domProps:{value:a.number},on:{input:function(t){t.target.composing||(a.number=t.target.value)}}}),a._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:a.appleTn,expression:"appleTn"}],staticClass:"toUpdate"},[e("img",{attrs:{src:n("xoih")}}),a._v(" "),e("input",{attrs:{id:"file",type:"file",accept:"image/*;capture=camera",multipl:"",runat:"server"},on:{change:a.imgUpdate}})]),a._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:a.AndroidTn,expression:"AndroidTn"}],staticClass:"toUpdate",on:{click:function(t){a.getImg("bl")}}},[e("img",{attrs:{src:n("xoih")}})])])])])]),a._v(" "),e("div",{staticClass:"card-btn"},[e("span",{staticClass:"btn",on:{click:a.addCardFn}},[a._v("保存")])]),a._v(" "),e("toast",{attrs:{type:a.type,name:"show",time:1e3},model:{value:a.verify,callback:function(t){a.verify=t},expression:"verify"}},[a._v(a._s(a.verifyTxt))])],1)},s=[],m={render:o,staticRenderFns:s},c=m,l=n("vSla"),M=e,u=l(d,c,!1,M,"data-v-24c755fa",null);t.default=u.exports},"3wVR":function(a,t,n){t=a.exports=n("UTlt")(!0),t.push([a.i,"\n.title i[data-v-24c755fa] {\n  margin-left: 0.26667rem;\n}\n.btn[data-v-24c755fa] {\n  width: 7.33333rem;\n  height: 1.13333rem;\n  display: block;\n  text-align: center;\n  line-height: 1.13333rem;\n  margin: 0 auto;\n  border-radius: 0.53333rem;\n  background-color: #3069cf;\n  color: #fff;\n  font-size: 18px;\n}\n.NoCard[data-v-24c755fa] {\n  padding: 0.4rem 0.53333rem;\n  background-color: #fff;\n  text-align: center;\n  margin-top: 1.33333rem;\n}\n.NoCard span[data-v-24c755fa] {\n    display: block;\n}\n.NoCard span[data-v-24c755fa]:last-child {\n    width: 2.4rem;\n    height: 0.66667rem;\n    line-height: 0.66667rem;\n    border-radius: 0.10667rem;\n    margin: 0 auto;\n    margin-top: 0.4rem;\n    background-color: #2a6dc9;\n    color: #fff;\n}\n.card-btn[data-v-24c755fa] {\n  margin-top: 0.26667rem;\n  background-color: #fff;\n  padding: 0.4rem 0.53333rem;\n}\n.man-grid[data-v-24c755fa] {\n  margin-top: 1.33333rem;\n  background-color: #fff;\n  padding: 0 0.53333rem;\n}\n.man-grid .item[data-v-24c755fa] {\n    padding: 0.4rem 0;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    border-bottom: 1px solid #f3f3f3;\n}\n.man-grid .item span[data-v-24c755fa] {\n      position: relative;\n}\n.man-grid .item span[data-v-24c755fa]:first-child {\n      width: 60px;\n      margin-right: 0.26667rem;\n}\n.man-grid .item .toUpdate[data-v-24c755fa] {\n      position: absolute;\n      right: -0.46667rem;\n      top: 0rem;\n}\n.man-grid .item .toUpdate img[data-v-24c755fa] {\n        width: 0.46667rem;\n}\n.man-grid .item .toUpdate input[data-v-24c755fa] {\n        width: 0.46667rem;\n        height: 100%;\n        opacity: 0;\n        height: 100%;\n        opacity: 0;\n        position: absolute;\n        top: 0;\n        left: 0;\n}\n.man-grid .item[data-v-24c755fa]:last-child {\n    border-bottom: 0px solid;\n}\n","",{version:3,sources:["D:/yys/src/page/Center/addCard.vue"],names:[],mappings:";AACA;EACE,wBAAwB;CACzB;AACD;EACE,kBAAkB;EAClB,mBAAmB;EACnB,eAAe;EACf,mBAAmB;EACnB,wBAAwB;EACxB,eAAe;EACf,0BAA0B;EAC1B,0BAA0B;EAC1B,YAAY;EACZ,gBAAgB;CACjB;AACD;EACE,2BAA2B;EAC3B,uBAAuB;EACvB,mBAAmB;EACnB,uBAAuB;CACxB;AACD;IACI,eAAe;CAClB;AACD;IACI,cAAc;IACd,mBAAmB;IACnB,wBAAwB;IACxB,0BAA0B;IAC1B,eAAe;IACf,mBAAmB;IACnB,0BAA0B;IAC1B,YAAY;CACf;AACD;EACE,uBAAuB;EACvB,uBAAuB;EACvB,2BAA2B;CAC5B;AACD;EACE,uBAAuB;EACvB,uBAAuB;EACvB,sBAAsB;CACvB;AACD;IACI,kBAAkB;IAClB,qBAAqB;IACrB,sBAAsB;IACtB,cAAc;IACd,iCAAiC;CACpC;AACD;MACM,mBAAmB;CACxB;AACD;MACM,YAAY;MACZ,yBAAyB;CAC9B;AACD;MACM,mBAAmB;MACnB,mBAAmB;MACnB,UAAU;CACf;AACD;QACQ,kBAAkB;CACzB;AACD;QACQ,kBAAkB;QAClB,aAAa;QACb,WAAW;QACX,aAAa;QACb,WAAW;QACX,mBAAmB;QACnB,OAAO;QACP,QAAQ;CACf;AACD;IACI,yBAAyB;CAC5B",file:"addCard.vue",sourcesContent:["\n.title i[data-v-24c755fa] {\n  margin-left: 0.26667rem;\n}\n.btn[data-v-24c755fa] {\n  width: 7.33333rem;\n  height: 1.13333rem;\n  display: block;\n  text-align: center;\n  line-height: 1.13333rem;\n  margin: 0 auto;\n  border-radius: 0.53333rem;\n  background-color: #3069cf;\n  color: #fff;\n  font-size: 18px;\n}\n.NoCard[data-v-24c755fa] {\n  padding: 0.4rem 0.53333rem;\n  background-color: #fff;\n  text-align: center;\n  margin-top: 1.33333rem;\n}\n.NoCard span[data-v-24c755fa] {\n    display: block;\n}\n.NoCard span[data-v-24c755fa]:last-child {\n    width: 2.4rem;\n    height: 0.66667rem;\n    line-height: 0.66667rem;\n    border-radius: 0.10667rem;\n    margin: 0 auto;\n    margin-top: 0.4rem;\n    background-color: #2a6dc9;\n    color: #fff;\n}\n.card-btn[data-v-24c755fa] {\n  margin-top: 0.26667rem;\n  background-color: #fff;\n  padding: 0.4rem 0.53333rem;\n}\n.man-grid[data-v-24c755fa] {\n  margin-top: 1.33333rem;\n  background-color: #fff;\n  padding: 0 0.53333rem;\n}\n.man-grid .item[data-v-24c755fa] {\n    padding: 0.4rem 0;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    border-bottom: 1px solid #f3f3f3;\n}\n.man-grid .item span[data-v-24c755fa] {\n      position: relative;\n}\n.man-grid .item span[data-v-24c755fa]:first-child {\n      width: 60px;\n      margin-right: 0.26667rem;\n}\n.man-grid .item .toUpdate[data-v-24c755fa] {\n      position: absolute;\n      right: -0.46667rem;\n      top: 0rem;\n}\n.man-grid .item .toUpdate img[data-v-24c755fa] {\n        width: 0.46667rem;\n}\n.man-grid .item .toUpdate input[data-v-24c755fa] {\n        width: 0.46667rem;\n        height: 100%;\n        opacity: 0;\n        height: 100%;\n        opacity: 0;\n        position: absolute;\n        top: 0;\n        left: 0;\n}\n.man-grid .item[data-v-24c755fa]:last-child {\n    border-bottom: 0px solid;\n}\n"],sourceRoot:""}])},mAdF:function(a,t,n){var e=n("3wVR");"string"==typeof e&&(e=[[a.i,e,""]]),e.locals&&(a.exports=e.locals);n("FIqI")("3eba616a",e,!0,{})},xoih:function(a,t){a.exports="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyBjbGFzcz0iaWNvbiIgd2lkdGg9IjY0cHgiIGhlaWdodD0iNjQuMDBweCIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxwYXRoIGZpbGw9IiM5ZjlmOWYiIGQ9Ik05MDguOTg0IDIyNC43MjhINzQzLjc1MnYtMTQuNTA0YzAtNjQuMTQ0LTUyLjItMTE2LjMzNi0xMTYuMzUyLTExNi4zMzZIMzk2LjU2OGMtNjQuMTQ0IDAtMTE2LjMyIDUyLjE5Mi0xMTYuMzIgMTE2LjMzNnYxNC41MDRIMTE0Ljk4NEM1MS41ODQgMjI0LjcyOCAwIDI3Ni4yNDggMCAzMzkuNTY4djQ3NS43MDRjMCA2My4zMTIgNTEuNTg0IDExNC44MzIgMTE0Ljk4NCAxMTQuODMyaDc5NGM2My40MzIgMCAxMTUuMDE2LTUxLjUyIDExNS4wMTYtMTE0LjgzMlYzMzkuNTY4YzAtNjMuMzItNTEuNTg0LTExNC44NC0xMTUuMDE2LTExNC44NHogbTU0LjMzNiA1OTAuNTQ0YzAgMjkuODU2LTI0LjM4NCA1NC4xNi01NC4zMzYgNTQuMTZoLTc5NGMtMjkuOTUyIDAtNTQuMzA0LTI0LjI5Ni01NC4zMDQtNTQuMTZWMzM5LjU2OGMwLTI5Ljg2NCAyNC4zNTItNTQuMTYgNTQuMzA0LTU0LjE2aDE5NS42MDhhMzAuMzIgMzAuMzIgMCAwIDAgMzAuMzM2LTMwLjMzNnYtNDQuODRhNTUuNjk2IDU1LjY5NiAwIDAgMSA1NS42NC01NS42NTZoMjMwLjgzMmMzMC42OTYgMCA1NS42NzIgMjQuOTYgNTUuNjcyIDU1LjY1NnY0NC44NGEzMC4zMiAzMC4zMiAwIDAgMCAzMC4zNDQgMzAuMzM2aDE5NS41NjhjMjkuOTUyIDAgNTQuMzM2IDI0LjI5NiA1NC4zMzYgNTQuMTZ2NDc1LjcwNHoiICAvPjxwYXRoIGZpbGw9IiM5ZjlmOWYiIGQ9Ik01MTIgMzUwLjYxNmMtMTA2Ljg0IDAtMTkzLjczNiA4Ni44OTYtMTkzLjczNiAxOTMuNzA0IDAgMTA2LjgxNiA4Ni44OTYgMTkzLjcxMiAxOTMuNzM2IDE5My43MTIgMTA2LjgzMiAwIDE5My43MzYtODYuODk2IDE5My43MzYtMTkzLjcxMiAwLTEwNi44MDgtODYuOTA0LTE5My43MDQtMTkzLjczNi0xOTMuNzA0eiBtMCAzMjYuNzM2Yy03My4zNiAwLTEzMy4wNTYtNTkuNjcyLTEzMy4wNTYtMTMzLjAzMiAwLTczLjM1MiA1OS42OTYtMTMzLjAyNCAxMzMuMDU2LTEzMy4wMjRzMTMzLjA2NCA1OS42NzIgMTMzLjA2NCAxMzMuMDI0YzAgNzMuMzYtNTkuNzA0IDEzMy4wMzItMTMzLjA2NCAxMzMuMDMyeiIgIC8+PC9zdmc+"}});
//# sourceMappingURL=17.345b006489775bba68c2.js.map