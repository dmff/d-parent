## 统一api两种实现方式

- 第一种实现方式使用路由转发
- 第二种实现使用自定义框架，反射实现

-参考博客：https://blog.csdn.net/qq_38011415/article/details/88779364

参考请求报文：
{
  "api_request_id": "string",
  "app_id": "api.test",
  "charset": "string",
  "content": "{\"itemList\":[{\"password\":\"string\",\"realName\":\"string\",\"username\":\"string\"}],\"test2BO\":{\"password\":\"string\",\"username\":\"\"}}",
  "sign": "string",
  "sign_type": "string",
  "version": "string"
}