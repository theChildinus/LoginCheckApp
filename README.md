## 接口及示例代码说明

## 全局

- 开发环境下，http协议通信，需修改 `utils/Common.java` 变量 `devUrlPrefix`
- 正式环境下，https协议通信，需修改 `utils/Common.java` 变量 `proUrlPrefix` 保留`/api/`
- 指定本地 `openssl` 路径，需修改 `utils/Common.java`  变量 `opensslPath `

## 接口及示例代码

#### 访问控制执行

- URL：policy/check
- 示例代码：Check.java

```json
// 请求
{
    "policyname": "策略1", //策略集合名
    "policysub": "技术部/经理", //策略规则主体
    "policyobj": "物联网资源/态势图", //策略规则资源
    "policyact": "read", //策略规则动作
    "username": "zhao", //用户名
    "userhash": "9e9e341f7d0597a5490e6e0ddf03edd3", //用户Hash，身份认证通过后产生
}

// 响应
{
	"code": 0, // 0 正常， -1 错误
	"normsg" / “errmsg”: "" //正确信息 或 错误信息
}
```

#### 身份认证

- URL：user/verifyIdentity
- 示例代码：VerifyIdentity.java

第一阶段：

```json
// 请求
{
    "name": "zhao", //用户名
}

// 响应
{  
    "code": 2610529275472644968, //大随机数，用于签名
}
```

第二阶段：

```json
// 请求
{
    "rand":2610529275472644968, // 随机数
    "name":"zhao", // 用户名
  "sign":"MEYCIQD9n9Sk15mGW\/u9QqmcqJA\/SAw96EyIaX3P7T\/Hz6RM9AIhAKKBtqGPT1EaSI8u5cbQq5Wn6mYdJxAvr3Jq44JBBELM", // 签名结果 Base64编码
    "type":"user" // 用户类型，可选
}

// 响应
{  
    "code": 0,  // 认证结果 0成功，-1失败
    "normsg": "VerifyIdentity zhao Success"
}

// 对签名计算MD5 产生用户Hash
```

#### 证书验证

- URL：user/verifyCert
- 示例代码：VerifyCert.java

```json
// 请求
{
  "certcontent":"LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUNWekNDQWY2Z0F3SUJBZ0lVUUJNd1I1Y21uaWQ4NGgrSzhWTHhob2I5dVBRd0NnWUlLb1pJemowRUF3SXcKY3pFTE1Ba0dBMVVFQmhNQ1ZWTXhFekFSQmdOVkJBZ1RDa05oYkdsbWIzSnVhV0V4RmpBVUJnTlZCQWNURFZOaApiaUJHY21GdVkybHpZMjh4R1RBWEJnTlZCQW9URUc5eVp6RXVaWGhoYlhCc1pTNWpiMjB4SERBYUJnTlZCQU1UCkUyTmhMbTl5WnpFdVpYaGhiWEJzWlM1amIyMHdIaGNOTWpBd016QTFNVE16TkRBd1doY05NakV3TXpBMU1UTXoKT1RBd1dqQWVNUTB3Q3dZRFZRUUxFd1IxYzJWeU1RMHdDd1lEVlFRREV3UjZhR0Z2TUZrd0V3WUhLb1pJemowQwpBUVlJS29aSXpqMERBUWNEUWdBRUY2VC9NNzFEdzdlQ1BpY0ZCcitncVRVWTYwclJLV0ovQlUwYTZKMlUvcXJiCjRKMTdvUlhCRzdWb0ZKRlNzTklxajRwRSt2VFJFUlhmVGZQUkQvejBDNk9CeERDQndUQU9CZ05WSFE4QkFmOEUKQkFNQ0I0QXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUxlQzh2TnZhMnVnVm1DQ3hrYkFNc3R6dQo5VE13S3dZRFZSMGpCQ1F3SW9BZ1FqbXFEYzEyMnU2NHVnemFjQmhSMFVVRTB4cXRHeTNkMjZ4cVZ6WmVTWHd3ClZRWUlLZ01FQlFZSENBRUVTWHNpWVhSMGNuTWlPbnNpYUdZdVFXWm1hV3hwWVhScGIyNGlPaUlpTENKb1ppNUYKYm5KdmJHeHRaVzUwU1VRaU9pSjZhR0Z2SWl3aWFHWXVWSGx3WlNJNkluVnpaWElpZlgwd0NnWUlLb1pJemowRQpBd0lEUndBd1JBSWdDMHV0V3padWRxSmdwa3h5TFNnL3czSTJGTFBaUzEvZUQ3UFphcHZ0cHVRQ0lITSt3cUhmCmhOK2t0Rko1dHNycldTVXp5NEh1djVLb0wyK3BNajIvQzQ0dQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg==", // 证书内容 Base64编码
    "name":"zhao", //用户名
    "type":"user" //用户类型 可选
}

// 响应
{  
    "code": 0,  
    "normsg": "VerifyCert zhao Success"
}
```

#### 获取策略规则（发布订阅系统使用）

- URL：policy/list
- 示例代码：GetPolicies.java

```json
// 请求
{
    "search_sub":"策略1"
}

// 响应
{  
    "list":
    [    
        {
            "policy_id": 2,      
            "policy_name": "策略1",      
            "policy_sub": "kong",      
            "policy_obj": "物联网资源/报表",      
            "policy_act": "upload",      
            "policy_type": "物联网用户",      
            "policy_ctime": "2019-10-11T21:24:22Z"    
        },    
        {      
            "policy_id": 3,      
            "policy_name": "策略1",      
            "policy_sub": "kong",      
            "policy_obj": "物联网资源/报表",      
            "policy_act": "exec",      
            "policy_type": "物联网用户",      
            "policy_ctime": "2019-10-11T21:25:02Z"    
        },  
    ],  
    "count": 7
}

```

#### 用户登录

- URL：user/login
- 示例代码：Login.java

```json
// 第一次登录
// 请求
{
    "role":"技术部\/培训部\/经理", //用户角色
    "name":"zhao", //用户名
  "secret":"MEUCIDIIii67dlQvRRZil4gV9yd24c06wcifqj72+5xnQqtbAiEAh9Qi7kv2SyhHjWdpdaGQp9YyZnoL+PfDi5rATWGKTQk=" //对123456进行签名的结果 Base64编码
}
// 响应
{  
    "code": 0,  
  "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE1ODM3NDg1MjgsInN1YiI6InpoYW8ifQ.4nqSBPWceAm-w8hWfdjvwazZir8fQ1ekU_qJfTDCCYg" //token 填入HTTP头部 Authorization 字段
}

// 第 n>1 次登录
// 请求
{
    "role":"技术部\/培训部\/经理",
    "name":"zhao"
}

// 响应
{  
    "code": 0,  
    "normsg": "Login zhao Success"
}
```

#### 用户登出

- URL：user/logout
- 示例代码：Logout.java

```json
// 请求
{
    "name":"zhao"
}

// 响应
{  
    "code": 0,  
    "normsg": "Logout zhao Success"
}

```

