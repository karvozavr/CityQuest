from django.conf.urls import url
from django.contrib import admin
from data import views


urlpatterns = [
    url(r'get_infos.*?$', views.get_infos, name='get_infos'),
    url(r'get_info.*?$', views.get_info, name='get_info'),
    url(r'get_steps.*?$', views.get_steps, name='get_steps'),
    url(r'post_rating.*?$', views.post_rating, name='post_rating'),
]
