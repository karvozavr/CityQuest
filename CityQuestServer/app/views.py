import json

from django.shortcuts import render, redirect
from django.template import loader
from django.http import HttpResponse, JsonResponse

from data.models import QuestInfo, QuestStep


def index(request):
    template = loader.get_template('page.html')
    context = {}
    return HttpResponse(template.render(context, request))


def save(request):
    json_data = request.POST.get('json')

    deserialized = json.loads(json_data)

    q = QuestInfo()
    q.name = deserialized["name"]
    q.author = deserialized["author"]
    q.image = '0'
    q.avg_distance = deserialized["avg_distance"]
    q.description = deserialized["description"]

    q.save()

    for step in deserialized["steps"]:

        s = QuestStep()
        s.title = step["title"]
        s.step_number = step["step_number"]
        s.step_type = step["step_type"]
        s.description = step["description"]
        s.goal = step["goal"]
        if s.step_type == 'geo':
            s.latitude = step["latitude"]
            s.longitude = step["longitude"]
        elif s.step_type == 'key':
            s.keywords = step["keywords"]

        s.quest_host_id = q.id

        s.save()

    return HttpResponse(deserialized.items())

    # It is not clear if I should do it
    # return redirect('index')
