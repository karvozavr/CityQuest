package ru.spbau.cityquest.webui.earlyloader

import kotlin.browser.document

fun performChecks() {
    if (document.head == null)
        println("Warning: document.head is null, the Early Loader may not work properly.")
}


fun createGoogleMapsLoader() {
    val loaderScript = document.createElement("script")
    loaderScript.setAttribute("async", "")
    loaderScript.setAttribute("defer", "")
    loaderScript.setAttribute("src",
            "https://maps.googleapis.com/maps/api/js?key=$GOOGLE_MAPS_API_KEY&callback=earlyloader.ru.spbau.cityquest.webui.earlyloader.createQuestEditorLoader")
    document.head?.appendChild(loaderScript)
}

@JsName("createQuestEditorLoader")
fun createQuestEditorLoader() {
    var loaderScript = document.createElement("script")
    loaderScript.setAttribute("src", "CityQuestWebUI/CityQuestWebUI.js")
    document.head?.appendChild(loaderScript);
}

fun main(args : Array<String>) {
    println("CityQuest Early Loader in Kotlin is at your service!")
    performChecks()
    createGoogleMapsLoader()
    println("Google maps loader has been added to the HTML DOM.")
    //while (js("typeof(google) === 'undefined'")) { }
    //createQuestEditorLoader()
    println("CityQuest Early loader has finished its functions. Bye!")
}