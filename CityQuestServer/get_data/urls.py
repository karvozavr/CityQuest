from django.conf.urls import url
from django.contrib import admin
from get_data import views


urlpatterns = [
    url(r'^$', views.index, name='get_data_index'),
]
