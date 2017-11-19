from django.db import models


class QuestInfo(models.Model):
    name = models.TextField(max_length=100)
    # TODO: how to store image and user?
    author = models.TextField(max_length=100)
    image = models.TextField(max_length=1000)

    avg_distance = models.FloatField()
    description = models.TextField(max_length=500)
    short_description = models.TextField(max_length=100)
    rating = models.FloatField()

    def __str__(self):
        return 'Quest "' + self.name + '" by ' + self.author


class QuestStep(models.Model):
    title = models.TextField(max_length=100)
    description = models.TextField(max_length=1000)
    goal = models.TextField(max_length=128)
    step_type = models.CharField(max_length=16)

    latitude = models.FloatField(blank=True)
    longitude = models.FloatField(blank=True)

    # ATTENTION: this is a string. TODO: what string?
    keywords = models.TextField(max_length=10000, blank=True)

    quest_host = models.ForeignKey(QuestInfo, on_delete=models.CASCADE)
    step_number = models.IntegerField(default=0)
