#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import unicode_literals
import os
import sys
import django
from django.test import TestCase
from beagle.searchtweets.main import TweetSearch
import threading

#load django
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "mysite.settings")
django.setup()
from beagle.models import SecuredAccounts

# Create your tests here.
try:
	if len(sys.argv) > 1:
		if sys.argv[1] == 'reset':
			accounts = SecuredAccounts.objects.filter(searching=1)
			if len(accounts) == 0:
				print("NOTHING FOR RESET...")
			else:	
				for account in accounts:
					SecuredAccounts.objects.filter(id_secured_account=account.id_secured_account).update(searching=0)
					print("Reset Account: ", account.id_secured_account)
		else:	
			account = SecuredAccounts.objects.get(id_secured_account=sys.argv[1])
			if account is not None:
				print("Searching Account: ", account.id_secured_account)
				SecuredAccounts.objects.filter(id_secured_account=account.id_secured_account).update(searching=1)
				search = TweetSearch(account.uri)
				search.run(account.description)
	else:
		accounts = SecuredAccounts.objects.filter(searching=0)
		if len(accounts) == 0:
			print("NOTHING FOR SEARCH...")	
		for account in accounts:
			SecuredAccounts.objects.filter(id_secured_account=account.id_secured_account).update(searching=1)
			print("Searching Account: ", account.id_secured_account)
			search = TweetSearch(account.uri)
			#search.run(account.description)
			thread = threading.Thread(target=search.run,args=(account.description,))
			thread.start()
except:
	print("Run Beagle - Error inesperado:", sys.exc_info()[0])
	accounts = SecuredAccounts.objects.filter(searching=1)
	if len(accounts) == 0:
		print("NOTHING FOR RESET...")
	else:	
		for account in accounts:
			SecuredAccounts.objects.filter(id_secured_account=account.id_secured_account).update(searching=0)
			print("Reset Account: ", account.id_secured_account)	
	
	
	
