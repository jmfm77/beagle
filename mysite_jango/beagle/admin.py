# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin

from .models import SecuredAccounts

from .models import Users

# Register your models here.
admin.site.register(SecuredAccounts)
admin.site.register(Users)