from django.http import HttpResponse, JsonResponse

from data.models import QuestInfo, QuestStep
from django.core import serializers
from json import loads

def get_infos(request):
    starts_from = int(request.GET.get('from'))
    amount = int(request.GET.get('len'))
    substr = request.GET.get('contains')

    s = serializers.serialize("json", QuestInfo.objects
                              .order_by('id')
                              .filter(name__contains=substr)[starts_from:starts_from + amount])
    return JsonResponse(loads(s), safe=False)

def get_info(request):
    quest_id = int(request.GET.get('id'))

    s = serializers.serialize("json", QuestInfo.objects.filter(pk=quest_id))[1:-1]
    return JsonResponse(loads(s), safe=False)


def get_steps(request):
    # ATTENTION: quest_ids are numerated from 1, not from 0
    quest_id = request.GET.get('id')

    s = serializers.serialize("json", QuestStep.objects
                              .filter(quest_host_id=quest_id)
                              .order_by('step_number'))

    return JsonResponse(loads(s), safe=False)


def get_number(request):
    n = QuestInfo.objects.count()

    return JsonResponse(loads(str(n)), safe=False)


def post_rating(request):
    rating_update = int(request.GET.get('rate'))
    quest_id = int(request.GET.get('id'))

    quest = QuestInfo.objects.get(pk=quest_id)
    quest.rating += rating_update
    quest.people_passed += 1
    quest.save()

    return HttpResponse("done")


def fill_database(request):
    QuestInfo.objects.all().delete()

    for i in range(1, 40):
        b = QuestInfo()
        b.name = 'Quest number ' + str(i)
        b.author = 'Me'
        b.image = '0' * 123456
        b.avg_distance = 3.14
        b.description = 'This is generated quest for testing.'
        b.save()

        for j in range(1, 3 + i % 5):
            s = QuestStep()
            s.title = 'QuestStep #' + str(j) + ' for quest #' + str(i)
            s.description = 'Just quest step'
            s.goal = 'complete this step'
            if j % 3 == 0:
                s.step_type = 'geo'
                s.description += '. To complete this one, you should be at 501.'
                s.latitude = 60.00953
                s.longitude = 30.35279
            else:
                s.step_type = 'key'
                s.description += '. Enter the word "password" or another secret word combination you know.'
                s.keywords = "password\nanother secret word\nkitten"

            s.quest_host_id = i
            s.step_number = j
            s.save()

        s = QuestStep()
        s.title = 'Final QuestStep for quest #' + str(i)
        s.description = 'Final quest step.'
        s.goal = 'Finish the quest.'
        s.quest_host_id = i
        s.step_number = 3 + i % 5
        s.step_type = 'final'
        s.save()

    # New Quest!
    b = QuestInfo()
    b.name = 'Quest number ' + str(40)
    b.author = 'Me'
    b.image = '0' * 131751
    b.avg_distance = 1
    b.description = 'This is another (totally different) quest ' \
                    'for testing. Now that our time has come to fight\n' \
                    'Scotland must unite\n' \
                    'We\'ll make a stand on Stirling ground\n' \
                    'To put a challenge to the crown\n' \
                    'We are one, we have come\n' \
                    'We\'re here to break and end the occupation\n' \
                    'We have our nation\'s fate in hand\n' \
                    'It\'s time we make our final stand\n' \
                    'Rally all the clans\n' \
                    'Englishmen advance\n' \
                    'Blood of Bannockburn\n' \
                    'Point of no return\n' \
                    'Join the Scottish revolution\n' \
                    'Freedom must be won by blood\n' \
                    'Now we call for revolution\n' \
                    'Play the pipes and cry out loud\n'
    b.save()

    s1 = QuestStep()
    s1.title = 'QuestStep #1 for quest #40'
    s1.description = 'A difficult quest step. To complete this one, you should visit one of ' \
                     'this locations: Scotland, Sweden or room 501. ' \
                     'It must be done when the sun rises in the west ' \
                     'and sets in the east, when the seas go dry and mountains blow ' \
                     'in the wind like leaves.'
    s1.goal = 'Visit the room 501, find the boy, defeat the dragon, conquer England, take Jerusalem.'
    s1.step_type = 'geo'
    s1.latitude = 60.00953
    s1.longitude = 30.35279
    s1.quest_host_id = 40
    s1.step_number = 0
    s1.save()

    s2 = QuestStep()
    s2.title = 'QuestStep #2 for quest #40'
    s2.description = 'A difficult quest step. To complete this one, you should write one of the subjects' \
                     'in Academic University, starting with letter "A"'
    s2.goal = 'Write the word. Also, defend the king, obey the king, ' \
              'obey your father, protect the innocent, defend the weak.'
    s2.step_type = 'key'
    s2.keywords = "Algebra\nAlgorithms\nArchitecture\nAlternative languages\nAD"
    s2.quest_host_id = 40
    s2.step_number = 1
    s2.save()

    s = QuestStep()
    s.title = 'Final QuestStep for quest #40'
    s.description = 'You have completed the quest. +100xp. А еще это неплохое место, чтобы потестить, ' \
                    'хорошо ли все с русским языком. '
    s.goal = 'Finish the quest.'
    s.quest_host_id = 40
    s.step_number = 2
    s.step_type = 'final'
    s.save()

    return HttpResponse("done")
