package com.wu.framework.easy.excel.util.demo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/08/03 11:20
 */
@Data
public class UserDemo {
    private Integer id;
    private String userName;
    private LocalDate birthday;

    private CityDemo cityDemo;

    private List<RoleDemo> roleDemoList;


    @Data
    public static final class CityDemo {
        private Integer cityId;
        private String cityName;
        private Double lng;
        private Double lat;

    }

    @Data
    public static final class RoleDemo {
        private Integer roleId;
        private String roleName;
        private String roleType;

    }
}
