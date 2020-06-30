import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp(functions.config().firebase)

export const onMessageCreate = functions.database
.ref('/adminControl/notifications/{nId}')
.onCreate((snap, context) => {

    const notification = snap.val();
    const topic = "notification"

    const payload = {
        notification:{
            title: notification.title,
            body: notification.body
        }
    }

    return admin.messaging().sendToTopic(topic, payload).then(res=>{              
        console.log('notification sent ')                   //if notification is deliverd then "notification sent is displayed in the log files"
        }).catch(err=>{                                     //catch error here
        console.log('notification sent '+err)            //if notification is failed then "notification err is displayed in the log files"
        })

})