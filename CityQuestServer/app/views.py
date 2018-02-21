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

    n = len(deserialized["steps"])

    q = QuestInfo()
    q.name = deserialized["name"]
    q.author = deserialized["author"]
    if "image" in deserialized:
        q.image = deserialized["image"]
    q.avg_distance = deserialized["avg_distance"]
    q.description = deserialized["description"]
    q.save()

    for i, step in zip(range(n), deserialized["steps"]):
        s = QuestStep()
        s.title = step["title"]
        s.step_number = i
        s.step_type = step["type"]
        s.description = step["description"]
        if "image" in step:
            s.image = step["image"]
        s.goal = step["goal"]

        if s.step_type == 'geo':
            s.latitude = step["lat"]
            s.longitude = step["lng"]
        elif s.step_type == 'key':
            s.keywords = step["keyword"]

        s.quest_host_id = q.id
        s.save()

    return redirect('index')
