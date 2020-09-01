/**
*   <#assign handlerGroup = mapping.body.contexts['application-1'].mappings.dispatcherServlets.dispatcherServlet>
*   <#if author??>@author ${author} <#else> add author here </#if>
*   API Count : ${handlerGroup?size}
*
*/
import { stringify } from 'qs';
import request from '@/utils/request';

<#list handlerGroup as handler>
<#if handler.details??>
<#assign options = handler.details.requestMappingConditions>
<#if options.methods[0]?? && options.methods[0] == 'GET' && options.patterns[0]?index_of('{id}')==-1>
export async function queryOptionList(params) {
    return request(`${options.patterns[0]}?${r'${stringify(params)}'}`);
}

<#elseif options.methods[0]?? && options.methods[0] == 'GET' && options.patterns[0]?index_of('{id}')!=-1>
export async function queryOptionList(params) {
    const { id, ...otherParams } = params;
    return request(`${options.patterns[0]}?${r'${stringify(otherParams)}'}`);
}

<#elseif options.methods[0]?? && options.methods[0] == 'POST'>
export async function submitOption(params) {
    return request('${options.patterns[0]}', {
        method: 'POST',
        body: params,
    });
}

<#elseif options.methods[0]?? && options.methods[0] == 'PUT'>
export async function submitOption(params) {
    return request('${options.patterns[0]}', {
        method: 'PUT',
        body: params,
    });
}
<#elseif options.methods[0]?? && options.methods[0] == 'DELETE'>

<#else>
</#if>
</#if>
</#list>