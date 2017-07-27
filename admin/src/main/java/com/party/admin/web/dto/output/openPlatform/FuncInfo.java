package com.party.admin.web.dto.output.openPlatform;

/**
 * 公众号授权给开发者的权限集列表
 */
public class FuncInfo {
    public class FuncscopeCategory {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    private FuncscopeCategory funcscope_category;

    public FuncscopeCategory getFuncscope_category() {
        return funcscope_category;
    }

    public void setFuncscope_category(FuncscopeCategory funcscope_category) {
        this.funcscope_category = funcscope_category;
    }
}
