<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<html>
<head>
    <title>图表管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/main/main.css">
    <%@include file="../../include/commonFile.jsp"%>
</head>
<body>
<%@include file="../../include/header.jsp"%>
    <div class="index-outside">
        <%@include file="../../include/sidebar.jsp"%>
        <section>
            <div class="head-main-car">
                <%--<div class="info-box bg-light-blue">
                    <div class="box-content " id="apply-num">
                        <div class="box-icon">
                        <i class="iconfont icon-squarecheck btn-icon"></i>
                        </div>
                        <div class="box-inner">
                            <div class="num">${onHandApply}</div>
                            <div class="info">活动报名待审核数</div>
                        </div>
                    </div>
                </div>--%>
                <div class="info-box bg-green">
                    <div class="box-content " id="allApply-num">
                        <div class="box-icon">
                            <i class="iconfont icon-friendadd btn-icon"></i>
                        </div>
                        <div class="box-inner">
                            <div class="num">${allApply}</div>
                            <div class="info">所有报名数</div>
                        </div>
                    </div>
                </div>
                <div class="info-box bg-orange">
                    <div class="box-content " id="order-num">
                        <div class="box-icon">
                            <i class="iconfont icon-form btn-icon"></i>
                        </div>
                        <div class="box-inner">
                            <div class="num">${allOrder}</div>
                            <div class="info">所有订单数</div>
                        </div>
                    </div>
                </div>
                <div class="info-box bg-red">
                    <div class="box-content " id="member-num">
                        <div class="box-icon">
                            <i class="iconfont icon-group btn-icon"></i>
                        </div>
                        <div class="box-inner">
                            <div class="num">${allMember}</div>
                            <div class="info">所有注册会员数</div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="chart-content" id="apply_charts" style="height:400px;"></div>
                <div class="chart-content" id="order_charts" style="height:400px;"></div>
                <div class="chart-content" id="member_charts" style="height:400px;"></div>
            </div>
        </section>
    </div>
    <%@include file="../../include/footer.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/static/echarts-3.0/echarts.min.js"></script>
<script type="text/javascript">
    $.get('${ctx}/system/main/data.do',function (res) {
        console.log(res);
        var applyChart = echarts.init(document.getElementById('apply_charts'));
        var orderChart = echarts.init(document.getElementById('order_charts'));
        var memberChart = echarts.init(document.getElementById('member_charts'));

        var applyOption = {
            title : {
                text:'近半月活动报名趋势图',
                x:'center'
            },
            color: ['#3398DB'],

            tooltip : {
                trigger: 'axis'
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            legend: {
                data:['活动报名']
            },
            xAxis: {
                data: res.data.dayList
            },
            yAxis: {},
            series: [{
                name: '报名人数',
                type: 'bar',
                data: res.data.applyList,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
            }]
        };

        var orderOption = {
            title : {
                text:'近半月订单数趋势图',
                x:'center'
            },
            color: ['#3398DB'],

            tooltip : {
                trigger: 'axis'
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            legend: {
                data:['新增订单']
            },
            xAxis: {
                data: res.data.dayList
            },
            yAxis: {},
            series: [{
                name: '订单数',
                type: 'bar',
                data: res.data.orderList,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
            }]
        };

        var memberOption = {
            title : {
                text:'近半月注册人数趋势图',
                x:'center'
            },
            color: ['#3398DB'],

            tooltip : {
                trigger: 'axis'
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            legend: {
                data:['新增注册人数']
            },
            xAxis: {
                data: res.data.dayList
            },
            yAxis: {},
            series: [{
                name: '注册人数',
                type: 'bar',
                data: res.data.memberList,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
            }]
        };

        applyChart.setOption(applyOption);
        orderChart.setOption(orderOption);
        memberChart.setOption(memberOption);

        orderChart.on('click', function (params) {
            var createDate = params.name;
            window.location.href = "${ctx}/order/order/orderList.do?createStart="+createDate+"&createEnd="+createDate;
        });

        applyChart.on('click', function (params) {
            var createDate = params.name;
            window.location.href = "${ctx}/activity/memberAct/applyList.do?createStart="+createDate+"&createEnd="+createDate;
        });

        memberChart.on('click', function (params) {
            var createDate = params.name;
            window.location.href = "${ctx}/system/member/memberList.do?createStart="+createDate+"&createEnd="+createDate;
        });
    });

    $('#allApply-num').on('click', function () {
        window.location.href = "${ctx}/activity/memberAct/applyList.do";
    });

    $('#order-num').on('click', function () {
        window.location.href="${ctx}/order/order/orderList.do";
    });

    $('#member-num').on('click', function () {
        window.location.href = "${ctx}/system/member/memberList.do";
    });

    $('#unauditedMember-num').on('click', function () {
        window.location.href = "${ctx}/system/member/memberList.do?userStatus=0";
    });
</script>
</body>
</html>
