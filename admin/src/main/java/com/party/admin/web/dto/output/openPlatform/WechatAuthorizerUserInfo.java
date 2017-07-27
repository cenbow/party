package com.party.admin.web.dto.output.openPlatform;

import java.util.List;

/**
 * 授权方的帐号基本信息
 */
public class WechatAuthorizerUserInfo {
    // 授权者信息
    private AuthorizerUserInfo authorizer_info;
    // 授权信息
    private AuthorizationInfo authorization_info;

    public AuthorizerUserInfo getAuthorizer_info() {
        return authorizer_info;
    }

    public void setAuthorizer_info(AuthorizerUserInfo authorizer_info) {
        this.authorizer_info = authorizer_info;
    }

    public AuthorizationInfo getAuthorization_info() {
        return authorization_info;
    }

    public void setAuthorization_info(AuthorizationInfo authorization_info) {
        this.authorization_info = authorization_info;
    }

    class AuthorizerUserInfo {
        // 授权方昵称
        private String nick_name;
        // 授权方头像
        private String head_img;
        // 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
        private ServiceTypeInfo service_type_info;
        // 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，
        // 4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
        private VerifyTypeInfo verify_type_info;
        // 授权方公众号的原始ID
        private String user_name;
        // 公众号的主体名称
        private String principal_name;
        // 授权方公众号所设置的微信号，可能为空
        private String alias;
        // 二维码图片的URL，开发者最好自行也进行保存
        private String qrcode_url;
        private businessInfo business_info;
        // 帐号介绍
        private String signature;
        private MiniProgramInfo MiniProgramInfo;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public ServiceTypeInfo getService_type_info() {
            return service_type_info;
        }

        public void setService_type_info(ServiceTypeInfo service_type_info) {
            this.service_type_info = service_type_info;
        }

        public VerifyTypeInfo getVerify_type_info() {
            return verify_type_info;
        }

        public void setVerify_type_info(VerifyTypeInfo verify_type_info) {
            this.verify_type_info = verify_type_info;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPrincipal_name() {
            return principal_name;
        }

        public void setPrincipal_name(String principal_name) {
            this.principal_name = principal_name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getQrcode_url() {
            return qrcode_url;
        }

        public void setQrcode_url(String qrcode_url) {
            this.qrcode_url = qrcode_url;
        }

        public businessInfo getBusiness_info() {
            return business_info;
        }

        public void setBusiness_info(businessInfo business_info) {
            this.business_info = business_info;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public MiniProgramInfo getMiniProgramInfo() {
            return MiniProgramInfo;
        }

        public void setMiniProgramInfo(MiniProgramInfo miniProgramInfo) {
            this.MiniProgramInfo = miniProgramInfo;
        }

        class ServiceTypeInfo {
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        class VerifyTypeInfo {
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        class businessInfo {
            // 是否开通微信门店功能
            private int open_store;
            // 是否开通微信扫商品功能
            private int open_scan;
            // 是否开通微信支付功能
            private int open_pay;
            // 是否开通微信卡券功能
            private int open_card;
            // 是否开通微信摇一摇功能
            private int open_shake;

            public int getOpen_store() {
                return open_store;
            }

            public void setOpen_store(int open_store) {
                this.open_store = open_store;
            }

            public int getOpen_scan() {
                return open_scan;
            }

            public void setOpen_scan(int open_scan) {
                this.open_scan = open_scan;
            }

            public int getOpen_pay() {
                return open_pay;
            }

            public void setOpen_pay(int open_pay) {
                this.open_pay = open_pay;
            }

            public int getOpen_card() {
                return open_card;
            }

            public void setOpen_card(int open_card) {
                this.open_card = open_card;
            }

            public int getOpen_shake() {
                return open_shake;
            }

            public void setOpen_shake(int open_shake) {
                this.open_shake = open_shake;
            }
        }

        /**
         * 小程序信息
         */
        class MiniProgramInfo {
            private Network network;
            private int visit_status;
            private List<Category> categories;

            public Network getNetwork() {
                return network;
            }

            public void setNetwork(Network network) {
                this.network = network;
            }

            public int getVisit_status() {
                return visit_status;
            }

            public void setVisit_status(int visit_status) {
                this.visit_status = visit_status;
            }

            public List<Category> getCategories() {
                return categories;
            }

            public void setCategories(List<Category> categories) {
                this.categories = categories;
            }

            class Network {
                private String[] RequestDomain;
                private String[] WsRequestDomain;
                private String[] UploadDomain;
                private String[] DownloadDomain;

                public String[] getRequestDomain() {
                    return RequestDomain;
                }

                public void setRequestDomain(String[] requestDomain) {
                    RequestDomain = requestDomain;
                }

                public String[] getWsRequestDomain() {
                    return WsRequestDomain;
                }

                public void setWsRequestDomain(String[] wsRequestDomain) {
                    WsRequestDomain = wsRequestDomain;
                }

                public String[] getUploadDomain() {
                    return UploadDomain;
                }

                public void setUploadDomain(String[] uploadDomain) {
                    UploadDomain = uploadDomain;
                }

                public String[] getDownloadDomain() {
                    return DownloadDomain;
                }

                public void setDownloadDomain(String[] downloadDomain) {
                    DownloadDomain = downloadDomain;
                }
            }

            class Category {
                private String first;
                private String second;

                public String getFirst() {
                    return first;
                }

                public void setFirst(String first) {
                    this.first = first;
                }

                public String getSecond() {
                    return second;
                }

                public void setSecond(String second) {
                    this.second = second;
                }
            }
        }
    }

    /**
     * 授权信息
     */
    class AuthorizationInfo {
        // 授权方appid
        private String appid;
        // 小程序授权给开发者的权限集列表，ID为17到19时分别代表：
        // 17.帐号管理权限
        // 18.开发管理权限
        // 19.客服消息管理权限
        private List<FuncInfo> func_info;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public List<FuncInfo> getFunc_info() {
            return func_info;
        }

        public void setFunc_info(List<FuncInfo> func_info) {
            this.func_info = func_info;
        }
    }
}
