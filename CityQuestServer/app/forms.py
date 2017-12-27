from django import forms
from .models import JsonQuest


class InvisibleForm(forms.ModelForm):

    class Meta:
        model = JsonQuest
        fields = ('json', )