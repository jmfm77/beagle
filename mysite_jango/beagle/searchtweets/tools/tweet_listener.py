import json
import sys

from tweepy.streaming import StreamListener
from elasticsearch import Elasticsearch
from .utils import Utils
from .analysis import Analysis

ES_HOST = {"host" : "192.168.127.129", "port" : 9200}

class TweetStreamListener(StreamListener):
	def __init__(self, index, doc_type, keyword, google_api_key=None):
		super(TweetStreamListener, self).__init__()
		self.index = index
		self.doc_type = doc_type
		self.keyword = keyword
		self.google_api_key = google_api_key

	def on_data(self, data):
		try:		
			util = Utils(self.google_api_key)			
			print("=> Retrived a tweet")
			# Decode json
			dict_data = json.loads(data)
			#print("*********************************** ",dict_data)
			# Process data
			language = "es"
			language = util.textblob_safe(dict_data["text"])
			#print("language:",language)
			
			texto_original = dict_data["text"]				

			tanslated_text = dict_data["text"]
			if (language != 'en' and language is not None):
				translated_text = util.textblob_translate(dict_data["text"],'en',language)
			else:
				translated_text = dict_data["text"]
			#print("translated-->",translated_text)			

			tokenize_message = ""
			tokenize_message = util.tokenize(dict_data["text"])
			#print("TOKENIZE: ",tokenize_message)
			
			#====================== DL ===========================			
			#Analysis deep learning
			analysis = Analysis()
			sentiment_DL = analysis.run(translated_text)
			#if sentiment_DL is not None:
			#	print("SENTIMENT_DL: ",sentiment_DL)
			#=====================================================

			polarity, subjectivity, sentiment = util.get_sentiment(translated_text)
			#print("[sentiment]", sentiment)

			hashtags = util.get_hashtags(dict_data)
			#print("[hashtags]", hashtags)

			country = util.get_geo_info(dict_data)
			if country is None:
				country = util.find_place(dict_data)
			#print("[country]", country)

			timestamp = util.get_timestamp(dict_data)
			#print("[time]", timestamp)

			# Inject data into Elasticsearch
						
			doc = {"author": dict_data["user"]["screen_name"],
				"timestamp": timestamp,
				"message": texto_original,
				"tokenize_message": tokenize_message,
				"tanslated_text": translated_text, 	
				"language": language,		
				"polarity": polarity,
				"subjectivity": subjectivity,
				"sentiment": sentiment,
				"sentimentDL":sentiment_DL,
				"country": country,
				"hashtags":hashtags,
				"keyword":self.keyword}
			
			#print("DOC ",doc)
			es = Elasticsearch(hosts = [ES_HOST])
			#print("ELK ",es)
			es.index(index=self.index, doc_type=self.doc_type, body=doc)
		except:	
			print("TweetStreamListener - Error inesperado:", sys.exc_info()[0])
			raise
		return True
	
	def on_error(self, status):
        	"""On failure"""
	        print(status)
	        raise
