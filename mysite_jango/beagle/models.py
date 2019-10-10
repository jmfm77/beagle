# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from django.utils import timezone
import datetime

# Create your models here.
class SecuredAccounts(models.Model):
    id_secured_account = models.BigAutoField(primary_key=True)
    name = models.CharField(max_length=100)
    description = models.CharField(max_length=250, blank=True, null=True)
    password = models.CharField(max_length=400)
    user = models.BigIntegerField()
    username = models.CharField(max_length=400)
    uri = models.CharField(max_length=500, blank=True, null=True)
    searching = models.CharField(max_length=1, blank=True, null=True)

    def __str__(self):
        return self.name
    class Meta:
        managed = False
        db_table = 'secured_accounts'


class Users(models.Model):
    id_user = models.BigAutoField(primary_key=True)
    username = models.CharField(max_length=256)
    password = models.CharField(max_length=256)
    role = models.CharField(max_length=45, blank=True, null=True)
    token = models.CharField(max_length=36, blank=True, null=True)
    created_on = models.CharField(max_length=20)
    last_token = models.CharField(max_length=20, blank=True, null=True)
    def __str__(self):
        return self.username
    class Meta:
        managed = False
        db_table = 'users'
