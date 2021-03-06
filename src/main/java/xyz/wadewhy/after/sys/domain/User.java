package xyz.wadewhy.after.sys.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.loginname
     *
     * @mbg.generated
     */
    private String loginname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.address
     *
     * @mbg.generated
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.sex
     *
     * @mbg.generated
     */
    private Integer sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.pwd
     *
     * @mbg.generated
     */
    private String pwd;



    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.hiredate
     *
     * @mbg.generated
     */
    private Date hiredate;



    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.available
     *
     * @mbg.generated
     */
    private Integer available;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.ordernum
     *
     * @mbg.generated
     */
    private Integer ordernum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.type
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.imgpath
     *
     * @mbg.generated
     */
    private String imgpath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.salt
     *
     * @mbg.generated
     */
    private String salt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.id
     *
     * @return the value of sys_user.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.id
     *
     * @param id the value for sys_user.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.name
     *
     * @return the value of sys_user.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.name
     *
     * @param name the value for sys_user.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.loginname
     *
     * @return the value of sys_user.loginname
     *
     * @mbg.generated
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.loginname
     *
     * @param loginname the value for sys_user.loginname
     *
     * @mbg.generated
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.address
     *
     * @return the value of sys_user.address
     *
     * @mbg.generated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.address
     *
     * @param address the value for sys_user.address
     *
     * @mbg.generated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.sex
     *
     * @return the value of sys_user.sex
     *
     * @mbg.generated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.sex
     *
     * @param sex the value for sys_user.sex
     *
     * @mbg.generated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.remark
     *
     * @return the value of sys_user.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.remark
     *
     * @param remark the value for sys_user.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.pwd
     *
     * @return the value of sys_user.pwd
     *
     * @mbg.generated
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.pwd
     *
     * @param pwd the value for sys_user.pwd
     *
     * @mbg.generated
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }



    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.hiredate
     *
     * @return the value of sys_user.hiredate
     *
     * @mbg.generated
     */
    public Date getHiredate() {
        return hiredate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.hiredate
     *
     * @param hiredate the value for sys_user.hiredate
     *
     * @mbg.generated
     */
    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }



    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.available
     *
     * @return the value of sys_user.available
     *
     * @mbg.generated
     */
    public Integer getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.available
     *
     * @param available the value for sys_user.available
     *
     * @mbg.generated
     */
    public void setAvailable(Integer available) {
        this.available = available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.ordernum
     *
     * @return the value of sys_user.ordernum
     *
     * @mbg.generated
     */
    public Integer getOrdernum() {
        return ordernum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.ordernum
     *
     * @param ordernum the value for sys_user.ordernum
     *
     * @mbg.generated
     */
    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.type
     *
     * @return the value of sys_user.type
     *
     * @mbg.generated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.type
     *
     * @param type the value for sys_user.type
     *
     * @mbg.generated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.imgpath
     *
     * @return the value of sys_user.imgpath
     *
     * @mbg.generated
     */
    public String getImgpath() {
        return imgpath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.imgpath
     *
     * @param imgpath the value for sys_user.imgpath
     *
     * @mbg.generated
     */
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath == null ? null : imgpath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.salt
     *
     * @return the value of sys_user.salt
     *
     * @mbg.generated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.salt
     *
     * @param salt the value for sys_user.salt
     *
     * @mbg.generated
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
}