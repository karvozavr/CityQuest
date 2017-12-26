from django.conf.urls import url
from django.contrib import admin
from get_data import views


urlpatterns = [
    url(r'get_views.*?$', views.get_views, name='get_views'),
    url(r'get_steps.*?$', views.get_steps, name='get_steps'),
    url(r'get_number$', views.get_number, name='get_number'),

    # Utility function. Never call it.
    url(r'fill$', views.fill_database, name='fill_database'),
]
