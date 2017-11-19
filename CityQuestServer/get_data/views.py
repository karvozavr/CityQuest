from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from get_data.models import QuestInfo
from django.core import serializers
from json import loads

#Temporal function
def index(request):
    return HttpResponse("This is get_data section!")
    #s = serializers.serialize("json", QuestInfo.objects.filter(id=1))
    #return JsonResponse(loads(s), safe=False)
