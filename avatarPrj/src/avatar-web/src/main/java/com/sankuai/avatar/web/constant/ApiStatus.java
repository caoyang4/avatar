package com.sankuai.avatar.web.constant;

import lombok.Getter;

@Getter
public enum ApiStatus {
        /**
         * 操作成功
         */
        OK(0, "操作成功"),

        /**
         * 未知异常
         */
        SUPPORT_ERROR(-2, "请求失败: "),


        DUPLICATE_ERROR(-3, "请勿重复添加相同数据"),

        /**
         * 未知异常
         */
        UNKNOWN_ERROR(500,"服务器出错啦");

        /**
         * 状态码
         */
        private Integer code;
        /**
         * 内容
         */
        private String message;

        ApiStatus(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode(){
                return this.code;
        }

        public String getMessage(){
                return this.message;
        }
}
