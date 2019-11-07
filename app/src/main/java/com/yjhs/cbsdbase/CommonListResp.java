package com.yjhs.cbsdbase;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.yjhs.cbsd.utils.Tools;

import java.io.Serializable;
import java.util.List;

/**
 * author: Administrator
 * date: 2018/5/23 0023
 * desc:
 */

public class CommonListResp extends JsonElement {

    /**
     * inforModel : [{"strinforid":"2","strtitle":"as","strbrandid":"2","strcarmodelid":"2","strcarspecid":"2","strcolumnid":"2","strbrandtypemodel":"sad","strimage":"asv","strcolumnname":"ds","strstate":"-1","strusername":"qq","struserinfoid":"2","ihitcount":0,"iforwarding":0,"icollectiontimes":0,"strinfo":"sadf","strcreatetime":"","iisdelete":0,"strdeletetime":"","strupdatetime":"2018-05-18 15:01:41","strremark":"的飒飒的","irank":1},{"strinforid":"1","strtitle":"迈凯伦","strbrandid":"1","strcarmodelid":"1","strcarspecid":"1","strcolumnid":"1","strbrandtypemodel":"奥迪-A6-2.0","strimage":"url/1.jpg","strcolumnname":"打折促销","strstate":"1","strusername":"zz","struserinfoid":"1","ihitcount":8,"iforwarding":0,"icollectiontimes":0,"strinfo":"sd","strcreatetime":"sad","iisdelete":0,"strdeletetime":"2018-05-16 17:03:19","strupdatetime":"2018-05-21 16:08:28","strremark":"","irank":2}]
     * iTotalrecords : 2
     */

    private int iTotalrecords;
    @SerializedName(value = "inforModel",alternate = {"dataList","collection","userModel","brandList",
            "carmodelList","adpicEntity","CarSpecList","columnModel","list"})
    private List<InforModelBean> inforModel;
    private String strImgRootPath;

    public int getITotalrecords() {
        return iTotalrecords;
    }

    public void setITotalrecords(int iTotalrecords) {
        this.iTotalrecords = iTotalrecords;
    }

    public List<InforModelBean> getInforModel() {
        return inforModel;
    }

    public void setInforModel(List<InforModelBean> inforModel) {
        this.inforModel = inforModel;
    }

    public String getStrImgRootPath() {
        return strImgRootPath;
    }

    public void setStrImgRootPath(String strImgRootPath) {
        this.strImgRootPath = strImgRootPath;
    }

    @Override
    public JsonElement deepCopy() {
        return null;
    }

    public static class InforModelBean implements MultiItemEntity, Serializable {
        /**
         * strinforid : 2
         * strtitle : as
         * strbrandid : 2
         * strcarmodelid : 2
         * strcarspecid : 2
         * strcolumnid : 2
         * strbrandtypemodel : sad
         * strimage : asv
         * strcolumnname : ds
         * strstate : -1
         * strusername : qq
         * struserinfoid : 2
         * ihitcount : 0
         * iforwarding : 0
         * icollectiontimes : 0
         * strinfo : sadf
         * strcreatetime :
         * iisdelete : 0
         * strdeletetime :
         * strupdatetime : 2018-05-18 15:01:41
         * strremark : 的飒飒的
         * irank : 1
         */
        private String peakEndTime;
        private String strinforid;
        private String strcolor;
        private String strtitle;
        private String strbrandid;
        private String strcarmodelid;
        private String strcarspecid;
        private String strcolumnid;
        private String strbrandtypemodel;
        private String strimage;
        private String strcolumnname;
        private String strstate;
        private String strusername;
        private String struserinfoid;
        private int ihitcount;
        private int iforwarding;
        private int icollectiontimes;
        private String strinfo;
        private String strcreatetime;
        private int iisdelete;
        private String strdeletetime;
        private String strupdatetime;
        private String strremark;
        private int irank;
        private String strImgRootPath;
        private String strtype;
        private String strheadimage;
        private String strnickname;

