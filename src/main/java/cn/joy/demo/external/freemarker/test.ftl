<#assign fieldWidth = 123 />
<#--
<#if fieldWidth?is_number>
	<#assign fieldWidth = fieldWidth+65 />
</#if>
-->
<#assign cmpId = params.id/>
<#assign cmpName = params.name/>
<#assign fileNum = 0/>
<#assign multiNum = "0"/>
<#if params.multi=='Y'&&params.images??&&params.images?size gt 1>
	<#assign fileNum = params.images?size-1/>
	<#list 0..fileNum as i>
		<#if i gt 0><#assign multiNum = multiNum+",">
		<#assign multiNum = multiNum+i/>
		</#if>
	</#list>
</#if>
<div id="${cmpId}_ct" class='cmp cmp-image cmp-fileupload cmp-imageupload cmp-table' <#if !fieldWidth?is_number>uw="${fieldWidth}"</#if>>
<table cellspacing=0 cellspadding=0 style="display:inline;"><tr><td valign=top style="${(params.multi=='Y')?string('','display:none;')}width:17px;"><#rt/>
<input type='hidden' id='${cmpId}_fileUnchange' name='${cmpName}_fileUnchange' value=''><#rt/>
<input type='hidden' id='${cmpId}_multi_num' name='${cmpName}_multi_num' value='${multiNum}' num='${fileNum}' rowNum="${params.rowNum}"><#rt/>
<button type="button" id='${cmpId}_fileAdd' class="cmp-fileadd ico-add" onclick="FormUtils.fileEH(this)" <#if params.edit=="false">disabled</#if>>+</button><#rt/>
</td><td id='${cmpId}_files' style="padding:0;margin:0" nowrap><#rt/>
<#list 0..fileNum as i>
<#if params.images??&&params.images?size gt 0>
<#assign filename = params.images[i][0]>
<#assign fileDisplayName = filename?split('*')[0]>
</#if>
<div id='${cmpId}_div_${i}'><#rt/>
<input type="file" class="cmp-fileinput" onchange="FormUtils.fileEH(this)" <#if params.edit=="false">disabled</#if> unselectable='on' id="${cmpId}_${i}" name="${cmpName}${(i==0)?string('','_'+i)}" <#if fieldWidth?is_number>style="width:${fieldWidth}px;"</#if> num="${i}"<#rt/>
	filename="${filename}" <#rt/>
><button type="button" id="${cmpId}_fileDel_${i}" <#if params.edit=="false">disabled</#if> class="cmp-filedel ico-del" onclick="FormUtils.fileEH(this)">-</button><#rt/>
<#if params.breakLine=='true'><br><#rt/></#if>
<#assign realPath = params.images[i][1]/>
<#assign imageUrl = params.images[i][2]/>
<#if params.downloadFileRuleId?has_content>
 <#assign imageUrl = '../formdatafacility/image.do?file=${filename}&downloadFileRuleId=${params.downloadFileRuleId}'/>
</#if>
<#if filename?has_content>
<a id="${cmpId}_imglink" onclick="FormUtils.imgEH(this)" class="cmp-imgpreview" imgUrl="${imageUrl}"><#rt/>
<img id="${cmpId}_img" realPath="${realPath}" src="${imageUrl}" class="imgsbg thumbnail"/></a><#rt/>
</#if>
</div><#rt/>
</#list>
</td></tr></table><#rt/>
<input biz="${params.bizType}" type="hidden" id="${cmpId}" name="${cmpName}_original" cmpName="${cmpName}" value="${params.html?html}" multi="${params.multi}"<#rt/>
 ${validateAttr}<#rt/> 
 entityName="${params.entityName}"<#rt/>
 fieldName="${params.entityName}"<#rt/>
 tableName="${params.tableName}"<#rt/>
 <#if params.formColumns=="1">formColumns="1"</#if><#rt/>
><#rt/>
</div>