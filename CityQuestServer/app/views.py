from django.shortcuts import render, redirect
from django.template import loader
from django.http import HttpResponse


def index(request):
    template = loader.get_template('page.html')
    context = {}
    return HttpResponse(template.render(context, request))


def save(request):
    json = request.POST.get('json')

    return HttpResponse(json)

    # It is not clear if I should do it
    return redirect('index')
