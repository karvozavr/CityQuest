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
    # try:
        json_data = request.POST.get('json')

        deserialized = json.loads(json_data)

        n = len(deserialized["steps"])
        if n == 0:
            raise Exception

        q = QuestInfo()
        q.name = deserialized["name"]
        q.author = deserialized["author"]
        q.image = '0'
        q.avg_distance = deserialized["avg_distance"]
        q.description = deserialized["description"]

        q.save()

        i = 0
        for step in deserialized["steps"]:
            s = QuestStep()
            s.title = step["title"]
            s.step_number = i
            s.step_type = step["type"]
            s.description = step["description"]
            s.goal = step["goal"]
            if s.step_type == 'geo':
                s.latitude = step["lat"]
                s.longitude = step["lng"]
                s.keywords = ""
            elif s.step_type == 'key':
                s.keywords = step["keyword"]

            s.quest_host_id = q.id
            s.save()

            i += 1

    # It is not clear if I should do it
        #return redirect('index')
    #except Exception:
        return redirect('index')
