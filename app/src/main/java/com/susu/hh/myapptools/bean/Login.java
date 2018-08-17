package com.susu.hh.myapptools.bean;

/**
 * 作者：sucheng on 2017/12/14 09:25
 * "token":授权token串,
 * "type":1食材 2二手,
 * "id":数据的id,
 * "title":标题名称,
 * "price":价格,
 * "number": 数量
 */

public class Login {
    String id;
    String phone;
    String token;
    String title;
    String price;
    String number;
    String fileByte;
    String rows;
    String keyword;
    String page;
    String address;
    String name;
    String district;
    String billname;
    String billcode;
    String type;
    String code;
    String data;
    String msg;
    String totalamount;
    String company;
    String subject;
    String cartnum;
    String dataid;
    String specid;//规格
    String careerid;
    String areaid;

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getCareerid() {
        return careerid;
    }

    public void setCareerid(String careerid) {
        this.careerid = careerid;
    }

    public String getSpecid() {
        return specid;
    }

    public void setSpecid(String specid) {
        this.specid = specid;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getCartnum() {
        return cartnum;
    }

    public void setCartnum(String cartnum) {
        this.cartnum = cartnum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }
    /*"type":类型的id，空表示所有类型,
"code":排序类型：1价格 2销量
*/

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getBillcode() {
        return billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getFileByte() {
        return fileByte;
    }

    public void setFileByte(String fileByte) {
        this.fileByte = fileByte;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", number='" + number + '\'' +
                ", fileByte='" + fileByte + '\'' +
                ", rows='" + rows + '\'' +
                ", keyword='" + keyword + '\'' +
                ", page='" + page + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", district='" + district + '\'' +
                ", billname='" + billname + '\'' +
                ", billcode='" + billcode + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                ", totalamount='" + totalamount + '\'' +
                ", company='" + company + '\'' +
                ", subject='" + subject + '\'' +
                ", cartnum='" + cartnum + '\'' +
                ", dataid='" + dataid + '\'' +
                ", specid='" + specid + '\'' +
                ", careerid='" + careerid + '\'' +
                ", areaid='" + areaid + '\'' +
                '}';
    }
}
