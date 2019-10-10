import json
import pytz
from datetime import datetime

from textblob import TextBlob

from .google_api_handler import GoogleAPIHandler

#import for text processing
import nltk  
#nltk.download()
from nltk.corpus import stopwords  
from nltk import word_tokenize  
from nltk.data import load  
from nltk.stem import SnowballStemmer  
from string import punctuation  

class Utils():
	def __init__(self,google_api_key=None):
		#stopword list to use
		self.spanish_stopwords = stopwords.words('spanish')
		#spanish stemmer
		self.stemmer = SnowballStemmer('spanish')
		#punctuation to remove
		self.non_words = list(punctuation)  
		#we add spanish punctuation
		self.non_words.extend(['¿', '¡'])  
		self.non_words.extend(map(str,range(10)))
		self.google_api_key = google_api_key

	def textblob_safe(self, tweet):  
		try:
			return TextBlob(tweet).detect_language()
		except Exception as e:
			pass  	
	def textblob_translate(self, tweet, source, tarject):  
		try:
			return str(TextBlob(tweet).translate(from_lang=source, to=tarject))
		except Exception as e:
			return tweet

	def stem_tokens(self, tokens, stemmer):  
		stemmed = []
		for item in tokens:
			stemmed.append(stemmer.stem(item))
		return stemmed

	def tokenize(self, text):  
		# remove punctuation
		text_aux = ''.join([c for c in text if c.encode("latin-1", "ignore") not in self.non_words])
		# tokenize
		tokens_aux =  word_tokenize(text_aux)
		# delete stopwords
		tokens = [w for w in tokens_aux if not w in self.spanish_stopwords] 
		# stem
		try:
			stems = self.stem_tokens(tokens, self.stemmer)
		except Exception as e:
			print("tokenize",e)
			stems = ['']
		return stems	

	def get_sentiment(self, decoded):
		# Pass textual data to TextBlob to process
		tweet = TextBlob(decoded)

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
   
	def get_hashtags(self, decoded):
		hashtags = None
		# Obtain hashtag if available
		if len(decoded["entities"]["hashtags"]) > 0:
			hashtags = decoded["entities"]["hashtags"][0]["text"].title()
		return hashtags	

	def get_geo_info(self, decoded):
		country = None
		if self.google_api_key:
			handler = GoogleAPIHandler(self.google_api_key)
			if 'coordinates' in decoded and decoded['coordinates'] is not None:
				latitude = str(decoded['coordinates']['coordinates'][1])
				longitude = str(decoded['coordinates']['coordinates'][0])
				country = handler.get_geo_info(latitude, longitude)
			
		return country

	def get_timestamp(self, decoded):
		timestamp = datetime.strptime(decoded['created_at'],'%a %b %d %H:%M:%S +0000 %Y').replace(tzinfo=pytz.UTC).isoformat()
		return timestamp

	def find_place(self, tweet):
		"""Find the location of the tweet using 'place' or 'user location' in tweets
		:param tweet: JSON tweet data to process
		:return: 2 letter state abbreviation for tweet location"""

		# Find location from place
		if tweet['place'] is not None:
			if((len(tweet['place']['full_name'].split(','))) > 1):
				state = tweet['place']['full_name'].split(',')[1].strip()
			elif(len((tweet['place']['full_name'].split(','))) > 0):
				state = tweet['place']['full_name'].split(',')[0].strip()
			return state
		# Find location from user location
		elif tweet['user']['location'] is not None:
			# Split location into single word tokens
			places_splits = tweet['user']['location'].replace(',', ' ').split(' ')
			for place in places_splits:
				# Remove leading and trailing whitespaces
				place = place.strip()
				return place
		else:
			return None		

	# utilities
	def langid_safe(self, tweet):  
		try:
			return langid.classify(tweet)[0]
		except Exception as e:
			print("langid_safe",e)
			pass

	def langdetect_safe(self, tweet):  
		try:
			return detect(tweet)
		except Exception as e:
			print("langdetect_safe",e)
			pass		

    



	

	

	
		