        private String strcompanyid;
        private String strcompanyname;
        private String strcompanyaddress;
        private String strcompanytelphone;
        private String strcompanylicence;
        private boolean attention = true;

        private String strpushid;
        private String strpushname;
        private String isread;
        private boolean showCircle;//是否显示圆圈
        private boolean bSelect;//是否被选中

        private String strUserPhone;
        private String strNickName;
        private String strUserinfoId;

        private String strbottomadid;
        private String strname;
        private String strremarks;
        private int istate;
        private boolean isSelect;

        private String strbrandname;
        private String strspell;

        private String strcarmodelname;

        private String stradpicid;
        private String stradpicname;
        private String strurl;
        private String strurltype;

        private String strcarspecname;
        private String strcarguigeid;
        private String guigename;


        private String strrank;


        private String strStartImgId;
        private String strImgUrl;
        private String strState;

        private String quotePrice;
        private String isQuote;
        private String isVideo;
        private String strVideo;
        private String peak;
        private String columnPeak;
        private String strUrl;
        private String isUrl;


        private String isRead;

        private String strCompanyName;


        //手册相关

        /**
         * id  手册id
         * title  标题
         * context  正文
         * @return
         */
        private String id;
        private String title;

        //广告图库相关

        private String imageUrl;
        private Double price;
        private Integer state;
        private int hasPay;//0未购买 1已购买（价格不为0的情况）
        private String updateTime;
        private String updateBy;

        /**
         * "id":"4d79902f6152413dbf827f9927e1c052",
         *                 "memberCateName":"自己人(五年)",
         *                 "month":60,
         *                 "entPrice":999,
         *                 "personPrice":666,
         *                 "isDelete":0,
         *                 "updateBy":"bd1e63543b32414babe0a343b92c629a",
         *                 "updateTime":"2019-09-09 09:47:17"
         * @return
         */
        //会员套餐相关
        private String memberCateName;
        private Integer month;
        private Double entPrice;//企业价格
        private Double personPrice;//个人价格


        /**
         * "id":1,
         *                 "dictType":"goldCoin",
         *                 "dictCode":"QD",
         *                 "dictImage":"2019/09/11/4eadde8de14e438cb0f1b10ac8517b24.png",
         *                 "dictName":"签到",
         *                 "dictValue":"10",
         *                 "sort":1,
         *                 "state":0,
         *                 "updateBy":"bd1e63543b32414babe0a343b92c629a",
         *                 "updateTime":"2019-09-11 11:53:07"
         * @return
         */
        //金币规则相关
        private String dictType;
        private String dictCode;
        private String dictImage;
        private String dictName;
        private String dictValue;
        private Integer sort;


        /**
         * {
         *                 "strColumnName":"首页",
         *                 "serveId":"1",
         *                 "serveType":1,
         *                 "strcolumnid":"0",
         *                 "price":500,
         *                 "state":0,
         *                 "updateTime":"2019",
         *                 "updateBy":"0"
         *             }
         * @return
         */
        //金币服务相关
        private String strColumnName;
        private String serveId;
//        private double price;//价格
//        private String strcolumnid;//0表示首页
        private int serveType;//1置顶  2广告
        private int isUse;//0 未使用  -1已使用


        /**
         * {
         *             "strColumnName":"首页",
         *             "num":1,
         *             "serveTime":"2019-09-19,2019-09-20,2019-09-22,2019-09-23",
         *             "expire":0,
         *             "serveId":"2",
         *             "serveType":2,
         *             "strcolumnid":"0",
         *             "price":1000,
         *             "state":0,
         *             "updateTime":"2019",
         *             "updateBy":"0"
         *         }
         * @return
         */
        private String serveTime;
        private int expire;
        private int num;


        /**
         * {
         *                 "id":"1",
         *                 "rmb":495,
         *                 "goldCoin":5000
         *             }
         * @return
         */
        //金币购买规则相关
        private double rmb;
        private double goldCoin;
        private boolean bselect = false;


