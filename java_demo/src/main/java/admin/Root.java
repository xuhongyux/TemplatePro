package admin;

import lombok.Builder;
import lombok.Data;

/**
 * @author xuhongyu
 * @describe
 * @create 2021-01-13-14:41
 */
@Builder
@Data
public class Root extends Persson {
    private  String name;
}
