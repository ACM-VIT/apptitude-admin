# Apptitude Admin - Official Admin App of Apptitude 2020
**Apptitude Admin** is an app that was created to ensure smooth functioning of Apptitude 2020 conducted by @ACM-VIT . This app relieves an admin's job to go log into Firebase for every nitty-gritty detail. A one stop shop for admin to control the whole event! From Participant Details to making the event go live. This app has a security layer and prompts for a root password to modify any paramteres of the event.

## Screenshots
![Apptitude Admin Screenshots](assets/screenshot.png)

## How is this app built?
This app written in **Kotlin** that follows **MVVM architecture** and is used with **Android Architecture Components**. **Kodein** is used for dependency injection.

## Features
* Get list of all participant and thier details.
* Get list of all the teams that have been registered.
* Get participant details of the teams and other details such as the problem statements generated for the team.
* Send Notifications right from the admin app.
* Modify all parameters such as discord link , submission time , event start and much more! [privileged admins only]
* Managed permissions , not all admins are previliged admins , privileged admins have special access.

## Andorid Components Used
* **Android Lifecycle Components** - Used to implement the expose and observe pattern of LiveData.
* **LiveData** - For observing the data in ViewModel and from repository of the app.
* **ViewModel** - For managing UI data.
* **Navigation** - For simplifyed navigation through various fragments.
* **Android KTX** - To make the codebase simpler.

## Other libraries/services used
* **Gson** - For converting JSON to POJO and vice versa.
* **Kodein** - For easy Dependency Injection.
* **Firebase** - For fetch and update details regarding the event.
* **Firebase KTX** - To make easy use of Firebase in the codebase. 

## License
Copyright 2020 ACM-VIT

Licensed under MIT License :  https://opensource.org/licenses/MIT

<br>
<br>