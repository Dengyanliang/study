package com.deng.study.java.java8.lambda;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/10/12 18:58
 */
public class MenuTest {

    public static void main(String[] args) {
        List<Menu> menus = menusList();

        List<List<Menu>> result = new ArrayList<>();

        // 先按照parentId进行排序
        menus.stream().sorted((x,y)->{
            if(x.getParentId() < y.getParentId()){
                return 1;
            }else if(x.getParentId() == y.getParentId()){
                return 2;
            }else{
                return 3;
            }
        })
                .collect(Collectors.groupingBy(Menu::getParentId)).values()  // 按照parentId进行分组
                .forEach(value->{  // 对分组后的数据再进行排序
                    List<Menu> collect = value.stream().sorted((x, y) -> {
                        if (x.getId() < y.getId()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }).collect(Collectors.toList());
                    result.add(collect);
                }
        );

        System.out.println(result);
    }

    private static List<Menu> menusList(){
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(2L, "资源列表", 1L));
        menus.add(new Menu(1L, "资源管理", 0L));
        menus.add(new Menu(7L, "用户管理", 0L));
        menus.add(new Menu(3L, "资源组列表", 1L));
        menus.add(new Menu(6L, "角色组列表", 4L));
        menus.add(new Menu(4L, "角色管理", 0L));
        menus.add(new Menu(5L, "角色列表", 4L));
        menus.add(new Menu(0L, "授权中心", -1L));
        menus.add(new Menu(8L, "用户列表", 7L));

        return menus;
    }
}

@Data
class Menu{
    private long id;
    private String name;
    private long parentId;
    private List<Menu> subList;

    public Menu(){

    }

    public Menu(long id, String name, long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
