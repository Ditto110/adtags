import sys
from importlib import reload

"""
reload() 函数用于重载之前载入的模块
sys.setdefaultencoding('ascii') 适用于Python2，Python3字符串默认编码unicode，因此此方法不存在
unicode编码 为每种语言字符设置了统一并且唯一的二进制编码，以满足跨语言跨平台的场景

编码：unicode类型 ——> 字节流 
解码：字节流 ——> unicode类型

# -*- coding: -*-  表示当前脚本文件内容的编码是utg-8
sys.setdefaultencoding('utf-8') 表示python解析器处理数据过程中的编解码格式是utf-8
"""

print(sys.getdefaultencoding())

reload(sys)

print("sss".encode())
print(b"sss".decode())
print(str("sss".encode()))  # str 函数如果指定了encode,则按照encode 编码，否则返回的是这个对象的__str__()
print(str("sss".encode(), encoding="utf-8"))
