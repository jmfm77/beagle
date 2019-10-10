#export TF_XLA_FLAGS=--tf_xla_cpu_global_jit
#from elasticsearch to pandas
from pandasticsearch import DataFrame
from elasticsearch import Elasticsearch
from pandasticsearch import Select
import sys
if sys.version_info[0] < 3: 
    from StringIO import StringIO
else:
    from io import StringIO

#Module to handle regular expressions
import re
#manage files
import os
#Import pandas and numpy to handle data
import pandas as pd
import numpy as np
from pandas.io.json import json_normalize

#Import nltk to check english lexicon
import nltk
from nltk import UnigramTagger as ut
from nltk import BigramTagger as bt
from nltk.tokenize import word_tokenize
from nltk.corpus import (
    cess_esp,
    wordnet,
    stopwords
)
#nltk.download('averaged_perceptron_tagger')
#nltk.download('wordnet')
#nltk.download('omw')
#nltk.download('cess_esp')

#import libraries for tokenization and ML
import json;
import keras;
import keras.preprocessing.text as kpt;

import sklearn
import pickle
from sklearn.preprocessing import Normalizer
from sklearn.feature_extraction.text import (
    CountVectorizer,
    TfidfVectorizer
)
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

#Import all libraries for creating a deep neural network
#Sequential is the standard type of neural network with stackable layers
from keras.models import (
    Sequential,
    model_from_json
)
#Dense: Standard layers with every node connected, dropout: avoids overfitting
from keras.layers import Dense, Dropout, Activation;