        private String showTime;//2019-09-19
        private String pushType;//1消息  2动态


        /**
         * {
         *   "addAmount": "string",
         *   "createTime": "string",
         *   "id": "string",
         *   "reason": "string",
         *   "struserinfoid": "string"
         * }
         * @return
         */

        //金币明细
        private String addAmount;
        private String reason;
        private String createTime;

        /**
         * 访客相关
         * {
         *                 "guestType":1,
         *                 "strnickname":"两个人",
         *                 "struserphone":"13642036512",
         *                 "strheadimage":"2019/04/10/3127d7bd1cc041f4b45c174e9c5cb03d.JPG",
         *                 "companyName":"天津优选信息技术有限公司",
         *                 "strtitle":"最后一遍",
         *                 "strinforid":"0a325be5eb2a44d5b6180dcc4adea622",
         *                 "createTime":"2019-09-18 10:48:36"
         *             }
         * @return
         */
        private int guestType;//1站内 2 站外
//        private String strnickname;
        private String struserphone;
//        private String strheadimage;
        private String companyName;
//        private String strtitle;
//        private String strinforid;
//        private String createTime;
        private int browseNum;

        private String isModel = "0";//0老版本文章  1新版本文章
        private String carmodelPeak;


        private String strHeadImage;

        public String getStrHeadImage() {
            return strHeadImage;
        }

        public void setStrHeadImage(String strHeadImage) {
            this.strHeadImage = strHeadImage;
        }

        public String getCarmodelPeak() {
            return carmodelPeak;
        }

        public void setCarmodelPeak(String carmodelPeak) {
            this.carmodelPeak = carmodelPeak;
        }

        public String getPeakEndTime() {
            return peakEndTime;
        }

        public void setPeakEndTime(String peakEndTime) {
            this.peakEndTime = peakEndTime;
        }

        public int getIsUse() {
            return isUse;
        }

        public void setIsUse(int isUse) {
            this.isUse = isUse;
        }

        public String getIsModel() {
            return isModel;
        }

        public void setIsModel(String isModel) {
            this.isModel = isModel;
        }

        public String getStrcarguigeid() {
            return strcarguigeid;
        }

        public void setStrcarguigeid(String strcarguigeid) {
            this.strcarguigeid = strcarguigeid;
        }

        public String getGuigename() {
            return guigename;
        }

        public void setGuigename(String guigename) {
            this.guigename = guigename;
        }

        public int getBrowseNum() {
            return browseNum;
        }

        public void setBrowseNum(int browseNum) {
            this.browseNum = browseNum;
        }

        public int getHasPay() {
            return hasPay;
        }

        public void setHasPay(int hasPay) {
            this.hasPay = hasPay;
        }

        public int getGuestType() {
            return guestType;
        }

        public void setGuestType(int guestType) {
            this.guestType = guestType;
        }

        public String getStruserphone() {
            return struserphone;
        }

