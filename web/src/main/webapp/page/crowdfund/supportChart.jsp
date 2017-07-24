<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<html>
<head>
    <title>Title</title>
    <%@include file="../include/commonFile.jsp"%>
</head>
<body style="min-width: 1050px">
    <input  id="projectId" name="projectId" value="${projectId}" hidden>
    <input  id="targetId" name="targetId" value="${targetId}" hidden>
    <div class="chart-content" id="charts" style="height:399px"></div>
    <script type="text/javascript" charset="utf-8" src="${ctx}/static/echarts-3.0/echarts.min.js"></script>
    <script type="text/javascript">
        var projectId = $('#projectId').val();
        var targetId = $('#targetId').val();
        $.get('${ctx}/crowdfund/support/chart.do',{projectId:projectId},function (res) {
            console.log(res);
            var chart = echarts.init(document.getElementById('charts'));
            var option = {
                title : {
                    text:'近一月支持趋势图'
                },
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
                    data:['支持金额','支持人数']
                },
                xAxis: {
                    data: res.data.dateList
                },
                yAxis: {},
                series: [{
                    name: '支持金额',
                    type: 'line',
                    data: res.data.paymentList,
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                }
                ]
            };

            chart.setOption(option);

            chart.on('click', function (params) {
                var createDate = params.name;
                var date = new Date(createDate);
                if (date.toString() == 'Invalid Date'){
                    return;
                }
                parent.location.href = "${ctx}/crowdfund/support/listForProjectId.do?id="+targetId+"&projectId="+projectId+"&startTime="+createDate;
            });
        });


    </script>
</body>
</html>
