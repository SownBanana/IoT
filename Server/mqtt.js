const mqtt = require('mqtt');
const endClient = require('./Model/end-client-layer.js')
const client = mqtt.connect('mqtt://broker.hivemq.com')
const express = require('express')
const app = express()
const PORT = process.env.PORT || 3000;
var requestMessage=0,statusMessageQueue={};

function sendMessage(topic,message){
  if(message.assignList!== undefined){
    for (i = 0; i < message.assignList.length; i++) {
      message.assignList[i].mssid=requestMessage;
      requestMessage++;
      statusMessageQueue[requestMessage]={resp_type:"Not recv",mss:""};
    }
  }
  else{
    message.mssid=requestMessage;
    requestMessage++;
    statusMessageQueue[requestMessage]={resp_type:"Not recv",mss:""};
  }
  client.publish(topic, JSON.stringify(message));

}
var deviceDict = {};
var defaultDevice = {
  "assignList": [
    {
      "mssid": 1,
      "id": 1, //Manage by server
      "type": 1, //ex LED 1, AIR 2, NFC 3
      "pin": 5
    },
    {
      "mssid": 2,
      "id": 2,
      "type": 2,
      "pin": 7
    }
  ],
  "permissions": [
    4324234324,
    10043242342340,
    4234324234
  ]
}

for (i = 0; i < defaultDevice.assignList.length; i++) {
  console.log(defaultDevice.assignList[i].id);
  deviceDict[defaultDevice.assignList[i].id] = [];
}
client.on('connect', () => {
  client.subscribe('sownInfo')
  client.subscribe('sownAssign')
  client.subscribe('sownResponse')
})
client.on("message", (topic, message) => {
  try {
    if (topic == 'sownInfo') {
      var obj = JSON.parse(message);
      check = false;
      for (var key in deviceDict) {
        if (key == obj.id) {
          check = true;
          break;
        }
      }
      if (check == true){
        deviceDict[obj.id].push(obj);
      }
      else
        console.log("Not assign id ");
    }
    if (topic == 'sownAssign') {


    }
    if (topic == 'sownResponse') {
      var message = JSON.parse(message);
      console.log(message);
      statusMessageQueue[message.mssid]={resp_type:message.resp_type,mss:message.mss};
    }
  }
  catch (e) {
    console.log(e);
  }


})

//API
app.get('/getDevice', (req, res) => {
  id=req.query.id
  return res.send(JSON.stringify(deviceDict[id]));
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});