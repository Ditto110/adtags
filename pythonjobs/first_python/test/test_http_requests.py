import requests
import json


# get请求
def test_get():
    r = requests.get("https://www.csdn.net/")
    print(r.encoding)
    r.encoding = 'utf-8'
    print(r.encoding)
    print(r.status_code)  # 返回状态码
    print(r.text)  # 以encoding 的方式解析响应体
    print(r.content)  # 以二进制的形式返回响应体
    print(r.json())  # 以内置的json解析工具返回


# post请求
def test_post():
    payload = {'some': 'data'}
    headers = {'content-type': 'application/json',
               'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:22.0) Gecko/20100101 Firefox/22.0'}

    json.dumps(payload)  # 将字典对象转换成json字符串
    requests.post("https://api.github.com/some/endpoint", data=json.dumps(payload),headers = headers)


if __name__ == '__main__':
    pass
