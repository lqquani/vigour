package org.qql.vigour.framework.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="t_organization")
public class Organization {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**组织名称*/
    @Column(length=64)
    private String name;
    
    /**组织编码*/
    @Column(length=64)
    private String code;
    
    /**父组织*/
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Organization parent;
    
    /**子组织*/
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Set<Organization> children = new HashSet<Organization>();
    
    /**
     * 拥有的用户
     */
    @OneToMany(mappedBy="organization",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<User> users = new HashSet<User>();
        
}
