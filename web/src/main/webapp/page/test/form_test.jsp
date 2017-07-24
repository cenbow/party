<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>同行者</title>
    <meta name="keywords" content="同行者,CEO户外,活动,创业者,活动报名">
    <meta name="description"  content="健康创业，户外同行">
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="HandheldFriendly" content="true">
    <link rel="icon" type="image/x-icon" href="${ctx}/image/favicon.ico" size="16x16 24x24 32x32 48x48">
    <link rel="shortcut icon" type="image/x-icon" href="${ctx}/image/favicon.ico">

    <!-- 作者：Juliana 时间：2016-02-16 描述：PC公共css-->
    <link rel="stylesheet"  href="${ctx}/themes/default/css/common/common.css">
    
    <link rel="stylesheet"  href="${ctxStatic}/layui-v1.0.7/css/layui.css">
</head>
<!--头部-->
<div class="header-public">
    <div class="header-public-main">
        <div class="header-public-logo">
            <h1>
                <a href="/">
                    <img title="同行者" alt="同行者" src="${ctx}/themes/default/image/logo_white.png">
                </a>
            </h1>
        </div>
        <ul class="header-public-nav-l">
            <li><a id="" href="/">首页</a></li>
            <li><a id="" href="/">关于同行者</a></li>
        </ul>
        <div class="user-address">
            <p class="show-city" data-code="guangzhou" id="show_city">
                <span>广州</span>
                <img src="${ctx}/themes/default/image/other/dropdown_icon.png" class="dropdown-icon">
            </p>
            <div class="city-box">
                <p class="current-city" id="j7"><span>当前：</span>广州</p>
                <p class="hot-city">热门城市：</p>
                <ul>
                    <li class="hot-city-li">
                        <a href=""><span id="" name="beijing">北京</span></a>
                    </li>
                </ul>
                <a href="/choose-city-info.html" class="more-city">更多城市&gt;</a>
            </div>
        </div>
        <div class="header-public-search">
            <input name="" value="" type="text" placeholder="搜索活动" id="pub-search">

            <a href="javascript:void(0)" id="searchBtn" onclick="">
                <img title="搜索" src="${ctx}/themes/default/image/other/searcnBtn.png">
            </a>
        </div>
        <div class="header-user-info">
            <div class="user-logo" style="background-image: url(${ctx}/image/def_user_logo.png)"></div>
            <span class="user-name">Juliana</span>
        </div>
        <ul class="header-public-nav-r">
            <li class="download-app">
                <a href="javascript:void(0)" rel="同行者">下载App</a>
                <div class="qr-code">
                    <img src="${ctx}/image/appqr_code.png" alt="">
                </div>
            </li>
            <li><a href="#" id="addPost">+发活动</a></li>
        </ul>
    </div>
</div>
<div class="header-public-bot"></div>
<div class="index-outside">
    <!--内容-->
    <section>
        <div class="section-main">
        	<!-- 正文请写在这里 -->
        	<form class="layui-form" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">单行输入框</label>
    <div class="layui-input-block">
      <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">验证必填项</label>
    <div class="layui-input-block">
      <input type="text" name="username" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">验证手机</label>
    <div class="layui-input-inline">
      <input type="tel" name="phone" lay-verify="phone" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">验证邮箱</label>
    <div class="layui-input-inline">
      <input type="text" name="email" lay-verify="email" autocomplete="off" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">验证数字</label>
      <div class="layui-input-inline">
        <input type="number" name="number" lay-verify="number" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">验证日期</label>
      <div class="layui-input-block">
        <input type="text" name="date" id="date" lay-verify="date" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">验证链接</label>
      <div class="layui-input-block">
        <input type="tel" name="url" lay-verify="url" autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">验证身份证</label>
    <div class="layui-input-block">
      <input type="text" name="identity" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">自定义验证</label>
    <div class="layui-input-inline">
      <input type="password" name="password" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
    </div>
    <div class="layui-form-mid layui-word-aux">请填写6到12位密码</div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">范围</label>
      <div class="layui-input-inline" style="width: 100px;">
        <input type="text" name="price_min" placeholder="￥" autocomplete="off" class="layui-input">
      </div>
      <div class="layui-form-mid">-</div>
      <div class="layui-input-inline" style="width: 100px;">
        <input type="text" name="price_max" placeholder="￥" autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">单行选择框</label>
    <div class="layui-input-block">
      <select name="interest" lay-filter="aihao">
        <option value=""></option>
        <option value="0">写作</option>
        <option value="1" selected="">阅读</option>
        <option value="2">游戏</option>
        <option value="3">音乐</option>
        <option value="4">旅行</option>
      </select>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">分组选择框</label>
    <div class="layui-input-inline">
      <select name="quiz">
        <option value="">请选择问题</option>
        <optgroup label="城市记忆">
          <option value="你工作的第一个城市">你工作的第一个城市</option>
        </optgroup>
        <optgroup label="学生时代">
          <option value="你的工号">你的工号</option>
          <option value="你最喜欢的老师">你最喜欢的老师</option>
        </optgroup>
      </select>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">行内选择框</label>
    <div class="layui-input-inline">
      <select name="quiz1">
        <option value="">请选择省</option>
        <option value="浙江" selected="">浙江省</option>
        <option value="你的工号">江西省</option>
        <option value="你最喜欢的老师">福建省</option>
      </select>
    </div>
    <div class="layui-input-inline">
      <select name="quiz2">
        <option value="">请选择市</option>
        <option value="杭州">杭州</option>
        <option value="宁波" disabled="">宁波</option>
        <option value="温州">温州</option>
        <option value="温州">台州</option>
        <option value="温州">绍兴</option>
      </select>
    </div>
    <div class="layui-input-inline">
      <select name="quiz3">
        <option value="">请选择县/区</option>
        <option value="西湖区">西湖区</option>
        <option value="余杭区">余杭区</option>
        <option value="拱墅区">临安市</option>
      </select>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">复选框</label>
    <div class="layui-input-block">
      <input type="checkbox" name="like[write]" title="写作">
      <input type="checkbox" name="like[read]" title="阅读" checked="">
      <input type="checkbox" name="like[game]" title="游戏">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">开关-关</label>
    <div class="layui-input-block">
      <input type="checkbox" name="close" lay-skin="switch" title="开关">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">开关-开</label>
    <div class="layui-input-block">
      <input type="checkbox" checked="" name="open" lay-skin="switch" lay-filter="switchTest" title="开关">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">单选框</label>
    <div class="layui-input-block">
      <input type="radio" name="sex" value="男" title="男" checked="">
      <input type="radio" name="sex" value="女" title="女">
      <input type="radio" name="sex" value="禁" title="禁用" disabled="">
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">普通文本域</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">编辑器</label>
    <div class="layui-input-block">
      <textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="LAY_demo_editor"></textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
  </div>
