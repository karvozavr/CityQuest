from django.http import HttpResponse, JsonResponse

from data.models import QuestInfo, QuestStep
from django.core import serializers
from json import loads


def get_infos(request):
    starts_from = int(request.GET.get('from'))
    amount = int(request.GET.get('len'))
    substr = request.GET.get('contains')

    s = serializers.serialize("json", QuestInfo.objects
                              .order_by('-id')
                              .filter(name__contains=substr)[starts_from:starts_from + amount])
    return JsonResponse(loads(s), safe=False)


def get_info(request):
    quest_id = int(request.GET.get('id'))

    s = serializers.serialize("json", QuestInfo.objects.filter(pk=quest_id))[1:-1]
    return JsonResponse(loads(s), safe=False)


def get_steps(request):
    quest_id = request.GET.get('id')

    s = serializers.serialize("json", QuestStep.objects
                              .filter(quest_host_id=quest_id)
                              .order_by('step_number'))

    return JsonResponse(loads(s), safe=False)


def post_rating(request):
    rating_update = int(request.GET.get('rate'))
    quest_id = int(request.GET.get('id'))

    quest = QuestInfo.objects.get(pk=quest_id)
    quest.rating += rating_update
    quest.people_passed += 1
    quest.save()

    return HttpResponse("done")
