import MySQLdb

connect = MySQLdb.connect(host='192.168.52.13', port=3306, user='root', password='', db='appstorev2')

cursor = connect.cursor()

execute = cursor.execute("select * from tb_app limit 2")

try:
    fetchone = cursor.fetchone()
    print(fetchone)
except Exception as e:
    print(e.args)
finally:
    cursor.close()
