package com.example.mybatisplus.model.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 注意数据库进行排序时不要用QueryWrapper,因为不能对中文进行排序
 */
@Data
public class SortDTO {
    private String prop;
    private String order;
    private Set<String> propSet;
    public SortDTO(){
        propSet = new HashSet<>();
        propSet.add("name");
        propSet.add("barcode");
        propSet.add("inventory");
        propSet.add("purchase_price");
        propSet.add("price");
        propSet.add("wholesale_price");
        propSet.add("supplier.name");
        propSet.add("produce_date");
        propSet.add("expiration_time");
        propSet.add("create_time");
        propSet.add("id");
        propSet.add("point");
        propSet.add("number");
    }
    /**
     * 转换大写字母为下划线加小写字母
     */
    private void convertProp(){
        if (prop == null) {
            return;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < prop.length(); i++) {
            char c = prop.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        prop = result.toString();
    }
    /**
     * 转化前端传来的字段为sql查询需要的形式，并判断是否能进行排序
     * @return 能排序返回true,否则false
     */
    public boolean isSortable(){
        convertProp();//转换大写字母为下划线加小写字母
        boolean flag = false;
        if(order == null) return false;
        if(order.equals("ascending")){
            order = "asc";
            flag = propSet.contains(prop);
        }else if(order.equals("descending")){
            order = "desc";
            flag = propSet.contains(prop);
        }
        return flag;
    }
    /**
     * 针对商品转化前端传来的字段为sql查询需要的形式，并判断是否能进行排序
     * @return 能排序返回true,否则false
     */
    public boolean isContain(){
        boolean flag = isSortable();
        if(!prop.equals("supplier.name")) {
            prop = "commodity." + prop;
        }
        return flag;
    }

}
