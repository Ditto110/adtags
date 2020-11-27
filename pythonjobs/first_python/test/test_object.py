"""
 @Author: ASUS
 @Create: 2020/11/27 19:53
 @Desc: python 对象三要素，id/type/value,以此三要素判断对象是否相等
 == 判断的是value 是否相等，is 判断的是id 是否相等
"""
a = [1, 2, 3, 4]
b = ['1', '2', '3', '4']
c = [1, 2, 3, 4]
# value 比较
print('a==b:%s,id(a):%s,id(b):%s' % (a == b, id(a), id(b)))
print(a is b)
#  value 比较
print('a==c:%s,id(a):%s,id(c):%s' % (a == c, id(a), id(c)))
print(a == c)
# id 比较
print('a is c:%s,id(a):%s,id(c):%s' % (a is c, id(a), id(c)))
print(a is c)
