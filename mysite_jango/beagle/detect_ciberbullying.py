#export TF_XLA_FLAGS=--tf_xla_cpu_global_jit
from pandasticsearch import DataFrame
from pandasticsearch import Select
from elasticsearch import Elasticsearch
from pandas.io.json import json_normalize
import sys

import smtplib, ssl
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

class Detect():
	def __init__(self,index):
		self.index = index
		print("detect - ",self.index)	

	def get_tweets_negative(self,index_search):
	    index_aux = "tweets-sentiment_"+index_search
	    df1 = DataFrame.from_es(url="http://192.168.127.129:9200", index=index_aux, doc_type="count", compat=6)
	    es = Elasticsearch('http://192.168.127.129:9200')
	    results = es.count(index=index_aux, body={"query": {"term" : {"sentiment": "negative"}}})
	    #print("negative", results)
	    return results

	def get_all_tweets(self,index_search):
	    index_aux = "tweets-sentiment_"+index_search
	    df1 = DataFrame.from_es(url="http://192.168.127.129:9200", index=index_aux, doc_type="new-tweet", compat=6)
	    es = Elasticsearch('http://192.168.127.129:9200')
	    results = es.count(index=index_aux, body={"query": {"match_all": {}}})
	    #print("all", results)
	    return results
	    
	def send_mail(self, email):
		sender_email = "beagle.search.utad@gmail.com"
		receiver_email = email
		password = "beagle2019"

		message = MIMEMultipart("alternative")
		message["Subject"] = "Alerta Beagle"
		message["From"] = sender_email
		message["To"] = receiver_email

		# Create the HTML message
		html = """\
		<html>
		  <body>
		    <p>Hola,<br>
		       Alerta de ciberacoso<br>
		       Para más información acceda a <a href="http://localhost:9000/#/login">Beagle</a> 
		    </p>
		  </body>
		</html>
		"""

		# Turn these into plain/html MIMEText objects
		part2 = MIMEText(html, "html")

		# Add HTML/plain-text parts to MIMEMultipart message
		# The email client will try to render the last part first
		message.attach(part2)

		# Create secure connection with server and send email
		context = ssl.create_default_context()
		with smtplib.SMTP_SSL("smtp.gmail.com", 465, context=context) as server:
		    server.login(sender_email, password)
		    server.sendmail(
			sender_email, receiver_email, message.as_string()
		    )

	def run(self,token,email):    
		if token != None:
			tweets_negative = self.get_tweets_negative(token)
			number_negative = 0
			negative_df = json_normalize(tweets_negative)
			if negative_df.size > 0:	
				number_negative = negative_df["count"][0]
				print("resultado negative",number_negative)
			
			
			all_tweets = self.get_all_tweets(token)
			number_all = 0
			all_tweets_df = json_normalize(all_tweets)
			if all_tweets_df.size > 0:		
				number_all = all_tweets_df["count"][0]
				print("resultado all",number_all)

			if (number_all > 0):
				porcentaje = number_negative*100/number_all		
				print("Porcentaje: ", porcentaje)

				if porcentaje > 2:
					self.send_mail(email)
		else:
			print("QUE QUIERES BUSCAR?")

    