        public void setStruserphone(String struserphone) {
            this.struserphone = struserphone;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAddAmount() {
            return addAmount;
        }

        public void setAddAmount(String addAmount) {
            this.addAmount = addAmount;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getPushType() {
            return pushType;
        }

        public void setPushType(String pushType) {
            this.pushType = pushType;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public boolean isBselect() {
            return bselect;
        }

        public void setBselect(boolean bselect) {
            this.bselect = bselect;
        }

        public double getRmb() {
            return rmb;
        }

        public void setRmb(double rmb) {
            this.rmb = rmb;
        }

        public double getGoldCoin() {
            return goldCoin;
        }

        public void setGoldCoin(double goldCoin) {
            this.goldCoin = goldCoin;
        }

        public String getServeTime() {
            return serveTime;
        }

        public void setServeTime(String serveTime) {
            this.serveTime = serveTime;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public String getStrColumnName() {
            return strColumnName;
        }

        public void setStrColumnName(String strColumnName) {
            this.strColumnName = strColumnName;
        }

        public String getServeId() {
            return serveId;
        }

        public void setServeId(String serveId) {
            this.serveId = serveId;
        }

        public int getServeType() {
            return serveType;
        }

        public void setServeType(int serveType) {
            this.serveType = serveType;
        }

        public String getDictType() {
            return dictType;
        }

        public void setDictType(String dictType) {
            this.dictType = dictType;
        }

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getDictImage() {
            return dictImage;
        }

        public void setDictImage(String dictImage) {
            this.dictImage = dictImage;
        }

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getMemberCateName() {
            return memberCateName;
        }

        public void setMemberCateName(String memberCateName) {
            this.memberCateName = memberCateName;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Double getEntPrice() {
            return entPrice;
        }

        public void setEntPrice(Double entPrice) {
            this.entPrice = entPrice;
        }

        public Double getPersonPrice() {
            return personPrice;
        }

        public void setPersonPrice(Double personPrice) {
            this.personPrice = personPrice;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getStrCompanyName() {
            return strCompanyName;
        }

        public void setStrCompanyName(String strCompanyName) {
            this.strCompanyName = strCompanyName;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(String quotePrice) {
            this.quotePrice = quotePrice;
        }

        public String getIsQuote() {
            return isQuote;
        }

        public void setIsQuote(String isQuote) {
            this.isQuote = isQuote;
        }

        public String getIsVideo() {
            return isVideo;
        }

        public void setIsVideo(String isVideo) {
            this.isVideo = isVideo;
        }

        public String getStrVideo() {
            return strVideo;
        }

        public void setStrVideo(String strVideo) {
            this.strVideo = strVideo;
        }

        public String getPeak() {
            return peak;
        }

        public void setPeak(String peak) {
            this.peak = peak;
        }

        public String getColumnPeak() {
            return columnPeak;
        }

        public void setColumnPeak(String columnPeak) {
            this.columnPeak = columnPeak;
        }

        public String getStrUrl() {
            return strUrl;
        }

        public void setStrUrl(String strUrl) {
            this.strUrl = strUrl;
        }

        public String getIsUrl() {
            return isUrl;
        }

        public void setIsUrl(String isUrl) {
            this.isUrl = isUrl;
        }

        public String getStrStartImgId() {
            return strStartImgId;
        }

        public void setStrStartImgId(String strStartImgId) {
            this.strStartImgId = strStartImgId;
        }

        public String getStrImgUrl() {
            return strImgUrl;
        }

        public void setStrImgUrl(String strImgUrl) {
            this.strImgUrl = strImgUrl;
        }

        public String getStrState() {
            return strState;
        }

        public void setStrState(String strState) {
            this.strState = strState;
        }

        public InforModelBean(String strcarspecname, boolean isSelect) {
            this.strcarspecname = strcarspecname;
            this.isSelect = isSelect;
        }

        public InforModelBean() {
        }

        public String getStrrank() {
            return strrank;
        }

        public void setStrrank(String strrank) {
            this.strrank = strrank;
        }

        public String getStrcarspecname() {
            return strcarspecname;
        }

        public void setStrcarspecname(String strcarspecname) {
            this.strcarspecname = strcarspecname;
        }

        public String getStradpicid() {
            return stradpicid;
        }

        public void setStradpicid(String stradpicid) {
            this.stradpicid = stradpicid;
        }

        public String getStradpicname() {
            return stradpicname;
        }

        public void setStradpicname(String stradpicname) {
            this.stradpicname = stradpicname;
        }

        public String getStrurl() {
            return strurl;
        }

        public void setStrurl(String strurl) {
            this.strurl = strurl;
        }

        public String getStrurltype() {
            return strurltype;
        }

        public void setStrurltype(String strurltype) {
            this.strurltype = strurltype;
        }

        public String getStrcarmodelname() {
            return strcarmodelname;
        }

        public void setStrcarmodelname(String strcarmodelname) {
            this.strcarmodelname = strcarmodelname;
        }

        public String getStrbrandname() {
            return strbrandname;
        }

        public void setStrbrandname(String strbrandname) {
            this.strbrandname = strbrandname;
        }

        public String getStrspell() {
            return strspell;
        }

        public void setStrspell(String strspell) {
            this.strspell = strspell;
        }

        public String getStrbottomadid() {
            return strbottomadid;
        }

        public void setStrbottomadid(String strbottomadid) {
            this.strbottomadid = strbottomadid;
        }

        public String getStrname() {
            return strname;
        }

        public void setStrname(String strname) {
            this.strname = strname;
        }

        public String getStrremarks() {
            return strremarks;
        }

        public void setStrremarks(String strremarks) {
            this.strremarks = strremarks;
        }

        public int getIstate() {
            return istate;
        }

        public void setIstate(int istate) {
            this.istate = istate;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getStrUserPhone() {
            return strUserPhone;
        }

        public void setStrUserPhone(String strUserPhone) {
            this.strUserPhone = strUserPhone;
        }

        public String getStrNickName() {
            return strNickName;
        }

        public void setStrNickName(String strNickName) {
            this.strNickName = strNickName;
        }

        public String getStrUserinfoId() {
            return strUserinfoId;
        }

        public void setStrUserinfoId(String strUserinfoId) {
            this.strUserinfoId = strUserinfoId;
        }

        public String getStrpushid() {
            return strpushid;
        }

        public void setStrpushid(String strpushid) {
            this.strpushid = strpushid;
        }

        public String getStrpushname() {
            return strpushname;
        }

        public void setStrpushname(String strpushname) {
            this.strpushname = strpushname;
        }

        public String getIsread() {
            return isread;
        }

        public void setIsread(String isread) {
            this.isread = isread;
        }

        public boolean isShowCircle() {
            return showCircle;
        }

        public void setShowCircle(boolean showCircle) {
            this.showCircle = showCircle;
        }

        public boolean isbSelect() {
            return bSelect;
        }

        public void setbSelect(boolean bSelect) {
            this.bSelect = bSelect;
        }

        public boolean isAttention() {
            return attention;
        }

        public void setAttention(boolean attention) {
            this.attention = attention;
        }

        public String getStrcompanyid() {
            return strcompanyid;
        }

        public void setStrcompanyid(String strcompanyid) {
            this.strcompanyid = strcompanyid;
        }

        public String getStrcompanyname() {
            return strcompanyname;
        }

        public void setStrcompanyname(String strcompanyname) {
            this.strcompanyname = strcompanyname;
        }

        public String getStrcompanyaddress() {
            return strcompanyaddress;
        }

        public void setStrcompanyaddress(String strcompanyaddress) {
            this.strcompanyaddress = strcompanyaddress;
        }

        public String getStrcompanytelphone() {
            return strcompanytelphone;
        }

        public void setStrcompanytelphone(String strcompanytelphone) {
            this.strcompanytelphone = strcompanytelphone;
        }

        public String getStrcompanylicence() {
            return strcompanylicence;
        }

        public void setStrcompanylicence(String strcompanylicence) {
            this.strcompanylicence = strcompanylicence;
        }

        public String getStrheadimage() {
            return strheadimage;
        }

        public void setStrheadimage(String strheadimage) {
            this.strheadimage = strheadimage;
        }

        public String getStrnickname() {
            return strnickname;
        }

        public void setStrnickname(String strnickname) {
            this.strnickname = strnickname;
        }

        public String getStrtype() {
            return strtype;
        }

        public void setStrtype(String strtype) {
            this.strtype = strtype;
        }

        public String getStrcolor() {
            return strcolor;
        }

        public void setStrcolor(String strcolor) {
            this.strcolor = strcolor;
        }

        public String getStrImgRootPath() {
            return strImgRootPath;
        }

        public void setStrImgRootPath(String strImgRootPath) {
            this.strImgRootPath = strImgRootPath;
        }

        public String getStrinforid() {
            return strinforid;
        }

        public void setStrinforid(String strinforid) {
            this.strinforid = strinforid;
        }

        public String getStrtitle() {
            return strtitle;
        }

        public void setStrtitle(String strtitle) {
            this.strtitle = strtitle;
        }

        public String getStrbrandid() {
            return strbrandid;
        }

        public void setStrbrandid(String strbrandid) {
            this.strbrandid = strbrandid;
        }

        public String getStrcarmodelid() {
            return strcarmodelid;
        }

        public void setStrcarmodelid(String strcarmodelid) {
            this.strcarmodelid = strcarmodelid;
        }

        public String getStrcarspecid() {
            return strcarspecid;
        }

        public void setStrcarspecid(String strcarspecid) {
            this.strcarspecid = strcarspecid;
        }

        public String getStrcolumnid() {
            return strcolumnid;
        }

        public void setStrcolumnid(String strcolumnid) {
            this.strcolumnid = strcolumnid;
        }

        public String getStrbrandtypemodel() {
            return strbrandtypemodel;
        }

        public void setStrbrandtypemodel(String strbrandtypemodel) {
            this.strbrandtypemodel = strbrandtypemodel;
        }

        public String getStrimage() {
            return strimage;
        }

        public void setStrimage(String strimage) {
            this.strimage = strimage;
        }

        public String getStrcolumnname() {
            return strcolumnname;
        }

        public void setStrcolumnname(String strcolumnname) {
            this.strcolumnname = strcolumnname;
        }

        public String getStrstate() {
            return strstate;
        }

        public void setStrstate(String strstate) {
            this.strstate = strstate;
        }

        public String getStrusername() {
            return strusername;
        }

        public void setStrusername(String strusername) {
            this.strusername = strusername;
        }

        public String getStruserinfoid() {
            return struserinfoid;
        }

        public void setStruserinfoid(String struserinfoid) {
            this.struserinfoid = struserinfoid;
        }

        public int getIhitcount() {
            return ihitcount;
        }

        public void setIhitcount(int ihitcount) {
            this.ihitcount = ihitcount;
        }

        public int getIforwarding() {
            return iforwarding;
        }

        public void setIforwarding(int iforwarding) {
            this.iforwarding = iforwarding;
        }

        public int getIcollectiontimes() {
            return icollectiontimes;
        }

        public void setIcollectiontimes(int icollectiontimes) {
            this.icollectiontimes = icollectiontimes;
        }

        public String getStrinfo() {
            return strinfo;
        }

        public void setStrinfo(String strinfo) {
            this.strinfo = strinfo;
        }

        public String getStrcreatetime() {
            return strcreatetime;
        }

        public void setStrcreatetime(String strcreatetime) {
            this.strcreatetime = strcreatetime;
        }

        public int getIisdelete() {
            return iisdelete;
        }

        public void setIisdelete(int iisdelete) {
            this.iisdelete = iisdelete;
        }

        public String getStrdeletetime() {
            return strdeletetime;
        }

        public void setStrdeletetime(String strdeletetime) {
            this.strdeletetime = strdeletetime;
        }

        public String getStrupdatetime() {
            return strupdatetime;
        }

        public void setStrupdatetime(String strupdatetime) {
            this.strupdatetime = strupdatetime;
        }

        public String getStrremark() {
            return strremark;
        }

        public void setStrremark(String strremark) {
            this.strremark = strremark;
        }

        public int getIrank() {
            return irank;
        }

        public void setIrank(int irank) {
            this.irank = irank;
        }

        @Override
        public int getItemType() {
            String strimage = getStrimage();
            String isVideo = Tools.safeString(getIsVideo());
            if ("1".equals(isVideo)) {
                //是视频
                return VIDEO;
            }else {
                if (strimage == null || "".equals(strimage)) {
                    return TEXT;
                } else {
                    return IMG;
                }
            }
        }
        public static final int TEXT = 1;
        public static final int IMG = 2;
        public static final int VIDEO = 3;
        private int itemType;

        public InforModelBean(int itemType) {
            this.itemType = itemType;
        }
    }
}
