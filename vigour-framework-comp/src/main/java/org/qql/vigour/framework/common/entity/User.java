package org.qql.vigour.framework.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * 用户
 */
@Setter
@Getter
@Entity
@Table(name="t_user")
public class User {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**账号*/
    @Column(length=32)
    private String account;
    
    /**密码*/
    @Column(length=32)
    private String password;
    
    /**姓名*/
    @Column(length=32)
    private String name;
    
    /**性别*/
    @Column(length=4)
    private String gender;
    
    /**邮箱*/
    @Column(length=32)
    private String email;
    
    /**手机号码*/
    @Column(length=20)
    private String mobileNumber;
    
    /**
     * 所属组织
     */
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="organization_id")
    private Organization organization;
    
    /**
     * 拥有的角色
     */
    @ManyToMany 
    @JoinTable(name="t_user_role",joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<Role>();
    
}
