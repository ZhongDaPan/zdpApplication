webpackJsonp([22],{FwGS:function(e,n,a){var t=a("veb1");"string"==typeof t&&(t=[[e.i,t,""]]),t.locals&&(e.exports=t.locals);a("FIqI")("58064c00",t,!0,{})},kYGW:function(e,n,a){"use strict";function t(e){a("FwGS")}Object.defineProperty(n,"__esModule",{value:!0});var i=a("3cXf"),c=a.n(i),A=a("a3Yh"),d=a.n(A),r=a("pKZN"),o=a("8pLc"),s=(r.a,o.a,{components:{back:r.a,noData:o.a},data:function(){var e;return e={MedicalTempId:this.$route.query.MedicalTempId,ImageId:this.$route.query.ImageId,PatientName:this.$route.query.PatientName,Advice:"",Diagnosis:"",DiseaseDesc:"",OperationName:""},d()(e,"Diagnosis",""),d()(e,"bl",[]),d()(e,"cf",[]),d()(e,"bg",[]),d()(e,"qt",[]),d()(e,"hj",[]),d()(e,"CT",[]),d()(e,"pageShow",!1),e},created:function(){this.pull()},methods:{pull:function(){var e=this;this.$get("/PlatFormAPI/PatientArchives/GetReportImageByImageId",{ImageId:this.ImageId,MedicalTempId:this.MedicalTempId}).then(function(n){e.pageShow=!0,e.Advice=n.Advice,e.Diagnosis=n.Diagnosis,e.DiseaseDesc=n.DiseaseDesc,e.Diagnosis=n.Diagnosis,e.OperationName=n.OperationName,e.PatientName=n.PatientName,n.ImgDetails.forEach(function(n){1==e.MedicalTempId?1===n.ImageType?e.bl=n.ImagePaths:2===n.ImageType?e.cf=n.ImagePaths:3===n.ImageType?e.bg=n.ImagePaths:e.qt=n.ImagePaths:2==e.MedicalTempId&&("喉镜"==n.PictureLabel?e.hj=n.ImagePaths:"CT、MR、DR"==n.PictureLabel&&(e.CT=n.ImagePaths))})})},toPreview:function(e,n){var a={position:n,url:e};this.$bridge.callhandler("Preview",c()(a))}}}),C=function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("div",{staticClass:"container pdt"},[a("div",{staticClass:"title"},[a("back"),e._v(" "),a("span",{staticClass:"name"},[e._v("病历详情")]),e._v(" "),a("a")],1),e._v(" "),a("p",{staticClass:"patient"},[e._m(0),e._v(" "),a("span",[e._v(e._s(e.PatientName))])]),e._v(" "),1==e.MedicalTempId?a("div",[a("div",{staticClass:"describe"},[e._m(1),e._v(" "),a("textarea",{directives:[{name:"model",rawName:"v-model",value:e.DiseaseDesc,expression:"DiseaseDesc"}],attrs:{readonly:""},domProps:{value:e.DiseaseDesc},on:{input:function(n){n.target.composing||(e.DiseaseDesc=n.target.value)}}})]),e._v(" "),a("div",{staticClass:"update"},[e._m(2),e._v(" "),a("div",{staticClass:"Image"},[a("p",{staticClass:"name"},[e._v("病历")]),e._v(" "),!e.bl.length&&e.pageShow?a("no-data"):e._e(),e._v(" "),a("div",{staticClass:"add"},e._l(e.bl,function(n,t){return a("img",{key:t,attrs:{src:n},on:{click:function(n){e.toPreview(e.bl,t)}}})}))],1),e._v(" "),a("div",{staticClass:"Image"},[a("p",{staticClass:"name"},[e._v("处方")]),e._v(" "),!e.cf.length&&e.pageShow?a("no-data"):e._e(),e._v(" "),a("div",{staticClass:"add"},e._l(e.cf,function(n,t){return a("img",{key:t,attrs:{src:n},on:{click:function(n){e.toPreview(e.cf,t)}}})}))],1),e._v(" "),a("div",{staticClass:"Image"},[a("p",{staticClass:"name"},[e._v("检验检查报告")]),e._v(" "),!e.bg.length&&e.pageShow?a("no-data"):e._e(),e._v(" "),a("div",{staticClass:"add"},e._l(e.bg,function(n,t){return a("img",{key:t,attrs:{src:n},on:{click:function(n){e.toPreview(e.bg,t)}}})}))],1),e._v(" "),a("div",{staticClass:"Image"},[a("p",{staticClass:"name"},[e._v("其他")]),e._v(" "),!e.qt.length&&e.pageShow?a("no-data"):e._e(),e._v(" "),a("div",{staticClass:"add"},e._l(e.qt,function(n,t){return a("img",{key:t,attrs:{src:n},on:{click:function(n){e.toPreview(e.qt,t)}}})}))],1)])]):e._e(),e._v(" "),2==e.MedicalTempId?a("div",[a("p",{staticClass:"patient2"},[a("span",[e._v("疾病")]),e._v(" "),a("span",[e._v(e._s(e.Diagnosis))])]),e._v(" "),a("p",{staticClass:"patient2"},[a("span",[e._v("手术")]),e._v(" "),a("span",[e._v(e._s(e.OperationName))])]),e._v(" "),a("div",{staticClass:"describe"},[e._m(3),e._v(" "),a("textarea",{directives:[{name:"model",rawName:"v-model",value:e.DiseaseDesc,expression:"DiseaseDesc"}],attrs:{readonly:""},domProps:{value:e.DiseaseDesc},on:{input:function(n){n.target.composing||(e.DiseaseDesc=n.target.value)}}})]),e._v(" "),a("div",{staticClass:"update"},[e._m(4),e._v(" "),a("div",{staticClass:"Image"},[a("p",{staticClass:"name"},[e._v("检验检查报告（喉镜）")]),e._v(" "),!e.hj.length&&e.pageShow?a("no-data"):e._e(),e._v(" "),a("div",{staticClass:"add"},e._l(e.hj,function(n,t){return a("img",{key:t,attrs:{src:n},on:{click:function(n){e.toPreview(e.hj,t)}}})}))],1),e._v(" "),a("div",{staticClass:"Image"},[a("p",{staticClass:"name"},[e._v("检验检查报告（CT、MR、DR）")]),e._v(" "),!e.CT.length&&e.pageShow?a("no-data"):e._e(),e._v(" "),a("div",{staticClass:"add"},e._l(e.CT,function(n,t){return a("img",{key:t,attrs:{src:n},on:{click:function(n){e.toPreview(e.CT,t)}}})}))],1)])]):e._e()])},l=[function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("span",{staticClass:"gray"},[a("i",{staticClass:"icon10"}),e._v(" 就诊人")])},function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("p",{staticClass:"gray"},[a("i",{staticClass:"icon11"}),e._v(" 病情描述")])},function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("p",{staticClass:"tit gray"},[a("i",{staticClass:"icon12"}),e._v(" 病情照片")])},function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("p",{staticClass:"gray"},[a("i",{staticClass:"icon11"}),e._v(" 病情描述")])},function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("p",{staticClass:"tit gray"},[a("i",{staticClass:"icon12"}),e._v(" 病情照片")])}],B={render:C,staticRenderFns:l},m=B,p=a("vSla"),g=t,b=p(s,m,!1,g,"data-v-cad6ec2c",null);n.default=b.exports},veb1:function(e,n,a){var t=a("L4zZ");n=e.exports=a("UTlt")(!0),n.push([e.i,"\n.container[data-v-cad6ec2c] {\n  font-size: 15px;\n}\n.pb[data-v-cad6ec2c] {\n  padding-bottom: 1.06667rem;\n}\n.btn[data-v-cad6ec2c] {\n  width: 80%;\n  height: 1.06667rem;\n  text-align: center;\n  line-height: 1.06667rem;\n  border-radius: 30px;\n  background: #306bce;\n  color: #fff;\n  margin: 0.93333rem auto 0;\n}\nbutton[data-v-cad6ec2c] {\n  background: #306bce;\n  color: #fff;\n  height: 1.06667rem;\n  width: 80%;\n  display: block;\n  margin: 1.06667rem auto;\n  border-radius: 30px;\n}\n.choose[data-v-cad6ec2c] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.choose i[data-v-cad6ec2c] {\n    display: block;\n    height: 0.46667rem;\n    width: 0.26667rem;\n    background: url("+t(a("b9hs"))+") no-repeat center;\n    background-size: cover;\n    margin-left: 0.13333rem;\n}\n.patient[data-v-cad6ec2c], .patient2[data-v-cad6ec2c] {\n  margin-top: 0.13333rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  height: 1.2rem;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  padding: 0 0.4rem;\n  background: #fff;\n  margin-bottom: 0.13333rem;\n  -webkit-box-pack: justify;\n  -webkit-justify-content: space-between;\n          justify-content: space-between;\n}\n.patient2[data-v-cad6ec2c] {\n  margin-top: 0;\n}\n.patient2 select[data-v-cad6ec2c] {\n    background: transparent;\n    border-color: transparent;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    height: 100%;\n    direction: rtl;\n    width: 100%;\n}\n.patient2 span[data-v-cad6ec2c] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.patient2 span[data-v-cad6ec2c]:last-child {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      height: 100%;\n      -webkit-box-pack: end;\n      -webkit-justify-content: flex-end;\n              justify-content: flex-end;\n}\n.patient2 .plhd[data-v-cad6ec2c] {\n    color: #A9A9A9;\n}\n.patient2 i[data-v-cad6ec2c] {\n    display: block;\n    height: 0.46667rem;\n    width: 0.26667rem;\n    background: url("+t(a("b9hs"))+") no-repeat center;\n    background-size: cover;\n}\n.item[data-v-cad6ec2c] {\n  padding: 0.2rem 0.4rem 0 0.4rem;\n}\n.item .date[data-v-cad6ec2c] {\n    padding: 0.13333rem 0;\n    font-size: 13px;\n    margin-bottom: 0.13333rem;\n    color: #666;\n    text-align: center;\n}\n.item .card[data-v-cad6ec2c] {\n    padding: 0.26667rem 0.53333rem;\n    border-radius: 20px;\n}\n.item .card p[data-v-cad6ec2c] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-pack: justify;\n      -webkit-justify-content: space-between;\n              justify-content: space-between;\n}\n.item .card p[data-v-cad6ec2c]:first-child {\n        margin-bottom: 0.26667rem;\n        border-bottom: 1px solid #ececec;\n        padding-bottom: 0.26667rem;\n}\n.item .card .text[data-v-cad6ec2c] {\n      -webkit-box-orient: vertical;\n      -webkit-box-direction: normal;\n      -webkit-flex-direction: column;\n              flex-direction: column;\n      line-height: 2;\n}\n.item .card .text .gray[data-v-cad6ec2c] {\n        color: #999;\n}\n.item .card .from[data-v-cad6ec2c] {\n      border: 1px solid #fea514;\n      color: #fea514;\n      padding: 0.04rem 0.26667rem;\n      font-size: 13px;\n      border-radius: 20px;\n}\n.describe[data-v-cad6ec2c] {\n  background: #fff;\n  padding-bottom: 0.26667rem;\n}\n.describe p[data-v-cad6ec2c] {\n    padding: 0.26667rem 0.4rem;\n    border-bottom: 1px solid #ececec;\n}\n.describe textarea[data-v-cad6ec2c] {\n    display: block;\n    width: 90%;\n    height: 2rem;\n    margin: 0.26667rem auto;\n    border-radius: 5px;\n    resize: none;\n    padding: 0.2rem;\n}\n.pt[data-v-cad6ec2c] {\n  padding: 0.26667rem 0.4rem 0.13333rem;\n}\n.update[data-v-cad6ec2c] {\n  background: #fff;\n  margin-top: 0.13333rem;\n}\n.update .tit[data-v-cad6ec2c] {\n    padding: 0.26667rem 0.4rem;\n    border-bottom: 1px solid #ececec;\n}\n.card[data-v-cad6ec2c] {\n  padding: 0.4rem;\n  background: #fff;\n}\n.gray[data-v-cad6ec2c] {\n  color: #666;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.gray i[data-v-cad6ec2c] {\n    display: block;\n    width: 0.4rem;\n    height: 0.4rem;\n    margin-right: 0.13333rem;\n}\n.gray .icon10[data-v-cad6ec2c] {\n    background: url("+t(a("+Y4L"))+") no-repeat center;\n    background-size: contain;\n}\n.gray .icon11[data-v-cad6ec2c] {\n    background: url("+t(a("YFD/"))+") no-repeat center;\n    background-size: contain;\n}\n.gray .icon12[data-v-cad6ec2c] {\n    background: url("+t(a("rg5S"))+") no-repeat center;\n    background-size: contain;\n}\n.Image[data-v-cad6ec2c] {\n  background: #fff;\n  margin-top: 0.13333rem;\n  padding: 0.13333rem 0.4rem;\n  border-bottom: 1px solid #ececec;\n}\n.Image .name[data-v-cad6ec2c] {\n    padding: 0 0.26667rem;\n    font-size: 13px;\n}\n.Image .add[data-v-cad6ec2c] {\n    padding-top: 0.13333rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-flex-wrap: wrap;\n            flex-wrap: wrap;\n}\n.Image .add img[data-v-cad6ec2c] {\n      width: 2.4rem;\n      height: 2.4rem;\n      margin: 0.2rem;\n}\n.Image .add p[data-v-cad6ec2c] {\n      position: relative;\n}\n.Image .add .pop[data-v-cad6ec2c] {\n      display: block;\n      width: 0.66667rem;\n      height: 0.66667rem;\n      background: url("+t(a("9bmq"))+") no-repeat center;\n      background-size: contain;\n      background-color: #fff;\n      position: absolute;\n      top: -0.06667rem;\n      right: 0;\n      border-radius: 50%;\n}\n.Image .add .icon[data-v-cad6ec2c] {\n      display: block;\n      width: 2.4rem;\n      height: 2.4rem;\n      border: 1px solid #ccc;\n      border-radius: 20px;\n      position: relative;\n      margin: 0.13333rem 0.2rem;\n}\n.Image .add .icon[data-v-cad6ec2c]::before {\n        content: '';\n        display: block;\n        width: 50%;\n        height: 0.2rem;\n        border-radius: 0.13333rem;\n        background: #ccc;\n        position: absolute;\n        top: 1.06667rem;\n        left: 25%;\n}\n.Image .add .icon[data-v-cad6ec2c]::after {\n        content: '';\n        display: block;\n        width: 0.2rem;\n        border-radius: 0.13333rem;\n        height: 50%;\n        background: #ccc;\n        position: absolute;\n        top: 25%;\n        left: 1.10667rem;\n}\n","",{version:3,sources:["D:/yys/src/style/page/case.scss"],names:[],mappings:"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",file:"case.scss",sourcesContent:['\n.container[data-v-cad6ec2c] {\n  font-size: 15px;\n}\n.pb[data-v-cad6ec2c] {\n  padding-bottom: 1.06667rem;\n}\n.btn[data-v-cad6ec2c] {\n  width: 80%;\n  height: 1.06667rem;\n  text-align: center;\n  line-height: 1.06667rem;\n  border-radius: 30px;\n  background: #306bce;\n  color: #fff;\n  margin: 0.93333rem auto 0;\n}\nbutton[data-v-cad6ec2c] {\n  background: #306bce;\n  color: #fff;\n  height: 1.06667rem;\n  width: 80%;\n  display: block;\n  margin: 1.06667rem auto;\n  border-radius: 30px;\n}\n.choose[data-v-cad6ec2c] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.choose i[data-v-cad6ec2c] {\n    display: block;\n    height: 0.46667rem;\n    width: 0.26667rem;\n    background: url("~assets/enter.png") no-repeat center;\n    background-size: cover;\n    margin-left: 0.13333rem;\n}\n.patient[data-v-cad6ec2c], .patient2[data-v-cad6ec2c] {\n  margin-top: 0.13333rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  height: 1.2rem;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  padding: 0 0.4rem;\n  background: #fff;\n  margin-bottom: 0.13333rem;\n  -webkit-box-pack: justify;\n  -webkit-justify-content: space-between;\n          justify-content: space-between;\n}\n.patient2[data-v-cad6ec2c] {\n  margin-top: 0;\n}\n.patient2 select[data-v-cad6ec2c] {\n    background: transparent;\n    border-color: transparent;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    height: 100%;\n    direction: rtl;\n    width: 100%;\n}\n.patient2 span[data-v-cad6ec2c] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.patient2 span[data-v-cad6ec2c]:last-child {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      height: 100%;\n      -webkit-box-pack: end;\n      -webkit-justify-content: flex-end;\n              justify-content: flex-end;\n}\n.patient2 .plhd[data-v-cad6ec2c] {\n    color: #A9A9A9;\n}\n.patient2 i[data-v-cad6ec2c] {\n    display: block;\n    height: 0.46667rem;\n    width: 0.26667rem;\n    background: url("~assets/enter.png") no-repeat center;\n    background-size: cover;\n}\n.item[data-v-cad6ec2c] {\n  padding: 0.2rem 0.4rem 0 0.4rem;\n}\n.item .date[data-v-cad6ec2c] {\n    padding: 0.13333rem 0;\n    font-size: 13px;\n    margin-bottom: 0.13333rem;\n    color: #666;\n    text-align: center;\n}\n.item .card[data-v-cad6ec2c] {\n    padding: 0.26667rem 0.53333rem;\n    border-radius: 20px;\n}\n.item .card p[data-v-cad6ec2c] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-pack: justify;\n      -webkit-justify-content: space-between;\n              justify-content: space-between;\n}\n.item .card p[data-v-cad6ec2c]:first-child {\n        margin-bottom: 0.26667rem;\n        border-bottom: 1px solid #ececec;\n        padding-bottom: 0.26667rem;\n}\n.item .card .text[data-v-cad6ec2c] {\n      -webkit-box-orient: vertical;\n      -webkit-box-direction: normal;\n      -webkit-flex-direction: column;\n              flex-direction: column;\n      line-height: 2;\n}\n.item .card .text .gray[data-v-cad6ec2c] {\n        color: #999;\n}\n.item .card .from[data-v-cad6ec2c] {\n      border: 1px solid #fea514;\n      color: #fea514;\n      padding: 0.04rem 0.26667rem;\n      font-size: 13px;\n      border-radius: 20px;\n}\n.describe[data-v-cad6ec2c] {\n  background: #fff;\n  padding-bottom: 0.26667rem;\n}\n.describe p[data-v-cad6ec2c] {\n    padding: 0.26667rem 0.4rem;\n    border-bottom: 1px solid #ececec;\n}\n.describe textarea[data-v-cad6ec2c] {\n    display: block;\n    width: 90%;\n    height: 2rem;\n    margin: 0.26667rem auto;\n    border-radius: 5px;\n    resize: none;\n    padding: 0.2rem;\n}\n.pt[data-v-cad6ec2c] {\n  padding: 0.26667rem 0.4rem 0.13333rem;\n}\n.update[data-v-cad6ec2c] {\n  background: #fff;\n  margin-top: 0.13333rem;\n}\n.update .tit[data-v-cad6ec2c] {\n    padding: 0.26667rem 0.4rem;\n    border-bottom: 1px solid #ececec;\n}\n.card[data-v-cad6ec2c] {\n  padding: 0.4rem;\n  background: #fff;\n}\n.gray[data-v-cad6ec2c] {\n  color: #666;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n}\n.gray i[data-v-cad6ec2c] {\n    display: block;\n    width: 0.4rem;\n    height: 0.4rem;\n    margin-right: 0.13333rem;\n}\n.gray .icon10[data-v-cad6ec2c] {\n    background: url("~assets/Consultation/house@2x.png") no-repeat center;\n    background-size: contain;\n}\n.gray .icon11[data-v-cad6ec2c] {\n    background: url("~assets/Consultation/doctor_service_icon3.png") no-repeat center;\n    background-size: contain;\n}\n.gray .icon12[data-v-cad6ec2c] {\n    background: url("~assets/Consultation/nurse_visits_image@2x.png") no-repeat center;\n    background-size: contain;\n}\n.Image[data-v-cad6ec2c] {\n  background: #fff;\n  margin-top: 0.13333rem;\n  padding: 0.13333rem 0.4rem;\n  border-bottom: 1px solid #ececec;\n}\n.Image .name[data-v-cad6ec2c] {\n    padding: 0 0.26667rem;\n    font-size: 13px;\n}\n.Image .add[data-v-cad6ec2c] {\n    padding-top: 0.13333rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-flex-wrap: wrap;\n            flex-wrap: wrap;\n}\n.Image .add img[data-v-cad6ec2c] {\n      width: 2.4rem;\n      height: 2.4rem;\n      margin: 0.2rem;\n}\n.Image .add p[data-v-cad6ec2c] {\n      position: relative;\n}\n.Image .add .pop[data-v-cad6ec2c] {\n      display: block;\n      width: 0.66667rem;\n      height: 0.66667rem;\n      background: url("~assets/Consultation/del.png") no-repeat center;\n      background-size: contain;\n      background-color: #fff;\n      position: absolute;\n      top: -0.06667rem;\n      right: 0;\n      border-radius: 50%;\n}\n.Image .add .icon[data-v-cad6ec2c] {\n      display: block;\n      width: 2.4rem;\n      height: 2.4rem;\n      border: 1px solid #ccc;\n      border-radius: 20px;\n      position: relative;\n      margin: 0.13333rem 0.2rem;\n}\n.Image .add .icon[data-v-cad6ec2c]::before {\n        content: \'\';\n        display: block;\n        width: 50%;\n        height: 0.2rem;\n        border-radius: 0.13333rem;\n        background: #ccc;\n        position: absolute;\n        top: 1.06667rem;\n        left: 25%;\n}\n.Image .add .icon[data-v-cad6ec2c]::after {\n        content: \'\';\n        display: block;\n        width: 0.2rem;\n        border-radius: 0.13333rem;\n        height: 50%;\n        background: #ccc;\n        position: absolute;\n        top: 25%;\n        left: 1.10667rem;\n}\n'],sourceRoot:""}])}});
//# sourceMappingURL=22.dc0964e23cbe4ffb7c7f.js.map