class Analysis():
    def __init__(self):
        self.index = 0

    #preprocess text in tweets by removing links, @UserNames, blank spaces, etc.
    def preprocessing_text(self,table):
        #put everythin in lowercase
        table['message'] = table['message'].str.lower()
        #Replace rt indicating that was a retweet
        table['message'] = table['message'].str.replace('rt', '')
        #Replace occurences of mentioning @UserNames
        table['message'] = table['message'].replace(r'@\w+', '', regex=True)
        #Replace links contained in the tweet
        table['message'] = table['message'].replace(r'http\S+', '', regex=True)
        table['message'] = table['message'].replace(r'www.[^ ]+', '', regex=True)
        #remove numbers
        table['message'] = table['message'].replace(r'[0-9]+', '', regex=True)
        #replace special characters and puntuation marks
        table['message'] = table['message'].replace(r'[!"#$%&()*+,-./:;<=>?@[\]^_`{|}~]', '', regex=True)
        return table   
    #Replace elongated words by identifying those repeated characters and then remove them and compare the new word with the english lexicon
    def in_dict(self,word):
        if wordnet.synsets(word, lang='spa'):
            #if the word is in the dictionary, we'll return True
            return True
    
    def replace_elongated_word(self,word):
        regex = r'(\w*)(\w+)\2(\w*)'
        repl = r'\1\2\3'    
        if self.in_dict(word):
            return word
        new_word = re.sub(regex, repl, word)
        if new_word != word:
            return self.replace_elongated_word(new_word)
        else:
            return new_word
    
    def detect_elongated_words(self,row):
        regexrep = r'(\w*)(\w+)(\2)(\w*)'
        words = [''.join(i) for i in re.findall(regexrep, row)]
        for word in words:
            if not self.in_dict(word):
                row = re.sub(word, replace_elongated_word(word), row)
        return row 
    def stop_words(self,table):
        #We need to remove the stop words
        stop_words_list = stopwords.words('spanish')
        table['message'] = table['message'].str.lower()
        table['message'] = table['message'].apply(lambda x: ' '.join([word for word in x.split() if word not in (stop_words_list)]))
        return table
    
    def replace_antonyms(self,word):
        #We get all the lemma for the word
        for syn in wordnet.synsets(word, lang='spa'):
            for lemma in syn.lemmas('spa'): 
                #if the lemma is an antonyms of the word
                for antonym in lemma.antonyms(): 
                    #we return the antonym
                    print("CHANGE WORD: ",antonym)
                    return antonym.name()
        return word
                
    def handling_negation(self,row):
        #Tokenize the row
        words = word_tokenize(row)
        
        # Read the corpus into a list, 
        # each entry in the list is one sentence.
        tagged_cess_sents = cess_esp.tagged_sents()
        # Train the unigram tagger
        uni_tag = ut(tagged_cess_sents)
        # Tagger reads a list of tokens.
        #uni_tag.tag(words)
        print("WORDS: ",words)
        print("TAGGGGS: ",uni_tag.tag(words))
        
        '''
        # Split corpus into training and testing set.
        train = int(len(tagged_cess_sents)*90/100)
        # Train a bigram tagger with only training data
        bi_tag = bt(tagged_cess_sents[:train], backoff=uni_tag)
        # Evaluates on testing data remaining 10%
        bi_tag.evaluate(tagged_cess_sents[train+1:])
        # Using the tagger.
        #bi_tag.tag(row)
        print("TAGGGGS: ",bi_tag.tag(row))
        '''
        speach_tags = ['JJ', 'JJR', 'JJS', 'NN', 'VB', 'VBD', 'VBG', 'VBN', 'VBP']
        #We obtain the type of words that we have in the text, we use the pos_tag function
        tags = nltk.pos_tag(words)
        print("WORDS: ", words)
        print("TAGS: ",tags)
        #Now we ask if we found a negation in the words
        tags_2 = ''
    
        if "no" in words:
            tags_2 = tags[words.index("no"):]
            words_2 = words[words.index("no"):]
            words = words[:words.index("no")] 
        
        print("tags_2 ",tags_2)  
        print("words_2 ",words_2)
        print("words ",words)
    
        for index, word_tag in enumerate(tags_2):
            print("index ",index)
            if word_tag[1] in speach_tags:
                print("REPLACE",word_tag[0])
                words = words+[replace_antonyms(word_tag[0])]+words_2[index+2:]
                #break
        #print("WORDS: ",words)
        print("FINAL TAGS2: ",tags_2)
        print("FINAL WORDS: ",' '.join(words))
        return ' '.join(words)    
    
    def cleaning_table(self,table):
        #This function will process all the required cleaning for the text in our tweets
        table = preprocessing_text(table)
        table['message'] = table['message'].apply(lambda x: detect_elongated_words(x))
        table = stop_words(table)
        table['message'] = table['message'].apply(lambda x: handling_negation(x))
        return table
    
    
    #Split Data into training and test dataset
    def splitting(self,table):
        X_train, X_test, y_train, y_test = self.train_test_split(table.message, table.sentiment, test_size=0.2, shuffle=True)
        return X_train, X_test, y_train, y_test
    
    #Tokenization for analysis
    def tokenization_tweets(self,dataset, features):
        ''''
        tokenization = TfidfVectorizer(max_features=features)
        tokenization.fit(cess_esp.words())
    
        with open('modelo.pickle', 'wb') as handle:
            pickle.dump(tokenization, handle, protocol=pickle.HIGHEST_PROTOCOL)
        '''
        
        with open('modelo.pickle', 'rb') as handle:
            tokenization_load = pickle.load(handle)
        dataset_transformed = tokenization_load.transform(dataset).toarray()
        return dataset_transformed
    
    
    
    #Create a Neural Network
    #Create the model
    '''
    features = 3500
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 6
        lr = 0.002
        epsilon = 1e-8
        validation = 0.1
    '''
    def train(self,X_train_mod, y_train, features, shuffle, drop, layer1, layer2, epoch, lr, epsilon, validation):
        model_nn = Sequential()
        model_nn.add(Dense(layer1, input_shape=(features,), activation='relu'))
        model_nn.add(Dropout(drop))
        model_nn.add(Dense(layer2, activation='sigmoid'))
        model_nn.add(Dropout(drop))
        model_nn.add(Dense(3, activation='softmax'))
        
        optimizer = keras.optimizers.Adam(lr=lr, beta_1=0.9, beta_2=0.999, epsilon=epsilon, decay=0.0, amsgrad=False)
        model_nn.compile(loss='sparse_categorical_crossentropy',
                     optimizer=optimizer,
                     metrics=['accuracy'])
        model_nn.fit(np.array(X_train_mod), y_train,
                     batch_size=32,
                     epochs=epoch,
                     verbose=1,
                     validation_split=validation,
                     shuffle=shuffle)
        return model_nn
    
    
    
    def test(self,X_test, model_nn):
        prediction = model_nn.predict(X_test)
        return prediction
    
    def model1(self,X_train, y_train):   
        features = 3500
        shuffle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.001
        epsilon = None
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shuffle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model1(X_train, y_train)
    
    def model2(self,X_train, y_train):   
        features = 3000
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.001
        epsilon = None
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model2(X_train, y_train)
    
    def model3(self,X_train, y_train):   
        features = 3500
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.002
        epsilon = None
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model_final = model3(X_train, y_train)
    
    def model4(self,X_train, y_train):   
        features = 5000
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 2
        lr = 0.005
        epsilon = None
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model4(X_train, y_train)
    
    def model5(self,X_train, y_train):   
        features = 3500
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.002
        epsilon = 1e-5
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model5(X_train, y_train)
    
    def model6(self,X_train, y_train):   
        features = 3500
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.002
        epsilon = 1e-8
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model6(X_train, y_train)
    
    def model7(self,X_train, y_train):   
        features = 3500
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 6
        lr = 0.002
        epsilon = 1e-8
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model7(X_train, y_train)
    
    def model8(self,X_train, y_train):   
        features = 3500
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.002
        epsilon = 1e-9
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model8(X_train, y_train)
    
    def model9(self,X_train, y_train):   
        features = 3500
        shufle = False
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.002
        epsilon = 1e-9
        validation = 0.1
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model9(X_train, y_train)
    
    def model10(self,X_train, y_train):   
        features = 3500
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.002
        epsilon = 1e-9
        validation = 0.2
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model10(X_train, y_train)
    
    def model11(self,X_train, y_train):   
        features = 3000
        shufle = True
        drop = 0.5
        layer1 = 512
        layer2 = 256
        epoch = 5
        lr = 0.002
        epsilon = 1e-9
        validation = 0.2
        X_train_mod = self.tokenization_tweets(X_train, features)
        model = self.train(X_train_mod, y_train, features, shufle, drop, layer1, layer2, epoch, lr, epsilon, validation)
        return model;
    #model11(X_train, y_train)
    
    def save_model(self,model):
        model_json = model.to_json()
        with open('model.json', 'w') as json_file:
            json_file.write(model_json)
    
        model.save_weights('model.h5')
    
    def get_tweets(self,index_search):
        index_aux = "tweets-sentiment_"+index_search
        df1 = DataFrame.from_es(url="http://192.168.127.129:9200", index=index_aux, doc_type="new-tweet", compat=6)
        es = Elasticsearch('http://192.168.127.129:9200')
        results = es.search(index=index_aux, size=5600, body={"query": {"match_all": {}}})
        df = Select.from_dict(results).to_pandas()
        return df
            
    '''
    if __name__ == "__main__":    
        #First we draw a word cloud
        tweet_table = get_tweets()
        #For All tweets
        word_cloud(pd.Series([t for t in tweet_table.message]).str.cat(sep=' '))    
        #For positive tweets 
        word_cloud(pd.Series([t for t in tweet_table[tweet_table.sentiment == "positive"].message]).astype(str).str.cat(sep=' '))   
        #For negative tweets
        word_cloud(pd.Series([t for t in tweet_table[tweet_table.sentiment == "negative"].message]).astype(str).str.cat(sep=' '))
    
    if __name__ == "__main__":
        #Get the frequency
        word_frequency = vectorization(tweet_table).sort_values(0, ascending = False)
        word_frequency_pos = vectorization(tweet_table[tweet_table['sentiment'] == 'positive']).sort_values(0, ascending = False)
        word_frequency_neg = vectorization(tweet_table[tweet_table['sentiment'] == 'negative']).sort_values(0, ascending = False)
    
        #Graph with frequency words all, positive and negative tweets and get the frequency
        graph(word_frequency, 'all')
        graph(word_frequency_pos, 'positive')
        graph(word_frequency_neg, 'negative')
    
    if __name__ == "__main__":
        #Concatenate word frequency for positive and negative
        table_regression = pd.concat([word_frequency_pos, word_frequency_neg], axis=1, sort=False)
        table_regression.columns = ["positive", "negative"]
        regression_graph(table_regression)
    
    '''

    def run(self, text):
        #print("Entra Analysis ", text)
        '''
        #GENERAR MODELO############################
        tweet_table = pd.read_csv("general-tweets-train-tagged.csv", delimiter = ',',names = ['message','sentiment','agreement'],encoding ='utf-8')
        tweet_table = cleaning_table(tweet_table) 
        X_interaction = self.tokenization_tweets(tweet_table.message, 3500)
        #print(X_interaction.shape)
        #print(X_interaction.shape)
        #tweet_table['sentiment'] = tweet_table['sentiment'].apply(lambda x: 2 if (x == 'P' or x== 'P+') else (0 if (x == 'N' or x == 'N+') else 1))
        tweet_table['sentiment'] = tweet_table['sentiment'].apply(lambda x: 4 if x=='P+' else 3 if x=='P' else 2 if (x=='NONE' or x=='NEU') else 1 if x=='N' else 0 if x=='N+' else 2)
        X_train, X_test, y_train, y_test = self.splitting(tweet_table)
        #print("XTRAIN",X_train)
        #print("YTRAIN",y_train)
        model_final = self.model7(X_train, y_train)
        self.save_model(model_final)
        #############################################
        '''
    
        if text is not None:
            '''
            tweet_table_interaction = self.get_tweets(sys.argv[1])
            tweet_table_csv = tweet_table_interaction.copy()	
            registros = tweet_table_interaction.shape[0]
            print("registros: ",registros)
            tweet_table_interaction = self.cleaning_table(tweet_table_interaction) 
            X_interaction = self.tokenization_tweets(tweet_table_interaction.message, 3500)
            X_interaction = np.resize(X_interaction,(registros,3500))
            #print(X_interaction.shape)
            #print(X_interaction)
        
            '''
            #print(str(nltk.corpus.cess_esp))
            data = {'message':[text]} 
            
            tweet_table_interaction = pd.DataFrame(data) 
            #tweet_table_interaction = self.cleaning_table(tweet_table_interaction)
            #print("LIMPIO: ",tweet_table_interaction)  
            X_interaction = self.tokenization_tweets(tweet_table_interaction.message, 3500)
            X_interaction=np.resize(X_interaction,(1,3500))

            # Open json file of saved model
            json_file = open('model.json', 'r')
            loaded_model_json = json_file.read()
            json_file.close()

            # Create a model 
            model = model_from_json(loaded_model_json)
            # Weight nodes with saved values
            model.load_weights('model.h5')
            int_prediction = model.predict(X_interaction)
            #print("int_prediction", int_prediction)
            labels = ['negative', 'neutral', 'positive']
            #labels = ['n+','n','none','p','p+']
            sentiments = [labels[np.argmax(pred)] for pred in int_prediction]
            tweet_table_interaction["sentiment"] = sentiments
 
            #print("SENTIMIENTOS:",sentiments)
            return sentiments
            #tweet_table_csv["sentiment_ML"] = sentiments
            #tweet_table_csv.to_csv("tweets_final_sentiment.csv")
        else:
            return None

