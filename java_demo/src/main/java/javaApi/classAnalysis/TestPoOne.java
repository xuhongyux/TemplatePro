package javaApi.classAnalysis;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xuhongyu
 * @describe
 * @create 2021-09-07-11:05 上午
 */

@Data
public class TestPoOne {

    private String str;

    private Long aLong;

    private boolean aBoolean;

    private List<String> lists;

    private  Object obj;

    private String one;

    private LocalDateTime localDateTime;
}