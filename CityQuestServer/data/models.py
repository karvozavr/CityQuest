from django.db import models


class QuestInfo(models.Model):
    name = models.TextField(max_length=100)
    # TODO: how to store image and user?
    author = models.TextField(max_length=100)
    # TODO: if we will keep image like this, make strict rule to not upload image bigger than 256 kb
    image = models.TextField(max_length=262144)

    avg_distance = models.FloatField()
    description = models.TextField(max_length=500)

    # This fields must NOT be filled.
    rating = models.BigIntegerField(default=0, blank=True)     # Sum of all rates. We don't like float.
    people_passed = models.IntegerField(default=0, blank=True)

    def __str__(self):
        return 'Quest "' + self.name + '" by ' + self.author


class QuestStep(models.Model):
    title = models.TextField(max_length=100)
    description = models.TextField(max_length=500)
    goal = models.TextField(max_length=128)
    step_type = models.CharField(max_length=8)

    latitude = models.FloatField(blank=True, null=True)
    longitude = models.FloatField(blank=True, null=True)

    # ATTENTION: this is a string - concatenation of keywords with \n as delimiter.
    keywords = models.TextField(max_length=2000, blank=True)

    # ATTENTION: quests are numerated from 1, so quest_host must be numerated from 1
    quest_host = models.ForeignKey(QuestInfo, on_delete=models.CASCADE)

    # ATTENTION: quest steps are numerated from 0
    step_number = models.IntegerField(default=0)