</form>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
  <legend>方框风格的表单集合</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">长输入框</label>
    <div class="layui-input-block">
      <input type="text" name="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">短输入框</label>
    <div class="layui-input-inline">
      <input type="text" name="username" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">日期选择</label>
      <div class="layui-input-block">
        <input type="text" name="date" id="date" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">行内表单</label>
      <div class="layui-input-inline">
        <input type="number" name="number" autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">密码</label>
    <div class="layui-input-inline">
      <input type="password" name="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
    </div>
    <div class="layui-form-mid layui-word-aux">请务必填写用户名</div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">范围</label>
      <div class="layui-input-inline" style="width: 100px;">
        <input type="text" name="price_min" placeholder="￥" autocomplete="off" class="layui-input">
      </div>
      <div class="layui-form-mid">-</div>
      <div class="layui-input-inline" style="width: 100px;">
        <input type="text" name="price_max" placeholder="￥" autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">单行选择框</label>
    <div class="layui-input-block">
      <select name="interest" lay-filter="aihao">
        <option value=""></option>
        <option value="0">写作</option>
        <option value="1" selected="">阅读</option>
        <option value="2">游戏</option>
        <option value="3">音乐</option>
        <option value="4">旅行</option>
      </select>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">行内选择框</label>
    <div class="layui-input-inline">
      <select name="quiz1">
        <option value="">请选择省</option>
        <option value="浙江" selected="">浙江省</option>
        <option value="你的工号">江西省</option>
        <option value="你最喜欢的老师">福建省</option>
      </select>
    </div>
    <div class="layui-input-inline">
      <select name="quiz2">
        <option value="">请选择市</option>
        <option value="杭州">杭州</option>
        <option value="宁波" disabled="">宁波</option>
        <option value="温州">温州</option>
        <option value="温州">台州</option>
        <option value="温州">绍兴</option>
      </select>
    </div>
    <div class="layui-input-inline">
      <select name="quiz3">
        <option value="">请选择县/区</option>
        <option value="西湖区">西湖区</option>
        <option value="余杭区">余杭区</option>
        <option value="拱墅区">临安市</option>
      </select>
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">文本域</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <button class="layui-btn" lay-submit="" lay-filter="demo2">跳转式提交</button>
  </div>
</form>
        </div>
    </section>
</div>

<!--底部-->
<div class="footer-gray">
    <div class="footer-content">
        <div class="qr-code-content clearfix">
            <div class="qr-code-item">
                <p class="f16" >微信公众号</p>
                <img src="${ctx}/image/gzhqr_code.png" alt="微信公众号">
                <p>同行者CEO户外</p>
            </div>
            <div class="qr-code-item">
                <p class="f16">同行者APP</p>
                <img src="${ctx}/image/appqr_code.png" alt="app下载二维码">
                <p>扫描二维码下载</p>
            </div>
            <div class="qr-code-item">
                <p class="f16">微信小程序</p>
                <img src="${ctx}/image/xcxqr_code.png" alt="扫一扫使用小程序">
                <p>扫一扫使用小程序</p>
            </div>
        </div>
        <div class="copy-right">
            <p>粤ICP备16040064号-1</p>
            <p>深圳前海同行者咨询有限公司，版权所有</p>
        </div>
    </div>
</div>
<!-- <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script> -->
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/myplugin/uploadCI.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/UEditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" charset="utf-8" src="${ctxStatic}/layui-v1.0.7/layui.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
  var form = layui.form()
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
 
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length < 5){
        return '标题至少得5个字符啊';
      }
     /*     $.ajax({
             async: false, // 使用同步的方法
             data: { //要提交到服务端验证的用户名
                 userName: value
             },
             dataType: 'json',
             success: function(result) { //栗子，返回的数据结果：{"rel":true,"message":"用户名已存在！"}
                 if (result.rel) {
                     return result.message;
                 }
             },
             url: 'http://xxx.com/api/' // 这里写你要验证的地址哦。
         }); */
    }
    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
    ,content: function(value){
      layedit.sync(editIndex);
    }
  });
  
  
  
  //监听提交
  form.on('submit(demo1)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return false;
  });
  
  
});
</script>
</body>
</html>