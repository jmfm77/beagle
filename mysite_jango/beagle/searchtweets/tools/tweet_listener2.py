import json
import pytz
from datetime import datetime

from tweepy.streaming import StreamListener
from textblob import TextBlob
from elasticsearch import Elasticsearch

from tools.google_api_handler import GoogleAPIHandler

#import for text processing
import nltk  
from nltk.corpus import stopwords  
from nltk import word_tokenize  
from nltk.data import load  
from nltk.stem import SnowballStemmer  
from string import punctuation  

ES_HOST = {"host" : "localhost", "port" : 9200}

class TweetStreamListener(StreamListener):
	def __init__(self, index, doc_type, google_api_key=None):
		super(TweetStreamListener, self).__init__()

		self.index = index
		self.doc_type = doc_type
		self.google_api_key = google_api_key

		#stopword list to use
		self.spanish_stopwords = stopwords.words('spanish')
		#spanish stemmer
		self.stemmer = SnowballStemmer('spanish')
		#punctuation to remove
		self.non_words = list(punctuation)  
		#we add spanish punctuation
		self.non_words.extend(['¿', '¡'])  
		self.non_words.extend(map(str,range(10)))

	def on_data(self, data):
		""""On success.
		To retrieve, process and organize tweets to get structured data
		and inject data into Elasticsearch
		"""

		print("=> Retrievd a tweet")

		# Decode json
		dict_data = json.loads(data)

		# Process data
		language = self._textblob_safe(dict_data["text"])
		if (language != 'en' and language is not None):
			tanslated_text = self._textblob_translate(dict_data["text"],'en',language)
		else:
			tanslated_text = dict_data["text"]			
		tokenize_message = self._tokenize(dict_data["text"])			

		polarity, subjectivity, sentiment = self._get_sentiment(tanslated_text)
		print("[sentiment]", sentiment)

		hashtags = self._get_hashtags(dict_data)
		print("[hashtags]", hashtags)

		country = self._get_geo_info(dict_data)
		print("[country]", country)

		timestamp = self._get_timestamp(dict_data)
		print("[time]", timestamp)

		# Inject data into Elasticsearch
		doc = {"author": dict_data["user"]["screen_name"],
		       "timestamp": timestamp,
		       "message": dict_data["text"],
		       "tokenize_message": tokenize_message,
		       "tanslated_text": tanslated_text, 	
		       "language": language,	
		       "polarity": polarity,
		       "subjectivity": subjectivity,
		       "sentiment": sentiment,
		       "country": country,
		       "hashtags":hashtags}
		
		es = Elasticsearch(hosts = [ES_HOST])

		es.index(index=self.index, doc_type=self.doc_type, body=doc)
		
		return True
	'''
	def on_error(self, status):
        	"""On failure"""
	        print(status)
	'''	
    
	def _get_sentiment(self, decoded):
		# Pass textual data to TextBlob to process
		tweet = TextBlob(decoded, analyzer=NaiveBayesAnalyzer())

		# [0, 1] where 1 means very subjective
		subjectivity = tweet.sentiment.subjectivity
		# [-1, 1]
		polarity = tweet.sentiment.polarity
		
		# Determine if sentiment is positive, negative, or neutral
		if polarity < 0:
			sentiment = "negative"
		elif polarity == 0:
			sentiment = "neutral"
		else:
			sentiment = "positive"

		return polarity, subjectivity, sentiment
    
	def _get_hashtags(self, decoded):
		hashtags = None
		# Obtain hashtag if available
		if len(decoded["entities"]["hashtags"]) > 0:
			hashtags = decoded["entities"]["hashtags"][0]["text"].title()
		return hashtags
    
	def _get_geo_info(self, decoded):
		country = None
		if self.google_api_key:
			handler = GoogleAPIHandler(self.google_api_key)
			if 'coordinates' in decoded and decoded['coordinates'] is not None:
				latitude = str(decoded['coordinates']['coordinates'][1])
				longitude = str(decoded['coordinates']['coordinates'][0])
				country = handler.get_geo_info(latitude, longitude)
			
		return country
    
	def _get_timestamp(self, decoded):
		timestamp = datetime.strptime(decoded['created_at'],'%a %b %d %H:%M:%S +0000 %Y').replace(tzinfo=pytz.UTC).isoformat()
		return timestamp

	# utilities
	def _langid_safe(self, tweet):  
		try:
			return langid.classify(tweet)[0]
		except Exception as e:
			pass

	def _langdetect_safe(self, tweet):  
		try:
			return detect(tweet)
		except Exception as e:
			pass

	def _textblob_safe(self, tweet):  
		try:
			return textblob.TextBlob(tweet).detect_language()
		except Exception as e:
			pass  

	def _textblob_translate(self, tweet, source, tarject):  
		try:
			return textblob.TextBlob(tweet).translate(from_lang=source, to=tarject)
		except Exception as e:
			pass  

	def _stem_tokens(self, tokens, stemmer):  
		stemmed = []
		for item in tokens:
			stemmed.append(stemmer.stem(item))
		return stemmed

	def _tokenize(self, text):  
		# remove punctuation
		text = ''.join([c for c in text if c.encode("latin-1", "ignore") not in self.non_words])
		# tokenize
		tokens =  word_tokenize(text)
		# stem
		try:
			stems = self._stem_tokens(tokens, self.stemmer)
		except Exception as e:
			print(e)
			print(text)
			stems = ['']
		return stems		
		
