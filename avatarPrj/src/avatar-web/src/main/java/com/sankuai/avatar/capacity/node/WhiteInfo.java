package com.sankuai.avatar.capacity.node;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.capacity.constant.WhiteApp;
import lombok.*;

import java.util.Date;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhiteInfo {
    public WhiteApp whiteApp;
    public String cName;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    public Date endTime;
    public String reason;
}
