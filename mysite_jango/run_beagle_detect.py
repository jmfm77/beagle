#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import unicode_literals
import os
import sys
import django
from django.test import TestCase
from beagle.detect_ciberbullying import Detect
import threading
from datetime import datetime

#load django
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "mysite.settings")
django.setup()
from beagle.models import SecuredAccounts
from beagle.models import Users

# Create your tests here.
accounts = SecuredAccounts.objects.filter(searching=1)
if len(accounts) == 0:
	print("NOTHING FOR DETECT...")	
# datetime object containing current date and time
now = datetime.now()
search = Detect(now)	
for account in accounts:
	print("Detecting Account: ", account.id_secured_account)
	user = Users.objects.get(id_user=account.user)
	search.run(account.uri,user.username)
		

