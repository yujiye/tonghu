package com.tonghu.pub.common.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: ListUtils
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年9月28日 下午6:32:28
 */
public class ListUtils {

	/**
	 * 去除集合中的空元素
	 * @param list
	 */
	public static void removeEmptyElement(List<?> list){
		if(list != null && !list.isEmpty()){
			for(int i = 0; i<list.size();i++){
				if(list.get(i) == null){
					list.remove(i);
				}else{
					if(list.get(i) instanceof String){
						if(StringUtils.isEmpty((String)list.get(i)))
							list.remove(i);
					}
				}
			}
		}
	}
	
	/** 
	 * 判断list 集合 是否为空
	 */
	public static <T>   boolean  isEmpty(List<T> list){
		if(null == list ||  list.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static <T>   boolean  isNotEmpty(List<T> list){
		if(null != list && !list.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * 找出两个List的相同数据封装成一个新的List 
	 * @param <T>
	 * @param list1
	 * @param list2
	 * @return
	 * @throws 
	 * yuhongbo 2017-9-16
	 */
	public static <T>   List<T>  buildSameList(List<T> list1,List<T> list2){
		
		List<T> t = new ArrayList<T>();
		if (isEmpty(list1) || isEmpty(list2)) {
			return t;
		}
		
		for (T t1 : list1) {
			if (null == t1) {
				continue;
			}
			for (T t2 : list1) {
				if (t1.equals(t2)) {
					t.add(t1);
					break;
				}
			}
		}
		return t;
		
	}
	/**
	 * 找出两个List的相同数据封装成一个新的List 
	 * @param <T>
	 * @param list1
	 * @param list2
	 * @return
	 * @throws 
	 * yuhongbo 2017-9-16
	 */
	public static  <T>  boolean  containsObject(List<T> list1,T o){

		if (isEmpty(list1) || null == o) {
			return false;
		}
		
		boolean flag = false;
		for (T t1 : list1) {
			if (null == t1) {
				continue;
			}
			if (o.equals(t1)) {
				flag = true;
				break;
			}
		}
		
		return flag;

	}
	
	/**
	 * 
	 * @Description: 将List中的元素转化成一个字符串,元素之间的分隔符使用指定的分隔符
	 * @param list 元素列表
	 * @param separator 分隔符
	 * @return 
	 * 1、如果list为null，则返回null
	 * 2、如果list的长度是0，则返回空字符串
	 * 3、如果list中的元素是【ele1】,分隔符是";",则返回ele1
	 * 4、如果list中的元素是【ele1,ele2,ele3】,分隔符是";",则返回ele1;ele2;ele3
	 * @return String
	 * @author xuhaifang
	 * @date 2017-7-28 下午05:50:38
	 * @version V1.0
	 */
	public static String toString(List<String> list, String separator) {
		
		if(null == list) {
			return null;
		}
		if(list.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(list.get(0));
		for(int i = 1; i < list.size(); i++) {
			sb.append(separator).append(list.get(i));
		}
		return sb.toString();
	}
	
	
	/**
	 * @Description: 将类型是Long的List转换成类型是String的List
	 * @param srcList
	 * @return
	 * @return List<String> 
	 * 1、如果srcList为null，则返回长度为0的List
	 * 2、如果srcList的长度是0，则返返回长度为0的List
	 * 3、如果srcList的长度大于0，则返返回大于0的List
	 * @author xuhaifang
	 * @date 2017-8-19 下午04:41:37
	 * @version V1.0
	 */
	public static List<String> toStringList(List<Long> srcList) {
		
		if(ListUtils.isEmpty(srcList)) {
			return new ArrayList<String>();
		}
		
		List<String> strList = new ArrayList<String>(srcList.size());
		for(Long longObj : srcList) {
			if(longObj != null){
				strList.add(longObj.toString());
			}
		}
		return strList;
	}
	
	/**
     * 将long[]型数组转换为Long[]数组
     * 
     * @param array
     *            长整型数组
     * @return 长整型集合
     */
    public static List<Long> toLongList(final Long[] array)
    {
        final List<Long> arryList = new ArrayList<Long>();
        if (ArrayUtils.isEmpty(array))
        {
            return arryList;
        }

        for (final Long element : array)
        {
            arryList.add(element);
        }
        return arryList;
    }
	
	
	public static List<Long> toLongList(List<Integer> srcList) {
		
		if(ListUtils.isEmpty(srcList)) {
			return new ArrayList<Long>();
		}
		
		List<Long> destList = new ArrayList<Long>(srcList.size());
		for(Integer longObj : srcList) {
			if(longObj != null){
				destList.add(longObj.longValue());
			}
		}
		return destList;
	}
	
	
	public static List<Integer> toIntegerList(List<Long> srcList) {
		
		if(ListUtils.isEmpty(srcList)) {
			return new ArrayList<Integer>();
		}
		
		List<Integer> destList = new ArrayList<Integer>(srcList.size());
		for(Long longObj : srcList) {
			if(longObj != null){
				destList.add(longObj.intValue());
			}
		}
		return destList;
	}
	
	public static Long[] toLongArrays(List<Long> srcList) {
		
		if(ListUtils.isEmpty(srcList)) {
			return new Long[0];
		}
		
		List<Long> destList = new ArrayList<Long>(srcList.size());
		for(Long longObj : srcList) {
			if(longObj != null){
				destList.add(longObj.longValue());
			}
		}
		
		return destList.toArray(new Long[destList.size()]);
	}
	
	public static Integer[] toIntegerArrays(List<Integer> srcList) {
		
		if(ListUtils.isEmpty(srcList)) {
			return new Integer[0];
		}
		
		List<Integer> destList = new ArrayList<Integer>(srcList.size());
		for(Integer longObj : srcList) {
			if(longObj != null){
				destList.add(longObj);
			}
		}
		
		return destList.toArray(new Integer[destList.size()]);
	}
	
	/**
	 * @Description: 对List进行分组，
	 * @param <T>
	 * @param list ： 要分组的List
	 * @param groupSize ：组大小
	 * @return ： 
	 * 1、List是null,返回长度是0的List
	 * 2、List长度是0.,返回长度是0的List
	 * 3、groupSize<=0,返回长度是0的List
	 * 4、根据gourpSize对List进行分组，
	 * @return List<List<T>>
	 * @author xuhaifang
	 * @date 2017-09-245 上午10:16:48
	 * @version V1.0
	 */
	public static <T> List<List<T>> groupList(List<T> list, int groupSize) {
		
		if(ListUtils.isEmpty(list)) {
			return new ArrayList<List<T>>();
		}
		
		if(groupSize <= 0) {
			return Arrays.asList(list);
		}
		
		int total = list.size();
		int groupTotal = (total % groupSize == 0) ? (total / groupSize) : (total / groupSize + 1);
		List<List<T>> groupList = new ArrayList<List<T>>(groupTotal);
		for(int i = 0; i < groupTotal; i++) {
			int startIndex = groupSize * i;
			int endIndex = ((i + 1) * groupSize);
			if(i == groupTotal - 1) {
				endIndex = total;
			}
			groupList.add(list.subList(startIndex, endIndex));
		}
		
		return groupList;
	}
	
	public static void main(String[] args) {
		
		System.out.println(groupList(null, 7));
		System.out.println(groupList(new ArrayList<Integer>(), 7));
		System.out.println(groupList(Arrays.asList(1,2,3), -7));
		System.out.println(groupList(Arrays.asList(1,2,3), 0));
		
		System.out.println(groupList(Arrays.asList(1,2,3,4,5,6,7,8), 2));
		System.out.println(groupList(Arrays.asList(1,2,3,4,5,6,7,8), 3));
		System.out.println(groupList(Arrays.asList(1,2,3,4,5,6,7,8), 4));
	}
}
