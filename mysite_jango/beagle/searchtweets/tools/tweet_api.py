import tweepy
import sys
from elasticsearch import Elasticsearch
from .utils import Utils
from .analysis import Analysis

ES_HOST = {"host" : "192.168.127.129", "port" : 9200}
################################# END OF IMPORTS ###########################################
class TweetApiSearch():

	def __init__(self, index, doc_type, google_api_key, consumer_key, consumer_secret, access_token, access_token_secret):
		self.index = index
		self.doc_type = doc_type
		self.google_api_key = google_api_key
		self.consumer_key = consumer_key
		self.consumer_secret = consumer_secret
		self.access_token = access_token
		self.access_token_secret = access_token_secret 
				
	############################################################################
	####################### PROCESS SIMPLE #####################################
	############################################################################
	def searchTweets(self, keyword, numberOfTweets):
		auth = tweepy.OAuthHandler(self.consumer_key, self.consumer_secret)
		auth.set_access_token(self.access_token, self.access_token_secret)
		api = tweepy.API(auth, wait_on_rate_limit=True)

		numberOfTweets = int(numberOfTweets)
	
		#create instance of elasticsearch
		es = Elasticsearch(hosts = [ES_HOST])
		
		util = Utils(self.google_api_key)

		for tweet in tweepy.Cursor(api.search, keyword, lang="es").items(numberOfTweets):
			try:
				# Decode json
				dict_data = tweet._json	
			
				language = "es"
				tokenize_message = ""

				texto_original = dict_data["text"]				

				translated_text = dict_data["text"]	
				
				# Process data
				language = util.textblob_safe(dict_data["text"])

				if (language != 'en' and language is not None):
					tanslated_text = util.textblob_translate(dict_data["text"],'en',language)
				else:
					tanslated_text = dict_data["text"]
				
				tokenize_message = util.tokenize(dict_data["text"])
				
				#====================== DL ===========================			
				#Analysis deep learning
				analysis = Analysis()
				sentiment_DL = analysis.run(translated_text)
				if sentiment_DL is not None:
					print("SENTIMENT_DL: ",sentiment_DL)
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
				       "tanslated_text": tanslated_text, 	
				       "language": language,		
				       "polarity": polarity,
				       "subjectivity": subjectivity,
				       "sentiment": sentiment,
				       "sentimentDL": sentiment_DL,
				       "country": country,
				       "hashtags":hashtags,
				       "keyword":keyword}
				
				# add text and sentiment info to elasticsearch
				#print("guardando índice...")				
				es.index(index=self.index, doc_type=self.doc_type, body=doc)        
				#print("fin índice")
			except tweepy.TweepError as e:
			    print(e.reason)
			    return			
			except StopIteration:
			    break
			except tweepy.TweepError as e:
        		    if e.reason[0]['code'] == "420":
                                searchTweets(keyword, numberOfTweets)
			except:
			    print("TweetApiSearch - Error inesperado:", sys.exc_info()[0])
			    raise 

		print("end searchTweets")
		return
	
	def on_error(self, status):
		"""On failure"""
		print(status)
		return

	
	
