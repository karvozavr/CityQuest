from django import forms
from .models import json_quest


class InvisibleForm(forms.ModelForm):

    class Meta:
        model = json_quest
        fields = ('json', )