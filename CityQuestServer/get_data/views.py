from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from get_data.models import QuestInfo, QuestStep
from django.core import serializers
from json import loads


def get_views(request):
    starts_from = int(request.GET.get('from'))
    amount = int(request.GET.get('len'))
    substr = request.GET.get('contains')

    s = serializers.serialize("json", QuestInfo.objects
                              .order_by('id')
                              .filter(name__contains=substr)[starts_from:starts_from + amount])
    return JsonResponse(loads(s), safe=False)


def get_steps(request):
    # ATTENTION: quest_ids are numerated from 1, not from 0
    quest_id = request.GET.get('id')

    s = serializers.serialize("json", QuestStep.objects
                              .filter(quest_host_id=quest_id)
                              .order_by('step_number'))

    return JsonResponse(loads(s), safe=False)


def get_number(request):
    n = QuestInfo.objects.count()

    return JsonResponse(loads(str(n)), safe=False)


def fill_database(request):
    QuestInfo.objects.all().delete()

    for i in range(1, 21):
        b = QuestInfo()
        b.name = 'Quest number ' + str(i)
        b.author = 'Me'
        b.image = '0' * 123456
        b.avg_distance = 3.14
        b.description = 'This is generated quest for testing.'
        b.save()

        for j in range(1, 4 + i % 3):
            s = QuestStep()
            s.title = 'QuestStep #' + str(j) + ' for quest #' + str(i)
            s.description = 'Just quest step'
            s.goal = 'complete this step'
            if j % 3 == 0:
                s.step_type = 'geo'
                s.latitude = 60
                s.longitude = 30
            else:
                s.step_type = 'key'
                s.description += '. Enter the word "password".'
                s.keywords = "password\n"

            s.quest_host_id = i
            s.step_number = j
            s.save()

        s = QuestStep()
        s.title = 'Final QuestStep for quest #' + str(i)
        s.description = 'Final quest step.'
        s.goal = 'Finish the quest.'
        s.quest_host_id = i
        s.step_number = 4 + i % 3
        s.step_type = 'final'
        s.save()

    return HttpResponse("done")
