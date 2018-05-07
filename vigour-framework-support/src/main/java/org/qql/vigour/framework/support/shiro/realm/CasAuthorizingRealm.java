package org.qql.vigour.framework.support.shiro.realm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Component
public class CasAuthorizingRealm extends AuthorizingRealm {


	public CasAuthorizingRealm() {
//		setAuthenticationTokenClass(BioneAuthenticationToken.class);
	}

	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
//		BioneAuthenticationToken bioneToken = (BioneAuthenticationToken) token;
//		if (token == null) {
//			return null;
//		}
//		boolean isSuperUser = bioneToken.getIsSuper();
//		// 获取登录用户的信息
//		BioneUserInfo user = userBS.getUserInfo(
//				GlobalConstants.SUPER_LOGIC_SYSTEM, bioneToken.getUsername());
//		if (user == null) {
//			throw new UnknownAccountException();
//		}
//		String ticket = (String) bioneToken.getTicket();
//		String tnt = bioneToken.getTnt();
//		BioneAuthInfoCAS authInfoCas = authConfigBS.getEntityByProperty(
//				BioneAuthInfoCAS.class, "authTypeNo",
//				GlobalConstants.AUTH_TYPE_LOCAL_CAS);
		String protocol = "";
//		if (authInfoCas.getServerProtocol().equals("02")) {
//			protocol = "http";
//		} else {
//			protocol = "https";
//		}
//		if (!StringUtils.isBlank(ticket)) {
//			// 当票据存在时
//			String serverUrl = protocol + "://" + authInfoCas.getIpAddress()
//					+ ":" + authInfoCas.getServerPort() + "/"
//					+ authInfoCas.getServerName() + "/serviceValidate";
//			try {
//				boolean isValidate = casAuthBS.validServiceTicket(serverUrl,
//						ticket, "");
//				if (!isValidate) {
//					throw new IncorrectCredentialsException("登录失败,账号或密码错误");
//				}
//			} catch (IOException e) {
//				throw new AuthenticationException("认证服务器连接异常" + e.getMessage());
//			}
//		} else {
//			String serverUrl = protocol + "://" + authInfoCas.getIpAddress()
//					+ ":" + authInfoCas.getServerPort() + "/"
//					+ authInfoCas.getServerName() + "/v1/tickets";
//			try {
//				tnt = casAuthBS.getTicketGrantingTicket(serverUrl,
//						bioneToken.getUsername(),
//						String.valueOf(bioneToken.getPassword()));
//				if (!StringUtils.isEmpty(tnt)) {
//					ticket = casAuthBS.getServiceTicket(serverUrl, tnt,
//							CasAuthBS.SERVICE_KEY);
//				}
//			} catch (IOException e) {
//				throw new AuthenticationException("认证服务器连接异常" + e.getMessage());
//			}
//			if (StringUtils.isBlank(ticket)) {
//				throw new IncorrectCredentialsException();
//			}
//		}
//		BioneUser bioneUser = new BioneUser();
//		bioneUser.setUserId(user.getUserId());
//		bioneUser.setUserName(user.getUserName());
//		bioneUser.setLoginName(user.getUserNo());
//		bioneUser.setCurrentLogicSysNo(bioneToken.getLogicSysNo());
//		bioneUser.setSuperUser(isSuperUser);
//		bioneUser.setAuthTypeNo(GlobalConstants.AUTH_TYPE_LOCAL_CAS);
//		bioneUser.setTicket(ticket);
//		bioneUser.setTnt(tnt);
//		bioneUser.setOrgNo(user.getOrgNo());
//		bioneUser.setDeptNo(user.getDeptNo());
//		bioneUser.setPwdStr(bioneToken.getPasswordstr());
//		bioneUser.setUserAgname(user.getUserAgname());
		return new SimpleAuthenticationInfo("bioneUser",
				"bioneToken.getPassword()", getName());
	}

	/**
	 * 获取当前用户的授权信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		SimpleAuthorizationInfo authzInfo = new SimpleAuthorizationInfo();
//		BioneUser bioneUser = BioneSecurityUtils.getCurrentUserInfo();
//		// 用户所属的机构和部门信息
//		if (!StringUtils.isBlank(bioneUser.getOrgNo())) {
//			BioneOrgInfo orgInfo = this.orgBS.findOrgInfoByOrgNo(
//					bioneUser.getOrgNo(), GlobalConstants.SUPER_LOGIC_SYSTEM);
//			if (orgInfo != null) {
//				bioneUser.setOrgNo(orgInfo.getOrgNo());
//				bioneUser.setOrgName(orgInfo.getOrgName());
//			}
//		}
//
//		if (!StringUtils.isBlank(bioneUser.getDeptNo())) {
//			BioneDeptInfo deptInfo = this.deptBS.findDeptInfoByOrgNoandDeptNo(
//					bioneUser.getOrgNo(), bioneUser.getDeptNo(),
//					GlobalConstants.SUPER_LOGIC_SYSTEM);
//			if (deptInfo != null) {
//				bioneUser.setDeptNo(deptInfo.getDeptNo());
//				bioneUser.setDeptName(deptInfo.getDeptName());
//			}
//		}
//		// 获取用户的授权对象信息
//		bioneUser.setAuthObjMap(this.authBS.findAuthObjUserRelMap(
//				bioneUser.getCurrentLogicSysNo(), bioneUser.getUserId()));
//
//		Set<String> allPermissions = Sets.newHashSet();
//		List<BioneAuthObjResRel> authObjResRelList = Lists.newArrayList();
//
//		// 查询通过用户授权的资源列表
//		List<String> objIdList = new ArrayList<String>();
//		objIdList.add(bioneUser.getUserId());
//		List<BioneAuthObjResRel> userResRelList = this.authBS
//				.findCurrentUserAuthObjResRels(
//						bioneUser.getCurrentLogicSysNo(),
//						GlobalConstants.AUTH_OBJ_DEF_ID_USER, objIdList);
//
//		if (userResRelList != null)
//			authObjResRelList.addAll(userResRelList);
//
//		// 获取授权组权限
//		List<String> authGrpIdList = this.authBS
//				.findAuthGroupIdOfCurrentUser(bioneUser.getCurrentLogicSysNo());
//
//		List<BioneAuthObjResRel> grpResRelList = this.authBS
//				.findCurrentUserAuthObjResRels(
//						bioneUser.getCurrentLogicSysNo(),
//						GlobalConstants.AUTH_OBJ_DEF_ID_GROUP, authGrpIdList);
//
//		if (grpResRelList != null)
//			authObjResRelList.addAll(grpResRelList);
//
//		// 通过其他授权对象获取授权资源
//		Map<String, List<String>> userAuthObjMap = bioneUser.getAuthObjMap();
//		Iterator<String> it = userAuthObjMap.keySet().iterator();
//
//		String objDefNo = null;
//		List<String> authObjIdList = null;
//		while (it.hasNext()) {
//
//			objDefNo = it.next();
//			authObjIdList = userAuthObjMap.get(objDefNo);
//
//			if (authObjIdList != null) {
//				List<BioneAuthObjResRel> objDefResRelList = this.authBS
//						.findCurrentUserAuthObjResRels(
//								bioneUser.getCurrentLogicSysNo(), objDefNo,
//								authObjIdList);
//				if (objDefResRelList != null)
//					authObjResRelList.addAll(objDefResRelList);
//			}
//		}
//
//		// 将用户所拥有权限的资源按照资源类型分组
//		Map<String, List<BioneAuthObjResRel>> resDefGroupMap = Maps
//				.newHashMap();
//
//		String resDefNo = null;
//		List<BioneAuthObjResRel> authObjResList = null;
//
//		for (BioneAuthObjResRel authObjResRel : authObjResRelList) {
//
//			resDefNo = authObjResRel.getId().getResDefNo();
//			authObjResList = resDefGroupMap.get(resDefNo);
//
//			if (authObjResList == null) {
//
//				authObjResList = Lists.newArrayList();
//				resDefGroupMap.put(resDefNo, authObjResList);
//			}
//
//			authObjResList.add(authObjResRel);
//		}
//
//		Iterator<String> resDefIt = resDefGroupMap.keySet().iterator();
//
//		while (resDefIt.hasNext()) {
//
//			resDefNo = resDefIt.next();
//			authObjResList = resDefGroupMap.get(resDefNo);
//
//			BioneAuthResDef adminAuthResDef = this.authBS.getEntityByProperty(
//					BioneAuthResDef.class, "resDefNo", resDefNo);
//			if (adminAuthResDef != null) {
//				try {
//					IResObject reObj = (IResObject) SpringContextHolder
//							.getBean(adminAuthResDef.getBeanName());
//					List<String> resSermissions = reObj
//							.doGetResPermissions(authObjResList);
//
//					if (resSermissions != null)
//						allPermissions.addAll(resSermissions);
//				} catch (org.springframework.beans.factory.NoSuchBeanDefinitionException e) {
//					// 跳过不存在的已定义授权资源bean
//					continue;
//				}
//			}
//
//		}

//		authzInfo.setStringPermissions(allPermissions);

		return authzInfo;

	}

}
