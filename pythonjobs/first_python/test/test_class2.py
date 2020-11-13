import pymysql


class QueryMysql:

    def __init__(self):
        self.con = pymysql.Connect(host="192.168.52.13",
                                   port=3306,
                                   user="root",
                                   password="",
                                   database="appstorev2",
                                   charset="utf8")

    def close(self):
        self.con.close()

    def query(self):
        cursor = self.con.cursor()
        cursor.execute("select * from tb_app limit 10")
        fetchone = cursor.fetchone()
        print(fetchone)
        print(fetchone[0])

    q = ha = query


mysql = QueryMysql()
mysql.q()
