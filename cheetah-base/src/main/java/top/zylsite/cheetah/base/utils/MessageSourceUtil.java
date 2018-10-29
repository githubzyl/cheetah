package top.zylsite.cheetah.base.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageSourceUtil {

	protected static MessageSource messageSource = SpringUtil.getBean(MessageSource.class);

	public static String getMessage(String property) {
		return messageSource.getMessage(property, null, getDefaultLocale());
	}

	public static String getMessage(String property, Object[] args) {
		return messageSource.getMessage(property, args, getDefaultLocale());
	}

	public static String getMessage(String property, Locale locale) {
		return messageSource.getMessage(property, null, locale);
	}

	public static String getMessage(String property, Object[] args, Locale locale) {
		return messageSource.getMessage(property, args, locale);
	}

	private static Locale getDefaultLocale() {
		return LocaleContextHolder.getLocale();
	}

}
