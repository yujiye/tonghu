<#include "/top.ftl">

<!-- 主内容模块 右侧内容列表模块 start -->
<div class="content_wrapper">
    <div class="contents">
        <div class="row-fluid">

        </div>
        <div class="separator"><span></span></div>
        <div id="msg" class="msg"></div>
        <div class="row-fluid">
            <div class="span12">
                <div class="widget_wrapper" style="overflow:hidden;">
                    <div class="widget_header">
                        <h3 class="icos_${currentPageUrl?substring(1,currentPageUrl?index_of("."))}">${pageHanName}</h3>
                    </div>
                    <div class="widget_content no-padding">
                        <div style="float:left; margin-top: 50px; margin-bottom: 50px; margin-left:60px; width:500px;">
                            <div id="warningDivTitle">
                                <div class="showCont">您好，${userInfo.trueName}！</div>
                                <div class="showCont">潼湖科技小镇可视化平台管理系统！</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 主内容模块 右侧内容列表模块结束 end -->
<#include "/foot.ftl">
</body>
</html>