package com.wode.factory.model; 
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.management.relation.Role;
import javax.persistence.Column;

 public class InnerUser implements Serializable{
	 
	 private static final long serialVersionUID = 1L;
	 
	 
   private String id;
   
   @Column
   private String email;
   @Column
   private String password;
   @Column
   private String niname;
   @Column
   private String realname;
   @Column
   private String pic;
   @Column
   private Integer gender;
   @Column
   private Date registtime;
   @Column
   private Date logintime;
   @Column
   private String ip;
   @Column
   public String depid;
  
   private Short sysAdmin;
   private List<Role> roles;
   
   
   

	public String getId() {
	return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNiname() {
		return niname;
	}

	public void setNiname(String niname) {
		this.niname = niname;
	}

	public String getPic() {
		return pic;
	}
	
	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getRegisttime() {
		return registtime;
	}

	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	
	public String getDepid() {
		return depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InnerUser other = (InnerUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Short getSysAdmin() {
		return sysAdmin;
	}

	public void setSysAdmin(Short sysAdmin) {
		this.sysAdmin = sysAdmin;
	}
	
	
	
	
 }

