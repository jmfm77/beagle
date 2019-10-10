from tweepy import OAuthHandler
from tweepy import Stream
from tweepy import TweepError

import sys
import logging

# Import twitter keys and tokens
from .config import *

# Import listener
from .tools.tweet_listener import TweetStreamListener
from .tools.tweet_api import TweetApiSearch
class TweetSearch():
    def __init__(self,index):
        self.index = index
    def run(self,texto_buscar):
        """Pipelines"""
        logging.basicConfig(level=logging.ERROR)
        # Obtain the input topics of your interests
        if (texto_buscar is None or texto_buscar == ""):
             return
        topics = [texto_buscar.lower()]
        '''
        if len(sys.argv) == 1:
            # Default topics
            topics = []
        else:
            for topic in sys.argv[1:]:
                topics.append(topic)
        '''
        # Change this if you're not happy with the index and type names
        index = "tweets-sentiment_"+self.index
        doc_type = "new-tweet"

        print("==> Topics", topics)
        print("==> Index: {}, doc type: {}".format(index, doc_type))
    
        print("==> Start searchTweets...")
        search = TweetApiSearch(index, doc_type, google_api_key, consumer_key, consumer_secret, access_token, access_token_secret)
        search.searchTweets(topics, 1000)
    
        print("==> Start retrieving tweets...")
    
        # Create instance of the tweepy tweet stream listener
        listener = TweetStreamListener(index, doc_type, topics, google_api_key=google_api_key)
    
        # Set twitter keys/tokens
        auth = OAuthHandler(consumer_key, consumer_secret)
        auth.set_access_token(access_token, access_token_secret)
    
        # Set the program to restart automatically in case of errors
        while True:
            try:
                # Search twitter for topics of your interests
                print("--> Itetate")
                stream = Stream(auth, listener)
                stream.filter(track=topics)
            except KeyboardInterrupt:
                # To stop the program
                stream.disconnect()
                print("==> Stop")
                raise
            except TweepError as e:
                if e.reason[0]['code'] == "420":
                    print("error 420")
                    time.sleep(200)
                    continue
            except stream.ConnectionError as e:
                print (" error: ", e.message)
                time.sleep(200)
                continue
            except stream.AuthenticationError as e:
                print (" error: ", e.message)
                raise
            except:
                print("main - Error inesperado:", sys.exc_info()[0])
                raise
