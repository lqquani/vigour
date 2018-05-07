package org.qql.vigour.framework.i18n;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;


/**
 * 国际化资源工具类
 **/
public class LocaleUtils {
	private static Logger log = LoggerFactory.getLogger(LocaleUtils.class);
    private final static String CHINA = "zh_cn";

    private static LocaleResolver localeResolver;
    private static MessageSource messageSource;

    static {
    }
    
   @Autowired
   public LocaleUtils(LocaleResolver localeResolver, MessageSource messageSource) {
        LocaleUtils.localeResolver = localeResolver;
        LocaleUtils.messageSource = messageSource;
    }

	/**
     * 根据当前request对象中的locale(Header的Accept属性)初始化系统国际化语言区域环境
     *
     * @param request  当前请求对象
     * @param response 当前响应对象
     */
    public static void setInitLocale(final HttpServletRequest request, final HttpServletResponse response) {
        final Locale locale = request.getLocale();
        log.info("Init locale from user request,country:{},lang:{}", locale.getCountry(),toLanguageTag(locale.toString()));//locale.toLanguageTag()

        if (localeResolver instanceof CookieLocaleResolver) {
            final CookieLocaleResolver cookieLocaleResolver = (CookieLocaleResolver)localeResolver;
            final Cookie cookie = WebUtils.getCookie(request, cookieLocaleResolver.getCookieName());
            if (cookie == null) {
                setLocale(toLanguageTag(locale.toString()), request, response);
            }
        }
        if (localeResolver instanceof SessionLocaleResolver) {
            final Locale sessionLocale = (Locale)WebUtils.getRequiredSessionAttribute(
                request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
            if (sessionLocale == null) {
                setLocale(toLanguageTag(locale.toString()), request, response);
            }
        }
    }

    /**
     * 设置国际化语言区域环境
     *
     * @param lang     国际化语言名称
     * @param request  当前请求对象
     * @param response 当前响应对象
     */
    public static void setLocale(String lang, final HttpServletRequest request, final HttpServletResponse response) {
        lang = StringUtils.defaultIfEmpty(lang, CHINA).toLowerCase();
        Locale[] locales = Locale.getAvailableLocales();  
        localeResolver.setLocale(request, response, Locale.CHINA);
        for (Locale locale : locales) {  
            if (toLanguageTag(locale.toString()).equalsIgnoreCase(lang)) {  
            	localeResolver.setLocale(request, response, locale);
            	break;
            } 
        }  
    }

    /**
     * @param code 对应messages配置的key.
     * @return String
     */
    public static String getMessage(final String code) {
        return getMessage(code, null);
    }

    /**
     * @param code 对应messages配置的key.
     * @param args 数组参数.
     * @return String
     */
    public static String getMessage(final String code, final Object[] args) {
        return getMessage(code, args, "");
    }

    /**
     * @param code           对应messages配置的key.
     * @param args           数组参数.
     * @param defaultMessage 没有设置key的时候的默认值.
     * @return String
     */
    public static String getMessage(final String code, final Object[] args, final String defaultMessage) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    public static String toLanguageTag(String lan){
    	return lan.replace("_", "-");
    }
}
