<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/tag.jsp" %>
<style type="text/css">
    .member-index-top {
        display: flex;
        padding: 20px 15px;
        border: 1px solid #dbdee5;
        border-radius: 5px;
    }

    .member-index-top .top-left {
        flex: 1;
        display: flex;
    }

    .top-left .logo {
        width: 75px;
        border-radius: 5px;
    }

    .member-index-top .info-div {
        font-size: 16px;
        color: #7f7f7f;
        flex: 1;
    }

    .display-flex {
        display: flex !important;
    }

    .huangguan-big {
        font-size: 20px !important;
        color: #ffc107 !important;
    }
</style>

<!-- 个人资料，余额 -->
<div class="member-index-top">
    <!-- 左边 -->
    <div class="top-left">
        <div class="mr20"><img class="logo" src="${member.logo}" alt=""></div>
        <div style="flex: 1;">
            <div class="f18 mb15">
                <span>${member.realname}</span>
                <span class="iconfont icon-huangguan huangguan-big" ${member.isExpert != 1 ? 'style="display: none;"' : '' } ></span>
                <a href="${ctx}/system/member/memberForm.do" target="_blank"
                   class="layui-btn layui-btn-danger layui-btn-small ml10"
                   style="padding-left: 15px; padding-right: 15px;"><i class="iconfont icon-edit btn-icon"></i>
                    编辑个人资料</a>
            </div>
            <div class="display-flex mb10">
                <div class="info-div">
                    <span>账号等级：</span>
                    <c:if test="${null == productPackage}">
                        <span>无</span>
                        <a href="${ctx}/charge/package/packageList.do" target="_blank"
                           class="layui-btn layui-btn-danger layui-btn-small ml10"
                           style="padding-left: 15px; padding-right: 15px;"><i
                                class="iconfont icon-choiceness btn-icon"></i> 开通</a>
                    </c:if>
                    <c:if test="${null != productPackage}">
                        <span>${productPackage.name}</span>
                        <a href="${ctx}/charge/package/packageList.do" target="_blank"
                           class="layui-btn layui-btn-danger layui-btn-small ml10"
                           style="padding-left: 15px; padding-right: 15px;"><i
                                class="iconfont icon-choiceness btn-icon"></i> 升级</a>
                    </c:if>
                </div>
                <div class="info-div">
                    <span>账户余额： </span>
                    <span><b class="active-red"><fmt:formatNumber pattern="#,###.##" value="${totalPayment}"/></b>元</span>
                    <a href="${ctx}/wallet/withdrawals/withdrawalForm.do" target="_blank"
                       class="layui-btn layui-btn-danger layui-btn-small ml10"
                       style="padding-left: 15px; padding-right: 15px;"><i class="iconfont icon-recharge btn-icon"></i>
                        提现</a>
                </div>
            </div>
            <c:if test="${null != productPackage}">
                <div class="display-flex">
                    <div class="info-div">
                        <span>到期时间：</span>
                        <c:if test="${null == endTime}">
                            <span><fmt:formatDate value="${packageMember.endTime}" pattern="yyyy-MM-dd HH:mm" /></span>
                        </c:if>
                        <c:if test="${null != endTime}">
                            <span>已过期</span>
                        </c:if>
                        <a href="${ctx}/charge/package/packageList.do" target="_blank"
                           class="layui-btn layui-btn-danger layui-btn-small ml10"
                           style="padding-left: 15px; padding-right: 15px;"><i
                                class="iconfont icon-choiceness btn-icon"></i> 续期</a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
