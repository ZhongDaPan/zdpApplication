webpackJsonp([43],{AG11:function(n,e,t){"use strict";function i(n){t("rQPq")}Object.defineProperty(e,"__esModule",{value:!0});var a=t("pKZN"),A=(a.a,{components:{back:a.a},data:function(){return{info:null,ageStr:""}},created:function(){this.pull()},methods:{pull:function(){var n=this;this.$get("PlatFormAPI/ServicePackItem/GetServicePackRecordDetail",this.$pick({ServicePackRecordId:this.$route.query.id})).then(function(e){n.info=e,n.age(e.PatientInfo.Birthday)})},age:function(n){var e=new Date(n.replace(/-/g,"/")),t=new Date;this.ageStr=t.getFullYear()-e.getFullYear()-(t.getMonth()<e.getMonth()||t.getMonth()==e.getMonth()&&t.getDate()<e.getDate()?1:0)},toCheck:function(){var n=this;this.$get("PlatFormAPI/ServicePackItem/GetServicePackItemBySPRId",this.$pick({ServicePackRecordId:this.$route.query.id})).then(function(e){e.length?n.$router.push("/record-checkIn?id="+n.$route.query.id):n.$toast("暂无需要人工登记的服务项")})}}}),o=function(){var n=this,e=n.$createElement,i=n._self._c||e;return n.info?i("div",{ref:"el",staticClass:"pdt"},[i("div",{staticClass:"title"},[i("back"),n._v(" "),i("span",{staticClass:"name"},[n._v("记录详情")]),n._v(" "),i("a")],1),n._v(" "),i("div",{staticClass:"patient"},[n.info.PatientInfo.PicturePath?i("img",{attrs:{src:n.info.PatientInfo.PicturePath,alt:""}}):i("img",{attrs:{src:t("uLfx")}}),n._v(" "),i("div",{staticClass:"info"},[i("p",[n._v("签约就诊人")]),n._v(" "),i("p",{staticClass:"pname"},[n._v(n._s(n.info.PatientInfo.PatientName)+"("+n._s(n.ageStr)+"岁)")]),n._v(" "),i("p",{staticClass:"no"},[n._v(n._s(n.info.PatientInfo.CredNo?n.info.PatientInfo.CredNo.substr(0,4)+"**********"+n.info.PatientInfo.CredNo.substr(14):""))]),n._v(" "),i("p",{staticClass:"no"},[n._v(n._s(n.info.PatientInfo.Mobile.replace(/(\d{3})\d{4}(\d{4})/,"$1****$2")))])])]),n._v(" "),i("div",{staticClass:"sContent"},[i("p",[n._v(n._s(n.info.ServicePackName))]),n._v(" "),i("div",{staticClass:"time"},[n.info.SignDate?i("p",[n._v("签约时间："+n._s(n.info.SignDate))]):n._e(),n._v(" "),n.info.ServiceMember?i("p",[n._v("服务人员："+n._s(n.info.ServiceMember))]):n._e()]),n._v(" "),n._l(n.info.Usages,function(e,t){return i("div",{key:t,staticClass:"card"},[i("p",[i("span",[n._v(n._s(e.ServiceItemName))])]),n._v(" "),e.NumType?i("p",[n._v("\n                共"+n._s(e.NumValue)+n._s(n._f("NumType")(e.NumType))+" 剩余"+n._s(e.Surplus)+n._s(n._f("NumType")(e.NumType))+"\n            ")]):i("p",[n._v("\n                按需\n            ")])])})],2),n._v(" "),n.info.UsageDetails&&n.info.UsageDetails.length?i("div",{staticClass:"record"},n._l(n.info.UsageDetails,function(e,t){return i("p",{key:t},[i("i",{class:{first:0==t}}),n._v(" "),i("span",{staticClass:"rname"},[n._v("\n                服务项目："+n._s(e.ServiceItemName)+"\n            ")]),n._v(" "),i("span",[n._v("\n                服务人员："+n._s(e.ServiceDr)+"\n            ")]),n._v(" "),i("span",[n._v("\n                服务执行情况："+n._s(e.Situation)+"\n            ")]),n._v(" "),i("span",[n._v(n._s(e.ExecuteDateTime))])])})):n._e(),n._v(" "),i("div",{staticClass:"btn",on:{click:n.toCheck}},[n._v("履约登记")])]):i("div",[i("div",{staticClass:"title"},[i("back"),n._v(" "),i("span",{staticClass:"name"},[n._v("记录详情")]),n._v(" "),i("a")],1)])},r=[],c={render:o,staticRenderFns:r},d=c,s=t("vSla"),B=i,l=s(A,d,!1,B,"data-v-3c783782",null);e.default=l.exports},qOxO:function(n,e,t){e=n.exports=t("UTlt")(!0),e.push([n.i,"\n.pdt[data-v-3c783782] {\n  padding-bottom: 2.66667rem;\n}\n.patient[data-v-3c783782] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  height: 2.66667rem;\n  background: #fff;\n  border-top: 1px solid #ececec;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.patient img[data-v-3c783782] {\n    width: 1.33333rem;\n    height: 1.33333rem;\n    border-radius: 50%;\n    margin: 0 0.66667rem;\n}\n.patient .info[data-v-3c783782] {\n    color: #999;\n}\n.patient .no[data-v-3c783782] {\n    font-size: 13px;\n}\n.patient .pname[data-v-3c783782] {\n    color: #333;\n}\n.sContent[data-v-3c783782] {\n  padding: 0.26667rem 0.4rem;\n  background: #fff;\n  margin-top: 0.4rem;\n  border-bottom: 1px solid #ececec;\n}\n.sContent .time[data-v-3c783782] {\n    color: #999;\n    padding: 0.26667rem 0.4rem;\n}\n.sContent .card[data-v-3c783782] {\n    padding: 0.4rem 0;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-orient: vertical;\n    -webkit-box-direction: normal;\n    -webkit-flex-direction: column;\n            flex-direction: column;\n    border-top: 1px solid #ececec;\n}\n.sContent .card p[data-v-3c783782] {\n      padding: 0 0.4rem;\n}\n.record[data-v-3c783782] {\n  background: #fff;\n  margin-top: 0.4rem;\n  padding: 0.4rem 0.8rem;\n}\n.record p[data-v-3c783782] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-orient: vertical;\n    -webkit-box-direction: normal;\n    -webkit-flex-direction: column;\n            flex-direction: column;\n    padding-left: 0.53333rem;\n    padding-bottom: 0.53333rem;\n    border-left: 1px solid #ececec;\n    line-height: 1.6;\n    position: relative;\n}\n.record span[data-v-3c783782] {\n    color: #999;\n    font-size: 13px;\n}\n.record .rname[data-v-3c783782] {\n    font-size: 15px;\n    color: #333;\n}\n.record i[data-v-3c783782] {\n    display: block;\n    width: 0.4rem;\n    height: 0.4rem;\n    border-radius: 50%;\n    background: #306bce;\n    position: absolute;\n    left: -0.2rem;\n}\n.record .first[data-v-3c783782] {\n    background: #fda222;\n}\n.btn[data-v-3c783782] {\n  color: #fff;\n  font-size: 17px;\n  background: #306bce;\n  height: 1.17333rem;\n  width: 90%;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  border-radius: 30px;\n  position: fixed;\n  bottom: 0.66667rem;\n  left: 5%;\n}\n","",{version:3,sources:["D:/yys/src/page/record/detail.vue"],names:[],mappings:";AACA;EACE,2BAA2B;CAC5B;AACD;EACE,qBAAqB;EACrB,sBAAsB;EACtB,cAAc;EACd,mBAAmB;EACnB,iBAAiB;EACjB,8BAA8B;EAC9B,0BAA0B;EAC1B,4BAA4B;UACpB,oBAAoB;CAC7B;AACD;IACI,kBAAkB;IAClB,mBAAmB;IACnB,mBAAmB;IACnB,qBAAqB;CACxB;AACD;IACI,YAAY;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;CACf;AACD;EACE,2BAA2B;EAC3B,iBAAiB;EACjB,mBAAmB;EACnB,iCAAiC;CAClC;AACD;IACI,YAAY;IACZ,2BAA2B;CAC9B;AACD;IACI,kBAAkB;IAClB,qBAAqB;IACrB,sBAAsB;IACtB,cAAc;IACd,6BAA6B;IAC7B,8BAA8B;IAC9B,+BAA+B;YACvB,uBAAuB;IAC/B,8BAA8B;CACjC;AACD;MACM,kBAAkB;CACvB;AACD;EACE,iBAAiB;EACjB,mBAAmB;EACnB,uBAAuB;CACxB;AACD;IACI,qBAAqB;IACrB,sBAAsB;IACtB,cAAc;IACd,6BAA6B;IAC7B,8BAA8B;IAC9B,+BAA+B;YACvB,uBAAuB;IAC/B,yBAAyB;IACzB,2BAA2B;IAC3B,+BAA+B;IAC/B,iBAAiB;IACjB,mBAAmB;CACtB;AACD;IACI,YAAY;IACZ,gBAAgB;CACnB;AACD;IACI,gBAAgB;IAChB,YAAY;CACf;AACD;IACI,eAAe;IACf,cAAc;IACd,eAAe;IACf,mBAAmB;IACnB,oBAAoB;IACpB,mBAAmB;IACnB,cAAc;CACjB;AACD;IACI,oBAAoB;CACvB;AACD;EACE,YAAY;EACZ,gBAAgB;EAChB,oBAAoB;EACpB,mBAAmB;EACnB,WAAW;EACX,qBAAqB;EACrB,sBAAsB;EACtB,cAAc;EACd,0BAA0B;EAC1B,4BAA4B;UACpB,oBAAoB;EAC5B,yBAAyB;EACzB,gCAAgC;UACxB,wBAAwB;EAChC,oBAAoB;EACpB,gBAAgB;EAChB,mBAAmB;EACnB,SAAS;CACV",file:"detail.vue",sourcesContent:["\n.pdt[data-v-3c783782] {\n  padding-bottom: 2.66667rem;\n}\n.patient[data-v-3c783782] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  height: 2.66667rem;\n  background: #fff;\n  border-top: 1px solid #ececec;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.patient img[data-v-3c783782] {\n    width: 1.33333rem;\n    height: 1.33333rem;\n    border-radius: 50%;\n    margin: 0 0.66667rem;\n}\n.patient .info[data-v-3c783782] {\n    color: #999;\n}\n.patient .no[data-v-3c783782] {\n    font-size: 13px;\n}\n.patient .pname[data-v-3c783782] {\n    color: #333;\n}\n.sContent[data-v-3c783782] {\n  padding: 0.26667rem 0.4rem;\n  background: #fff;\n  margin-top: 0.4rem;\n  border-bottom: 1px solid #ececec;\n}\n.sContent .time[data-v-3c783782] {\n    color: #999;\n    padding: 0.26667rem 0.4rem;\n}\n.sContent .card[data-v-3c783782] {\n    padding: 0.4rem 0;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-orient: vertical;\n    -webkit-box-direction: normal;\n    -webkit-flex-direction: column;\n            flex-direction: column;\n    border-top: 1px solid #ececec;\n}\n.sContent .card p[data-v-3c783782] {\n      padding: 0 0.4rem;\n}\n.record[data-v-3c783782] {\n  background: #fff;\n  margin-top: 0.4rem;\n  padding: 0.4rem 0.8rem;\n}\n.record p[data-v-3c783782] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-orient: vertical;\n    -webkit-box-direction: normal;\n    -webkit-flex-direction: column;\n            flex-direction: column;\n    padding-left: 0.53333rem;\n    padding-bottom: 0.53333rem;\n    border-left: 1px solid #ececec;\n    line-height: 1.6;\n    position: relative;\n}\n.record span[data-v-3c783782] {\n    color: #999;\n    font-size: 13px;\n}\n.record .rname[data-v-3c783782] {\n    font-size: 15px;\n    color: #333;\n}\n.record i[data-v-3c783782] {\n    display: block;\n    width: 0.4rem;\n    height: 0.4rem;\n    border-radius: 50%;\n    background: #306bce;\n    position: absolute;\n    left: -0.2rem;\n}\n.record .first[data-v-3c783782] {\n    background: #fda222;\n}\n.btn[data-v-3c783782] {\n  color: #fff;\n  font-size: 17px;\n  background: #306bce;\n  height: 1.17333rem;\n  width: 90%;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  border-radius: 30px;\n  position: fixed;\n  bottom: 0.66667rem;\n  left: 5%;\n}\n"],sourceRoot:""}])},rQPq:function(n,e,t){var i=t("qOxO");"string"==typeof i&&(i=[[n.i,i,""]]),i.locals&&(n.exports=i.locals);t("FIqI")("006d4963",i,!0,{})}});
//# sourceMappingURL=43.d618395eaf6450f4a7df.js.map