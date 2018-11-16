package top.zylsite.cheetah.base.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;

/**
 * 反射工具类
 * 
 * @author: zhaoyl
 * @since: 2017年12月14日 上午9:03:07
 * @history:
 */
public class ReflectionUtilEX {
	
	/**
	 * 判断某个类是否存在某个属性, 如果存在，返回它的简单类型，不存在返回null
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static String isExistField(Class<?> clazz, String fieldName) {
		Field[] fields=clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].getName().equals(fieldName))
			{
				return fields[i].getType().getSimpleName();
			}
		}
		return null;
	}

	/**
	 * 获取某个对象的所有属性
	 * 
	 * @param object
	 * @return
	 * @create: 2017年12月14日 上午9:03:43 zhaoyl
	 * @history:
	 */
	public static Field[] getAllFields(Object object) {
		Class<? extends Object> clazz = object.getClass();
		return getAllFields(clazz);
	}

	/**
	 * 将实体类的集合转换成Map集合
	 * 
	 * @param entityList
	 * @return
	 * @create: 2017年12月14日 上午9:06:11 zhaoyl
	 * @history:
	 */
	public static List<Map<String, Object>> convertEntityListToMapList(List<? extends Object> entityList) {
		if (null == entityList || entityList.size() <= 0) {
			return null;
		}
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (Object t : entityList) {
			mapList.add(ReflectionUtilEX.convertEntityToMap(t));
		}
		return mapList;
	}

	/**
	 * 将对象转换成Map
	 * 
	 * @param obj
	 * @return
	 * @create: 2017年12月14日 上午9:11:56 zhaoyl
	 * @history:
	 */
	public static Map<String, Object> convertEntityToMap(Object obj) {
		if (null == obj) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		try {
			Field[] fields = ReflectionUtilEX.getAllFields(obj);
			for (Field field : fields) {
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName())) {
					map.put(field.getName(), field.get(obj));
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 将对象转换成Map
	 * 
	 * @return
	 * @create: 2018年3月22日 下午1:45:23 zhaoyl
	 * @history:
	 */
	public static <T> T convertMapToEntity(Map<String, Object> map, Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			Field[] fields = ReflectionUtilEX.getAllFields(clazz);
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				String fieldType = field.getType().getSimpleName();
				if (!"serialVersionUID".equals(fieldName)) {
					field.set(t, convertFieldType(fieldType, map.get(fieldName)));
				}
			}
			return t;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void copyProperities(Object source, Object target) {
		BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
		copier.copy(source, target, null);
	}

	private static Field[] getAllFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

	private static Object convertFieldType(String type, Object obj) {
		if (null == obj || "".equals(obj)) {
			return null;
		}
		String str = obj.toString();
		if ("String".equals(type)) {
			return str;
		} else if ("Integer".equals(type)) {
			return Integer.parseInt(str);
		} else if ("Long".equals(type)) {
			return Long.parseLong(str);
		} else if ("Double".equals(type)) {
			return Double.parseDouble(str);
		} else if ("Float".equals(type)) {
			return Float.parseFloat(str);
		} else if ("Short".equals(type)) {
			return Short.parseShort(str);
		}
		return obj;
	}
}
