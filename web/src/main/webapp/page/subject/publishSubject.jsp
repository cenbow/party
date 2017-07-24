<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>发布专题</title>
<%@include file="../include/commonFile.jsp" %>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/subject/publish_subject.css">
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="${ctx}/subject/subject/subjectList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">我发布的专题&nbsp;&gt;&nbsp;${subject == null ? '发布' : '编辑'}专题</span>
				</div>
			</div>
			<!-- 正文请写在这里 -->
			<form id="myForm" class="layui-form mt20" method="post" action="${ctx}/subject/subject/save.do">
				<div class="layui-form-item">
					<label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="text" name="name" lay-verify="name" style="width: 85%" autocomplete="off" placeholder="专题名称" class="layui-input" value="${subject.name}">
						<input type="hidden" name="id" value="${subject.id}"/>
					</div>
				</div>
				<div class="layui-form-item" id="pic_content">
					<label class="layui-form-label">封面图<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="picture" id="pic" lay-verify="pic" value="${subject.picture}" />
						<c:if test="${subject == null || empty subject.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${subject != null && not empty subject.picture}">
							<span id="cover-img" class="cover-img" style="background-image:url('${subject.picture}')"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_single_img" class="u-single-file"> <span class="u-single-upload-icon">+添加封面图</span>
						</div>
					</div>
				</div>			
				<div class="layui-form-item">
					<label class="layui-form-label">模板<span class="f-verify-red">*</span></label>
					<input type="hidden" id="tempType" name="tempType" value="${subject.tempType}"/>
					<div class="layui-input-block" id="temp_items">
				   		<div data-type="1" class="temp-item <c:if test="${subject == null || subject.tempType == 1}">active</c:if>" style="background-image:url(${ctx}/image/subject/subject_temp1.png)"><span class="item-check"><i class="iconfont icon-check"></i></span></div>
				   		<div data-type="2" class="temp-item <c:if test="${subject.tempType == 2}">active</c:if>" style="background-image:url(${ctx}/image/subject/subject_temp2.png)"><span class="item-check"><i class="iconfont icon-check"></i></span></div>
				    </div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">行栏目个数<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
						<input type="number" min="1" max="5" name="colNum" lay-verify="colNum" style="width: 10%" autocomplete="off"  class="layui-input" value="${subject.colNum}">
					</div>
				</div>
				<div class="layui-form-item" id="showpic_content"  <c:if test="${subject == null || subject.tempType == 1}">style="display:none"</c:if>>
					<label class="layui-form-label">是否显示封面图<span class="f-verify-red">*</span></label>
				     <div class="layui-input-block">				     	
				      	<input type="radio" name="showPic" value="1" title="显示" <c:if test="${subject == null|| subject.showPic ==null || subject.showPic == 1}">checked=""</c:if>>		
				      	<input type="radio" name="showPic" value="0" title="不显示" <c:if test="${subject.showPic == 0}">checked=""</c:if>>		      
				    </div>
				</div>		
					
				<div class="layui-form-item" id="bgpic_content" <c:if test="${subject == null  || subject.tempType == 1}">style="display:none"</c:if>>
					<label class="layui-form-label">背景图<span class="f-verify-red">*</span></label>
					<div class="cover-content">
						<input type="hidden" name="bgPic" id="bgPic" lay-verify="bgPic" value="${subject.bgPic}" />
						<c:if test="${subject == null || empty subject.bgPic}">
							<span id="bg_img" class="cover-img" style="background-image:url(${ctx}/image/posterImg.png)"></span>
						</c:if>
						<c:if test="${subject != null && not empty subject.bgPic}">
							<span id="bg_img" class="cover-img" style="background-image:url(${subject.bgPic})"></span>
						</c:if>
						<div class="u-single-upload">
							<input type="file" id="upload_bg_img" class="u-single-file"> <span class="u-single-upload-icon">+添加背景图</span>
						</div>
					</div>
				</div>
				<div class="layui-form-item"> 
					<label class="layui-form-label">描述<span class="f-verify-red">*</span></label>
					<div class="layui-input-block">
				   		<textarea name="remarks" placeholder="专题描述" lay-verify="remarks" class="layui-textarea" style="width: 85%" >${subject.remarks}</textarea>
				    </div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
						<a href="${ctx}/subject/subject/subjectList.do" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
		</div>
	</section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>

<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/resize.js"></script>
<script>
	var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
	


	$(function() {
        layui.use(['form'], function () {
            var form = layui.form(), laydate = layui.laydate;

            //自定义验证规则
            form.verify({
                name : function(value) {
                    if (value == "") {
                        return '请填写专题名称';
                    }
                },
                pic : function(value) {
                    if (value == "") {
                        return "请上传专题封面图";
                    }
                },
                bgPic : function(value) {
                    if($('#tempType').val() == 2){
                        if (value == "") {
                            return "请上传专题背景图";
                        }
                    }
                },
                colNum:function(value){
                    if(value == ""){
                        return "请填写每行显示栏目数";
                    }
                    if(value > 5){
                        return "每行最多显示5个栏目";
                    }
                    if(value < 2){
                        return "每行至少显示2个栏目";
                    }
                },
                remarks : function(value) {
                    if (value == "") {
                        return '请填写专题描述';
                    }
                }
            });

            //监听提交
            form.on('submit', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#myForm").attr("action");
                $.post(action, $('#myForm').serialize(), function(res) {
                    if (res.success) {
                        top.layer.msg('提交成功', {icon : 1}, function(index){
                            location.href = "${ctx}/subject/subject/subjectList.do";
                        });
                    } else {
                        top.layer.msg('提交失败', {icon : 2});
                    }
                });
                return false;
            });
        });
		$('#upload_single_img').change(function() {
			if (util.isValid(this.value)) {
				uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
					$('#cover-img').css('background-image','url(' + ret.data.download_url + ')');
					$('#pic').val(ret.data.download_url);
					$('#upload_single_img').val('');
				});
			}
		});
		$('#upload_bg_img').change(function() {
			if (util.isValid(this.value)) {
				uploadFile.uploadSinglePhoto(this.files[0], function(ret) {
					$('#bg_img').css('background-image','url(' + ret.data.download_url + ')');
					$('#bgPic').val(ret.data.download_url);
					$('#upload_bg_img').val('');
				});
			}
		});	
		$('#temp_items .temp-item').click(function(){
			$('#temp_items .temp-item.active').removeClass('active');
			$(this).addClass('active');
			var type = $(this).data('type');
			$('#tempType').val(type);
			if(parseInt(type) == 2){
				$('#bgpic_content').css('display','inherit');
				$('#showpic_content').css('display','inherit');
			}else{
				$('#bgpic_content').css('display','none');
				$('#showpic_content').css('display','none');
			}
		});
	})
</script>
</body>
</html>