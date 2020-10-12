"""
占位符api
"""
province_name="广东"
city_name="深圳"
pop=1000
rate=1.4356


# 方式一
def get_place_holder1():
    print("method1")
    print("省份：%s,城市：%s,人口：%d" %(province_name,city_name,pop))

    # 一个参数可以不用括号，%与参数间一个空格
    print("幸福指数:%f" %(rate))
    print("幸福指数:%f" % rate)
    # 保留几位小数
    print("幸福指数:%.2f" % rate)
    # print("\n")


# 方式二
def get_place_holder2():
    print("method2")
    # 通过对应的位置关系匹配
    print("省份：{},城市：{},人口：{}".format(province_name,city_name,pop))
    # 通过key_name匹配
    print("省份：{p_name},城市：{c_name},人口：{pop}".format(p_name = province_name,c_name = city_name,pop=pop))


if __name__ == '__main__':
    get_place_holder1()
    get_place_holder2()