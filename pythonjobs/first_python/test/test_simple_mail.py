"""
 @Author: ASUS
 @Create: 2020/11/27 20:42
 @Desc: 
"""
import smtplib
from email.header import Header
from email.mime.text import MIMEText

mail = MIMEText(_text='python test mail', _subtype='plain', _charset='utf-8')

smtp = smtplib.SMTP("")

