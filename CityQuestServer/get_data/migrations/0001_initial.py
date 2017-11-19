# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-11-19 16:56
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='QuestInfo',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.TextField(max_length=100)),
                ('author', models.TextField(max_length=100)),
                ('image', models.TextField(max_length=1000)),
                ('avg_distance', models.FloatField()),
                ('description', models.TextField(max_length=500)),
                ('short_description', models.TextField(max_length=100)),
                ('rating', models.FloatField()),
            ],
        ),
        migrations.CreateModel(
            name='QuestStep',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.TextField(max_length=100)),
                ('description', models.TextField(max_length=1000)),
                ('goal', models.TextField(max_length=128)),
                ('step_type', models.CharField(max_length=16)),
                ('latitude', models.FloatField(blank=True)),
                ('longitude', models.FloatField(blank=True)),
                ('keywords', models.TextField(blank=True, max_length=10000)),
                ('step_number', models.IntegerField(default=0)),
                ('quest_host', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='get_data.QuestInfo')),
            ],
        ),
    ]
