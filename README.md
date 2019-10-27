# PingMe
A CHAT app featuring different users chatting with a bot server and persisting messages.


# Overview

This app demonstrates the usage of Work manager with MVVM architecture and single source of truth strategy.

# ScreenShots

<img src = "https://github.com/kanch231004/PingMe/blob/master/screenshots/Chat%20UI.jpg" width = 350 /> <img src = "https://github.com/kanch231004/PingMe/blob/master/screenshots/User%20Sessions.jpg" width = 350/>

# Tech-Stack

* __Retrofit__ : For Network calls
* __Architecture__ : MVVM
* __Work Manager__ for pushing message into queue
* __Coroutines__ for background operations like inserting data into DB
* __Room database__ : For offline persistence
* __Live Data__ : To notify view for change
* __Dagger__ : For dependency injection
* __Language__ : Kotlin


