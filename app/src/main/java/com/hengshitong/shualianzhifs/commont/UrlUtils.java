package com.hengshitong.shualianzhifs.commont;

public class UrlUtils {
    //测试
//    public final static String url = "http://test.aiyouzhan.cn/api/";
//    public final static String url1 = "http://test.aiyouzhan.cn/";
    //生产正式
//      public final static String url = "http://api.aiyouzhan.cn/api/";
//      public final static String url1 = "http://user.aiyouzhan.cn/";
    //晓龙测试
    public final static String url = "192.168.1.21:8088/api/";
    public final static String url1 = "http://test.aiyouzhan.cn/api/";


    //获取人脸凭证接口:
    public final static String HUOQUSHUANLIANZF = url1+"wxface/app/wxface/getAuthinfo";
    //获取人脸支付接口:
    public final static String ZHIFUJIEK = url1+"wxface/test/facepay";
    //查询设备是否绑定
    public final static String SHEBEIBANGDING = url1+"enterprise/enterprise/face-recognize/whetherBind";
    //绑定油站
    public final static String BANGIDNGYOUZHAN = url1+"enterprise/enterprise/face-recognize/bindFaceEquipment";
    //获取油枪列表
    public final static String YOUQIANGJIEBIAO = url1+"gas/user/getGunsAndOperator";
    //获取优惠方案
    public final static String YOUHUIFANGAN = url1+"gas/user/getDiscountPlan";
    //获取优惠券
   // public final static String YOUHUIJUAN= url1+"coupon/enterprise/coupon/getCouponByFace";
    public final static String YOUHUIJUAN= url1+"coupon/user/coupon/page-list";
    //获取登录的token
     public final static String UTOKENDS= url1+"wxface/pp/wxface/getUToken";
    //获取员工工牌
    public final static String YUANGOGNGONGPAI= url1+"enterprise/user/getWeixinInfoByCode";
    //获取立减列表
    public final static String HUOQULIJIANJUAN= url1+"gas/user/getListEnable";
    //获取公众号支付二维码
    public final static String GONGZONGHAOERWEIMA= url1+"gas/user/getListEnable";
    //获取用户余额
    public final static String HUOQUYONGHUBLANCES= url1+"member/user/getUserStationInfo";
    //获取用户余额
    public final static String YUGEXIADAN= url1+"gas/user/createOrder";
    //订单生成
    public final static String DINGDANSHENGCHENG= url1+"gas/user/createOrder";
    //刷脸支付接口
    public final static String SHUALIANZHIFUJIEK= url1+"wxface/app/wxface/wxfacePay";



    //获取图形验证码
    public final static String TUXINGYANZMA = url+"common/getImageCaptche";
    //登录
    public final static String LOGDENGLU = url+"enterprise/enterprise/login";
    //获取油站信息
    public final static String GETSTAIONDETIAL = url+"enterprise/enterprise/gas-station/get";
    //企业员工等车出
    public final static String LONGOUTS = url+"enterprise/enterprise/logout";
    //绑定打印机
    public final static String BANGDINGDAYINJI = url+"enterprise/enterprise/bindClientId";
    //班结列表
    public final static String BANJIELIEBIAO = url+"gas/enterprise/shift/getListShift";
    //班结详情
   // public final static String BANJIEXINGQING = url+"gas/enterprise/shift/DetailsShift";
    //新班结详情
    public final static String BANJIEXINGQING = url+"gas/enterprise/shift/detailsShiftNew";

 //操作员班结详情
    public final static String CAOZUOYUANBANJIEXINGQING = url+"gas/enterprise/shift/getList";
    //时时班结详情  enterprise/shift/realTimeShiftNew
    public final static String SHISHIBANJIEXIANGQ= url+"gas/enterprise/shift/realTimeShiftNew";
    //油枪列表
 //   public final static String YOUQIANGLIEBIAO= url+"gas/enterprise/shift/getGunList";

    //油枪列表2
    public final static String YOUQIANGLIEBIAO= url+"gas/enterprise/getGunList";

    //当前办结ID e
    public final static String DANGQIANBANJIEID= url+"gas/enterprise/shift/getUnshiftIdByStaion";
    //时时班借操作员详情keyi
    public final static String SHISHICAOZUOYUANBANJIEXINGQING = url+"gas/enterprise/shift/getRealTimeShiftList";
    //一键班结  oneKeyShiftNew
    public final static String YIJIANBANJIE= url+"gas/enterprise/shift/oneKeyShiftNew";
    //今日订单
    public final static String JINREDINGDAN= url+"gas/enterprise/searchOrder";
    //查询用户验证码
    public final static String CHAXUNYONGHUYANZMA= url+"member/enterprise/getPhoneCode";
    //查询企业会员
    public final static String CHAXUNQIYEHUIYUAN= url+"member/enterprise/searchUser";
    //获取加油员
    public final static String HUOQUJAYOUYUAN= url+"gas/enterprise/getGunsAndOperator";
    //手动创建订单（线下订单）
  //  public final static String XIANXIADINGDNA= url+"gas/enterprise/manualOrder";

    //手动创建订单（线下订单）
    public final static String XIANXIADINGDNA= url+"gas/user/createUnderLineOrder";

    //车牌号查询用户
    public final static String CHEPAIHAOCHAXUNYONGHU= url+"member/enterprise/search-user-business";
    //获取油品
    public final static String HUOQUYOUQINGS= url+"gas/enterprise/getOilTypes";
    //根据油品id 获取油枪
    public final static String YOUQIANGHOUQU= url+"gas/enterprise/getByType";
    //返回优惠券
    public final static String FNUIYOUHUIJUAN= url+"gas/enterprise/getDiscountPlan";


    //获取今日订单日期
    public final static String HUOQUDINGRRQI= url+"gas/enterprise/shift/getDate";
    //http://test.aiyouzhan.cn
    public final static String JINRRDINGDNA= url+"gas/enterprise/searchOrder";
    //保持员工登录
    public final static String BAOCHIDNEGLU= url+"enterprise/enterprise/keepalive";
    // 支付反扫
    public final static String ZHIFUFANSHAO= url+"gas/enterprise/order/createOrderAndPayment";
    // 检查更新1
    //public final static String JIANCHAGENINX= "https://www.aiyouzhan.cn/version.json";
    // 检查更新2
    public final static String JIANCHAGENINX= "https://www.aiyouzhan.cn/pos_app/version.json";
    // 是否支付成功
    public final static String SHIFOUPAYSUCCSESS =url+"gas/enterprise/order/detail";
    //获取用户积分日志分页
    public final static String HUOYQJIFENFENYE =url+"point/operator/user-point-log/pageByOperatorName";
    //通根据id获取用户详细信息
  //  public final static String POINTHUOQUYONGH =url+"member/enterprise/getUserById";
    public final static String POINTHUOQUYONGH =url+"member/enterprise/getUserInfo-post";

    //发送验证码模板消息
    public final static String YANZHANGMAMUBAN =url+"wxpub/enterprise/sendValidationCodeMsg";
    //更新积分
     public final static String GEGNXINPOINT=url+"point/operator/user-point/update";
    //获取用户积分
    public final static String HUOQUYONHUJIFEN=url+"point/operator/user-point/getUserPoint";

    //积分调整打印模板消息
    public final static String JIFENDAYINS=url+"point/operator/user-point-log/getPointLogById";

    //获取打印信息
    public final static String DAYINDINGDAN=url+"gas/enterprise/order/getOrderPush";

    //微信模板消息接口
    public final static String WEIXINMUBANXX=url+"enterprise/enterprise/captche-user/posGetCaptcheUser";



}
