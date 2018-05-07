package org.qql.vigour.framework.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * 角色
 */
@Setter
@Getter
@Entity
@Table(name="t_role")
public class Role {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**角色名称*/
    @Column(length=64)
    private String name;
    
    /**角色代码*/
    @Column(length=64)
    private String code;
    
    /**角色描述*/
    @Column(length=256)
    private String description;
    
    /**
     * 被分配给的用户
     */
    @ManyToMany(mappedBy="roles")
    private Set<User> users = new HashSet<User>();
    
    /**
     * 可以访问的资源
     */
    @ManyToMany(mappedBy="roles")
    private Set<Resource> resources = new HashSet<Resource>();
    
    /**
     * 可以对访问的资源进行的操作
     */
    @OneToMany(mappedBy="role",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<Operator> operators = new HashSet<Operator>();
    
}
