<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>编辑人员信息</title>
<%@include file="../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_form.css">
<style type="text/css">
.aui-field-title {
	border: none;
	border-top: 1px solid #e2e2e2;
	text-align: center;
	margin: 15px 30px 0px;
}

.aui-field-title legend {
	padding: 0px 10px;
}

.layui-input, .layui-textarea {
	width: 80% !important;
}

.layui-unselect {
	width: 100% !important;
}

.layui-form-radio {
	width: auto !important;
}
</style>
</head>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<c:if test="${not empty project.id && empty group.id}">
						<a href="${ctx}/gatherInfo/member/list.do?projectId=${project.id}" class="layui-btn layui-btn-radius layui-btn-danger">
							<i class="iconfont icon-refresh btn-icon"></i>返回
						</a>
					</c:if>
					<c:if test="${not empty group.id}">
						<a href="${ctx}/gatherInfo/member/list.do?pGroupId=${group.id}" class="layui-btn layui-btn-radius layui-btn-danger">
							<i class="iconfont icon-refresh btn-icon"></i>返回
						</a>
					</c:if>
				</div>
				<div class="ovh">
					<c:if test="${not empty project.id && empty group.id}">
						<span class="title f20" title="${project.title}">我发布的项目&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 20}">${fn:substring(project.title,0,20)}...</c:if>
							<c:if test="${fn:length(project.title) <= 20}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;人员管理&nbsp;&gt;&nbsp;编辑人员信息
						</span>
					</c:if>
					<c:if test="${not empty group.id}">
						<span class="title f20" title="${group.groupName}">我发布的项目&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(project.title) > 10}">${fn:substring(project.title,0,10)}...</c:if>
							<c:if test="${fn:length(project.title) <= 10}">${project.title}</c:if>
							&nbsp;&gt;&nbsp;
							<c:if test="${fn:length(group.groupName) > 10}">${fn:substring(group.groupName,0,10)}...</c:if>
							<c:if test="${fn:length(group.groupName) <= 10}">${group.groupName}</c:if>
							&nbsp;&gt;&nbsp;人员管理&nbsp;&gt;&nbsp;编辑人员信息
						</span>
					</c:if>
				</div>
			</div>
			
			<!-- 正文请写在这里 -->
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
                <ul class="layui-tab-title">
                    <li class="layui-this"><span class="title f18 ml5 mr5">个人信息编辑</span></li>
                    <li><span class="title f18 ml5 mr5">保险信息编辑</span></li>
                    <li><span class="title f18 ml5 mr5">行程信息编辑</span></li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                    	<form id="baseForm" class="layui-form mt20" method="post" action="${ctx}/gatherInfo/member/saveBaseInfo.do">
							<input type="hidden" name="gMemberId" value="${memberInfo.id}" />
							<input type="hidden" name="id" value="${member.id}" />
                    		<div class="layui-form-item">
								<label class="layui-form-label">姓&emsp;&emsp;名</label>
								<div class="layui-input-block">
									<input class="layui-input" name="fullname" value='${empty member.fullname ? member.realname : member.fullname}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">头&emsp;&emsp;像</label>
								<div class="cover-content">
									<input type="hidden" name="logo" id="pic" lay-verify="pic" value="${member.logo}" />
									<c:if test="${member == null || empty member.logo}">
										<span id="cover-img" class="cover-img" style='background-image: url("${ctx}/image/avatar1.png")'></span>
									</c:if>
									<c:if test="${member != null || not empty member.logo}">
										<span id="cover-img" class="cover-img" style='background-image: url("${member.logo}")'></span>
									</c:if>
									<div class="u-single-upload">
										<input type="file" id="upload_single_img" class="u-single-file">
										<span class="u-single-upload-icon">+添加头像</span>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">性&emsp;&emsp;别</label>
								<div class="layui-input-block">
									<input type="radio" name="sex" lay-filter="sex" id="male" value="1" title="男"
										${member == null || member.sex == "1" ? 'checked="checked"' : ''}
									>
									<input type="radio" name="sex" lay-filter="sex" id="female" value="0" title="女"
										${member != null && member.sex == "0" ? 'checked="checked"' : ''}
									>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">公&emsp;&emsp;司</label>
								<div class="layui-input-block">
									<input class="layui-input" name="company" value='${member.company}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">职&emsp;&emsp;务</label>
								<div class="layui-input-block">
									<input class="layui-input" name="jobTitle" value='${member.jobTitle}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">手&emsp;&emsp;机</label>
								<div class="layui-input-block">
									<input class="layui-input" name="mobile" lay-verify="mobile" value='${member.mobile}' ${empty member.mobile ? '' : 'readonly="readonly"'} autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">微&emsp;&emsp;信</label>
								<div class="layui-input-block">
									<input class="layui-input" name="wechatNum" value='${member.wechatNum}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">地&emsp;&emsp;区</label>
									<div class="layui-input-inline">
										<select lay-filter="province">
											<option value="">选择省份/直辖市</option>
											<c:forEach var="area" items="${areas}">
												<option value="${area.id}" ${arParent == area.id?'selected="selected"' : ''}>${area.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="layui-input-inline">
										<select name="city" id="city">
											<option value="">选择城市</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">行&emsp;&emsp;业</label>
									<div class="layui-input-inline">
										<select lay-filter="industry">
											<option value="">选择行业分类</option>
											<c:forEach var="industry" items="${industries}">
												<option value="${industry.id}" ${inParent == industry.id?'selected="selected"' : ''}>${industry.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="layui-input-inline">
										<select name="industry" id="industry">
											<option value="">选择行业</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">自我介绍</label>
								<div class="layui-input-block">
							   		<textarea name="signature" placeholder="用一两句话来让别人更全面的认识您" class="layui-textarea">${member.signature}</textarea>
							    </div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">组内职务</label>
								<div class="layui-input-block">
									<input class="layui-input" name="groupJobTitle" value='${memberInfo.groupJobTitle}' />
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="baseForm">立即提交</a>
									<c:if test="${not empty project.id && empty group.id}">
										<a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
									</c:if>
									<c:if test="${not empty group.id}">
										<a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
									</c:if>
								</div>
							</div>
                    	</form>
                    </div>
                    <div class="layui-tab-item">
                    	<form id="insuranceForm" class="layui-form mt20" method="post" action="${ctx}/gatherInfo/member/saveInsuranceInfo.do">
                    		<input type="hidden" name="id" value="${mInfo.id}" />
                    		<input type="hidden" name="memberId" value="${member.id}" />
                    		<fieldset class="aui-field-title">
								<legend>保险信息<b class="pl5 pr5">·</b>个人信息</legend>
							</fieldset>
                    		<div class="layui-form-item">
								<label class="layui-form-label">身份证</label>
								<div class="layui-input-block">
									<input class="layui-input" name="idCard" lay-verify="idCard" value='${mInfo.idCard}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">血&emsp;型</label>
								<div class="layui-input-block">
									<input class="layui-input" name="bloodGroup" value='${mInfo.bloodGroup}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">病&emsp;史</label>
								<div class="layui-input-block">
									<input class="layui-input" name="medicalHistory" value='${mInfo.medicalHistory}' autocomplete="off" />
								</div>
							</div>
							<fieldset class="aui-field-title">
								<legend>保险信息<b class="pl5 pr5">·</b>紧急联系人</legend>
							</fieldset>
							<div class="layui-form-item">
								<label class="layui-form-label">姓&emsp;名</label>
								<div class="layui-input-block">
									<input class="layui-input" name="contactName" value='${mInfo.contactName}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">关&emsp;系</label>
								<div class="layui-input-block">
									<input class="layui-input" name="contactRelation" value='${mInfo.contactRelation}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">手&emsp;机</label>
								<div class="layui-input-block">
									<input class="layui-input" name="contactMobile" lay-verify="contactMobile" value='${mInfo.contactMobile}' autocomplete="off" />
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="insuranceForm">立即提交</a>
									<c:if test="${not empty project.id && empty group.id}">
										<a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
									</c:if>
									<c:if test="${not empty group.id}">
										<a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
									</c:if>
								</div>
							</div>
                    	</form>
                    </div>
                    <div class="layui-tab-item">
                    	<form id="itineraryForm" class="layui-form mt20" method="post" action="${ctx}/gatherInfo/member/saveItineraryInfo.do">
                    		<input type="hidden" name="id" value="${memberInfo.id}" />
                    		<fieldset class="aui-field-title">
								<legend>行程信息<b class="pl5 pr5">·</b>去程信息</legend>
							</fieldset>
							<div class="layui-form-item">
								<label class="layui-form-label">出发城市</label>
								<div class="layui-input-block">
									<input class="layui-input" name="goDepartCity" value='${memberInfo.goDepartCity}' />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">出发方式</label>
								<div class="layui-input-block">
									<input class="layui-input" name="goType" value='${memberInfo.goType}' placeholder="出发方式，飞机、火车、汽车、其他。" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">航班/列车编号</label>
								<div class="layui-input-block">
									<input class="layui-input" name="goNumber" value='${memberInfo.goNumber}' placeholder="出发航班/列车编号" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">出发时间</label>
								<div class="layui-input-block">
									<input class="layui-input" name="goDepartDate" lay-verify="goDepartDate"
										value='<fmt:formatDate value="${memberInfo.goDepartTime}" pattern="yyyy-MM-dd HH:mm" />'
										onclick="layui.laydate({elem: this,format:'YYYY-MM-DD hh:mm',istime:true})"
									/>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">到达时间</label>
								<div class="layui-input-block">
									<input class="layui-input" name="goDate" lay-verify="goTime"
										value='<fmt:formatDate value="${memberInfo.goTime}" pattern="yyyy-MM-dd HH:mm" />'
										onclick="layui.laydate({elem: this,format:'YYYY-MM-DD hh:mm',istime:true})"
									/>
								</div>
							</div>
							
							<div class="layui-form-item">
								<label class="layui-form-label">到达站点名称</label>
								<div class="layui-input-block">
									<input class="layui-input" name="goStation" value='${memberInfo.goStation}' />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">到达是否需要接</label>
								<div class="layui-input-block">
									<input type="radio" name="goShuttle" value="1" title="是"
										${not empty memberInfo.goShuttle && memberInfo.goShuttle == 1 ? 'checked="checked"' : ''}
									>
									<input type="radio" name="goShuttle" value="0" title="否"
										${empty memberInfo.goShuttle || memberInfo.goShuttle == 0 ? 'checked="checked"' : ''}
									>
								</div>
							</div>
							<fieldset class="aui-field-title">
								<legend>行程信息<b class="pl5 pr5">·</b>返程信息</legend>
							</fieldset>
							<div class="layui-form-item">
								<label class="layui-form-label">返程方式</label>
								<div class="layui-input-block">
									<input class="layui-input" name="backType" value='${memberInfo.backType}' placeholder="返程方式，飞机、火车、汽车、其他。" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">航班/列车编号</label>
								<div class="layui-input-block">
									<input class="layui-input" name="backNumber" value='${memberInfo.backNumber}' />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">出发时间</label>
								<div class="layui-input-block">
									<input class="layui-input" name="backDepartDate" lay-verify="backDepartDate"
										value='<fmt:formatDate value="${memberInfo.backDepartTime}" pattern="yyyy-MM-dd HH:mm" />'
										onclick="layui.laydate({elem: this,format:'YYYY-MM-DD hh:mm',istime:true})"
									/>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">到达时间</label>
								<div class="layui-input-block">
									<input class="layui-input" name="backDate" lay-verify="backTime"
										value='<fmt:formatDate value="${memberInfo.backTime}" pattern="yyyy-MM-dd HH:mm" />'
										onclick="layui.laydate({elem: this,format:'YYYY-MM-DD hh:mm',istime:true})"
									/>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">到达城市</label>
								<div class="layui-input-block">
									<input class="layui-input" name="backDepartCity" value='${memberInfo.backDepartCity}' />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">到达站点名称</label>
								<div class="layui-input-block">
									<input class="layui-input" name="backStation" value='${memberInfo.backStation}' />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">返程是否需要送</label>
								<div class="layui-input-block">
									<input type="radio" name="backShuttle" value="1" title="是"
										${not empty memberInfo.backShuttle && memberInfo.backShuttle == 1 ? 'checked="checked"' : ''}
									>
									<input type="radio" name="backShuttle" value="0" title="否"
										${empty memberInfo.backShuttle || memberInfo.backShuttle == 0 ? 'checked="checked"' : ''}
									>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">备注信息</label>
								<div class="layui-input-block">
							   		<textarea name="remarks" placeholder="如提前到达需主办方预订酒店请备注时间及房间要求" class="layui-textarea">${memberInfo.remarks}</textarea>
							    </div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="itineraryForm">立即提交</a>
									<c:if test="${not empty project.id && empty group.id}">
										<a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
									</c:if>
									<c:if test="${not empty group.id}">
										<a href="javascript:history.back();" class="layui-btn layui-btn-primary">取消</a>
									</c:if>
								</div>
							</div>
                    	</form>
                    </div>
             	</div>
        	</div>
		</div>
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/myplugin/uploadCI.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script type="text/javascript">
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
	$(function() {
        layui.use([ 'form' , 'laydate' , 'element'], function() {
            var form = layui.form(), laydate = layui.laydate, element = layui.element();

			element.on('tab', function (data) {
		        console.log(this); //当前Tab标题所在的原始DOM元素
		        console.log(data.index); //得到当前Tab的所在下标
		        console.log(data.elem); //得到当前的Tab大容器
		    });

			//自定义验证规则
			form.verify({
				mobile : function(value) {
					var reg = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$|17[0-9]{9}$/;
					if(value != "" && !reg.test(value)){
						return '手机号格式不正确';
					} else if (value != "" && reg.test(value)) {
                        var isRepeat = ajaxSubmit(value, '${member.id}', 'mobile');
                        if (isRepeat) {
                            return "手机号已存在";
                        }
                    }
				},
				idCard : function(value) {
					var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
					if(value != "" && !reg.test(value)){
						return '身份证格式不正确';
					}
				},
				contactMobile : function(value) {
					var reg = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$|17[0-9]{9}$/;
					if(value != "" && !reg.test(value)){
						return '手机号格式不正确';
					}
				},
				goDepartDate : function(value){
					var goDepartDate = $("[name=goDepartDate]").val();
					var goDate = $("[name=goDate]").val();
					if (goDate != "" && goDepartDate == ""){
						return "请选择去程出发时间";
					}
				},
				goTime : function(value){
					var goDepartDate = $("[name=goDepartDate]").val();
					var goDate = $("[name=goDate]").val();
	                
	                var gd1 = new Date(goDepartDate.replace(/-/g, "/"));
	                var gd2 = new Date(goDate.replace(/-/g, "/"));
					
					if (goDepartDate != ""){
						if (goDate == ""){
							return "请选择去程到达时间";
						} else if (gd1 >= gd2) {
							return "去程到达时间应该晚于去程出发时间";
						}
					}
				},
				backDepartDate : function(value){
					var goDate = $("[name=goDate]").val();
					var backDepartDate = $("[name=backDepartDate]").val();
					var backDate = $("[name=backDate]").val();
					
					var gd2 = new Date(goDate.replace(/-/g, "/"));
					var bd3 = new Date(backDepartDate.replace(/-/g, "/"));
					
					if(backDate != ""){
						if(backDepartDate == ""){
							return "请选择返程出发时间";
						} else if(gd2 >= bd3){
							return "返程出发时间应该晚于去程到达时间";
						}
					}
				},
				backTime : function(value){
					var backDepartDate = $("[name=backDepartDate]").val();
					var backDate = $("[name=backDate]").val();
	                
	                var bd3 = new Date(backDepartDate.replace(/-/g, "/")); 
	                var bd4 = new Date(backDate.replace(/-/g, "/"));
					if (backDepartDate != ""){
						if (backDate == ""){
							return "请选择返程到达时间";
						} else if (bd3 >= bd4) {
							return "返程到达时间应该晚于返程出发时间";
						}
					}
				}
			});
			
			//监听提交 基本信息
	        form.on('submit(baseForm)', function (data) {
	            var action = $("#baseForm").attr("action");
	            $.post(action, $('#baseForm').serialize(), function (res) {
	                if (res.success) {
	                	layer.msg('提交成功', {icon: 1, time : 2000}, function(index){
	                		location.reload();
	                	});
	                } else {
	                    layer.msg('提交失败', {icon: 2, time : 2000});
	                }
	            });
	            return false;
	        });
			
	      	//监听提交 保险信息
	        form.on('submit(insuranceForm)', function (data) {
	            var action = $("#insuranceForm").attr("action");
	            $.post(action, $('#insuranceForm').serialize(), function (res) {
	                if (res.success) {
	                	layer.msg('提交成功', {icon: 1, time : 2000}, function(index){
	                		location.reload();
	                	});
	                } else {
	                    layer.msg('提交失败', {icon: 2, time : 2000});
	                }
	            });
	            return false;
	        });
	
			//监听提交 行程信息
			form.on('submit(itineraryForm)', function(data) {
				var action = $("#itineraryForm").attr("action");
				$.post(action, $('#itineraryForm').serialize(), function(res) {
					if (res.success) {
						layer.msg('提交成功', {icon : 1, time : 2000}, function(index) {
							if ('${project.id}' != "" && '${group.id}' == "") {
								location.href = "${ctx}/gatherInfo/member/list.do?projectId=${project.id}";
							} else if ('${group.id}' != "") {
								location.href = "${ctx}/gatherInfo/member/list.do?pGroupId=${group.id}";
							}
						});
					} else {
						layer.msg('提交失败', {icon : 2, time : 2000});
					}
				});
				return false;
			});
			
			// 城市
	        form.on('select(province)', function (data) {
	            $("#city").html("");
	            var cityId = data.value;
	            loadCityData(cityId);
	        });
			
	        form.on('select(industry)', function (data) {
	            $("#industry").html("");
	            var industryId = data.value;
	            loadIndustryData(industryId);
	        });
	
	        if ('${member.city}' != "") {
	            loadCityData('${arParent}', '${member.city}');
	        }
	        
	        if ('${member.industry}' != "") {
	            loadIndustryData('${inParent}', '${member.industry}');
	        }
	
	        function loadCityData(pCityId, cCityId) {
	            $.post("${ctx}/system/member/getCityByParentId.do", {
	                "cityId": pCityId
	            }, function (data) {
	                var array = new Array();
	                for (var i = 0; i < data.length; i++) {
	                    if (cCityId == data[i].id) {
	                        array.push("<option value = '" + data[i].id + "' selected='selected'>" + data[i].name + "</option>");
	                    } else {
	                        array.push("<option value = '" + data[i].id + "'>" + data[i].name + "</option>");
	                    }
	                }
	                $("#city").append(array.join(""));
	                form.render('select');
	            });
	        }
	        
	        function loadIndustryData(pIndustryId, cIndustryId) {
	            $.post("${ctx}/system/member/getIndustryByParentId.do", {
	                "industryId": pIndustryId
	            }, function (data) {
	                var array = new Array();
	                for (var i = 0; i < data.length; i++) {
	                    if (cIndustryId == data[i].id) {
	                        array.push("<option value = '" + data[i].id + "' selected='selected' >" + data[i].name + "</option>");
	                    } else {
	                        array.push("<option value = '" + data[i].id + "'>" + data[i].name + "</option>");
	                    }
	                }
	                $("#industry").append(array.join(""));
	                form.render('select');
	            });
	        }
		});
		$('#upload_single_img').change(function() {
			if (util.isValid(this.value)) {
				uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
					console.log('回调：' + ret);
					$('#cover-img').css('background-image','url(' + ret.data.download_url + ')');
					$('#pic').val(ret.data.download_url);
					$('#upload_single_img').val('');
				});
			}
		});
	})
	
	function ajaxSubmit(property, userId, type) {
        var isRepeat = false;
        $.ajax({
            type: 'POST',
            async: false, // 使用同步的方法
            data: {
                property: property,
                userId: userId,
                type: type
            },
            dataType: 'json',
            success: function (result) {
                isRepeat = !result;
            },
            url: '${ctx}/system/member/checkUniqueProperty.do'
        });
        return isRepeat;
    }
</script>
</body>
</html>