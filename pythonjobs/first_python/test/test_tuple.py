"""
tuple
"""


# key-value
def get_tuple_data():
    data = {'Google': 'www.google.com', 'Runoob': 'www.runoob.com', 'taobao': 'www.taobao.com'}
    print(data.items())
    for k,v in data.items():
        print("key:%s,value:%s" % (k,v))


# 元组
def get_tuple_data2():
    data = [('Google','www.google.com',"a"), ('Runoob', 'www.runoob.com',"b"), ('taobao', 'www.taobao.com',"c")]
    for k,v,s in data:
        print("key:%s\t value:%s\t str:%s" % (k,v,s))


if __name__ == '__main__':
    pass
    get_tuple_data()
    get_tuple_data2()