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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 资源
 */
@Setter
@Getter
@Entity
@Table(name="t_resource")
public class Resource {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**资源名称*/
    @Column(length=64)
    private String name;
    
    /**资源代码*/
    @Column(length=64)
    private String code;
    
    /**资源描述*/
    @Column(length=256)
    private String description;
    
    /**资源URL*/
    @Column(length=256)
    private String url;
    
    /**上级资源*/
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Resource parent;
    
    /**下级资源*/
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Set<Resource> children = new HashSet<Resource>();
    
    /**
     *哪些角色可以访问
     */
    @ManyToMany 
    @JoinTable(name="t_resource_role",joinColumns=@JoinColumn(name="function_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<Role>();
    
    /**
     * 拥有的操作
     */
    @OneToMany(mappedBy="resource",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<Operator> operators = new HashSet<Operator>();
    
}
