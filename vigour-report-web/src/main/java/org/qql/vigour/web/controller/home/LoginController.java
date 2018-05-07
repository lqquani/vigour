package org.qql.vigour.web.controller.home;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.qql.vigour.framework.annotation.Dbsource;
import org.qql.vigour.framework.common.DynamicDataSource;
import org.qql.vigour.framework.holder.DataSourceHolder;
import org.qql.vigour.framework.holder.SpringContextHolder;
import org.qql.vigour.framework.repository.jdbc.JDBCBaseDAO;
import org.qql.vigour.framework.repository.jpa.JPABaseDAO;
import org.qql.vigour.web.service.EventService;
import org.qql.vigour.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户登录页控制器
 *
 */
//@Dbsource("1")
@Slf4j
@Controller
@RequestMapping(value = "/")
public class LoginController {

	@Autowired
	protected JPABaseDAO baseDAO;
	@Autowired
	protected JDBCBaseDAO jdbcBaseDAO;
	
	@Autowired
	@Qualifier("vigourDataSource")
	private DataSource vigourDataSource;
	
	@Dbsource("1")
	private DataSource dataSource;
	
//	@Autowired
//	private DataSource datasource;
	
	 @Resource
	private EventService eventService;
	 @Resource
	 private TestService testService;
	 
	 @Autowired
	 private CacheManager cacheManager;
	 
    @GetMapping("/login/{lang}")
    public ModelAndView language(@PathVariable final String lang, final HttpServletRequest request,
                                 final HttpServletResponse response) {
        return new ModelAndView("redirect:/");
    }

//    @Dbsource("2")
    @GetMapping(value = {"/login"})
    @Cacheable(value="demo",key="#model + 'findById'")  
    public String login(final Model model, final HttpServletRequest request, final HttpServletResponse response) {
    	try {
    		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
    		List<Map<String, Object>> list=jdbcTemplate.queryForList("select * from dual");
    		System.out.println(list);
    		baseDAO.findPageWithNameParamByNativeSQL(1,10,"select * from dual", null);
//    		Random d=new Random();
//    		DataSourceHolder.setDataSourceId(""+d.nextInt(2));
//    		this.eventService.add("authenticate", "admin", "用户登录", "INFO", "/user/duuu");
//    		this.testService.save("authenticate", "admin", "用户登录", "INFO", "/user/duuu");
//    		Connection conn=dataSource.getConnection();
//    		System.out.println(conn);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
    	List<Object[]> t=this.baseDAO.findByNativeSQLWithIndexParam("select * from dual");
//    	
//    	 new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 for (int x=100;x<120;x++){
//                     DataSourceHolder.setDataSourceId(""+x);
//                     try {
//						DataSource dynamicDataSource=SpringContextHolder.getBean("dynamicDataSource");
//						JdbcTemplate jdbcTemplate=new JdbcTemplate(dynamicDataSource);
//						Connection conn=dynamicDataSource.getConnection();
//					} catch (Exception e) {
//						System.out.println("xxxxx:"+e.getMessage());
//					}
//     				
//                 }
//             }
//         },"线程2").start();
    	 
//    	for(int i=10;i<30;i++){
//    		DataSourceHolder.setDataSourceId(""+i);
//        	try {
//				DataSource dynamicDataSource=SpringContextHolder.getBean("dynamicDataSource");
//				JdbcTemplate jdbcTemplate=new JdbcTemplate(dynamicDataSource);
//				Connection conn=dynamicDataSource.getConnection();
//				
//				
////				List<?> t1=jdbcTemplate.queryForList("select * from dual");
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				try {
//					Thread.sleep(1000);
//					DataSource dynamicDataSource=SpringContextHolder.getBean("dynamicDataSource");
//					Connection conn=dynamicDataSource.getConnection();
//				} catch (Exception e1) {
//					System.out.println(e1.getMessage());
//				}
//				 
//				
//			}
//    	}
    	
//    	new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int x=100;x<120;x++){
//                    try {
//						DataSource dynamicDataSource=SpringContextHolder.getBean("dynamicDataSource");
//						JdbcTemplate jdbcTemplate=new JdbcTemplate(dynamicDataSource);
//						Connection conn=dynamicDataSource.getConnection();
//					} catch (Exception e) {
//						System.out.println("线程3:"+e.getMessage());
//					}
//    				
//                }
//            }
//        },"线程3").start();
    
       this.baseDAO.createNativeQueryWithIndexParam("select * from dual", null);
    	return "login";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "/login";
    }

    @ResponseBody
    @PostMapping(value = "/authenticate")
    public String authenticate(final String account, final String password, final boolean rememberMe,
                                       final HttpServletRequest req) {
        try {
            final UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            token.setRememberMe(rememberMe);
            final Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (IncorrectCredentialsException | UnknownAccountException ex) {
        } catch (final Exception ex) {
            if ("LockedAccountException".equals(ex.getClass().getSimpleName())) {
            } else if ("ExcessiveAttemptsException".equals(ex.getClass().getSimpleName())) {
            }
        }
        return "true";
    }
}