package com.wode.factory.company.query;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wode.common.frame.base.BaseQuery;
import com.wode.factory.model.EnterpriseUser;

/**
 * @author liangkq
 * 福利发放记录Vo
 */
public class GiveBenefitRecordVo extends BaseQuery implements Serializable{

	private static final long serialVersionUID = -6893459202832460322L;

	private Long id;

    /**
     * 员工序号
     */
    private String empNumber;
    /**
     * 福利级别
     */
    private Integer welfareLevel;

    /**
     * 工龄
     */
    private Integer seniority;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */

    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 企业id
     */
    private Long enterpriseId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否已注销  0 是未注销，1是已注销'
     */
    private Byte logout;
    /**
     * 类型，用于区分管理员和员工。   1:管理员，2:员工'
     */
    private Integer type;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 已发放内购券（季度）
     */
    private BigDecimal giveTicketSason ;
    /**
     * 已发放现金储值（季度）
     */
    private BigDecimal giveCashSason ;
    
    /**
     * 员工id
     */
    private Long empId ;
    /**
     * 开始工龄
     */
    private Integer startSeniority;
    /**
     * 结束工龄
     */
    private Integer endSeniority;
    /**
     * 年度
     */
    private String curYear ;
    /**
     * 季度
     */
    private String curSeason;
    
    /**
     * 企业福利季度总额
     */
    private BigDecimal entTicketSasonSum;
    /**
     * 企业现金季度总额
     */
    private BigDecimal entCashSasonSum;
    
    public void setUser(EnterpriseUser user){
    	if(user != null ){
    		this.setEmpId(user.getId());
    		this.setEmpNumber(user.getEmpNumber());
    		this.setWelfareLevel(user.getWelfareLevel());
    		this.setSeniority(user.getSeniority());
    		this.setName(user.getName());
    		this.setSex(user.getSex());
    		this.setAge(user.getAge());
    		this.setUserName(user.getUserName());
    		this.setEnterpriseId(user.getEnterpriseId());
    		this.setEmail(user.getEmail());
    		this.setLogout(user.getLogout());
    		this.setType(user.getType());
    		this.setPhone(user.getPhone());
    		
    	}
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber == null ? null : empNumber.trim();
    }

    public Integer getWelfareLevel() {
        return welfareLevel;
    }

    public void setWelfareLevel(Integer welfareLevel) {
        this.welfareLevel = welfareLevel;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getLogout() {
        return logout;
    }

    public void setLogout(Byte logout) {
        this.logout = logout;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

	public BigDecimal getGiveTicketSason() {
		return giveTicketSason;
	}

	public void setGiveTicketSason(BigDecimal giveTicketSason) {
		this.giveTicketSason = giveTicketSason;
	}

	public BigDecimal getGiveCashSason() {
		return giveCashSason;
	}

	public void setGiveCashSason(BigDecimal giveCashSason) {
		this.giveCashSason = giveCashSason;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Integer getStartSeniority() {
		return startSeniority;
	}

	public void setStartSeniority(Integer startSeniority) {
		this.startSeniority = startSeniority;
	}

	public Integer getEndSeniority() {
		return endSeniority;
	}

	public void setEndSeniority(Integer endSeniority) {
		this.endSeniority = endSeniority;
	}
	public String getCur_year() {
		return curYear;
	}
	public void setCur_year(String cur_year) {
		this.curYear = cur_year;
	}
	public String getCur_season() {
		return curSeason;
	}
	public void setCur_season(String cur_season) {
		this.curSeason = cur_season;
	}
	public String getCurYear() {
		return curYear;
	}
	public void setCurYear(String curYear) {
		this.curYear = curYear;
	}
	public String getCurSeason() {
		return curSeason;
	}
	public void setCurSeason(String curSeason) {
		this.curSeason = curSeason;
	}
	public BigDecimal getEntTicketSasonSum() {
		return entTicketSasonSum;
	}
	public void setEntTicketSasonSum(BigDecimal entTicketSasonSum) {
		this.entTicketSasonSum = entTicketSasonSum;
	}
	public BigDecimal getEntCashSasonSum() {
		return entCashSasonSum;
	}
	public void setEntCashSasonSum(BigDecimal entCashSasonSum) {
		this.entCashSasonSum = entCashSasonSum;
	}
    
}
