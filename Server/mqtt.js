const mqtt = require('mqtt');
const endClient = require('./Model/end-client-layer.js')
const client = mqtt.connect('mqtt://broker.hivemq.com')
const express = require('express')
var mysql = require('mysql')
const app = express()
const PORT = process.env.PORT || 3000;
var requestMessage = 0, statusMessageQueue = {};

//connect to db
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "smart_home"
});

con.connect(function (err) {
  if (err) throw err;
  console.log("Connected!!!");
});

function sendMessage(topic, message) {
  if (message.assignList !== undefined) {
    for (i = 0; i < message.assignList.length; i++) {
      message.assignList[i].mssid = requestMessage;
      statusMessageQueue[requestMessage] = { resp_type: "Not recv", mss: "" };
      requestMessage++;
    }
  }
  else {
    message.mssid = requestMessage;
    statusMessageQueue[requestMessage] = { resp_type: "Not recv", mss: "" };
    requestMessage++;
  }
  client.publish(topic, JSON.stringify(message));

}
var deviceDict = {};
var defaultDevice = { //danh sach cac thiet bi cai dat
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
  "permissions": [  //danh sach cac the co the mo cua
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
      if (check == true) {
        deviceDict[obj.id].push(obj);
        console.log(obj.id);

        //luu vao database
        con.query("SELECT COUNT(*) AS num FROM device_info WHERE device_id = " + obj.id, function (err, result) {
          if (err) throw err;
          num = result[0].num;
          if(num>43200){
            con.query("DELETE FROM `device` WHERE device_id = " + obj.id + " AND time_stamp = (SELECT MIN(time_stamp) FROM `device_info`)", function (err, result) {
              if (err) throw err;
              console.log("Delete 1 row success!")
            });
          }
        });

        var sql = "SELECT type FROM device WHERE id = " + obj.id;
        var type;
        con.query(sql, function (err, result, type) {
          if (err) throw err;
          type = result[0].type;

          if(type == 1){                                //gui json LED
            sql = "INSERT INTO `device_info` (device_id, value, time_stamp) VALUES (" + obj.id + "," + obj.value + "," + obj.time_stamp + ");"
          }
          else if(type == 2){                           //gui json AIR
            sql = "INSERT INTO `device_info` (device_id, value, time_stamp, duration) VALUES (" + obj.id + "," + obj.value + "," + obj.time_stamp + "," + obj.duration +");"
          }
          else{                                         //gui json NFC
            sql = "INSERT INTO `device_info` (device_id, value, time_stamp, permission) VALUES (" + obj.id + "," + obj.value + "," + obj.time_stamp + "," + obj.permission +");"
          }


          con.query(sql, function (err, result) {
            if (err) throw err;
            console.log("Insert success!")
          });
        });
      }
      else
        console.log("Not assign id ");
    }
    if (topic == 'sownAssign') {


    }
    if (topic == 'sownResponse') {
      var message = JSON.parse(message);
      console.log(message);
      statusMessageQueue[message.mssid] = { resp_type: message.resp_type, mss: message.mss };
    }
  }
  catch (e) {
    console.log(e);
  }


})

//API
app.get('/getDevice', (req, res) => {
  id = req.query.id
  return res.send(JSON.stringify(deviceDict[id]));
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});

//lay trang thai hien thai cua bong den
app.get('/getStateBulb', (req, res) => {
  id = req.query.id;
  var sql = "SELECT value FROM `device_info` WHERE device_id = " + id + " AND time_stamp = (SELECT MAX(time_stamp) FROM `device_info`)";
  console.log(sql);
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("res = "+result);
    res.send(result);
  });
});

//lay lich su ra vao cua Cua tu
app.get('/getEntranceHistory', (req, res) => {
  id = req.query.id;
  var sql = "SELECT time_stamp, permission FROM `device_info` WHERE device_id = " + id;
  console.log(sql);
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("res = "+result);
    res.send(result);
  });
});

//lay chi so khong khi theo thoi gian
app.get('/getAirQuality', (req, res) => {
  id = req.query.id;
  var sql = "SELECT time_stamp, value FROM `device_info` WHERE device_id = " + id;
  console.log(sql);
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("res = "+result);
    res.send(result);
  });
});

//lay lich su bat/tat bong den
app.get('/getBulbHistory', (req, res) => {
  id = req.query.id;
  var sql = "SELECT time_stamp, value FROM `device_info` WHERE device_id = " + id;
  console.log(sql);
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("res = "+result);
    res.send(result);
  });
});