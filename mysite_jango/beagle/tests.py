# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.test import TestCase
from searchtweets.main import TweetSearch

from .models import SecuredAccounts

# Create your tests here.
account = SecuredAccounts.objects.get(name='1')
search = TweetSearch(1)
search.run(account.description)
