from django.db import models


class JsonQuest(models.Model):
    json = models.TextField(max_length=100000)
