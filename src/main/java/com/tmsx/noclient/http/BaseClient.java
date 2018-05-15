package com.tmsx.noclient.http;

/**
 * httpclient使用流程
 * 1. 创建HttpClient对象。
 * 2. 创建请求方法的实例，并指定请求URL。如果需要发送GET请求， 创建HttpGet对象； 如果需要发送POST请求，创建HttpPost对象。
 * 3. 如果需要发送请求参数， 可调用HttpGet、 HttpPost共同的setParams(HetpParams params)方法来添加请求参数； 对于HttpPost对象而言，
 * 也可调用setEntity(HttpEntity entity)方法来设置请求参数。
 * 4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求该方法返回一个HttpResponse。
 * 5. 调用HttpResponse的getAllHeaders()、 getHeaders(String name)等方法可获取服务器的响应头； 调用HttpResponse的getEntity()方法
 * 可获取HttpEntity对象，该对象包装了服务器的响应内容。 程序可通过该对象获取服务器的响应内容。
 * 6. 释放连接。 无论执行方法是否成功， 都必须释放连接。
 *
 * @author yang.zhou
 * @date 2017/10/27
 */
public class BaseClient {

}
