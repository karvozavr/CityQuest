from django.db import models


class QuestInfo(models.Model):
    name = models.TextField(max_length=100)
    author = models.TextField(max_length=100)
    # Image is kept as site.
    image = models.TextField(max_length=500)

    avg_distance = models.FloatField()
    description = models.TextField(max_length=750)

    # This fields must NOT be filled.
    rating = models.BigIntegerField(default=0, blank=True)     # Sum of all rates. We don't like float.
    people_passed = models.IntegerField(default=0, blank=True)

    def __str__(self):
        return 'Quest "' + self.name + '" by ' + self.author


class QuestStep(models.Model):
    title = models.TextField(max_length=100)
    description = models.TextField(max_length=750)
    goal = models.TextField(max_length=150)
    step_type = models.CharField(max_length=8)

    latitude = models.FloatField(blank=True, null=True)
    longitude = models.FloatField(blank=True, null=True)

    # This is a string - concatenation of keywords with \n as delimiter.
    keywords = models.TextField(max_length=1000, blank=True)

    quest_host = models.ForeignKey(QuestInfo, on_delete=models.CASCADE)
    step_number = models.IntegerField(default=0)
